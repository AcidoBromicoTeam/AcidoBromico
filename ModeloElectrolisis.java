/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.epi;

/**
 *
 * @author vicen
 */
public class ModeloElectrolisis {
    private ParametrosSimulados parametros;
    private double constanteFaraday = 96485.0;
    
    private double LHV_HIDROGENO_KWH_PER_KG = 33.3; 
    
    private double energiaConsumida;
    private double molesHidrogeno;
    private double co2Evitado;
    private double masaHidrogeno; 

    public ModeloElectrolisis(ParametrosSimulados parametros) {
        this.parametros = parametros;
    }
    
    public ResultadosSimulados ejecutarSimulacion(){
        calcularProduccionHidrogeno();
        
        calcularEnergiaConsumida();
        
        calcularCO2Evitado();
        
        double eficienciaEnergeticaReal = calcularEficienciaEnergetica();
        
        return new ResultadosSimulados(
            molesHidrogeno,
            masaHidrogeno,
            molesHidrogeno * 22.4, 
            energiaConsumida,
            eficienciaEnergeticaReal, 
            co2Evitado
        );
    }
    
    public double calcularProduccionHidrogeno() {
        
        double I = parametros.getCorriente();
        double t = parametros.getTiempo();
        double eficienciaFaraday = parametros.getEficiencia() / 100.0;
        
        molesHidrogeno = (I * t * eficienciaFaraday) / (2 * constanteFaraday);
        masaHidrogeno = molesHidrogeno * 0.002016;
        return molesHidrogeno;
    }

    public double calcularEnergiaConsumida() {
        double I = parametros.getCorriente();
        double V = parametros.getVoltaje();
        double t = parametros.getTiempo();
        energiaConsumida = (I * V * t) / 3600000; 
        return energiaConsumida;
    }

    public double calcularCO2Evitado() {
        double factor = parametros.getFactorCO2();
        co2Evitado = energiaConsumida * factor;
        return co2Evitado;
    }
    
   
    public double calcularEficienciaEnergetica() {
        if (energiaConsumida == 0) {
            return 0; 
        }
               
        double energiaH2Producida = masaHidrogeno * LHV_HIDROGENO_KWH_PER_KG;
        
        double eficienciaReal = (energiaH2Producida / energiaConsumida) * 100.0;
        
        return eficienciaReal;
    }
}