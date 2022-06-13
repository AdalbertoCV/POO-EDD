package registros.competenciasciclismo;

public class Ciclista {
    protected int noCompetidor;
    protected String nombre;
    protected int edad;
    protected char sexo;

    public Ciclista(int noCompetidor, String nombre, int edad, char sexo) {
        this.noCompetidor = noCompetidor;
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
    }

    public int getNoCompetidor() {
        return noCompetidor;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public char getSexo() {
        return sexo;
    }

    public void setNoCompetidor(int noCompetidor) {
        this.noCompetidor = noCompetidor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString(){
        return ""+noCompetidor;
    }

    public String obtenerDatos(){
        return "No. de competidor: "+ noCompetidor + "\n" + "Nombre: "+ nombre + "\n"+ "Edad: "+ edad +"\n"+ "Sexo: "+ sexo;
    }
}
