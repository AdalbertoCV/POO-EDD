package poo2.prog3.clientecontrolconcursos.entidades;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class DatosEstudiante implements Serializable  {

  private String emailEstudiante;
  private String carreraEstudiante;
  private Date fechaInicioCarrera;
  private Date fechaEsperadaTerminacion;

  public DatosEstudiante() {
  }

  public DatosEstudiante(String emailEstudiante) {
    this.emailEstudiante = emailEstudiante;
  }

  public DatosEstudiante(String emailEstudiante, String carreraEstudiante, Date fechaInicioCarrera, Date fechaEsperadaTerminacion) {
    this.emailEstudiante = emailEstudiante;
    this.carreraEstudiante = carreraEstudiante;
    this.fechaInicioCarrera = fechaInicioCarrera;
    this.fechaEsperadaTerminacion = fechaEsperadaTerminacion;
  }

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DatosEstudiante that = (DatosEstudiante) o;
    return emailEstudiante.equals(that.emailEstudiante);
  }

  @Override
  public int hashCode() {
    return Objects.hash(emailEstudiante);
  }

  @Override
  public String toString() {
    return emailEstudiante;
  }
}
