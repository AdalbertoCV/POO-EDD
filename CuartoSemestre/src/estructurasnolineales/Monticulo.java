package estructurasnolineales;

import enumerados.TipoOrden;
import estructuraslineales.auxiliares.NodoDoble;
import herramientas.generales.Utilerias;


public class Monticulo extends ArbolBinario{
    private final TipoOrden tipoOrdenamiento; // ordenamiento ascendente o descendente
    private int controlAgregacion; // variable para agregar a la izquierda o derecha segun sea el caso
    private Object ultimo; // el elemento del ultimo nodo del monticulo
    private NodoDoble padreUltimo; // el padre del ultimo nodo del monticulo

    public Monticulo (TipoOrden orden){
        tipoOrdenamiento = orden;
        controlAgregacion =0;
        ultimo = null;
        padreUltimo = null;
    }

    /**
     * Metodo publico para insertar un nuevo nodo al monticulo
     * @param valor es el valor a insertar
     */
    public void insertar(Object valor){
        insertar(valor,raiz);
    }

    /**
     * Metodo privado y recursivo para insertar un nuevo nodo al monticulo
     * @param valor es el valor a insertar
     * @param subRaiz es el nodo actual
     */
    private void insertar(Object valor, NodoDoble subRaiz) {
        NodoDoble nuevoNodo = new NodoDoble(valor); // creamos el nuevo nodo
        if (raiz == null){ // si no hay elementos, se agrega como raiz
            raiz = nuevoNodo;
            controlAgregacion++;
        }else{ // si hay elementos
            if (subRaiz.getNodoIzq() == null){ // si hay espacio a la izquierda del nodo
                subRaiz.setNodoIzq(nuevoNodo);
            }else{
                if (subRaiz.getNodoDer()==null){ // si hay espacio a la derecha del nodo
                    subRaiz.setNodoDer(nuevoNodo);
                }else{
                    if (controlAgregacion%2 ==0){ // no hay espacio, cambiamos al siguiente nodo derecho
                        insertar(valor, subRaiz.getNodoDer());
                        controlAgregacion++;
                    }else{ // no hay espacio, cambiamos al siguiente nodo izquierdo
                        insertar(valor, subRaiz.getNodoIzq());
                        controlAgregacion++;
                    }
                }
            }
            ordenarNodos(raiz); // ordenamos el arbol cada que se agrege un nuevo nodo
        }
    }

    /**
     * es el metodo recursivo para ordenar los nodos al insertar un nodo nuevo
     * @param subRaiz es el nodo actual
     */
    private void ordenarNodos(NodoDoble subRaiz) {
        if(subRaiz!=null) {
            ordenarNodos(subRaiz.getNodoIzq()); // recorremos cada nodo izquierdo hasta que sea null
            NodoDoble izquierdo = subRaiz.getNodoIzq();
            NodoDoble derecho = subRaiz.getNodoDer();
            if (izquierdo!=null){ // si hay nodo izquierdo
                cambiarNodos(izquierdo,subRaiz); // hacemos el intercambio si se requiere
            }
            if (derecho!=null){ // si hay nodo derecho
                cambiarNodos(derecho,subRaiz); // hacemos el intercambio si se requiere
            }
            ordenarNodos(subRaiz.getNodoDer()); // recorremos cada nodo derecho hasta que sea null
        }
    }

    /**
     * Realiza el intercambio de los nodos si se requiere
     * @param hijo es el nodo hijo a evaluar
     * @param padre es el nodo padre a evaluar
     */
    private void cambiarNodos(NodoDoble hijo, NodoDoble padre){
        if (tipoOrdenamiento.getValor()==1){ // si el orden es ascendente
            if (Utilerias.compararObjetos(hijo.getContenido(), padre.getContenido())>=0){ // si el hijo es mayor
                Object temporal = padre.getContenido();  // realizamos el intercambio
                padre.setContenido(hijo.getContenido());
                hijo.setContenido(temporal);
            }
        }else{ // si el orden es descendente
            if (Utilerias.compararObjetos(hijo.getContenido(), padre.getContenido())<=0){ // si el hijo es menor
                Object temporal = padre.getContenido(); // realizamos el intercambio
                padre.setContenido(hijo.getContenido());
                hijo.setContenido(temporal);
            }
        }
    }

    /**
     * metodo para eliminar un nodo del arbol (el nodo raiz)
     */
    public Object eliminar(){
        if (raiz!=null){
            Object eliminado = raiz.getContenido();
            cambiarultimo(raiz, padreUltimo); // encontramos el ultimo nodo del arbol
            soltarUltimo(); // hacemos que el padre del ultimo nodo lo suelte.
            Object reemplazo = ultimo;
            raiz.setContenido(reemplazo); // se reemplaza el valor de la raiz
            NodoDoble izquierdo = raiz.getNodoIzq();
            NodoDoble derecho = raiz.getNodoDer();
            if ( izquierdo!= null && derecho != null){ // si hay dos hijos
                if (tipoOrdenamiento.getValor()==1){ // si el orden es ascendente
                    if (Utilerias.compararObjetos(izquierdo.getContenido(),derecho.getContenido())>=0){ // si el izquierdo es mayor
                        cambiarNodos(izquierdo,raiz); // se intercambia si se requiere
                    }else{ // si el derecho es mayor
                        cambiarNodos(derecho,raiz); // se intercambia si se requiere
                    }
                }else{ // si es orden descendente
                    if (Utilerias.compararObjetos(izquierdo.getContenido(),derecho.getContenido())<=0){ // si el izquierdo es mayor
                        cambiarNodos(izquierdo,raiz);// se intercambia si se requiere
                    }else{// si el derecho es mayor
                        cambiarNodos(derecho,raiz); // se intercambia si se requiere
                    }
                }
            }else{ // si no hay dos hijos
                if (izquierdo == null && derecho != null){ // hay hijo derecho
                    cambiarNodos(derecho,raiz); // se intercambia si se requiere
                }else{ // hay hijo izquierdo
                    if (izquierdo!= null && derecho == null){
                        cambiarNodos(izquierdo, raiz);
                    }else{
                        ultimo = raiz.getContenido();
                        raiz = null;
                    }
                }
            }
            ordenarNodos(raiz);
            return eliminado;
        }else{
            return null;
        }
    }

    /**
     * encontramos el ultimo nodo del arbol
     * @param subRaiz es el nodo actual
     * @param padre es el padre del nodo actual (nodo anterior)
     */
    private void cambiarultimo(NodoDoble subRaiz, NodoDoble padre){
        if (subRaiz!=null){
            padreUltimo = padre; // hacemos el padre al nodo anterior
            ultimo = subRaiz.getContenido(); // asignamos el valor actual a ultimo
            cambiarultimo(subRaiz.getNodoIzq(),subRaiz); // sigue el ciclo hasta que ya no hayan nodos
            cambiarultimo(subRaiz.getNodoDer(), subRaiz);// sigue el ciclo hasta que ya no hayan nodos
        }
    }

    /**
     * metodo para soltar el ultimo nodo del arbol
     */
    private void soltarUltimo(){
        if (padreUltimo!=null){
            if (padreUltimo.getNodoIzq()!= null && padreUltimo.getNodoDer()!=null){ // si hay dos hijos
                padreUltimo.setNodoDer(null); // soltamos nodo derecho (ultimo)
            }else{ // si no hay dos hijos
                padreUltimo.setNodoIzq(null); // soltamos nodo izquierdo (ultimo)
            }
        }
    }

    public boolean vacio(){
        if (raiz==null){
            return true;
        }else{
            return false;
        }
    }

    public Object getUltimo(){
        return ultimo;
    }

}
