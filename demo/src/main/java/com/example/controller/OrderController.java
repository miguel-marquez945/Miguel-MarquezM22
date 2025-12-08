package com.example.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.Order;
import com.example.service.ExchangeRateService;
import com.example.view.OrderView;

public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    // Referencia a la vista
    private final OrderView view;
    // Lista de pedidos cargados desde el JSON
    private final List<Order> orders;
    // Servicio para consultar el tipo de cambio EUR/USD
    private final ExchangeRateService exchangeRateService = new ExchangeRateService();

    public OrderController(OrderView view, List<Order> orders) {
        // Inicializar atributos
        this.view = view;
        this.orders = orders;

        // Añadir listener al botón de búsqueda
        this.view.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchOrder();
            }
        });
    }

    private void searchOrder() {
        String id = view.getSearchId();
        if (id == null || id.isBlank()) {
            log.warn("Search pressed with empty id");
            view.displayOrder(null, 0.0);
            return;
        }

        // Buscar pedido con ese id
        Order order = orders.stream()
                .filter(o -> id.equals(o.getId()))
                .findFirst()
                .orElse(null);

        if (order == null) {
            log.info("Order {} not found", id);
            view.displayOrder(null, 0.0);
            return;
        }

        try {
            double rate = exchangeRateService.getEurUsdRate();
            order.setUsdRate(rate); // por si se quiere reutilizar
            log.info("Using EUR/USD rate {} for order {}", rate, id);
            view.displayOrder(order, rate);
        } catch (IOException ex) {
            log.error("IO error getting EUR/USD rate. Showing totals only in EUR", ex);
            // usamos el usdRate almacenado (por defecto 1.0) como plan B
            view.displayOrder(order, order.getUsdRate());
        } catch (InterruptedException ex) {
            log.error("Interrupted while getting EUR/USD rate", ex);
            Thread.currentThread().interrupt();
            view.displayOrder(order, order.getUsdRate());
        }
    }
}
