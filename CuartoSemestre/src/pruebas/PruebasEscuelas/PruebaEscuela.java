package pruebas;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaEstatica;
import registros.escuelas.Alumno;
import registros.escuelas.Escuela;

public class PruebaEscuela {
    public  static void main(String argumentos[]){
        Escuela escuela=new Escuela("Escuela Profesional de Datos", 50);

        ListaEstatica calificaciones1=new ListaEstatica(4);
        calificaciones1.agregar(8.9);
        calificaciones1.agregar(5.2);
        calificaciones1.agregar(9.1);
        calificaciones1.agregar(3.1);
        Alumno alumno1=new Alumno("90945654","Pedro", "Lopez",23,calificaciones1);

        ListaEstatica calificaciones2=new ListaEstatica(3);
        calificaciones2.agregar(9.2);
        calificaciones2.agregar(5.1);
        calificaciones2.agregar(8.0);
        Alumno alumno2=new Alumno("90945651","Maria", "Mendez",21,calificaciones2);

        ListaEstatica calificaciones3=new ListaEstatica(5);
        calificaciones3.agregar(9.2);
        calificaciones3.agregar(6.5);
        calificaciones3.agregar(8.5);
        calificaciones3.agregar(3.2);
        calificaciones3.agregar(9.4);
        Alumno alumno3=new Alumno("90945656","Rosa", "Lira",20,calificaciones3);

        escuela.agregarAlumno(alumno1);
        escuela.agregarAlumno(alumno2);
        escuela.agregarAlumno(alumno3);

        SalidaPorDefecto.consola("La escuela "+ escuela.getNombre() + " tiene los siguientes alumnos: \n");
        escuela.imprimirListadoAlumnos();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("El promedio del alumno 90945656 es "+
                escuela.calcularPromedioAlumno("90945656")+ "\n");
    }
}
