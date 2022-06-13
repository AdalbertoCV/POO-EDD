package poo2.progs.servicios;

import poo2.progs.entidades.Concurso;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("concurso")
public class ConcursoREST extends RESTAbstracto<Condurso>{
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
	public List<Concurso> obtenConcursos() {
		return super.getAll();
	}
	
	// no entiendo como validar las fechas en concurso
	private boolean fechaValida(Date fecCon, Date fecIniReg, Date fecFinReg) {
		boolean resultado = true
		
		Calendar calendario = Calendar.getInstance();
		
		long diaCon = 0;
		long mesCon = 0;
		long diaIniReg = 0;
		long diaFinReg = 0;
		long mesIniReg =0;
		long mesFinReg=0;

		calendario.setTime(fecCon);
		long a = calendario.get(Calendar.DAY_OF_MONTH);
		diaCon = a;
		a = calendario.get(Calendar.MONTH);
		mesCon = a;

		calendario.setTime(fecIniReg);
		long b = calendario.get(Calendar.DAY_OF_MONTH);
		diaIniReg = b;
		b = calendario.get(Calendar.MONTH);
		mesIniReg = b;

		calendario.setTime(fecFinReg);
		long c = calendario.get(Calendar.DAY_OF_MONTH);
		diaFinReg = c;
		c = calendario.get(Calendar.MONTH);
		mesFinReg = c;
		
		if(((mesCon == mesFinReg) && (diaCon <= diaFinReg)) ||
			(mesCon < mesFinReg) || ((mesCon == mesIniReg) && 
			(diaCon <= diaIniReg)) || (mesCon < mesIniReg)){
				resultado = false;
		}
		if (((mesFinReg == mesIniReg) && (diaFinReg <= diaIniReg)) || 
		(mesFinReg < mesIniReg)){
			resultado= false;
		}
		return resultado;
	}
	
	@POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String agregaConcurso(Concurso dato) {
		String resultado = "false";
		if (fechaValida(dato.getFechaConcurso(), dato.getFechaInicioRegistro(),
			dato.getFechaFinRegistro())) {
			resultado = super.save(dato.getIdConcurso(), dato);
		}
		return resultado;
	}
	
	@PUT
    @Path("{idconcurso: [0-9]+}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String actualizaConcurso(PathParam(idconcurso) long idCon, Concurso dato) {
		String resultado = "false";
		if (fechaValida(dato.getFechaConcurso(), dato.getFechaInicioRegistro(),
			dato.getFechaFinRegistro())) {
			resultado = super.update(dato.getIdConcurso(), dato);
		}
		return resultado;
	}
	
	private boolean noHayRegistrosAsociados(long idconcurso){
        boolean resultado = false;
        try{
            Query q = em.createQuery("SELECT COUNT(sc) FROM SedeConcurso sc WHERE sc.idConcurso=:id").setParameter("id", idconcurso);
            Long cantidad = (Long) q.getSingleResult();
            if (cantidad==0){
				resultado = true;
			}
        }
        catch(Exception ex){

        }
        return resultado;
    }
	
	@DELETE
    @Path("{idconcurso: [0-9]+}")
    @Produces(MediaType.TEXT_PLAIN)
	public String eliminaConcurso(long idConc) {
		String resultado = "false";
        if (noHayRegistrosAsociados(idConc)){
            resultado=  super.delete(idConc);
        }
        return resultado;
	}

}