package poo2.progs.entidades;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
@Entity
@Table(name="equipos_sede_concurso")
@XmlRootElement
public class EquiposSedeConcurso {
  @Id
  @Basic(optional = false)
  @Column(name="id_equipo_sede_concurso")
  private long idEquipoSedeConcurso;
  @Basic(optional = false)
  @NotNull
  @Column(name = "id_equipo")
  private long idEquipo;
  @Basic(optional = false)
  @NotNull
  @Column(name = "id_sede_concurso")
  private long idSedeConcurso;

  public EquiposSedeConcurso() {
  }

  public EquiposSedeConcurso(long idEquipoSedeConcurso) {
    this.idEquipoSedeConcurso = idEquipoSedeConcurso;
  }

  public EquiposSedeConcurso(long idEquipoSedeConcurso, long idEquipo, long idSedeConcurso) {
    this.idEquipoSedeConcurso = idEquipoSedeConcurso;
    this.idEquipo = idEquipo;
    this.idSedeConcurso = idSedeConcurso;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof EquiposSedeConcurso)) return false;
    EquiposSedeConcurso that = (EquiposSedeConcurso) o;
    return getIdEquipoSedeConcurso() == that.getIdEquipoSedeConcurso();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getIdEquipoSedeConcurso());
  }


  public long getIdEquipoSedeConcurso() {
    return idEquipoSedeConcurso;
  }

  public void setIdEquipoSedeConcurso(long idEquipoSedeConcurso) {
    this.idEquipoSedeConcurso = idEquipoSedeConcurso;
  }

  public long getIdEquipo() {
    return idEquipo;
  }

  public void setIdEquipo(long idEquipo) {
    this.idEquipo = idEquipo;
  }

  public long getIdSedeConcurso() {
    return idSedeConcurso;
  }

  public void setIdSedeConcurso(long idSedeConcurso) {
    this.idSedeConcurso = idSedeConcurso;
  }

}
