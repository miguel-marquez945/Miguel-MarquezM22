package com.example.View;

import com.example.model.Article;
import com.example.model.Order;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OrderView extends JFrame {

    private DefaultListModel<String> modeloIds;
    private JList<String> listaIds;

    private JTextField campoBuscar;
    private JButton botonBuscar, botonCrear, botonEditar, botonBorrar;

    private JTextArea areaDetalle;

    private JLabel lblPedidoActual, lblEstado;

    
    private final Color BG = new Color(245, 246, 248);
    private final Color CARD = new Color(255, 255, 255);
    private final Color LINE = new Color(220, 220, 220);
    private final Color TEXT_SOFT = new Color(90, 90, 90);

    public OrderView() {
        setTitle("Order Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(980, 580));
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(12, 12));

        // Model + lista
        modeloIds = new DefaultListModel<String>();
        listaIds = new JList<String>(modeloIds);
        listaIds.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaIds.setFixedCellHeight(34);
        listaIds.setBorder(new EmptyBorder(6, 6, 6, 6));
        listaIds.setCellRenderer(crearRendererLista());

        // Controles
        campoBuscar = new JTextField(14);
        campoBuscar.setToolTipText("Escribe un ID (ej: 0001) y pulsa Buscar");

        botonBuscar = new JButton("Buscar");
        botonCrear = new JButton("Nuevo pedido");
        botonEditar = new JButton("Editar");
        botonBorrar = new JButton("Eliminar");

        estilizarBoton(botonBuscar, false);
        estilizarBoton(botonCrear, true);
        estilizarBoton(botonEditar, false);
        estilizarBoton(botonBorrar, false);

        // Detalle
        areaDetalle = new JTextArea();
        areaDetalle.setEditable(false);
        areaDetalle.setLineWrap(true);
        areaDetalle.setWrapStyleWord(true);
        areaDetalle.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        areaDetalle.setBorder(new EmptyBorder(10, 10, 10, 10));

        // UI
        add(crearHeader(), BorderLayout.NORTH);
        add(crearSplit(), BorderLayout.CENTER);
        add(crearStatusBar(), BorderLayout.SOUTH);

        setEditDeleteEnabled(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private DefaultListCellRenderer crearRendererLista() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel c = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                c.setBorder(new EmptyBorder(6, 10, 6, 10));
                c.setFont(c.getFont().deriveFont(Font.BOLD, 12f));
                c.setOpaque(true);

                if (isSelected) {
                    c.setBackground(new Color(30, 40, 70));
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(new Color(40, 40, 40));
                }
                return c;
            }
        };
    }

    private void estilizarBoton(JButton b, boolean principal) {
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(8, 12, 8, 12));
        b.setFont(b.getFont().deriveFont(Font.BOLD, 12f));

        if (principal) {
            b.setBackground(new Color(30, 40, 70));
            b.setForeground(Color.WHITE);
        } else {
            b.setBackground(new Color(235, 236, 240));
            b.setForeground(new Color(35, 35, 35));
        }
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout(12, 12));
        header.setBorder(new EmptyBorder(12, 14, 0, 14));
        header.setOpaque(false);

        JPanel titulos = new JPanel();
        titulos.setOpaque(false);
        titulos.setLayout(new BoxLayout(titulos, BoxLayout.Y_AXIS));

        JLabel t = new JLabel("Orders");
        t.setFont(t.getFont().deriveFont(Font.BOLD, 20f));

        JLabel s = new JLabel("Gestiona pedidos rápido (buscar / crear / editar / eliminar)");
        s.setFont(s.getFont().deriveFont(Font.PLAIN, 12f));
        s.setForeground(TEXT_SOFT);

        titulos.add(t);
        titulos.add(Box.createVerticalStrut(3));
        titulos.add(s);

        JPanel buscar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buscar.setOpaque(false);
        buscar.add(new JLabel("ID:"));
        buscar.add(campoBuscar);
        buscar.add(botonBuscar);

        header.add(titulos, BorderLayout.WEST);
        header.add(buscar, BorderLayout.EAST);

        return header;
    }

    private JComponent crearSplit() {
        JPanel izquierda = tarjeta(crearPanelIzquierdo(), "Pedidos");
        JPanel derecha = tarjeta(crearPanelDerecho(), null);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, izquierda, derecha);
        split.setDividerLocation(300);
        split.setResizeWeight(0.0);
        split.setBorder(new EmptyBorder(0, 14, 8, 14));
        split.setDividerSize(8);
        return split;
    }

    private JPanel crearPanelIzquierdo() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setOpaque(false);

        JScrollPane scroll = new JScrollPane(listaIds);
        scroll.setBorder(BorderFactory.createLineBorder(LINE));
        scroll.getViewport().setBackground(Color.WHITE);

        JPanel acciones = new JPanel();
        acciones.setOpaque(false);
        acciones.setLayout(new GridLayout(3, 1, 8, 8));
        acciones.add(botonCrear);
        acciones.add(botonEditar);
        acciones.add(botonBorrar);

        p.add(scroll, BorderLayout.CENTER);
        p.add(acciones, BorderLayout.SOUTH);

        return p;
    }

    private JPanel crearPanelDerecho() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setOpaque(false);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel titulo = new JLabel("Detalle del pedido");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 13f));

        lblPedidoActual = new JLabel("Ningún pedido seleccionado");
        lblPedidoActual.setForeground(TEXT_SOFT);

        top.add(titulo, BorderLayout.WEST);
        top.add(lblPedidoActual, BorderLayout.EAST);

        JScrollPane scrollDetalle = new JScrollPane(areaDetalle);
        scrollDetalle.setBorder(BorderFactory.createLineBorder(LINE));
        scrollDetalle.getViewport().setBackground(Color.WHITE);

        p.add(top, BorderLayout.NORTH);
        p.add(scrollDetalle, BorderLayout.CENTER);

        return p;
    }

    private JPanel crearStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBorder(new EmptyBorder(6, 16, 10, 16));
        bar.setOpaque(false);

        lblEstado = new JLabel("Listo.");
        lblEstado.setForeground(TEXT_SOFT);

        bar.add(lblEstado, BorderLayout.WEST);
        return bar;
    }

    private JPanel tarjeta(JComponent contenido, String titulo) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LINE),
                new EmptyBorder(12, 12, 12, 12)
        ));

        if (titulo != null) {
            JLabel t = new JLabel(titulo);
            t.setFont(t.getFont().deriveFont(Font.BOLD, 13f));
            card.add(t, BorderLayout.NORTH);
        }

        card.add(contenido, BorderLayout.CENTER);
        return card;
    }

    private void setEstado(String txt) {
        if (lblEstado != null) lblEstado.setText(txt == null ? "" : txt);
    }

    private void setPedidoActual(String txt) {
        if (lblPedidoActual != null) lblPedidoActual.setText(txt == null ? "" : txt);
    }

    

    public JButton getSearchButton() { return botonBuscar; }
    public JButton getCreateButton() { return botonCrear; }
    public JButton getEditButton() { return botonEditar; }
    public JButton getDeleteButton() { return botonBorrar; }

    public String getSearchId() { return campoBuscar.getText().trim(); }

    public void setOrderIds(List<String> ids) {
        modeloIds.clear();
        if (ids == null) return;
        for (int i = 0; i < ids.size(); i++) modeloIds.addElement(ids.get(i));
        setEstado("Cargados " + ids.size() + " pedidos.");
    }

    public void addOrderSelectionListener(ListSelectionListener listener) {
        listaIds.addListSelectionListener(listener);
    }

    public String getSelectedOrderId() { return listaIds.getSelectedValue(); }

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
        setEstado("Error: " + mensaje);
    }

    public void showInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Informacion", JOptionPane.INFORMATION_MESSAGE);
        setEstado(mensaje);
    }

    public boolean confirm(String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje, "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }



    public void displayOrder(Order order) {
        if (order == null) {
            areaDetalle.setText("Pedido no encontrado...");
            setPedidoActual("Pedido: -");
            setEstado("Pedido no encontrado.");
            return;
        }
        areaDetalle.setText(order.toString());
        setPedidoActual("Pedido: " + order.getId());
        setEstado("Mostrando pedido " + order.getId());
    }

    public void displayOrder(Order order, double tipoCambio) {
        if (order == null) {
            areaDetalle.setText("Pedido no encontrado...");
            setPedidoActual("Pedido: -");
            setEstado("Pedido no encontrado.");
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
        setPedidoActual("Pedido: " + order.getId());
        setEstado("Mostrando pedido " + order.getId() + " (EUR/USD)");
    }

    

    public Order showCreateOrderDialog(List<String> idsExistentes) {
        String id = JOptionPane.showInputDialog(this, "Introduce el ID del pedido (unico):",
                "Crear pedido", JOptionPane.QUESTION_MESSAGE);
        if (id == null) return null;
        id = id.trim();

        if (id.length() == 0) { showError("El ID no puede estar vacio."); return null; }

        if (idsExistentes != null) {
            for (int i = 0; i < idsExistentes.size(); i++) {
                if (idsExistentes.get(i).equalsIgnoreCase(id)) {
                    showError("El ID ya existe. Debe ser unico.");
                    return null;
                }
            }
        }

        String textoNum = JOptionPane.showInputDialog(this, "¿Cuantos articulos quieres añadir?",
                "Crear pedido", JOptionPane.QUESTION_MESSAGE);
        if (textoNum == null) return null;

        int num;
        try { num = Integer.parseInt(textoNum.trim()); }
        catch (Exception e) { showError("Numero de articulos invalido."); return null; }

        if (num <= 0) { showError("Debes añadir al menos 1 articulo."); return null; }

        List<Article> articulos = new ArrayList<Article>();
        for (int i = 0; i < num; i++) {
            Article a = pedirArticulo(i + 1);
            if (a == null) return null;
            articulos.add(a);
        }

        return new Order(id, articulos);
    }

    private Article pedirArticulo(int indice) {
        String nombre = JOptionPane.showInputDialog(this, "Articulo " + indice + " - Nombre:",
                "Crear pedido", JOptionPane.QUESTION_MESSAGE);
        if (nombre == null) return null;
        nombre = nombre.trim();
        if (nombre.length() == 0) { showError("El nombre del articulo no puede estar vacio."); return null; }

        String textoCantidad = JOptionPane.showInputDialog(this, "Articulo " + indice + " - Cantidad:",
                "Crear pedido", JOptionPane.QUESTION_MESSAGE);
        if (textoCantidad == null) return null;

        int cantidad;
        try { cantidad = Integer.parseInt(textoCantidad.trim()); }
        catch (Exception e) { showError("Cantidad invalida."); return null; }
        if (cantidad <= 0) { showError("La cantidad debe ser mayor que 0."); return null; }

        String textoPrecio = JOptionPane.showInputDialog(this, "Articulo " + indice + " - Precio unitario:",
                "Crear pedido", JOptionPane.QUESTION_MESSAGE);
        if (textoPrecio == null) return null;

        double precio;
        try { precio = Double.parseDouble(textoPrecio.trim().replace(",", ".")); }
        catch (Exception e) { showError("Precio invalido."); return null; }
        if (precio < 0) { showError("El precio debe ser mayor o igual que 0."); return null; }

        String textoDescuento = JOptionPane.showInputDialog(this, "Articulo " + indice + " - Descuento (%):",
                "Crear pedido", JOptionPane.QUESTION_MESSAGE);
        if (textoDescuento == null) return null;

        double descuento;
        try { descuento = Double.parseDouble(textoDescuento.trim().replace(",", ".")); }
        catch (Exception e) { showError("Descuento invalido."); return null; }
        if (descuento < 0 || descuento > 100) { showError("El descuento debe estar entre 0 y 100."); return null; }

        return new Article(nombre, cantidad, precio, descuento);
    }

    

    public boolean showEditOrderDialog(Order pedido) {
        if (pedido == null || pedido.getArticles() == null || pedido.getArticles().size() == 0) {
            showError("El pedido no tiene articulos para editar.");
            return false;
        }

        List<Article> articulos = pedido.getArticles();

        JPanel panel = new JPanel(new GridLayout(articulos.size() + 1, 3, 8, 6));
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

        int opcion = JOptionPane.showConfirmDialog(this, panel,
                "Editar pedido " + pedido.getId() + " (cantidad y descuento)",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion != JOptionPane.OK_OPTION) return false;

        for (int i = 0; i < articulos.size(); i++) {
            int cantidad; double descuento;

            try { cantidad = Integer.parseInt(camposCantidad[i].getText().trim()); }
            catch (Exception e) { showError("Cantidad invalida en el articulo " + (i + 1)); return false; }

            try { descuento = Double.parseDouble(camposDescuento[i].getText().trim().replace(",", ".")); }
            catch (Exception e) { showError("Descuento invalido en el articulo " + (i + 1)); return false; }

            if (cantidad <= 0) { showError("Cantidad > 0 en el articulo " + (i + 1)); return false; }
            if (descuento < 0 || descuento > 100) { showError("Descuento 0-100 en el articulo " + (i + 1)); return false; }
        }

        for (int i = 0; i < articulos.size(); i++) {
            Article a = articulos.get(i);
            a.setQuantity(Integer.parseInt(camposCantidad[i].getText().trim()));
            a.setDiscount(Double.parseDouble(camposDescuento[i].getText().trim().replace(",", ".")));
        }

        setEstado("Pedido " + pedido.getId() + " actualizado.");
        return true;
    }
}
