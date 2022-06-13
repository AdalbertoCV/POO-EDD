package poo2.prog3.clientecontrolconcursos.entidades;


import java.io.Serializable;
import java.util.Objects;

public class Equipo implements Serializable {

  private long idEquipo;
  private String nombreEquipo;
  private String emailCoach;
  private String emailConcursante1;
  private String emailConcursante2;
  private String emailConcursante3;
  private String emailConcursanteReserva;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Equipo equipo = (Equipo) o;
    return idEquipo == equipo.idEquipo;
  }

  @Override
  public int hashCode() {
    return Objects.hash(idEquipo);
  }

  @Override
  public String toString() {
    return nombreEquipo;
  }
}
