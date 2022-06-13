package poo2.progs.servicios;

import poo2.progs.entidades.DatosEstudiante;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("datos_estudiante")
public class DatosEstudianteREST extends RESTAbstracto<DatosEstudiante>{
	@PersistenceContext(unitName = "default")
    private EntityManager em;

    public DatosEstudianteREST() {
        super(DatosEstudiante.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
	
	@GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public DatosEstudiante obtenDatosEstudiante(String emailEstudiante) {
		Query q = em.createQuery(
		"SELECT de FROM DatosEstudiante de").setParameter();
		List<DatosEstudiante>  datosEst = q.getResultList();
		DatosEstudiante resultado = dat;
		return resultado;
	}
	// Este metodo no es como la obtencion de tipo lista
	// que se hace?

	private boolean correoValido(String email_persona){
        boolean resultado = true;
        try{
            Query q = em.createQuery("SELECT p FROM Persona p WHERE p.emailPersona=:correo").setParameter("correo", email_persona);
            Persona c = (Persona) q.getSingleResult();
        }
        catch(NoResultException ex){
            resultado = false;
        }
        return resultado;
    }
	
	private boolean fechaValida(Date fecha_inicio_carrera, Date fecha_esperada_terminacion) {
		boolean resultado = true;
		long anno1 = 0;
		long anno2 = 0;
		Calendar calendario = Calendar.getInstance();
		
		calendario.setTime(fecha_inicio_carrera);
		long annoIni = calendario.get(Calendar.YEAR);
		anno1 = annoIni;
		
		calendario.setTime(fecha_esperada_terminacion);
		long annoFin = calendario.get(Calendar.YEAR);
		anno2 = annoFin;
		if ((anno2 - anno1)<3) {
			resultado = false
		}
		return resultado
	}
	
	@POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public boolean agregaDatosEstudiante (DatosEstudiante dato) {
		String resultado  "false";
		
		if (correoValido(dato.getEmailEstudiante()) 
			&& fechaValida(dato.getFechaInicioCarrera(), dato.getFechaEsperadaTerminacion())) {
			
			resultado = super.save(dato.getEmailEstudiante(), dato);
		}
		return resultado;
	}
	
	@PUT
    @Path("{email}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public boolean actualizaDatosEstudiante (PathParam("email") DatosEstudiante dato) {
		if (correoValido() == true && correoValido(dato.getEmailEstudiante())) {
			resultado = update.save(dato.getEmailEstudiante(), dato);
		}
		return resultado;
	}
	
	private boolean noHayRegistrosAsociados(String email){
        boolean resultado = false;
        try{
            Query q = em.createQuery("SELECT COUNT(p) FROM Persona p WHERE p.emailPersona=:correo").setParameter("correo", email);
            Long cantidad = (Long) q.getSingleResult();
			if(cantidad == 0) {
				resultado = true;
			}
        }
        catch(Exception ex){

        }
        return resultado;
    }
	
	@DELETE
    @Path("{email}")
    @Produces(MediaType.TEXT_PLAIN)
	public boolean eliminaDatosEstudiante (PathParam("email") String emailPersona) {
		String resultado = "false";
		if (noHayRegistrosAsociados(emailPersona)) {
			resultado = super.delete(emailPersona)
		}
		return resultado;
	}
}