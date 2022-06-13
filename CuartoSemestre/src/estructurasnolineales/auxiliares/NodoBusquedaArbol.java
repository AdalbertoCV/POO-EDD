package estructurasnolineales.auxiliares;

import estructuraslineales.ListaDinamica;


public class NodoBusquedaArbol {
    private String indice;
    private ListaDinamica direccion;

    public NodoBusquedaArbol(){
        indice = null;
        direccion = new ListaDinamica();
    }

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }

    public Long getDireccion() {
        return (Long) direccion.obtener(0);
    }

    public ListaDinamica obtenerDirecciones(){
        return direccion;
    }

    public void setDireccion(Long dir) {
         direccion.agregar(dir);
    }

    @Override
    public String toString() {
        return indice;
    }
}
