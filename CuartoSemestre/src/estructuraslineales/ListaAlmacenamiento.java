package estructuraslineales;

/**
 * Esta interfaz gestiona la funcionalidad de una lista de almacenamiento.
 * @author Clase ED - Adalberto Cerrillo Vazquez 4B.
 * @version 1.1
 */
public interface ListaAlmacenamiento {

    /**
     * Determina si una lista de almacenamiento esta vacia.
     * @return Regresa <b>true</b> si la lista esta vacia, <b>false</b> en caso contrario.
     */
    public boolean vacia();

    /**
     * Inserta al final de la lista el elemento especificados como argumento.
     * @param valor Es el dato que se va a agregar en la lista.
     * @return Regresa la posicion en memoria(indice) en donde se agrega el valor, o -1 en caso que no se haya podido insertar.
     */
    public int agregar(Object valor);

    /**
     * Imprime nuestra lista Estatica en orden normal (como se puede imaginrar en memoria)
     */
    public void imprimir();

    /**
     * Imprime nuestra lista Estatica en orden inverso
     */
    public void imprimirOI();

    /**
     * Busca en la lista un elemento especificado como argumento
     * @param valor Es el dato que se va a buscar en la lista
     * @return Regresa la posicion en donde se encuentra el valor de haberlo encontrado o null si no se encontro
     */
    public Object buscar(Object valor);

    /**
     * Intenta Eliminar de la lista un elemento especificado como argumento
     * @param valor Es el elemento que se intentara eliminar de la lista
     * @return Regresa el elemento eliminado si lo pudo eliminar o null si no se ha podido eliminar
     */
    public Object eliminar(Object valor);

    /**
     * Indica si la lista enviada como parametro es igual a una primera lista
     * @param lista2 es la lista que se vsa a comparar
     * @return si las listas son iguales se devuelve un true, si no son iguales se devuelve un false
     */
    public boolean esIgual(Object lista2);

    /**
     * Cambia el valor viejo indicado por un valor nuevo la cantidad de ocurrencias indicadas
     * @param valorViejo este es el valor que sera reemplazado
     * @param valorNuevo este es el valor a colocar
     * @param numVeces este es el numero de veces en que se reemplazara
     * @return regresa true si se hicieron modificaciones y false si no fue posible
     */
    public boolean cambiar(Object valorViejo, Object valorNuevo, int numVeces);

    /**
     * Busca un valor indicado como parametro y todas sus ocurrencias
     * @param valor es el valor a buscar
     * @return regresa una ListaEstatica con el o los indices en donde esta el valor
     */
    public ListaEstatica buscarValores(Object valor);

    /**
     * Elimina el elemento en la ultima posicion de la lista
     * @return regresa el elemento eliminado
     */
    public Object eliminar();

    /**
     * Vacia el contenido de la lista estatica
     */
    public void vaciar();

    /**
     * Agrega una lista al final de lista actual
     * @param lista2 es la lista que se agregara al final
     * @return regresa true si la operacion fue posible y false si no fue posible
     */
    public boolean agregarLista(Object lista2);

    /**
     * Invierte el orden de los elementos de la lista
     */
    public void invertir();

    /**
     * Indica cuantos elementos del indicado como parametro se repiten en la lista
     * @param valor este es el elemento a contar
     * @return regresa el numero de veces que el numero fue encontrado
     */
    public int contar(Object valor);

    /**
     * Eliminara cada elemento de la lista2 que se encuentre en la lista actual
     * @param lista2 es la lista que se va a eliminar
     * @return regresara true si se hicieron modificaciones y false si no se hicieron
     */
    public boolean eliminarLista(Object lista2);

    /**
     * Rellena la lista con la cantidad indicada de elementos iguales al valor dado como parametro
     * @param valor es el valor a agregar
     * @param cantidad es la cantidad de veces que se agregara dicho valor
     */
    public void rellenar(Object valor, int cantidad);

    /**
     * Crea una copia de la lista
     * @return Regresa la lista clonada
     */
    public Object clonar();

    public Object verUltimo();
}
