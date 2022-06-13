package poo2.prog3.clientecontrolconcursos.entidades;


import java.io.Serializable;
import java.util.Objects;

public class EquiposSedeConcurso implements Serializable {

  private long idEquipoSedeConcurso;
  private long idEquipo;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EquiposSedeConcurso that = (EquiposSedeConcurso) o;
    return idEquipoSedeConcurso == that.idEquipoSedeConcurso;
  }

  @Override
  public int hashCode() {
    return Objects.hash(idEquipoSedeConcurso);
  }
}
