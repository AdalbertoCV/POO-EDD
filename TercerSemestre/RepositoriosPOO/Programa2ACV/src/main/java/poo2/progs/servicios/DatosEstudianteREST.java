package poo2.progs.servicios;
import poo2.progs.entidades.DatosEstudiante;
import poo2.progs.entidades.Institucion;
import poo2.progs.entidades.Persona;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Calendar;
import java.util.Optional;

@Stateless
@Path("datosestudiante")
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
    @Path("{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public DatosEstudiante get(@PathParam("email") String email) {
        return super.get(email);
    }

    public boolean personaValida(String emailEstudiante){
        boolean resultado = true;
        try{
            Query q = em.createQuery("SELECT p FROM Persona p WHERE p.emailPersona=:id").setParameter("id", emailEstudiante);
            Persona p = (Persona) q.getSingleResult();
            if (!p.getTipoPersona().equals("Estudiante")){
                resultado = false;
            }
        }
        catch(NoResultException ex){
            resultado = false;
        }
        return resultado;
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String agregaDatosEstudiante (DatosEstudiante dato){
        String resultado = "false";
        Calendar cal = Calendar.getInstance();
        cal.setTime(dato.getFechaInicioCarrera());
        long fechaIn = cal.get(Calendar.YEAR);
        cal.setTime(dato.getFechaEsperadaTerminacion());
        long fechaTer = cal.get(Calendar.YEAR);
        if (personaValida(dato.getEmailEstudiante()) && ((fechaTer - fechaIn)>3)){
            resultado = super.save(dato.getEmailEstudiante(),dato);
        }
        return resultado;
    }

    @PUT
    @Path("{email}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String actualiza(@PathParam("email") String email, DatosEstudiante dato){
        String resultado = "false";
        Calendar cal = Calendar.getInstance();
        cal.setTime(dato.getFechaInicioCarrera());
        long fechaIn = cal.get(Calendar.YEAR);
        cal.setTime(dato.getFechaEsperadaTerminacion());
        long fechaTer = cal.get(Calendar.YEAR);
        if (personaValida(dato.getEmailEstudiante()) && ((fechaTer - fechaIn)>3) && (email.equals(dato.getEmailEstudiante()))){
            resultado = super.update(dato.getEmailEstudiante(),dato);
        }
        return resultado;
    }

    private boolean noHayRegistrosAsociados(String email){
        boolean resultado = false;
        try{
                Query q  = em.createQuery("SELECT COUNT (e) FROM Equipo e WHERE e.emailCoach=:id").setParameter("id",email);
                Long cantidad =  (Long) q.getSingleResult();
                if (cantidad==0) {
                    q = em.createQuery("SELECT COUNT (e) FROM Equipo e WHERE e.emailConcursante1=:id").setParameter("id", email);
                    cantidad = (Long) q.getSingleResult();
                    if (cantidad==0){
                        q = em.createQuery("SELECT COUNT (e) FROM Equipo e WHERE e.emailConcursante2=:id").setParameter("id",email);
                        cantidad =  (Long) q.getSingleResult();
                        if (cantidad==0){
                            q = em.createQuery("SELECT COUNT (e) FROM Equipo e WHERE e.emailConcursante3=:id").setParameter("id",email);
                            cantidad =  (Long) q.getSingleResult();
                            if (cantidad ==0){
                                q = em.createQuery("SELECT COUNT (e) FROM Equipo e WHERE e.emailConcursanteReserva=:id").setParameter("id",email);
                                cantidad =  (Long) q.getSingleResult();
                                if (cantidad==0){
                                    resultado=true;
                                }
                            }
                        }
                    }
                }
            }
        catch(Exception ex){
        }
        return resultado;
    }

    @DELETE
    @Path("{email}")
    @Produces(MediaType.TEXT_PLAIN)
    public String elimina(@PathParam("email") String id){
        String resultado = "false";
        if (noHayRegistrosAsociados(id)){
            resultado=  super.delete(id);
        }
        return resultado;
    }

}
