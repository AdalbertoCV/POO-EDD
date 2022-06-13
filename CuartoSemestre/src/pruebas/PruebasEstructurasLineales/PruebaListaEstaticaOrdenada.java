package pruebas;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaEstaticaOrdenada;
import enumerados.TipoOrden;

public class PruebaListaEstaticaOrdenada {
    public static void main(String args[]){

        //SalidaPorDefecto.consola(CompararDatos.objetoMayor(1,2)+"\n");


        ListaEstaticaOrdenada lista = new ListaEstaticaOrdenada(10, TipoOrden.INCR);
        ListaEstaticaOrdenada lista2 = new ListaEstaticaOrdenada(10, TipoOrden.INCR);
        ListaEstaticaOrdenada lista3 = new ListaEstaticaOrdenada(6, TipoOrden.INCR);
        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");
        lista.agregar("D");
        lista.agregar("G");
        lista.agregar("L");
        lista2.agregar("A");
        lista2.agregar("B");
        lista2.agregar("C");
        lista3.agregar("X");
        lista3.agregar("Y");
        lista3.agregar("Z");

        lista.imprimir();
        SalidaPorDefecto.consola(" Prueba cambiar elemento G: \n");
        lista.cambiar("G","M", 1);
        SalidaPorDefecto.consola("\n");
        lista.imprimir();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba agregar lista2 (Elementos ya existen): \n");
        lista.agregarLista(lista2);
        lista.imprimir();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba invertir lista: \n");
        lista.invertir();
        lista.imprimir();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba es sublista: \n");
        SalidaPorDefecto.consola(lista.esSublista(lista2)+"\n");
        SalidaPorDefecto.consola("Prueba arreglo desordenado: \n");
        lista.arregloDesordenado();
        lista.imprimir();
        SalidaPorDefecto.consola("\n");
        ListaEstaticaOrdenada cambiar1 = new ListaEstaticaOrdenada(7, TipoOrden.INCR);
        ListaEstaticaOrdenada cambiar2 = new ListaEstaticaOrdenada(3, TipoOrden.INCR);
        cambiar1.agregar("A");
        cambiar1.agregar("B");
        cambiar1.agregar("C");
        cambiar2.agregar("X");
        cambiar2.agregar("Y");
        cambiar2.agregar("Z");
        SalidaPorDefecto.consola("Prueba cambiar lista (X,Y,Z): \n");
        lista.cambiarLista(cambiar1, cambiar2);
        lista.imprimir();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba retener lista: \n");
        lista.retenerLista(lista3);
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba rellenar lista valor positivo: \n");
        lista.rellenar(4);
        SalidaPorDefecto.consola("Prueba rellenar lista valor negativo: \n");
        lista.rellenar(-2);
        SalidaPorDefecto.consola("\n");
        lista.imprimir();
        lista.eliminar(0);
        SalidaPorDefecto.consola("Prueba insertar elemento H: \n");
        lista.insertar(1,"H");
        SalidaPorDefecto.consola("\n");
        lista.imprimir();
        lista2.invertir();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba copiar lista: \n");
        lista.copiarLista(lista2);
        lista.imprimir();
    }
}
