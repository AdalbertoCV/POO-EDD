package poo2.prog3.clientecontrolconcursos.entidades;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Persona implements Serializable {

  private String emailPersona;
  private String nombrePersona;
  private String apellidosPersona;
  private String sexoPersona;
  private String calleNumPersona;
  private String coloniaPersona;
  private long idMunicipioPersona;
  private long idEntidadPersona;
  private String codpostalPersona;
  private String telefonoPersona;
  private Date fechaNacPersona;
  private long idInstitucionPersona;
  private String tipoPersona;

  public Persona() {
  }

  public Persona(String emailPersona) {
    this.emailPersona = emailPersona;
  }

  public Persona(String emailPersona, String nombrePersona, String apellidosPersona, String sexoPersona, String calleNumPersona, long idMunicipioPersona, long idEntidadPersona, String telefonoPersona, Date fechaNacPersona, long idInstitucionPersona, String tipoPer) {
    this.emailPersona = emailPersona;
    this.nombrePersona = nombrePersona;
    this.apellidosPersona = apellidosPersona;
    this.sexoPersona = sexoPersona;
    this.calleNumPersona = calleNumPersona;
    this.idMunicipioPersona = idMunicipioPersona;
    this.idEntidadPersona = idEntidadPersona;
    this.telefonoPersona = telefonoPersona;
    this.fechaNacPersona = fechaNacPersona;
    this.idInstitucionPersona = idInstitucionPersona;
    this.tipoPersona=tipoPer;
  }

  public String getEmailPersona() {
    return emailPersona;
  }

  public void setEmailPersona(String emailPersona) {
    this.emailPersona = emailPersona;
  }

  public String getNombrePersona() {
    return nombrePersona;
  }

  public void setNombrePersona(String nombrePersona) {
    this.nombrePersona = nombrePersona;
  }


  public String getApellidosPersona() {
    return apellidosPersona;
  }

  public void setApellidosPersona(String apellidosPersona) {
    this.apellidosPersona = apellidosPersona;
  }


  public String getSexoPersona() {
    return sexoPersona;
  }

  public void setSexoPersona(String sexoPersona) {
    this.sexoPersona = sexoPersona;
  }


  public String getCalleNumPersona() {
    return calleNumPersona;
  }

  public void setCalleNumPersona(String calleNumPersona) {
    this.calleNumPersona = calleNumPersona;
  }


  public String getColoniaPersona() {
    return coloniaPersona;
  }

  public void setColoniaPersona(String coloniaPersona) {
    this.coloniaPersona = coloniaPersona;
  }


  public long getIdMunicipioPersona() {
    return idMunicipioPersona;
  }

  public void setIdMunicipioPersona(long idMunicipioPersona) {
    this.idMunicipioPersona = idMunicipioPersona;
  }


  public long getIdEntidadPersona() {
    return idEntidadPersona;
  }

  public void setIdEntidadPersona(long idEntidadPersona) {
    this.idEntidadPersona = idEntidadPersona;
  }


  public String getCodpostalPersona() {
    return codpostalPersona;
  }

  public void setCodpostalPersona(String codpostalPersona) {
    this.codpostalPersona = codpostalPersona;
  }


  public String getTelefonoPersona() {
    return telefonoPersona;
  }

  public void setTelefonoPersona(String telefonoPersona) {
    this.telefonoPersona = telefonoPersona;
  }


  public Date getFechaNacPersona() {
    return fechaNacPersona;
  }

  public void setFechaNacPersona(Date fechaNacPersona) {
    this.fechaNacPersona = fechaNacPersona;
  }


  public long getIdInstitucionPersona() {
    return idInstitucionPersona;
  }

  public void setIdInstitucionPersona(long idInstitucionPersona) {
    this.idInstitucionPersona = idInstitucionPersona;
  }


  public String getTipoPersona() {
    return tipoPersona;
  }

  public void setTipoPersona(String tipoPersona) {
    this.tipoPersona = tipoPersona;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Persona persona = (Persona) o;
    return emailPersona.equals(persona.emailPersona);
  }

  @Override
  public int hashCode() {
    return Objects.hash(emailPersona);
  }

  @Override
  public String toString() {
    return String.format("%s %s",nombrePersona,apellidosPersona);
  }
}
