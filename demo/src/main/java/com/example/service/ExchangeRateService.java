package com.example.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeRateService {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    private static final URI[] ENDPOINTS = new URI[] {
            URI.create("https://api.frankfurter.app/latest?from=EUR&to=USD"),
            URI.create("https://api.frankfurter.app/latest?base=EUR&symbols=USD")
    };

    private double lastRate = -1.0;
    private long lastFetchMs = 0L;

    public double eurUsd() {
        long now = System.currentTimeMillis();
        if (lastRate > 0 && (now - lastFetchMs) < 60_000) {
            return lastRate;
        }

        RuntimeException lastError = null;

        for (int i = 0; i < ENDPOINTS.length; i++) {
            try {
                double rate = fetch(ENDPOINTS[i]);
                lastRate = rate;
                lastFetchMs = now;
                return rate;
            } catch (RuntimeException ex) {
                lastError = ex;
            }
        }

        if (lastError != null) throw lastError;
        throw new RuntimeException("No se pudo obtener el tipo de cambio EUR/USD");
    }

    private double fetch(URI uri) {
        HttpRequest req = HttpRequest.newBuilder(uri)
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> res = CLIENT.send(req, HttpResponse.BodyHandlers.ofString());

            int code = res.statusCode();
            if (code < 200 || code >= 300) {
                throw new IOException("HTTP " + code);
            }

            String json = res.body();
            return extractUsd(json);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Peticion interrumpida", e);
        } catch (IOException e) {
            throw new RuntimeException("Error de red", e);
        }
    }

    private double extractUsd(String json) {
        int key = json.indexOf("\"USD\"");
        if (key < 0) key = json.indexOf("\"USD\":");
        if (key < 0) throw new RuntimeException("No aparece USD en la respuesta");

        int colon = json.indexOf(':', key);
        if (colon < 0) throw new RuntimeException("Formato JSON invalido");

        int i = colon + 1;
        while (i < json.length() && Character.isWhitespace(json.charAt(i))) i++;

        int j = i;
        while (j < json.length()) {
            char c = json.charAt(j);
            if ((c >= '0' && c <= '9') || c == '.' || c == '-' || c == 'E' || c == 'e' || c == '+') {
                j++;
            } else {
                break;
            }
        }

        if (j == i) throw new RuntimeException("No se pudo leer el numero USD");
        return Double.parseDouble(json.substring(i, j));
    }
}
