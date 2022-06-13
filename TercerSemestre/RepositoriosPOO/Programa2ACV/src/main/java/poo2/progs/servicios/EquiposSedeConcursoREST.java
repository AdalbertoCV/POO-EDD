package poo2.progs.servicios;

import poo2.progs.entidades.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("equipossedeconcurso")
public class EquiposSedeConcursoREST extends RESTAbstracto<EquiposSedeConcurso>{
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public EquiposSedeConcursoREST() {
        super(EquiposSedeConcurso.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public boolean EquipoValido(long idEquipo){
        boolean resultado = true;
        try{
            Query q = em.createQuery("SELECT e FROM Equipo e WHERE e.idEquipo=:id").setParameter("id", idEquipo);
            Equipo e = (Equipo) q.getSingleResult();
        }
        catch(Exception ex){
            resultado = false;
        }
        return resultado;
    }

    public boolean sedeConcursoValida(long idSedeConcurso){
        boolean resultado = true;
        try{
            Query q = em.createQuery("SELECT sc FROM SedeConcurso sc WHERE sc.idSedeConcurso=:id").setParameter("id", idSedeConcurso);
            SedeConcurso sc = (SedeConcurso) q.getSingleResult();
        }
        catch(Exception ex){
            resultado = false;
        }
        return resultado;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String agregaEquipoSedeConcurso(EquiposSedeConcurso dato) {
        String resultado = "false";
        if (sedeConcursoValida(dato.getIdSedeConcurso()) && EquipoValido(dato.getIdEquipo())){
            resultado = super.save(dato.getIdEquipoSedeConcurso(), dato);
        }
        return resultado;
    }

    @DELETE
    @Path("{idequiposedeconc}")
    @Produces(MediaType.TEXT_PLAIN)
    public String elimina(@PathParam("idequiposedeconc") Long id){
        return super.delete(id);
    }

    @GET
    @Path("registrados/{idconc}/{idinst}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<EquiposSedeConcursoExtendida> obtenEquiposRegistrados(@PathParam("idconc") Long idConcurso,@PathParam("idinst") Long idInstitucion){
        List<EquiposSedeConcursoExtendida> resultado = new ArrayList<>();
        try{
            Query q = em.createQuery("SELECT c FROM Concurso c WHERE c.idConcurso=:id").setParameter("id", idConcurso);
            Concurso c = (Concurso) q.getSingleResult();
            String nombreConcurso = c.getNombreConcurso();
            q = em.createQuery("SELECT sc FROM SedeConcurso sc WHERE sc.idConcurso=:id").setParameter("id", idConcurso);
            List<SedeConcurso> sc = q.getResultList();
            for (int x =0; x<sc.size();x++){
                long idSedeConcurso = sc.get(x).getIdSedeConcurso();
                long idSede = sc.get(x).getIdSede();
                q = em.createQuery("SELECT s FROM Sede s WHERE s.idSede=:id").setParameter("id", idSede);
                Sede s = (Sede) q.getSingleResult();
                String nombreSede = s.getNombreSede();
                q = em.createQuery("SELECT esc FROM EquiposSedeConcurso esc WHERE esc.idSedeConcurso=:id").setParameter("id", idSedeConcurso);
                List<EquiposSedeConcurso> esc = q.getResultList();
                for (int i=0; i<esc.size();i++){
                    long idEquipoSedeConcurso = esc.get(i).getIdEquipoSedeConcurso();
                    long idEquipo = esc.get(i).getIdEquipo();
                    q = em.createQuery("SELECT e FROM Equipo e WHERE e.idEquipo=:id").setParameter("id", idEquipo);
                    Equipo e = (Equipo) q.getSingleResult();
                    String nombreEquipo = e.getNombreEquipo();
                    long idInstEquipo = e.getIdInstitucionEquipo();
                    if (idInstitucion == idInstEquipo){
                        EquiposSedeConcursoExtendida ESC = new EquiposSedeConcursoExtendida(idEquipoSedeConcurso, idEquipo, idSedeConcurso);
                        ESC.setNombreEquipo(nombreEquipo);
                        ESC.setNombreSede(nombreSede);
                        ESC.setNombreConcurso(nombreConcurso);
                        resultado.add(ESC);
                    }
                }
            }
        }
        catch(Exception ex){
        }
        return resultado;
    }

}
