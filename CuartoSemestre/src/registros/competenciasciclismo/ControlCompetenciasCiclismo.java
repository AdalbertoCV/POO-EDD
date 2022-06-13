package registros.competenciasciclismo;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaEstatica;
import estructurasnolineales.Matriz3;

public class ControlCompetenciasCiclismo {
    protected Matriz3 kilometrosRecorridos;
    protected ListaEstatica ciclistas;
    protected ListaEstatica eventos;
    protected ListaEstatica anios;

    public  ControlCompetenciasCiclismo(int numEventos, int numCiclistas, int numAnios){
        ciclistas=new ListaEstatica(numCiclistas);
        eventos=new ListaEstatica(numEventos);
        anios=new ListaEstatica(numAnios);
        kilometrosRecorridos=new Matriz3(numEventos,numCiclistas,numAnios,0.0);
    }

    public boolean agregarCiclista(Ciclista ciclista){
        Integer posicionBusqueda=(Integer)ciclistas.buscar(ciclista);
        if(posicionBusqueda==null){ //no existe, se puede agregar
            int retorno=ciclistas.agregar(ciclista);
            if(retorno==-1){ //no pudo agregarlo
                return false;
            }else{ //si pudo
                return true;
            }
        }else{  //ya existe
            return false;
        }
    }

    public boolean agregarEvento(EventoCiclismo evento){
        Integer posicionBusqueda=(Integer)eventos.buscar(evento);
        if(posicionBusqueda==null){ //si no existe
            int retorno=eventos.agregar(evento);
            if(retorno==-1){ // está llena
                return false;
            }else{ // hay espacio
                return true;
            }
        }else{//si ya existe
            return false;
        }
    }

    public boolean agregarAnio(int anio){
        Integer posicionBusqueda=(Integer) anios.buscar(anio);
        if(posicionBusqueda==null){ //no existe, puedo agregarlo
            int retorno=anios.agregar(anio);
            if(retorno==-1){ //hubo error
                return false;
            }else{ // todo con éxito
                return true;
            }
        }else{ //ya existe, no puedo
            return false;
        }
    }

    public boolean agregarKilometrosRecorridos(String evento, int ciclista, int anio, double kilometros){
        //buscar los índices que les corresponden a los datos de usuario dados como argumentos
        ListaEstatica indicesBusqueda=obtenerIndicesCeldas(evento, ciclista,anio);
        if(indicesBusqueda!=null){ //se puede agregar
            return kilometrosRecorridos.cambiar((int)indicesBusqueda.obtener(0),(int)indicesBusqueda.obtener(1),
                    (int)indicesBusqueda.obtener(2),kilometros);
        }else{ //algún datos no existe
            return false;
        }
    }

    private ListaEstatica obtenerIndicesCeldas(String evento, int ciclista, int anio){
        Integer indiceEvento=(Integer)eventos.buscar(evento);
        Integer indiceCiclista=(Integer)ciclistas.buscar(ciclista);
        Integer indiceAnio=(Integer)anios.buscar(anio);

        if(indiceEvento!=null && indiceCiclista!=null && indiceAnio!=null){ //los índices son válidos, existen los datos
            ListaEstatica indicesArgumentos=new ListaEstatica(3);
            indicesArgumentos.agregar(indiceEvento.intValue());
            indicesArgumentos.agregar(indiceCiclista.intValue());
            indicesArgumentos.agregar(indiceAnio.intValue());
            return indicesArgumentos;
        }else{ //no existe alguno o todos
            return null;
        }
    }

    public void imprimirListadoEventos(){
        eventos.imprimirOI();
    }

    public void imprimirListadoCiclistas(){
        ciclistas.imprimirOI();
    }

    public void imprimirListadoAnios(){
        anios.imprimirOI();
    }

    public void imprimirListadoKilometrosRecorridos(){
        kilometrosRecorridos.imprimirXColumnas();
    }

    public void imprimirDatosCompetencias(){
        SalidaPorDefecto.consola("Los datos de los ciclistas y sus kilòmetros recorridos son los siguientes\n");
        SalidaPorDefecto.consola("Los eventos en los que se participa: \n");
        imprimirListadoEventos();
        SalidaPorDefecto.consola("Los ciclistas participantes son: \n");
        imprimirListadoCiclistas();
        SalidaPorDefecto.consola("Los años de partticipación son:\n");
        imprimirListadoAnios();
        SalidaPorDefecto.consola("Y los kilómetros recorridos por estas competencias son:\n");
        imprimirListadoKilometrosRecorridos();
        SalidaPorDefecto.consola("\n");
    }
    //Consultas frecuentes que pueden hacerse a los datos proporcionados ya en el almacenamiento
    //Obtener los kilómetros recorridos en la competencia "Carrera Centenario" por el ciclista Pedro Pérez, en los años 2005, 2009, 2010
    //Obtener los kilómetros recorridos en la competencia "Carrera Centenario" por el ciclista Pedro Pérez, en los años 2005
    //Obtener los kilómetros recorridos en la competencia "Carrera Centenario" en los años 2005

    public double kilometrosXEventoXCiclista(String evento, int ciclista, ListaEstatica aniosCompetencias){
        double kmsRecorridos=0.0;

        //Como existe varios años, tengo que obtener los datos de cada año.
        for(int porCadaAnio=0;porCadaAnio<aniosCompetencias.numeroElementos();porCadaAnio++){
            //sacamos el valor de cada años de un ciclista de un evento
            double valorCelda=kilometrosXEventoXCiclistaXAnio(evento, ciclista, (int)aniosCompetencias.obtener(porCadaAnio));
            if(valorCelda!=-1) { // si me regresa unos kilómetros válidos
                //acumula kilómetros recorridos por cada año, por un evento, por un clista
                kmsRecorridos = kmsRecorridos + valorCelda;
            }
        }
        return kmsRecorridos;
    }

    public double kilometrosXEventoXCiclistaXAnio(String evento, int ciclista, int anio){
        ListaEstatica indicesBusqueda=obtenerIndicesCeldas(evento,ciclista,anio);

        if(indicesBusqueda!=null){ //si hay valido
            //sacar los ídncies y retornarlos como valores de celda
            return (double)kilometrosRecorridos.obtener((int)indicesBusqueda.obtener(0), (int)indicesBusqueda.obtener(1),
                    (int)indicesBusqueda.obtener(2));
        }else{ //invalido
            return -1.0;
        }
    }

}
