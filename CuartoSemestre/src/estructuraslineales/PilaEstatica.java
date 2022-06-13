package estructuraslineales;

public class PilaEstatica implements LoteAlmacenamiento{
    public ListaEstatica pila;

    public PilaEstatica(int maximo){
        pila=new ListaEstatica(maximo);
    }

    @Override
    public boolean vacio(){
        return pila.vacia();
    }

    @Override
    public boolean lleno(){
        return pila.llena();
    }

    @Override
    public boolean poner(Object valor){
        int valorRetorno=pila.agregar(valor);
        if(valorRetorno>=0){
            return true;
        }else{ //retorna -1
            return false;
        }
    }

    @Override
    public Object quitar(){
        return pila.eliminar();
    }

    @Override
    public void imprimir(){
        pila.imprimir();
    }

    @Override
    public Object verUltimo(){
        return pila.obtener(pila.ultimo);
    }

    public int numeroElementos(){
        return pila.numeroElementos();
    }
}
