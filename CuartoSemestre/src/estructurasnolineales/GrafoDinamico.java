package estructurasnolineales;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaDinamica;
import estructuraslineales.PilaDinamica;
import estructuraslineales.auxiliares.Nodo;
import estructurasnolineales.auxiliares.Vertice;
import herramientas.generales.Utilerias;

public class GrafoDinamico {
    protected ListaDinamica listaAdyacencia; //guarda las listas secundarias (vértices orígenes hacia losdestinos).

    public GrafoDinamico(){
        listaAdyacencia=new ListaDinamica();
    }

    private ListaDinamica buscarVerticeListaAdyacencia(Object contenidoVertice){
        //Recorrer la lista de Adycacencia para buscarlo solo en la primera posicion de cada sublista

        listaAdyacencia.inicializarIterador();

        while(listaAdyacencia.hayNodos()==true){ //recorremos la lista
            //sacamos un elemento de la lista, es decir una sublista
            ListaDinamica subListaCadaVertice=(ListaDinamica) listaAdyacencia.obtenerNodo();
            //checar si el primer elemento de cada sublista es igual al que nos dan como argumento
            Vertice primerVerticeCadasublista=(Vertice)subListaCadaVertice.verPrimero();
            //checo si este primero es igual al que me dan como argumento
            if(primerVerticeCadasublista.toString().equalsIgnoreCase(contenidoVertice.toString())==true){ //sí está
                return subListaCadaVertice;
            }
        }
        //en caso que nunca entre el ciclo o al if, quiere decir que no lo encontramos
        return null;
    }

    public boolean agregarVertice(Object contenido){
        // Checar si existe o no ese vértice
        // Para esto debemos buscar en la primera posicion de cada sublista
        ListaDinamica sublistaVerticeAEncontrar=buscarVerticeListaAdyacencia(contenido);

        if(sublistaVerticeAEncontrar==null){ //no existe, podemos agregarlo
            ListaDinamica sublistaVerticeNuevo=new ListaDinamica();  //creamos la sublista del vértice nuevo
            Vertice verticeNuevo=new Vertice();  //creamos un nuevo vértice
            verticeNuevo.setContenido(contenido);
            sublistaVerticeNuevo.agregar(verticeNuevo); //agregamos el vértice nuevo a su propia sublista

            //agregamos la sublista a la lista de adycacencia
            if(listaAdyacencia.agregar(sublistaVerticeNuevo)==0){ //si se pudo agregar
                return true;
            }else{ //no se pudo agregar
                return false;
            }
        }else{ //ya existe
            return false;
        }
    }

    public boolean agregarArista(Object origen, Object destino, double costo) {
        //Se ocupa saber si el origen y el destino existen
        ListaDinamica subListaOrigen = buscarVerticeListaAdyacencia(origen);
        ListaDinamica subListaDestino = buscarVerticeListaAdyacencia(destino);

        //checar si son existentes
        if (subListaOrigen != null && subListaDestino != null) { //existen
            //sacamos el primer vértice de la sublista del destino
            Vertice verticeDestino = (Vertice) subListaDestino.verPrimero();
            //agregar este vértice al final de la sublista del origen
            if (subListaOrigen.agregar(verticeDestino) == 0 && subListaOrigen.agregar(costo)==0) { //si se pudo agregar
                return true;
            } else { //no se pudo agregar
                return false;
            }
        } else { //no existen
            return false;
        }
    }

    public void imprimir(){
        //Recorrer la lista de adyacencia y sacar cada una de las sublistas
        listaAdyacencia.inicializarIterador();

        while(listaAdyacencia.hayNodos()==true){
            ListaDinamica sublistaCadaVertice=(ListaDinamica) listaAdyacencia.obtenerNodo();
            //imprimir cada sublista
            sublistaCadaVertice.imprimir();
            SalidaPorDefecto.consola("\n");
        }
    }

    //paso 3 de recorrido en profundidad
    private void enpilarYMarcarVerticesAdyacentes(ListaDinamica sublistaVerticeActual, PilaDinamica pila, ListaDinamica marcados){

        sublistaVerticeActual.inicializarIterador();
        sublistaVerticeActual.obtenerNodo(); //nos brincamos el primero, que es el vértice actual

        //empezaremos a partir del segundo, que son los vecinos del primero (vértice actual)
        while(sublistaVerticeActual.hayNodos()==true){
            //obtener elemento de la sublista (vecino)
            Vertice verticeVecino=(Vertice)sublistaVerticeActual.obtenerNodo();
            //checar si está marcado, porque vecino si es
            if(marcados.buscar(verticeVecino.getContenido())==null){ //no está, por lo tanto no está marcado
                //enpilamos
                pila.poner(verticeVecino);
                //marcamos
                marcados.agregar(verticeVecino);
            }
        }
    }

    //Recorrido en profundidad
    public ListaDinamica recorridoProfunidad(Object origen){
        ListaDinamica recorridoP=new ListaDinamica();
        PilaDinamica pila=new PilaDinamica();
        ListaDinamica marcados=new ListaDinamica();

        //Pasos:

        //0. Validar la existencia del origen.
        ListaDinamica sublistaOrigen=buscarVerticeListaAdyacencia(origen);
        if(sublistaOrigen!=null){ //existe
            //1.- Partir de un vértice origen. Este vértice se marca y se mete en una pila.
            //sacar el vértice Oirgen de la sublista
            Vertice verticeOrigen=(Vertice)sublistaOrigen.verPrimero();
            //marcamos este vértice origen
            marcados.agregar(verticeOrigen);
            //meter el origen en la pila
            pila.poner(verticeOrigen);

            while(pila.vacio()==false) {
                //2.- Mientras existan vértices en la pila, se van a extraer (de uno por uno) y se procesarán (imprimir).
                Vertice verticeActual=(Vertice)pila.quitar(); //sacamos de pila
                recorridoP.agregar(verticeActual.getContenido());//agregamos en la salida el contenido del vértice

                //3.- Los vértices adyacentes (vecinos directos) no marcados y que están enlazados al nodo que actualmente
                // se procesa (el paso 2) se marcan y se meten en la pila.
                //Obtener la sublista del vértice actual
                ListaDinamica subListaVerticeActual=buscarVerticeListaAdyacencia(verticeActual.getContenido());
                enpilarYMarcarVerticesAdyacentes(subListaVerticeActual, pila, marcados);
            }
        }else{ //no existe
            return null;
        }
        return recorridoP;
    }

    /**
     * Algoritmo de prim para obtener el camino mas barato
     * @return regresa una lista dinamica con el camino mas barato
     */
    public ListaDinamica Prim() {
        // obtener una lista de vertices
        ListaDinamica vertices = obtenerVertices();
        ListaDinamica L = new ListaDinamica();
        ListaDinamica U = new ListaDinamica();
        U.agregar(vertices.obtener(0));
        Object verticeActual = vertices.obtener(0);
        Object origenActual = U.obtener(0);
        while (!vertices.listaIgual(U)){
            double aristaMenor = Double.MAX_VALUE;
            for (int posU =0; posU< U.numeroElementos();posU++){
                for (int posV =0; posV<vertices.numeroElementos(); posV++){
                    if (U.buscar(vertices.obtener(posV))==null){
                        double aristaActual = obtenerArista(U.obtener(posU), vertices.obtener(posV));
                        if (aristaActual!=0.0){ // no exite la arista
                            if (aristaActual< aristaMenor){
                                aristaMenor = aristaActual;
                                origenActual = U.obtener(posU);
                                verticeActual = vertices.obtener(posV);
                            }
                        }
                    }
                }
            }
            L.agregar("("+origenActual+","+verticeActual+")");
            U.agregar(verticeActual);
        }
        return L;
    }

    /**
     * obtenemos una lista con los vertices del grafo
     * @return regresa una lista dinamica con los vertices del grafo
     */
    public ListaDinamica obtenerVertices(){
        ListaDinamica vertices = new ListaDinamica();
        Nodo iter = listaAdyacencia.getPrimerNodo();
        while (iter!=null){
            ListaDinamica subLista = (ListaDinamica) iter.getContenido();
            vertices.agregar(subLista.obtener(0));
            iter = iter.getNodoDer();
        }
        return vertices;
    }

    /**
     * Obtiene el valor de la arista solicitada
     * @param origen es el vertice de origen
     * @param destino es el vertice destino
     * @return regres ael valor del vertice y 0.0 si no existe
     */
    public double obtenerArista(Object origen, Object destino){
        ListaDinamica subListaOrigen = buscarVerticeListaAdyacencia(origen);
        if (subListaOrigen!=null){
            if (subListaOrigen.numeroElementos()>1){
                int posicionCosto = 0;
                for (int pos=0; pos< subListaOrigen.numeroElementos(); pos++){
                    if (destino.equals(subListaOrigen.obtener(pos))){
                        posicionCosto = pos+1;
                    }
                }
                if (posicionCosto==0){
                    return 0.0;
                }else{
                    return (double) subListaOrigen.obtener(posicionCosto);
                }
            }else{
                return 0.0;
            }
        }else{
            return 0.0;
        }
    }
}
