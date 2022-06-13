package poo2.progs.entidades;


import java.util.Objects;
import java.io.Serializable;

@Entity
@Table(name = "sede_concurso")
@XmlRootElement
public class SedeConcurso implements Serializable{

  @Id
  @Basic(optional = false)
  @Column(name = "id_sede_concurso")
  private long idSedeConcurso;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "id_sede")
  private long idSede;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "id_concurso")
  private long idConcurso;
  
  public SedeConcurso(){
	  
  }
  public SedeConcurso(long idSedeConcurso){
	  this.idSedeConcurso = idSedeConcurso;
  }
  public SedeConcurso(long idSedeConcurso, long idSede, long idConcurso){
	  this.idSedeConcurso = idSedeConcurso;
	  this.idSede = idSede;
	  this.idConcurso = idConcurso;
  }

  public long getIdSedeConcurso() {
    return idSedeConcurso;
  }

  public void setIdSedeConcurso(long idSedeConcurso) {
    this.idSedeConcurso = idSedeConcurso;
  }


  public long getIdSede() {
    return idSede;
  }

  public void setIdSede(long idSede) {
    this.idSede = idSede;
  }


  public long getIdConcurso() {
    return idConcurso;
  }

  public void setIdConcurso(long idConcurso) {
    this.idConcurso = idConcurso;
  }
  @Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SedeConcurso that = (SedeConcurso) o;
		return idSedeConcurso == that.idSedeConcurso;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(idSedeConcurso);
	}

}
