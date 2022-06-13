package estructurasnolineales;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ColaDinamica;
import estructuraslineales.PilaDinamica;
import estructuraslineales.auxiliares.NodoDoble;
import herramientas.generales.Utilerias;


public class ArbolBinarioBusqueda extends ArbolBinario{

    public boolean agregar(Object valor){
        if(raiz==null){ //el valor lo agregaremos como la nueva raíz
            NodoDoble nuevoNodo=new NodoDoble(valor);
            if(nuevoNodo!=null){ //si hay espacio
                raiz=nuevoNodo;
                return true;
            }else{ //no hay espacio o hay error
                return false;
            }
        }else{ //ya hay nodos
            return agregar(raiz,valor);
        }
    }

    private boolean agregar(NodoDoble subRaiz, Object valor){
        //Comparar si el valor es mayor o menor a la subraíz
        if(Utilerias.compararObjetos(valor,subRaiz.getContenido())<0){ //valor < subRaiz
            if(subRaiz.getNodoIzq()==null){ //llegamos a la posicion donde le toca ser insertado
                NodoDoble nuevoNodo=new NodoDoble(valor);
                if(nuevoNodo!=null){ // si hay espacio
                    subRaiz.setNodoIzq(nuevoNodo);
                    return true;
                }else{ //no hay espacio
                    return false;
                }
            }else{ //hacemos llamada recursiva hacia esa rama
                return agregar(subRaiz.getNodoIzq(),valor);
            }
        }else if(Utilerias.compararObjetos(valor,subRaiz.getContenido())>0){ //valor > subRaiz
            if(subRaiz.getNodoDer()==null){ //llegamos a la posicion donde le toca ser insertado
                NodoDoble nuevoNodo=new NodoDoble(valor);
                if(nuevoNodo!=null){ // si hay espacio
                    subRaiz.setNodoDer(nuevoNodo);
                    return true;
                }else{ //no hay espacio
                    return false;
                }
            }else{ //hacemos llamada recursiva hacia esa rama
                return agregar(subRaiz.getNodoDer(),valor);
            }
        }else{ //valor = subRaiz
            return false;
        }
    }

    public Object buscar(Object valor){
        return buscar(raiz, valor);
    }

    private Object buscar(NodoDoble subRaiz, Object valor){
        if(subRaiz!=null){ //hay donde buscar
            if (Utilerias.compararObjetos(valor,subRaiz.getContenido())<0) {   //<
                return buscar(subRaiz.getNodoIzq(), valor);
            }else if (Utilerias.compararObjetos(valor,subRaiz.getContenido())>0){  //>
                return buscar(subRaiz.getNodoDer(), valor);
            }else{ //si son iguales
                return subRaiz.getContenido();
            }
        }else{ //no hay donde buscar
            return null;
        }
    }

    /**
     * Metodo publico para eliminar un nodo del abb
     * @param valor es el valor del nodo a eliminar
     * @return regresa el nodo si lo elimino o null si no se encontro
     */
    public Object eliminar(Object valor){
        return eliminar(raiz,valor,null);
    }

    /**
     * Metodo privado y recursivo para eliminar un nodo del abb
     * @param subRaiz es el nodo actual segun el orden
     * @param valor es el valor a eliminar
     * @param padre es el padre del nodo actual
     * @return regresa el nodo si se elimino y null si no se encontro
     */
    private Object eliminar(NodoDoble subRaiz, Object valor, NodoDoble padre){
        if(subRaiz!=null){
            if (Utilerias.compararObjetos(valor,subRaiz.getContenido())<0) {
                return eliminar(subRaiz.getNodoIzq(), valor, subRaiz);
            }else if (Utilerias.compararObjetos(valor,subRaiz.getContenido())>0){
                return eliminar(subRaiz.getNodoDer(), valor, subRaiz);
            }else{
                Object eliminado = subRaiz.getContenido();
                if (subRaiz.getNodoDer()!=null && subRaiz.getNodoIzq()!=null){
                    NodoDoble hijo = subRaiz.getNodoDer();
                    subRaiz = masALaDerecha(subRaiz.getNodoIzq());
                    subRaiz.setNodoDer(hijo);
                    if (Utilerias.compararObjetos(subRaiz.getContenido(),padre.getContenido())>0){
                        padre.setNodoDer(subRaiz);
                    }else{
                        padre.setNodoIzq(subRaiz);
                    }
                    return eliminado;
                }else{
                    if (subRaiz.getNodoDer()!=null){
                        subRaiz =subRaiz.getNodoDer();
                        if (Utilerias.compararObjetos(subRaiz.getContenido(),padre.getContenido())>0){
                            padre.setNodoDer(subRaiz);
                        }else{
                            padre.setNodoIzq(subRaiz);
                        }
                        return eliminado;
                    }else{
                        if (subRaiz.getNodoIzq()!=null){
                            subRaiz = subRaiz.getNodoIzq();
                            if (Utilerias.compararObjetos(subRaiz.getContenido(),padre.getContenido())>0){
                                padre.setNodoDer(subRaiz);
                            }else{
                                padre.setNodoIzq(subRaiz);
                            }
                            return eliminado;
                        }else{
                            if (Utilerias.compararObjetos(subRaiz.getContenido(),padre.getContenido())>0){
                                padre.setNodoDer(null);
                            }else{
                                padre.setNodoIzq(null);
                            }
                            return  eliminado;
                        }
                    }
                }
            }
        }else{
            return null;
        }
    }

    /**
     * Metodo privado y recursivo para encontrar el nodo mas a la derecha de un nodo
     * @param subRaiz es el nodo actual
     * @return regresa el nodo que ya no tenga hijos.
     */
    private NodoDoble masALaDerecha(NodoDoble subRaiz){
        if (subRaiz.getNodoDer()!=null && subRaiz.getNodoIzq()!=null){
            return masALaDerecha(subRaiz.getNodoDer());
        }else{
            return subRaiz;
        }
    }

    /**
     * Metodo no recursivo para recorrer en postorden los nodos del abb
     */
    public void postordenNoRecursivo() {
        if (raiz!=null){
            PilaDinamica pila = new PilaDinamica();
            NodoDoble actual = raiz;
            NodoDoble padre = null;
            while(!pila.vacio() || actual !=null){
                while (actual!=null){
                    pila.poner(actual);
                    actual = actual.getNodoIzq();
                }
                if (!pila.vacio()){
                    NodoDoble temporal = (NodoDoble) pila.quitar();
                    if (temporal.getNodoDer()== null || padre == temporal.getNodoDer()){
                        SalidaPorDefecto.consola(temporal.getContenido()+" ");
                        padre = temporal;
                    }else{
                        pila.poner(temporal);
                        actual = temporal.getNodoDer();
                    }
                }
            }
        }
    }

    /**
     * Recorre el abb con el algoritmo de busqueda por amplitud usando una cola
     */
    public void recorridoAmplitudCola(){
        if (raiz!=null){
            ColaDinamica cola = new ColaDinamica();
            cola.poner(raiz);
            while (!cola.vacio()){
                NodoDoble actual = (NodoDoble) cola.quitar();
                SalidaPorDefecto.consola(actual.getContenido()+" ");
                if (actual.getNodoIzq()!=null){
                    cola.poner(actual.getNodoIzq());
                }
                if (actual.getNodoDer()!=null){
                    cola.poner(actual.getNodoDer());
                }
            }
        }
    }

    /**
     * Recorre el abb con el algoritmo de busqueda por amplitud usando una pila
     */
    public void recorridoAmplitudPila(){
        if (raiz!=null){
            PilaDinamica pila = new PilaDinamica();
            pila.poner(raiz);
            while (!pila.vacio()){
                NodoDoble actual = (NodoDoble) pila.quitar();
                SalidaPorDefecto.consola(actual.getContenido()+" ");
                if (actual.getNodoIzq()!=null){
                    pila.poner(actual.getNodoIzq());
                }
                if (actual.getNodoDer()!=null){
                    pila.poner(actual.getNodoDer());
                }
            }
        }
    }

}
