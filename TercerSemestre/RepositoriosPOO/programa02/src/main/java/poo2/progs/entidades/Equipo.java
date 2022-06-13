package poo2.progs.entidades;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="equipo")
@XmlRootElement
public class Equipo implements Serializable {
  @Id
  @Basic(optional= false)
  @Column(name = "id_equipo")
  private long idEquipo;
  @Basic(optional= false)
  @Column(name= "nombre_equipo")
  @Size(min=1, max=40)
  private String nombreEquipo;
  @Basic(optional= false)
  @Column(name= "email_coach")
  @Size(min=1, max=40)
  private String emailCoach;
  @Basic(optional= false)
  @Column(name= "email_concursante1")
  @Size(min=1, max=40)
  private String emailConcursante1;
  @Basic(optional= false)
  @Column(name= "email_concursante2")
  @Size(min=1, max=40)
  private String emailConcursante2;
  @Basic(optional= false)
  @Column(name= "email_concursante3")
  @Size(min=1, max=40)
  private String emailConcursante3;
  @Column(name= "email_concursante_reserva")
  @Size(max=40)
  private String emailConcursanteReserva;
  @Basic(optional = false)
  @NotNull
  @Column(name="id_institucion_equipo")
  private long idInstitucionEquipo;

  public Equipo() {
  }

  public Equipo(long idEquipo) {
    this.idEquipo = idEquipo;
  }

  public Equipo(long idEquipo, String nombreEquipo, String emailCoach, String emailConcursante1, String emailConcursante2, String emailConcursante3, long idInstitucionEquipo) {
    this.idEquipo = idEquipo;
    this.nombreEquipo = nombreEquipo;
    this.emailCoach = emailCoach;
    this.emailConcursante1 = emailConcursante1;
    this.emailConcursante2 = emailConcursante2;
    this.emailConcursante3 = emailConcursante3;
    this.idInstitucionEquipo = idInstitucionEquipo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Equipo)) return false;
    Equipo equipo = (Equipo) o;
    return getIdEquipo() == equipo.getIdEquipo();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getIdEquipo());
  }

  @Override
  public String toString() {
    return nombreEquipo ;
  }

  public long getIdEquipo() {
    return idEquipo;
  }

  public void setIdEquipo(long idEquipo) {
    this.idEquipo = idEquipo;
  }


  public String getNombreEquipo() {
    return nombreEquipo;
  }

  public void setNombreEquipo(String nombreEquipo) {
    this.nombreEquipo = nombreEquipo;
  }


  public String getEmailCoach() {
    return emailCoach;
  }

  public void setEmailCoach(String emailCoach) {
    this.emailCoach = emailCoach;
  }


  public String getEmailConcursante1() {
    return emailConcursante1;
  }

  public void setEmailConcursante1(String emailConcursante1) {
    this.emailConcursante1 = emailConcursante1;
  }


  public String getEmailConcursante2() {
    return emailConcursante2;
  }

  public void setEmailConcursante2(String emailConcursante2) {
    this.emailConcursante2 = emailConcursante2;
  }


  public String getEmailConcursante3() {
    return emailConcursante3;
  }

  public void setEmailConcursante3(String emailConcursante3) {
    this.emailConcursante3 = emailConcursante3;
  }


  public String getEmailConcursanteReserva() {
    return emailConcursanteReserva;
  }

  public void setEmailConcursanteReserva(String emailConcursanteReserva) {
    this.emailConcursanteReserva = emailConcursanteReserva;
  }


  public long getIdInstitucionEquipo() {
    return idInstitucionEquipo;
  }

  public void setIdInstitucionEquipo(long idInstitucionEquipo) {
    this.idInstitucionEquipo = idInstitucionEquipo;
  }

}

