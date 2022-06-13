package poo2.progs.servicios;

import poo2.progs.entidades.Equipo;
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
@Path("equipo")
public class EquipoREST extends RESTAbstracto<Equipo>{
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public EquipoREST() {
        super(Equipo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Equipo> obtenEquipos() {
        return super.getAll();
    }

    private boolean correoValidos(String email, long id_institucion_equipo) {
        boolean resultado = true;
        try {
            Query q = em.createQuery(
                            "SELECT p FROM Persona p WHERE p.emailPersona=:correo").
                    setParameter("correo", email);
            Persona p = (Persona) q.getSingleResult();
			
			q = em.createQuery(
                            "SELECT i FROM Institucion i WHERE i.idInstitucion=:id").
                    setParameter("id", id_institucion_equipo);
			Institucion i = (Institucion) q.getSingleResult();
        }
        catch(NoResultException ex){
            resultado = false;
        }
        return resultado;

    }

    private boolean tipoEstValido(String email_persona) {
        boolean resultado = true;
        try {
            String tipoEstudiante = "Estudiante";
            Query q = em.createQuery(
                            "SELECT p.tipoPersona FROM Persona p WHERE p.emailPersona=:correo").
                    setParameter("correo", email_persona);
            String consulta = (String) q.getSingleResult();
            if (tipoEstudiante.equals(q) == true) {
                resultado = true;
            }
        }catch(NoResultException ex) {
            resultado = false;
        }
        return resultado;
    }

    private boolean tipoProfValido(String email_persona) {
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

    private boolean correosDuplicados(String email_coach, String email_concursante1, String email_concursante2, String email_concursante3,
                                      String email_concursante_reserva) {
        boolean resultado = true;
        try {
            Query q = em.createQuery(
                            "SELECT e.emailCoach FROM Equipo e WHERE e.emailCoach=:ec").
                    setParameter("ec", email_coach);
            String emailCoa = (String) q.getSingleResult();

             q = em.createQuery(
                            "SELECT e.emailConcursante1 FROM Equipo e WHERE e.emailConcursante1=:ecc1").
                    setParameter("ecc1", email_concursante1);
            String emailCon1 = (String) q.getSingleResult();

             q = em.createQuery(
                            "SELECT e.emailConcursante2 FROM Equipo e WHERE e.emailConcursante2=:ecc2").
                    setParameter("ecc2", email_concursante2);
            String emailCon2 = (String) q.getSingleResult();

             q = em.createQuery(
                            "SELECT e.emailConcursante3 FROM Equipo e WHERE e.emailConcursante3=:ecc3").
                    setParameter("ecc3", email_concursante3);
            String emailCon3 = (String) q.getSingleResult();

             q = em.createQuery(
                            "SELECT e.emailConcursanteReserva FROM Equipo e WHERE e.emailConcursanteReserva=:ecr").
                    setParameter("ecr", email_concursante_reserva);
            String emailConRes = (String) q.getSingleResult();

            if (emailCon1.equals(emailCon2) || emailCon1.equals(emailCon3) || emailCon1.equals(emailConRes)
                    || emailCon1.equals(emailCoa) || emailCon2.equals(emailCon3) || emailCon2.equals(emailConRes)
                    || emailCon3.equals(emailCoa) || emailCon3.equals(emailConRes) || emailCon3.equals(emailCoa)
                    || emailConRes.equals(emailCoa)) {
                resultado = false;
            }

            }catch(NoResultException ex) {
                resultado = false;
            }
        return resultado;
    }
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
    @Consumes ({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String agregaEquipo(Equipo dato) {
		String resultado = "false";
		if (correoValidos(dato.getEmailCoach(), dato.getIdInstitucionEquipo()) &&
			correoValidos(dato.getEmailConcursante1(), dato.getIdInstitucionEquipo()) &&
			correoValidos(dato.getEmailConcursante2(), dato.getIdInstitucionEquipo()) &&
			correoValidos(dato.getEmailConcursante3(), dato.getIdInstitucionEquipo()) &&
			tipoProfValido(dato.getEmailCoach()) &&
			tipoEstValido(dato.getEmailConcursante1()) &&
			tipoEstValido(dato.getEmailConcursante2()) &&
			tipoEstValido(dato.getEmailConcursante3()) &&
			correosDuplicados(dato.getEmailCoach(), dato.getEmailConcursante1(),
			dato.getEmailConcursante2(),dato.getEmailConcursante3(),
			dato.getEmailConcursanteReserva())
			institucionValida(dato.get )) {

				if (dato.getEmailConcursanteReserva() != null && 
					dato.getEmailConcursanteReserva() != "") {
					if (correoValidos(dato.getEmailConcursanteReserva(), 
						dato.getIdInstitucionEquipo()) &&
						tipoEstValido(dato.getEmailConcursanteReserva())) {
							resultado = super.save(dato.getIdEquipo(), dato);
					}
				}else {
					resultado = super.save(dato.getIdEquipo(), dato);
				}
			}
            return resultado;
        }
	
	@PUT
    @Path("{idequipo: [0-9]+}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String actualizaEquipo(Equipo dato) {
		String resultado = "false";
		if (correoValidos(dato.getEmailCoach()) &&
			correoValidos(dato.getEmailConcursante1()) &&
			correoValidos(dato.getEmailConcursante2()) &&
			correoValidos(dato.getEmailConcursante3()) &&
			tipoProfValido(dato.getEmailCoach()) &&
			tipoEstValido(dato.getEmailConcursante1()) &&
			tipoEstValido(dato.getEmailConcursante2()) &&
			tipoEstValido(dato.getEmailConcursante3()) &&
			correosDuplicados(dato.getEmailCoach(), dato.getEmailConcursante1(),
			dato.getEmailConcursante2(),dato.getEmailConcursante3(),
			dato.getEmailConcursanteReserva())) {

				if (dato.getEmailConcursanteReserva() != null && 
					dato.getEmailConcursanteReserva() != "") {
						if (correoValidos(dato.getEmailConcursanteReserva()) &&
							tipoEstValido(dato.getEmailConcursanteReserva())) {
								resultado = super.update(dato.getIdEquipo(), dato);
						}
				}else {
					resultado = super.update(dato.getIdEquipo(), dato);
				}
			}
            return resultado;
        }

        private boolean noHayRegistrosAsociados(long idequipo){
            boolean resultado = false;
            try{
                Query q = em.createQuery("SELECT COUNT (esc) FROM EquiposSedeConcurso esc WHERE esc.idEquipo=:id").setParameter("id", idequipo);
                Long cantidad = (Long) q.getSingleResult();
                if (cantidad==0){
                    resultado = true;
                }
            }catch(Exception ex){
				
			}
            return resultado;
        }

        @DELETE
        @Path("{idequipo: [0-9]+}")
        @Produces(MediaType.TEXT_PLAIN)
        public String eliminaEquipo(@PathParam("idequipo") Long idEquipo) {
            String resultado = "false";
            if (noHayRegistrosAsociados(idEquipo)) {
                resultado = super.delete(idEquipo);
            }
            return resultado;
        }

        @GET
        @Path("disponibles/{idsedeconcurso}/{idinst}")
        @Produces(MediaType.TEXT_PLAIN)
        public List<Equipo> obtenEquiposDisponibles(@PathParam("idsedeconcurso") long idSedeConcurso,@PathParam("idinst") long idInstitucion) {
            return null;
        }
		// No lo entiedo

}