package estructuraslineales;

import entradasalida.SalidaPorDefecto;

public class ColaEstatica implements LoteAlmacenamiento {
    protected Object datos[];
    protected int primero;
    protected int ultimo;
    protected int MAXIMO;

    public ColaEstatica(int maximo){
        MAXIMO=maximo;
        datos=new Object[MAXIMO];
        primero=-1;
        ultimo=-1;
    }

    @Override
    public boolean vacio(){
        if(primero==-1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean lleno(){
        if((primero==0 && ultimo== (MAXIMO -1)) || primero== (ultimo+1)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean poner(Object valor){
        if(lleno()==false){ //hay espacio
            if(vacio()==true){  //a)
                primero=0;
                ultimo=0;
                //datos[ultimo]=valor;
            }else if(ultimo==(MAXIMO-1)){ //d
                ultimo=0;
                //datos[ultimo]=valor;
            }else{  //c y e
                ultimo = ultimo +1;
                //datos[ultimo]=valor;
            }
            datos[ultimo]=valor;
            return true;
        }else{ //no hay espacio  b)
            return false;
        }
    }

    @Override
    public Object quitar(){
        if(vacio()==false){ //hay datos
            Object valorEliminado=datos[primero];
            if(primero==ultimo){ //c)
                //valorEliminado=datos[primero];
                primero=-1;
                ultimo=-1;
            }else if(primero==(MAXIMO-1)){  //d
                //valorEliminado=datos[primero];
                primero=0;
            }else{ //b) y e)
                //valorEliminado=datos[primero];
                primero ++;
            }
            return valorEliminado;
        }else{  //a
            return null;
        }
    }

    @Override
    public void imprimir(){
        if(vacio()==false){ //hay algo, a)
            if(primero<=ultimo){ //b, comportamiento lineal
                for(int indice=primero;indice<=ultimo;indice++){
                    SalidaPorDefecto.consola(""+datos[indice]+ " ");
                }
            }else{ //c, comportamiento circular
                for(int indice=primero;indice<=(MAXIMO -1);indice++){
                    SalidaPorDefecto.consola(""+datos[indice]+ " ");
                }
                for(int indice=0;indice<=ultimo;indice++){
                    SalidaPorDefecto.consola(""+datos[indice]+ " ");
                }
            }
        }
    }

    @Override
    public Object verUltimo(){
        if(vacio()==false){
            return datos[ultimo];
        }else{
            return null;
        }
    }

    public Object verPrimero(){
        if(vacio()==false){
            return datos[primero];
        }else{
            return null;
        }
    }
}
