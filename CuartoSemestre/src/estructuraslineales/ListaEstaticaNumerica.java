package estructuraslineales;

public class ListaEstaticaNumerica extends ListaEstatica {

    public ListaEstaticaNumerica(int maximo) {
        super(maximo);
    }

    @Override
    public int agregar(Object valor){
        Number valorNuevo = 0;
        if (esNumerico(valor)){
            if (valor instanceof Integer){
                valorNuevo = new Double((int)valor);
            }else{
                valorNuevo = (double) valor;
            }
            return super.agregar(valorNuevo);
        }else{
            return -1;
        }
    }

    @Override
    public boolean cambiar(int indice, Object valor) {
        Number valorNuevo = 0;
        if (esNumerico(valor)){
            if (valor instanceof Integer){
                valorNuevo = new Double((int)valor);
            }else{
                valorNuevo = (double) valor;
            }
            return super.cambiar(indice, valor);
        }else{
            return false;
        }
    }

    @Override
    public boolean cambiarListaEstatica(ListaEstatica indicesBusqueda, ListaEstatica valoresNuevos) {
        int contadorNumericos =0;
        for (int posicion =0; posicion< valoresNuevos.numeroElementos(); posicion++){
            if (esNumerico(valoresNuevos.datos[posicion])){
                contadorNumericos++;
            }
        }
        if (contadorNumericos ==valoresNuevos.numeroElementos()){
            return super.cambiarListaEstatica(indicesBusqueda, valoresNuevos);
        }else{
            return false;
        }
    }

    @Override
    public Object buscar(Object valor) {
        if (esNumerico(valor)){
            return super.buscar(valor);
        }else{
            return null;
        }
    }

    @Override
    public Object eliminar(Object valor) {
        if (esNumerico(valor)){
            return super.eliminar(valor);
        }else{
            return null;
        }
    }


    @Override
    public boolean cambiar(Object valorViejo, Object valorNuevo, int numVeces) {
        if (esNumerico(valorNuevo)){
            return super.cambiar(valorViejo, valorNuevo, numVeces);
        }else{
            return false;
        }
    }

    @Override
    public ListaEstatica buscarValores(Object valor) {
        if (esNumerico(valor)){
            return super.buscarValores(valor);
        }else{
            return null;
        }
    }

    @Override
    public boolean agregarLista(Object lista2) {
        ListaEstatica lista = (ListaEstatica) lista2;
        int contadorNumericos =0;
        for (int posicion =0; posicion< lista.numeroElementos(); posicion++){
            if (esNumerico(lista.datos[posicion])){
                contadorNumericos++;
            }
        }
        if (contadorNumericos ==lista.numeroElementos()){
            return super.agregarLista(lista2);
        }else{
            return false;
        }
    }

    @Override
    public int contar(Object valor) {
        if (esNumerico(valor)){
            return super.contar(valor);
        }else{
            return 0;
        }
    }

    @Override
    public void rellenar(Object valor, int cantidad) {
        if (esNumerico(valor)){
            super.rellenar(valor, cantidad);
        }
    }


    @Override
    public boolean insertar(int indice, Object valor) {
        if (esNumerico(valor)){
            return super.insertar(indice, valor);
        }else{
            return false;
        }
    }

    @Override
    public boolean copiarLista(ListaAlmacenamiento lista2) {
        ListaEstatica lista = (ListaEstatica) lista2;
        int contadorNumericos =0;
        for (int posicion =0; posicion< lista.numeroElementos(); posicion++){
            if (esNumerico(lista.datos[posicion])){
                contadorNumericos++;
            }
        }
        if (contadorNumericos ==lista.numeroElementos()){
            return super.copiarLista(lista2);
        }else{
            return false;
        }
    }

    public boolean esNumerico(Object valor){
        if ((valor instanceof Number)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Multiplica cada posicion del arreglo por el escalar indicado como parametro
     * @param escalar es el numero por el cual multiplicar
     * @return regresa true si se hicieron cambios en el arreglo y false si no
     */
    public boolean porEscalar(Number escalar){
        if (!vacia()){
            for (int posicion =0; posicion<numeroElementos(); posicion++){
                datos[posicion] =  (double)datos[posicion] * (int)escalar;
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * Suma el escalar indicado como parametro a cada posicion del arreglo
     * @param escalar es el valor a sumar
     * @return regresa true si hubo cambios en el arreglo y false si no
     */
    public boolean sumarEscalar(Number escalar){
        if (!vacia()){
            for (int posicion =0; posicion<numeroElementos(); posicion++){
                datos[posicion] =  (double)datos[posicion] + (int)escalar;
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * Suma los elementos de la lista2 a la lista actual en el indice correspondiente
     * @param lista2 es la lista que contiene los valores a sumar
     * @return regresa true si se hicieron cambios y false si no
     */
    public boolean sumar(ListaEstaticaNumerica lista2){
        if (numeroElementos()== lista2.numeroElementos()){
            for (int posicion=0; posicion<lista2.numeroElementos(); posicion++){
                datos[posicion] = (double)datos[posicion] + (double)lista2.datos[posicion];
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * Multiplica los valores de la lista2 en la lista actual en cada posicion correspondiente
     * @param lista2 es la lista de los valores a multiplicar
     * @return regresa true si se hicieron cambios y false si no
     */
    public boolean multiplicar(ListaEstaticaNumerica lista2){
        if (numeroElementos()== lista2.numeroElementos()){
            for (int posicion=0; posicion<lista2.numeroElementos(); posicion++){
                datos[posicion] = (double)datos[posicion] * (double)lista2.datos[posicion];
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * Eleva cada elemento del arreglo a la potencia del escalar
     * @param escalar es el escalar qie representa la potencia
     * @return regresa true si se pudieron hacer cambios y false si no
     */
    public boolean aplicarPotencia(Number escalar){
        if (!vacia()){
            for (int posicion =0; posicion<numeroElementos(); posicion++){
                datos[posicion] =  Math.pow((double)datos[posicion],(int) escalar);
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * Eleva el elemento a la posicion correspondiente de cada escalar de la lista enviada
     * @param listaEscalares es la lista que contiene los escalares a elevar
     * @return regresa true si hubo cambios y false si no
     */
    public boolean aplicarPotencia(ListaEstaticaNumerica listaEscalares){
        if (numeroElementos()== listaEscalares.numeroElementos()){
            for (int posicion=0; posicion<listaEscalares.numeroElementos(); posicion++){
                datos[posicion] = Math.pow((double)(datos[posicion]),(double) listaEscalares.datos[posicion]);
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * obtiene el valor del producto de la lista de escalares con las posiciones correspondientes
     * @param lista2 es la lista a multiplicar
     * @return regresa el valor del producto de los dos vectores
     */
    public double productoEscalar(ListaEstaticaNumerica lista2){
        double producto = 0.0;
        if (numeroElementos()== lista2.numeroElementos()){
            for (int posicion=0; posicion<lista2.numeroElementos(); posicion++){
               producto = producto + ((double)datos[posicion] * (double)lista2.datos[posicion]);
            }
            return producto;
        }else{
            return 0.0;
        }
    }

    /**
     * Calcula la norma de nuestro vector
     * @return regresa el valor de la norma
     */
    public double norma(){
        double norma = 0.0;
        for (int posicion =0; posicion<numeroElementos(); posicion++){
            norma = norma + ((double)datos[posicion] * (double)datos[posicion]);
        }
        norma = Math.sqrt(norma);
        return norma;
    }

    /**
     * Calcula la norma de la diferencia del vector 2 con el vector actual
     * @param arreglo2 es el vector a restar
     * @return regresa el valor de la norma euclidiana
     */
    public double normaEuclidiana(ListaEstaticaNumerica arreglo2){
        double normaE = 0.0;
        if (numeroElementos()== arreglo2.numeroElementos()){
            for (int posicion =0; posicion< arreglo2.numeroElementos(); posicion++){
                normaE = normaE + Math.pow(((double) arreglo2.datos[posicion]-(double)datos[posicion]), 2);
            }
            normaE = Math.sqrt(normaE);
            return normaE;
        }else{
            return 0.0;
        }
    }

    /**
     * Suma una por una todas las listas contenidas en listas.
     * @param listas es el arreglo que contiene las listas a sumar
     * @return regresa la suma total de las listas
     */
    public double sumarListaEstatica(ListaEstatica listas){
        double sumaListas = 0.0;
        for (int posicion =0; posicion< listas.numeroElementos(); posicion++){
            ListaEstaticaNumerica lista = (ListaEstaticaNumerica) listas.datos[posicion];
            if (numeroElementos() == lista.numeroElementos() && (lista instanceof  ListaEstaticaNumerica)) {
                for (int posicion1 = 0; posicion < lista.numeroElementos(); posicion1++) {
                    datos[posicion1] = (double) datos[posicion1] + (double) lista.datos[posicion1];
                    sumaListas = sumaListas + ((double) datos[posicion1] + (double) lista.datos[posicion1]);

                }
            }
        }
        return sumaListas;
    }

    /**
     * Suma consigos mismos todos los elementos indicados en la lista de indices
     * @param listaIndices estos son los indices a sumar
     * @return regresa la suma total de los indices
     */
    public double sumarIndices(ListaEstaticaNumerica listaIndices){
        double sumaIndices = 0.0;
        if (numeroElementos()>= listaIndices.numeroElementos()){
            for (int posicion =0; posicion< listaIndices.numeroElementos(); posicion++){
                datos[(int) Math.round((double) listaIndices.datos[posicion])] = (double)datos[(int) Math.round((double) listaIndices.datos[posicion])] + (double)datos[(int) Math.round((double) listaIndices.datos[posicion])];
                sumaIndices = sumaIndices + (double)datos[(int) Math.round((double) listaIndices.datos[posicion])] + (double)datos[(int) Math.round((double) listaIndices.datos[posicion])];
            }
        }
        return sumaIndices;
    }

    /**
     * obtiene una sublista de los indices indicados pero verifica que los elementos sean numericos
     * @param listaIndices estos son los indices de los elementos a extraer
     * @return regresa la nueva subLista
     */
    @Override
    public ListaEstatica subLista(ListaEstaticaNumerica listaIndices) {
        int numNumericos =0;
        for (int posicion =0; posicion< listaIndices.numeroElementos(); posicion++){
            if (listaIndices.obtener(posicion) instanceof  Number){
                numNumericos++;
            }
        }
        if (numNumericos == listaIndices.numeroElementos()){
            return super.subLista(listaIndices);
        }else{
            return null;
        }
    }

    /**
     * Calcula si el vector enviado es ortogonal con el actual
     * @param lista2 es el vector a calcular
     * @return regresa true si es ortogonal y false si no lo es
     */
    public boolean esOrtogonal(ListaEstaticaNumerica lista2){
        double suma =0.0;
        if (numeroElementos()== lista2.numeroElementos()){
            for (int posicion =0; posicion<numeroElementos(); posicion++){
                suma = suma + (double)datos[posicion] * (double)lista2.datos[posicion];
            }
            if (suma==0){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * Calcula si los dos vectores son paralelos
     * @param lista2 es el segundo vector a comparar
     * @return regresa true si son paralelos y false si no lo son
     */
    public boolean esParalelo(ListaEstaticaNumerica lista2){
        if (numeroElementos() == lista2.numeroElementos()){
            double sumaVector1 = 0.0;
            double sumaVector2 = 0.0;
            for (int posicion=0; posicion< numeroElementos(); posicion++ ){
                sumaVector1 = sumaVector1 + (double)datos[posicion] + (double)datos[posicion];
            }
            for (int posicion=0; posicion< numeroElementos(); posicion++ ){
                sumaVector2 = sumaVector2 + (double)lista2.datos[posicion] + (double)lista2.datos[posicion];
            }
            boolean encontrado=false;
            for (int numero =-100; numero<100; numero++){
                if (sumaVector1 == (sumaVector2*numero)){
                    encontrado = true;
                }
            }
            return encontrado;
        }else{
            return false;
        }
    }

    /**
     * Calcula si los vectores enviados como parametro son linealmente dependientes
     * @param listaVectores es el conjunto de vectores
     * @return regresa true si son linealmente dependientes y false si no
     */
    public boolean sonLinealmenteDependientes(ListaEstatica listaVectores){
        double sumaVectores = 0.0;
        ListaEstatica listaResultados = new ListaEstatica(listaVectores.numeroElementos());
        if (numeroElementos() == listaVectores.numeroElementos()){
            int contadorCerosEscalares=0;
            int contadorCerosResultados =0;
            for (int posicion =0; posicion<listaVectores.numeroElementos(); posicion++){
                ListaEstaticaNumerica vector = (ListaEstaticaNumerica) listaVectores.obtener(posicion);
                for (int posicion1 =0; posicion1< vector.numeroElementos(); posicion1++){
                    sumaVectores = sumaVectores + (double) vector.obtener(posicion1);
                }
                listaResultados.agregar(sumaVectores * (Double)datos[posicion]);
            }
            for (int posicion =0; posicion<listaResultados.numeroElementos(); posicion++){
                if ((Double)listaResultados.obtener(posicion) ==0){
                    contadorCerosResultados++;
                }
            }
            for (int posicion=0; posicion<numeroElementos(); posicion++){
                if ((Double) datos[posicion]==0){
                    contadorCerosEscalares++;
                }
            }
            if(contadorCerosEscalares<numeroElementos() && contadorCerosResultados== listaResultados.numeroElementos()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * Calcula si los vectores enviados como parametro son linealmente independientes
     * @param listaVectores es el conjunto de vectores
     * @return regresa true si son linealmente independientes y false si no
     */
    public boolean sonLinealmenteIndependientes(ListaEstatica listaVectores){

        Double sumaVectores = 0.0;
        ListaEstatica listaResultados = new ListaEstatica(listaVectores.numeroElementos());
        if (numeroElementos() == listaVectores.numeroElementos()){
            int contadorCerosEscalares=0;
            int contadorCerosResultados =0;
            for (int posicion =0; posicion<listaVectores.numeroElementos(); posicion++){
                ListaEstaticaNumerica vector = (ListaEstaticaNumerica) listaVectores.obtener(posicion);
                for (int posicion1 =0; posicion1< vector.numeroElementos(); posicion1++){
                    sumaVectores = sumaVectores + (double) vector.obtener(posicion1);
                }
                listaResultados.agregar(sumaVectores * (Double)datos[posicion]);
            }
            for (int posicion =0; posicion<listaResultados.numeroElementos(); posicion++){
                if ((Double)listaResultados.obtener(posicion) ==0){
                    contadorCerosResultados++;
                }
            }
            for (int posicion=0; posicion<numeroElementos(); posicion++){
                if ((Double) datos[posicion]==0){
                    contadorCerosEscalares++;
                }
            }
            if(contadorCerosEscalares<numeroElementos() && contadorCerosResultados!= listaResultados.numeroElementos()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
