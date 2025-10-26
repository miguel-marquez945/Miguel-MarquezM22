package com.example;

import com.example.model.Order;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        // (1) Fuerza nivel DEBUG por si no se cargó logback.xml
        try {
            ch.qos.logback.classic.Logger root =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
            root.setLevel(ch.qos.logback.classic.Level.DEBUG);
        } catch (Throwable ignore) {
            // Si no está logback, no pasa nada; seguirá al nivel por defecto
        }

        log.info("Starting Order Management System...");

        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

        // Cargar /orders.json desde resources
        try (InputStream is = Main.class.getResourceAsStream("/orders.json")) {
            if (is == null) {
                throw new IllegalStateException("No se encontró /orders.json en src/main/resources");
            }

            // Parsear a List<Order>
            List<Order> orders = mapper.readValue(is, new TypeReference<List<Order>>() {});
            log.info("Pedidos leídos: {}", orders.size());

            // Requisito: un DEBUG por cada pedido
            for (Order o : orders) {
                log.debug("Loaded order: {}", o.getId());
            }
        }
    }
}
