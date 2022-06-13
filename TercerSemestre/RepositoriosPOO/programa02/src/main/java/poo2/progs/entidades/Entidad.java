package poo2.progs.entidades;


import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
@Entity
@Table(name="entidad")
@XmlRootElement
public class Entidad {
  @Id
  @Basic(optional = false)
  @Column(name = "id_entidad")
  private long idEntidad;
  @Basic(optional = false)
  @Column(name = "nombre_entidad")
  @Size(min=1,max=50)
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Entidad)) return false;
    Entidad entidad = (Entidad) o;
    return getIdEntidad() == entidad.getIdEntidad();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getIdEntidad());
  }

  @Override
  public String toString() {
    return nombreEntidad;
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

}
