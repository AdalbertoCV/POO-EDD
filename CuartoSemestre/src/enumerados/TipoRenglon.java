package enumerados;

public enum TipoRenglon {

    SUPERIOR("SUPERIOR",1), INFERIOR("INFERIOR",2);

    private String nombreTipo;
    private int valor;

    private TipoRenglon(String nombre, int val){
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