package estructuraslineales;

import entradasalida.SalidaPorDefecto;


public class ListaEstatica implements VectorLista{
    protected int MAXIMO;
    protected int ultimo;
    protected Object datos[];


    public ListaEstatica(int maximo){
        MAXIMO=maximo;
        datos=new Object[MAXIMO];
        ultimo=-1;
    }

    @Override
    public boolean vacia() {
        if(ultimo==-1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean llena() {
        if(ultimo== (MAXIMO-1)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Object obtener(int indice) {
        if (indice <= ultimo && indice >=0){
            return datos[indice];
        }else{
            return null;
        }
    }

    @Override
    public boolean cambiar(int indice, Object valor) {
        if (indice >=0 && indice <=ultimo){
            datos[indice] = valor;
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean cambiarListaEstatica(ListaEstatica indicesBusqueda, ListaEstatica valoresNuevos) {
        ListaEstatica indices = (ListaEstatica) indicesBusqueda;
        ListaEstatica valoresaColocar = (ListaEstatica) valoresNuevos;

        if (indices.ultimo == valoresNuevos.ultimo){
            if (ultimo >= indices.ultimo){
                for (int posicion =0; posicion<= indices.ultimo; posicion++){
                    datos[(int) indices.datos[posicion]] = valoresNuevos.datos[posicion];
                }
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public Object eliminar(int indice) {
        if (indice>=0 && indice<=ultimo){
            Object elemento = datos[indice];
            this.eliminar(elemento);
            return elemento;
        }else{
            return null;
        }
    }

    @Override
    public Object redimensionar(int maximo) {
        ListaEstatica redimensionada = new ListaEstatica(maximo);
        if ((maximo-MAXIMO)>=0){
            if ((maximo-MAXIMO)==0){
                return this;
            }
            for (int posicion =0; posicion<=ultimo; posicion++){
                redimensionada.agregar(datos[posicion]);
            }
            return redimensionada;
        }else{
            for (int posicion=0; posicion<=maximo; posicion++){
                redimensionada.agregar(datos[posicion]);
            }
            return redimensionada;
        }
    }

    @Override
    public int maximo() {
        return this.MAXIMO;
    }

    @Override
    public int numeroElementos() {
        return (ultimo+1);
    }

    @Override
    public int agregar(Object valor) {
        if(llena()==false){ //hay espacio
            ultimo=ultimo+1;
            datos[ultimo]=valor;
            return ultimo;
        }else{ //no hay espacio
            return -1;
        }
    }

    @Override
    public void imprimir() {
        for(int posicion=ultimo;posicion>=0;posicion--){
            SalidaPorDefecto.consola(datos[posicion]+ "\n");
        }
    }

    @Override
    public void imprimirOI() {
        for(int posicion=0;posicion<=ultimo;posicion++){
            SalidaPorDefecto.consola(datos[posicion]+ "\n");
        }
    }

    @Override
    public Object buscar(Object valor) {
        int posicion=0;
        //buscamos mientras haya donde buscar y mientras no lo encontremos
        while(posicion<=ultimo && !valor.toString().equalsIgnoreCase(datos[posicion].toString())){
            posicion=posicion+1;
        }
        if(posicion>ultimo){ //no loencontramos
            return null;
        }else{  //si lo encontramos
            return posicion;
        }
    }

    @Override
    public Object eliminar(Object valor) {
        Integer posicion=(Integer)buscar(valor);
        if(posicion!=null){ //si lo encontramos
            Object valorEliminado=datos[posicion];
            ultimo--;
            for(int movimiento=posicion;movimiento<=ultimo;movimiento++){
                datos[movimiento]=datos[movimiento+1];
            }
            return valorEliminado;
        }else{  //no lo encontramos
            return null;
        }
    }

    @Override
    public boolean esIgual(Object lista2) {
        ListaEstatica lista = (ListaEstatica) lista2;
        int contadorCoincidencias = 0;
        for (int posicion =0; posicion<=ultimo; posicion++){
            if (datos[posicion].toString().equalsIgnoreCase(lista.datos[posicion].toString())){
                contadorCoincidencias++;
            }
        }
        if (contadorCoincidencias == ultimo+1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean cambiar(Object valorViejo, Object valorNuevo, int numVeces) {
        int contadorModificaciones =0;
        for (int posicion =0; posicion <= ultimo; posicion++){
            if (contadorModificaciones< numVeces){
                if (datos[posicion].toString().equalsIgnoreCase(valorViejo.toString())){
                    datos[posicion] = valorNuevo;
                    contadorModificaciones++;
                }
            }
        }
        if (contadorModificaciones ==0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public ListaEstatica buscarValores(Object valor) {
        ListaEstatica listaRetorno = new ListaEstatica(MAXIMO);
        for (int posicion =0; posicion<=ultimo; posicion++){
            if (datos[posicion].toString().equalsIgnoreCase(valor.toString())){
                listaRetorno.agregar(posicion);
            }
        }
        return listaRetorno;
    }

    @Override
    public Object eliminar() {
        Object elemento = datos[ultimo];
        this.eliminar(ultimo);
        return elemento;
    }

    @Override
    public void vaciar() {
        for (int posicion=ultimo; posicion>=0; posicion--){
            this.eliminar();
        }
    }

    @Override
    public boolean agregarLista(Object lista2) {
        try{
            ListaEstatica listaAgregar = (ListaEstatica) lista2;
            for (int posicion = 0 ; posicion<=listaAgregar.ultimo; posicion++){
                this.agregar(listaAgregar.datos[posicion]);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public void invertir() {
        ListaEstatica posicionesNuevas = new ListaEstatica(MAXIMO);
        for (int posicionesExtraer = ultimo ; posicionesExtraer>=0; posicionesExtraer--){
            posicionesNuevas.agregar(this.datos[posicionesExtraer]);
        }
        for (int posicionesIngresar = 0; posicionesIngresar<=ultimo; posicionesIngresar++){
            this.cambiar(posicionesIngresar, posicionesNuevas.datos[posicionesIngresar]);
        }
    }

    @Override
    public int contar(Object valor) {
        int contadorCoincidencias = 0;
        for (int posicion = 0; posicion<=ultimo; posicion++){
            if (datos[posicion].toString().equalsIgnoreCase(valor.toString())){
                contadorCoincidencias++;
            }
        }
        return contadorCoincidencias;
    }

    @Override
    public boolean eliminarLista(Object lista2) {
        try{
            ListaEstatica listaEliminar = (ListaEstatica) lista2;
            int contadorCambios=0;
            for (int posicionLista2 = 0; posicionLista2<=listaEliminar.ultimo; posicionLista2++){
                for (int posicionLista1 =0; posicionLista1<=ultimo; posicionLista1++){
                    if (listaEliminar.datos[posicionLista2].toString().equalsIgnoreCase(datos[posicionLista1].toString())){
                        this.eliminar( listaEliminar.datos[posicionLista2]);
                        contadorCambios++;
                    }
                }
            }
            if (contadorCambios ==0){
                return false;
            }else{
                return true;
            }
        }
        catch(Exception ex){
            return false;
        }
    }

    @Override
    public void rellenar(Object valor, int cantidad) {
        if (MAXIMO-(ultimo) >= cantidad){
            for (int posicion = ultimo-1; posicion<=MAXIMO; posicion++ ){
                if (posicion<= cantidad){
                    this.agregar(valor);
                }
            }
        }else{
            SalidaPorDefecto.consola("No hay espacio Suficiente." + "\n");
        }
    }

    @Override
    public Object clonar() {
        ListaEstatica listaRetorno = new ListaEstatica(MAXIMO);
        for (int posicion =0; posicion<=ultimo; posicion++){
            listaRetorno.agregar(datos[posicion]);
        }
        return listaRetorno;
    }


    public Object subLista(int indiceInicial, int indiceFinal) {
        ListaEstatica subLista = new ListaEstatica(MAXIMO);
        if ((indiceInicial>=0 && indiceInicial<=ultimo) && (indiceFinal>=0 && indiceFinal>indiceInicial && indiceFinal<=ultimo)){
            for (int posicion= indiceInicial; posicion<= indiceFinal; posicion++){
                subLista.agregar(datos[posicion]);
            }
            return subLista;
        }else{
            return null;
        }
    }


    public boolean esSublista(ListaAlmacenamiento lista2) {
        return false;
    }


    public boolean insertar(int indice, Object valor) {
        if (numeroElementos()< maximo()){
            int resultado = agregar(valor);
            if (resultado<0){
                return false;
            }else{
                Object memoria = datos[indice];
                datos[indice] = valor;
                datos[ultimo] = memoria;
                return true;
            }
        }else{
            return false;
        }
    }

    public boolean copiarLista(ListaAlmacenamiento lista2) {
       ListaEstatica lista = (ListaEstatica) lista2;
        if (maximo() == lista.maximo()){
            vaciar();
            for (int posicion=0; posicion<lista.numeroElementos(); posicion++){
                agregar(lista.datos[posicion]);
            }
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Object verUltimo() {
        return null;
    }

    /**
     * Obtiene una sublista con los elementos indicados por la lista de indices
     * @param listaIndices estos son los indices de los elementos a extraer
     * @return regresa la nueva sublista
     */
    public ListaEstatica subLista(ListaEstaticaNumerica listaIndices){
        ListaEstatica subLista = new ListaEstatica(listaIndices.MAXIMO);
        if (numeroElementos() >= listaIndices.numeroElementos()){
            for (int posicion =0; posicion<listaIndices.numeroElementos(); posicion++){
                subLista.agregar(datos[(int) Math.round((double) listaIndices.obtener(posicion))]);
            }
        }
        return subLista;
    }

    /**
     * Guarda el contenido de la variable buffer de la clase AudioFileRecord al leer un archivo
     * @param buffer son los datos a sobrescribir
     * @return regresa true si fue posible hacer la sobrescritura y false si no
     */
    public boolean guardarContenidoBuffer(Object[] buffer){
        if (buffer instanceof Object[]){
            datos =  buffer;
            ultimo = buffer.length - 1;
            return true;
        }else{
            return false;
        }
    }

    /**
     * Permite que el buffer en la clase AudioFileRecord se lea como un arreglo de datos contenidos en la lista
     * @return Regresa el arreglo con los datos de la lista
     */
    public double[] leerArreglo(){
        double[] arregloRetorno = new double[MAXIMO];
        for (int posicion=0; posicion<numeroElementos(); posicion++){
            arregloRetorno[posicion] = (double) datos[posicion];
        }
        return arregloRetorno;
    }

    public Object[] getDatos(){
        return datos;
    }
}
