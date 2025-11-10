package com.example;

import com.example.controller.OrderController;
import com.example.model.Order;
import com.example.view.OrderView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingUtilities;
import java.io.InputStream;
import java.util.List;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        log.info("Starting Order Management System...");

        // 1) Leer orders.json
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        List<Order> orders;
        try (InputStream is = Main.class.getResourceAsStream("/orders.json")) {
            if (is == null) throw new IllegalStateException("No se encontró /orders.json en src/main/resources");
            orders = mapper.readValue(is, new TypeReference<List<Order>>() {});
        }
        log.info("Pedidos leídos: {}", orders.size());
        for (Order o : orders) log.debug("Loaded order: {}", o.getId());

        // 2) Crear la UI en el hilo de Swing
        final List<Order> finalOrders = orders;
        SwingUtilities.invokeLater(() -> {
            OrderView view = new OrderView();
            new OrderController(view, finalOrders);
            // por si el constructor no la hace visible:
            view.setVisible(true);
        });
    }
}
