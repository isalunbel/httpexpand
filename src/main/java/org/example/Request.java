package org.example;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private final String path;
    private final List<NameValuePair> queryParams;

    public Request(String requestLine) {
        String[] parts = requestLine.split("\\s+");
        this.path = parsePath(parts[1]);
        this.queryParams = parseQueryParams(parts[1]);
    }

    public String getPath() {
        return path;
    }

    public String getQueryParam(String name) {
        for (NameValuePair param : queryParams) {
            if (param.getName().equals(name)) {
                return param.getValue();
            }
        }
        return null;
    }

    public List<NameValuePair> getQueryParams() {
        return queryParams;
    }

    private String parsePath(String requestPath) {
        try {
            URI uri = new URI(requestPath);
            return uri.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private List<NameValuePair> parseQueryParams(String requestPath) {
        try {
            URI uri = new URI(requestPath);
            String queryString = uri.getQuery();
            return URLEncodedUtils.parse(queryString, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}