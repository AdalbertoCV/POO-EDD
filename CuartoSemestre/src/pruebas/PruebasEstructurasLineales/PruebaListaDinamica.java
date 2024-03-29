package pruebas.PruebasEstructurasLineales;

import entradasalida.SalidaPorDefecto;
import enumerados.TipoTabla;
import estructuraslineales.ListaDinamica;
import estructuraslineales.ListaEstatica;
import estructurasnolineales.Matriz2;

public class PruebaListaDinamica {
    public static void main(String args[]){
        SalidaPorDefecto.consola("---Creando Lista Dinamica---\n");
        ListaDinamica lista = new ListaDinamica();
        SalidaPorDefecto.consola("Prueba agregando elemento a la lista (1): " + lista.agregar(1)+"\n");
        SalidaPorDefecto.consola("Prueba agregando elemento a la lista (2): " + lista.agregar(2)+"\n");
        SalidaPorDefecto.consola("Prueba agregando elemento a la lista (3): " + lista.agregar(3)+"\n");
        SalidaPorDefecto.consola("Prueba agregando elemento a la lista (4): " + lista.agregar(4)+"\n");
        SalidaPorDefecto.consola("Prueba agregando elemento a la lista (5): " + lista.agregar(5)+"\n");
        SalidaPorDefecto.consola("Prueba imprimir orden normal: \n");
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba imprimir orden inverso: \n");
        lista.imprimirOI();
        SalidaPorDefecto.consola("Prueba buscar elemento existente (4): " + lista.buscar(4)+"\n");
        SalidaPorDefecto.consola("Prueba buscar elemento inexistente (6): " + lista.buscar(6)+"\n");
        SalidaPorDefecto.consola("Prueba convertir a lista estatica: \n");
        ListaEstatica listaEstatica = lista.aListaEstatica();
        listaEstatica.imprimir();
        SalidaPorDefecto.consola("\n");
        ListaEstatica valoresDescartar = new ListaEstatica(3);
        valoresDescartar.agregar(1);
        valoresDescartar.agregar(3);
        valoresDescartar.agregar(5);
        SalidaPorDefecto.consola("Prueba convertir a lista estatica descartando valores (1,3,5): \n");
        listaEstatica = lista.aListaEstatica(valoresDescartar);
        listaEstatica.imprimir();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba convertir a matriz2d (3x3): \n");
        Matriz2 matriz = lista.aMatriz2D(3,3);
        matriz.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Lista Estatica a agregar: \n");
        ListaEstatica listaagregar= new ListaEstatica(2);
        listaagregar.agregar(10);
        listaagregar.agregar(13);
        listaagregar.imprimir();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba agregar una lista estatica: "+ lista.agregarLista(listaagregar) + "\n");
        lista.imprimir();
        SalidaPorDefecto.consola("Lista Dinamica a agregar: \n");
        ListaDinamica dinamicaAgregar = new ListaDinamica();
        dinamicaAgregar.agregar(20);
        dinamicaAgregar.agregar(23);
        dinamicaAgregar.agregar(100);
        dinamicaAgregar.imprimir();
        SalidaPorDefecto.consola("Prueba agregar una lista dinamica: "+ lista.agregarLista(dinamicaAgregar) + "\n");
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba clonar lista: \n");
        ListaDinamica copia = (ListaDinamica) lista.clonar();
        copia.imprimir();
        SalidaPorDefecto.consola("Matriz a agregar: \n");
        Matriz2 matriz1 = new Matriz2(2,2);
        matriz1.rellenar(1);
        matriz1.cambiar(1,1,2);
        matriz1.cambiar(1,0,3);
        matriz1.cambiar(0,1,4);
        matriz1.imprimirXRenglones();
        SalidaPorDefecto.consola("Prueba agregar matriz a la lista por renglon: " + lista.agregarMatriz2D(matriz1, TipoTabla.RENGLON)+"\n");
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba agregar matriz a la lista por Columna: " + lista.agregarMatriz2D(matriz1, TipoTabla.COLUMNA)+"\n");
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba vaciar la lista: \n");
        lista.vaciar();
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba rellenar lista vacia (5) 3 veces: \n");
        lista.rellenar(5,3);
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba rellenar lista (2) 2 veces: \n");
        lista.rellenar(2,2);
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba contar ocurrencias del numero 5: " + lista.contar(5) + "\n");
        SalidaPorDefecto.consola("Prueba contar ocurrencias del numero 2: " + lista.contar(2) + "\n");
        SalidaPorDefecto.consola("Prueba contar ocurrencias del numero 1: " + lista.contar(1) + "\n");
        SalidaPorDefecto.consola("Prueba invertir lista: \n");
        lista.invertir();
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba cambiar 5 por 4, 2 veces: " + lista.cambiar(5,4,2)+ "\n");
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba cambiar elemento posicion 3 por 8: " + lista.cambiar(3,8) + "\n");
        SalidaPorDefecto.consola("Prueba cambiar elemento posicion 8 (no existe) por 8: "+ lista.cambiar(8,8)+ "\n");
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba obtener elemento en la posicion 1: " + lista.obtener(1)+ "\n");
        ListaDinamica lista1 = new ListaDinamica();
        lista1.agregar(2);
        lista1.agregar(2);
        lista1.agregar(4);
        lista1.agregar(8);
        lista1.agregar(5);
        SalidaPorDefecto.consola("Lista1 a comparar: \n");
        lista1.imprimir();
        ListaDinamica lista2 = new ListaDinamica();
        lista2.agregar(1);
        lista2.agregar(2);
        lista2.agregar(9);
        lista2.agregar(1);
        lista2.agregar(4);
        SalidaPorDefecto.consola("Lista2 a comparar: \n");
        lista2.imprimir();
        SalidaPorDefecto.consola("Prueba comparar con Lista1: " + lista.esIgual(lista1) + "\n");
        SalidaPorDefecto.consola("Prueba comparar con Lista2: " + lista.esIgual(lista2) + "\n");
        SalidaPorDefecto.consola("Prueba redimensionar a 9: \n");
        lista = (ListaDinamica) lista.redimensionar(9);
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba redimensionar a 3: \n");
        lista = (ListaDinamica) lista.redimensionar(3);
        lista.imprimir();
        Object eliminar = 4;
        SalidaPorDefecto.consola("Prueba eliminar 4: " + lista.eliminar(eliminar)+ "\n");
        lista.imprimir();
        SalidaPorDefecto.consola("Agregando de nuevo el 4 para eliminarlo por indice: "+ lista.agregar(4)+ "\n");
        lista.imprimir();
        SalidaPorDefecto.consola("Prueba eliminar indice 2: " + lista.eliminar(2) + "\n");
        lista.imprimir();
    }
}
