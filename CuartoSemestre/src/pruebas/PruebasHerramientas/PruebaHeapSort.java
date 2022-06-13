package pruebas.PruebasHerramientas;

import entradasalida.SalidaPorDefecto;
import enumerados.TipoOrden;
import estructuraslineales.PilaDinamica;
import herramientas.ordenamiento.HeapSort;

public class PruebaHeapSort {
    public static void main (String args[]){
        HeapSort hs = new HeapSort(TipoOrden.DECR);
        hs.ingresarValores();
        hs.imprimir();

        SalidaPorDefecto.consola("\n");

        HeapSort hs1 = new HeapSort(TipoOrden.INCR);
        hs1.ingresarValores();
        hs1.imprimir();

        SalidaPorDefecto.consola("\nEnviando una pila de valores: \n");
        PilaDinamica pila = new PilaDinamica();
        pila.poner(10);
        pila.poner(20);
        pila.poner(11);
        pila.poner(12);
        pila.poner(2);
        pila.poner(9);
        pila.poner(3);
        pila.poner(6);
        pila.poner(19);
        pila.poner(4);
        SalidaPorDefecto.consola("Pila: ");
        pila.imprimir();
        SalidaPorDefecto.consola("\n");
        HeapSort hs2 = new HeapSort(TipoOrden.INCR);
        hs2.ordenarValores(pila);
        hs2.imprimir();
    }
}
