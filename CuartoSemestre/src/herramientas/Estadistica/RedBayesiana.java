package herramientas.Estadistica;

import entradasalida.EntradaPorDefecto;
import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaEstatica;
import estructuraslineales.ListaEstaticaNumerica;
import estructuraslineales.auxiliares.NodoClave;
import estructurasnolineales.Matriz2;
import estructurasnolineales.Matriz2Numerica;

/**
 * Clase RedBayesiana
 * @author Alexia Hernandez Martinez
 * @author Adalberto Cerrillo Vazquez
 * @version  1.0
 */
public class RedBayesiana {
    /**
     * Donde guardar los vertices
     */
    protected ListaEstatica vertices;
    /**
     * Donde guardar los aristas
     */
    protected Matriz2 aristas;

    /**
     * Donde se guardan las claves de los vertices para buscar su posicion
     */
    protected ListaEstatica posicionesVertices;

    /**
     * Constructor de la clase
     * @param cantidadVertices es la cantidad de vertices que contendra el grafo
     */
    public RedBayesiana(int cantidadVertices) {
        vertices = new ListaEstatica(cantidadVertices);
        posicionesVertices = new ListaEstatica(cantidadVertices);
        aristas = new Matriz2(cantidadVertices, cantidadVertices, 0.0);
    }

    /**
     * metodo para agregar un nuevo vertice al grafo
     * @param clave es la clave del nuevo vertice
     * @param padre es el primer padre del vertice (si lo tiene) puede enviarse como null
     * @param padre2 es el segundo padre del vertice (si lo tiene) puede enviarse como null
     * @return regresa true si se pudo agregar y false si no se pudo
     */
    public boolean agregarVertice(Object clave, Object padre, Object padre2) {
        if (padre != null && padre2 != null) {
            if (posicionesVertices.buscar(padre) != null && posicionesVertices.buscar(padre2) != null) {
                Matriz2Numerica contenido = agregarProbabilidades(clave, padre, padre2);
                NodoClave vertice = new NodoClave(contenido, clave);
                vertices.agregar(vertice);
                posicionesVertices.agregar(clave);
                aristas.cambiar((Integer) posicionesVertices.buscar(padre), (Integer) posicionesVertices.buscar(clave), 1.0);
                aristas.cambiar((Integer) posicionesVertices.buscar(padre2), (Integer) posicionesVertices.buscar(clave), 1.0);
                return true;

            }
        } else {
            if ((padre != null && posicionesVertices.buscar(padre) != null) || (padre2 != null && posicionesVertices.buscar(padre2) != null)) {
                if (padre != null) {
                    Matriz2Numerica contenido = agregarProbabilidades(clave, padre, null);
                    NodoClave vertice = new NodoClave(contenido, clave);
                    vertices.agregar(vertice);
                    posicionesVertices.agregar(clave);
                    aristas.cambiar((Integer) posicionesVertices.buscar(padre), (Integer) posicionesVertices.buscar(clave), 1.0);
                    return true;

                } else {
                    Matriz2Numerica contenido = agregarProbabilidades(clave, padre2, null);
                    NodoClave vertice = new NodoClave(contenido, clave);
                    vertices.agregar(vertice);
                    posicionesVertices.agregar(clave);
                    aristas.cambiar((Integer) posicionesVertices.buscar(padre2), (Integer) posicionesVertices.buscar(clave), 1.0);
                    return true;
                }
            } else {
                Matriz2Numerica contenido = agregarProbabilidades(clave, null, null);
                NodoClave vertice = new NodoClave(contenido, clave);
                vertices.agregar(vertice);
                posicionesVertices.agregar(clave);
                return true;
            }

        }
        return false;

    }

    /**
     * se agregan las probabilidades de un vertice de forma interactiva con el usuario (por teclado)
     * @param clave es la clave del vertice
     * @param Padre es primer padre del vertice (si lo tiene) puede enviarse como null
     * @param Padre2 es primer padre del vertice (si lo tiene) puede enviarse como null
     * @return regresa una matriz llena de las probabilidades elegidas por el usuario
     */
    private Matriz2Numerica agregarProbabilidades(Object clave, Object Padre, Object Padre2) {
        if (Padre == null && Padre2 == null) {//chica
            Matriz2Numerica matriz = new Matriz2Numerica(1, 2);
            ListaEstaticaNumerica valores = new ListaEstaticaNumerica(2);
            SalidaPorDefecto.consola("Cual es la probabilidad de la primera variante de: " + clave);
            Double valor = EntradaPorDefecto.consolaDouble();
            valores.agregar(valor);
            SalidaPorDefecto.consola("Cual es la probabilidad de la segunda variante de: " + clave);
            Double valor2 = EntradaPorDefecto.consolaDouble();
            valores.agregar(valor2);
            matriz.agregarRenglon(valores);
            return matriz;

        } else {
            if (Padre2 == null) {//mediana
                Matriz2Numerica matriz = new Matriz2Numerica(2, 2);
                ListaEstaticaNumerica afirmacion = new ListaEstaticaNumerica(2);
                ListaEstaticaNumerica negacion = new ListaEstaticaNumerica(2);

                SalidaPorDefecto.consola("Cual es la probabilidad de que tenga " + clave + " si tiene la primera variante de " + Padre);
                Double valor = EntradaPorDefecto.consolaDouble();
                SalidaPorDefecto.consola("Cual es la probabilidad de que tenga " + clave + " si tiene la segunda variante de " + Padre);
                Double valor2 = EntradaPorDefecto.consolaDouble();
                afirmacion.agregar(valor);
                afirmacion.agregar(valor2);

                SalidaPorDefecto.consola("Cual es la probabilidad de que no tenga " + clave + " si no tiene la primera variante de " + Padre);
                Double valor3 = EntradaPorDefecto.consolaDouble();
                SalidaPorDefecto.consola("Cual es la probabilidad de que no tenga " + clave + " si no tiene la segunda variante de " + Padre);
                Double valor4 = EntradaPorDefecto.consolaDouble();
                negacion.agregar(valor3);
                negacion.agregar(valor4);
                matriz.agregarRenglon(afirmacion);
                matriz.agregarRenglon(negacion);
                return matriz;

            } else {//grande
                Matriz2Numerica matriz = new Matriz2Numerica(2, 4);
                ListaEstaticaNumerica afirmacion = new ListaEstaticaNumerica(4);
                ListaEstaticaNumerica negacion = new ListaEstaticaNumerica(4);

                SalidaPorDefecto.consola("Cual es la probabilidad de que si tenga " + clave + " si si tiene la primera variante de " + Padre);
                Double valor = EntradaPorDefecto.consolaDouble();
                SalidaPorDefecto.consola("Cual es la probabilidad de que si tenga " + clave + " si si tiene la segunda variante de " + Padre);
                Double valor2 = EntradaPorDefecto.consolaDouble();
                SalidaPorDefecto.consola("Cual es la probabilidad de que si tenga " + clave + " si si tiene la primera variante de " + Padre2);
                Double valor3 = EntradaPorDefecto.consolaDouble();
                SalidaPorDefecto.consola("Cual es la probabilidad de que si tenga " + clave + " si si tiene la segunda variante de " + Padre2);
                Double valor4 = EntradaPorDefecto.consolaDouble();
                afirmacion.agregar(valor);
                afirmacion.agregar(valor2);
                afirmacion.agregar(valor3);
                afirmacion.agregar(valor4);


                SalidaPorDefecto.consola("Cual es la probabilidad de que no tenga " + clave + " si no tiene la primera variante de " + Padre);
                Double valor5 = EntradaPorDefecto.consolaDouble();
                SalidaPorDefecto.consola("Cual es la probabilidad de que si tenga " + clave + " si no tiene la segunda variante de " + Padre);
                Double valor6 = EntradaPorDefecto.consolaDouble();
                SalidaPorDefecto.consola("Cual es la probabilidad de que si tenga " + clave + " si no tiene la primera variante de " + Padre2);
                Double valor7 = EntradaPorDefecto.consolaDouble();
                SalidaPorDefecto.consola("Cual es la probabilidad de que si tenga " + clave + " si no tiene la segunda variante de " + Padre2);
                Double valor8 = EntradaPorDefecto.consolaDouble();
                negacion.agregar(valor5);
                negacion.agregar(valor6);
                negacion.agregar(valor7);
                negacion.agregar(valor8);

                matriz.agregarRenglon(afirmacion);
                matriz.agregarRenglon(negacion);
                return matriz;
            }
        }

    }

    /**
     * imprime la informacion del grafo
     */
    public void imprimir(){
        for(int contador=0; contador<posicionesVertices.numeroElementos();contador++){
            NodoClave temporal= (NodoClave) vertices.obtener(contador);
            Matriz2Numerica temp= (Matriz2Numerica) temporal.getContenido();
            SalidaPorDefecto.consola("\n"+temporal.getClave()+"\n");
            temp.imprimirXRenglones();
        }
        SalidaPorDefecto.consola("\nAristas:\n");
        aristas.imprimirXRenglones();
    }

    /**
     * obtenemos una probabilidad especificada por el usuario
     * @param calcular es el vertice a obtener la probabilidad
     */
    public void obtenerProbabilidad(Object calcular){
        if(posicionesVertices.buscar(calcular)!=null){
            Integer posicion= (Integer) posicionesVertices.buscar(calcular);
            NodoClave mostrar=(NodoClave) vertices.obtener(posicion);
            Matriz2Numerica mostrarFinal=(Matriz2Numerica) mostrar.getContenido();
            SalidaPorDefecto.consola("\n"+calcular+"\n");
            mostrarFinal.imprimirXRenglones();
        }else{
            SalidaPorDefecto.consola("\nNo se encuentra "+calcular+"\n");
        }
    }

    /**
     * calcular una probabilidad compuesta solicitada por el usuario
     * @param calcular es la primera probabilidad a obtener
     * @param Calcular2 es la segunda probabilidad a obtener
     */
    public void calcularProbabilidad(Object calcular,Object Calcular2) throws Exception {
        if(posicionesVertices.buscar(calcular)!=null && posicionesVertices.buscar(Calcular2)!=null){
            Integer posicion1=(Integer) posicionesVertices.buscar(calcular);
            Integer posicion2=(Integer) posicionesVertices.buscar(Calcular2);
            NodoClave temporal1=(NodoClave) vertices.obtener(posicion1);
            NodoClave temporal2=(NodoClave) vertices.obtener(posicion2);
            Matriz2Numerica temp1= (Matriz2Numerica) temporal1.getContenido();
            Matriz2Numerica temp2= (Matriz2Numerica) temporal2.getContenido();
            if(temp1.obtenerColumnas()==temp2.obtenerRenglones()){
                Matriz2Numerica clonada=(Matriz2Numerica) temp1.clonarNumerica();
                clonada=clonada.Multiplicacion(temp2);
                SalidaPorDefecto.consola("\nLa probabilidad de que tenga "+calcular+" y "+Calcular2+" se define en la tablita\n");
                clonada.imprimirXRenglones();

            }else{
                if(temp2.obtenerColumnas()==temp1.obtenerRenglones()){
                    Matriz2Numerica clonada=(Matriz2Numerica) temp2.clonarNumerica();
                    clonada=clonada.Multiplicacion(temp1);
                    SalidaPorDefecto.consola("\nLa probabilidad de que tenga "+calcular+" y "+Calcular2+" se define en la tablita\n");
                    clonada.imprimirXRenglones();
                }else{
                    SalidaPorDefecto.consola("\n Ese calculo no es posible\n");
                }


            }
        }else{
            SalidaPorDefecto.consola("\nNo se encuentra alguna de las opciones ingresadas\n");
        }
    }



}
