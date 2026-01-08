package com.example.View;

import com.example.model.Article;
import com.example.model.Order;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OrderView extends JFrame {

    private DefaultListModel<String> modeloIds;
    private JList<String> listaIds;

    private JTextField campoBuscar;
    private JButton botonBuscar;
    private JButton botonCrear;
    private JButton botonEditar;
    private JButton botonBorrar;

    private JTextArea areaDetalle;

    public OrderView() {
        setTitle("Order Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        modeloIds = new DefaultListModel<String>();
        listaIds = new JList<String>(modeloIds);
        listaIds.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        campoBuscar = new JTextField(10);
        botonBuscar = new JButton("Buscar");
        botonCrear = new JButton("Crear pedido");
        botonEditar = new JButton("Editar pedido");
        botonBorrar = new JButton("Borrar pedido");

        areaDetalle = new JTextArea(16, 55);
        areaDetalle.setEditable(false);

        add(crearPanelIzquierdo(), BorderLayout.WEST);
        add(crearPanelCentral(), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("IDs de pedidos"));
        panel.add(new JScrollPane(listaIds), BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(180, 300));
        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        JPanel arriba = new JPanel(new FlowLayout(FlowLayout.LEFT));
        arriba.add(new JLabel("Buscar por ID:"));
        arriba.add(campoBuscar);
        arriba.add(botonBuscar);

        arriba.add(Box.createHorizontalStrut(15));
        arriba.add(botonCrear);
        arriba.add(botonEditar);
        arriba.add(botonBorrar);

        panel.add(arriba, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaDetalle), BorderLayout.CENTER);

        return panel;
    }

    // ------------------ Metodos usados por el Controller ------------------

    public JButton getSearchButton() {
        return botonBuscar;
    }

    public JButton getCreateButton() {
        return botonCrear;
    }

    public JButton getEditButton() {
        return botonEditar;
    }

    public JButton getDeleteButton() {
        return botonBorrar;
    }

    public String getSearchId() {
        return campoBuscar.getText().trim();
    }

    public void setOrderIds(List<String> ids) {
        modeloIds.clear();
        if (ids == null) return;
        for (int i = 0; i < ids.size(); i++) {
            modeloIds.addElement(ids.get(i));
        }
    }

    public void addOrderSelectionListener(ListSelectionListener listener) {
        listaIds.addListSelectionListener(listener);
    }

    public String getSelectedOrderId() {
        return listaIds.getSelectedValue();
    }

    public void selectOrderId(String id) {
        if (id == null) return;
        listaIds.setSelectedValue(id, true);
    }

    public void setEditDeleteEnabled(boolean enabled) {
        botonEditar.setEnabled(enabled);
        botonBorrar.setEnabled(enabled);
    }

    public void showError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Informacion", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean confirm(String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje, "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    // ------------------ Mostrar pedido ------------------

    public void displayOrder(Order order) {
        if (order == null) {
            areaDetalle.setText("Pedido no encontrado...");
            return;
        }
        areaDetalle.setText(order.toString());
    }

    public void displayOrder(Order order, double tipoCambio) {
        if (order == null) {
            areaDetalle.setText("Pedido no encontrado...");
            return;
        }

        double totalEur = order.getGrossTotal();
        double totalEurConDescuento = order.getDiscountedTotal();
        double totalUsd = totalEur * tipoCambio;
        double totalUsdConDescuento = totalEurConDescuento * tipoCambio;

        StringBuilder sb = new StringBuilder();
        sb.append("Pedido: ").append(order.getId()).append("\n\n");
        sb.append("Articulos:\n");

        List<Article> articulos = order.getArticles();
        if (articulos != null) {
            for (int i = 0; i < articulos.size(); i++) {
                Article a = articulos.get(i);
                sb.append(" - ").append(a.getName());
                sb.append(" | cantidad: ").append(a.getQuantity());
                sb.append(" | precio: ").append(String.format("%.2f", a.getUnitPrice()));
                sb.append(" | descuento: ").append(String.format("%.2f", a.getDiscount())).append("%");
                sb.append("\n");
            }
        }

        sb.append("\nTotales:\n");
        sb.append("Total bruto EUR: ").append(String.format("%.2f €", totalEur)).append("\n");
        sb.append("Total con descuento EUR: ").append(String.format("%.2f €", totalEurConDescuento)).append("\n");
        sb.append("Total bruto USD: ").append(String.format("%.2f $", totalUsd)).append("\n");
        sb.append("Total con descuento USD: ").append(String.format("%.2f $", totalUsdConDescuento)).append("\n");
        sb.append("1 EUR = ").append(String.format("%.4f USD", tipoCambio)).append("\n");

        areaDetalle.setText(sb.toString());
    }

    // ------------------ Crear pedido (formulario simple) ------------------

    public Order showCreateOrderDialog(List<String> idsExistentes) {
        String id = JOptionPane.showInputDialog(this, "Introduce el ID del pedido (unico):", "Crear pedido", JOptionPane.QUESTION_MESSAGE);
        if (id == null) return null;
        id = id.trim();

        if (id.length() == 0) {
            showError("El ID no puede estar vacio.");
            return null;
        }

        if (idsExistentes != null) {
            for (int i = 0; i < idsExistentes.size(); i++) {
                if (idsExistentes.get(i).equalsIgnoreCase(id)) {
                    showError("El ID ya existe. Debe ser unico.");
                    return null;
                }
            }
        }

        String textoNum = JOptionPane.showInputDialog(this, "¿Cuantos articulos quieres añadir?", "Crear pedido", JOptionPane.QUESTION_MESSAGE);
        if (textoNum == null) return null;

        int num;
        try {
            num = Integer.parseInt(textoNum.trim());
        } catch (Exception e) {
            showError("Numero de articulos invalido.");
            return null;
        }

        if (num <= 0) {
            showError("Debes añadir al menos 1 articulo.");
            return null;
        }

        List<Article> articulos = new ArrayList<Article>();

        for (int i = 0; i < num; i++) {
            Article articulo = pedirArticulo(i + 1);
            if (articulo == null) {
                return null; // cancelado o invalido
            }
            articulos.add(articulo);
        }

        return new Order(id, articulos);
    }

    private Article pedirArticulo(int indice) {
        String nombre = JOptionPane.showInputDialog(this, "Articulo " + indice + " - Nombre:", "Crear pedido", JOptionPane.QUESTION_MESSAGE);
        if (nombre == null) return null;
        nombre = nombre.trim();

        if (nombre.length() == 0) {
            showError("El nombre del articulo no puede estar vacio.");
            return null;
        }

        String textoCantidad = JOptionPane.showInputDialog(this, "Articulo " + indice + " - Cantidad:", "Crear pedido", JOptionPane.QUESTION_MESSAGE);
        if (textoCantidad == null) return null;

        int cantidad;
        try {
            cantidad = Integer.parseInt(textoCantidad.trim());
        } catch (Exception e) {
            showError("Cantidad invalida.");
            return null;
        }

        if (cantidad <= 0) {
            showError("La cantidad debe ser mayor que 0.");
            return null;
        }

        String textoPrecio = JOptionPane.showInputDialog(this, "Articulo " + indice + " - Precio unitario:", "Crear pedido", JOptionPane.QUESTION_MESSAGE);
        if (textoPrecio == null) return null;

        double precio;
        try {
            precio = Double.parseDouble(textoPrecio.trim().replace(",", "."));
        } catch (Exception e) {
            showError("Precio invalido.");
            return null;
        }

        if (precio < 0) {
            showError("El precio debe ser mayor o igual que 0.");
            return null;
        }

        String textoDescuento = JOptionPane.showInputDialog(this, "Articulo " + indice + " - Descuento (%):", "Crear pedido", JOptionPane.QUESTION_MESSAGE);
        if (textoDescuento == null) return null;

        double descuento;
        try {
            descuento = Double.parseDouble(textoDescuento.trim().replace(",", "."));
        } catch (Exception e) {
            showError("Descuento invalido.");
            return null;
        }

        if (descuento < 0 || descuento > 100) {
            showError("El descuento debe estar entre 0 y 100.");
            return null;
        }

        return new Article(nombre, cantidad, precio, descuento);
    }

    // ------------------ Editar pedido (solo cantidad y descuento) ------------------

    public boolean showEditOrderDialog(Order pedido) {
        if (pedido == null || pedido.getArticles() == null || pedido.getArticles().size() == 0) {
            showError("El pedido no tiene articulos para editar.");
            return false;
        }

        List<Article> articulos = pedido.getArticles();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(articulos.size() + 1, 3, 8, 6));

        panel.add(new JLabel("Articulo"));
        panel.add(new JLabel("Cantidad"));
        panel.add(new JLabel("Descuento (%)"));

        JTextField[] camposCantidad = new JTextField[articulos.size()];
        JTextField[] camposDescuento = new JTextField[articulos.size()];

        for (int i = 0; i < articulos.size(); i++) {
            Article a = articulos.get(i);

            panel.add(new JLabel(a.getName()));

            camposCantidad[i] = new JTextField(String.valueOf(a.getQuantity()));
            camposDescuento[i] = new JTextField(String.valueOf(a.getDiscount()));

            panel.add(camposCantidad[i]);
            panel.add(camposDescuento[i]);
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Editar pedido " + pedido.getId() + " (solo cantidad y descuento)",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (opcion != JOptionPane.OK_OPTION) {
            return false;
        }

        for (int i = 0; i < articulos.size(); i++) {
            int cantidad;
            double descuento;

            try {
                cantidad = Integer.parseInt(camposCantidad[i].getText().trim());
            } catch (Exception e) {
                showError("Cantidad invalida en el articulo " + (i + 1));
                return false;
            }

            try {
                descuento = Double.parseDouble(camposDescuento[i].getText().trim().replace(",", "."));
            } catch (Exception e) {
                showError("Descuento invalido en el articulo " + (i + 1));
                return false;
            }

            if (cantidad <= 0) {
                showError("La cantidad debe ser mayor que 0 en el articulo " + (i + 1));
                return false;
            }

            if (descuento < 0 || descuento > 100) {
                showError("El descuento debe estar entre 0 y 100 en el articulo " + (i + 1));
                return false;
            }
        }

        // Aplicar cambios (aqui ya se guarda bien porque ya hemos leido los textos)
        for (int i = 0; i < articulos.size(); i++) {
            Article a = articulos.get(i);
            int cantidad = Integer.parseInt(camposCantidad[i].getText().trim());
            double descuento = Double.parseDouble(camposDescuento[i].getText().trim().replace(",", "."));
            a.setQuantity(cantidad);
            a.setDiscount(descuento);
        }

        return true;
    }
}
