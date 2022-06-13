package estructuraslineales;
import enumerados.TipoOrden;
import operaciones.CompararDatos;
import java.util.Random;

public class ListaEstaticaOrdenada extends ListaEstatica {
    TipoOrden tipoOrdenLista;

    /**
     * Constructor para la listaEstaticaOrdenada
     * @param maximo maximo de valores de la lista
     * @param orden ordenamiento, incremental o decremental
     */
    public ListaEstaticaOrdenada(int maximo, TipoOrden orden) {
        super(maximo);
        this.tipoOrdenLista = orden;
    }

    /**
     * agrega un valor a la lista en caso de no ser encontrado previamente
     * @param valor es el valor a agrgar
     * @return regresa la posicion en que se agrego si se agrego, y -1 si no se pudo agregar
     */
    @Override
    public int agregar(Object valor){
        Object busqueda = buscar(valor);
        if (busqueda ==null){
            int resultado= super.agregar(valor);
            if (tipoOrdenLista.getValor() ==1){
                reordenar();
            }else{
                reordenarOI();
            }
            return resultado;
        }else{ //No se admiten valores repetidos en una lista ordenada
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
            reordenar();
        }else{
            reordenarOI();
        }
        return resultado;
    }

    /**
     * Busca el valor dentro de la lista ordenada, comparando ya sea entero o no
     * @param valor es el valor que se va a buscar
     * @return regresa numeros negativos si no se encontro y positivos si si se encontro
     */
    @Override
    public Object buscar(Object valor){
        Object resultado = super.buscar(valor);
        if (tipoOrdenLista.getValor() ==1){
            reordenar();
        }else{
            reordenarOI();
        }
        return resultado;
    }

    /**
     * Remplaza un valor viejo por un valor nuevo y reordena el arreglo
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
                reordenar();
            }else{
                reordenarOI();
            }
            return  resultado;
        }else{
            return false;
        }
    }

    /**
     * Reordena el arreglo para que al cambiar un elemento mantenga el orden esperado
     */
    public void reordenar(){
        for (int posicion = 0; posicion < numeroElementos(); posicion++) {
            for (int posicion2 = 0; posicion2 < numeroElementos()-posicion-1; posicion2++) {
                if(!CompararDatos.objetoMayor(datos[posicion2],datos[posicion2+1])){
                    Object memoria = datos[posicion2+1];
                    datos[posicion2+1] = datos[posicion2];
                    datos[posicion2] = memoria;
                }
            }
        }
    }

    /**
     * Reordena el arreglo para que al cambiar un elemento mantenga el orden esperado
     */
    public void reordenarOI(){
        for (int posicion = 0; posicion < numeroElementos(); posicion++) {
            for (int posicion2 = 0; posicion2 < numeroElementos()-posicion-1; posicion2++) {
                if(CompararDatos.objetoMayor(datos[posicion2],datos[posicion2+1])){
                    Object memoria = datos[posicion2+1];
                    datos[posicion2+1] = datos[posicion2];
                    datos[posicion2] = memoria;
                }
            }
        }
    }

    @Override
    public boolean agregarLista(Object lista2){
        boolean resultado = super.agregarLista(lista2);
        if (tipoOrdenLista.getValor() ==1){
            reordenar();
        }else{
            reordenarOI();
        }
        return resultado;
    }

    /**
     * Invierte el orden de la lista y cambia el tipo de orden de la misma
     */
    @Override
    public void invertir(){
       if (tipoOrdenLista.getValor()==1){
           tipoOrdenLista = TipoOrden.DECR;
           reordenarOI();
       }else{
           tipoOrdenLista = TipoOrden.INCR;
           reordenar();
       }
    }

    /**
     * Rellena la lista usando el valor enviado como parametro a modo de limite
     * @param valor
     */
    public void rellenar(Object valor){
        if (valor instanceof  Integer){
            if ((int)valor < 0){
                int negativo = (int) valor;
                for (int posicion = ultimo; posicion< numeroElementos(); posicion++){
                    if (tipoOrdenLista.getValor()==1){
                        agregar(negativo);
                        negativo++;
                    }else{
                        agregar(negativo+posicion);
                    }
                }
            }else{
                int valorPositivo = (int) valor;
                for (int posicion = ultimo; posicion<numeroElementos();posicion++ ){
                    if (tipoOrdenLista.getValor() == 1){
                        agregar(valorPositivo-ultimo);
                    }else{
                        agregar(valorPositivo);
                        valorPositivo--;
                    }
                }
            }
        }else{
            agregar(valor);
        }
    }

    /**
     * Indica si una lista es un conjunto de la otra
     * @param lista2 es la lista a evaluar
     * @return regresa true si es sublista y false si no lo es
     */
    @Override
    public boolean esSublista(ListaAlmacenamiento lista2){
        ListaEstaticaOrdenada lista = (ListaEstaticaOrdenada) lista2;
        int numOcurrencias=0;
        if (lista.tipoOrdenLista.getValor()==1){
            reordenar();
        }else{
            reordenarOI();
        }
        for (int posicion =0; posicion< numeroElementos();posicion++){
            for (int posicion2 =0; posicion2< lista.numeroElementos(); posicion2++){
                if ( lista.datos[posicion2].toString().equalsIgnoreCase(datos[posicion].toString())){
                    numOcurrencias++;
                }
            }
        }
        if (numOcurrencias==lista.numeroElementos()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Desordena una lista ordenada usando un generador de posiciones aleatorias
     * @return regresa la lista desordenada
     */
    public ListaAlmacenamiento arregloDesordenado(){
        Random r = new Random();
        for (int posicion=0; posicion<numeroElementos(); posicion++) {
            int posicionAlea = r.nextInt(numeroElementos()); //dentro del rango
            Object memoria = datos[posicion];
            datos[posicion] = datos[posicionAlea];
            datos[posicionAlea] = memoria;
        }
        return this;
    }

    /**
     * Cambia todos los elementos de la lista1 que esten en la lista, por los de la lista 2
     * @param lista1 son los valores a reemplazar
     * @param lista2 son los valores nuevos
     * @return regresa true si se puede realizar la operacion
     */
    public boolean cambiarLista(ListaAlmacenamiento lista1, ListaAlmacenamiento lista2){
        ListaEstaticaOrdenada leo1 = (ListaEstaticaOrdenada) lista1;
        ListaEstaticaOrdenada leo2 = (ListaEstaticaOrdenada) lista2;
        if (leo1.numeroElementos() == leo2.numeroElementos()){
            for (int posicion = 0; posicion<leo1.numeroElementos(); posicion++){
                cambiar(leo1.datos[posicion],leo2.datos[posicion],1);
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * Deja solamente los elementos que se encuentran en la lista de la lista2
     * @param lista2 son los elementos a retener
     * @return regresa la lista resultante
     */
    public boolean retenerLista(ListaAlmacenamiento lista2) {
        ListaEstaticaOrdenada lista = (ListaEstaticaOrdenada) lista2;
        if (lista.numeroElementos()< numeroElementos()){
            this.vaciar();
            for (int posicion =0; posicion<lista.numeroElementos();posicion++){
                agregar(lista.datos[posicion]);
            }
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean copiarLista(ListaAlmacenamiento lista2){
        ListaEstaticaOrdenada lista = (ListaEstaticaOrdenada) lista2;
        if (lista.tipoOrdenLista.getValor() == this.tipoOrdenLista.getValor()){//Si respeta el orden
            super.copiarLista(lista2);
            return true;
        }else{
            return false;
        }
    }

    public Object[] getDatos(){
        return datos;
    }
}
