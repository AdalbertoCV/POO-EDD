package poo2.prog3.clientecontrolconcursos.entidades;


import java.io.Serializable;
import java.util.Objects;

public class Entidad implements Serializable {

  private long idEntidad;
  private String nombreEntidad;

  public Entidad() {
  }

  public Entidad(long idEntidad) {
    this.idEntidad = idEntidad;
  }

  public Entidad(long idEntidad, String nombreEntidad) {
    this.idEntidad = idEntidad;
    this.nombreEntidad = nombreEntidad;
  }

  public long getIdEntidad() {
    return idEntidad;
  }

  public void setIdEntidad(long idEntidad) {
    this.idEntidad = idEntidad;
  }


  public String getNombreEntidad() {
    return nombreEntidad;
  }

  public void setNombreEntidad(String nombreEntidad) {
    this.nombreEntidad = nombreEntidad;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Entidad entidad = (Entidad) o;
    return idEntidad == entidad.idEntidad;
  }

  @Override
  public int hashCode() {
    return Objects.hash(idEntidad);
  }

  @Override
  public String toString() {
    return nombreEntidad;
  }
}
