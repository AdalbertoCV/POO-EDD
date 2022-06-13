package estructuraslineales;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.auxiliares.Nodo;
import estructuraslineales.auxiliares.NodoClave;
import estructurasnolineales.Matriz2;

public class ListaDinamicaClave{
    protected NodoClave primero;
    protected NodoClave ultimo;

    public ListaDinamicaClave(){
        primero = null;
        ultimo = null;
    }

    /**
     * Indica si la lista se encuentra vacia
     * @return regresa true si esta vacia y false si hay elementos
     */
    public boolean vacia(){
        if (primero == null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Busca un elemento por su clave indicada como parametro
     * @param clave es la clave del elemento a buscar
     * @return regresa el elemento si lo encontro y null si no lo encontro
     */
    public Object buscar(Object clave){
        if (!vacia()){
            NodoClave iterador = primero;
            while (iterador!=null){
                Object claveActual = iterador.getClave();
                if (claveActual.toString().equalsIgnoreCase(clave.toString())){
                   return iterador.getContenido();
                }
                iterador = iterador.getNodoDer();
            }
            return null;
        }else{
            return null;
        }
    }

    /**
     * Agrega un nuevo elemento a la lista si su clave no existe
     * @param clave es la clave a agregar
     * @param valor es el valor a agregar
     * @return regresa true si se agrego y false si no se agrego
     */
    public boolean agregar(Object clave, Object valor){
        Object buscarClave = buscar(clave);
        if (buscarClave == null){
            NodoClave nuevo = new NodoClave(valor, clave);
            if (vacia()){
                primero = nuevo;
                ultimo = nuevo;
            }else{
                ultimo.setNodoDer(nuevo);
                ultimo = nuevo;
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * Elimina un elemento de la lista por su clave
     * @param clave es la clave del elemento a eliminar
     * @return regresa el elemento eliminado si se elimino y null si no se elimino
     */
    public Object eliminar(Object clave){
        if (!vacia()){
            Object buscarClave = buscar(clave);
            if (buscarClave != null){
                return eliminarContenido(buscarClave);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * Elimina un elemento por su valor
     * @param valor es el valor del elemento a eliminar
     * @return regresa el elemento eliminado si se pudo eliminar y null si no se elimino
     */
    public Object eliminarContenido(Object valor){
        if (!vacia()){
            NodoClave anterior = primero;
            NodoClave nodoBuscado = primero;
            NodoClave siguiente = nodoBuscado.getNodoDer();
            while (nodoBuscado!=null && nodoBuscado.getContenido()!= valor){
                anterior = nodoBuscado;
                nodoBuscado = nodoBuscado.getNodoDer();
                siguiente = nodoBuscado.getNodoDer();
            }
            if (nodoBuscado==null){
                return null;
            }else{
                Object contenidoEliminar = nodoBuscado.getContenido();
                if (primero == ultimo){
                    primero = null;
                    ultimo = null;
                }else{
                    if(nodoBuscado == primero){
                        primero = siguiente;
                    }else{
                        if (nodoBuscado == ultimo){
                            ultimo = anterior;
                            ultimo.setNodoDer(null);
                        }else{
                            anterior.setNodoDer(siguiente);
                        }
                    }
                }
                return contenidoEliminar;
            }
        }else{
            return null;
        }
    }

    /**
     * Busca un elemento en la lista por su contenido
     * @param valor es el contenido a buscar
     * @return regresa el contenido si se encontro y null si no se encontro
     */
    public Object buscarContenido(Object valor){
        if (!vacia()){
            NodoClave iterador = primero;
            while(iterador!=null){
                Object contenido = iterador.getContenido();
                if (contenido.toString().equalsIgnoreCase(valor.toString())){
                    return contenido;
                }
                iterador = iterador.getNodoDer();
            }
            return null;
        }else{
            return null;
        }
    }

    /**
     * Cambia el valor de un elemento buscandolo por su clave
     * @param clave es la clave del elemento a cambiar
     * @param valor es el valor nuevo a colocar
     * @return regresa true si se cambio correctamente y false si no hubo cambio
     */
    public boolean cambiar(Object clave, Object valor){
        if (!vacia()){
            NodoClave iterador = primero;
            while (iterador != null){
                Object claveActual = iterador.getClave();
                if (claveActual.toString().equalsIgnoreCase(clave.toString())){
                    iterador.setContenido(valor);
                    return true;
                }
                iterador = iterador.getNodoDer();
            }
            return false;
        }else{
            return false;
        }
    }

    /**
     * Cambie el valor de un elemento buscandolo por su contenido
     * @param valorViejo es el contenido a buscar
     * @param valorNuevo es el nuevo contenido a colocar
     * @return regresa true si se hizo el cambio y false si no se pudieron realiar los cambios
     */
    public boolean cambiarValor(Object valorViejo, Object valorNuevo){
        if (!vacia()){
            NodoClave iterador = primero;
            while (iterador != null){
                Object contenido = iterador.getContenido();
                if (contenido.toString().equalsIgnoreCase(valorViejo.toString())){
                    iterador.setContenido(valorNuevo);
                    return true;
                }
                iterador = iterador.getNodoDer();
            }
            return false;
        }else{
            return false;
        }
    }

    /**
     * Imprime la lista completa con las claves y contenidos
     */
    public void imprimir(){
        NodoClave iterador=primero;

        while(iterador!=null){
            Object clave = iterador.getClave();
            Object contenido=iterador.getContenido();
            SalidaPorDefecto.consola("(" + clave + ") " + contenido+" -> ");
            iterador=iterador.getNodoDer();
        }
        SalidaPorDefecto.consola("null \n");
    }

    /**
     * Imprime una lista con todas las claves
     */
    public void imprimirClaves(){
        NodoClave iterador=primero;

        while(iterador!=null){
            Object clave = iterador.getClave();
            SalidaPorDefecto.consola(clave+" -> ");
            iterador=iterador.getNodoDer();
        }
        SalidaPorDefecto.consola("null \n");
    }

    /**
     * Imprime una lista con todos los contenidos
     */
    public void imprimirContenidos(){
        NodoClave iterador=primero;

        while(iterador!=null){
            Object contenido = iterador.getContenido();
            SalidaPorDefecto.consola(contenido+" -> ");
            iterador=iterador.getNodoDer();
        }
        SalidaPorDefecto.consola("null \n");
    }

    /**
     * Obtiene el valor asociado a una clave
     * @param clave es la clave a buscar
     * @return regresa el valor si se encontro la clave y null si no se encontro
     */
    public Object obtener(Object clave){
        return buscar(clave);
    }

    /**
     * Vacia la lista
     */
    public void vaciar(){
        while (!vacia()){
            this.eliminarContenido(ultimo.getContenido());
        }
    }

    /**
     * Obtiene el numero de elementos actual de la lista
     * @return regresa el numero de elementos
     */
    public int numeroElementos(){
        if (!vacia()){
            int num =0;
            NodoClave iterador =  primero;
            while (iterador != null){
                num++;
                iterador = iterador.getNodoDer();
            }
            return num;
        }else{
            return 0;
        }
    }

    /**
     * convierte la lista actual en un par de listas estaticas de claves y contenidos
     * @return Regresa un arreglo con un arreglo de claves y un arreglo de contenidos
     */
    public ListaEstatica aListasEstaticas(){
        if (!vacia()){
            ListaEstatica listas = new ListaEstatica(2);
            ListaEstatica claves = new ListaEstatica(numeroElementos());
            ListaEstatica contenidos = new ListaEstatica(numeroElementos());
            NodoClave iterador = primero;
            while (iterador != null){
                Object clave = iterador.getClave();
                Object contenido = iterador.getContenido();
                claves.agregar(clave);
                contenidos.agregar(contenido);
                iterador = iterador.getNodoDer();
            }
            listas.agregar(claves);
            listas.agregar(contenidos);
            return listas;
        }else{
            return null;
        }
    }

    /**
     * Convierte la lista actual a una lista con dos listas dinamicas
     * @return regresa una lista con dos listas dinamicas, de claves y contenidos
     */
    public ListaDinamica aListasDinamicas(){
        if (!vacia()){
            ListaDinamica listas = new ListaDinamica();
            ListaDinamica claves = new ListaDinamica();
            ListaDinamica contenidos = new ListaDinamica();
            NodoClave iterador = primero;
            while (iterador != null){
                Object clave = iterador.getClave();
                Object contenido = iterador.getContenido();
                claves.agregar(clave);
                contenidos.agregar(contenido);
                iterador = iterador.getNodoDer();
            }
            listas.agregar(claves);
            listas.agregar(contenidos);
            return listas;
        }else{
            return null;
        }
    }

    /**
     * Convierte la lista a una matriz2
     * @return regresa la matriz 2
     */
    public Matriz2 aMatriz2(){
        if (!vacia()){
            Matriz2 matriz = new Matriz2(numeroElementos(),2);
            int numRenglon =0;
            NodoClave iterador = primero;
            while (iterador != null){
                Object clave = iterador.getClave();
                Object contenido = iterador.getContenido();
                matriz.cambiar(numRenglon,0,clave);
                matriz.cambiar(numRenglon,1,contenido);
                numRenglon++;
                iterador = iterador.getNodoDer();
            }
            return matriz;
        }else{
            return null;
        }
    }

    /**
     * Agrega una lista al final de la lista actual
     * @param lista2 es la lista a agregar
     * @return regresa true si es posible agregar la lista completa o false si no se puede
     */
    public boolean agregarLista(ListaDinamicaClave lista2){
        ListaEstatica listas = lista2.aListasEstaticas();
        ListaEstatica claves = (ListaEstatica) listas.obtener(0);
        ListaEstatica contenido = (ListaEstatica) listas.obtener(1);
        ListaEstatica busquedas = new ListaEstatica(claves.numeroElementos());
        for (int pos =0; pos < claves.numeroElementos(); pos++){
            Object busqueda = buscar(claves.obtener(0));
            if (busqueda==null){
                busquedas.agregar(busqueda);
            }
        }
        if (busquedas.numeroElementos() == claves.numeroElementos()){
            for (int pos =0; pos < claves.numeroElementos(); pos++){
                this.agregar(claves.obtener(pos), contenido.obtener(pos));
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * Agrega un par de listas estaticas como claves y contenidos siempre y cuando las claves no se repitan
     * @param arregloClaves es el arreglo con las claves
     * @param arregloValores es el arreglo con los valores
     * @return regresa true si se agregan las listas y false si no se logro
     */
    public boolean agregarListasEstaticas(ListaEstatica arregloClaves, ListaEstatica arregloValores){
        if (arregloClaves.numeroElementos() == arregloValores.numeroElementos()){
            ListaEstatica busquedas = new ListaEstatica(arregloClaves.numeroElementos());
            for (int pos = 0; pos< arregloClaves.numeroElementos(); pos++){
                Object busqueda = buscar(arregloClaves.obtener(pos));
                if (busqueda == null){
                    busquedas.agregar(busqueda);
                }
            }
            if (busquedas.numeroElementos() == arregloClaves.numeroElementos()){
                for (int pos =0; pos< arregloClaves.numeroElementos(); pos++){
                    this.agregar(arregloClaves.obtener(pos), arregloValores.obtener(pos));
                }
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * Agrega un par de listas dinamicas como claves y contenidos siempre y cuando no se repitan las claves
     * @param listaClaves son las claves a agregar
     * @param listaValores son los contenidos a agregar
     * @return regresa true si se pudo agregar las listas y false si no se pudo
     */
    public boolean agregarListasDinamicas(ListaDinamica listaClaves, ListaDinamica listaValores){
        if (listaClaves.numeroElementos() == listaValores.numeroElementos()){
            int numNulls = 0;
            Nodo iterador = listaClaves.getPrimerNodo();
            while (iterador!=null){
                Object clave = iterador.getContenido();
                if (buscar(clave) == null){
                    numNulls++;
                }
                iterador = iterador.getNodoDer();
            }
            if (numNulls == listaClaves.numeroElementos()){
                Nodo iteradorClaves = listaClaves.getPrimerNodo();
                Nodo iteradorContenidos = listaValores.getPrimerNodo();
                while (iteradorClaves!=null){
                    this.agregar(iteradorClaves.getContenido(),iteradorContenidos.getContenido());
                    iteradorClaves = iteradorClaves.getNodoDer();
                    iteradorContenidos = iteradorContenidos.getNodoDer();
                }
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * Agrega una matriz a la lista
     * @param matriz es la matriz a agregar
     * @return regresa un true si se agrego y un false si no
     */
    public boolean agregarMatriz2(Matriz2 matriz){
        if (matriz.obtenerColumnas()==2){
            for (int pos = 0; pos< matriz.obtenerRenglones(); pos++){
                this.agregar(matriz.obtener(pos,0),matriz.obtener(pos,1));
            }
            return true;
        }else{
            return false;
        }
    }

    public NodoClave verPrimerNodo(){
        return primero;
    }
}
