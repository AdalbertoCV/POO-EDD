package registros.Computadoras;

/**
 * Clase Usuario - Contiene toda la informacion de un usuario que usara una computadora
 * @author  Adalberto Cerrillo Vazquez - 4B
 * @version  1.0
 */
public class Usuario {
    protected String nombre;
    protected String apellido;
    protected int edad;
    protected String puestoEscuela;

    public Usuario(String nom, String ap, int edad, String puesto){
        this.nombre = nom;
        this.apellido = ap;
        this.edad = edad;
        this.puestoEscuela = puesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getPuestoEscuela() {
        return puestoEscuela;
    }

    public void setPuestoEscuela(String puestoEscuela) {
        this.puestoEscuela = puestoEscuela;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " " + edad + " años " + puestoEscuela;
    }
}
