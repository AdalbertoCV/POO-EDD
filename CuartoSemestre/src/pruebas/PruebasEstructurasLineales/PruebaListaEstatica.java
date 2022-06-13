package pruebas;

import entradasalida.EntradaPorDefecto;
import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaEstatica;

public class PruebaListaEstatica {
    public static void main(String argumentos[]){
        ListaEstatica lista=new ListaEstatica(7);

        lista.agregar("F");
        lista.agregar("D");
        lista.agregar("A");
        lista.agregar("S");
        lista.agregar("G");

        lista.imprimir();

        SalidaPorDefecto.consola("\n");

        lista.imprimirOI();

        SalidaPorDefecto.consola("\n");

        SalidaPorDefecto.consola("Buscando el valor: D ->" + lista.buscar("D")+ "\n");
        SalidaPorDefecto.consola("Buscando el valor: H ->" + lista.buscar("H")+ "\n");

        SalidaPorDefecto.consola("Eliminando A: "+lista.eliminar("A")+ "\n");
        SalidaPorDefecto.consola("Eliminando Z: "+lista.eliminar("Z")+ "\n");

        lista.imprimir();
        SalidaPorDefecto.consola("\n");


        ListaEstatica lista2 = new ListaEstatica(6);

        lista2.agregar("F");
        lista2.agregar("D");
        lista2.agregar("A");
        lista2.agregar("S");
        lista2.agregar("G");

        lista2.imprimir();
        SalidaPorDefecto.consola("Probando igualdad lista2 (no deberia ser igual): " + lista.esIgual(lista2) + "\n");

        lista2.eliminar("A");
        SalidaPorDefecto.consola("\n");
        lista2.imprimir();

        SalidaPorDefecto.consola("Probando igualdad lista2 (Deberia ser igual): " + lista.esIgual(lista2) + "\n");

        SalidaPorDefecto.consola("Probando obtencion de elemento: " + lista.obtener(2) + "\n");
        SalidaPorDefecto.consola("Probando obtencion de elemento: " + lista.obtener(7) + "\n");
        SalidaPorDefecto.consola("Probando obtencion de elemento: " + lista.obtener(-4) + "\n");

        lista.agregar("S");
        lista.agregar("S");

        lista.imprimir();
        SalidaPorDefecto.consola("\n");


        SalidaPorDefecto.consola("Probando cambiar elemento n veces: "+ lista.cambiar("S","X",2)+ "\n");
        lista.imprimir();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Probando cambiar elemento n veces: "+ lista.cambiar("Y","X",2)+ "\n");

        SalidaPorDefecto.consola("Probando cambiar un elemento por indice: " + lista.cambiar(2,"J") + "\n");
        lista.imprimir();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Probando cambiar un elemento por indice: " + lista.cambiar(7,"J") + "\n");

        ListaEstatica indices = new ListaEstatica(6);
        ListaEstatica valoresNuevos = new ListaEstatica(6);
        indices.agregar(0);
        indices.agregar(2);
        indices.agregar(3);
        valoresNuevos.agregar("Z");
        valoresNuevos.agregar("W");
        valoresNuevos.agregar("V");
        SalidaPorDefecto.consola("Probando cambiar lista por indices: "+ lista.cambiarListaEstatica(indices, valoresNuevos)+"\n");
        lista.imprimir();
        valoresNuevos.eliminar("W");
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Probando cambiar lista por indices: "+ lista.cambiarListaEstatica(indices, valoresNuevos)+"\n");

        lista.cambiar(4,"S");
        lista.cambiar(3,"S");

        SalidaPorDefecto.consola("Probando buscar ocurrencias de un valor: " + "\n");
        lista.imprimir();
        SalidaPorDefecto.consola("\n");
        ListaEstatica listaOcurrencias =  lista.buscarValores("S");
        listaOcurrencias.imprimir();
        SalidaPorDefecto.consola("\n");

        SalidaPorDefecto.consola("Probando Eliminar por indice: "+ lista.eliminar(2) + "\n");
        lista.imprimir();
        SalidaPorDefecto.consola("\n");

        SalidaPorDefecto.consola("Probando eliminar el ultimo elemento de la lista: " + lista.eliminar() + "\n");
        lista.imprimir();
        SalidaPorDefecto.consola("\n");

        SalidaPorDefecto.consola("Probando vaciar una lista estatica: (Imprimiendo una nueva lista)" + "\n");
        ListaEstatica listaVaciar = new ListaEstatica(6);
        listaVaciar.agregar("A");
        listaVaciar.agregar("B");
        listaVaciar.agregar("C");
        listaVaciar.agregar("D");
        listaVaciar.imprimir();
        SalidaPorDefecto.consola("\n");
        listaVaciar.vaciar();
        listaVaciar.imprimir();
        SalidaPorDefecto.consola("Comprobando que la lista vaciada esta vacia: " + listaVaciar.vacia() + "\n");
        lista2.vaciar();
        SalidaPorDefecto.consola("Comprobando que la lista vaciada esta vacia: " + lista2.vacia() + "\n");
        ListaEstatica agregar = new ListaEstatica(6);
        agregar.agregar("U");
        agregar.agregar("H");
        agregar.agregar("P");
        SalidaPorDefecto.consola(("Probando agregar una lista: " + lista.agregarLista(agregar) + "\n"));
        lista.imprimir();
        SalidaPorDefecto.consola("\n");
        lista.invertir();
        SalidaPorDefecto.consola("Probando invertir la lista: "+ "\n");
        lista.imprimir();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Probando contar elemento (contando letra S): " + lista.contar("S") + "\n");

        SalidaPorDefecto.consola("Probando eliminar una lista: "+ lista.eliminarLista(agregar)+ "\n");
        lista.imprimir();
        SalidaPorDefecto.consola("\n");

        ListaEstatica listaRellenar = new ListaEstatica(8);
        listaRellenar.agregar("A");
        listaRellenar.agregar("B");
        listaRellenar.agregar("C");

        SalidaPorDefecto.consola("Probando rellenar una lista: " + "\n");
        SalidaPorDefecto.consola("Lista normal: "+"\n");
        listaRellenar.imprimir();
        SalidaPorDefecto.consola("\n");
        listaRellenar.rellenar("Z", 4);
        SalidaPorDefecto.consola("Lista rellenada: "+"\n");
        listaRellenar.imprimir();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Probando rellenar una lista sin espacio: " + "\n");
        listaRellenar.rellenar("X",3);

        SalidaPorDefecto.consola("Probando clonar una lista: " +"\n");
        SalidaPorDefecto.consola("Lista normal: "+"\n");
        lista.imprimir();
        ListaEstatica listaClonada = (ListaEstatica) lista.clonar();
        SalidaPorDefecto.consola("Lista clonada: "+"\n");
        listaClonada.imprimir();
        SalidaPorDefecto.consola("\n");

        SalidaPorDefecto.consola("Probando creacion de sub lista: " + "\n");
        ListaEstatica listaNueva = new ListaEstatica(8);
        listaNueva.agregar("A");
        listaNueva.agregar("B");
        listaNueva.agregar("C");
        listaNueva.agregar("D");
        listaNueva.agregar("E");
        listaNueva.agregar("F");
        listaNueva.agregar("G");
        listaNueva.agregar("H");
        SalidaPorDefecto.consola("Lista normal: "+"\n");
        listaNueva.imprimir();
        SalidaPorDefecto.consola("\n");
        ListaEstatica sublista = (ListaEstatica) listaNueva.subLista(3,7);
        SalidaPorDefecto.consola("Sub lista: "+"\n");
        sublista.imprimir();
        SalidaPorDefecto.consola("\n");

        SalidaPorDefecto.consola("Probando redimensionar lista: "+"\n");
        SalidaPorDefecto.consola("lista normal: "+"\n");
        sublista.imprimir();
        SalidaPorDefecto.consola("\n");
        ListaEstatica redimensionada = (ListaEstatica) sublista.redimensionar(2);
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Lista redimensionada: "+"\n");
        redimensionada.imprimir();
        SalidaPorDefecto.consola("\n");

        SalidaPorDefecto.consola("Pruebas Conlcuidas!!!!!!!"+"\n");
    }
}
