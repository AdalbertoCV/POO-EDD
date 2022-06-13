package estructurasnolineales;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.*;
import estructurasnolineales.auxiliares.Vertice;
import herramientas.generales.EtiquetaGrafo;
import enumerados.TipoOrden;

public class GrafoEstatico {
    //Dónde guardar los vértices
    protected ListaEstatica vertices;
    //Dónde guardar las aristas
    protected Matriz2 aristas;
    //Definimos el orden para trabjar con grafos ponderados
    protected TipoOrden orden;

    public GrafoEstatico(int cantidadVertices){
        vertices=new ListaEstatica(cantidadVertices);
        aristas=new Matriz2(cantidadVertices,cantidadVertices,0.0);
    }

    public GrafoEstatico(int cantidadVertices, TipoOrden orden){
        vertices=new ListaEstatica(cantidadVertices);
        this.orden=orden;
        //asignar por defecto los valores infinitos
        if(this.orden==TipoOrden.DECR){ //decremental
            aristas=new Matriz2(cantidadVertices,cantidadVertices,Double.MAX_VALUE); //+inifnito
        }else{  //incremental
            aristas=new Matriz2(cantidadVertices,cantidadVertices,Double.MIN_VALUE); //-infinito
        }
        aristas.matrizDiagonal(0.0);//diagonal con ceros
    }


    public boolean agregarVertice(Object contenido){
        //Al momento de agregar vértices es oportuno checar que no se registren contenidos duplicados.
        //Buscar la existencia del vértice
        Integer indiceVertice=(Integer)vertices.buscar(contenido);

        if(indiceVertice==null){ //no existe
            //Crear un vértice
            Vertice nuevoVertice=new Vertice();

            //Le colocamos su atributos
            nuevoVertice.setContenido(contenido);
            nuevoVertice.setNumVertice(vertices.numeroElementos()); // el número de vértice lo obtenemos de la cantidad de vértices

            int retornoOperacion=vertices.agregar(nuevoVertice);
            if(retornoOperacion>=0){ //si se pudo agregar
                return true;
            }else{ //no se pudo agregar
                return false;
            }
        }else{ //si existe
            return false;
        }
    }

    public boolean agregarArista(Object origen, Object destino){ // V1 -> V2
        return agregarArista(origen,destino,1.0);
    }

    public boolean agregarArista(Object origen, Object destino, double peso){ // V1 -> V2
        //Verificar que existan
        Integer indiceOrigen=(Integer)vertices.buscar(origen);
        Integer indiceDestino=(Integer)vertices.buscar(destino);

        if(indiceOrigen!=null && indiceDestino!=null){ //si existen ambos
            //creamos la arista
            return aristas.cambiar(indiceOrigen,indiceDestino,peso);
        }else{ //uno u otro no existe
            return false;
        }
    }

    public void imprimir(){
        SalidaPorDefecto.consola("Vértices:\n");
        vertices.imprimir();

        SalidaPorDefecto.consola("Aristas:\n");
        aristas.imprimirXRenglones();
    }

    //método axuliar de paso 1 de OT
    private int gradoDeEntradaXVertice(int cadaDestino){
        int gradodeEntradaVertice=0;
        //recorrer todos los renglones (origenes) hacia el vertice destino
        for(int cadaOrigen=0; cadaOrigen< aristas.obtenerRenglones(); cadaOrigen++){
            //usando la matriz obtenemos esa flecha
            Double flecha=(Double)aristas.obtener(cadaOrigen,cadaDestino);
            if(flecha!=null && flecha>0){ //hay una flecha del orgien a ese destino
                gradodeEntradaVertice++;
            }
        }
        return gradodeEntradaVertice;
    }

    //paso 1 de OT
    private void inicializarGradosEntrada(ListaEstatica gradosEntrada){
        //recorrer todos los posibles vèrtices o procesos (destinos en la matriz), para calcular en cada uno de ellos
        //la cantidad de flechas o aristas que les llega (grados de entrada).
        for(int cadaDestino=0; cadaDestino< aristas.obtenerColumnas(); cadaDestino++){
            //para cada uno de estos destinos posibles, calculemos los grados de entrada o flechas que le llegan a èl.
            //es decir los renglones (origenes) que llegan a este destino
            int gradosEntradaXVerticeDestino=gradoDeEntradaXVertice(cadaDestino);
            gradosEntrada.agregar(gradosEntradaXVerticeDestino); // el grado de entrada se guarda en  la misma posición
            //que el vertice destino
        }
    }

    //paso 2 y 5 de OT
    private void encolarYMarcarVerticesGrado0(ListaEstatica gradosEntrada, ListaEstatica marcados, ColaEstatica colaProcesamiento){
        //recorrer todos los vèrtices para determinar lo que se requiere
        for(int cadaVertice=0;cadaVertice<gradosEntrada.numeroElementos();cadaVertice++){
            //si no esta marcado y tiene grado de E 0, encolamos y marcamos
            if((int)gradosEntrada.obtener(cadaVertice)==0 && (boolean)marcados.obtener(cadaVertice)==false){
                colaProcesamiento.poner(cadaVertice); //encolamos
                marcados.cambiar(cadaVertice,true); //marcamos
            }
        }
    }

    //paso 4 de OT
    private void recalcularGradosEntradaVertices(ListaEstatica gradosEntrada, ListaEstatica marcados, int indiceVerticeActual){
        for(int cadaDestino=0; cadaDestino< aristas.obtenerColumnas(); cadaDestino++){
            //recorremos los destinos posibles provinientes del verticeActual y si tiene flecha, le afectaba
            //y que no estuviera marcado.

            //saber si desde origen a ese destino hay flecha
            Double flecha=(Double)aristas.obtener(indiceVerticeActual,cadaDestino);
            if(flecha!=null && flecha>0 && (boolean)marcados.obtener(cadaDestino)==false){ //hay flecha hacia ese destino
                //actualizamos la incidencia o grado de entrada, es decir, se resta en 1
                int gradoEntradaVerticeDestino=(int)gradosEntrada.obtener(cadaDestino);
                gradosEntrada.cambiar(cadaDestino,gradoEntradaVerticeDestino - 1);
            }
        }
    }

    //mètodo principal de OT
    public ListaDinamica ordenacionTopologica(){
        ListaDinamica ordenProcesos=new ListaDinamica(); // es el resultado de la ordenación topológica
        ColaEstatica colaProcesamiento=new ColaEstatica(vertices.numeroElementos());
        ListaEstatica gradosEntrada=new ListaEstatica(vertices.numeroElementos());
        ListaEstatica marcados=new ListaEstatica(vertices.numeroElementos());

        //0.-En otro módulo o función deberá llevarse a cabo una verificación de no existencia de ciclos.

        //1.- Inicializar las incidencias (grados de entrada de los vértices).
        inicializarGradosEntrada(gradosEntrada);

        //2.- Los procesos (vértices) con grados de entrada en 0 (no marcados)
        // se colocan en una cola de procesamiento y se marcan como ya analizados.

        //inicializar los mrcados como false
        marcados.rellenar(false,vertices.numeroElementos());
        //invocar al mètodo que determina el paso 2 como tal
        encolarYMarcarVerticesGrado0(gradosEntrada, marcados, colaProcesamiento);

        while(colaProcesamiento.vacio()==false) { //mientras no esté vacía
            //3.- Sacar un proceso (vértice) de la cola de procesamiento y
            // lo ejecutamos (mientras haya datos en la cola).
            int indiceVerticeActual=(int)colaProcesamiento.quitar();
            Vertice verticeActual=(Vertice)vertices.obtener(indiceVerticeActual);
            ordenProcesos.agregar(verticeActual.getContenido());

            //4.- Recalcular grados de entrada dado el paso 3.
            recalcularGradosEntradaVertices(gradosEntrada, marcados, indiceVerticeActual);

            //5.- Los procesos (vértices) con grado de entrada 0 (no marcados) se colocan
            // en la cola de procesamiento y se marcan como ya analizados.
            encolarYMarcarVerticesGrado0(gradosEntrada, marcados, colaProcesamiento);
        }
        return ordenProcesos;
    }

    //paso 3 de recorrido en profundidad
    private void enpilarYMarcarVerticesAdyacentes(int indiceVerticeActual, PilaEstatica pila, ListaEstatica marcados){
        for(int cadaDestino=0;cadaDestino<aristas.obtenerColumnas(); cadaDestino++){
            //recorremos a todos los destinos posibles a partir de lvértice actual (origen)
            Double flecha=(Double)aristas.obtener(indiceVerticeActual,cadaDestino);
            //hay flecha si hay adyacencia y no están marcados
            if(flecha!=null && flecha>0 && (boolean)marcados.obtener(cadaDestino)==false){
                //enpilamos
                pila.poner(cadaDestino);
                //marcamos
                marcados.cambiar(cadaDestino,true);
            }
        }
    }

    //Recorrido en profundidad
    public ListaDinamica recorridoProfunidad(Object origen) {
        ListaDinamica recorridoP = new ListaDinamica();
        PilaEstatica pila = new PilaEstatica(vertices.numeroElementos());
        ListaEstatica marcados = new ListaEstatica(vertices.numeroElementos());

        //Pasos:

        //0. Validar la existencia del origen.
        Integer indiceOrigen = (Integer) vertices.buscar(origen);
        if (indiceOrigen != null) { //existe
            //1.- Partir de un vértice origen. Este vértice se marca y se mete en una pila.
            //Llenar el arreglo de marcados con falsos.
            marcados.rellenar(false, vertices.numeroElementos());
            //marcamos este vértice origen
            marcados.cambiar(indiceOrigen, true);
            //meter el origen en la pila
            pila.poner(indiceOrigen);

            while (pila.vacio() == false) {
                //2.- Mientras existan vértices en la pila, se van a extraer (de uno por uno) y se procesarán (imprimir).
                int indiceVerticeActual = (int) pila.quitar(); //sacamos de pila
                Vertice verticeActual = (Vertice) vertices.obtener(indiceVerticeActual); //obtenemos el objeto vértice
                recorridoP.agregar(verticeActual.getContenido());//agregamos en la salida el contenido del vértice

                //3.- Los vértices adyacentes (vecinos directos) no marcados y que están enlazados al nodo que actualmente
                // se procesa (el paso 2) se marcan y se meten en la pila.
                enpilarYMarcarVerticesAdyacentes(indiceVerticeActual, pila, marcados);
            }
        } else { //no existe
            return null;
        }
        return recorridoP;
    }

    ///////////////////////////////////////////////////DIJKSTRA

    // Paso 1
    private void inicializarEtiquetasGrafo(ListaEstatica etiquetasOptimas, int indiceVerticeOrigen, double metricaIndiceOrigen,
                                           double metricaVertices, int verticeAnterior){
        for(int cadaVertice=0; cadaVertice<vertices.numeroElementos(); cadaVertice++){
            EtiquetaGrafo etiqueta=new EtiquetaGrafo();
            etiqueta.setMetricaAcumulada(metricaVertices); // en este caso en nuestro ejemplo era infinito....
            etiqueta.setVerticeAnterior(verticeAnterior); // por ejemplo en nuestro caso - (-1)
            etiqueta.setInteracion(0);
            etiquetasOptimas.agregar(etiqueta);//agregarla a nuestro arreglo de de etiquetas
        }
        //en particular falta cambiar el valor e la métrica en el vértice origen -> 0
        EtiquetaGrafo etiquetaVerticeOrigen=(EtiquetaGrafo) etiquetasOptimas.obtener(indiceVerticeOrigen);
        etiquetaVerticeOrigen.setMetricaAcumulada(metricaIndiceOrigen); // por ejemplo con 0.0
    }

    // Paso 2
    private void actualizarMetricaAcumuladaEtiquetas(int verticeActual, ListaEstatica etiquetasOptimas, ListaEstatica permanentes, int iteracion,
                                                      double infinito){

        //recorrer todos los vértices
        for(int cadaPosibleVecino=0; cadaPosibleVecino<aristas.obtenerColumnas();cadaPosibleVecino++){
            //checar cuáles son vecinos no marcados
            Double flechaMetricaOrigenActualDestino=(Double)aristas.obtener(verticeActual, cadaPosibleVecino);
            if(flechaMetricaOrigenActualDestino!=null && flechaMetricaOrigenActualDestino!=0 && flechaMetricaOrigenActualDestino!=infinito &&
                    (boolean)permanentes.obtener(cadaPosibleVecino)==false){
                //calcularemos las métricas acumuladas desde el vértice actual a este cada vecino adyacente y si resulta mejor la métrica
                //se actualizará en la etiqueta

                //sacar la métrica acumulada del vértice actual
                EtiquetaGrafo etiquetaVerticeActual=(EtiquetaGrafo) etiquetasOptimas.obtener(verticeActual);
                double metricaAcumuladaVerticeActual=etiquetaVerticeActual.getMetricaAcumulada();
                //sumar la métrica acumulada del vértice actual +  la métrica del vértice actual hacia el vecino
                double metricaAcumuladaVerticeActualDestino= metricaAcumuladaVerticeActual + flechaMetricaOrigenActualDestino;

                //sacar la métrica acumulada del vecino
                EtiquetaGrafo etiquetaVerticeDestino=(EtiquetaGrafo) etiquetasOptimas.obtener(cadaPosibleVecino);
                double metricaVerticeDestino=etiquetaVerticeDestino.getMetricaAcumulada();

                //comparar si es mejor la métrica acumulada hacia un vecino desde el origen actual
                //no olvidad si es DEC y INC
                boolean banderaActualizarEtiqueta=false;
                if(orden==TipoOrden.DECR){ //más chico es mejor
                    if(metricaAcumuladaVerticeActualDestino<metricaVerticeDestino){ //si es mejor, actualizar
                        banderaActualizarEtiqueta=true;
                    }
                }else{ //INC, más grande es mejor
                    if(metricaAcumuladaVerticeActualDestino>metricaVerticeDestino){ //si es mejor, actualizar
                        banderaActualizarEtiqueta=true;
                    }
                } //if
                if(banderaActualizarEtiqueta==true){ //cambiar los valores de la etiqueta
                    etiquetaVerticeDestino.setInteracion(iteracion);
                    etiquetaVerticeDestino.setMetricaAcumulada(metricaAcumuladaVerticeActualDestino);
                    etiquetaVerticeDestino.setVerticeAnterior(verticeActual);
                }
            }//if vecino
        } //for
    }

    // Paso 3
    private int obtenerVerticeMetricaOptima(ListaEstatica etiquetasOptimas, ListaEstatica permanentes, double infinito){
        //Siempre cuando se toma el valor más grande o más chico de un arreglo, se suele partir de un valor inicial,
        //el cual se indica que será el mejor en ese momento.
        double mejorMetrica=infinito; //es la métrica mejor inicial
        int mejorVertice=-1; //es el mejor vértice

        //recorrer todos los vértices no marcados y ver cuál es mejor que el "mejorVertice"
        for(int cadaVertice=0; cadaVertice<vertices.numeroElementos();cadaVertice++){
            //checar solo los no marcados como permanentes
            if((boolean)permanentes.obtener(cadaVertice)==false){
                //obtener la etiqueta de cada vértice
                EtiquetaGrafo etiquetaCadaVertice=(EtiquetaGrafo) etiquetasOptimas.obtener(cadaVertice);
                //obtener la métrica de cada vértice
                double metricaCadaVertice=etiquetaCadaVertice.getMetricaAcumulada();
                //comparamos esta métrica con la que ya se tiene comno "mejorMetrica" y si se mejora, se substituye
                //considerar el orden INC o DEC
                if(orden==TipoOrden.DECR){ //más pequeño es mejor
                    if(metricaCadaVertice<mejorMetrica){ // se mejora, cambiamos
                        mejorMetrica=metricaCadaVertice; //cambiamos la mejor métrica
                        mejorVertice=cadaVertice;//cambiar el mejor vértice
                    }
                }else{ //INC, más grande es mejor
                    if(metricaCadaVertice>mejorMetrica){ // se mejora, cambiamos
                        mejorMetrica=metricaCadaVertice; //cambiamos la mejor métrica
                        mejorVertice=cadaVertice;//cambiar el mejor vértice
                    }
                }
            }//permanentes
        }//for
        return mejorVertice; //regresamos el vértice que tuvo de entre todos, el mejor valor en métrica
    }

    // Etiquetas de Dijkstra

    public ListaEstatica etiquetasOptimasDijkstra(Object origen){
        ListaEstatica etiquetasOptimas=new ListaEstatica(vertices.numeroElementos()); //arreglo paralelo para las etiquetas
        ListaEstatica permanentes=new ListaEstatica(vertices.numeroElementos()); //arreglo paralelo para los marcados como permanentes
        //definimos el infinito
        double infinito=0;
        if(orden==TipoOrden.DECR){ //decremental, más chico es mejor,  +infinito
            infinito=Double.MAX_VALUE;
        }else{ //incremental, más grande es mejor, -infinito
            infinito=Double.MIN_VALUE;
        }

        //Pasos:

        //0.- Validar que el oirgen exista
        Integer indiceVerticeOrigen=(Integer)vertices.buscar(origen);
        if(indiceVerticeOrigen!=null) { //si existe el origen

            //1.- Inicializar etiquetas partiendo de un nodo origen, marcándolo como permanente.
            inicializarEtiquetasGrafo(etiquetasOptimas, indiceVerticeOrigen, 0.0, infinito, -1);
            //marcar el vértice origen como permanente
            permanentes.rellenar(false, vertices.numeroElementos());//rellenado con falsos
            permanentes.cambiar(indiceVerticeOrigen,true); //marcamos el origen

            //indicar que el vértice origen es el vértice Actual, para la primera itreración
            int verticeActual=indiceVerticeOrigen;
            for(int iteracion=1; iteracion< vertices.numeroElementos(); iteracion++) { // el último vértice no se necesita procesar en el paso 2 y 3
                //2.- Calcular los nuevos valores de las etiquetas de los vértices a partir de las métricas acumuladas hacia
                //    los vecinos (adyacentes) no marcados como permanentes; todo esto partiendo del vértice acrtual. si se mejora
                //    la métrica, se actualiza la etiqueta de ese vértice.
                actualizarMetricaAcumuladaEtiquetas(verticeActual, etiquetasOptimas, permanentes,iteracion, infinito);

                //3.- Elegir el vértice con la mejor métrica acumulada (óptima), tomando en cuenta vértices no marcados como permanentes.
                //    entonces ese vértice elegido se marca y se convierte en el vértice actual.
                verticeActual=obtenerVerticeMetricaOptima(etiquetasOptimas, permanentes, infinito); //el mejor vértice se convierte en el actual
                permanentes.cambiar(verticeActual,true); //marca el vértice actual
            }
            return etiquetasOptimas;
        }else{ //no existe el origen
            return null;
        }
    }

    // Métria Origen -> Destino
    public Double obtenerMetricaOptimaDijkstra(Object origen, Object destino){
        //buscar que existan los vértices
        Integer indiceDestino=(Integer)vertices.buscar(destino);
        ListaEstatica etiquetasOptimas=etiquetasOptimasDijkstra(origen);
        if(indiceDestino!=null && etiquetasOptimas!=null){ //si existe el destino y el origen
            EtiquetaGrafo etiquetaDestino=(EtiquetaGrafo) etiquetasOptimas.obtener(indiceDestino); //obtenemos la métrica de la etiqueta del destino
            return etiquetaDestino.getMetricaAcumulada();
        }else{ //no existe el origen o el destino
            return null;
        }
    }

    // Ruta de Origen -> Destino
    public ListaDinamica obtenerRutaOptimaDijkstra(Object origen, Object destino){
        ListaDinamica rutaOrigenDestino=new ListaDinamica();

        //verificar que existan el origen y el destino
        Integer indiceDestino=(Integer)vertices.buscar(destino);
        ListaEstatica etiquetasOptimas=etiquetasOptimasDijkstra(origen);
        if(indiceDestino!=null && etiquetasOptimas!=null) { //si existe el destino y el origen
            //hacer el backtrace
            //primero declaramos una variable que me diga quién es el vértice actual, empezando con el destino
            int indiceVerticeActual=indiceDestino;

            //ciclo que recorra hasta que encontremos un vértice anterior = indeterminado : -1
            do{
                //agregar el vértice actual a la ruta
                Vertice verticeActual=(Vertice)vertices.obtener(indiceVerticeActual);
                rutaOrigenDestino.agregarInicio(verticeActual.getContenido());

                //nos vamos hacia atrás, usando como referencia el vértice anterior de la etiqueta del vértice actual.
                //ese vértice anterior, se convertirá en el nuevo vértice actual en la siguiente iteración
                EtiquetaGrafo etiquetaVerticeActual=(EtiquetaGrafo) etiquetasOptimas.obtener(indiceVerticeActual);
                indiceVerticeActual=etiquetaVerticeActual.getVerticeAnterior();
            }while(indiceVerticeActual!=-1); //hasta no llegar a un -1 en el campo de vértice anterior, el cual solo debería tenerlo el origen
            //regresamos la ruta
            return rutaOrigenDestino;
        }else{ //no existe o el origen o el destino
            return null;
        }

    }
	
	/**
     * Elimina un vertice especifico del grafo
     * @param vertice es el contenido del vertice a eliminar
     * @return regresa el contenido del vertice eliminado o null si no se encontro
     */
    public Object eliminarVertice(Object vertice){
        Integer indiceVertice=(Integer)vertices.buscar(vertice);
        if (indiceVertice!=null){
            Object eliminado = vertices.obtener(indiceVertice);
            aristas.eliminarColumna(indiceVertice);
            aristas.eliminarRenglon(indiceVertice);
            vertices.eliminar(vertice);
            return eliminado;
        }else{
            return null;
        }
    }

    /**
     * Indica si un nodo destino es adyacente con un nodo origen
     * @param origen es el origen a evaluar
     * @param destino es el destino a evaluar
     * @return regresa true si es adyacente y false si no lo es
     */
    public boolean esAdyacente(Object origen, Object destino){
        Integer indiceOrigen = (Integer) vertices.buscar(origen);
        Integer indiceDestino = (Integer) vertices.buscar(destino);
        if (indiceOrigen!=null && indiceDestino!=null){
            double arista = (double) aristas.obtener(indiceOrigen,indiceDestino);
            if (arista>0){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * Elimina una arista indicada por su origen y su destino si existe
     * @param origen es el origen a buscar
     * @param destino es el destino a buscar
     * @return regresa true si se elimino la arista y false si no existe.
     */
    public boolean eliminarArista(Object origen, Object destino){
        Integer indiceOrigen = (Integer) vertices.buscar(origen);
        Integer indiceDestino = (Integer) vertices.buscar(destino);
        if (indiceOrigen!=null && indiceDestino!=null){
            double arista = (double) aristas.obtener(indiceOrigen,indiceDestino);
            if (arista>0){
                aristas.cambiar(indiceOrigen, indiceDestino, 0.0);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * busca un vertice especifico dentro del grafo
     * @param vertice es el vertice a buscar
     * @return regresa el contenido del vertice si se encontro y null si no se encontro
     */
    public Object buscarVertice(Object vertice){
        Integer indiceVertice = (Integer) vertices.buscar(vertice);
        if (indiceVertice!=null){
            return vertices.obtener(indiceVertice);
        }else{
            return null;
        }
    }

    /**
     * Indica si el grafo contiende nudos
     * @return regresa true si es pseudografo y flase si no lo es
     */
    public boolean esPseudografo(){
        for (int fila=0; fila<aristas.obtenerRenglones(); fila++){
            for (int col =0; col< aristas.obtenerColumnas(); col++){
                double aristaActual =(double) aristas.obtener(fila,col);
                if (fila == col){
                    if (aristaActual>0){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Indica si el grafo contiene aristas paralelos
     * @return regresa true si es multigrafo y false si no lo es
     */
    public boolean esMultigrafo(){
        int cantidadParalelos =0;
        for (int pos=0; pos< vertices.numeroElementos(); pos++){
            for (int pos2 =0; pos2< vertices.numeroElementos(); pos2++){
                double arista1 = (double) aristas.obtener(pos,pos2);
                double arista2 = (double) aristas.obtener(pos2,pos);
                if (arista1>0.0 && arista2>0.0){
                    cantidadParalelos++;
                }
            }
        }
        if (cantidadParalelos>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Obtiene el grado de un vertice
     * @param vertice es el vertice a buscar
     * @return regresa el grado del vertice, si no lo encuentra o es aislado regresa 0.
     */
    public int gradoVertice(Object vertice){
        int grado =0;
        Integer indiceVertice = (Integer) vertices.buscar(vertice);
        if (indiceVertice!=null){
            for (int fil =0; fil<aristas.obtenerRenglones(); fil++){
                double aristaActual = (double) aristas.obtener(fil, indiceVertice);
                if (aristaActual>0.0){
                    grado++;
                }
            }
            for (int col =0; col<aristas.obtenerColumnas(); col++){
                double aristaActual = (double) aristas.obtener(indiceVertice, col);
                if (aristaActual>0.0){
                    grado++;
                }
            }
        }
        return grado;
    }

    /**
     * Indica si hay una ruta para llegar del vertice de origen al vertice destino
     * @param origen es el vertice de origen
     * @param destino es el vertice a llegar
     * @return regres true si hay ruta y false si no la hay
     */
    public boolean hayRuta(Object origen, Object destino){
        ListaEstatica marcados = new ListaEstatica(vertices.numeroElementos());
        marcados.rellenar(false,vertices.numeroElementos());
        boolean ruta = false;
        int numAdyacentes = adyacentes(origen);
        int numRecorridos=0;
        if (esAdyacente( origen, destino)){
            return true;
        }else{
            Integer indiceOrigen = (Integer) vertices.buscar(origen);
            Integer indiceActual = indiceOrigen;
            if (indiceOrigen!=null){
                for (int col=0; col< aristas.obtenerColumnas(); col++){
                    double arista = (double) aristas.obtener(indiceActual, col);
                    if (arista>0.0){
                        if (!(boolean)marcados.obtener(col)){
                            marcados.cambiar(col,true);
                            indiceActual = col;
                            col=0;
                            if (esAdyacente(vertices.obtener(indiceActual), destino)){
                                ruta=true;
                            }
                        }
                    }
                    if (col == aristas.obtenerColumnas()-1) {
                        indiceActual =indiceOrigen;
                        numRecorridos++;
                        if (numRecorridos<numAdyacentes){
                            col=0;
                        }
                    }
                }
                return ruta;
            }else{
                return false;
            }
        }
    }

    /**
     * obtiene la cantidad de nodos adyacentes a un vertice
     * @param vertice es el vertice a evaluar
     * @return regresa la cantidad de vertices adyacentes
     */
    public int adyacentes(Object vertice){
        int numAristas=0;
        Integer indice = (Integer) vertices.buscar(vertice);
        if (indice!=null){
            for (int col =0; col< aristas.obtenerColumnas(); col++){
                if ((double)aristas.obtener(indice,col)>0.0){
                    numAristas++;
                }
            }
        }
        return numAristas;
    }

    /**
     * Indica si el grafo es conexo
     * @return regresa true si es conexo y false si no lo es
     */
    public boolean esConexo(){
        int numConexos=0;
        for (int numvertice =0; numvertice< vertices.numeroElementos(); numvertice++){
            for (int pos =0; pos < vertices.numeroElementos(); pos++){
                if (numvertice!=pos){
                    if (hayRuta(vertices.obtener(numvertice), vertices.obtener(pos))){
                        numConexos++;
                    }
                }
            }
        }
        if (numConexos== (vertices.numeroElementos()* (vertices.numeroElementos()-1))){
            return true;
        }else{
            return false;
        }
    }

    /**
     * indica si el grafo tiene ciclos o caminos cerrados
     * @return regresa true si tiene camino cerrado y false si no
     */
    public boolean hayCaminoCerrado(){
        ListaDinamica OT = ordenacionTopologica();
        if (OT== null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Indica si hay un camino simple entre dos nodos
     * @param origen es el nodo donde se inicia el recorrido
     * @param destino es el nodo donde termina el recorrido
     * @return regresa true si es camino simple y false si no lo es
     */
    public boolean esCaminoSimple(Object origen, Object destino){
        ListaEstatica marcados = new ListaEstatica(vertices.numeroElementos());
        marcados.rellenar(false,vertices.numeroElementos());
        int numAdyacentes = adyacentes(origen);
        int numRecorridos=0;
        boolean repetidos=false;
        if (esAdyacente( origen, destino)){
            return true;
        }else{
            Integer indiceOrigen = (Integer) vertices.buscar(origen);
            Integer indiceActual = indiceOrigen;
            if (indiceOrigen!=null){
                for (int col=0; col< aristas.obtenerColumnas(); col++){
                    double arista = (double) aristas.obtener(indiceActual, col);
                    if (arista>0.0){
                        if (!(boolean)marcados.obtener(col)){
                            marcados.cambiar(col,true);
                            indiceActual = col;
                            col=0;
                            if (esAdyacente(vertices.obtener(indiceActual), destino)){
                                if (repetidos){
                                    return false;
                                }else{
                                    return true;
                                }
                            }
                        }else{
                           repetidos=true;
                        }
                    }
                    if (col == aristas.obtenerColumnas()-1) {
                        indiceActual =indiceOrigen;
                        numRecorridos++;
                        if (numRecorridos<numAdyacentes){
                            col=0;
                        }
                    }
                }
                if (numAdyacentes==0 || repetidos){
                    return false;
                }else{
                    return true;
                }
            }else{
                return false;
            }
        }
    }

    /**
     * indica si el grafo es dirigido
     * @return regres true si es dirigiso y false si no lo es
     */
    public boolean esDirigido(){
        if(!vertices.vacia()){
            boolean retorno =true;
            for(int pos=0;pos<vertices.numeroElementos();pos++){
                Object vertice=vertices.obtener(pos);
                for(int pos2=0;pos2<vertices.numeroElementos();pos2++){
                    Object vertice2=vertices.obtener(pos2);
                    if(vertice!=vertice2){
                        if(esAdyacente(vertice,vertice2)){
                            if(esAdyacente(vertice2,vertice)){
                                retorno=false;
                            }
                        }
                    }
                }
            }
            return retorno;
        }else {
            return false;
        }
    }

    /**
     * indica si un grafo es arbol (es conexo, no tiene ciclos y cada vertice tiene solo un padre)
     * @return regresa true si es arbol y false si no lo es
     */
    public boolean esArbol(){
        if (esConexo()){
            if (!hayCaminoCerrado()){
                for (int pos=0; pos< vertices.numeroElementos(); pos++){
                    Object vertice = vertices.obtener(pos);
                    int numAdyacentes = adyacentes(vertice);
                    int grado = gradoVertice(vertice);
                    if (grado-numAdyacentes>2){
                        return false;
                    }
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
     * enlista todas las aristas del grafo
     */
    public void listarAristas(){
        for (int fil=0; fil< aristas.obtenerRenglones();fil++ ){
            for (int col=0; col<aristas.obtenerColumnas(); col++){
                if ((double)aristas.obtener(fil,col)>0.0){
                    SalidaPorDefecto.consola("("+vertices.obtener(fil)+","+vertices.obtener(col)+","+aristas.obtener(fil,col)+")\n");
                }
            }
        }
    }

    /**
     * Enlista todas las aristas de un vertice en especifico
     * @param vertice es el vertice a buscar
     */
    public void listarAristas(Object vertice){
        Integer buscar = (Integer) vertices.buscar(vertice);
        if (buscar!=null){
            for (int fil=0; fil<aristas.obtenerColumnas(); fil++){
                if ((double)aristas.obtener(fil,buscar)>0.0){
                    SalidaPorDefecto.consola("("+vertices.obtener(fil)+","+vertices.obtener(buscar)+","+aristas.obtener(fil,buscar)+")\n");
                }
            }
            for (int col=0; col<aristas.obtenerColumnas(); col++){
                if ((double)aristas.obtener(buscar,col)>0.0){
                    SalidaPorDefecto.consola("("+vertices.obtener(buscar)+","+vertices.obtener(col)+","+aristas.obtener(buscar,col)+")\n");
                }
            }
        }
    }

    /**
     * imprime los vertices del grafo
     */
    public void listarVertices(){
        vertices.imprimir();
    }


    /**
     * Imprime los componentes conexos del grafo y si este es o no conexo
     * @param origen es el vertice de origen del cual partir el recorrido
     */
    public void imprimirComponentesConexos(Object origen){
        ListaEstatica marcados = new ListaEstatica(vertices.numeroElementos());
        marcados.rellenar(false, vertices.numeroElementos());
        int numComponentes =0;
        Integer resultado = imprimirCompConexos(origen, marcados, numComponentes);
        if (resultado!=null){
            if (resultado==0){
                SalidaPorDefecto.consola("Es conexo\n");
            }else{
                SalidaPorDefecto.consola("No es conexo\n");
            }
        }else{
            SalidaPorDefecto.consola("No es un grafo no dirigido.\n");
        }
    }

    /**
     * Metodo privado para imprimir los componentes conexos del grafo no dirigido
     * @param origen es el nodo del cual se parte
     * @param marcados es la lista de elementos actuales marcados
     * @param numComponentes es el numero de componentes
     * @return regresa el numero de componentes para indicar si es o no conexo
     */
    private Integer imprimirCompConexos(Object origen, ListaEstatica marcados, int numComponentes){
        if (!esDirigido()){
            ListaDinamica recorridos = new ListaDinamica();
            PilaEstatica pila = new PilaEstatica(vertices.numeroElementos());
            boolean todosMarcados = false;

            Integer indiceOrigen = (Integer) vertices.buscar(origen);
            if (indiceOrigen != null) {
                marcados.cambiar(indiceOrigen, true);
                pila.poner(indiceOrigen);
                while (!pila.vacio()) {
                    int indiceVerticeActual = (int) pila.quitar();
                    Vertice verticeActual = (Vertice) vertices.obtener(indiceVerticeActual);
                    recorridos.agregar(verticeActual.getContenido());
                    enpilarYMarcarVerticesAdyacentes(indiceVerticeActual, pila, marcados);
                }
            }
            if (marcados.buscarValores(true).numeroElementos()== marcados.numeroElementos()){
                SalidaPorDefecto.consola("Componente conexo: ");
                todosMarcados = true;
                recorridos.imprimir();
            }else{
                numComponentes++;
                SalidaPorDefecto.consola("Componente conexo:  ");
                for (int pos=0; pos< marcados.numeroElementos(); pos++){
                    if ((boolean)marcados.obtener(pos)){
                        SalidaPorDefecto.consola(vertices.obtener(pos)+" ");
                    }
                }
                SalidaPorDefecto.consola("\n");
            }

            if(!todosMarcados){
                for (int pos=0; pos< marcados.numeroElementos(); pos++){
                    if (!(boolean)marcados.obtener(pos)){
                        imprimirCompConexos(vertices.obtener(pos),marcados, numComponentes);
                    }
                }
            }

            return numComponentes;
        }else{
            return null;
        }
    }

    /**
     * Imprime los componenetes fuertes de un grado dirigido
     * @param origen es el vertice del cual se parte
     */
    public void imprimirComponentesFuertes(Object origen){
        int numComp =0;
        imprimirCompFuertes(origen, numComp, null);
    }

    /**
     * metodo privado para imprimir los componentes fuertes del grafo
     * @param origen es el vertice del cual se parte
     * @param numComponentes es el numero de componenetes fuertes
     * @param pilaProcesados es la pila actual de los nodos restantes por procesar
     */
    private void imprimirCompFuertes(Object origen, int numComponentes, PilaDinamica pilaProcesados){
        if (esDirigido()) {
            if (pilaProcesados==null){
                pilaProcesados = new PilaDinamica();
            }
            ListaDinamicaOrdenada compFuertes = new ListaDinamicaOrdenada(TipoOrden.INCR);
            ListaDinamicaOrdenada noComunes = new ListaDinamicaOrdenada(TipoOrden.INCR);
            ListaDinamica recorridoDescendentes = recorridoProfunidad(origen);
            aristas.transpuesta();
            ListaDinamica recorridoAscendentes = recorridoProfunidad(origen);
            aristas.transpuesta();
            if (recorridoAscendentes.numeroElementos() > recorridoDescendentes.numeroElementos()) {
                for (int pos = 0; pos < recorridoAscendentes.numeroElementos(); pos++) {
                    if (recorridoDescendentes.buscar(recorridoAscendentes.obtener(pos)) == null) {
                        noComunes.agregar(recorridoAscendentes.obtener(pos));
                        if (numComponentes==0){
                            pilaProcesados.poner(recorridoAscendentes.obtener(pos));
                        }
                    } else {
                        compFuertes.agregar(recorridoAscendentes.obtener(pos));
                    }
                }
            } else {
                for (int pos = 0; pos < recorridoDescendentes.numeroElementos(); pos++) {
                    if (recorridoAscendentes.buscar(recorridoDescendentes.obtener(pos)) == null) {
                        noComunes.agregar(recorridoDescendentes.obtener(pos));
                        if (numComponentes==0){
                            pilaProcesados.poner(recorridoDescendentes.obtener(pos));
                        }
                    } else {
                        compFuertes.agregar(recorridoDescendentes.obtener(pos));
                    }
                }
            }
            SalidaPorDefecto.consola("Componente fuertemente conexo: ");
            compFuertes.imprimir();
            if (pilaProcesados.vacio()){
                if (numComponentes==0){
                    SalidaPorDefecto.consola("Es fuertemente conexo\n");
                }else{
                    SalidaPorDefecto.consola("No es fuertemente conexo\n");
                }
            }else{
                numComponentes++;
                imprimirCompFuertes(pilaProcesados.quitar(),numComponentes,pilaProcesados);
            }
        }
    }

}
