import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.example.HttpClientUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HttpClientUtilsTest {

    private CloseableHttpClient mockHttpClient;
    private ObjectMapper mockObjectMapper;

    @BeforeEach
    void setUp() {
        mockHttpClient = mock(CloseableHttpClient.class);
        HttpClientUtils.setHttpClient(mockHttpClient);

        mockObjectMapper = mock(ObjectMapper.class);
        HttpClientUtils.setObjectMapper(mockObjectMapper);
    }

    @Test
    void testGet() throws IOException {
        // Arrange
        String url = "https://example.com/api";
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("param1", "value1");
        queryParams.put("param2", "value2");

        String expectedResponseBody = "{\"key\":\"value\"}";

        CloseableHttpResponse mockHttpResponse = createMockHttpResponse(expectedResponseBody, HttpStatus.SC_OK);
        when(mockHttpClient.execute(any(HttpUriRequest.class))).thenReturn(mockHttpResponse);

        // Act
        TestResponse result = HttpClientUtils.get(url, queryParams, null, TestResponse.class);

        // Assert
        assertEquals("value", result.getKey());

        // Verify that the request was made with the correct URL and method
        ArgumentCaptor<HttpGet> requestCaptor = ArgumentCaptor.forClass(HttpGet.class);
        verify(mockHttpClient).execute(requestCaptor.capture());
        HttpGet capturedRequest = requestCaptor.getValue();
        assertEquals(url + "?param1=value1&param2=value2", capturedRequest.getURI().toString());
        assertEquals("GET", capturedRequest.getMethod());
    }

    @Test
    void testPost() throws IOException {
        // Arrange
        String url = "https://example.com/api";
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("param1", "value1");
        queryParams.put("param2", "value2");

        TestRequest requestBody = new TestRequest("testValue");

        String expectedResponseBody = "{\"key\":\"value\"}";

        CloseableHttpResponse mockHttpResponse = createMockHttpResponse(expectedResponseBody, HttpStatus.SC_OK);
        when(mockHttpClient.execute(any(HttpUriRequest.class))).thenReturn(mockHttpResponse);

        // Act
        TestResponse result = HttpClientUtils.post(url, requestBody, queryParams, null, TestResponse.class);

        // Assert
        assertEquals("value", result.getKey());

        // Verify that the request was made with the correct URL, method, and request body
        ArgumentCaptor<HttpPost> requestCaptor = ArgumentCaptor.forClass(HttpPost.class);
        verify(mockHttpClient).execute(requestCaptor.capture());
        HttpPost capturedRequest = requestCaptor.getValue();
        assertEquals(url + "?param1=value1&param2=value2", capturedRequest.getURI().toString());
        assertEquals("POST", capturedRequest.getMethod());
        assertEquals("{\"value\":\"testValue\"}", getRequestBodyAsString(capturedRequest));
    }

    // Similar tests for put and delete methods

    private CloseableHttpResponse createMockHttpResponse(String responseBody, int statusCode) throws IOException {
        StatusLine statusLine = mock(StatusLine.class);
        when(statusLine.getStatusCode()).thenReturn(statusCode);

        HttpEntity httpEntity = mock(HttpEntity.class);
        when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream(responseBody.getBytes()));

        CloseableHttpResponse response = mock(CloseableHttpResponse.class);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(httpEntity);

        return response;
    }

    private String getRequestBodyAsString(HttpEntityEnclosingRequestBase request) throws IOException {
        InputStream inputStream = request.getEntity().getContent();
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        return new String(bytes);
    }

    private static class TestRequest {
        private String value;

        public TestRequest(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private static class TestResponse {
        private String key;

        public String getKey() {
            return key;
        }
    }
}
