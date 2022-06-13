package poo2.progs.entidades;

import java.util.Date;

public class DatosEstudiante {

  private String emailEstudiante;
  private String carreraEstudiante;
  private Date fechaInicioCarrera;
  private Date fechaEsperadaTerminacion;


  public String getEmailEstudiante() {
    return emailEstudiante;
  }

  public void setEmailEstudiante(String emailEstudiante) {
    this.emailEstudiante = emailEstudiante;
  }


  public String getCarreraEstudiante() {
    return carreraEstudiante;
  }

  public void setCarreraEstudiante(String carreraEstudiante) {
    this.carreraEstudiante = carreraEstudiante;
  }


  public Date getFechaInicioCarrera() {
    return fechaInicioCarrera;
  }

  public void setFechaInicioCarrera(Date fechaInicioCarrera) {
    this.fechaInicioCarrera = fechaInicioCarrera;
  }


  public Date getFechaEsperadaTerminacion() {
    return fechaEsperadaTerminacion;
  }

  public void setFechaEsperadaTerminacion(Date fechaEsperadaTerminacion) {
    this.fechaEsperadaTerminacion = fechaEsperadaTerminacion;
  }

}
