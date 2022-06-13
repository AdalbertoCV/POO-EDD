package estructuraslineales;

public class PilaDinamica implements LoteAlmacenamiento{
    public ListaDinamica pila;

    public PilaDinamica(){
        pila = new ListaDinamica();
    }
    @Override
    public boolean vacio() {
        return pila.vacia();
    }

    @Override
    public boolean lleno() {
        return false;
    }

    @Override
    public boolean poner(Object valor) {
        int valorRetorno=pila.agregar(valor);
        if(valorRetorno>=0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Object quitar() {
        return pila.eliminar();
    }

    @Override
    public void imprimir() {
        pila.imprimir();
    }

    @Override
    public Object verUltimo() {
        return pila.ultimo.getContenido();
    }
}
