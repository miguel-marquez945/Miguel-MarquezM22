package com.example.view;

import com.example.model.Order;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class OrderView extends JFrame {

    private final JTextField searchField = new JTextField(10);
    private final JButton searchButton = new JButton("Search");
    private final JTextArea resultArea = new JTextArea(10, 40);

    public OrderView() {
        super("Order Management");

        // Icono de la aplicación (app.png debe estar en src/main/resources)
        setIconImage(loadAppIcon());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior: etiqueta + campo de texto + botón
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Order ID:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

        // Área de resultados, solo lectura, dentro de un JScrollPane
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // centrar en pantalla
        setVisible(true);
    }

    /** Devuelve el ID de pedido escrito por el usuario. */
    public String getSearchId() {
        return searchField.getText().trim();
    }

    /** Devuelve el botón de búsqueda para que el controlador le añada el listener. */
    public JButton getSearchButton() {
        return searchButton;
    }

    /**
     * Muestra la información del pedido y el tipo de cambio usado.
     * (Las cantidades concretas pueden venir incluidas en el toString() de Order).
     */
    public void displayOrder(Order order, double rate) {
        if (order == null) {
            resultArea.setText("Order not found.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("ORDER INFORMATION\n");
        sb.append("-----------------\n");
        sb.append(order.toString()).append("\n\n");
        sb.append("EUR/USD rate at query time: ").append(rate).append("\n");

        resultArea.setText(sb.toString());
    }

    /** Carga el icono app.png desde el classpath (src/main/resources). */
    private Image loadAppIcon() {
        URL iconUrl = getClass().getResource("/app.png");
        if (iconUrl == null) {
            System.err.println("Warning: resource /app.png not found");
            return null;
        }
        return new ImageIcon(iconUrl).getImage();
    }
}
