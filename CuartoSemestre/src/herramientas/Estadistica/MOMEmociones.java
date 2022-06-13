package herramientas.Estadistica;

import estructuraslineales.ListaDinamica;
import estructuraslineales.ListaDinamicaClave;
import estructuraslineales.auxiliares.NodoClave;
import estructurasnolineales.Matriz2Numerica;

/**
 * Clase MOMEmociones: Modelo oculto de markov para calcular probabilidades de emociones y actividades
 * @author Adalberto Cerrillo Vazquez
 * @author Alexia Hernandez Martinez
 * @version 1.0
 */
public class MOMEmociones {
    /**
     * es el nombre del primer estado (emocion)
     */
    private String nomEstado1;
    /**
     * es el nombre del segundo estado (emocion)
     */
    private String nomEstado2;
    /**
     * es el nombre del tercer estado (emocion)
     */
    private String nomEstado3;
    /**
     * es el nombre del cuarto estado (emocion)
     */
    private String nomEstado4;
    /**
     * Es la lista donde se almacenaran los vertices del grafo
     */
    private ListaDinamica vertices;
    /**
     * Es la lista con los valores de la probabilidad inicial
     */
    private ListaDinamicaClave probabilidadInicial;
    /**
     * Es la matriz que almacena las aristas con los valores de transicion
     */
    private Matriz2Numerica aristasTransiciones;
    /**
     * nombre de la actividad 1
     */
    private String nombreA1;
    /**
     * nombre de la actividad 2
     */
    private String nombreA2;
    /**
     * nombre de la actividad 3
     */
    private String nombreA3;
    /**
     * nombre de la actividad 4
     */
    private String nombreA4;

    /**
     * constructor de la clase para incicializar las variables
     */
    public MOMEmociones(){
        nomEstado1 = "Alegria";
        nomEstado2 = "Tristeza";
        nomEstado3 = "Estres";
        nomEstado4 = "Tranquilidad";
        nombreA1 = "Cantar";
        nombreA2 = "Leer";
        nombreA3 = "Discutir";
        nombreA4 = "Llorar";
        vertices = new ListaDinamica();
        probabilidadInicial = new ListaDinamicaClave();
        aristasTransiciones = new Matriz2Numerica(4,4);
        inicializarValores();
    }

    /**
     * Metodo para inicializar (ingresar) todos los valores requeridos
     */
    private void inicializarValores(){
        iniciarValoresPIniciales();
        agregarVertices();
        iniciarValoresTransicion();
    }

    /**
     * Metodo para ingresar los valores de las probabilidades iniciales de cada estado
     */
    private void iniciarValoresPIniciales(){
        probabilidadInicial.agregar(nomEstado1, .3);
        probabilidadInicial.agregar(nomEstado2, .2);
        probabilidadInicial.agregar(nomEstado3, .1);
        probabilidadInicial.agregar(nomEstado4, .4);
    }

    /**
     * metodo para ingresar valores de emision y agregar los vertices a la lista de vertices
     */
    private void agregarVertices(){
        ListaDinamicaClave alegria = new ListaDinamicaClave();
        ListaDinamicaClave tristeza = new ListaDinamicaClave();
        ListaDinamicaClave estres = new ListaDinamicaClave();
        ListaDinamicaClave tranquilidad = new ListaDinamicaClave();


        alegria.agregar(nombreA1, .6);
        alegria.agregar(nombreA2, .2);
        alegria.agregar(nombreA3, .1);
        alegria.agregar(nombreA4, .1);
        NodoClave nodoAlegria = new NodoClave(alegria,nomEstado1);


        tristeza.agregar(nombreA1,.2);
        tristeza.agregar(nombreA2,.2);
        tristeza.agregar(nombreA3,.2);
        tristeza.agregar(nombreA4,.4);
        NodoClave nodoTristeza = new NodoClave(tristeza, nomEstado2);

        estres.agregar(nombreA1, .2);
        estres.agregar(nombreA2, .1);
        estres.agregar(nombreA3, .4);
        estres.agregar(nombreA4, .3);
        NodoClave nodoEstres = new NodoClave(estres, nomEstado3);


        tranquilidad.agregar(nombreA1,.3);
        tranquilidad.agregar(nombreA2,.5);
        tranquilidad.agregar(nombreA3,.1);
        tranquilidad.agregar(nombreA4,.1);
        NodoClave nodoTranquilidad = new NodoClave(tranquilidad, nomEstado4);

        vertices.agregar(nodoAlegria);
        vertices.agregar(nodoTristeza);
        vertices.agregar(nodoEstres);
        vertices.agregar(nodoTranquilidad);
    }

    /**
     * Metodo para agregar las aristas correspondientes con los valores de transicion
     */
    private void iniciarValoresTransicion(){
        // agregando aristas al grafo

        aristasTransiciones.cambiar(0,0,.5); // de alegria a alegria
        aristasTransiciones.cambiar(0,1,.1); // de alegria a tristeza
        aristasTransiciones.cambiar(0,2,.1); // de alegria a estres
        aristasTransiciones.cambiar(0,3,.3); // de alegria a tranquilidad

        aristasTransiciones.cambiar(1,0,.1); // de tristeza a alegria
        aristasTransiciones.cambiar(1,1,.5); // de tristeza a tristeza
        aristasTransiciones.cambiar(1,2,.3); // de tristeza a estres
        aristasTransiciones.cambiar(1,3,.1); // de tristeza a tranquilidad

        aristasTransiciones.cambiar(2,0,.1); // de estres a alegria
        aristasTransiciones.cambiar(2,1,.4); // de estres a tristeza
        aristasTransiciones.cambiar(2,2,.4); // de estres a estres
        aristasTransiciones.cambiar(2,3,.1); // de estres a tranquilidad

        aristasTransiciones.cambiar(3,0,.4); // de tranquilidad a alegria
        aristasTransiciones.cambiar(3,1,.2); // de tranquilidad a tristeza
        aristasTransiciones.cambiar(3,2,.2); // de tranquilidad a estres
        aristasTransiciones.cambiar(3,3,.2); // de tranquilidad a tranquilidad
    }

    /**
     * Calculamos la probabilidad inicial de un estado
     * @param estado es el estado a consultar su probabilidad inicial
     * @return regresa el valor de la probabilidad inicial
     */
    public Double CalcularProbabilidadInicial(String estado){
        Object busqueda =  probabilidadInicial.buscar(estado);
        if (busqueda!=null){
            return (Double) busqueda;
        }else{
            return null;
        }
    }

    /**
     * Calculamos la probabilidad de que se realice una actividad segun un estado de animo
     * @param estadoInicial es el estado en el que se encuentra la persona
     * @param actividad es la actividad a consultar su probabilidad
     * @return regresa el valor de la probabilidad
     */
    public Double CalcularProbabilidadActividad(String estadoInicial, String actividad){
        for (int pos=0; pos< vertices.numeroElementos(); pos++){
            NodoClave nodoActual = (NodoClave) vertices.obtener(pos);
            if (nodoActual.getClave().toString().equals(estadoInicial)){
                Object estado = probabilidadInicial.buscar(estadoInicial);
                if (estado!=null){
                    ListaDinamicaClave listaNodo = (ListaDinamicaClave) nodoActual.getContenido();
                    Object buscarActividad = listaNodo.buscar(actividad);
                    if (buscarActividad!=null){
                        return (Double) buscarActividad;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Calculamos la probabilidad de que un estado se de dado uno previo
     * @param estadoInicial es el estado anterior al cual queremos calcular
     * @param estado es el estado a calcular a probabilidad de transicion
     * @return regresa el valor de la probabilidad
     */
    public Double CalcularProbabilidadEstado(String estadoInicial, String estado){
        int pos=-1;
        int pos2=-1;
        if(estadoInicial!=null && estado!=null){
            for(int contador=0;contador<vertices.numeroElementos();contador++){
                NodoClave nodoActual= (NodoClave) vertices.obtener(contador);
                if(nodoActual.getClave().toString().equals(estadoInicial)){
                    pos=contador;
                }
                if(nodoActual.getClave().toString().equals(estado)){
                    pos2=contador;
                }
            }
            if(pos!=-1 && pos2!=-1){
                return (Double) aristasTransiciones.obtener(pos,pos2);
            }else{
                return null;
            }
        }else{
            return null;
        }

    }

    /**
     * Calculamos la probabilidad de que una cierta secuencia de estado se de
     * @param secuencia es la cadena de la secuencia
     * @return regresa el valor de la probabilidad
     */
    public Double CalcularProbabilidadSecuencia(String secuencia) {
        String[] listaVertices=secuencia.split(",");
        Double total=CalcularProbabilidadInicial(listaVertices[0]);
        for(int contador=0;contador<listaVertices.length-1;contador++){
            if(total!=null && CalcularProbabilidadEstado(listaVertices[contador],listaVertices[contador+1])!=null){
                total=total*CalcularProbabilidadEstado(listaVertices[contador],listaVertices[contador+1]);
            }else{
                return null;
            }


        }
        return total;
    }
}