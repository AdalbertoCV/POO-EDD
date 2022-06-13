package pruebas.PruebasHerramientas;

import entradasalida.SalidaPorDefecto;
import herramientas.Estadistica.CalcularRPearson;

public class PruebaCalcularRPearson {
    public static void main (String args[]){
        CalcularRPearson calculo = new CalcularRPearson("src\\Examen1\\Programa2\\X.txt","src\\Examen1\\Programa2\\Y.txt");
        SalidaPorDefecto.consola("El valor del coeficiente es: " + calculo.obtenerCoeficienteR()+"\n");
        SalidaPorDefecto.consola("La interpretacion del coeficiente es: "+ calculo.obtenerInterpretacion()+"\n");
    }
}
