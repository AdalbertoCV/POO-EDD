package poo2.progs.basedatos;

import poo2.progs.entidades.DatosEstudiante;
import poo2.progs.entidades.Sede;
import poo2.progs.interfaces.Dao;
import poo2.progs.interfaces.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoSede implements Dao<Sede> {
    private Connection conn;
    private PreparedStatement buscaSede;
    private PreparedStatement buscaTodos;
    private PreparedStatement AgregaSede;
    private PreparedStatement ActualizaSede;
    private PreparedStatement BorraSede;
    private PreparedStatement buscaEmailPersona;
    private PreparedStatement buscaInst;
    private PreparedStatement buscaSedeConcurso;
    private PreparedStatement buscaSedeConcursok;


    public DaoSede(Connection con) throws DaoException {
        if (con==null) {
            throw new DaoException("Conector recibido es nulo");
        }
        conn=con;
        inicializaStmts();
    }
    public DaoSede(String ubicacionServidor, String nomBD,
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

    public DaoSede() throws DaoException {
        this("localhost","controlconcursos",
                "IngSW","UAZsw2021");
        inicializaStmts();
    }

    public void inicializaStmts() throws DaoException {
        try{
            buscaSede = conn.prepareStatement("SELECT * FROM sede WHERE id_sede=?");
            buscaTodos = conn.prepareStatement("SELECT * FROM sede");
            BorraSede = conn.prepareStatement("DELETE FROM sede WHERE id_sede=?");
            AgregaSede = conn.prepareStatement("INSERT INTO sede SET " +
                    "nombre_sede =?, id_institucion_sede =?,"+
                    "email_director_sede=?, url_sede=?, id_sede=?");
            ActualizaSede = conn.prepareStatement("UPDATE sede SET " +
                    "nombre_sede =?, id_institucion_sede =?,"+
                    "email_director_sede=?, url_sede=? WHERE id_sede=?");
            buscaEmailPersona = conn.prepareStatement("SELECT * FROM persona WHERE email_persona=?");
            buscaInst = conn.prepareStatement("SELECT * FROM institucion WHERE id_institucion=?");
            buscaSedeConcurso = conn.prepareStatement("SELECT * FROM sede_concurso WHERE id_sede=?");
            buscaSedeConcursok = conn.prepareStatement("SELECT * FROM equipos_sede_concurso WHERE id_sede_concurso=?");
        }
        catch(SQLException ex){
            throw new DaoException(ex.getMessage());
        }
    }
    @Override
    public Optional<Sede> get(Object id) {
        Optional<Sede> resultado =  Optional.empty();
        if (id instanceof  Long){
            long idSede = (Long) id;
            try{
                buscaSede.setLong(1, idSede);
                ResultSet rs = buscaSede.executeQuery();
                if (rs.next()){
                    String nom = rs.getString("nombre_sede");
                    long idInst = rs.getLong("id_institucion_sede");
                    String emailD = rs.getString("email_director_sede");
                    String url = rs.getString("url_sede");
                    Sede sede = new Sede(idSede, nom, idInst, emailD);
                    sede.setUrlSede(url);
                    resultado = Optional.of(sede);
                }
            }
            catch(SQLException ex){
                throw new DaoException(ex.getMessage());
            }
        }
        return resultado;
    }

    @Override
    public List<Sede> getAll() {
        List<Sede> resultado =  new ArrayList<>();
            try{
                ResultSet rs = buscaTodos.executeQuery();
                while (rs.next()){
                    long idSede = rs.getLong("id_sede");
                    String nom = rs.getString("nombre_sede");
                    long idInst = rs.getLong("id_institucion_sede");
                    String emailD = rs.getString("email_director_sede");
                    String url = rs.getString("url_sede");
                    Sede sede = new Sede(idSede, nom, idInst, emailD);
                    sede.setUrlSede(url);
                    resultado.add(sede);
                }
            }
            catch(SQLException ex){
                throw new DaoException(ex.getMessage());
            }
        return resultado;
    }

    public boolean valida(Sede info){
        boolean resultado = true;
        if (info == null){
            resultado = false;
        }
        else{
            String email = info.getEmailDirectorSede();
            long idInst = info.getIdInstitucionSede();
            String nom = info.getNombreSede();
            if (email == null || email=="" || email.length()==0|| email.length()>40
            || nom== null || nom=="" || nom.length() ==0 || nom.length()>50){
                resultado = false;
            }
            else{
                String url = info.getUrlSede();
                if(url != null && url.length()>200){
                    resultado = false;
                }
                else{
                    try{
                        buscaEmailPersona.setString(1,email);
                        ResultSet rs = buscaEmailPersona.executeQuery();
                        if (!rs.next()){
                            resultado = false;
                        }
                        else{
                            String tipo = rs.getString("tipo_persona");
                            long idPersona = rs.getLong("id_institucion_persona");
                            if (!tipo.equals("Profesor")){
                                resultado = false;
                            }
                            else{
                                try{
                                    buscaInst.setLong(1, idInst);
                                    ResultSet rs1 = buscaInst.executeQuery();
                                    if(!rs1.next()){
                                        resultado= false;
                                    }
                                    else{
                                        if (!(idInst == idPersona)){
                                            resultado = false;
                                        }
                                    }
                                }
                                catch(SQLException ex){
                                }
                            }
                        }
                    }
                    catch(SQLException ex){
                    }
                }
            }
        }
        return resultado;
    }



    @Override
    public boolean save(Sede dato) {
        boolean resultado = false;
        if (valida(dato) == true){
            try{
                AgregaSede.setString(1, dato.getNombreSede());
                AgregaSede.setLong(2, dato.getIdInstitucionSede());
                AgregaSede.setString(3, dato.getEmailDirectorSede());
                AgregaSede.setString(4, dato.getUrlSede());
                AgregaSede.setLong(5, dato.getIdSede());
                int numafectados = AgregaSede.executeUpdate();
                if (numafectados == 1){
                    resultado = true;
                }
            }
            catch(SQLException ex){
            }
        }
        return resultado;
    }

    @Override
    public boolean update(Sede dato) {
        boolean resultado = false;
        if (valida(dato) == true){
            try{
                ActualizaSede.setString(1, dato.getNombreSede());
                ActualizaSede.setLong(2, dato.getIdInstitucionSede());
                ActualizaSede.setString(3, dato.getEmailDirectorSede());
                ActualizaSede.setString(4, dato.getUrlSede());
                ActualizaSede.setLong(5, dato.getIdSede());
                int numafectados = ActualizaSede.executeUpdate();
                if (numafectados == 1){
                    resultado = true;
                }
            }
            catch(SQLException ex){
            }
        }
        return resultado;
    }

    @Override
    public boolean delete(Object id) {
        boolean resultado = false;
        if (id instanceof Long){
            long idSede = (Long) id;
            try{
                    buscaSedeConcurso.setLong(1, idSede);
                    ResultSet rs1 = buscaSedeConcurso.executeQuery();
                    if (!rs1.next()){
                        BorraSede.setLong(1,idSede);
                        int numafectados = BorraSede.executeUpdate();
                        if (numafectados == 1){
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
