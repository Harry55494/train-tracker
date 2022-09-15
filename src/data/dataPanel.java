package data;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;

public class dataPanel extends JPanel {

    TreeMap<String, String> lineStatus = new TreeMap<>();
    HashMap<String, Color> lineColours = new HashMap<>();


    public void getData(){

        try {

            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse("http://cloud.tfl.gov.uk/TrackerNet/LineStatus");

            for (int i = 0; i < doc.getElementsByTagName("LineStatus").getLength(); i++) {

                Node node = doc.getElementsByTagName("LineStatus").item(i);
                String lineName = null;
                String status;

                for (int j = 0; j < node.getChildNodes().getLength(); j++) {
                    if (node.getChildNodes().item(j).getNodeName().equals("Line")) {
                        lineName = node.getChildNodes().item(j).getAttributes().getNamedItem("Name").getNodeValue();
                    }
                }
                if (Objects.equals(node.getAttributes().getNamedItem("StatusDetails").getNodeValue(), "")){
                    status = "No Disruption";
                } else {
                    status = node.getAttributes().getNamedItem("StatusDetails").getNodeValue();
                }

                assert lineName != null;
                if (!lineName.equals("Trams")) {
                    lineStatus.put(lineName, status);
                }

            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

    }


    public JPanel linePanel(String name, String status, Color lineColor){

        JPanel linePanel = new JPanel();
        linePanel.setLayout(new GridLayout(1, 2));
        linePanel.setBackground(lineColor);
        linePanel.setPreferredSize(new Dimension(450, 70));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(lineColor);
        titlePanel.setLayout(new BorderLayout());
        JLabel lineLabel = new JLabel(name, SwingConstants.CENTER);
        lineLabel.setForeground(Color.WHITE);
        lineLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(lineLabel, BorderLayout.CENTER);
        linePanel.add(titlePanel);

        JLabel statusLabel = new JLabel(status);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 15));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        linePanel.add(statusLabel, BorderLayout.SOUTH);


        return linePanel;

    }

    public dataPanel() {

        lineColours.put("Bakerloo", new Color(153, 68, 23));
        lineColours.put("Central", new Color(255, 36, 31));
        lineColours.put("Circle", new Color(255, 206, 0));
        lineColours.put("District", new Color(0, 114, 41));
        lineColours.put("DLR", new Color(0, 175, 173));
        lineColours.put("Hammersmith and City", new Color(244, 169, 190));
        lineColours.put("Jubilee", new Color(161, 165, 167));
        lineColours.put("Metropolitan", new Color(155, 0, 88));
        lineColours.put("Northern", new Color(0, 0, 0));
        lineColours.put("Piccadilly", new Color(0, 25, 168));
        lineColours.put("Victoria", new Color(0, 160, 226));
        lineColours.put("Waterloo and City", new Color(147, 206, 186));
        lineColours.put("Elizabeth", new Color(147, 100, 204));
        lineColours.put("Overground", new Color(239, 123, 16));

        getData();
        setSize(455, 975);
        setLayout(new GridLayout(0, 1));

        for (String key : lineStatus.keySet()) {
            System.out.println(key + ": " + lineStatus.get(key));
            Color lineColor = lineColours.get(key);
            if (lineColor == null) {
                lineColor = Color.black;
            }
            JPanel panel = linePanel(key, lineStatus.get(key), lineColor);
            add(panel);

        }

        JPanel timePanel = new JPanel();
        timePanel.setLayout(new BorderLayout());
        timePanel.setPreferredSize(new Dimension(450, 40));
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        JLabel timeLabel = new JLabel("Last updated: " + formatter.format(System.currentTimeMillis()), SwingConstants.CENTER);
        timeLabel.setForeground(Color.gray);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 15));
        timePanel.add(timeLabel);
        add(timePanel);



    }


}
