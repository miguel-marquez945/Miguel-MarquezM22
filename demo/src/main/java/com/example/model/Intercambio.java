package com.example.model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Intercambio {

    private static final String URL = "https://api.frankfurter.dev/v1/latest?base=EUR&symbols=USD";

    public double obtenerEurUsd() {
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest peticion = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .GET()
                .build();

        try {
            HttpResponse<String> respuesta = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());
            String cuerpo = respuesta.body();

            int pos = cuerpo.indexOf("\"USD\":");
            if (pos == -1) {
                throw new RuntimeException("No se ha encontrado el cambio EUR/USD");
            }

            int inicio = pos + 6;
            int fin = inicio;

            while (fin < cuerpo.length() && "0123456789.-".indexOf(cuerpo.charAt(fin)) >= 0) {
                fin++;
            }

            String numero = cuerpo.substring(inicio, fin);
            return Double.parseDouble(numero);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al obtener el tipo de cambio EUR/USD", e);
        }
    }
}
  

