package org.example;

import org.example.exceptions.CustomException;
import org.example.models.FilterRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws IOException, CustomException, ExecutionException, InterruptedException {

        List<CompletableFuture<Object>> futures = new ArrayList<>();

        // POST request test
        System.out.println("POST request");
        FilterRequest filterRequest = new FilterRequest(List.of("RELIANCE"), List.of("NSE"));
        Map<String , FilterRequest> requestBody = new HashMap<>();
        requestBody.put("filterRequest" , filterRequest);
        CompletableFuture<Object> ans1 = HttpClientUtils.postAsync("https://stockseyescloudrun-n2ermovi6q-el.a.run.app/searchInstruments", requestBody, null, null,  Object.class);
        futures.add(ans1);

        // GET request test
        System.out.println("GET request");
        CompletableFuture<Object> ans2 = HttpClientUtils.getAsync("https://stockseyescloudrun-n2ermovi6q-el.a.run.app/latestQuote",Map.of("tradingSymbol","RELIANCE"),null, Object.class);
        futures.add(ans2);


        System.out.println(ans1.get());
//        // PUT request
//        CompletableFuture<Void> all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
//        // DELETE request
//
//        all.join();

        throw new CustomException("Falied to get data");

    }
}