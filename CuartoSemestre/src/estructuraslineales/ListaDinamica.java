package estructuraslineales;

import entradasalida.SalidaPorDefecto;
import enumerados.TipoTabla;
import estructuraslineales.auxiliares.*;
import estructurasnolineales.Matriz2;

public class ListaDinamica implements ListaAlmacenamiento{
    protected Nodo primero;
    protected Nodo ultimo;
    protected Nodo iterador;
    protected int numeroElementos;

    public ListaDinamica(){
        primero=null;
        ultimo=null;
        iterador = null;
    }

    @Override
    public boolean vacia(){
        if(primero==null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int agregar(Object valor){
        Nodo nuevoNodo=new Nodo(valor); //paso 1
        if(nuevoNodo!=null){ //hay espacio
            if(vacia()==true){  //a)
                primero=nuevoNodo; //paso 2
                ultimo=nuevoNodo;
            }else{ //b) y c)
                ultimo.setNodoDer(nuevoNodo); //paso 2
                ultimo=nuevoNodo; //paso 3
            }
            return 0;
        }else{ //no hay espacio
            return -1;
        }
    }

    public int agregarInicio(Object valor){
        Nodo nuevoNodo=new Nodo(valor); //paso 1
        if(nuevoNodo!=null){ //hay esapcio
            if(vacia()==true){ //a)
                primero=nuevoNodo; //paso 2
                ultimo=nuevoNodo;
            }else{  //b)
                nuevoNodo.setNodoDer(primero); //paso 2
                primero=nuevoNodo; //paso 3
            }
            return 0;
        }else{ //no hay espacio
            return -1;
        }
    }

    @Override
    public void imprimir(){
        Nodo iterador=primero; //paso inicial
        
        while(iterador!=null){
            Object contenido=iterador.getContenido(); //paso 1
            SalidaPorDefecto.consola(contenido+" -> ");//paso 2
            iterador=iterador.getNodoDer();//paso 3
        }
        SalidaPorDefecto.consola("null \n");
    }

    /**
     * Imprime en orden inverso todos los elementos que se eucuentren en la lista actualmente
     */
    @Override
    public void imprimirOI(){
        PilaDinamica pilaContenido = new PilaDinamica();
        Nodo iterador = primero;

        while(iterador!=null){
            Object contenido = iterador.getContenido();
            pilaContenido.poner(contenido);
            iterador = iterador.getNodoDer();
        }

        while (!pilaContenido.vacio()){
            SalidaPorDefecto.consola(pilaContenido.quitar() + " -> ");
        }
        SalidaPorDefecto.consola("null \n");
    }

    public int numeroElementos(){
        if (!vacia()){
            int num =0;
            Nodo iterador =  primero;
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
     * Busca un elemento dentro de la lista dinamica
     * @param valor Es el dato que se va a buscar en la lista
     * @return regresa el contenido si se encontro y null si no se encontro
     */
    @Override
    public Object buscar(Object valor){
        if (!vacia()){
            Nodo iterador = primero;
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
     * Convierte la lista dinamica actual en una lista estatica
     * @return regres la lista estatica si se creo y null si o se pudo
     */
    public ListaEstatica aListaEstatica(){
        if (!vacia()){
            int contadorElementos =0;
            Nodo iterador = primero;
            while (iterador!=null){
                iterador= iterador.getNodoDer();
                contadorElementos++;
            }
            ListaEstatica retorno = new ListaEstatica(contadorElementos);
            iterador = primero;
            while(iterador!=null){
                Object contenido = iterador.getContenido();
                retorno.agregar(contenido);
                iterador = iterador.getNodoDer();
            }
            return retorno;
        }else{
            return null;
        }
    }

    /**
     * Convierte la lista a una lista estatica, pero descarta los elementos de la lista pasada como argumento
     * @param elementosADescartar es la lista con los elementos a descartar
     * @return regresa la lista estatica obtenida
     */
    public ListaEstatica aListaEstatica(ListaEstatica elementosADescartar){
        if (!vacia()){
            int contadorElementos =0;
            Nodo iterador = primero;
            while (iterador!=null){
                iterador= iterador.getNodoDer();
                contadorElementos++;
            }
            ListaEstatica retorno = new ListaEstatica(contadorElementos);
            iterador = primero;
            while(iterador!=null){
                Object contenido = iterador.getContenido();
                Object elementoBusqueda = elementosADescartar.buscar(contenido);
                if (elementoBusqueda == null){
                    retorno.agregar(contenido);
                }
                iterador = iterador.getNodoDer();
            }
            return retorno;
        }else{
            return null;
        }
    }

    /**
     * Convierte la lista actual a una matriz de dos dimensiones
     * @param filas es la cantidad de filas
     * @param columnas es la cantidad de columnas
     * @return regresa la matriz si se puedo crear y null si no
     */
    public Matriz2 aMatriz2D(int filas, int columnas){
        if (!vacia()){
            Matriz2 matrizRetorno = new Matriz2(filas, columnas);
            Nodo iterador = primero;
            ColaDinamica colaContenido = new ColaDinamica();
            int contElementosActuales =0;
            while(iterador!=null){
                Object Contenido = iterador.getContenido();
                colaContenido.poner(Contenido);
                contElementosActuales++;
                iterador = iterador.getNodoDer();
            }
            if (contElementosActuales <= (filas*columnas)){
                for (int fila=0; fila<filas; fila++){
                    for (int col=0; col<columnas; col++){
                        matrizRetorno.cambiar(fila,col, colaContenido.quitar());
                    }
                }
                return matrizRetorno;
            }else{
                return null;
            }
        }else {
            return null;
        }
    }

    /**
     * Agrega ya sea una lista estatica o una lista dinamica al final de la lista actual
     * @param listaDatos2 es la lista a agregar
     * @return regresa true si se agrego o false si no se agrego
     */
    public boolean agregarLista(ListaAlmacenamiento listaDatos2){
        if (true){
            if (listaDatos2 instanceof ListaEstatica){
                ListaEstatica lista = (ListaEstatica) listaDatos2;
                for (int pos=0; pos< lista.numeroElementos(); pos++){
                    this.agregar(lista.obtener(pos));
                }
                return true;
            }else{
                if (listaDatos2 instanceof  ListaDinamica){
                    ListaDinamica lista = (ListaDinamica) listaDatos2;
                    Nodo iterador = lista.primero;
                    while (iterador!= null){
                        Object contenido = iterador.getContenido();
                        this.agregar(contenido);
                        iterador = iterador.getNodoDer();
                    }
                    return true;
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }
    }

    /**
     * Agrega al final de la lista una matriz ya sea por renglones o por columnas
     * @param tabla es la matriz a agregar
     * @param enumTipoTabla es el enumerado que nos dice si se agrega por renglon o columna
     * @return regresa true si se pudo agregar y false si no se pudo
     */
    public boolean agregarMatriz2D(Matriz2 tabla, TipoTabla enumTipoTabla){
        if (enumTipoTabla.getValor()==1){
            for (int fila=0; fila<tabla.obtenerRenglones(); fila++){
                for (int col=0; col< tabla.obtenerColumnas(); col++){
                    this.agregar(tabla.obtener(fila,col));
                }
            }
            return true;
        }else{
            if (enumTipoTabla.getValor()==2){
                for (int col=0; col< tabla.obtenerColumnas(); col++){
                    for (int fila=0; fila< tabla.obtenerRenglones(); fila++){
                        this.agregar(tabla.obtener(fila,col));
                    }
                }
                return true;
            }else{
                return false;
            }
        }
    }

    public Object eliminarInicio(){
        if(vacia()==false){ //hay algo
            Object contenidoEliminado=primero.getContenido(); //paso 1
            if(primero==ultimo){ //b
                primero=null;//paso 2
                ultimo=null;
            }else{ //c
                primero=primero.getNodoDer();//paso 2
            }
            return contenidoEliminado;
        }else{ //vacía a)
            return null;
        }
    }

    /**
     * Elimina un valor en especifico
     * @param valor Es el elemento que se intentara eliminar de la lista
     * @return regresa el objeto eliminado y null si no se elimino nada
     */
    @Override
    public Object eliminar(Object valor){
        if (!vacia()){
            Nodo anterior = primero;
            Nodo nodoBuscado = primero;
            Nodo siguiente = nodoBuscado.getNodoDer();
            while (nodoBuscado!=null && nodoBuscado.getContenido()!= valor){
                anterior = nodoBuscado;
                nodoBuscado = nodoBuscado.getNodoDer();
                if (siguiente!= null){
                    siguiente = nodoBuscado.getNodoDer();
                }
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
     * Elimina el elemento de la lista que se encuentre en la posicion indicada
     * @param indice es la posicion a eliminar
     * @return regresa el elemento eleminido o null si no se elimino nada
     */
    public Object eliminar(int indice){
        if (!vacia()){
            int contadorIndices=0;
            Nodo anterior = primero;
            Nodo nodoBuscado = primero;
            Nodo siguiente = nodoBuscado.getNodoDer();
            while (nodoBuscado!=null){
                if (contadorIndices == indice){
                    break;
                }
                contadorIndices++;
                anterior = nodoBuscado;
                nodoBuscado = nodoBuscado.getNodoDer();
                if (anterior == ultimo){
                    siguiente = null;
                }else{
                    siguiente = nodoBuscado.getNodoDer();
                }
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

    @Override
    public boolean esIgual(Object lista2) {
        return false;
    }

    /**
     * Indica si la lista actual y la enviada como parametro es igual
     * @param listaDatos2 es la lista a comparar
     * @return regresa true si son iguales y false si no lo son
     */
    public boolean esIgual(ListaAlmacenamiento listaDatos2){
        if (!vacia()){
            if (listaDatos2 instanceof ListaDinamica){
                ListaDinamica lista2 = (ListaDinamica) listaDatos2;
                int numElemsLista2 =0;
                Nodo iteradorLista2 = lista2.primero;
                while(iteradorLista2 !=null){
                    numElemsLista2++;
                    iteradorLista2 = iteradorLista2.getNodoDer();
                }
                int contadorIndices =0;
                int contadorIguales =0;
                Nodo iterador = primero;
                while(iterador !=null){
                    if (iterador.getContenido().toString().equalsIgnoreCase(((ListaDinamica) listaDatos2).obtener(contadorIndices).toString())){
                        contadorIguales++;
                    }
                    iterador = iterador.getNodoDer();
                    contadorIndices++;
                }
                if ((contadorIguales == contadorIndices) && (contadorIndices == numElemsLista2)){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean listaIgual(ListaDinamica lista2){
        int contadorExistencia =0;
        if (!vacia()){
            if (!lista2.vacia()){
                if (this.numeroElementos() == lista2.numeroElementos()){
                    for (int pos=0; pos<this.numeroElementos();pos++){
                        if (lista2.buscar(obtener(pos))!=null){
                            contadorExistencia++;
                        }
                    }
                    if (contadorExistencia == this.numeroElementos()){
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * Cambie el valor viejo indicado por el valor nuevo indicado la cantidad de veces indicada como parametro
     * @param valorViejo este es el valor que sera reemplazado
     * @param valorNuevo este es el valor a colocar
     * @param numVeces este es el numero de veces en que se reemplazara
     * @return
     */
    @Override
    public boolean cambiar(Object valorViejo, Object valorNuevo, int numVeces) {
        if (!vacia()){
            int contadorVeces=0;
            Nodo iterador = primero;
            while(iterador!=null){
                Object contenido = iterador.getContenido();
                if (contenido.toString().equalsIgnoreCase(valorViejo.toString()) && contadorVeces<numVeces){
                    iterador.setContenido(valorNuevo);
                    contadorVeces++;
                }
                iterador = iterador.getNodoDer();
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * Cambia el valor de la lista en el indice indicado
     * @param indice es la posicion de el elemento
     * @param valor es el valor a colocar
     * @return regresa true si se pudo cambiar y false si no se pudo
     */
    public boolean cambiar(int indice, Object valor){
        if (!vacia()){
            int indiceElemento =0;
            Nodo iterador = primero;
            boolean indiceValido = false;
            while (iterador!=null){
                if (indiceElemento == indice){
                    iterador.setContenido(valor);
                    indiceValido= true;
                }
                iterador= iterador.getNodoDer();
                indiceElemento++;
            }
            if (indiceValido){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public ListaEstatica buscarValores(Object valor) {
        return null;
    }

    @Override
    public Object eliminar(){
        if(vacia()==false){ //hay datos
            Object contenidoEliminado=ultimo.getContenido();//paso 1
            if(primero==ultimo){  //b)
                //contenidoEliminado=ultimo.getContenido();//paso 1
                primero=null; //paso 2
                ultimo=null;
            }else{ //c)
                //contenidoEliminado=ultimo.getContenido();//paso 1
                //buscar a penultimo
                Nodo penultimo=primero;
                while(penultimo.getNodoDer()!=ultimo){
                    penultimo=penultimo.getNodoDer(); //i=i+1
                }
                ultimo=penultimo; //paso 2
                ultimo.setNodoDer(null);//paso 3
            }
            return contenidoEliminado;
        }else { //a)
            return null;
        }
    }

    public void ImprimirFormatoColumna(){
        Nodo iterador = primero;
        while (iterador!=null){
            Object contenido = iterador.getContenido();
            SalidaPorDefecto.consola(contenido.toString()+"\n");
            iterador = iterador.getNodoDer();
        }
    }

    /**
     * Elimina todos los elementos de la lista
     */
    @Override
    public void vaciar() {
        while (!vacia()){
            this.eliminar();
        }
    }

    @Override
    public boolean agregarLista(Object lista2) {
        return false;
    }

    /**
     * Invierte el contenido de la pila
     */
    @Override
    public void invertir() {
        PilaDinamica pilaContenido = new PilaDinamica();
        Nodo iterador = primero;
        int numElems=0;

        while(iterador!=null){
            Object contenido = iterador.getContenido();
            pilaContenido.poner(contenido);
            numElems++;
            iterador = iterador.getNodoDer();
        }

        this.vaciar();
        for (int pos=0; pos< numElems; pos++){
            this.agregar(pilaContenido.quitar());
        }
    }

    /**
     * Cuenta la cantidad de veces que el valor indicado como parametro existe en la lista
     * @param valor este es el elemento a contar
     * @return regresa la cantidad
     */
    @Override
    public int contar(Object valor) {
        if (!vacia()){
            Nodo iterador = primero;
            int contador=0;
            while(iterador!=null){
                Object contenido = iterador.getContenido();
                if (contenido.toString().equalsIgnoreCase(valor.toString())){
                    contador++;
                }
                iterador = iterador.getNodoDer();
            }
            return contador;
        }else{
            return 0;
        }
    }

    @Override
    public boolean eliminarLista(Object lista2) {
        return false;
    }

    /**
     * Rellena la lista con el elemento la cantidad de veces que se indique como parametro
     * @param valor es el valor a agregar
     * @param cantidad es la cantidad de veces que se agregara dicho valor
     */
    @Override
    public void rellenar(Object valor, int cantidad) {
        for (int indice =0; indice< cantidad;indice++){
            this.agregar(valor);
        }
    }

    /**
     * Crea una copia de la lista actual
     * @return regresa la lista dinamica copia.
     */
    @Override
    public Object clonar() {
        if (!vacia()){
            ListaDinamica copia = new ListaDinamica();
            Nodo iterador = primero;
            while(iterador!=null){
                Object contenido = iterador.getContenido();
                copia.agregar(contenido);
                iterador = iterador.getNodoDer();
            }
            return copia;
        }else{
            return null;
        }
    }

    /**
     * Obtiene el elemento que se encuentre en el indice indicado
     * @param indice es la posicion indicada
     * @return regresa el elemento en dicha posicion si lo encontro
     */
    public Object obtener(int indice){
        if (!vacia()){
            Object retorno = null;
            int contadorIndice=0;
            Nodo iterador = primero;
            while (iterador!= null){
                if (contadorIndice == indice){
                    retorno = iterador.getContenido();
                }
                iterador = iterador.getNodoDer();
                contadorIndice++;
            }
            return retorno;
        }else{
            return null;
        }
    }

    /**
     * Redimensiona la lista segun el maximo indicado recortando los sobrantes o agregando como null los faltantes
     * @param maximo es el nuevo maximo
     * @return regresa la nueva lista dinamica
     */
    public Object redimensionar(int maximo){
        ListaDinamica nueva = new ListaDinamica();
        int contIndices =0;
        Nodo iterador= primero;
        while(contIndices <= maximo-1){
            contIndices++;
            if (iterador!=null){
                Object contenido = iterador.getContenido();
                nueva.agregar(contenido);
                iterador = iterador.getNodoDer();
            }else{
                nueva.agregar(null);
            }
        }
        return nueva;
    }

    @Override
    public Object verUltimo(){
        return ultimo.getContenido();
    }

    public Nodo getPrimerNodo(){
        return primero;
    }

    public Object verPrimero(){
        if(vacia()==false){ //hay algo
            return primero.getContenido();
        }else{
            return null;
        }
    }

    public void inicializarIterador(){
        iterador=primero;
    }

    public boolean hayNodos(){
        if(iterador==null){
            return false;
        }else{
            return true;
        }
    }

    public Object obtenerNodo(){
        if(hayNodos()==true){
            Object contenidoNodo=iterador.getContenido();
            iterador=iterador.getNodoDer();
            return  contenidoNodo;
        }else{ //no hay elementos
            return null;
        }
    }


}
