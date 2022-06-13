package enumerados;

public enum TipoLogaritmo {
    BASE10("BASE10",10), BASE2("BASE2",2);

    private String nombreTipo;
    private int valor;

    private TipoLogaritmo(String nombre, int val){
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
