package poo2.progs.basedatos;

import poo2.progs.entidades.Institucion;
import poo2.progs.entidades.Persona;
import poo2.progs.interfaces.Dao;
import poo2.progs.interfaces.DaoException;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DaoPersona implements Dao<Persona>{
    private Connection conn;
    private PreparedStatement stmtObtenUnaPersona;
    private PreparedStatement stmtObtenTodas;
    private PreparedStatement stmtAgregaPersona;
    private PreparedStatement stmtEliminaPersona;
    private PreparedStatement stmtmtActualizaPersona;
    private PreparedStatement stmtBuscaPersonaEnInst;
    private PreparedStatement stmtBuscaEmailCoach;
    private PreparedStatement stmtBuscaEmailC1;
    private PreparedStatement stmtBuscaEmailC2;
    private PreparedStatement stmtBuscaEmailC3;
    private PreparedStatement stmtBuscaEmailReserva;
    private PreparedStatement stmtBuscaEmailDirectorSede;
    private PreparedStatement stmtBuscaEmailEstudiante;


    public DaoPersona(Connection con) throws DaoException {
        if (con==null) {
            throw new DaoException("Conector recibido es nulo");
        }
        conn=con;
        inicializaStmts();
    }
    public DaoPersona(String ubicacionServidor, String nomBD,
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

    public DaoPersona() throws DaoException {
        this("localhost","controlconcursos",
                "IngSW","UAZsw2021");
        inicializaStmts();
    }




    private void inicializaStmts() throws DaoException  {
       try{
           stmtObtenUnaPersona = conn.prepareStatement("SELECT * FROM persona WHERE email_persona=?");
           stmtObtenTodas = conn.prepareStatement("SELECT * FROM persona");
           stmtEliminaPersona = conn.prepareStatement("DELETE FROM persona WHERE email_persona=?");
           stmtAgregaPersona = conn.prepareStatement("INSERT INTO  persona SET "+
                   "nombre_persona =?, apellidos_persona=?, sexo_persona=?," +
                   "calle_num_persona=?, colonia_persona=?, id_municipio_persona=?," +
                   "id_entidad_persona=?, codpostal_persona=?, telefono_persona=?, " +
                   "fecha_nac_persona=?, id_institucion_persona=?, tipo_persona=?, email_persona=?");
           stmtmtActualizaPersona = conn.prepareStatement("UPDATE  persona SET "+
                   "nombre_persona =?, apellidos_persona=?, sexo_persona=?," +
                   "calle_num_persona=?, colonia_persona=?, id_municipio_persona=?," +
                   "id_entidad_persona=?, codpostal_persona=?, telefono_persona=?, " +
                   "fecha_nac_persona=?, id_institucion_persona=?, tipo_persona=? WHERE email_persona=? ");
           stmtBuscaPersonaEnInst = conn.prepareStatement("SELECT * FROM institucion WHERE id_institucion =?");
           stmtBuscaEmailCoach = conn.prepareStatement("SELECT * FROM equipo WHERE email_coach=?");
           stmtBuscaEmailC1 = conn.prepareStatement("SELECT * FROM equipo WHERE email_concursante1=?");
           stmtBuscaEmailC2 = conn.prepareStatement("SELECT * FROM equipo WHERE email_concursante2=?");
           stmtBuscaEmailC3 = conn.prepareStatement("SELECT * FROM equipo WHERE email_concursante3=?");
           stmtBuscaEmailReserva = conn.prepareStatement("SELECT * FROM equipo WHERE email_concursante_reserva=?");
           stmtBuscaEmailEstudiante = conn.prepareStatement("SELECT * FROM datos_estudiante WHERE email_estudiante=?");
           stmtBuscaEmailDirectorSede = conn.prepareStatement("SELECT * FROM sede WHERE email_director_sede=?");
       }
        catch (SQLException ex){
           throw new DaoException(ex.getMessage());
        }

    }

    @Override
    public Optional<Persona> get(Object id) {
        Optional<Persona> resultado = Optional.empty();
        if (id instanceof String){
            String emailPersona = (String) id;
            try {
                stmtObtenUnaPersona.setString(1, emailPersona);
                ResultSet rs = stmtObtenUnaPersona.executeQuery();
                if (rs.next()){
                    String nombre = rs.getString("nombre_persona");
                    String apellidos = rs.getString("apellidos_persona");
                    String sexo = rs.getString("sexo_persona");
                    String calle = rs.getString("calle_num_persona");
                    String colonia = rs.getString("colonia_persona");
                    long idMun = rs.getLong("id_municipio_persona");
                    long idEntidad = rs.getLong("id_entidad_persona");
                    String codpostal = rs.getString("codpostal_persona");
                    String telefono = rs.getString("telefono_persona");
                    Date fecha = rs.getDate("fecha_nac_persona");
                    long idInst = rs.getLong("id_institucion_persona");
                    String tipo = rs.getString("tipo_persona");
                    Persona persona = new Persona(emailPersona, nombre, apellidos, sexo, calle, idMun, idEntidad, telefono, fecha, idInst, tipo);
                    persona.setCodpostalPersona(codpostal);
                    persona.setColoniaPersona(colonia);
                    resultado = Optional.of(persona);
                }
            }
            catch (SQLException ex){
                throw new DaoException(ex.getMessage());
            }

        }
        return resultado;
    }

    @Override
    public List<Persona> getAll() {
        List<Persona> resultado = new ArrayList<>();
        try{
            ResultSet rs = stmtObtenTodas.executeQuery();
            while(rs.next()){
                String emailPersona = rs.getString("email_persona");
                String nombre = rs.getString("nombre_persona");
                String apellidos = rs.getString("apellidos_persona");
                String sexo = rs.getString("sexo_persona");
                String calle = rs.getString("calle_num_persona");
                String colonia = rs.getString("colonia_persona");
                long idMun = rs.getLong("id_municipio_persona");
                long idEntidad = rs.getLong("id_entidad_persona");
                String codpostal = rs.getString("codpostal_persona");
                String telefono = rs.getString("telefono_persona");
                Date fecha = rs.getDate("fecha_nac_persona");
                long idInst = rs.getLong("id_institucion_persona");
                String tipo = rs.getString("tipo_persona");
                Persona persona = new Persona(emailPersona, nombre, apellidos, sexo, calle, idMun, idEntidad, telefono, fecha, idInst, tipo);
                persona.setCodpostalPersona(codpostal);
                persona.setColoniaPersona(colonia);
                resultado.add(persona);
            }
        }
        catch(SQLException ex){
            throw  new DaoException((ex.getMessage()));
        }
        return resultado;
    }
    public boolean validaDatos(Persona info){
        boolean resultado = true;
        if (info==null){
            resultado= false;
        }
        else{
            String email = info.getEmailPersona();
            String nom = info.getNombrePersona();
            String ap = info.getApellidosPersona();
            String sex = info.getSexoPersona();
            String calle = info.getCalleNumPersona();
            String tel = info.getTelefonoPersona();
            Date fecha = info.getFechaNacPersona();
            String tipo = info.getTipoPersona();
            if(nom==null || nom==""|| nom.length()==0 || nom.length() > 30
            || ap=="" || ap ==null || ap.length() ==0 || ap.length()>40
            || sex=="" ||sex ==null || (sex!= "F" && sex!="M")
            || calle == ""||calle==null || calle.length()==0 || calle.length()>50
            || tel == "" ||tel ==null || tel.length()==0 || tel.length()>10
            || fecha ==null
            || tipo =="" ||tipo == null || (tipo!= "Estudiante" && tipo!= "Profesor")
            || email==null || email=="" || email.length()==0 || email.length()>40){
                resultado= false;
            }
            else{
                String col = info.getColoniaPersona();
                String codpos = info.getCodpostalPersona();
                if (col!= null && col.length()>50){
                    resultado = false;
                }
                else{
                    if(codpos!= null  && !(codpos.length()==0 || codpos.length()==5)){
                        resultado = false;
                    }
                    else{
                        long idmun = info.getIdMunicipioPersona();
                        long iden  = info.getIdEntidadPersona();
                        long idinst = info.getIdInstitucionPersona();
                        if(idmun<1001 || idmun>32058){
                            resultado=false;
                        }
                        else {
                            if (iden <1 || iden>32){
                                resultado = false;
                            }
                            else{
                                try{
                                    stmtBuscaPersonaEnInst.setLong(1, idinst);
                                    ResultSet rs = stmtBuscaPersonaEnInst.executeQuery();
                                    if (!rs.next()){
                                        resultado = false;
                                    }
                                }
                                catch (SQLException ex){

                                }
                            }
                        }
                    }
                }
            }
        }



        return resultado;
    }

    @Override
    public boolean save(Persona dato) {
       boolean resultado = false;
       if (validaDatos(dato) == true){
           try{
               stmtAgregaPersona.setString(1, dato.getNombrePersona());
               stmtAgregaPersona.setString(2, dato.getApellidosPersona());
               stmtAgregaPersona.setString(3,dato.getSexoPersona());
               stmtAgregaPersona.setString(4, dato.getCalleNumPersona());
               stmtAgregaPersona.setString(5, dato.getColoniaPersona());
               stmtAgregaPersona.setLong(6,dato.getIdMunicipioPersona());
               stmtAgregaPersona.setLong(7, dato.getIdEntidadPersona());
               stmtAgregaPersona.setString(8,dato.getCodpostalPersona());
               stmtAgregaPersona.setString(9,dato.getTelefonoPersona());
               stmtAgregaPersona.setDate(10, (java.sql.Date) dato.getFechaNacPersona());
               stmtAgregaPersona.setLong(11, dato.getIdInstitucionPersona());
               stmtAgregaPersona.setString(12, dato.getTipoPersona());
               stmtAgregaPersona.setString(13, dato.getEmailPersona());
               int numafectados = stmtAgregaPersona.executeUpdate();
               if (numafectados==1){
                   resultado = true;
               }
           }
           catch (SQLException ex ){

           }
       }
       return resultado;
    }

    @Override
    public boolean update(Persona dato) {
        boolean resultado = false;
        if (validaDatos(dato) == true){
            try{
                stmtmtActualizaPersona.setString(1, dato.getNombrePersona());
                stmtmtActualizaPersona.setString(2, dato.getApellidosPersona());
                stmtmtActualizaPersona.setString(3,dato.getSexoPersona());
                stmtmtActualizaPersona.setString(4, dato.getCalleNumPersona());
                stmtmtActualizaPersona.setString(5, dato.getColoniaPersona());
                stmtmtActualizaPersona.setLong(6,dato.getIdMunicipioPersona());
                stmtmtActualizaPersona.setLong(7, dato.getIdEntidadPersona());
                stmtmtActualizaPersona.setString(8,dato.getCodpostalPersona());
                stmtmtActualizaPersona.setString(9,dato.getTelefonoPersona());
                stmtmtActualizaPersona.setDate(10, (java.sql.Date) dato.getFechaNacPersona());
                stmtmtActualizaPersona.setLong(11, dato.getIdInstitucionPersona());
                stmtmtActualizaPersona.setString(12, dato.getTipoPersona());
                stmtmtActualizaPersona.setString(13, dato.getEmailPersona());
                int numafectados = stmtmtActualizaPersona.executeUpdate();
                if (numafectados==1){
                    resultado = true;
                }
            }
            catch (SQLException ex ){

            }
        }
        return resultado;
    }

    @Override
    public boolean delete(Object id) {
        boolean resultado = false;
        if (id instanceof String){
            String email = (String) id;
            try{
                stmtBuscaEmailCoach.setString(1, email);
                ResultSet rs = stmtBuscaEmailCoach.executeQuery();
                if(!rs.next()){
                    stmtBuscaEmailC1.setString(1,email);
                    rs = stmtBuscaEmailC1.executeQuery();
                    if(!rs.next()){
                        stmtBuscaEmailC2.setString(1,email);
                        rs = stmtBuscaEmailC2.executeQuery();
                        if(!rs.next()){
                            stmtBuscaEmailC3.setString(1, email);
                            rs = stmtBuscaEmailC3.executeQuery();
                            if (!rs.next()){
                                stmtBuscaEmailEstudiante.setString(1,email);
                                rs = stmtBuscaEmailEstudiante.executeQuery();
                                if (!rs.next()){
                                    stmtBuscaEmailDirectorSede.setString(1,email);
                                    rs = stmtBuscaEmailDirectorSede.executeQuery();
                                    if(!rs.next()){
                                        stmtEliminaPersona.setString(1, email);
                                        int numafectados = stmtEliminaPersona.executeUpdate();
                                        if (numafectados==1){
                                            resultado = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
            catch (SQLException ex){
                ex.printStackTrace();

            }
        }

        return resultado;
    }
}
