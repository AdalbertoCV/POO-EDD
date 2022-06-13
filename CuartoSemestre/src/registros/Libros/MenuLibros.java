package registros.Libros;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaEstaticaOrdenada;

public class MenuLibros {

    /**
     *
     * @param lista es la lista a la que se le agregara el termino
     * @param termino es el termino a agregar
     * @return regresa true si se pudo agregar y false si no se pudo
     */
    public boolean agregarTermino(ListaEstaticaOrdenada lista, Termino termino){
        int resultado = lista.agregar(termino);
        if(resultado <0){
            return false;
        }else{
            return true;
        }
    }

    /**
     *
     * @param lista es la lista a la que se le agregara el subtermino
     * @param subtermino es el subtermino a agregar
     * @return regresa true si se pudo agregar y false si no se pudo
     */
    public boolean agregarSubTermino(ListaEstaticaOrdenada lista, Subtermino subtermino){
        int resultado = lista.agregar(subtermino);
        if(resultado <0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * lista todos los terminos dentro de la lista ordenanda
     * @param lista es la lista a imprimir
     */
    public void Listar(ListaEstaticaOrdenada lista){
        lista.imprimir();
    }

    /**
     * Despliega el listado de los terminos unicamente del rango indicado
     * @param lista es la lista a mostrar
     * @param letraIncio es el inicio del rango
     * @param letraFinal es el final del rango
     */
    public void ListarPorRango(ListaEstaticaOrdenada lista, String letraIncio, String letraFinal){
        for (int posicion =0; posicion< lista.numeroElementos(); posicion++){
            if ((lista.getDatos()[posicion].toString().charAt(0) == letraIncio.charAt(0))||lista.getDatos()[posicion].toString().charAt(0) == letraFinal.charAt(0)){
                SalidaPorDefecto.consola(lista.getDatos()[posicion].toString()+"\n");
            }
        }
    }

    /**
     * consultar la definicion del termino por nombre
     * @param lista es la lista donde se buscara el termino
     * @param nombreTermino es el termino a buscar
     */
    public void ConsultarTermino(ListaEstaticaOrdenada lista, String nombreTermino){
        for (int posicion =0; posicion< lista.numeroElementos(); posicion++){
            if (lista.getDatos()[posicion].toString().equalsIgnoreCase(nombreTermino)){
                Termino termino = (Termino) lista.obtener(posicion);
                SalidaPorDefecto.consola( termino.getDefinicion()+ "\n");
            }
        }
    }
    /**
     * consultar la definicion del subtermino por nombre
     * @param lista es la lista donde se buscara el subtermino
     * @param nombreTermino es el subtermino a buscar
     */
    public void ConsultarSubTermino(ListaEstaticaOrdenada lista, String nombreTermino){
        for (int posicion =0; posicion< lista.numeroElementos(); posicion++){
            if (lista.getDatos()[posicion].toString().equalsIgnoreCase(nombreTermino)){
                Subtermino subtermino = (Subtermino) lista.obtener(posicion);
                SalidaPorDefecto.consola( subtermino.getDefinicion()+ "\n");
            }
        }
    }
}
