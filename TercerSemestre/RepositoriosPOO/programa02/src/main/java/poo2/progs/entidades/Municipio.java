package poo2.progs.entidades;


import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="municipio")
@XmlRootElement
public class Municipio implements Serializable {
  // TODO Coloque las anotaciones necesarias en los atributos de esta y todas las clases
  @Id
  @Basic(optional=false)
  @Column(name= "id_municipio")
  private long idMunicipio;

  @Basic(optional = false)
  @Column(name="nombre_municipio")
  @Size(min=1,max=50)
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
    return nombreMunicipio ;
  }
}
