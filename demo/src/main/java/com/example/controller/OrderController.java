package com.example.Controller;

import com.example.View.OrderView;
import com.example.model.Api;
import com.example.model.Order;
import com.example.model.OrdersRepository;

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
    private OrdersRepository repositorio;
    private Api intercambio;

    public OrderController(OrderView view, List<Order> orders, OrdersRepository repository, Api intercambio) {
        this.vista = view;
        this.pedidos = (orders != null) ? orders : new ArrayList<Order>();
        this.repositorio = repository;
        this.intercambio = intercambio;

        actualizarListaIds();
        vista.setEditDeleteEnabled(false);
        vista.displayOrder(null);

        vista.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPedido();
            }
        });

        vista.getCreateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearPedido();
            }
        });

        vista.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarPedido();
            }
        });

        vista.getEditButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarPedido();
            }
        });

        vista.addOrderSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    mostrarPedidoSeleccionado();
                }
            }
        });
    }

    private void actualizarListaIds() {
        List<String> ids = new ArrayList<String>();
        for (int i = 0; i < pedidos.size(); i++) {
            Order p = pedidos.get(i);
            if (p != null && p.getId() != null && p.getId().trim().length() > 0) {
                ids.add(p.getId());
            }
        }
        vista.setOrderIds(ids);
    }

    private boolean idExiste(String id) {
        if (id == null) return false;
        for (int i = 0; i < pedidos.size(); i++) {
            Order p = pedidos.get(i);
            if (p != null && p.getId() != null && p.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private void guardarCambios() {
        try {
            repositorio.guardarPedidos(pedidos);
        } catch (IOException e) {
            vista.showError("Error al guardar en el fichero JSON.");
        }
    }

    private void buscarPedido() {
        String id = vista.getSearchId();

        if (id == null || id.length() == 0) {
            vista.displayOrder(null);
            vista.setEditDeleteEnabled(false);
            return;
        }

        Order encontrado = null;
        for (int i = 0; i < pedidos.size(); i++) {
            Order p = pedidos.get(i);
            if (p.getId() != null && p.getId().equalsIgnoreCase(id)) {
                encontrado = p;
                break;
            }
        }

        if (encontrado == null) {
            vista.displayOrder(null);
            vista.setEditDeleteEnabled(false);
        } else {
            vista.selectOrderId(encontrado.getId());
            double tipoCambio = intercambio.obtenerEurUsd();
            vista.displayOrder(encontrado, tipoCambio);
            vista.setEditDeleteEnabled(true);
        }
    }

    private void mostrarPedidoSeleccionado() {
        String id = vista.getSelectedOrderId();

        if (id == null) {
            vista.displayOrder(null);
            vista.setEditDeleteEnabled(false);
            return;
        }

        Order encontrado = null;
        for (int i = 0; i < pedidos.size(); i++) {
            Order p = pedidos.get(i);
            if (p.getId() != null && p.getId().equalsIgnoreCase(id)) {
                encontrado = p;
                break;
            }
        }

        if (encontrado == null) {
            vista.displayOrder(null);
            vista.setEditDeleteEnabled(false);
        } else {
            double tipoCambio = intercambio.obtenerEurUsd();
            vista.displayOrder(encontrado, tipoCambio);
            vista.setEditDeleteEnabled(true);
        }
    }

    private void crearPedido() {
        List<String> idsExistentes = new ArrayList<String>();
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i) != null && pedidos.get(i).getId() != null) {
                idsExistentes.add(pedidos.get(i).getId());
            }
        }

        Order nuevo = vista.showCreateOrderDialog(idsExistentes);
        if (nuevo == null) return;

        if (idExiste(nuevo.getId())) {
            vista.showError("El ID ya existe. Debe ser unico.");
            return;
        }

        pedidos.add(nuevo);
        actualizarListaIds();
        guardarCambios();

        vista.selectOrderId(nuevo.getId());
        vista.displayOrder(nuevo, intercambio.obtenerEurUsd());
        vista.setEditDeleteEnabled(true);

        vista.showInfo("Pedido creado y guardado correctamente.");
    }

    private void borrarPedido() {
        String id = vista.getSelectedOrderId();

        if (id == null) {
            vista.showInfo("Selecciona un pedido para borrar.");
            return;
        }

        boolean confirmado = vista.confirm("¿Seguro que deseas borrar el pedido " + id + "?\nEsta accion no se puede deshacer.");
        if (!confirmado) return;

        Order aBorrar = null;
        for (int i = 0; i < pedidos.size(); i++) {
            Order p = pedidos.get(i);
            if (p != null && p.getId() != null && p.getId().equalsIgnoreCase(id)) {
                aBorrar = p;
                break;
            }
        }

        if (aBorrar != null) {
            pedidos.remove(aBorrar);
        }

        actualizarListaIds();
        guardarCambios();

        vista.displayOrder(null);
        vista.setEditDeleteEnabled(false);
        vista.showInfo("Pedido borrado y cambios guardados.");
    }

    private void editarPedido() {
        String id = vista.getSelectedOrderId();

        if (id == null) {
            vista.showInfo("Selecciona un pedido para editar.");
            return;
        }

        Order encontrado = null;
        for (int i = 0; i < pedidos.size(); i++) {
            Order p = pedidos.get(i);
            if (p.getId() != null && p.getId().equalsIgnoreCase(id)) {
                encontrado = p;
                break;
            }
        }

        if (encontrado == null) {
            vista.showError("No se ha encontrado el pedido seleccionado.");
            return;
        }

        boolean actualizado = vista.showEditOrderDialog(encontrado);
        if (!actualizado) return;

        guardarCambios();
        vista.displayOrder(encontrado, intercambio.obtenerEurUsd());
        vista.showInfo("Pedido editado y cambios guardados.");
    }
}
