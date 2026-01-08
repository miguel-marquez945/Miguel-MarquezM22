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
    private JButton botonBuscar;
    private JButton botonCrear;
    private JButton botonEditar;
    private JButton botonBorrar;

    private JTextArea areaDetalle;

    // extras visuales (no afectan al controller)
    private JLabel lblPedidoActual;
    private JLabel lblEstado;

    public OrderView() {
        setTitle("Order Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(950, 560));
        setLayout(new BorderLayout());

        modeloIds = new DefaultListModel<String>();
        listaIds = new JList<String>(modeloIds);
        listaIds.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaIds.setFixedCellHeight(28);
        listaIds.setBorder(new EmptyBorder(6, 6, 6, 6));

        campoBuscar = new JTextField(14);
        botonBuscar = new JButton("Buscar");

        botonCrear = new JButton("Crear pedido");
        botonEditar = new JButton("Editar");
        botonBorrar = new JButton("Borrar");

        areaDetalle = new JTextArea();
        areaDetalle.setEditable(false);
        areaDetalle.setLineWrap(true);
        areaDetalle.setWrapStyleWord(true);
        areaDetalle.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        add(crearHeader(), BorderLayout.NORTH);
        add(crearCuerpo(), BorderLayout.CENTER);
        add(crearBarraEstado(), BorderLayout.SOUTH);

        setEditDeleteEnabled(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ===================== UI (cambia solo como se ve) =====================

    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout(10, 10));
        header.setBorder(new EmptyBorder(12, 14, 12, 14));
        header.setBackground(new Color(245, 246, 248));

        JPanel titulos = new JPanel();
        titulos.setLayout(new BoxLayout(titulos, BoxLayout.Y_AXIS));
        titulos.setOpaque(false);

        JLabel titulo = new JLabel("Orders Dashboard");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));

        JLabel subtitulo = new JLabel("Buscar, crear y gestionar pedidos");
        subtitulo.setFont(subtitulo.getFont().deriveFont(Font.PLAIN, 12f));
        subtitulo.setForeground(new Color(90, 90, 90));

        titulos.add(titulo);
        titulos.add(Box.createVerticalStrut(2));
        titulos.add(subtitulo);

        JPanel buscador = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buscador.setOpaque(false);
        buscador.add(new JLabel("ID:"));
        buscador.add(campoBuscar);
        buscador.add(botonBuscar);

        header.add(titulos, BorderLayout.WEST);
        header.add(buscador, BorderLayout.EAST);

        return header;
    }

    private JComponent crearCuerpo() {
        JPanel izquierda = crearSidebar();
        JPanel derecha = crearDetalle();

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, izquierda, derecha);
        split.setDividerLocation(280);
        split.setResizeWeight(0.0);
        split.setBorder(null);

        return split;
    }

    private JPanel crearSidebar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(14, 14, 14, 10));

        JLabel lbl = new JLabel("Pedidos");
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 13f));

        JScrollPane scroll = new JScrollPane(listaIds);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JPanel arriba = new JPanel(new BorderLayout());
        arriba.add(lbl, BorderLayout.WEST);

        // Botones en vertical (distinto a tu vista original)
        JPanel acciones = new JPanel();
        acciones.setLayout(new BoxLayout(acciones, BoxLayout.Y_AXIS));
        acciones.setBorder(new EmptyBorder(8, 0, 0, 0));

        botonCrear.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonEditar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonBorrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        acciones.add(botonCrear);
        acciones.add(Box.createVerticalStrut(8));
        acciones.add(botonEditar);
        acciones.add(Box.createVerticalStrut(8));
        acciones.add(botonBorrar);

        panel.add(arriba, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(acciones, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearDetalle() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(14, 10, 14, 14));

        // Cabecera de “tarjeta”
        JPanel cabecera = new JPanel(new BorderLayout(10, 10));
        cabecera.setBorder(new EmptyBorder(10, 12, 10, 12));
        cabecera.setBackground(new Color(250, 250, 250));
        cabecera.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(10, 12, 10, 12)
        ));

        JLabel lblTitulo = new JLabel("Detalle del pedido");
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 13f));

        lblPedidoActual = new JLabel("Ningún pedido seleccionado");
        lblPedidoActual.setForeground(new Color(90, 90, 90));

        cabecera.add(lblTitulo, BorderLayout.WEST);
        cabecera.add(lblPedidoActual, BorderLayout.EAST);

        JScrollPane scrollDetalle = new JScrollPane(areaDetalle);
        scrollDetalle.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        panel.add(cabecera, BorderLayout.NORTH);
        panel.add(scrollDetalle, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearBarraEstado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(6, 12, 6, 12));
        panel.setBackground(new Color(245, 246, 248));

        lblEstado = new JLabel("Listo.");
        lblEstado.setForeground(new Color(80, 80, 80));

        panel.add(lblEstado, BorderLayout.WEST);
        return panel;
    }

    private void setEstado(String txt) {
        if (lblEstado != null) lblEstado.setText(txt == null ? "" : txt);
    }

    private void setPedidoActual(String txt) {
        if (lblPedidoActual != null) lblPedidoActual.setText(txt == null ? "" : txt);
    }

    // ===================== Metodos usados por el Controller =====================

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
        setEstado("Cargados " + ids.size() + " pedidos.");
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

    // ===================== Mostrar pedido =====================

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

    // ===================== Crear pedido (formulario simple) =====================

    public Order showCreateOrderDialog(List<String> idsExistentes) {
        String id = JOptionPane.showInputDialog(this, "Introduce el ID del pedido (unico):",
                "Crear pedido", JOptionPane.QUESTION_MESSAGE);
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

        String textoNum = JOptionPane.showInputDialog(this, "¿Cuantos articulos quieres añadir?",
                "Crear pedido", JOptionPane.QUESTION_MESSAGE);
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
                return null;
            }
            articulos.add(articulo);
        }

        return new Order(id, articulos);
    }

    private Article pedirArticulo(int indice) {
        String nombre = JOptionPane.showInputDialog(this, "Articulo " + indice + " - Nombre:",
                "Crear pedido", JOptionPane.QUESTION_MESSAGE);
        if (nombre == null) return null;
        nombre = nombre.trim();

        if (nombre.length() == 0) {
            showError("El nombre del articulo no puede estar vacio.");
            return null;
        }

        String textoCantidad = JOptionPane.showInputDialog(this, "Articulo " + indice + " - Cantidad:",
                "Crear pedido", JOptionPane.QUESTION_MESSAGE);
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

        String textoPrecio = JOptionPane.showInputDialog(this, "Articulo " + indice + " - Precio unitario:",
                "Crear pedido", JOptionPane.QUESTION_MESSAGE);
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

        String textoDescuento = JOptionPane.showInputDialog(this, "Articulo " + indice + " - Descuento (%):",
                "Crear pedido", JOptionPane.QUESTION_MESSAGE);
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

    // ===================== Editar pedido (solo cantidad y descuento) =====================

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
