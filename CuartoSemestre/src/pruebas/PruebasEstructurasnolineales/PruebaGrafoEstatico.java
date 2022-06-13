package pruebas.PruebasEstructurasnolineales;

import entradasalida.SalidaPorDefecto;
import estructurasnolineales.GrafoEstatico;

public class PruebaGrafoEstatico {
    public static void main (String args[]){
        GrafoEstatico grafo1=new GrafoEstatico(5);

        grafo1.agregarVertice("Z");
        grafo1.agregarVertice("M");
        grafo1.agregarVertice("S");
        grafo1.agregarVertice("T");

        grafo1.agregarArista("Z","S");
        grafo1.agregarArista("S","T");
        grafo1.agregarArista("T","M");
        grafo1.agregarArista("M","S");

        grafo1.imprimir();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba es adyacente Z-S: " + grafo1.esAdyacente("Z","S")+"\n");
        SalidaPorDefecto.consola("Prueba es adyacente S-Z: " + grafo1.esAdyacente("S","Z")+"\n");
        //SalidaPorDefecto.consola("Prueba eliminar arista Z-S: " + grafo1.eliminarArista("Z","S")+"\n");
        grafo1.imprimir();
        SalidaPorDefecto.consola("Prueba buscar vertice S: " + grafo1.buscarVertice("S")+ "\n");
        SalidaPorDefecto.consola("Prueba buscar vertice A: " + grafo1.buscarVertice("A")+ "\n");
        SalidaPorDefecto.consola("Prueba es pseudografo: " + grafo1.esPseudografo()+"\n");
        SalidaPorDefecto.consola("Prueba es multigrafo: " + grafo1.esMultigrafo()+"\n");
        SalidaPorDefecto.consola("Prueba obtener grado vertice T: " + grafo1.gradoVertice("T")+"\n");
        SalidaPorDefecto.consola("Prueba obtener grado vertice Z: " + grafo1.gradoVertice("Z")+"\n");
        SalidaPorDefecto.consola("Prueba hay ruta Z-M: "+ grafo1.hayRuta("Z","M")+"\n");
        SalidaPorDefecto.consola("Prueba hay ruta M-S: "+ grafo1.hayRuta("M","S")+"\n");
        SalidaPorDefecto.consola("Prueba es conexo: " + grafo1.esConexo()+"\n" );
        SalidaPorDefecto.consola("Prueba hay camino cerrado: " + grafo1.hayCaminoCerrado()+"\n");
        SalidaPorDefecto.consola("Prueba es camino simple Z-T: " + grafo1.esCaminoSimple("Z","T")+"\n");
        SalidaPorDefecto.consola("Prueba es camino simple Z-Z: " + grafo1.esCaminoSimple("Z","Z")+"\n");
        grafo1.esDirigido();
        SalidaPorDefecto.consola("Prueba es dirigido: "+ grafo1.esDirigido()+"\n");
        SalidaPorDefecto.consola("Prueba es arbol: " + grafo1.esArbol()+"\n");
        SalidaPorDefecto.consola("Prueba enlistar aristas: \n");
        grafo1.listarAristas();
        SalidaPorDefecto.consola("Prueba enlistar aristas de Z: \n");
        grafo1.listarAristas("Z");
        SalidaPorDefecto.consola("Prueba mostrar vertices: \n");
        grafo1.listarVertices();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba eliminar vertice T: "+ grafo1.eliminarVertice("T")+"\n");
        grafo1.imprimir();


        //Creando un grafo no dirigido

        GrafoEstatico grafo2 = new GrafoEstatico(4);
        grafo2.agregarVertice("M");
        grafo2.agregarVertice("Z");
        grafo2.agregarVertice("T");
        grafo2.agregarVertice("S");

        grafo2.agregarArista("M","Z");
        grafo2.agregarArista("Z","T");
        grafo2.agregarArista("T","S");
        grafo2.agregarArista("Z","M");
        grafo2.agregarArista("T","Z");
        grafo2.agregarArista("S","T");

        SalidaPorDefecto.consola("\n\nCreando grafo no dirigido: \n");
        grafo2.imprimir();

        SalidaPorDefecto.consola("Prueba es dirigido: " + grafo2.esDirigido()+"\n");
        SalidaPorDefecto.consola("Prueba esArbol: "+ grafo2.esArbol()+"\n");
    }
}
