package poo2.progs.entidades;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="persona")
@XmlRootElement
public class Persona implements Serializable {
  @Id
  @Basic(optional = false)
  @Column(name ="email_persona")
  @Size(min=1, max=40)
  private String emailPersona;
  @Basic(optional = false)
  @Column(name ="nombre_persona")
  @Size(min=1, max=30)
  private String nombrePersona;
  @Basic(optional = false)
  @Column(name ="apellidos_persona")
  @Size(min=1, max=40)
  private String apellidosPersona;
  @Basic(optional = false)
  @Column(name ="sexo_persona")
  @Size(min=1, max=1)
  private String sexoPersona;
  @Basic(optional = false)
  @Column(name ="calle_num_persona")
  @Size(min=1, max=50)
  private String calleNumPersona;
  @Column(name ="colonia_persona")
  @Size(max=50)
  private String coloniaPersona;
  @Basic(optional = false)
  @NotNull
  @Column(name ="id_municipio_persona")
  private long idMunicipioPersona;
  @Basic(optional = false)
  @NotNull
  @Column(name ="id_entidad_persona")
  private long idEntidadPersona;
  @Column(name ="codpostal_persona")
  @Size(max=5)
  private String codpostalPersona;
  @Basic(optional = false)
  @Column(name ="telefono_persona")
  @Size(min=10,max=10)
  private String telefonoPersona;
  @Basic(optional = false)
  @Column(name ="fecha_nac_persona")
  @Temporal(TemporalType.DATE)
  private Date fechaNacPersona;
  @Basic(optional = false)
  @NotNull
  @Column(name ="id_institucion_persona")
  private long idInstitucionPersona;
  @Basic(optional = false)
  @Column(name ="tipo_persona")
  @Size(min=8,max=10)
  private String tipoPersona;

  public Persona() {
  }

  public Persona(String emailPersona) {
    this.emailPersona = emailPersona;
  }

  public Persona(String emailPersona, String nombrePersona, String apellidosPersona, String sexoPersona, String calleNumPersona, long idMunicipioPersona, long idEntidadPersona, String telefonoPersona, Date fechaNacPersona, long idInstitucionPersona, String tipoPersona) {
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
    this.tipoPersona = tipoPersona;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Persona)) return false;
    Persona persona = (Persona) o;
    return Objects.equals(getEmailPersona(), persona.getEmailPersona());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getEmailPersona());
  }

  @Override
  public String toString() {
    return nombrePersona + " " + apellidosPersona;

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

}

