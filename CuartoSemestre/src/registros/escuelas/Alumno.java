package registros.escuelas;

import estructuraslineales.ListaEstatica;

public class Alumno {
    protected String matricula;
    protected String nombre;
    protected String apellido;
    protected int edad;
    protected ListaEstatica calificaciones;

    public Alumno(String matricula, String nombre, String apellido, int edad, ListaEstatica calificaciones){
        this.matricula=matricula;
        this.nombre=nombre;
        this.apellido=apellido;
        this.edad=edad;
        this.calificaciones=calificaciones;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getEdad() {
        return edad;
    }

    public ListaEstatica getCalificaciones() {
        return calificaciones;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setCalificaciones(ListaEstatica calificaciones) {
        this.calificaciones = calificaciones;
    }

    public Double obtenerPromedio(){
        double sumaCalificaciones=0.0;
        double promedioCalificaciones=0.0;

        if(calificaciones.vacia()==false){ //si hay califs
            for(int posicion=0;posicion<calificaciones.numeroElementos();posicion++){
                sumaCalificaciones=sumaCalificaciones+(double)calificaciones.obtener(posicion);
            }
            promedioCalificaciones=sumaCalificaciones / calificaciones.numeroElementos();
            return promedioCalificaciones;
        }else{ //no hay califs
            return null;
        }
    }

    @Override
    public String toString(){
        return matricula;
    }
}
