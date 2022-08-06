package gui;

import data.dataPanel;

import javax.swing.*;
import java.awt.*;

public class display extends JFrame {

    int height = 975;
    int width = 470;

    public display() {

        JPanel title = new JPanel();
        title.setLayout(new BorderLayout());
        title.setBackground(new Color(0, 25, 168));
        title.setPreferredSize(new Dimension(width, 70));

        JLabel titleLabel = new JLabel("TFL Disruption");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        title.add(titleLabel, BorderLayout.CENTER);
        add(title, BorderLayout.NORTH);



        dataPanel dataPanel = new dataPanel();

        JScrollPane scrollPanel = new JScrollPane(dataPanel);

        add(scrollPanel, BorderLayout.CENTER);

        setTitle("\uD83D\uDE86 Train Tracker");
        setMinimumSize(new Dimension(width, height));
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

    }

}
