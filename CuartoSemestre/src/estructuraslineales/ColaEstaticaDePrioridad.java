package estructuraslineales;

import entradasalida.SalidaPorDefecto;

public class ColaEstaticaDePrioridad{
    protected int MAXIMO;
    protected Object[] datos;
    protected int[] prioridad;
    protected int ultimo;

    public ColaEstaticaDePrioridad(int maximo) {
        MAXIMO = maximo;
        datos = new Object[maximo];
        prioridad = new int[maximo];
        ultimo=0;
    }

    public boolean vacio(){
        if (numeroElementos()==0){
            return true;
        }else{
            return false;
        }
    }

    public boolean lleno(){
        if (numeroElementos() == MAXIMO){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Agrega un elemento a la cola
     * @param valor es el valor a agregar
     * @param prioridadValor es la prioridad que se le dara a el valor al momento de atender
     * @return regresa true si se pudo agregar y false si no se pudo
     */
    public boolean poner(Object valor, int prioridadValor){
        if (lleno()){
            return false;
        }else{
            datos[ultimo] = valor;
            prioridad[ultimo] = prioridadValor;
            ultimo++;
            return true;
        }
    }

    /**
     * Quita el elemento con mayor prioridad de la cola
     * @return regresa el elemento quitado o null si no se pudo quitar nada
     */
    public Object quitar(){
        if (!vacio()){
            int[]  prioridadesNuevo = new int[prioridad.length];
            Object[] arregloNuevo = new Object[datos.length];
            Object elementoaQuitar = datos[posPrioridadMayor()];
            for (int pos = 0; pos < datos.length; pos++) {
                if(datos[pos] == elementoaQuitar){
                    for(int posArregloNuevo = 0; posArregloNuevo < pos; posArregloNuevo++){
                        arregloNuevo[posArregloNuevo] = datos[posArregloNuevo];
                    }
                    for(int indiceCambio = pos; indiceCambio < datos.length - 1; indiceCambio++){
                        arregloNuevo[indiceCambio] = datos[indiceCambio+1];
                    }

                    for(int posArregloNuevo = 0; posArregloNuevo < pos; posArregloNuevo++){
                        prioridadesNuevo[posArregloNuevo] = prioridad[posArregloNuevo];
                    }
                    for(int indiceCambio = pos; indiceCambio < prioridad.length - 1; indiceCambio++){
                        prioridadesNuevo[indiceCambio] = prioridad[indiceCambio+1];
                    }
                }
            }
            ultimo--;
            prioridad = prioridadesNuevo;
            datos = arregloNuevo;
            return elementoaQuitar;
        }else{
            return null;
        }
    }

    /**
     * Imprime la cola
     */
    public void imprimir(){
        for (int pos=0; pos< datos.length; pos++){
            if (datos[pos]!= null){
                SalidaPorDefecto.consola(""+ datos[pos]+" ");
            }
        }
        SalidaPorDefecto.consola("\n");
    }

    /**
     * Obtenemos la posicion de la prioridad mayor en la cola
     * @return regresa la posicion de el valor de mayor prioridad
     */
    public int posPrioridadMayor(){
        int posMayor = 0;
        int mayor = prioridad[0];
        for (int pos =0; pos< prioridad.length; pos++){
            if (prioridad[pos] > mayor){
                posMayor = pos;
            }
        }
        return posMayor;
    }

    /**
     * obtenemos el numero actual de elementos de la cola
     * @return regresa el numero
     */
    public int numeroElementos(){
        return ultimo;
    }

    public Object verUltimo(){
        return datos[ultimo];
    }

    public Object verPrimero(){
        if (!vacio()){
            return datos[0];
        }else{
            return null;
        }
    }

}
