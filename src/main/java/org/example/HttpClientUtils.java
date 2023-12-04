package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpClientUtils {

    private static HttpClient httpClient = HttpClients.createDefault();
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    private HttpClientUtils() {
        // Private constructor to prevent instantiation
    }

    public static <T> CompletableFuture<T> getAsync(String url, Map<String, String> queryParams, Map<String, String> headers, Class<T> responseType) {
        return executeAsync(() -> get(url, queryParams, headers, responseType));
    }

    public static <T> CompletableFuture<T> postAsync(String url, Object requestBody, Map<String, String> queryParams, Map<String, String> headers, Class<T> responseType) {
        return executeAsync(() -> post(url, requestBody, queryParams, headers, responseType));
    }

    public static <T> CompletableFuture<T> putAsync(String url, Object requestBody, Map<String, String> queryParams, Map<String, String> headers, Class<T> responseType) {
        return executeAsync(() -> put(url, requestBody, queryParams, headers, responseType));
    }

    public static <T> CompletableFuture<T> deleteAsync(String url, Map<String, String> queryParams, Map<String, String> headers, Class<T> responseType) {
        return executeAsync(() -> delete(url, queryParams, headers, responseType));
    }

    private static <T> CompletableFuture<T> executeAsync(AsyncRequest<T> asyncRequest) {
        CompletableFuture<T> future = new CompletableFuture<>();
        executorService.submit(() -> {
            try {
                T result = asyncRequest.execute();
                future.complete(result);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    public static <T> T get(String url, Map<String, String> queryParams, Map<String, String> headers, Class<T> responseType) throws IOException {
        HttpGet request = new HttpGet(buildUrlWithParams(url, queryParams));
        addHeaders(request, headers);
        return executeRequest(request, responseType);
    }

    public static <T> T post(String url, Object requestBody, Map<String, String> queryParams, Map<String, String> headers, Class<T> responseType) throws IOException {
        HttpPost request = new HttpPost(buildUrlWithParams(url, queryParams));
        addHeaders(request, headers);
        setRequestBody(request, requestBody);
        return executeRequest(request, responseType);
    }

    public static <T> T put(String url, Object requestBody, Map<String, String> queryParams, Map<String, String> headers, Class<T> responseType) throws IOException {
        HttpPut request = new HttpPut(buildUrlWithParams(url, queryParams));
        addHeaders(request, headers);
        setRequestBody(request, requestBody);
        return executeRequest(request, responseType);
    }

    public static <T> T delete(String url, Map<String, String> queryParams, Map<String, String> headers, Class<T> responseType) throws IOException {
        HttpDelete request = new HttpDelete(buildUrlWithParams(url, queryParams));
        addHeaders(request, headers);
        return executeRequest(request, responseType);
    }

    private static <T> T executeRequest(HttpUriRequest request, Class<T> responseType) throws IOException {
        HttpResponse response = httpClient.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        StringBuilder responseBody = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
        }

        T result = objectMapper.readValue(responseBody.toString(), responseType);

        if (statusCode >= 200 && statusCode < 300) {
            return result;
        } else {
            throw new IOException("HTTP request failed with status code: " + statusCode);
        }
    }

    private static void setRequestBody(HttpEntityEnclosingRequestBase request, Object requestBody) throws IOException {
        if (requestBody != null) {
            String jsonBody = objectMapper.writeValueAsString(requestBody);
            StringEntity entity = new StringEntity(jsonBody);
            entity.setContentType("application/json");
            request.setEntity(entity);
        }
    }

    private static void addHeaders(HttpUriRequest request, Map<String, String> headers) {
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    private static String buildUrlWithParams(String url, Map<String, String> queryParams) {
        if (queryParams == null || queryParams.isEmpty()) {
            return url;
        }

        StringBuilder urlWithParams = new StringBuilder(url);
        if (!url.contains("?")) {
            urlWithParams.append("?");
        } else {
            urlWithParams.append("&");
        }

        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        String paramString = String.join("&", params.stream()
                .map(p -> p.getName() + "=" + p.getValue())
                .toArray(String[]::new));

        urlWithParams.append(paramString);
        return urlWithParams.toString();
    }

    public static void setHttpClient(CloseableHttpClient mockHttpClient) {
        httpClient = mockHttpClient;
    }

    public static void setObjectMapper(ObjectMapper mockObjectMapper) {
        objectMapper = objectMapper;
    }

    public static void shutdownExecutorService() {
        executorService.shutdown();
    }

    @FunctionalInterface
    private interface AsyncRequest<T> {
        T execute() throws Exception;
    }
}
