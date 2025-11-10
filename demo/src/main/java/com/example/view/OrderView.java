package com.example.view;

import javax.swing.*;
import com.example.model.Order;
import java.awt.*;

public class OrderView extends JFrame {
    private JTextField searchField = new JTextField(10);
    private JButton searchButton = new JButton("Search");
    private JTextArea resultArea = new JTextArea(10, 40);

    public OrderView() {
        setTitle("Order Management");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        //add new JLabel("Order ID:");
        //add to view the search field
        // add to view the search button
        // add to view the result area inside a JScrollPane

        pack();
        setVisible(true);
    }

    public String getSearchId() {
        //...
        return "";
    }

    public JButton getSearchButton() {
        //...
        return null;
    }

    public void displayOrder(Order order, double rate) {
        //Display order details in resultArea
    }
}
