package ru.otus.java.basic.http.server;

public class HttpRequest {
    private String rawRequest;
    private String uri;
    private String method;

    public String getUri() {
        return uri;
    }

    public HttpRequest(String rawRequest) {
        this.rawRequest = rawRequest;
        this.parse();
    }

    private void parse() {
        int startIndex = rawRequest.indexOf(' ');
        int endIndex = rawRequest.indexOf(' ', startIndex + 1);
        this.uri = rawRequest.substring(startIndex + 1, endIndex);
        this.method = rawRequest.substring(0, startIndex);
    }

    public void printInfo(boolean showRawRequest) {
        System.out.println("uri: " + uri);
        System.out.println("method: " + method);
        if (showRawRequest) {
            System.out.println(rawRequest);
        }
    }
}
