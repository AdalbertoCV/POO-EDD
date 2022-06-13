package poo2.progs.basedatos;

import poo2.progs.entidades.Equipo;
import poo2.progs.interfaces.Dao;
import poo2.progs.interfaces.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoEquipo implements Dao<Equipo> {
    private Connection conn;
    private PreparedStatement buscaUnEquipo;
    private PreparedStatement buscaTodos;
    private PreparedStatement AgregaEquipo;
    private PreparedStatement ActualizaEquipo;
    private PreparedStatement BorraEquipo;
    private PreparedStatement buscaEquipoEnInst;
    private PreparedStatement buscaEmailEnPersona;
    private PreparedStatement buscaEquipoEnSede;


    public DaoEquipo(Connection con) throws DaoException {
        if (con==null) {
            throw new DaoException("Conector recibido es nulo");
        }
        conn=con;
        inicializaStmts();
    }
    public DaoEquipo(String ubicacionServidor, String nomBD,
                      String usuario, String clave)
            throws DaoException {
        String url=String.format("jdbc:mysql://%s/%s",
                ubicacionServidor, nomBD);
        try {
            conn= DriverManager.getConnection(url, usuario, clave);
        }
        catch (SQLException exsql) {
            throw new DaoException(exsql.getMessage(), exsql.getCause());
        }
        inicializaStmts();
    }

    public DaoEquipo() throws DaoException {
        this("localhost","controlconcursos",
                "IngSW","UAZsw2021");
        inicializaStmts();
    }

    public void inicializaStmts() throws DaoException{
        try{
            buscaUnEquipo = conn.prepareStatement("SELECT * FROM equipo WHERE id_equipo=?");
            buscaTodos = conn.prepareStatement("SELECT * FROM equipo");
            BorraEquipo = conn.prepareStatement("DELETE FROM equipo WHERE id_equipo=?");
            AgregaEquipo = conn.prepareStatement("INSERT INTO equipo SET "+
                    "nombre_equipo=?, email_coach=?, email_concursante1=?, "+
                    "email_concursante2=?, email_concursante3=?, "+
                    "email_concursante_reserva=?, id_institucion_equipo=?, " +
                    "id_equipo=?");
            ActualizaEquipo = conn.prepareStatement("UPDATE equipo SET "+
                    "nombre_equipo=?, email_coach=?, email_concursante1=?, "+
                    "email_concursante2=?, email_concursante3=?, "+
                    "email_concursante_reserva=?, id_institucion_equipo=? WHERE id_equipo=?");
            buscaEquipoEnInst = conn.prepareStatement("SELECT * FROM institucion WHERE id_institucion=?");
            buscaEmailEnPersona = conn.prepareStatement("SELECT * FROM persona WHERE email_persona=?");
            buscaEquipoEnSede = conn.prepareStatement("SELECT * FROM equipos_sede_concurso WHERE id_equipo=?");

        }
        catch(SQLException ex){
            throw new DaoException(ex.getMessage());
        }
    }


    @Override
    public Optional<Equipo> get(Object id) {
        Optional<Equipo> resultado = Optional.empty();
        if (id instanceof Long){
            long idEquipo = (Long) id;
            try{
                buscaUnEquipo.setLong(1,idEquipo);
                ResultSet rs = buscaUnEquipo.executeQuery();
                if (rs.next()){
                    String nombre = rs.getString("nombre_equipo");
                    String emailCouch = rs.getString("email_coach");
                    String emailc1 = rs.getString("email_concursante1");
                    String emailc2 = rs.getString("email_concursante2");
                    String emailc3 = rs.getString("email_concursante3");
                    String emailreserva = rs.getString("email_concursante_reserva");
                    long idInst = rs.getLong("id_institucion_equipo");
                    Equipo equipo = new Equipo(idEquipo, nombre, emailCouch, emailc1, emailc2, emailc3, idInst );
                    equipo.setEmailConcursanteReserva(emailreserva);
                    resultado= Optional.of(equipo);
                }
            }
            catch (SQLException ex){
                throw new DaoException(ex.getMessage());
            }
        }
        return resultado;
    }

    @Override
    public List<Equipo> getAll() {
       List<Equipo> resultado = new ArrayList<>();
       try{
           ResultSet rs = buscaTodos.executeQuery();
           while(rs.next()){
               long idEquipo = rs.getLong("id_equipo");
               String nombre = rs.getString("nombre_equipo");
               String emailCouch = rs.getString("email_coach");
               String emailc1 = rs.getString("email_concursante1");
               String emailc2 = rs.getString("email_concursante2");
               String emailc3 = rs.getString("email_concursante3");
               String emailreserva = rs.getString("email_concursante_reserva");
               long idInst = rs.getLong("id_institucion_equipo");
               Equipo equipo = new Equipo(idEquipo, nombre, emailCouch, emailc1, emailc2, emailc3, idInst );
               equipo.setEmailConcursanteReserva(emailreserva);
               resultado.add(equipo);
           }
       }
       catch(SQLException ex){
           throw new DaoException(ex.getMessage());
       }
       return resultado;
    }

    public boolean valida(Equipo info) {
        boolean resultado = true;
        if (info == null) {
            resultado = false;
        } else {
            String nom = info.getNombreEquipo();
            String eCo = info.getEmailCoach();
            String ec1 = info.getEmailConcursante1();
            String ec2 = info.getEmailConcursante2();
            String ec3 = info.getEmailConcursante3();
            // reglas para null y limite de tamaño...
            if (nom == null || nom.length() == 0 || nom.length() > 40 ||
                    eCo  == null || eCo=="" || eCo .length() == 0 || eCo .length() > 40 ||
                    ec1 == null || ec1=="" || ec1.length() == 0 || ec1.length() > 40 ||
                    ec2 == null || ec2 == "" || ec2.length() == 0 || ec2.length() > 40 ||
                    ec3 == null || ec3 =="" || ec3.length() == 0 || ec3.length() > 40) {
                resultado = false;
            } else {
                long idInsEquipo = info.getIdInstitucionEquipo();
                // reserva
                String eR = info.getEmailConcursanteReserva();
                if (eR != null && eR.length() > 40) {
                    resultado = false;
                } else {// deben ser todos distintos
                    if (ec1 == ec2 || ec1 == ec3 || ec1 == eR || ec1 == eCo ||
                            ec2 == ec3 || ec2 == eR || ec3 == eCo ||
                            ec3 == eR || ec3 == eCo ||
                            eR == eCo) {
                        resultado = false;
                    } else {
                        try {
                            buscaEquipoEnInst.setLong(1, idInsEquipo);
                            ResultSet rs = buscaEquipoEnInst.executeQuery();
                            if (!rs.next()) {
                                resultado = false;
                            }
                        } catch (SQLException ex) {
                        }
                    }
                }
            }

        }return resultado;
    }

    @Override
    public boolean save (Equipo dato){
        boolean resultado = false;
        long idInst= dato.getIdInstitucionEquipo();
        String econ1= dato.getEmailConcursante1();
        try{
            // Buscamos al concursante1 con su llave primaria
            buscaEmailEnPersona.setString(1,econ1);
            ResultSet rs=buscaEmailEnPersona.executeQuery();
            // Si no esta regresamos un falso
            if(!rs.next()){
                resultado = false;
            }
            // Si esta...
            else{
                // Verificamos que sea un estudiante..
                // Si no lo es, regresamos falso. (Debe serlo)
                if(!rs.getString(13).equals("Estudiante")){
                    resultado=false;
                }
                else{
                    //si lo es...
                    //debe pertenecer a la misma institucion del equipo..
                    if(rs.getLong(12)!=idInst){
                        resultado=false;
                    }
                    else{
                        // Buscamos al segundo estudiante y comprobamos las mismas condiciones
                        String econ2=dato.getEmailConcursante2();
                        buscaEmailEnPersona.setString(1,econ2);
                        rs=buscaEmailEnPersona.executeQuery();
                        if(!rs.next()){
                            resultado=false;
                        }
                        else{
                            if(!rs.getString(13).equals("Estudiante")){
                                resultado=false;
                            }
                            else{
                                if(rs.getLong(12)!=idInst){
                                    resultado=false;
                                }
                                else{
                                    // concursante 3
                                    String econ3= dato.getEmailConcursante3();
                                    buscaEmailEnPersona.setString(1,econ3);
                                    rs=buscaEmailEnPersona.executeQuery();
                                    if(!rs.next()){
                                        resultado=false;
                                    }else{
                                        if(!rs.getString(13).equals("Estudiante")){
                                            resultado=false;
                                        }else{
                                            if(rs.getLong(12)!=idInst){
                                                resultado=false;
                                            }
                                            else{
                                                // coach
                                                String emailC= dato.getEmailCoach();
                                                buscaEmailEnPersona.setString(1,emailC);
                                                rs=buscaEmailEnPersona.executeQuery();
                                                if(!rs.next()){
                                                    resultado=false;
                                                }else{
                                                    // Debe ser un profesor
                                                    if(!rs.getString(13).equals("Profesor")){
                                                        resultado=false;
                                                    }
                                                    else{
                                                        if(rs.getLong(12)!=idInst){
                                                            resultado=false;
                                                        }
                                                        else{
                                                            // reserva
                                                            String eReserva=dato.getEmailConcursanteReserva();
                                                            if(eReserva !=null && eReserva.length()!=0){
                                                                buscaEmailEnPersona.setString(1,eReserva);
                                                                rs=buscaEmailEnPersona.executeQuery();
                                                                if(!rs.next()){
                                                                    resultado=false;
                                                                }else{
                                                                    // Debe ser estudiante
                                                                    if(!rs.getString(13).equals("Estudiante")){
                                                                        resultado=false;
                                                                    }else{
                                                                        if(rs.getLong(12)!=idInst){
                                                                            resultado=false;
                                                                        }
                                                                        else{
                                                                            // llamamos a la funcion para hacer el resto de validaciones
                                                                            if (valida(dato))
                                                                                try {
                                                                                    AgregaEquipo.setString(1, dato.getNombreEquipo());
                                                                                    AgregaEquipo.setString(2, dato.getEmailCoach());
                                                                                    AgregaEquipo.setString(3, dato.getEmailConcursante1());
                                                                                    AgregaEquipo.setString(4, dato.getEmailConcursante2());
                                                                                    AgregaEquipo.setString(5, dato.getEmailConcursante3());
                                                                                    AgregaEquipo.setString(6, dato.getEmailConcursanteReserva());
                                                                                    AgregaEquipo.setLong(7, dato.getIdInstitucionEquipo());
                                                                                    AgregaEquipo.setLong(8, dato.getIdEquipo());
                                                                                    int numafectados = AgregaEquipo.executeUpdate();
                                                                                    if (numafectados == 1) {
                                                                                        resultado = true;
                                                                                    }
                                                                                } catch (SQLException ex) {
                                                                                }

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            else{ // En el caso de que el reserva no nulo...
                                                                if (valida(dato))
                                                                    try {
                                                                        AgregaEquipo.setString(1, dato.getNombreEquipo());
                                                                        AgregaEquipo.setString(2, dato.getEmailCoach());
                                                                        AgregaEquipo.setString(3, dato.getEmailConcursante1());
                                                                        AgregaEquipo.setString(4, dato.getEmailConcursante2());
                                                                        AgregaEquipo.setString(5, dato.getEmailConcursante3());
                                                                        AgregaEquipo.setString(6, dato.getEmailConcursanteReserva());
                                                                        AgregaEquipo.setLong(7, dato.getIdInstitucionEquipo());
                                                                        AgregaEquipo.setLong(8, dato.getIdEquipo());
                                                                        int numafectados = AgregaEquipo.executeUpdate();
                                                                        if (numafectados == 1) {
                                                                            resultado = true;
                                                                        }
                                                                    } catch (SQLException ex) {
                                                                    }
                                                            }
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }catch(SQLException ex){

        }

        return resultado;
    }

    @Override
    public boolean update(Equipo dato) {
        boolean resultado = false;
        long idInst= dato.getIdInstitucionEquipo();
        String econ1= dato.getEmailConcursante1();
        try{
            // Buscamos al concursante1 con su llave primaria
            buscaEmailEnPersona.setString(1,econ1);
            ResultSet rs=buscaEmailEnPersona.executeQuery();
            // Si no esta regresamos un falso
            if(!rs.next()){
                resultado = false;
            }
            // Si esta...
            else{
                // Verificamos que sea un estudiante..
                // Si no lo es, regresamos falso. (Debe serlo)
                if(!rs.getString(13).equals("Estudiante")){
                    resultado=false;
                }
                else{
                    //si lo es...
                    //debe pertenecer a la misma institucion del equipo..
                    if(rs.getLong(12)!=idInst){
                        resultado=false;
                    }
                    else{
                        // Buscamos al segundo estudiante y comprobamos las mismas condiciones
                        String econ2=dato.getEmailConcursante2();
                        buscaEmailEnPersona.setString(1,econ2);
                        rs=buscaEmailEnPersona.executeQuery();
                        if(!rs.next()){
                            resultado=false;
                        }
                        else{
                            if(!rs.getString(13).equals("Estudiante")){
                                resultado=false;
                            }
                            else{
                                if(rs.getLong(12)!=idInst){
                                    resultado=false;
                                }
                                else{
                                    // concursante 3
                                    String econ3= dato.getEmailConcursante3();
                                    buscaEmailEnPersona.setString(1,econ3);
                                    rs=buscaEmailEnPersona.executeQuery();
                                    if(!rs.next()){
                                        resultado=false;
                                    }else{
                                        if(!rs.getString(13).equals("Estudiante")){
                                            resultado=false;
                                        }else{
                                            if(rs.getLong(12)!=idInst){
                                                resultado=false;
                                            }
                                            else{
                                                // coach
                                                String emailC= dato.getEmailCoach();
                                                buscaEmailEnPersona.setString(1,emailC);
                                                rs=buscaEmailEnPersona.executeQuery();
                                                if(!rs.next()){
                                                    resultado=false;
                                                }else{
                                                    // Debe ser un profesor
                                                    if(!rs.getString(13).equals("Profesor")){
                                                        resultado=false;
                                                    }
                                                    else{
                                                        if(rs.getLong(12)!=idInst){
                                                            resultado=false;
                                                        }
                                                        else{
                                                            // reserva
                                                            String eReserva=dato.getEmailConcursanteReserva();
                                                            if(eReserva !=null && eReserva.length()!=0){
                                                                buscaEmailEnPersona.setString(1,eReserva);
                                                                rs=buscaEmailEnPersona.executeQuery();
                                                                if(!rs.next()){
                                                                    resultado=false;
                                                                }else{
                                                                    // Debe ser estudiante
                                                                    if(!rs.getString(13).equals("Estudiante")){
                                                                        resultado=false;
                                                                    }else{
                                                                        if(rs.getLong(12)!=idInst){
                                                                            resultado=false;
                                                                        }
                                                                        else{
                                                                            // llamamos a la funcion para hacer el resto de validaciones
                                                                            if (valida(dato))
                                                                                try {
                                                                                    ActualizaEquipo.setString(1, dato.getNombreEquipo());
                                                                                    ActualizaEquipo.setString(2, dato.getEmailCoach());
                                                                                    ActualizaEquipo.setString(3, dato.getEmailConcursante1());
                                                                                    ActualizaEquipo.setString(4, dato.getEmailConcursante2());
                                                                                    ActualizaEquipo.setString(5, dato.getEmailConcursante3());
                                                                                    ActualizaEquipo.setString(6, dato.getEmailConcursanteReserva());
                                                                                    ActualizaEquipo.setLong(7, dato.getIdInstitucionEquipo());
                                                                                    ActualizaEquipo.setLong(8, dato.getIdEquipo());
                                                                                    int numafectados = ActualizaEquipo.executeUpdate();
                                                                                    if (numafectados == 1) {
                                                                                        resultado = true;
                                                                                    }
                                                                                } catch (SQLException ex) {
                                                                                }

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            else{ // En el caso de que el reserva  sea nulo...
                                                                if (valida(dato))
                                                                    try {
                                                                        ActualizaEquipo.setString(1, dato.getNombreEquipo());
                                                                        ActualizaEquipo.setString(2, dato.getEmailCoach());
                                                                        ActualizaEquipo.setString(3, dato.getEmailConcursante1());
                                                                        ActualizaEquipo.setString(4, dato.getEmailConcursante2());
                                                                        ActualizaEquipo.setString(5, dato.getEmailConcursante3());
                                                                        ActualizaEquipo.setString(6, dato.getEmailConcursanteReserva());
                                                                        ActualizaEquipo.setLong(7, dato.getIdInstitucionEquipo());
                                                                        ActualizaEquipo.setLong(8, dato.getIdEquipo());
                                                                        int numafectados = ActualizaEquipo.executeUpdate();
                                                                        if (numafectados == 1) {
                                                                            resultado = true;
                                                                        }
                                                                    } catch (SQLException ex) {
                                                                    }
                                                            }
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }catch(SQLException ex){

        }

        return resultado;
    }

    @Override
    public boolean delete(Object id) {
        boolean resultado = false;
        if (id instanceof  Long){
            long idEquipo = (Long) id;
            try{
                buscaEquipoEnSede.setLong(1,idEquipo);
                ResultSet rs = buscaEquipoEnSede.executeQuery();
                if (!rs.next()){
                    BorraEquipo.setLong(1, idEquipo);
                    int numafectados = BorraEquipo.executeUpdate();
                    if (numafectados ==1){
                        resultado = true;
                    }
                }
            }
            catch(SQLException ex){
            }
        }
        return resultado;
    }
}
