package estructurasnolineales.auxiliares;

public class Vertice {
    protected int numVertice;
    protected Object contenido;

    public int getNumVertice() {
        return numVertice;
    }

    public void setNumVertice(int numVertice) {
        this.numVertice = numVertice;
    }

    public Object getContenido() {
        return contenido;
    }

    public void setContenido(Object contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString(){
        return contenido.toString();
    }

    public String obtenerDatos(){
        return contenido + "("+ numVertice+")";
    }
}
