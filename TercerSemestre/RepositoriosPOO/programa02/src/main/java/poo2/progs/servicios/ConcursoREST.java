package poo2.progs.servicios;

import poo2.progs.entidades.Concurso;
import poo2.progs.entidades.DatosEstudiante;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless
@Path("concurso")
public class ConcursoREST extends RESTAbstracto<Concurso> {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public ConcursoREST() {
        super(Concurso.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Concurso> obtenConcursos(){
        return super.getAll();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String agregaConcurso(Concurso Dato){
        String resultado = "false";
        Calendar cal = Calendar.getInstance();
        cal.setTime(Dato.getFechaConcurso());
        long diaC = cal.get(Calendar.DAY_OF_MONTH);
        long mesC= cal.get(Calendar.MONTH);
        cal.setTime(Dato.getFechaInicioRegistro());
        long diaIR =cal.get(Calendar.DAY_OF_MONTH);
        long mesIR =cal.get(Calendar.MONTH);
        cal.setTime(Dato.getFechaFinRegistro());
        long diaFR =cal.get(Calendar.DAY_OF_MONTH);
        long mesFR=cal.get(Calendar.MONTH);
        if(!(((mesC==mesFR)&&(diaC<=diaFR)) ||
                (mesC<mesFR) || ((mesC==mesIR)&&(diaC<=diaIR)) ||
                (mesC<mesIR))){
            if (!(((mesFR== mesIR)&&(diaFR<=diaIR)) || (mesFR<mesIR))){
                resultado= super.save(Dato.getIdConcurso(),Dato);
            }
        }
        return resultado;
    }
    @PUT
    @Path("{idconcurso}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String actualizaConcurso(@PathParam("idconcurso") Long idconcurso, Concurso Dato){
        String resultado = "false";
        Calendar cal = Calendar.getInstance();
        cal.setTime(Dato.getFechaConcurso());
        long diaC = cal.get(Calendar.DAY_OF_MONTH);
        long mesC= cal.get(Calendar.MONTH);
        cal.setTime(Dato.getFechaInicioRegistro());
        long diaIR =cal.get(Calendar.DAY_OF_MONTH);
        long mesIR =cal.get(Calendar.MONTH);
        cal.setTime(Dato.getFechaFinRegistro());
        long diaFR =cal.get(Calendar.DAY_OF_MONTH);
        long mesFR=cal.get(Calendar.MONTH);
        if(!(((mesC==mesFR)&&(diaC<=diaFR)) ||
                (mesC<mesFR) || ((mesC==mesIR)&&(diaC<=diaIR)) ||
                (mesC<mesIR))){
            if (!(((mesFR== mesIR)&&(diaFR<=diaIR)) || (mesFR<mesIR)) && (idconcurso== Dato.getIdConcurso())){
                resultado= super.update(Dato.getIdConcurso(),Dato);
            }
        }
        return resultado;
    }
    private boolean noHayRegistrosAsociados(Long idConcurso) {
        boolean resultado = false;
        try{
            Query q = em.createQuery("SELECT COUNT (sc) FROM SedeConcurso sc WHERE sc.idConcurso=:id").setParameter("id", idConcurso);
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
    @Path("{idconcurso}")
    @Produces(MediaType.TEXT_PLAIN)
    public String elimina(@PathParam("idconcurso") Long id){
        String resultado = "false";
        if (noHayRegistrosAsociados(id)){
            resultado=  super.delete(id);
        }
        return resultado;
    }
}
