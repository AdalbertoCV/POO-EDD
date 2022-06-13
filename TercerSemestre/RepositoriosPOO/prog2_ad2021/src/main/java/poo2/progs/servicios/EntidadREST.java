package poo2.progs.servicios;

import poo2.progs.entidades.Entidad;

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
@Path("entidad")
public class EntidadREST extends RESTAbstracto<Entidad> {
	
	@PersistenceContext(unitName = "default")
    private EntityManager em;

    public EntidadREST() {
        super(Entidad.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
	
	@GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Entidad> getAll(){
        return super.getAll();
    }
}