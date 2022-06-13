package poo2.progs.entidades;


import java.util.Date;

public class Concurso {

  private long idConcurso;
  private String nombreConcurso;
  private Date fechaConcurso;
  private Date fechaInicioRegistro;
  private Date fechaFinRegistro;


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
