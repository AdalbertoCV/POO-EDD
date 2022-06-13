package Examen2.Pruebas;

import Examen2.Estructura.ListaDinamicaParalela;
import entradasalida.SalidaPorDefecto;

public class PruebaListaParalela {
    public static void main (String args[]){
        ListaDinamicaParalela lista = new ListaDinamicaParalela();
        SalidaPorDefecto.consola("Prueba agregando elemento 1 al inicio: "+ lista.agregarInicio(1) +"\n");
        lista.Imprimir();
        SalidaPorDefecto.consola("Prueba agregando elemento 2 al inicio: "+ lista.agregarInicio(2) +"\n");
        lista.Imprimir();
        SalidaPorDefecto.consola("Prueba agregando elemento 3 al inicio: "+ lista.agregarInicio(3) +"\n");
        lista.Imprimir();
        SalidaPorDefecto.consola("Prueba agregando elemento 4 al inicio: "+ lista.agregarInicio(4) +"\n");
        lista.Imprimir();
        SalidaPorDefecto.consola("Prueba agregando elemento 5 al inicio: "+ lista.agregarInicio(5) +"\n");
        lista.Imprimir();
        SalidaPorDefecto.consola("Prueba agregando elemento H al final: " + lista.agregarFinal("H")+"\n");
        lista.Imprimir();
        SalidaPorDefecto.consola("Prueba agregando elemento W al final: " + lista.agregarFinal("W")+"\n");
        lista.Imprimir();
        SalidaPorDefecto.consola("Prueba agregando elemento 6 al final: " + lista.agregarFinal(6)+"\n");
        lista.Imprimir();
        SalidaPorDefecto.consola("Prueba agregando elemento 7 al final: " + lista.agregarFinal(7)+"\n");
        lista.Imprimir();

        SalidaPorDefecto.consola("Prueba eliminar inicio: " + lista.eliminarInicio()+"\n");
        lista.Imprimir();
        SalidaPorDefecto.consola("Prueba eliminar inicio: " + lista.eliminarInicio()+"\n");
        lista.Imprimir();
        SalidaPorDefecto.consola("Prueba eliminar inicio: " + lista.eliminarInicio()+"\n");
        lista.Imprimir();

        SalidaPorDefecto.consola("Prueba eliminar final: " + lista.eliminarFinal()+"\n");
        lista.Imprimir();
        SalidaPorDefecto.consola("Prueba eliminar final: " + lista.eliminarFinal()+"\n");
        lista.Imprimir();
        SalidaPorDefecto.consola("Prueba eliminar final: " + lista.eliminarFinal()+"\n");
        lista.Imprimir();

        SalidaPorDefecto.consola("Prueba buscar 2: " + lista.buscar(2)+"\n");
        SalidaPorDefecto.consola("Prueba buscar 7: " + lista.buscar(7)+"\n");

        SalidaPorDefecto.consola("Prueba eliminar W: "+ lista.eliminar("W")+"\n");
        lista.Imprimir();
    }
}
