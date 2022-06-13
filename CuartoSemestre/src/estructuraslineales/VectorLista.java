package estructuraslineales;

public interface VectorLista extends ListaAlmacenamiento{
    /**
     * Indica si la lista alcanzo su tamaño maximo
     * @return regresa true si se alcanzo el tamaño maximo o false si aun queda espacio
     */
    public boolean llena();

    /**
     * Intentara obtener el elemento que se encunetre en el indice indicado como parametro
     * @param indice es el indice en el que se obtendra el elemento
     * @return regresa el elemento en dicho indice o null si el indice es invalido
     */
    public Object obtener(int indice);

    /**
     * Cambiara el valor que ocupe la posicion del indice indicado
     * @param indice esta es la posicion del valor a remplazar
     * @param valor este es el nuevo valor
     * @return regresa un true si se logro hacer el cambio y un false si no se logro
     */
    public boolean cambiar(int indice, Object valor);

    /**
     * Cambiara los valores de la lista estatica enviada como parametro por los valores de la segunda lista
     * @param indicesBusqueda esta es la lista que se remplazara
     * @param valoresNuevos estos son los valores nuevos
     * @return regresa true si se logro hacer el cambio, false si no se pudo
     */
    public boolean cambiarListaEstatica(ListaEstatica indicesBusqueda, ListaEstatica valoresNuevos);

    /**
     * Elimina el elemento que se encuentre en el indice indicado
     * @param indice esta es la posicion del elemento a eliminar
     * @return regresa el elemento eliminado
     */
    public Object eliminar(int indice);

    /**
     * redimensiona el tamaño maximo de nuestra lista
     * @param maximo es el nuevo maximo espacio de la lista
     * @return regresa la lista redimensionada
     */
    public Object redimensionar(int maximo);

    /**
     * Indica el maximo numero de elementos de la lista
     * @return regresa el numero maximo de elementos
     */
    public int maximo();

    /**
     * Indica la cantidad de elementos que se encuentran en la lista
     * @return regresa el numero de elementos que fueron encontrados
     */
    public int numeroElementos();
}
