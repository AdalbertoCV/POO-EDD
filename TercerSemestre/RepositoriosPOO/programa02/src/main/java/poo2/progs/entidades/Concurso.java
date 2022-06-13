package poo2.progs.entidades;


import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="concurso")
@XmlRootElement
public class Concurso {
  @Id
  @Basic(optional = false)
  @Column(name="id_concurso")
  private long idConcurso;
  @Basic(optional = false)
  @Column(name="nombre_concurso")
  @Size(min=1,max=100)
  private String nombreConcurso;
  @Basic(optional = false)
  @Column(name="fecha_concurso")
  @Temporal(TemporalType.DATE)
  private Date fechaConcurso;
  @Basic(optional = false)
  @Column(name="fecha_inicio_registro")
  @Temporal(TemporalType.DATE)
  private Date fechaInicioRegistro;
  @Basic(optional = false)
  @Column(name="fecha_fin_registro")
  @Temporal(TemporalType.DATE)
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
