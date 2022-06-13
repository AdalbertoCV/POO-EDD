package enumerados;

public enum TipoTabla {
    RENGLON("RENGLON",1), COLUMNA("COLUMNA",2);

    private String nombreTipo;
    private int valor;

    private TipoTabla(String nombre, int val){
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
