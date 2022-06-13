package poo2.progs.servicios;

import poo2.progs.entidades.Institucion;
import poo2.progs.entidades.Municipio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("institucion")
public class InstitucionREST extends RESTAbstracto<Institucion>{
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public InstitucionREST() {
        super(Institucion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Institucion> getAll(){
        return super.getAll();
    }

    private boolean entidadValida(long id_entidad){
        return (id_entidad>=1 && id_entidad<=32);
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

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String agrega(Institucion dato){
        String resultado = "false";
        if (dato.getIdEntidadInstitucion() == (dato.getIdMunicipioInstitucion()/1000)
                && entidadValida(dato.getIdEntidadInstitucion())
                && municipioValido(dato.getIdMunicipioInstitucion())){
            resultado = super.save(dato.getIdInstitucion(),dato);
        }
        return resultado;
    }

    @PUT
    @Path("{idinst: [0-9]+}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String actualiza(@PathParam("idinst") Long id, Institucion dato){
        String resultado = "false";
        if (dato.getIdEntidadInstitucion() == (dato.getIdMunicipioInstitucion()/1000)
                && entidadValida(dato.getIdEntidadInstitucion())
                && municipioValido(dato.getIdMunicipioInstitucion())
                && id == dato.getIdInstitucion()){
            resultado = super.update(id,dato);
        }
        return resultado;
    }
    private boolean noHayRegistrosAsociados(long idinst){
        boolean resultado = false;
        try{
            Query q = em.createQuery("SELECT COUNT(s) FROM Sede s WHERE s.idInstitucionSede=:id").setParameter("id", idinst);
            Long cantidad = (Long) q.getSingleResult();
            if (cantidad==0){
                q = em.createQuery("SELECT COUNT(p) FROM Persona p WHERE p.idInstitucionPersona=:id").setParameter("id", idinst);
                cantidad = (Long) q.getSingleResult();
                if (cantidad ==0){
                    q = em.createQuery("SELECT COUNT(e) FROM Equipo e WHERE e.idInstitucionEquipo=:id").setParameter("id", idinst);
                    cantidad = (Long) q.getSingleResult();
                    if (cantidad==0){
                        resultado = true;
                    }
                }
            }

        }
        catch(Exception ex){

        }
        return resultado;
    }

    @DELETE
    @Path("{idinst: [0-9]+}")
    @Produces(MediaType.TEXT_PLAIN)
    public String elimina(@PathParam("idinst") Long id){
        String resultado = "false";
        if (noHayRegistrosAsociados(id)){
            resultado=  super.delete(id);
        }
        return resultado;
    }
}
