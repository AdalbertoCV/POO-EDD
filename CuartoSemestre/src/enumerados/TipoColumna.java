package enumerados;

public enum TipoColumna {

    IZQUIERDA("IZQUIERDA",1), DERECHA("DERECHA",2);

    private String nombreTipo;
    private int valor;

    private TipoColumna(String nombre, int val){
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
