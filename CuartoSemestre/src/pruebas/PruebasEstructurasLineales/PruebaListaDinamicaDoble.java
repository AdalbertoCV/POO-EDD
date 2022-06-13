package pruebas.PruebasEstructurasLineales;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaDinamicaDoble;
import estructuraslineales.ListaEstatica;

public class PruebaListaDinamicaDoble {
    public  static  void main(String argumentos[]){
        ListaDinamicaDoble lista=new ListaDinamicaDoble();

        SalidaPorDefecto.consola("Prueba agregando elemento 1: " + lista.agregar(1)+"\n");
        SalidaPorDefecto.consola("Prueba agregando elemento 2: " + lista.agregar(2)+"\n");
        SalidaPorDefecto.consola("Prueba agregando elemento 3: " + lista.agregar(3)+"\n");
        SalidaPorDefecto.consola("Prueba agregando elemento 4: " + lista.agregar(4)+"\n");
        SalidaPorDefecto.consola("Prueba agregando elemento 5: " + lista.agregar(5)+"\n");

        SalidaPorDefecto.consola("Imprimiendo en orden normal:\n");
        lista.imprimir();
        SalidaPorDefecto.consola("Imprimiendo en orden inverso: \n");
        lista.imprimirOI();

        SalidaPorDefecto.consola("Prueba buscar elemento 3 (busqueda orden normal): " + lista.buscar(3)+ "\n");
        SalidaPorDefecto.consola("Prueba buscar elemento 8 (busqueda orden normal): " + lista.buscar(8)+ "\n");

        SalidaPorDefecto.consola("Prueba eliminar el ultimo elemento: " + lista.eliminar()+"\n");
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba eliminar el elemento 3: " + lista.eliminar(3)+"\n");
        lista.imprimir();
        SalidaPorDefecto.consola("Nuevas dos listas a comparar: \n");
        ListaDinamicaDoble lista2 = new ListaDinamicaDoble();
        lista2.agregar(1);
        lista2.agregar(2);
        lista2.agregar(4);
        ListaDinamicaDoble lista3 = new ListaDinamicaDoble();
        lista3.agregar(0);
        lista3.agregar("H");
        lista3.agregar(10);
        lista2.imprimir();
        lista3.imprimir();
        SalidaPorDefecto.consola("Prueba es igual (lista 2): " + lista.esIgual(lista2)+"\n" );
        SalidaPorDefecto.consola("Prueba es igual (lista 3): " + lista.esIgual(lista3)+"\n" );
        SalidaPorDefecto.consola("Prueba rellenar con (1) 4 veces: \n");
        lista.rellenar(1,4);
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba cambiar el 1 por 0 3 veces: " + lista.cambiar(1,0,3)+"\n");
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba buscar valores (0): \n");
        ListaEstatica ceros = lista.buscarValores(0);
        ceros.imprimir();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba clonar la lista: \n");
        ListaDinamicaDoble copia = (ListaDinamicaDoble) lista.clonar();
        copia.imprimir();
        SalidaPorDefecto.consola("Prueba vaciar lista: \n");
        copia.vaciar();
        copia.imprimir();
        SalidaPorDefecto.consola("Lista a agregar: \n");
        lista2.imprimir();
        SalidaPorDefecto.consola("Prueba agregar lista: " + lista.agregarLista(lista2)+"\n");
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba invertir la lista: \n");
        lista.invertir();
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba contar elemento 1: "+ lista.contar(1)+"\n");
        SalidaPorDefecto.consola("Prueba eliminar lista2: " + lista.eliminarLista(lista2)+"\n");
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba ver ultimo elemento: " + lista.verUltimo()+"\n");
        SalidaPorDefecto.consola("Prueba agregar elemento 9 al inicio: " + lista.agregarInicio(9)+"\n");
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba eliminar elemento al inicio: " + lista.eliminarInicio()+"\n");
        lista.imprimir();
        SalidaPorDefecto.consola("Buscar en orden inverso el elemento 4: " + lista.buscarOI(4)+"\n\n");

        SalidaPorDefecto.consola("---Probando el iterador---\n");
        SalidaPorDefecto.consola("Inicializando iterador circular en el inicio: "+lista.iniciarIteradorAlInicio(true)+"\n");
        SalidaPorDefecto.consola("Prueba mover iterador a la derecha: " + lista.moverIteradorDerecha()+"\n");
        SalidaPorDefecto.consola("Prueba mover iterador a la derecha: " + lista.moverIteradorDerecha()+"\n");
        SalidaPorDefecto.consola("Prueba mover iterador a la derecha: " + lista.moverIteradorDerecha()+"\n");
        SalidaPorDefecto.consola("Prueba mover iterador a la izquierda: " + lista.moverIteradorIzquierda()+"\n");
        SalidaPorDefecto.consola("Prueba mover iterador a la izquierda: " + lista.moverIteradorIzquierda()+"\n\n");
        lista.imprimir();
        SalidaPorDefecto.consola("\n");

        SalidaPorDefecto.consola("Inicializando iterador circular en el final: " + lista.iniciarIteradorAlFinal(true)+"\n");
        SalidaPorDefecto.consola("Prueba mover iterador a la derecha: " + lista.moverIteradorDerecha()+"\n");
        SalidaPorDefecto.consola("Prueba mover iterador a la derecha: " + lista.moverIteradorDerecha()+"\n");
        SalidaPorDefecto.consola("Prueba mover iterador a la derecha: " + lista.moverIteradorDerecha()+"\n");
        SalidaPorDefecto.consola("Prueba mover iterador a la izquierda: " + lista.moverIteradorIzquierda()+"\n");
        SalidaPorDefecto.consola("Prueba mover iterador a la izquierda: " + lista.moverIteradorIzquierda()+"\n");
        SalidaPorDefecto.consola("Prueba mover iterador a la izquierda: " + lista.moverIteradorIzquierda()+"\n");
        SalidaPorDefecto.consola("Prueba mover iterador a la izquierda: " + lista.moverIteradorIzquierda()+"\n");
        SalidaPorDefecto.consola("Prueba mover iterador a la izquierda: " + lista.moverIteradorIzquierda()+"\n\n");

    }
}
