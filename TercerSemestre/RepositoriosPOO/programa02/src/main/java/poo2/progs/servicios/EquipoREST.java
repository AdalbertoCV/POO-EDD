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
@Path("equipo")
public class EquipoREST extends RESTAbstracto<Equipo> {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public EquipoREST() {
        super(Equipo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Equipo> obtenEquipos(){
        return super.getAll();
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

    public boolean integrantesValidos(Equipo dato){
        boolean resultado = true;
        String eCo = dato.getEmailCoach();
        String ec1 = dato.getEmailConcursante1();
        String ec2 = dato.getEmailConcursante2();
        String ec3 = dato.getEmailConcursante3();
        try{
            Query q = em.createQuery("SELECT p FROM Persona p WHERE p.emailPersona=:email").setParameter("email", dato.getEmailCoach());
            Persona p = (Persona) q.getSingleResult();
            if (!p.getTipoPersona().equals("Profesor") || (p.getIdInstitucionPersona() != dato.getIdInstitucionEquipo())){
                resultado = false;
            }
            else{
                q = em.createQuery("SELECT p FROM Persona p WHERE p.emailPersona=:email").setParameter("email", dato.getEmailConcursante1());
                p = (Persona) q.getSingleResult();
                if (!p.getTipoPersona().equals("Estudiante") || (p.getIdInstitucionPersona() != dato.getIdInstitucionEquipo())){
                    resultado = false;
                }
                else{
                    q = em.createQuery("SELECT p FROM Persona p WHERE p.emailPersona=:email").setParameter("email", dato.getEmailConcursante2());
                    p = (Persona) q.getSingleResult();
                    if (!p.getTipoPersona().equals("Estudiante") || (p.getIdInstitucionPersona() != dato.getIdInstitucionEquipo())){
                        resultado = false;
                    }
                    else{
                        q = em.createQuery("SELECT p FROM Persona p WHERE p.emailPersona=:email").setParameter("email", dato.getEmailConcursante3());
                        p = (Persona) q.getSingleResult();
                        if (!p.getTipoPersona().equals("Estudiante") || (p.getIdInstitucionPersona() != dato.getIdInstitucionEquipo())){
                            resultado = false;
                        }
                        else{
                            q = em.createQuery("SELECT COUNT (p) FROM Persona p WHERE p.emailPersona=:email").setParameter("email", dato.getEmailConcursanteReserva());
                            long cantidad = (long) q.getSingleResult();
                            if (cantidad !=0){
                                String eR = dato.getEmailConcursanteReserva();
                                q = em.createQuery("SELECT p FROM Persona p WHERE p.emailPersona=:email").setParameter("email", dato.getEmailConcursanteReserva());
                                p = (Persona) q.getSingleResult();
                                if (!p.getTipoPersona().equals("Estudiante") || (p.getIdInstitucionPersona() != dato.getIdInstitucionEquipo())){
                                    resultado = false;
                                }
                                else{
                                    if (ec1.equals(ec2) || ec1.equals(ec3) || ec1.equals(eR) || ec1.equals(eCo) ||
                                            ec2.equals(ec3 )|| ec2.equals(eR) || ec3.equals(eCo) ||
                                            ec3.equals(eR) || ec3.equals(eCo) ||
                                            eR.equals(eCo)){
                                        resultado = false;
                                    }
                                }
                            }
                            else{
                                if (ec1.equals(ec2) || ec1.equals(ec3 ) || ec1.equals(eCo) ||
                                        ec2.equals(ec3)  || ec2.equals(eCo)  || ec3.equals(eCo)){
                                    resultado = false;
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

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String agregaEquipo(Equipo dato) {
        String resultado = "false";
        if (institucionValida(dato.getIdInstitucionEquipo()) && integrantesValidos(dato)){
            resultado = super.save(dato.getIdEquipo(),dato);
        }
        return resultado;
    }

    @PUT
    @Path("{idequipo}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String actualizaEquipo(@PathParam("idequipo") Long id, Equipo dato){
        String resultado = "false";
        if (institucionValida(dato.getIdInstitucionEquipo()) && integrantesValidos(dato) && (dato.getIdEquipo() == id)){
            resultado = super.update(dato.getIdEquipo(),dato);
        }
        return resultado;
    }

    private boolean noHayRegistrosAsociados(long idEquipo){
        boolean resultado = false;
        try{
            Query q = em.createQuery("SELECT COUNT (esc) FROM EquiposSedeConcurso esc WHERE esc.idEquipo=:id").setParameter("id", idEquipo);
            long cantidad = (Long) q.getSingleResult();
            if (cantidad == 0){
                resultado = true;
            }
        }
        catch(Exception ex) {
        }
        return resultado;
    }

    @DELETE
    @Path("{idequipo}")
    @Produces(MediaType.TEXT_PLAIN)
    public String elimina(@PathParam("idequipo") Long id){
        String resultado = "false";
        if (noHayRegistrosAsociados(id)){
            resultado=  super.delete(id);
        }
        return resultado;
    }

    @GET
    @Path("disponibles/{idconcurso}/{idinst}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Equipo> obtenEquiposDisponibles(@PathParam("idconcurso") Long idconcurso, @PathParam("idinst") Long idinst){
        List<Equipo> resultado= new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        try{
            Query q = em.createQuery("SELECT sc FROM SedeConcurso sc WHERE sc.idConcurso=:id").setParameter("id", idconcurso);
            List<SedeConcurso> sc = q.getResultList();
            for (int x=0; x<sc.size();x++){
                long idSedeConcurso = sc.get(x).getIdSedeConcurso();
                q = em.createQuery("SELECT esc FROM EquiposSedeConcurso esc WHERE esc.idSedeConcurso=:id").setParameter("id", idSedeConcurso);
                List<EquiposSedeConcurso> esc = q.getResultList();
                for (int i =0; i<esc.size();i++){
                    long idEquipos = esc.get(i).getIdEquipo();
                    ids.add(idEquipos);
                }
            }
            if (ids.size() == 0){
                q = em.createQuery("SELECT esc FROM EquiposSedeConcurso esc WHERE esc.idSedeConcurso=:id").setParameter("id", idconcurso);
                List<EquiposSedeConcurso> ESC = q.getResultList();
                for (int y =0; y<ESC.size(); y++){
                    long idE = ESC.get(y).getIdEquipo();
                    ids.add(idE);
                }
                try{
                    q = em.createQuery("SELECT e FROM Equipo e");
                    List<Equipo> e = q.getResultList();
                    for (int w =0; w<e.size();w++){
                        long idteam = e.get(w).getIdEquipo();
                        String nombreequipo = e.get(w).getNombreEquipo();
                        String mailC = e.get(w).getEmailCoach();
                        String mailC1 = e.get(w).getEmailConcursante1();
                        String mailC2 = e.get(w).getEmailConcursante2();
                        String mailC3 = e.get(w).getEmailConcursante3();
                        String mailCR = e.get(w).getEmailConcursanteReserva();
                        long idIE = e.get(w).getIdInstitucionEquipo();
                        if (idIE == idinst){
                            if (ids.contains(idteam)){
                                Equipo equipo = new Equipo(idteam, nombreequipo, mailC, mailC1, mailC2, mailC3, idIE);
                                equipo.setEmailConcursanteReserva(mailCR);
                                resultado.add(equipo);
                            }
                        }
                    }
                    if (!(resultado.size()==0)){
                        resultado.remove(0);
                    }
                }
                catch(Exception ex){
                }
            }
        }
        catch(Exception ex){
        }
        try{
            Query q = em.createQuery("SELECT e FROM Equipo e");
            List<Equipo> e = q.getResultList();
            for (int w =0; w<e.size();w++){
                long idteam = e.get(w).getIdEquipo();
                String nombreequipo = e.get(w).getNombreEquipo();
                String mailC = e.get(w).getEmailCoach();
                String mailC1 = e.get(w).getEmailConcursante1();
                String mailC2 = e.get(w).getEmailConcursante2();
                String mailC3 = e.get(w).getEmailConcursante3();
                String mailCR = e.get(w).getEmailConcursanteReserva();
                long idIE = e.get(w).getIdInstitucionEquipo();
                if (idIE == idinst){
                    if (!ids.contains(idteam)){
                        Equipo equipo = new Equipo(idteam, nombreequipo, mailC, mailC1, mailC2, mailC3, idIE);
                        equipo.setEmailConcursanteReserva(mailCR);
                        resultado.add(equipo);
                    }
                }
            }
        }
        catch(Exception ex){
        }
        return resultado;
    }
}
