package poo2.progs.entidades;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Concurso implements Serializable {

  private long idConcurso;
  private String nombreConcurso;
  private Date fechaConcurso;
  private Date fechaInicioRegistro;
  private Date fechaFinRegistro;

  public Concurso() {
  }
  public Concurso(long idConcurso) {
    this.idConcurso = idConcurso;
  }

  public Concurso(long idConcurso, String nombreConcurso, Date fechaConcurso, Date fechaInicioRegistro, Date fechaFinRegistro) {
    this.idConcurso = idConcurso;
    this.nombreConcurso = nombreConcurso;
    this.fechaConcurso = fechaConcurso;
    this.fechaInicioRegistro = fechaInicioRegistro;
    this.fechaFinRegistro = fechaFinRegistro;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Concurso)) return false;
    Concurso concurso = (Concurso) o;
    return getIdConcurso() == concurso.getIdConcurso();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getIdConcurso());
  }

  @Override
  public String toString() {
    return nombreConcurso;
  }


  public long getIdConcurso() {
    return idConcurso;
  }

  public void setIdConcurso(long idConcurso) {
    this.idConcurso = idConcurso;
  }


  public String getNombreConcurso() {
    return nombreConcurso;
  }

  public void setNombreConcurso(String nombreConcurso) {
    this.nombreConcurso = nombreConcurso;
  }


  public Date getFechaConcurso() {
    return fechaConcurso;
  }

  public void setFechaConcurso(Date fechaConcurso) {
    this.fechaConcurso = fechaConcurso;
  }


  public Date getFechaInicioRegistro() {
    return fechaInicioRegistro;
  }

  public void setFechaInicioRegistro(Date fechaInicioRegistro) {
    this.fechaInicioRegistro = fechaInicioRegistro;
  }


  public Date getFechaFinRegistro() {
    return fechaFinRegistro;
  }

  public void setFechaFinRegistro(Date fechaFinRegistro) {
    this.fechaFinRegistro = fechaFinRegistro;
  }

}
