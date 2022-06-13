package pruebas.PruebasHerramientas;

import entradasalida.SalidaPorDefecto;
import herramientas.Estadistica.MOMEmociones;

public class PruebaMOM {
    public static void main (String args[]){
        MOMEmociones prueba = new MOMEmociones();
        SalidaPorDefecto.consola(prueba.CalcularProbabilidadInicial("Alegria")+"\n");
        prueba.CalcularProbabilidadActividad("Tristeza","Llorar");
        SalidaPorDefecto.consola("Probabilidad de que llore si esta triste: " + prueba.CalcularProbabilidadActividad("Tristeza","Llorar")+"\n");
        SalidaPorDefecto.consola("Probabilidad de que cambie a estresado si esta alegre: " + prueba.CalcularProbabilidadEstado("Estres","Alegria")+"\n");
        SalidaPorDefecto.consola("Probabilidad de que cambie a alegre si esta triste: " + prueba.CalcularProbabilidadEstado("Alegria","Tristeza")+"\n");
        String secuencia = "Tristeza,Alegria,Tristeza,Tranquilidad";
        SalidaPorDefecto.consola("Probabilidad secuencia: "+ prueba.CalcularProbabilidadSecuencia(secuencia)+"\n");
        String secuencia2 = "Tristeza,Tristeza,Estres,Tristeza";
        SalidaPorDefecto.consola("Probabilidad secuencia: "+ prueba.CalcularProbabilidadSecuencia(secuencia2)+"\n");
    }
}
