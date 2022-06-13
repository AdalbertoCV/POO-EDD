package poo2.progs.entidades;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="sede")
@XmlRootElement
public class Sede implements Serializable {

  @Id
  @Basic(optional= false)
  @Column(name = "id_sede")
  private long idSede;

  @Basic(optional=false)
  @Column(name="nombre_sede")
  @Size(min=1, max=50)
  private String nombreSede;

  @Basic(optional=false)
  @NotNull
  @Column(name="id_institucion_sede")
  private long idInstitucionSede;

  @Basic(optional=false)
  @Column(name = "email_director_sede")
  @Size(min=1, max=40)
  private String emailDirectorSede;

  @Column(name = "url_sede")
  @Size(max=200)
  private String urlSede;

  public Sede() {
  }

  public Sede(long idSede) {
    this.idSede = idSede;
  }

  public Sede(long idSede, String nombreSede, long idInstitucionSede, String emailDirectorSede) {
    this.idSede = idSede;
    this.nombreSede = nombreSede;
    this.idInstitucionSede = idInstitucionSede;
    this.emailDirectorSede = emailDirectorSede;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Sede)) return false;
    Sede sede = (Sede) o;
    return getIdSede() == sede.getIdSede();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getIdSede());
  }

  @Override
  public String toString() {
    return nombreSede;
  }

  public long getIdSede() {
    return idSede;
  }

  public void setIdSede(long idSede) {
    this.idSede = idSede;
  }


  public String getNombreSede() {
    return nombreSede;
  }

  public void setNombreSede(String nombreSede) {
    this.nombreSede = nombreSede;
  }


  public long getIdInstitucionSede() {
    return idInstitucionSede;
  }

  public void setIdInstitucionSede(long idInstitucionSede) {
    this.idInstitucionSede = idInstitucionSede;
  }


  public String getEmailDirectorSede() {
    return emailDirectorSede;
  }

  public void setEmailDirectorSede(String emailDirectorSede) {
    this.emailDirectorSede = emailDirectorSede;
  }


  public String getUrlSede() {
    return urlSede;
  }

  public void setUrlSede(String urlSede) {
    this.urlSede = urlSede;
  }

}

