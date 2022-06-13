package Examen1.Pruebas;

import Examen1.Programa2.RegresionLineal;
import entradasalida.EntradaPorDefecto;
import entradasalida.SalidaPorDefecto;


public class PruebaRegresionLineal {
    public static void main(String args[]){
        RegresionLineal regresion = new RegresionLineal("src\\Examen1\\Programa2\\X.txt","src\\Examen1\\Programa2\\Y.txt");
        int opcion=0;
        while (opcion!=3){
            SalidaPorDefecto.consola("Seleccione una opcion: \n");
            SalidaPorDefecto.consola("(1) calcular un nuevo costo.\n");
            SalidaPorDefecto.consola("(2) Ver ecuacion. \n");
            SalidaPorDefecto.consola("(3) Salir\n");
            opcion = EntradaPorDefecto.consolaInt();
            if (opcion ==1) {
                SalidaPorDefecto.consola("Ingrese el valor de habitaciones a calcular: \n");
                double valornuevo = EntradaPorDefecto.consolaDouble();
                SalidaPorDefecto.consola("Costo estimado = " + regresion.calcularCosto(valornuevo) + " mil \n");
            }
            if (opcion==2){
                SalidaPorDefecto.consola("Ecuacion "+ regresion.obtenerEcuacion()+"\n");
            }

            if (opcion==3){
                System.exit(0);
            }
        }

    }
}
