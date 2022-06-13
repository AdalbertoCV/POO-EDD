package poo2.prog3.clientecontrolconcursos.entidades;


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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Concurso concurso = (Concurso) o;
    return idConcurso == concurso.idConcurso;
  }

  @Override
  public int hashCode() {
    return Objects.hash(idConcurso);
  }

  @Override
  public String toString() {
    return nombreConcurso;
  }
}
