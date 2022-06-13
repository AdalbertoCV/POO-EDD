package pruebas.PruebasHerramientas;

import entradasalida.SalidaPorDefecto;
import enumerados.TipoDatos;
import herramientas.Estadistica.CalcularCovarianza;

public class PruebaCalcularCovarianza {
    public static void main (String args[]){
        CalcularCovarianza calculo = new CalcularCovarianza("src\\Examen1\\Programa2\\X.txt","src\\Examen1\\Programa2\\Y.txt", TipoDatos.Muestra);
        SalidaPorDefecto.consola("Calculando resultados para la muestra: \n");
        SalidaPorDefecto.consola("Covarianza: " + calculo.obtenerCovarianza()+"\n");
        SalidaPorDefecto.consola("Valor del coeficiente: " + calculo.getCoeficienteMuestral()+"\n");
        SalidaPorDefecto.consola("Interpretacion: " + calculo.getInterpretacionMuestra()+"\n");

        SalidaPorDefecto.consola("---------------------------------------------\n");
        CalcularCovarianza calculo1 = new CalcularCovarianza("src\\Examen1\\Programa2\\X.txt","src\\Examen1\\Programa2\\Y.txt", TipoDatos.Poblacion);
        SalidaPorDefecto.consola("Calculando resultados para la poblacion: \n");
        SalidaPorDefecto.consola("Covarianza: " + calculo.obtenerCovarianza()+"\n");
        SalidaPorDefecto.consola("Valor del coeficiente: " + calculo1.getCoeficientePoblacional()+"\n");
        SalidaPorDefecto.consola("Interpretacion: " + calculo1.getInterpretacionPoblacion()+"\n\n");
    }
}
