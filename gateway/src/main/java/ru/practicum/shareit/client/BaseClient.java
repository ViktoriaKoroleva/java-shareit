package ru.practicum.shareit.client;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class BaseClient {
    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected ResponseEntity<Object> sendGetRequest(String path) {
        return sendGetRequest(path, null, null);
    }

    protected ResponseEntity<Object> sendGetRequest(String path, long userId) {
        return sendGetRequest(path, userId, null);
    }

    protected ResponseEntity<Object> sendGetRequest(String path, Long userId, @Nullable Map<String, Object> parameters) {
        return executeRequest(HttpMethod.GET, path, userId, parameters, null);
    }

    protected <T> ResponseEntity<Object> sendPostRequest(String path, T body) {
        return sendPostRequest(path, null, null, body);
    }

    protected <T> ResponseEntity<Object> sendPostRequest(String path, long userId, T body) {
        return sendPostRequest(path, userId, null, body);
    }

    protected <T> ResponseEntity<Object> sendPostRequest(String path, Long userId, @Nullable Map<String, Object> parameters, T body) {
        return executeRequest(HttpMethod.POST, path, userId, parameters, body);
    }

    protected <T> ResponseEntity<Object> sendPutRequest(String path, long userId, T body) {
        return sendPutRequest(path, userId, null, body);
    }

    protected <T> ResponseEntity<Object> sendPutRequest(String path, long userId, @Nullable Map<String, Object> parameters, T body) {
        return executeRequest(HttpMethod.PUT, path, userId, parameters, body);
    }

    protected <T> ResponseEntity<Object> sendPatchRequest(String path, T body) {
        return sendPatchRequest(path, null, null, body);
    }

    protected <T> ResponseEntity<Object> sendPatchRequest(String path, long userId) {
        return sendPatchRequest(path, userId, null, null);
    }

    protected <T> ResponseEntity<Object> sendPatchRequest(String path, long userId, T body) {
        return sendPatchRequest(path, userId, null, body);
    }

    protected <T> ResponseEntity<Object> sendPatchRequest(String path, Long userId, @Nullable Map<String, Object> parameters, T body) {
        return executeRequest(HttpMethod.PATCH, path, userId, parameters, body);
    }

    protected ResponseEntity<Object> sendDeleteRequest(String path) {
        return sendDeleteRequest(path, null, null);
    }

    protected ResponseEntity<Object> sendDeleteRequest(String path, long userId) {
        return sendDeleteRequest(path, userId, null);
    }

    protected ResponseEntity<Object> sendDeleteRequest(String path, Long userId, @Nullable Map<String, Object> parameters) {
        return executeRequest(HttpMethod.DELETE, path, userId, parameters, null);
    }

    private <T> ResponseEntity<Object> executeRequest(HttpMethod method, String path, Long userId, @Nullable Map<String, Object> parameters, @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, createDefaultHeaders(userId));

        ResponseEntity<Object> shareitServerResponse;
        try {
            if (parameters != null && !parameters.isEmpty()) {
                shareitServerResponse = rest.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                shareitServerResponse = rest.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return handleResponse(shareitServerResponse);
    }

    private HttpHeaders createDefaultHeaders(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        if (userId != null) {
            headers.set("X-Sharer-User-Id", String.valueOf(userId));
        }
        return headers;
    }

    private static ResponseEntity<Object> handleResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}
