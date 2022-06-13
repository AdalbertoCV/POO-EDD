package pruebas.PruebasEstructurasLineales;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.PilaDinamica;

public class PruebaPilaDinamica {
    public static void main(String args[]){
        SalidaPorDefecto.consola("---Creando la pila dinamica---\n");
        PilaDinamica pila = new PilaDinamica();
        SalidaPorDefecto.consola("Prueba poner elemento en la pila (hola): " + pila.poner("hola") + "\n");
        SalidaPorDefecto.consola("Prueba poner elemento en la pila (3): " + pila.poner(3)+"\n");
        SalidaPorDefecto.consola("Prueba poner elemento en la pila (Proceso): " + pila.poner("proceso") + "\n");
        pila.imprimir();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba quitar elemento: " + pila.quitar()+"\n");
        pila.imprimir();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba ver ultimo elemento: " + pila.verUltimo()+"\n");
    }
}
