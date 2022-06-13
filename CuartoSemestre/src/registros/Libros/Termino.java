package registros.Libros;

public class Termino {
    protected String nombre;
    protected String rangoPaginas;
    protected String definicion;

    public Termino(String nom, String rango, String def){
        nombre  = nom;
        rangoPaginas = rango;
        definicion=def;
    }

    @Override
    public String toString(){
      return nombre;
    }

    public String getDefinicion(){
        return definicion;
    }
}
