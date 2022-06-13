package poo2.prog3.clientecontrolconcursos.entidades;


import java.io.Serializable;
import java.util.Objects;

public class Institucion implements Serializable {

  private long idInstitucion;
  private String nombreInstitucion;
  private String nombreCortoInstitucion;
  private String urlInstitucion;
  private String calleNumInstitucion;
  private String coloniaInstitucion;
  private long idMunicipioInstitucion;
  private long idEntidadInstitucion;
  private String codpostalInstitucion;
  private String telefonoInstitucion;

  public Institucion() {
  }

  public Institucion(long idInstitucion) {
    this.idInstitucion = idInstitucion;
  }

  public Institucion(long idInstitucion, String nombreInstitucion, String nombreCortoInstitucion, String urlInstitucion, String calleNumInstitucion, long idMunicipioInstitucion, long idEntidadInstitucion) {
    this.idInstitucion = idInstitucion;
    this.nombreInstitucion = nombreInstitucion;
    this.nombreCortoInstitucion = nombreCortoInstitucion;
    this.urlInstitucion = urlInstitucion;
    this.calleNumInstitucion = calleNumInstitucion;
    this.idMunicipioInstitucion = idMunicipioInstitucion;
    this.idEntidadInstitucion = idEntidadInstitucion;
  }

  public long getIdInstitucion() {
    return idInstitucion;
  }

  public void setIdInstitucion(long idInstitucion) {
    this.idInstitucion = idInstitucion;
  }


  public String getNombreInstitucion() {
    return nombreInstitucion;
  }

  public void setNombreInstitucion(String nombreInstitucion) {
    this.nombreInstitucion = nombreInstitucion;
  }


  public String getNombreCortoInstitucion() {
    return nombreCortoInstitucion;
  }

  public void setNombreCortoInstitucion(String nombreCortoInstitucion) {
    this.nombreCortoInstitucion = nombreCortoInstitucion;
  }


  public String getUrlInstitucion() {
    return urlInstitucion;
  }

  public void setUrlInstitucion(String urlInstitucion) {
    this.urlInstitucion = urlInstitucion;
  }


  public String getCalleNumInstitucion() {
    return calleNumInstitucion;
  }

  public void setCalleNumInstitucion(String calleNumInstitucion) {
    this.calleNumInstitucion = calleNumInstitucion;
  }


  public String getColoniaInstitucion() {
    return coloniaInstitucion;
  }

  public void setColoniaInstitucion(String coloniaInstitucion) {
    this.coloniaInstitucion = coloniaInstitucion;
  }


  public long getIdMunicipioInstitucion() {
    return idMunicipioInstitucion;
  }

  public void setIdMunicipioInstitucion(long idMunicipioInstitucion) {
    this.idMunicipioInstitucion = idMunicipioInstitucion;
  }


  public long getIdEntidadInstitucion() {
    return idEntidadInstitucion;
  }

  public void setIdEntidadInstitucion(long idEntidadInstitucion) {
    this.idEntidadInstitucion = idEntidadInstitucion;
  }


  public String getCodpostalInstitucion() {
    return codpostalInstitucion;
  }

  public void setCodpostalInstitucion(String codpostalInstitucion) {
    this.codpostalInstitucion = codpostalInstitucion;
  }


  public String getTelefonoInstitucion() {
    return telefonoInstitucion;
  }

  public void setTelefonoInstitucion(String telefonoInstitucion) {
    this.telefonoInstitucion = telefonoInstitucion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Institucion that = (Institucion) o;
    return idInstitucion == that.idInstitucion;
  }

  @Override
  public int hashCode() {
    return Objects.hash(idInstitucion);
  }

  @Override
  public String toString() {
    return nombreInstitucion;
  }
}
