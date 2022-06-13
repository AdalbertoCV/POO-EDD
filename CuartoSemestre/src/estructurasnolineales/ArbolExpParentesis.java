package estructurasnolineales;

import estructuraslineales.PilaEstatica;
import estructuraslineales.auxiliares.NodoDoble;
import herramientas.matematicas.ExpresionesAritmeticas;

public class ArbolExpParentesis extends ArbolBinario{
    /**
     * Metodo para generar el arbol a partir de una expresion con parentesis
     * @param expresion es la expresion a tokenizar y de la cual se generara el arbol
     * @return regresa false si no se pudo generar y true si se pudo
     */
    public boolean generarArbolExpresion(String expresion) {
        PilaEstatica pila = new PilaEstatica(expresion.length());
        NodoDoble nodoActual = new NodoDoble("");
        for (int pos = 0; pos < expresion.length(); pos++) {
            char token = expresion.charAt(pos);
            if (String.valueOf(token).equals("(") && raiz == null) {
                nodoActual.setContenido(token);
                raiz = nodoActual;
                pila.poner(nodoActual);
            } else{
                if (String.valueOf(token).equals("(")) {
                    NodoDoble nuevoNodo = new NodoDoble("");
                    if (nodoActual.getNodoIzq() == null) {
                        nodoActual.setNodoIzq(nuevoNodo);
                    } else {
                        nodoActual.setNodoDer(nuevoNodo);
                    }
                    nodoActual = nuevoNodo;
                    if (!pila.poner(nuevoNodo)) {
                        return false;
                    }
                } else {
                    if (ExpresionesAritmeticas.esOperando(token)) {
                        NodoDoble nuevoNodo = new NodoDoble(token);
                        if (nodoActual.getNodoIzq() == null) {
                            nodoActual.setNodoIzq(nuevoNodo);
                        } else {
                            nodoActual.setNodoDer(nuevoNodo);
                        }
                    } else{
                        if (!String.valueOf(token).equals(" ") && !String.valueOf(token).equals(")")) {
                            try{
                                nodoActual = (NodoDoble) pila.quitar();
                            }catch(Exception e){
                                return false;
                            }
                            nodoActual.setContenido(token);
                        }
                    }
                }
            }
        }
        return true;
    }

}
