package poo2.progs.servicios;

import poo2.progs.entidades.Concurso;
import poo2.progs.entidades.Sede;
import poo2.progs.entidades.SedeConcurso;
import poo2.progs.entidades.SedeConcursoExtendida;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("sedeconcurso")
public class SedeConcursoREST extends RESTAbstracto<SedeConcurso> {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public SedeConcursoREST() {
        super(SedeConcurso.class);
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public boolean sedeCorrecta(long idsede){
        boolean resultado = true;
        try{
            Query q = em.createQuery("SELECT s FROM Sede s WHERE s.idSede=:id").setParameter("id", idsede);
            Sede s = (Sede) q.getSingleResult();
        }
        catch(Exception ex){
            resultado = false;
        }
        return resultado;
    }

    public boolean ConcursoCorrecto(long idConcurso){
        boolean resultado = true;
        try{
            Query q = em.createQuery("SELECT c FROM Concurso c WHERE c.idConcurso=:id").setParameter("id", idConcurso);
            Concurso c = (Concurso) q.getSingleResult();
        }
        catch(Exception ex){
            resultado = false;
        }
        return resultado;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String agregaSedeConcurso(SedeConcurso dato) {
        String resultado = "false";
        if (sedeCorrecta(dato.getIdSede()) && ConcursoCorrecto(dato.getIdConcurso())){
            resultado = super.save(dato.getIdSedeConcurso(),dato);
        }
        return resultado;
    }

    private boolean noHayRegistrosAsociados(long idSedeConcurso){
        boolean resultado = false;
        try{
            Query q = em.createQuery("SELECT COUNT (esc) FROM EquiposSedeConcurso esc WHERE esc.idSedeConcurso=:id").setParameter("id", idSedeConcurso);
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
    @Path("{idsedeconc}")
    @Produces(MediaType.TEXT_PLAIN)
    public String elimina(@PathParam("idsedeconc") Long id){
        String resultado = "false";
        if (noHayRegistrosAsociados(id)){
            resultado=  super.delete(id);
        }
        return resultado;
    }
    @Path("asignadas/{idconcurso}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SedeConcursoExtendida> obtenSedesAsignadas(@PathParam("idconcurso") Long idConcurso){
        List<SedeConcursoExtendida> resultado = new ArrayList<>();
        Query q = em.createQuery("SELECT sc FROM SedeConcurso  sc WHERE sc.idConcurso=:id").setParameter("id",idConcurso);
        List<SedeConcurso> sedesA = q.getResultList();
        for (int x=0;x<sedesA.size();x++){
            long idSCE = sedesA.get(x).getIdSedeConcurso();
            long idsede = sedesA.get(x).getIdSede();
            long idconcurso = sedesA.get(x).getIdConcurso();
            SedeConcursoExtendida add = new SedeConcursoExtendida(idSCE,idsede,idconcurso);
            q = em.createQuery("SELECT s FROM Sede s WHERE s.idSede=:id").setParameter("id", idsede);
            Sede sede = (Sede) q.getSingleResult();
            String nSede = sede.getNombreSede();
            q = em.createQuery("SELECT c FROM Concurso c WHERE c.idConcurso=:id").setParameter("id", idconcurso);
            Concurso conc = (Concurso) q.getSingleResult();
            String nConc = conc.getNombreConcurso();
            add.setNombreConcurso(nConc);
            add.setNombreSede(nSede);
            resultado.add(add);
        }
        return resultado;
    }
}
