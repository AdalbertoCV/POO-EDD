package herramientas.ordenamiento;

import entradasalida.EntradaPorDefecto;
import entradasalida.SalidaPorDefecto;
import enumerados.TipoOrden;
import estructuraslineales.PilaDinamica;
import estructurasnolineales.Monticulo;

/** Clase ordenamiento Heap Sort
 * @author Adalberto Cerrillo Vazquez - 4B
 * @version 1.0
 */
public class HeapSort {
    /**
     * Es la pila en donde se guardaran los valores ingresados por el usuario
     */
    private PilaDinamica pila;
    /**
     * Es el monticulo donde se ordenaran los valores
     */
    private Monticulo heap;

    /**
     * Constructor de la clase
     * @param orden es el orden en que se ordenaran los valores
     */
    public HeapSort(TipoOrden orden){
        pila = new PilaDinamica();
        if (orden.getValor()==1){
            heap = new Monticulo(TipoOrden.DECR);
        }else{
            heap = new Monticulo(TipoOrden.INCR);
        }
    }

    /**
     * Se ingresan los valores de manera interactiva con el usuario, guardandolos en la pila
     */
    public void ingresarValores(){
        String r = "s";
        while (r.equalsIgnoreCase("s")){
            SalidaPorDefecto.consola("Desea ingresar un nuevo valor? ");
            r = EntradaPorDefecto.consolaCadenas();
            if (r.equalsIgnoreCase("s")){
                SalidaPorDefecto.consola("Ingrese el valor: ");
                int valor = EntradaPorDefecto.consolaInt();
                pila.poner(valor);
            }
        }
        ordenarValores();
    }

    /**
     * Se ordenan los valores ingresandolos al monticulo
     */
    private void ordenarValores(){
        while (!pila.vacio()){
            heap.insertar(pila.quitar());
        }
    }

    /**
     * Se ordenan los valores enviados en una pila
     * @param pila es la pila de los valores a ordenar
     */
    public void ordenarValores(PilaDinamica pila){
        while (!pila.vacio()){
            heap.insertar(pila.quitar());
        }
    }

    /**
     * mostramos todas las impresiones de las eliminaciones de las raices del monticulo
     */
    public void imprimir(){
        SalidaPorDefecto.consola("Orden: \n");
        while (!heap.vacio()){
            SalidaPorDefecto.consola(heap.eliminar()+" ");
        }
        SalidaPorDefecto.consola(heap.getUltimo()+"");
    }

}
