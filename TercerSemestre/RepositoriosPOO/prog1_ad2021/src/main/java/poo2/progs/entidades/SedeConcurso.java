package poo2.progs.entidades;


import java.io.Serializable;
import java.util.Objects;

public class SedeConcurso implements Serializable {

  private long idSedeConcurso;
  private long idSede;
  private long idConcurso;

  public SedeConcurso() {
  }

  public SedeConcurso(long idSedeConcurso) {
    this.idSedeConcurso = idSedeConcurso;
  }

  public SedeConcurso(long idSedeConcurso, long idSede, long idConcurso) {
    this.idSedeConcurso = idSedeConcurso;
    this.idSede = idSede;
    this.idConcurso = idConcurso;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SedeConcurso)) return false;
    SedeConcurso that = (SedeConcurso) o;
    return getIdSedeConcurso() == that.getIdSedeConcurso();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getIdSedeConcurso());
  }



  public long getIdSedeConcurso() {
    return idSedeConcurso;
  }

  public void setIdSedeConcurso(long idSedeConcurso) {
    this.idSedeConcurso = idSedeConcurso;
  }


  public long getIdSede() {
    return idSede;
  }

  public void setIdSede(long idSede) {
    this.idSede = idSede;
  }


  public long getIdConcurso() {
    return idConcurso;
  }

  public void setIdConcurso(long idConcurso) {
    this.idConcurso = idConcurso;
  }

}
