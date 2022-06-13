package poo2.progs.entidades;


import java.io.Serializable;
import java.util.Objects;

public class Municipio  implements Serializable {
  private long idMunicipio;
  private String nombreMunicipio;

  public Municipio() {
  }
  public Municipio(long idMunicipio) {
    this.idMunicipio = idMunicipio;
  }
  public Municipio(long idMunicipio, String nombreMunicipio) {
    this.idMunicipio = idMunicipio;
    this.nombreMunicipio = nombreMunicipio;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Municipio)) return false;
    Municipio municipio = (Municipio) o;
    return getIdMunicipio() == municipio.getIdMunicipio();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getIdMunicipio());
  }

  @Override
  public String toString() {
    return  nombreMunicipio;

  }

  public long getIdMunicipio() {
    return idMunicipio;
  }

  public void setIdMunicipio(long idMunicipio) {
    this.idMunicipio = idMunicipio;
  }


  public String getNombreMunicipio() {
    return nombreMunicipio;
  }

  public void setNombreMunicipio(String nombreMunicipio) {
    this.nombreMunicipio = nombreMunicipio;
  }

}
