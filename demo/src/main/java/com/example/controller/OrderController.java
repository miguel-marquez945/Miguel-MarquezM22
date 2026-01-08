package com.example.Controller;

import com.example.View.OrderView;
import com.example.model.Api;
import com.example.model.Order;
import com.example.model.OrderRepositorio;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderController {

    private OrderView vista;
    private List<Order> pedidos;
    private OrderRepositorio repositorio;
    private Api intercambio;

    public OrderController(OrderView view, List<Order> orders, OrderRepositorio repository, Api intercambio) {
        this.vista = view;
        this.pedidos = (orders != null) ? orders : new ArrayList<Order>();
        this.repositorio = repository;
        this.intercambio = intercambio;

        iniciarVista();
        enlazarEventos();
    }

    private void iniciarVista() {
        refrescarIds();
        vista.setEditDeleteEnabled(false);
        vista.displayOrder(null);
    }

    private void enlazarEventos() {
        vista.getSearchButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { accionBuscar(); }
        });

        vista.getCreateButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { accionCrear(); }
        });

        vista.getDeleteButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { accionBorrar(); }
        });

        vista.getEditButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { accionEditar(); }
        });

        vista.addOrderSelectionListener(new ListSelectionListener() {
            @Override public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) accionSeleccion();
            }
        });
    }

    // ------------------ Helpers ------------------

    private void refrescarIds() {
        List<String> ids = obtenerIdsValidos();
        vista.setOrderIds(ids);
    }

    private List<String> obtenerIdsValidos() {
        List<String> ids = new ArrayList<String>();
        for (int i = 0; i < pedidos.size(); i++) {
            Order p = pedidos.get(i);
            String id = (p == null) ? "" : limpiar(p.getId());
            if (id.length() > 0) ids.add(id);
        }
        return ids;
    }

    private String limpiar(String s) {
        return (s == null) ? "" : s.trim();
    }

    private Order buscarPorId(String id) {
        String buscado = limpiar(id);
        if (buscado.length() == 0) return null;

        for (int i = 0; i < pedidos.size(); i++) {
            Order p = pedidos.get(i);
            if (p != null && p.getId() != null && p.getId().equalsIgnoreCase(buscado)) {
                return p;
            }
        }
        return null;
    }

    private boolean existeId(String id) {
        return buscarPorId(id) != null;
    }

    private void guardar() {
        try {
            repositorio.guardarPedidos(pedidos);
        } catch (IOException e) {
            vista.showError("Error al guardar en el fichero JSON.");
        }
    }

    private void mostrarPedido(Order pedido, boolean activarBotones) {
        if (pedido == null) {
            vista.displayOrder(null);
            vista.setEditDeleteEnabled(false);
            return;
        }
        double tipoCambio = intercambio.obtenerEurUsd();
        vista.displayOrder(pedido, tipoCambio);
        vista.setEditDeleteEnabled(activarBotones);
    }

    // ------------------ Acciones ------------------

    private void accionBuscar() {
        String id = vista.getSearchId();
        if (limpiar(id).length() == 0) {
            mostrarPedido(null, false);
            return;
        }

        Order encontrado = buscarPorId(id);
        if (encontrado == null) {
            mostrarPedido(null, false);
            return;
        }

        vista.selectOrderId(encontrado.getId());
        mostrarPedido(encontrado, true);
    }

    private void accionSeleccion() {
        String id = vista.getSelectedOrderId();
        Order encontrado = buscarPorId(id);
        mostrarPedido(encontrado, encontrado != null);
    }

    private void accionCrear() {
        List<String> idsExistentes = obtenerIdsValidos();

        Order nuevo = vista.showCreateOrderDialog(idsExistentes);
        if (nuevo == null) return;

        if (existeId(nuevo.getId())) {
            vista.showError("El ID ya existe. Debe ser unico.");
            return;
        }

        pedidos.add(nuevo);
        refrescarIds();
        guardar();

        vista.selectOrderId(nuevo.getId());
        mostrarPedido(nuevo, true);

        vista.showInfo("Pedido creado y guardado correctamente.");
    }

    private void accionBorrar() {
        String id = vista.getSelectedOrderId();

        if (limpiar(id).length() == 0) {
            vista.showInfo("Selecciona un pedido para borrar.");
            return;
        }

        boolean ok = vista.confirm("¿Seguro que deseas borrar el pedido " + id + "?\nEsta accion no se puede deshacer.");
        if (!ok) return;

        Order aBorrar = buscarPorId(id);
        if (aBorrar != null) pedidos.remove(aBorrar);

        refrescarIds();
        guardar();

        mostrarPedido(null, false);
        vista.showInfo("Pedido borrado y cambios guardados.");
    }

    private void accionEditar() {
        String id = vista.getSelectedOrderId();

        if (limpiar(id).length() == 0) {
            vista.showInfo("Selecciona un pedido para editar.");
            return;
        }

        Order encontrado = buscarPorId(id);
        if (encontrado == null) {
            vista.showError("No se ha encontrado el pedido seleccionado.");
            return;
        }

        boolean actualizado = vista.showEditOrderDialog(encontrado);
        if (!actualizado) return;

        guardar();
        mostrarPedido(encontrado, true);
        vista.showInfo("Pedido editado y cambios guardados.");
    }
}
