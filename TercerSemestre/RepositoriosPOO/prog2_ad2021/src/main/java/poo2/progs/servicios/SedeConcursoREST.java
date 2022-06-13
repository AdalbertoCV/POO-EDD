package poo2.progs.servicios;

import poo2.progs.entidades.SedeConcurso;
import poo2.progs.entidades.Sede;
import poo2.progs.entidades.Concurso;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("sedeconcurso")
public class SedeConcursoREST extends RESTAbstracto<Persona>{
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public SedeConcursoREST() {
        super(SedeConcurso.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

	@GET
    @Path("asignadas/{idconcurso}")
    @Produces(MediaType.TEXT_PLAIN)
	public List<SedeConcursoExtendida> obtenSedesAsignadas(long idConcurso) {
		
	}
	
	private boolean sedeValida(long id_sede) {
		boolean resultado = true;
		try {
			Query q = em.createQuery(
				"SELECT s FROM Sede s WHERE s.idSede=:id").
				setParameter("id", id_sede);
            Sede s = (Sede) q.getSingleResult();
		}catch (NoResultException ex) {
			resultado = false;
		}
		return resultado;
	}
	
	private boolean concursoValido(long id_concurso) {
		boolean resultado = true;
		try {
			Query q = em.createQuery(
				"SELECT c FROM Concurso c WHERE c.idConcurso=:id").
				setParameter("id", id_concurso);
            Sede s = (Sede) q.getSingleResult();
		}catch (NoResultException ex) {
			resultado = false;
		}
		return resultado;
	}
	
	@POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes ({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String agregaSedeConcurso(SedeConcurso dato) {
		String resultado = "false";
        if (sedeValida(dato.getIdSede()) && concursoValido(dato.getIdConcurso())) {
			resultado = super.save(dato.getIdSedeConcurso(), dato);
		}
        return resultado;
	}
	
	private boolean noHayRegistrosAsociados(long idsedeconc){
        boolean resultado = false;
        try{
            Query q = em.createQuery(
				"SELECT COUNT(esc) FROM EquiposSedeConcurso esc WHERE esc.idSedeConcurso=:id").
				setParameter("id", idsedeconc);
            Long cantidad = (Long) q.getSingleResult();
            if (cantidad==0){
                resultado = true;
            }
        }
        catch(Exception ex){
			
		}
        return resultado;
    }
	
	@DELETE
    @Path("{idsedeconc}")
    @Produces(MediaType.TEXT_PLAIN)
	public String eliminaSedeConcurso(long idSedeConcurso) {
		String resultado = "false";
		if (noHayRegistrosAsociados(idSedeConcurso)) {
			resultado = super.delete(idSedeConcurso);
		}
		return resultado;
	}

}