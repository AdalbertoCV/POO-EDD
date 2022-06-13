package pruebas.PruebasHerramientas;

import entradasalida.SalidaPorDefecto;
import herramientas.Estadistica.RedBayesiana;

public class PruebaBayes {
    public static void main(String args[]) throws Exception {
        RedBayesiana prueba=new RedBayesiana(7);
        prueba.agregarVertice("Covid",null,null);
        prueba.agregarVertice("Fiebre","Covid",null);
        prueba.agregarVertice("Dolor","Fiebre",null);
        prueba.agregarVertice("Influenza",null,null);
        prueba.agregarVertice("Gripe","Covid","Influenza");
        prueba.agregarVertice("Estornudos","Gripe",null);
        prueba.agregarVertice("Afonismo","Gripe",null);
        prueba.imprimir();

        prueba.obtenerProbabilidad("Covid");
        prueba.obtenerProbabilidad("Salmonela");
        prueba.obtenerProbabilidad("Gripe");
        SalidaPorDefecto.consola("\n Calculo probabilidades\n");

        prueba.calcularProbabilidad("Covid","Gripe");
        prueba.calcularProbabilidad("Dolor","Gripe");
        prueba.calcularProbabilidad("Influenza","Estornudos");
        prueba.calcularProbabilidad("Covid","Estornudos");
    }
}
