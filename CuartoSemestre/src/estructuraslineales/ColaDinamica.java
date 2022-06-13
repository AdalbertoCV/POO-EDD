package estructuraslineales;


public class ColaDinamica implements LoteAlmacenamiento{
    protected ListaDinamica datos;

    public ColaDinamica(){
        datos = new ListaDinamica();
    }


    @Override
    public boolean vacio() {
        return datos.vacia();
    }

    @Override
    public boolean lleno() {
        return false;
    }

    @Override
    public boolean poner(Object valor) {
        int respuesta = datos.agregar(valor);
        if (respuesta ==0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Object quitar() {
        return datos.eliminarInicio();
    }

    @Override
    public void imprimir() {
        datos.imprimir();
    }

    @Override
    public Object verUltimo() {
        return datos.verUltimo();
    }
}
