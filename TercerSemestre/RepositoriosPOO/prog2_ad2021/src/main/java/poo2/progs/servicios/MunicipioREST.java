package poo2.progs.servicios;

import poo2.progs.entidades.Municipio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("municipio")
public class MunicipioREST extends RESTAbstracto<Municipio> {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public MunicipioREST() {
        super(Municipio.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("{identidad: [0-9]+}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Municipio> obtenMunicipios(@PathParam("identidad") long idEntidad){
        long liminf = idEntidad*1000;
        long limsup = (idEntidad+1) *1000;
        Query q = em.createQuery("SELECT m FROM Municipio m WHERE m.idMunicipio>:li AND m.idMunicipio<:ls").setParameter("li", liminf).setParameter("ls", limsup);
        List<Municipio> result= q.getResultList();
        return result;
    }
}
