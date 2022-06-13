package enumerados;

public enum TipoOrden
{
    INCR("INCREMENTAL", 1), DECR("DECREMENTAL", 2);

    private String nombreTipo;
    private int valor;

    private TipoOrden(String nombre, int val){
        this.nombreTipo = nombre;
        this.valor = val;
    }

    public String getNombreTipo(){
        return this.nombreTipo;
    }

    public int getValor(){
        return this.valor;
    }
}
