package registros.escuelas;

import estructuraslineales.ListaEstatica;

public class Escuela {
    protected String nombre;
    protected ListaEstatica alumnos;

    public Escuela(String nombre, int cantidadAlumnos) {
        this.nombre = nombre;
        alumnos=new ListaEstatica(cantidadAlumnos);
    }

    public boolean agregarAlumno(Alumno alumno){
        int valorRetornoAgregado=alumnos.agregar(alumno);
        if(valorRetornoAgregado==-1){ //no se pudo agregar
            return false;
        }else{ //si se pudo agregar
            return true;
        }
    }

    public void imprimirListadoAlumnos(){
        alumnos.imprimirOI();
    }

    public void imprimirListadoDatosAlumnos(){
        //hay que hacer un for para extraer de uno por uno los alumnos de la ListaEstatica
        //sacar con los get los datos de cada atributo y concatenarlos para imprimirlos
        //y tendrían que utilizar el método de imprimir en la consola de SalidaPorDefecto
    }

    public Double calcularPromedioAlumno(String matricula){
        //Buscar al alumno con esa matricula
        Integer posicionBusqueda=(Integer)alumnos.buscar(matricula);

        if(posicionBusqueda!=null){ //si esta
            Alumno alumnoBusqueda=(Alumno)alumnos.obtener(posicionBusqueda);
            return alumnoBusqueda.obtenerPromedio(); //regreso el promedio o un nulo
        }else{ //no esta
            return null;
        }
    }

    public String getNombre() {
        return nombre;
    }
}
