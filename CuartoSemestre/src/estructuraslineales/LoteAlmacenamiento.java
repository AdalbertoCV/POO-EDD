package estructuraslineales;

public interface LoteAlmacenamiento {
    public boolean vacio();

    public boolean lleno();

    public boolean poner(Object valor);

    public Object quitar();

    public void imprimir();

    public Object verUltimo();

}
