package com.example;

import com.example.Controller.OrderController;
import com.example.View.OrderView;
import com.example.model.Api;
import com.example.model.Order;
import com.example.model.OrderRepositorio;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("Iniciando sistema de gestion de pedidos...\n");

        OrderRepositorio repositorio = new OrderRepositorio();
        List<Order> pedidos;

        try {
            pedidos = repositorio.cargarPedidos();
        } catch (IOException e) {
            System.out.println("Error al cargar los pedidos.");
            return;
        }

        System.out.println("Pedidos cargados: " + pedidos.size());
        System.out.println("Fichero JSON usado: " + repositorio.obtenerRutaAbsoluta());

        OrderView vista = new OrderView();
        Api intercambio = new Api();

        new OrderController(vista, pedidos, repositorio, intercambio);

        System.out.println("Aplicacion iniciada correctamente.");
    }
}
