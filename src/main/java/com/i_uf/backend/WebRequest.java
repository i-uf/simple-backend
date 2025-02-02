package com.i_uf.backend;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WebRequest {
    InetSocketAddress getAddress();
    URI getURI();
    String getMethod();
    Map<String, String> getQuery();
    Map<String, List<String>> getHeader();
    String getBody();
    static WebRequest create(HttpExchange e) throws IOException {
        InetSocketAddress address = e.getRemoteAddress();
        URI uri = e.getRequestURI();
        String method = e.getRequestMethod();
        Map<String, String> query = new HashMap<>();
        if(uri.getQuery() != null) for(String entry : uri.getQuery().split("&")) {
            StringBuilder key = new StringBuilder(), value = new StringBuilder();
            boolean isValue = false;
            for(char c : entry.toCharArray()) {
                ((isValue != (isValue|=c=='=')) ? new StringBuilder() : isValue ? value : key).append(c);
            }
            query.put(key.toString(), value.toString());
        }
        Map<String, List<String>> header = e.getRequestHeaders();
        BufferedReader reader = new BufferedReader(new InputStreamReader(e.getRequestBody(), StandardCharsets.UTF_8));
        String body = reader.lines().reduce("", (a, b) -> a + '\n' + b);
        reader.close();

        return new WebRequest() {
            public InetSocketAddress getAddress() { return address; }
            public URI getURI() { return uri; }
            public String getMethod() { return method; }
            public Map<String, String> getQuery() { return query; }
            public Map<String, List<String>> getHeader() { return header; }
            public String getBody() { return body; }
        };
    }
}
