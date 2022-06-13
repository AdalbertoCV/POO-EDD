package poo2.progs.servicios;
import poo2.progs.entidades.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
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
    public List<Sede> obtenSedes(){
        return super.getAll();
    }

    public boolean emailValido(String email, Sede s){
        boolean resultado = true;
        try{
            Query q = em.createQuery("SELECT p FROM Persona p WHERE p.emailPersona=:email").setParameter("email",email);
            Persona persona = (Persona) q.getSingleResult();
            if (!persona.getTipoPersona().equals("Profesor")){
                resultado = false;
            }
            else{
                if (!(s.getIdInstitucionSede() == persona.getIdInstitucionPersona())){
                    resultado = false;
                }
            }
        }
        catch(Exception ex){
            resultado = false;
        }
        return resultado;
    }

    private boolean institucionValida(long id_institucion){
        boolean resultado = true;
        try{
            Query q = em.createQuery("SELECT i FROM Institucion i WHERE i.idInstitucion=:id").setParameter("id", id_institucion);
            Institucion i = (Institucion) q.getSingleResult();
        }
        catch(NoResultException ex){
            resultado = false;
        }
        return resultado;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String agregaSede(Sede dato) {
        String resultado = "false";
        if (emailValido(dato.getEmailDirectorSede(), dato) && institucionValida(dato.getIdInstitucionSede())){
            resultado = super.save(dato.getIdSede(), dato);
        }
        return resultado;
    }

    @PUT
    @Path("{idsede}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String actualizaSede(@PathParam("idsede") Long idsede, Sede dato){
        String resultado = "false";
        if (emailValido(dato.getEmailDirectorSede(), dato) && institucionValida(dato.getIdInstitucionSede())
        && dato.getIdSede() == idsede){
            resultado = super.update(dato.getIdSede(), dato);
        }
        return resultado;
    }

    private boolean noHayRegistrosAsociados(Long idSede) {
        boolean resultado = false;
        try{
            Query q = em.createQuery("SELECT COUNT (sc) FROM SedeConcurso sc WHERE sc.idSede=:id").setParameter("id", idSede);
            Long cantidad = (Long) q.getSingleResult();
            if (cantidad == 0){
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
    public String elimina(@PathParam("idsede") Long id){
        String resultado = "false";
        if (noHayRegistrosAsociados(id)){
            resultado=  super.delete(id);
        }
        return resultado;
    }
    @GET
    @Path("disponibles/{idconc}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Sede> obtenSedesDisponibles(@PathParam("idconc") long idconcurso) {
        List<Sede> resultado = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT m FROM SedeConcurso m WHERE m.idConcurso=:id").setParameter("id", idconcurso);
            List<SedeConcurso> sedec = (List<SedeConcurso>) q.getResultList();
            Iterator sedecI = sedec.iterator();
            SedeConcurso sedeconcurso;
            while(sedecI.hasNext()){
                sedeconcurso=(SedeConcurso) sedecI.next();
                ids.add(sedeconcurso.getIdSede());
            }
            q = em.createQuery("SELECT m FROM Sede m");
            List<Sede> disponibles=(List<Sede>) q.getResultList();
            Iterator SEDE = disponibles.iterator();
            Sede sede;
            while(SEDE.hasNext()){
                sede=(Sede) SEDE.next();
                if(!ids.contains(sede.getIdSede())){
                    long idsede = sede.getIdSede();
                    String nombreSede = sede.getNombreSede();
                    long idinst = sede.getIdInstitucionSede();
                    String email = sede.getEmailDirectorSede();
                    String url = sede.getUrlSede();
                    Sede agregar = new Sede(idsede, nombreSede, idinst, email);
                    agregar.setUrlSede(url);
                    resultado.add(agregar);

                }
            }
        } catch (Exception ex) {

        }
        return resultado;
    }
}
