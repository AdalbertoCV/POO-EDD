package poo2.progs.servicios;

import poo2.progs.entidades.Institucion;
import poo2.progs.entidades.Municipio;
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
@Path("persona")
public class PersonaREST extends RESTAbstracto<Persona>{
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public PersonaREST() {
        super(Persona.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Persona> obtenPersonas() {
        return super.getAll();
    }

    private boolean entidadValida(long id_entidad) {
        return (id_entidad >=1 && id_entidad <=32);
    }

    private boolean municipioValido(long id_municipio){
        boolean resultado = true;
        try{
            Query q = em.createQuery("SELECT m FROM Municipio m WHERE m.idMunicipio=:id").setParameter("id", id_municipio);
            Municipio m = (Municipio) q.getSingleResult();
        }
        catch(NoResultException ex){
            resultado = false;
        }
        return resultado;
    }

    private boolean institucionValida(long id_institucion) {
        boolean resultado = true;
        try {
            Query q = em.createQuery("SELECT i FROM Institucion i WHERE i.idInstitucion=:id").setParameter("id", id_institucion);
            Institucion i = (Institucion) q.getSingleResult();
        }catch(NoResultException ex) {
            resultado = false;
        }
        return resultado;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes ({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String agregaPersona(Persona dato) {
        String resultado = "false";
        if (entidadValida(dato.getIdEntidadPersona())
                && municipioValido(dato.getIdMunicipioPersona())
                && institucionValida(dato.getIdInstitucionPersona()) && (dato.getTipoPersona().equals("Estudiante") || dato.getTipoPersona().equals("Profesor"))
                && (dato.getSexoPersona().equals("M") || dato.getSexoPersona().equals("F"))) {

            resultado = super.save(dato.getEmailPersona(), dato);
        }
        return resultado;
    }

    private boolean noHayRegistrosAsociados(String email){
        boolean resultado = false;
        try{
            Query q = em.createQuery("SELECT COUNT (de) FROM DatosEstudiante de WHERE de.emailEstudiante=:id").setParameter("id",email);
            Long cantidad = (Long) q.getSingleResult();
            if (cantidad==0){
                q = em.createQuery("SELECT COUNT (e) FROM Equipo e WHERE e.emailCoach=:id").setParameter("id",email);
                cantidad =  (Long) q.getSingleResult();
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
        }
        catch(Exception ex){
            resultado = false;
        }
        return resultado;
    }

    @DELETE
    @Path("{emailpersona}")
    @Produces(MediaType.TEXT_PLAIN)
    public String eliminaPersona(@PathParam("emailpersona") String email) {
        String resultado = "false";
        if (noHayRegistrosAsociados(email)) {
            return super.delete(email);
        }
        return resultado;
    }

    @PUT
    @Path("{emailpersona}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String actualizaPersona(@PathParam("emailpersona")String email, Persona dato) {
        String resultado = "false";
        if (entidadValida(dato.getIdEntidadPersona())
                && municipioValido(dato.getIdMunicipioPersona())
                && institucionValida(dato.getIdInstitucionPersona())
                && email.equals(dato.getEmailPersona())) {

            resultado = super.update(dato.getEmailPersona(), dato);
        }
        return resultado;
    }

    @GET
    @Path("correos/{idinst}/{tipopersona}")
    @Produces(MediaType.TEXT_PLAIN)
    public String obtenCorreosDeInstitucion(@PathParam("idinst") Long idInstitucion, @PathParam("tipopersona") String tipo){
        Query q = em.createQuery("SELECT p.emailPersona FROM Persona p WHERE p.idInstitucionPersona=:idI AND p.tipoPersona=:tipoP").setParameter("idI",idInstitucion).setParameter("tipoP",tipo);
        List<String> emails=  q.getResultList();
        String resultado = "";
        for (int x =0; x<emails.size();x++){
            resultado = resultado + emails.get(x) + ";";
        }
        return resultado;
    }
}
