package poo2.progs.entidades;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SedeConcursoExtendida extends SedeConcurso {
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

    @Override
    public String toString() {
        return  nombreSede +" (" +nombreConcurso +")" ;
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
