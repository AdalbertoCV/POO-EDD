package poo2.progs.entidades;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EquiposSedeConcursoExtendida extends EquiposSedeConcurso{
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

    @Override
    public String toString() {
        return nombreEquipo+ " en "+ nombreSede +" (" +nombreConcurso +")" ;
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

}
