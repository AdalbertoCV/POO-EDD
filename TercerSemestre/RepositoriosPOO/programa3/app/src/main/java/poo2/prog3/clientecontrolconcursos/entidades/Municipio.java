package poo2.prog3.clientecontrolconcursos.entidades;


import java.io.Serializable;
import java.util.Objects;

public class Municipio implements Serializable  {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Municipio municipio = (Municipio) o;
    return idMunicipio == municipio.idMunicipio;
  }

  @Override
  public int hashCode() {
    return Objects.hash(idMunicipio);
  }

  @Override
  public String toString() {
    return nombreMunicipio;
  }

}
