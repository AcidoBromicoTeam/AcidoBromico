/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.epi;

/**
 *
 * @author vicen
 */
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private JSlider sCorriente, sVoltaje, sTiempo, sEficiencia, sTemperatura;
    private JTextField tCorriente, tVoltaje, tTiempo, tEficiencia, tTemperatura;
    private JButton btnSimular;
    private JTextArea resultadosArea;
    private GraficoPanel grafico;

    public Main() { //antes cuando estaba el h2 visual lab tiraba error
        setTitle("H₂ Visual Lab  by AcidoBromico Team");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(220, 240, 230));

        JPanel panelControles = crearPanelControles();
        panelControles.setPreferredSize(new Dimension(400, 0));
        add(panelControles, BorderLayout.WEST);

        JPanel panelResultados = crearPanelResultados();
        panelResultados.setPreferredSize(new Dimension(300, 0));
        add(panelResultados, BorderLayout.EAST);

        grafico = new GraficoPanel();
        add(grafico, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel crearPanelControles() {
        JPanel p = new JPanel();
        p.setBackground(new Color(210, 235, 220));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createTitledBorder("Parametros simulados"));

        sCorriente = crearSlider(0, 10, 5);
        sVoltaje = crearSlider(0, 10, 2);
        sTiempo = crearSlider(0, 3600, 1800);
        sEficiencia = crearSlider(0, 100, 85);
        sTemperatura = crearSlider(0, 100, 25);

        tCorriente = new JTextField("5.0", 5);
        tVoltaje = new JTextField("2.0", 5);
        tTiempo = new JTextField("1800", 5);
        tEficiencia = new JTextField("85.0", 5);
        tTemperatura = new JTextField("25.0", 5);

        p.add(crearFila("Corriente (A):", sCorriente, tCorriente));
        p.add(crearFila("Voltaje (V):", sVoltaje, tVoltaje));
        p.add(crearFila("Tiempo (s):", sTiempo, tTiempo));
        p.add(crearFila("Eficiencia (%):", sEficiencia, tEficiencia));
        p.add(crearFila("Temperatura (°C):", sTemperatura, tTemperatura));

        btnSimular = new JButton("Simular");
        btnSimular.setBackground(new Color(150, 200, 170));
        btnSimular.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnSimular.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnSimular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarSimulacion();
            }
        });

        p.add(Box.createVerticalStrut(15));
        p.add(btnSimular);
        return p;
    }

    private JPanel crearFila(String etiqueta, JSlider slider, JTextField campo) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila.setBackground(new Color(210, 235, 220));
        JLabel label = new JLabel(etiqueta);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        label.setPreferredSize(new Dimension(120, 20));
        campo.setPreferredSize(new Dimension(60, 25));
        fila.add(label);
        fila.add(slider);
        fila.add(campo);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                campo.setText(String.format("%.1f", (double) slider.getValue()));
            }
        });

        return fila;
    }

    private JSlider crearSlider(int min, int max, int valor) {
        JSlider s = new JSlider(min, max, valor);
        s.setMajorTickSpacing((max - min) / 5);
        s.setPaintTicks(true);
        s.setBackground(new Color(210, 235, 220));
        return s;
    }

    private JPanel crearPanelResultados() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.setBackground(new Color(210, 235, 220));
        p.setBorder(BorderFactory.createTitledBorder("Resultados"));

        resultadosArea = new JTextArea();
        resultadosArea.setEditable(false);
        resultadosArea.setFont(new Font("Consolas", Font.PLAIN, 18));
        resultadosArea.setText("Producción de Hidrógeno: --\nConsumo de Energía: --\nCO₂ Evitado: --");

        p.add(resultadosArea, BorderLayout.CENTER);
        return p;
    }

    private void ejecutarSimulacion() { 
        try {

            double corriente = Double.parseDouble(tCorriente.getText().replace(',', '.')); //sin el replace no dejaba cambiar los valores
            double voltaje = Double.parseDouble(tVoltaje.getText().replace(',', '.'));
            double tiempo = Double.parseDouble(tTiempo.getText().replace(',', '.'));
            double eficiencia = Double.parseDouble(tEficiencia.getText().replace(',', '.'));
            double temperatura = Double.parseDouble(tTemperatura.getText().replace(',', '.'));

            ParametrosSimulados p = new ParametrosSimulados(corriente, voltaje, tiempo, eficiencia, temperatura, 0.45);
            
            if (!p.validarParametros()) {
                JOptionPane.showMessageDialog(this, "Parámetros inválidos.");
                return;
            }

            ModeloElectrolisis modelo = new ModeloElectrolisis(p);
            ResultadosSimulados r = modelo.ejecutarSimulacion();

            resultadosArea.setText(String.format(
                    "Moles de H₂: %.4f mol\n" +
                    "Masa de H₂: %.4f kg\n" +
                    "Volumen: %.2f L\n" +
                    "Energía: %.3f kWh\n" +
                    "Eficiencia Real: %.2f %%\n" +
                    "CO₂ evitado: %.3f kg",
                    r.getMolesHidrogeno(),
                    r.getMasaHidrogeno(),
                    r.getVolumenEstandar(),
                    r.getEnergiaConsumida(),
                    r.getEficienciaEnergetica(),
                    r.getCo2Evitado()
            ));

            grafico.actualizarDatos(r);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores válidos numéricos.");
        }
    }

    class GraficoPanel extends JPanel {
        double h2 = 0;
        double co2 = 0;

        public void actualizarDatos(ResultadosSimulados r) {
            h2 = r.getMasaHidrogeno() * 2000000.0; 
            co2 = r.getCo2Evitado() * 50000.0;
            repaint();
        }

@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Font fuenteGrafico = new Font("Segoe UI", Font.BOLD, 18);
            g.setFont(fuenteGrafico);
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.black);
            g.drawString("Comparación (Valores escalados)", 20, 20);
            g.setColor(new Color(60, 140, 60));
            g.fillRect(80, getHeight() - (int) h2 - 50, 100, (int) h2);
            g.setColor(new Color(130, 190, 130)); 
            g.fillRect(250, getHeight() - (int) co2 - 50, 100, (int) co2);
            g.setColor(Color.black);
            g.drawString("H₂ (kg)", 110, getHeight() - 20);
            g.drawString("CO₂ Evitado (kg)", 250, getHeight() - 20);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}