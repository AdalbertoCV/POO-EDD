package poo2.prog3.clientecontrolconcursos.entidades;

import java.io.Serializable;

public class SedeConcursoExtendida extends SedeConcurso
    implements Serializable {

    private String nombreSede;
    private String nombreConcurso;

    public SedeConcursoExtendida() {
    }

    public SedeConcursoExtendida(long idSedeConcurso) {
        super(idSedeConcurso);
    }

    public SedeConcursoExtendida(long idSedeConcurso, long idSede, long idConcurso) {
        super(idSedeConcurso, idSede, idConcurso);
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public String getNombreConcurso() {
        return nombreConcurso;
    }

    public void setNombreConcurso(String nombreConcurso) {
        this.nombreConcurso = nombreConcurso;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)",nombreSede,nombreConcurso);
    }
}
