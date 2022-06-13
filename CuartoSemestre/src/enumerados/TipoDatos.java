package enumerados;

public enum TipoDatos {
    Muestra("Muestra",1), Poblacion("Poblacion",2);

    private String nombreTipo;
    private int valor;

    private TipoDatos(String nombre, int val){
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
