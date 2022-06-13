package poo2.progs.entidades;

@Entity
@XmlRootElement
public class EquiposSedeConcursoExtendida extends EquiposSedeConcurso{
	
	@Basic(optional= false)
	@Column(name= "nombre_equipo")
	@Size(min=1, max=40)
    private String nombreEquipo;
	
	@Basic(optional=false)
	@Column(name="nombre_sede")
	@Size(min=1, max=50)
    private String nombreSede;
	
	@Basic(optional = false)
	@Column(name = "nombre_concurso")
	@Size(min=1, max=100)
    private String nombreConcurso;
	
	public EquiposSedeConcursoExtendida() {
		super();
	}
	public EquiposSedeConcursoExtendida(long idEquipoSedeConcurso) {
		super(idEquipoSedeConcurso);
	}
	public EquiposSedeConcursoExtendida(long idEquipoSedeConcurso, long idEquipo, 
			long idSedeConcurso) {
		super(idEquipoSedeConcurso, idEquipo, idSedeConcurso);
		this.nombreEquipo = nombreEquipo;
		this.nombreSede = nombreSede;
		this.nombreConcurso = nombreConcurso;
	}

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public String getNombreConcurso() {
        return nombreConcurso;
    }

    public void setNombreConcurso(String nombreConcurso) {
        this.nombreConcurso = nombreConcurso;
    }
	
	@Override
	public String toString() {
		return (nombreEquipo + " en " + nombreSede + " " + 
			"(" + nombreConcurso + ")");
	}
}
