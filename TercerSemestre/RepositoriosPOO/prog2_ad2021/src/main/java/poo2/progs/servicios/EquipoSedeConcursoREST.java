package poo2.progs.servicios;

import poo2.progs.entidades.EquipoSedeConcurso;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("equipossedeconcurso")
public class EquipoSedeConcursoREST extends RESTAbstracto<Persona>{
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public EquipoSedeConcursoREST() {
        super(EquipoSedeConcurso.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
	
	public List<EquiposSedeConcursoExtendida> obtenEquiposRegistrados(long idConcurso,
		long idInstitucion) {
			
	}
		
	public boolean registrarEquipoSedeConcurso(EquiposSedeConcurso dato) {
		
	}
	
	public boolean cancelarEquipoSedeConcurso (long idEquipoSedeConcurso)

}