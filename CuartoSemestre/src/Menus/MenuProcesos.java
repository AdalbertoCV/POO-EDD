package Menus;

import entradasalida.EntradaPorDefecto;
import entradasalida.SalidaPorDefecto;
import estructuraslineales.ColaEstaticaDePrioridad;
import estructuraslineales.ListaEstatica;


public class MenuProcesos {

    protected ColaEstaticaDePrioridad colaDescargas;
    protected ColaEstaticaDePrioridad colaEjecuciones;
    protected ColaEstaticaDePrioridad colaImpresiones;

    public MenuProcesos(int maximoDescargas, int maximoEjecuciones, int maximoImpresiones){
        colaDescargas = new ColaEstaticaDePrioridad(maximoDescargas);
        colaEjecuciones = new ColaEstaticaDePrioridad(maximoEjecuciones);
        colaImpresiones = new ColaEstaticaDePrioridad(maximoImpresiones);
    }

    /**
     * Pide la opcion mostrando las posibles elecciones en pantalla
     * @return regresa la opcion seleccionada
     */
    public int pedirOpcion(){
        int opcion =0;
        while ((opcion>7 || opcion<1)){
            SalidaPorDefecto.consola("Elija una opcion: \n");
            SalidaPorDefecto.consola("(1) Agregar Descarga a la cola \n");
            SalidaPorDefecto.consola("(2) Agregar ejecucion en sistema operativo a la cola\n");
            SalidaPorDefecto.consola("(3) Agregar impresion a la cola \n");
            SalidaPorDefecto.consola("(4) Atender siguiente Descarga \n");
            SalidaPorDefecto.consola("(5) Atender siguiente Ejecucion \n");
            SalidaPorDefecto.consola("(6) Atender siguiente Impresion \n");
            SalidaPorDefecto.consola("(7) Salir \n");
            opcion = EntradaPorDefecto.consolaInt();
        }
        if (opcion ==7){
            System.exit(0);
        }
        return opcion;
    }

    /**
     * Ejecuta la opcion seleccionada.
     * @param opcion es la opcion seleccionada
     */
    public void EjecutarOpcion(int opcion){
        if (opcion>0 && opcion<=3){
            ListaEstatica datos = pedirNombreYPrioridad(opcion);
            agregarACola(opcion, datos);
        }else{
            if (opcion>3 && opcion<7){
                atenderProceso(opcion);
            }
        }
    }

    /**
     * Pide el nombre y la prioridad segun una opcion seleccinada
     * @param opcion es la opcion previamente seleccionada
     * @return regresa una lista estatica con los datos ingresados
     */
    public ListaEstatica pedirNombreYPrioridad(int opcion){
        if (opcion<4 && opcion>0){
            ListaEstatica datos = new ListaEstatica(2);
            String nombreProceso="";
            int prioridadProceso=0;
            if (opcion==1){
                SalidaPorDefecto.consola("Nombre de la descarga: \n");
                nombreProceso = EntradaPorDefecto.consolaCadenas();
                SalidaPorDefecto.consola("Prioridad de la descarga: \n");
                prioridadProceso = EntradaPorDefecto.consolaInt();
            }else{
                if (opcion==2){
                    SalidaPorDefecto.consola("Nombre de la Ejecucion: \n");
                    nombreProceso = EntradaPorDefecto.consolaCadenas();
                    SalidaPorDefecto.consola("Prioridad de la Ejecucion: \n");
                    prioridadProceso = EntradaPorDefecto.consolaInt();
                }else{
                    if (opcion==3){
                        SalidaPorDefecto.consola("Nombre de la impresion: \n");
                        nombreProceso = EntradaPorDefecto.consolaCadenas();
                        SalidaPorDefecto.consola("Prioridad de la impresion: \n");
                        prioridadProceso = EntradaPorDefecto.consolaInt();
                    }
                }
            }
            datos.agregar(nombreProceso);
            datos.agregar(prioridadProceso);
            return datos;
        }else{
            return null;
        }
    }

    /**
     * Se agrega a la cola correspondiente el nuevo proceso
     * @param opcion es la opcion seleccionada previamente que define el tipo de proceso
     * @return
     */
    public boolean agregarACola(int opcion, ListaEstatica datos){
        if (opcion<4 && opcion>0){
            if (datos.obtener(0) != null && datos.obtener(1)!= null){
                if (opcion == 1){
                    colaDescargas.poner(datos.obtener(0), (Integer) datos.obtener(1));
                }else{
                    if(opcion==2){
                        colaEjecuciones.poner(datos.obtener(0), (Integer) datos.obtener(1));
                    }else{
                        if (opcion ==3){
                            colaImpresiones.poner(datos.obtener(0), (Integer) datos.obtener(1));
                        }
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
     * Se atiende el proceso siguiente segun la opcion seleccionada
     * @param opcion es la opcion previamente seleccionada
     * @return regresa true si se pudo atender un proceso y false si no
     */
    public boolean atenderProceso(int opcion){
        if (opcion>3 && opcion<7){
            if(opcion == 4){
                SalidaPorDefecto.consola("Atendiendo descarga: "+ colaDescargas.quitar() + "\n");
            }else{
                if (opcion ==5){
                    SalidaPorDefecto.consola("Atendiendo Ejecucion: "+ colaEjecuciones.quitar() + "\n");
                }else{
                    if(opcion==6){
                        SalidaPorDefecto.consola("Atendiendo Impresion: "+ colaImpresiones.quitar() + "\n");
                    }
                }
            }
            return true;
        }else{
            return false;
        }
    }
}
