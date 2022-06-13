package poo2.progs.entidades;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="datos_estudiante")
@XmlRootElement
public class DatosEstudiante {
  @Id
  @Basic(optional = false)
  @Column(name="email_estudiante")
  @Size(min=1,max=40)
  private String emailEstudiante;
  @Basic(optional = false)
  @Column(name="carrera_estudiante")
  @Size(min=1,max=50)
  private String carreraEstudiante;
  @Basic(optional = false)
  @Column(name="fecha_inicio_carrera")
  @Temporal(TemporalType.DATE)
  private Date fechaInicioCarrera;
  @Basic(optional = false)
  @Column(name="fecha_esperada_terminacion")
  @Temporal(TemporalType.DATE)
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DatosEstudiante)) return false;
    DatosEstudiante that = (DatosEstudiante) o;
    return getEmailEstudiante().equals(that.getEmailEstudiante());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getEmailEstudiante());
  }

  @Override
  public String toString() {
    return  emailEstudiante;
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

}
