package registros.competenciasciclismo;

public class EventoCiclismo {
    protected String nombre;
    protected String lugar;

    public EventoCiclismo(String nombre, String lugar) {
        this.nombre = nombre;
        this.lugar = lugar;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    @Override
    public String toString(){
        return nombre;
    }

    public String obtenerDatos(){
        return "Nombre del evento: " + nombre+ "\n" + "Lugar: "+ lugar;
    }
}
