package estructuraslineales;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.auxiliares.Nodo;
import estructuraslineales.auxiliares.NodoDoble;

public class ListaDinamicaDoble implements ListaAlmacenamiento{
    protected NodoDoble primero;
    protected NodoDoble ultimo;
    protected NodoDoble iterador;
    protected boolean iterCircular;

    public ListaDinamicaDoble(){
        primero=null;
        ultimo=null;
        iterador = null;
        iterCircular = false;
    }

    /**
     * Indica si la lista esta vacia en ese momento
     * @return regresa true si esta vacia y false si no lo esta
     */
    @Override
    public boolean vacia(){
        if(primero==null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Agrega un nuevo valor a la lista
     * @param valor Es el dato que se va a agregar en la lista.
     * @return regresa 0 si se agrego el valor y -1 si no se pudo agregar
     */
    @Override
    public int agregar(Object valor){
        NodoDoble nuevoNodo=new NodoDoble(valor); //paso 1
        if(nuevoNodo!=null){ //hay espacio
            if(vacia()==true){ //a)
                primero=nuevoNodo;//paso 2
                ultimo=nuevoNodo;
            }else{  //b)
                ultimo.setNodoDer(nuevoNodo);//paso 2
                nuevoNodo.setNodoIzq(ultimo);//paso 3
                ultimo=nuevoNodo;//paso 4
            }
            return 0;
        }else{  //no hay espacio
            return -1;
        }
    }

    /**
     * Imprime la lista en el orden natural recorreidno los valores desde el primero hasta el ultimo
     */
    @Override
    public void imprimir(){
        if(vacia()==true){
            SalidaPorDefecto.consola("null\n");
        }else{
            NodoDoble temporal=primero;
            SalidaPorDefecto.consola("null <- ");
            while (temporal!=ultimo){
                SalidaPorDefecto.consola(temporal.getContenido()+ " <-> ");
                temporal=temporal.getNodoDer();
            }
            SalidaPorDefecto.consola(temporal.getContenido()+ " -> null\n");
        }
    }

    /**
     * Imprime la lista en el orden inverso recorriendo los valores desde el ultimo hasta el primero
     */
    @Override
    public void imprimirOI(){
        if(vacia()){
            SalidaPorDefecto.consola("null\n");
        }else{
            NodoDoble temporal=ultimo;
            SalidaPorDefecto.consola("null <- ");
            while (temporal!=primero){
                SalidaPorDefecto.consola(temporal.getContenido()+ " <-> ");
                temporal=temporal.getNodoIzq();
            }
            SalidaPorDefecto.consola(temporal.getContenido()+ " -> null \n");
        }
    }

    /**
     * Busca el un valor indicado dentro de la lista
     * @param valor Es el dato que se va a buscar en la lista
     * @return regresa el valor si lo encuentra y null si no lo encuentra
     */
    @Override
    public Object buscar(Object valor){
        if (!vacia()){
            NodoDoble iterador = primero;
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
     * Elimina un valor de la lista pasado como parametro
     * @param valor Es el elemento que se intentara eliminar de la lista
     * @return regresa el objeto eliminado si lo elimina y null si no se elimino nada
     */
    @Override
    public Object eliminar(Object valor){
        if (!vacia()){
            NodoDoble anterior = null;
            NodoDoble nodoBuscado = primero;
            NodoDoble siguiente = nodoBuscado.getNodoDer();
            while (nodoBuscado!=null && nodoBuscado.getContenido()!= valor){
                nodoBuscado = nodoBuscado.getNodoDer();
                anterior = nodoBuscado.getNodoIzq();
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
                        primero.setNodoIzq(null);
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
     * Indica si la lista actual y la enviada como parametro son iguales
     * @param lista2 es la lista que se vsa a comparar
     * @return regresa true si son iguales y false si no lo son
     */
    @Override
    public boolean esIgual(Object lista2) {
        if (!vacia()){
            if (lista2 instanceof ListaDinamicaDoble){
                ListaDinamicaDoble lista = (ListaDinamicaDoble) lista2;
                int numElemsLista2 =0;
                NodoDoble iteradorLista2 = lista.primero;
                while(iteradorLista2 !=null){
                    numElemsLista2++;
                    iteradorLista2 = iteradorLista2.getNodoDer();
                }
                int contadorIndices =0;
                int contadorIguales =0;
                NodoDoble iterador = primero;
                while(iterador !=null){
                    if (iterador.getContenido().toString().equalsIgnoreCase(lista.obtener(contadorIndices).toString())){
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

    /**
     * Obtiene el elemento que se encuentre en la posicion indicada
     * @param indice es la posicion a buscar
     * @return regresa el objeto si lo encuentra o null si no
     */
    public Object obtener(int indice){
        if (!vacia()){
            Object retorno = null;
            int contadorIndice=0;
            NodoDoble iterador = primero;
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
     * cambiamos un elemento por un elemento nuevo la cantidad de veces que se indique
     * @param valorViejo este es el valor que sera reemplazado
     * @param valorNuevo este es el valor a colocar
     * @param numVeces este es el numero de veces en que se reemplazara
     * @return regresa true si se pudo cambiar y false si no se pudo
     */
    @Override
    public boolean cambiar(Object valorViejo, Object valorNuevo, int numVeces) {
        if (!vacia()){
            int contadorVeces=0;
            NodoDoble iterador = primero;
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
     * Obtiene todos los elementos repetidos del elemento indicado
     * @param valor es el valor a buscar
     * @return regresa una lista estatica con las concurrencias del elemento
     */
    @Override
    public ListaEstatica buscarValores(Object valor) {
        if (!vacia()){
            int contadorElementos =0;
            NodoDoble iterador = primero;
            while (iterador!=null){
                Object contenido = iterador.getContenido();
                if (contenido.toString().equalsIgnoreCase(valor.toString())){
                    contadorElementos++;
                }
                iterador = iterador.getNodoDer();
            }
            iterador = primero;
            ListaEstatica elementos = new ListaEstatica(contadorElementos);
            while (iterador!=null){
                Object contenido = iterador.getContenido();
                if (contenido.toString().equalsIgnoreCase(valor.toString())){
                    elementos.agregar(contenido);
                }
                iterador = iterador.getNodoDer();
            }
            return elementos;
        }else{
            return null;
        }
    }

    /**
     * Elimina el ultimo elemento de la lista
     * @return regresa el objeto eliminado si se elimino y null si no se pudo eliminar
     */
    @Override
    public Object eliminar(){
        if(vacia()==false){ //hay elementos
            Object contenidoEliminado=ultimo.getContenido();//paso 1
            if(primero==ultimo){   //b) único
                //Object contenidoEliminado=ultimo.getContenido();//paso 1
                primero=null;
                ultimo=null;//paso 2
            }else{    //c)  varios
                //Object contenidoEliminado=ultimo.getContenido();//paso 1
                ultimo=ultimo.getNodoIzq(); //paso 2
                ultimo.setNodoDer(null);//paso 3
            }
            return contenidoEliminado;
        }else {   //a)
            return null;
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

    /**
     * Agrega una lista estatica, dinamica, o dinamica doble a la lista actual
     * @param lista2 es la lista que se agregara al final
     * @return regresa true si se pudo agregar y false si no se pudo
     */
    @Override
    public boolean agregarLista(Object lista2) {
        if (!vacia()){
            if (lista2 instanceof ListaEstatica){
                ListaEstatica lista = (ListaEstatica) lista2;
                for (int pos=0; pos< lista.numeroElementos(); pos++){
                    this.agregar(lista.obtener(pos));
                }
                return true;
            }else{
                if (lista2 instanceof  ListaDinamica){
                    ListaDinamica lista = (ListaDinamica) lista2;
                    Nodo iterador = lista.primero;
                    while (iterador!= null){
                        Object contenido = iterador.getContenido();
                        this.agregar(contenido);
                        iterador = iterador.getNodoDer();
                    }
                    return true;
                }else{
                    if (lista2 instanceof  ListaDinamicaDoble){
                        ListaDinamicaDoble lista = (ListaDinamicaDoble) lista2;
                        NodoDoble iterador = lista.primero;
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
            }
        }else{
            return false;
        }
    }

    /**
     * Invierte el orden de la lista actual
     */
    @Override
    public void invertir() {
        PilaDinamica pilaContenido = new PilaDinamica();
        NodoDoble iterador = primero;
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
     * Cuenta la cantidad de veces que un elemento esta en la lista
     * @param valor este es el elemento a contar
     * @return regresa el numero de ocurrencias
     */
    @Override
    public int contar(Object valor) {
        if (!vacia()){
            NodoDoble iterador = primero;
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

    /**
     * Elimina una lista ya sea estatica, dinamica o dinamica doble
     * @param lista2 es la lista que se va a eliminar
     * @return regresa true si almenos un elemento de la lista se elimina y false si no se elimino ninguno
     */
    @Override
    public boolean eliminarLista(Object lista2) {
        int contElems=0;
        if (!vacia()){
            if (lista2 instanceof ListaEstatica){
                ListaEstatica lista = (ListaEstatica) lista2;
                for (int pos=0; pos< lista.numeroElementos(); pos++){
                    if (this.eliminar(lista.obtener(pos)) !=null){
                        contElems++;
                    }
                }
                if (contElems>0){
                    return true;
                }else{
                    return false;
                }
            }else{
                if (lista2 instanceof  ListaDinamica){
                    ListaDinamica lista = (ListaDinamica) lista2;
                    Nodo iterador = lista.primero;
                    while (iterador!= null){
                        Object contenido = iterador.getContenido();
                        if (this.eliminar(contenido) !=null){
                            contElems++;
                        }
                        iterador = iterador.getNodoDer();
                    }
                    if (contElems>0){
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    if (lista2 instanceof  ListaDinamicaDoble){
                        ListaDinamicaDoble lista = (ListaDinamicaDoble) lista2;
                        NodoDoble iterador = lista.primero;
                        while (iterador!= null){
                            Object contenido = iterador.getContenido();
                            if (this.eliminar(contenido) !=null){
                                contElems++;
                            }
                            iterador = iterador.getNodoDer();
                        }
                        if (contElems>0){
                            return true;
                        }else{
                            return false;
                        }
                    }else{
                        return false;
                    }
                }
            }
        }else{
            return false;
        }
    }

    /**
     * Agrega un valor a la lista la cantidad de veces indicada
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
     * Clona la lista actual en una nueva lista
     * @return regresa la lista actual
     */
    @Override
    public Object clonar() {
        if (!vacia()){
            ListaDinamicaDoble copia = new ListaDinamicaDoble();
            NodoDoble iterador = primero;
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
     * obtiene el contenido del ultimo nodo de la lista
     * @return regresa el contenido de ultimo
     */
    @Override
    public Object verUltimo(){
        return ultimo.getContenido();
    }

    /**
     * Agrega un elemento al inicio de la lista
     * @param valor es el valor del nuevo primer elemento
     * @return regresa 0 si se pudo agregar y -1 si no se pudo
     */
    public int agregarInicio(Object valor){
        NodoDoble nuevoNodo=new NodoDoble(valor);
        if(nuevoNodo!=null){
            if(vacia()){
                primero=nuevoNodo;
                ultimo=nuevoNodo;
            }else{
                primero.setNodoIzq(nuevoNodo);
                nuevoNodo.setNodoDer(primero);
                primero=nuevoNodo;

            }
            return 0;
        }else{
            return -1;
        }
    }

    /**
     * Elimina un elemento al inicio de la lista
     * @return regresa el objeto si se elimino, y null si no se pudo eliminar
     */
    public Object eliminarInicio(){
        if(!vacia()){
            Object contenidoEliminado=primero.getContenido();
            if(primero==ultimo){
                primero=null;
                ultimo=null;
            }else{
                primero.getNodoDer().setNodoIzq(null);
                primero=primero.getNodoDer();
            }
            return contenidoEliminado;
        }else{
            return null;
        }
    }

    /**
     * Buscamos un elemento partiendo desde el ultimo elemento hasta el primero
     * @param valor es el valor a buscar
     * @return regresa el elemento si lo encontro o null si no lo encontro
     */
    public Object buscarOI(Object valor){
        if (!vacia()){
            NodoDoble iterador = ultimo;
            while(iterador!=null){
                Object contenido = iterador.getContenido();
                if (contenido.toString().equalsIgnoreCase(valor.toString())){
                    return contenido;
                }
                iterador = iterador.getNodoIzq();
            }
            return null;
        }else{
            return null;
        }
    }

    /**
     * Inicializa el iterador en el inicio de la lista
     * @param circular indica si el iterador sera o no circular
     * @return regresa objeto en la posicion actual
     */
    public Object iniciarIteradorAlInicio(boolean circular){
        iterador = primero;
        iterCircular = circular;
        return iterador.getContenido();
    }

    /**
     * Inicializa el iterador en el final de la lista
     * @param circular indica si el iterador sera o no circular
     * @return regresa objeto en la posicion actual
     */
    public Object iniciarIteradorAlFinal(boolean circular){
        iterador = ultimo;
        iterCircular = circular;
        return iterador.getContenido();
    }

    /**
     * mueve el iterador a la derecha
     * @return regresa el valor en la posicion actual
     */
    public Object moverIteradorDerecha(){
        if (iterador!=null){
            if (iterador == ultimo){
                if (iterCircular){
                    iterador = primero;
                    return iterador.getContenido();
                }else{
                    return iterador.getContenido();
                }
            }else{
                iterador = iterador.getNodoDer();
                return iterador.getContenido();
            }
        }else{
            return null;
        }
    }

    /**
     * mueve el iterador a la izquierda
     * @return regresa el valor en la posicion actual
     */
    public Object moverIteradorIzquierda(){
        if (iterador!=null){
            if (iterador == primero){
                if (iterCircular){
                    iterador = ultimo;
                    return iterador.getContenido();
                }else{
                    return iterador.getContenido();
                }
            }else{
                iterador = iterador.getNodoIzq();
                return iterador.getContenido();
            }
        }else{
            return null;
        }
    }

    public NodoDoble getPrimerNodo(){
        return primero;
    }

    public int numeroElementos(){
        if (!vacia()){
            int num =0;
            NodoDoble iterador =  primero;
            while (iterador != null){
                num++;
                iterador = iterador.getNodoDer();
            }
            return num;
        }else{
            return 0;
        }
    }
}
