/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.epi;

/**
 *
 * @author vicen
 */
public class ParametrosSimulados {
    private double corriente;
    private double voltaje;
    private double tiempo;
    private double eficiencia;
    private double temperatura;
    private double factorCO2;

    public ParametrosSimulados(double corriente, double voltaje, double tiempo, double eficiencia, double temperatura, double factorCO2) {
        this.corriente = corriente;
        this.voltaje = voltaje;
        this.tiempo = tiempo;
        this.eficiencia = eficiencia;
        this.temperatura = temperatura;
        this.factorCO2 = factorCO2;
    }
    
    public boolean validarParametros() {
        return corriente > 0 && 
            voltaje > 0 && 
            tiempo > 0 && 
            eficiencia > 0 && eficiencia <= 100 &&
               temperatura > -273.15 && 
               factorCO2 >= 0;
    }
    

    public double getCorriente() {
        return corriente;
    }

    public double getVoltaje() {
        return voltaje;
    }


    public double getTiempo() {
        return tiempo;
    }

    public double getEficiencia() {
        return eficiencia;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public double getFactorCO2() {
        return factorCO2;
    }  
}