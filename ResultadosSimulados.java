/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.epi;

/**
 *
 * @author vicen
 */
import java.util.ArrayList;
import java.util.List;
public class ResultadosSimulados {
    private double molesHidrogeno;
    private double masaHidrogeno;
    private double volumenEstandar;
    private double energiaConsumida;
    private double eficienciaEnergetica;
    private double co2Evitado;
    public ResultadosSimulados(double molesHidrogeno, double masaHidrogeno, double volumenEstandar, double energiaConsumida, double eficienciaEnergetica, double co2Evitado) {
        this.molesHidrogeno = molesHidrogeno;
        this.masaHidrogeno = masaHidrogeno;
        this.volumenEstandar = volumenEstandar;
        this.energiaConsumida = energiaConsumida;
        this.eficienciaEnergetica = eficienciaEnergetica;
        this.co2Evitado = co2Evitado;
    }
    public double getMolesHidrogeno() {
        return molesHidrogeno;
    }
    public double getMasaHidrogeno() {
        return masaHidrogeno;
    }
    public double getVolumenEstandar() {
        return volumenEstandar;
    }
    public double getEnergiaConsumida() {
        return energiaConsumida;
    }
    public double getEficienciaEnergetica() {
        return eficienciaEnergetica;
    }
    public double getCo2Evitado() {
        return co2Evitado;
    }
    public void mostrarResultados(){
        System.out.println("_____Resultados Finales by AcidoBromico Team_____");
        System.out.printf("Moles de H₂ producidos: %.4f mol%n", molesHidrogeno);
        System.out.printf("Masa de H₂: %.4f kg%n", masaHidrogeno);
        System.out.printf("Volumen estándar: %.2f L%n", volumenEstandar);
        System.out.printf("Energía consumida: %.4f kWh%n", energiaConsumida);
        System.out.printf("Eficiencia energética: %.2f %% %n", eficienciaEnergetica);
        System.out.printf("CO₂ evitado: %.4f kg%n", co2Evitado);
    }
    public List<String> generartablaresultados(){
        List<String> tabla = new ArrayList<>();
        tabla.add("Moles H2: " + molesHidrogeno);
        tabla.add("Masa H2 (kg): " + masaHidrogeno);
        tabla.add("Volumen (L): " + volumenEstandar);
        tabla.add("Energía (kWH): " + energiaConsumida);
        tabla.add("Eficiencia (%): " + eficienciaEnergetica);
        tabla.add("CO2 evitado (kg): "+ co2Evitado);
        return tabla;
    }
    public void generarGrafico(){
        System.out.println("Generando grafico");
    }
}