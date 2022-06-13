package estructuraslineales;
import enumerados.TipoOrden;
import enumerados.TipoTabla;
import estructuraslineales.auxiliares.Nodo;
import estructurasnolineales.Matriz2;
import operaciones.CompararDatos;

public class ListaDinamicaOrdenada extends ListaDinamica {
    TipoOrden tipoOrdenLista;

    /**
     * Constructor para la listaDinamicaOrdenada
     * @param orden ordenamiento, incremental o decremental
     */
    public ListaDinamicaOrdenada(TipoOrden orden) {
        super();
        this.tipoOrdenLista = orden;
    }

    /**
     * agrega un valor a la lista en caso de no ser encontrado previamente
     * @param valor es el valor a agregar
     * @return regresa 0 si se agrego, y -1 si no se pudo agregar
     */
    @Override
    public int agregar(Object valor){
        Object busqueda = buscar(valor);
        if (busqueda ==null){
            int resultado= super.agregar(valor);
            if (tipoOrdenLista.getValor() ==1){
                comparacionesASC();
            }else{
                comparacionesDSC();
            }
            return resultado;
        }else{ //No se admiten valores repetidos en una lista ordenada
            return -1;
        }
    }

    /**
     * Agrega un valor a la lista sin considerar el orden, solo lo coloca en el final
     * @param valor es el valor a agregar
     * @return regresa cero si se pudo agregar y -1 si no se pudo
     */
    public int agregarFinal(Object valor){
        Object busqueda = buscar(valor);
        if (tipoOrdenLista.getValor() ==1){
            comparacionesASC();
        }else{
            comparacionesDSC();
        }
        if (busqueda ==null){
            return super.agregar(valor);
        }else{
            return -1;
        }
    }

    /**
     * Agrega un valor a la lista en la primera posicion
     * @param valor es el valor a agregar
     * @return regresa un cero si se agrego y un -1 si no se pudo agregar
     */
    @Override
    public int agregarInicio(Object valor){
        Object busqueda = buscar(valor);
        if (tipoOrdenLista.getValor() ==1){
            comparacionesASC();
        }else{
            comparacionesDSC();
        }
        if (busqueda ==null){
            return super.agregarInicio(valor);
        }else{
            return -1;
        }
    }

    /**
     * Elimina un valor indicado como parametro
     * @param valor Es el valor a eliminar
     * @return Regresa el elemento eliminado si se pudo eliminar, y un null si no se pudo
     */
    @Override
    public Object eliminar(Object valor){
        Object resultado = super.eliminar(valor);
        if (tipoOrdenLista.getValor() ==1){
            comparacionesASC();
        }else{
            comparacionesDSC();
        }
        return resultado;
    }

    /**
     * Elimina el ultimo elemento de la lista
     * @return regresa el elemento eliminado o null si no lo pudo eliminar
     */
    @Override
    public Object eliminar(){
        Object resultado = super.eliminar();
        if (resultado != null){
            if (tipoOrdenLista.getValor() ==1){
                comparacionesASC();
            }else{
                comparacionesDSC();
            }
            return resultado;
        }else{
            return null;
        }
    }

    /**
     * Elimina el primer elemento de la lista
     * @return regresa el elemento eliminado o null si no se pudo eliminar
     */
    @Override
    public Object eliminarInicio(){
        Object resultado = super.eliminarInicio();
        if (resultado !=null){
            if (tipoOrdenLista.getValor() ==1){
                comparacionesASC();
            }else{
                comparacionesDSC();
            }
            return resultado;
        }else{
            return null;
        }
    }

    /**
     * Busca el valor dentro de la lista dinamica ordenada, comparando ya sea entero o no
     * @param valor es el valor que se va a buscar
     * @return regresa el elemento si se encontro y null si no se encontro
     */
    @Override
    public Object buscar(Object valor){
        return super.buscar(valor);
    }

    /**
     * Agrega datos pasados en forma de arreglo, pueden estar en orden o desordenados
     * @param listaDatos2 es la lista a agregar
     * @return regresa true si se pudo agregar y false si no se pudo
     */
    @Override
    public boolean agregarLista(ListaAlmacenamiento listaDatos2){
        boolean resultado = super.agregarLista(listaDatos2);
        if (tipoOrdenLista.getValor() ==1){
            comparacionesASC();
        }else{
            comparacionesDSC();
        }
        return resultado;
    }

    /**
     * Agrega una matriz a la lista dinamica, puede estar ordenada o desordenada
     * @param tabla es la matriz a agregar
     * @param enumTipoTabla es el enumerado que nos dice si se agrega por renglon o columna
     * @return regresa true si se pudo agregar o false si no se pudo
     */
    @Override
    public boolean agregarMatriz2D(Matriz2 tabla, TipoTabla enumTipoTabla){
        boolean resultado = super.agregarMatriz2D(tabla,enumTipoTabla);
        if (tipoOrdenLista.getValor() ==1){
            comparacionesASC();
        }else{
            comparacionesDSC();
        }
        return resultado;
    }

    /**
     * Elimina un conjunto de datos pasados como arreglo (ListaEstatica) (Solo los que existan)
     * @param lista son los elementos a eliminar
     */
    public void eliminarLista(ListaEstatica lista){
        if (!vacia()) {
            for (int pos = 0; pos < lista.numeroElementos(); pos++) {
                this.eliminar(lista.obtener(pos));
            }
            if (tipoOrdenLista.getValor() ==1){
                comparacionesASC();
            }else{
                comparacionesDSC();
            }
        }
    }

    /**
     * Elimina los elementos pasados en forma de matriz (solo los que existan)
     * @param matriz es la matriz con los elementos a eliminar
     */
    public void eliminarMatriz2D(Matriz2 matriz){
        if (!vacia()){
            for (int fila =0; fila< matriz.obtenerRenglones(); fila++){
                for (int col =0; col< matriz.obtenerColumnas(); col++){
                    this.eliminar(matriz.obtener(fila,col));
                }
            }
            if (tipoOrdenLista.getValor() ==1){
                comparacionesASC();
            }else{
                comparacionesDSC();
            }
        }
    }

    /**
     * Remplaza un valor viejo por un valor nuevo y reordena la lista dinamica
     * @param valorViejo es el valor a reemplazar
     * @param valorNuevo es el valor a colocar
     * @param numOcurrencias cantidad de veces que se colocara (siempre es 1)
     * @return regresa true si se pudo realizar el cambio, false si no se pudo
     */
    @Override
    public boolean cambiar(Object valorViejo, Object valorNuevo, int numOcurrencias){
        if (numOcurrencias==1){
            boolean resultado = super.cambiar(valorViejo,valorNuevo,numOcurrencias);
            if (tipoOrdenLista.getValor() ==1){
                comparacionesASC();
            }else{
                comparacionesDSC();
            }
            return  resultado;
        }else{
            return false;
        }
    }

    /**
     * realiza las comparacion de tamanios de los elementos de forma ascendente
     */
    public void comparacionesASC() {
        if (!vacia()){
            Nodo iterador = primero;
            int numeroElementos = 0;
            while (iterador != null) {
                numeroElementos++;
                iterador = iterador.getNodoDer();
            }
            iterador = primero;
            ListaEstatica elementos = new ListaEstatica(numeroElementos);
            while (iterador != null) {
                Object contenido = iterador.getContenido();
                elementos.agregar(contenido);
                iterador = iterador.getNodoDer();
            }
            if (numeroElementos>1){
                for (int posicion = 0; posicion < numeroElementos; posicion++) {
                    for (int posicion2 = 0; posicion2 < numeroElementos-posicion-1; posicion2++) {
                        if(CompararDatos.objetoMenor(elementos.datos[posicion2+1],elementos.datos[posicion2])){
                            Object memoria = elementos.datos[posicion2+1];
                            elementos.datos[posicion2+1] = elementos.datos[posicion2];
                            elementos.datos[posicion2] = memoria;
                        }
                    }
                }
            }

            this.vaciar();
            for (int pos=0; pos<numeroElementos; pos++){
                super.agregar(elementos.obtener(pos));
            }
        }
    }

    /**
     * realiza las comparaciones de tamanios de los elementos de forma descendente
     */
    public void comparacionesDSC(){
        if (!vacia()){
            Nodo iterador = primero;
            int numeroElementos = 0;
            while (iterador != null) {
                numeroElementos++;
                iterador = iterador.getNodoDer();
            }
            iterador = primero;
            ListaEstatica elementos = new ListaEstatica(numeroElementos);
            while (iterador != null) {
                Object contenido = iterador.getContenido();
                elementos.agregar(contenido);
                iterador = iterador.getNodoDer();
            }
            if (numeroElementos>1){
                for (int posicion = 0; posicion < numeroElementos; posicion++) {
                    for (int posicion2 = 0; posicion2 < numeroElementos-posicion-1; posicion2++) {
                        if(CompararDatos.objetoMayor(elementos.datos[posicion2+1],elementos.datos[posicion2])){
                            Object memoria = elementos.datos[posicion2+1];
                            elementos.datos[posicion2+1] = elementos.datos[posicion2];
                            elementos.datos[posicion2] = memoria;
                        }
                    }
                }
            }

            this.vaciar();
            for (int pos=0; pos<numeroElementos; pos++){
                super.agregar(elementos.obtener(pos));
            }
        }
    }

    /**
     * Invierte el orden de la lista y cambia el tipo de orden de la misma
     */
    @Override
    public void invertir(){
        if (tipoOrdenLista.getValor()==1){
            tipoOrdenLista = TipoOrden.DECR;
            comparacionesASC();
        }else{
            tipoOrdenLista = TipoOrden.INCR;
            comparacionesDSC();
        }
        if (tipoOrdenLista.getValor() ==1){
            comparacionesASC();
        }else{
            comparacionesDSC();
        }
    }

    /**
     * Clona la lista actual en una nueva independiente
     * @return regresa la lista clonada
     */
    public Object clonar(){
        return super.clonar();
    }

    /**
     * Indica si la lista actual y la pasada como argumento son iguales
     * @param lista2 es la lista a comparar
     * @return regresa true si son iguales, false si no lo son
     */
    @Override
    public boolean esIgual(Object lista2){
        return super.esIgual(lista2);
    }

    /**
     * busca un valor y lo regresa si lo encuentra
     * @param valor es el valor a buscar
     * @return regresa una lista estatica que contiene el valor
     */
    @Override
    public ListaEstatica buscarValores(Object valor) {
        if (!vacia()){
            ListaEstatica elemento = new ListaEstatica(1);
            Nodo iterador = primero;
            while (iterador!=null){
                Object contenido = iterador.getContenido();
                if (contenido.toString().equalsIgnoreCase(valor.toString())){
                    elemento.agregar(contenido);
                }
                iterador = iterador.getNodoDer();
            }
            return elemento;
        }else{
            return null;
        }
    }

    /**
     * Vacia la lista por completo
     */
    @Override
    public void vaciar(){
        super.vaciar();
    }

    /**
     * Cuenta las ocurrencias de el elemento (en este caso siempre sera uno si lo encuentra ya que no se admiten duplicados)
     * @param valor este es el elemento a contar
     * @return regresa 1 si el elemento existe y 0 si no existe en la lista
     */
    @Override
    public int contar(Object valor){
        return super.contar(valor);
    }

    /**
     * Rellena la lista con el valor indicado
     * @param valor es el valor a agregar
     * @param cantidad es la cantidad de veces que se agregara dicho valor (en este caso se ignora y siempre sera 1)
     */
    @Override
    public void rellenar(Object valor, int cantidad){
        super.rellenar(valor,1);
    }

    /**
     * Muestra el ultimo elemento actual de la lista
     * @return regresa el elemento
     */
    @Override
    public Object verUltimo(){
        return super.verUltimo();
    }
}
