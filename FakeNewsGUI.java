package com.fakenews;

import javax.swing.*;
import java.awt.*;

public class FakeNewsGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new FakeNewsGUI().createUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void createUI() throws Exception {

        FakeNewsClassifier classifier = new FakeNewsClassifier();

        JFrame frame = new JFrame("Fake News Detection System");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // ---------- TITLE ----------
        JLabel titleLabel = new JLabel("Fake News Detection System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        // ---------- TEXT AREA ----------
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createTitledBorder("Enter News Text"));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // ---------- RESULT LABEL ----------
        JLabel resultLabel = new JLabel("Prediction will appear here", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---------- BUTTON ----------
        JButton checkButton = new JButton("Check News");
        checkButton.setFont(new Font("Segoe UI", Font.BOLD, 15));

        checkButton.addActionListener(e -> {
            try {
                String newsText = textArea.getText().trim();

                if (newsText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter news text.");
                    return;
                }

                String result = classifier.predict(newsText);
                resultLabel.setText("Prediction: " + result);

                if (result.startsWith("REAL")) {
                    resultLabel.setForeground(new Color(0, 128, 0)); // Green
                } else if (result.startsWith("FAKE")) {
                    resultLabel.setForeground(Color.RED);
                } else {
                    resultLabel.setForeground(new Color(255, 140, 0)); // Orange
                }

            } catch (Exception ex) {
                resultLabel.setText("Error during prediction");
                resultLabel.setForeground(Color.RED);
            }
        });

        // ---------- PANELS ----------
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(resultLabel, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        bottomPanel.add(checkButton);

        // ---------- FRAME LAYOUT ----------
        frame.setLayout(new BorderLayout());
        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}

