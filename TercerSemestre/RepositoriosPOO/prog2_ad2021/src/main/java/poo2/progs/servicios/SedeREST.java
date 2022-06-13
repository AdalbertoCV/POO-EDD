package poo2.progs.servicios;

import poo2.progs.entidades.Sede;
import poo2.progs.entidades.Institucion;
import poo2.progs.entidades.Persona;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("sede")
public class SedeREST extends RESTAbstracto<Sede>{
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public SedeREST() {
        super(Sede.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Sede> obtenSedes() {
		return super.getAll();
	}
	
	private boolean institucionValida(long id_institucion) {
        boolean resultado = true;
        try {
            Query q = em.createQuery("SELECT i FROM Institucion i WHERE i.idInstitucion=:id").setParameter("id", id_institucion);
            Institucion i = (Institucion) q.getSingleResult();
        }
		catch(NoResultException ex) {
            resultado = false;
        }
        return resultado;
    }
	
	private boolean emailValido(String email_persona) {
        boolean resultado = true;
        try {
            Query q = em.createQuery("SELECT p FROM Persona p WHERE p.emailPersona=:correo").setParameter("correo", email_persona);
            Persona i = (Persona) q.getSingleResult();
        }
		catch(NoResultException ex) {
            resultado = false;
        }
        return resultado;
		// Como se valida que el correo es tipo Profesor?
    }
	
	private boolean tipoValido(String email_persona) {
		boolean resultado = true;
		try {
			String tipoProfesor = "Profesor";
			Query q = em.createQuery(
				"SELECT p.tipoPersona FROM Persona p WHERE p.emailPersona=:correo").
				setParameter("correo", email_persona);
			String consulta = (String) q.getSingleResult();
			if (tipoProfesor.equals(q) == true) {
				resultado = true;
			}
		}catch(NoResultException ex) {
            resultado = false;
        }
        return resultado;
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String agregaSede(Sede dato) {
		String resultado = "false";
		if (institucionValida(dato.getIdInstitucionSede())
			&& emailValido(dato.getEmailDirectorSede())
			&& tipoValido(dato.getEmailDirectorSede())) {
			
			resultado = super.save(dato.getIdSede(), dato);
		}
		return resultado;
	}
	
	@PUT
	@Path("{idsede: [0-9]+}")
	@Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String actualizaSede(@PathParam("idsede") long idSede, Sede dato) {
		String resultado = "false";
		if (institucionValida(dato.getIdInstitucionSede())
			&& emailValido(dato.getEmailDirectorSede())) {
			
			resultado = super.update(dato.getIdSede(), dato);
		}
		return resultado;
	}
	
	private boolean noHayRegistrosAsociados(long idsede){
        boolean resultado = false;
        try{
            Query q = em.createQuery("SELECT COUNT(sc) FROM SedeConcurso sc WHERE sc.idSede=:id").setParameter("id", idsede);
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
	@Path("{idsede}")
	@Produces(MediaType.TEXT_PLAIN)
	public String eliminaSede(@PathParam("idsede") long idSede) {
		String resultado = "false";
		if (noHayRegistrosAsociados(idSede)) {
			resultado = super.delete(idSede);			
		}
		return resultado;
	}
	
	@GET
    @Path("disponibles/{idconc}")
    @Produces(MediaType.TEXT_PLAIN)
	public List<Sede> obtenSedesDisponibles(@PathParam("idconc") long idConcurso) {
		Query q = em.createQuery("SELECT s.idSede FROM Sede s WHERE s.idSede=:idS").setParameter("idS", idConcurso);
		List<Sede> sedes = q.getResultList();
		return sedes;
	}
	// No entiendo de donde sacar los datos y como hacer su obtncion
}