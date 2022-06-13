package poo2.prog3.clientecontrolconcursos.entidades;

import java.io.Serializable;

public class EquiposSedeConcursoExtendida extends EquiposSedeConcurso
    implements Serializable {
    private String nombreEquipo;
    private String nombreSede;
    private String nombreConcurso;

    public EquiposSedeConcursoExtendida() {
    }

    public EquiposSedeConcursoExtendida(long idEquipoSedeConcurso) {
        super(idEquipoSedeConcurso);
    }

    public EquiposSedeConcursoExtendida(long idEquipoSedeConcurso, long idEquipo, long idSedeConcurso) {
        super(idEquipoSedeConcurso, idEquipo, idSedeConcurso);
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
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
        return String.format("%s en %s (%s)",nombreEquipo,nombreSede, nombreConcurso);
    }
}
