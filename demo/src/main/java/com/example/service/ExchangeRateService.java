package com.example.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Servicio sencillo para obtener el tipo de cambio EUR/USD
 * llamando a un servicio externo (Frankfurter API).
 *
 * Requiere Java 11+ (HttpClient).
 */
public class ExchangeRateService {

    private static final String EUR_USD_URL =
            "https://api.frankfurter.app/latest?from=EUR&to=USD";

    private final HttpClient client = HttpClient.newHttpClient();

    /**
     * Devuelve el tipo de cambio EUR→USD actual.
     *
     * @return tipo de cambio (1 EUR = X USD)
     */
    public double getEurUsdRate() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(EUR_USD_URL))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Error HTTP " + response.statusCode());
        }

        String body = response.body();
        // Respuesta tipo: {"amount":1.0,"base":"EUR","date":"2024-01-01","rates":{"USD":1.08}}
        int usdIndex = body.indexOf("\"USD\"");
        if (usdIndex == -1) {
            throw new IOException("No se encontró la clave USD en la respuesta: " + body);
        }
        int colonIndex = body.indexOf(':', usdIndex);
        int endIndex = body.indexOf('}', colonIndex);
        String rateStr = body.substring(colonIndex + 1, endIndex).trim();

        return Double.parseDouble(rateStr);
    }
}
