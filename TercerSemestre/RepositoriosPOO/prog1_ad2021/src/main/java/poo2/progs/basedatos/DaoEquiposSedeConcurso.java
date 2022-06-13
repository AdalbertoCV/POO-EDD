package poo2.progs.basedatos;

import poo2.progs.entidades.EquiposSedeConcurso;
import poo2.progs.entidades.SedeConcurso;
import poo2.progs.interfaces.Dao;
import poo2.progs.interfaces.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoEquiposSedeConcurso implements Dao<EquiposSedeConcurso> {
    private Connection conn;
    private PreparedStatement buscaESC;
    private PreparedStatement buscaTodos;
    private PreparedStatement borraESC;
    private PreparedStatement agregaESC;
    private PreparedStatement actializaESC;
    private PreparedStatement buscaEnEquipo;
    private PreparedStatement buscaEnSedeConcurso;

    public DaoEquiposSedeConcurso(Connection con) throws DaoException {
        if (con==null) {
            throw new DaoException("Conector recibido es nulo");
        }
        conn=con;
        inicializaStmts();
    }
    public DaoEquiposSedeConcurso(String ubicacionServidor, String nomBD,
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

    public DaoEquiposSedeConcurso() throws DaoException {
        this("localhost","controlconcursos",
                "IngSW","UAZsw2021");
        inicializaStmts();
    }

    public void inicializaStmts() throws DaoException {
        try{
            buscaESC = conn.prepareStatement("SELECT * FROM equipos_sede_concurso WHERE id_equipo_sede_concurso=?");
            buscaTodos = conn.prepareStatement("SELECT * FROM equipos_sede_concurso");
            borraESC = conn.prepareStatement("DELETE FROM equipos_sede_concurso WHERE id_equipo_sede_concurso=?");
            agregaESC = conn.prepareStatement("INSERT INTO equipos_sede_concurso SET "+
                    "id_equipo=?, id_sede_concurso=?, id_equipo_sede_concurso=?");
            actializaESC = conn.prepareStatement("UPDATE equipos_sede_concurso SET "+
                    "id_equipo=?, id_sede_concurso=? WHERE id_equipo_sede_concurso=?");
            buscaEnEquipo = conn.prepareStatement("SELECT * FROM equipo WHERE id_equipo=?");
            buscaEnSedeConcurso = conn.prepareStatement("SELECT * FROM sede_concurso WHERE id_sede_concurso=?");
        }
        catch(SQLException ex){
            throw new DaoException(ex.getMessage());
        }
    }
    @Override
    public Optional<EquiposSedeConcurso> get(Object id) {
        Optional<EquiposSedeConcurso> resultado =  Optional.empty();
        if (id instanceof  Long){
            long idESC = (long) id;
            try{
                buscaESC.setLong(1, idESC);
                ResultSet rs = buscaESC.executeQuery();
                if (rs.next()){
                    long idEquipo = rs.getLong("id_equipo");
                    long idSC = rs.getLong("id_sede_concurso");
                    EquiposSedeConcurso ESC = new EquiposSedeConcurso(idESC,idEquipo,idSC);
                    resultado = Optional.of(ESC);
                }
            }
            catch(SQLException ex){
                throw new DaoException(ex.getMessage());
            }
        }
        return resultado;
    }

    @Override
    public List<EquiposSedeConcurso> getAll() {
        List<EquiposSedeConcurso> resultado =  new ArrayList<>();
            try{
                ResultSet rs = buscaTodos.executeQuery();
                while (rs.next()){
                    long idESC = rs.getLong("id_equipo_sede_concurso");
                    long idEquipo = rs.getLong("id_equipo");
                    long idSC = rs.getLong("id_sede_concurso");
                    EquiposSedeConcurso ESC = new EquiposSedeConcurso(idESC,idEquipo,idSC);
                    resultado.add(ESC);
                }
            }
            catch(SQLException ex){
                throw new DaoException(ex.getMessage());
            }
        return resultado;
    }

    public boolean valida(EquiposSedeConcurso info){
        boolean resultado = true;
        if(info==null){
            resultado = false;
        }
        else{
            long idESC = info.getIdEquipoSedeConcurso();
            long idSede = info.getIdSedeConcurso();
            long idEquipo = info.getIdEquipo();
            try{
                buscaEnEquipo.setLong(1,idEquipo);
                ResultSet rs = buscaEnEquipo.executeQuery();
                if (!rs.next()){
                    resultado= false;
                }
                else{
                    buscaEnSedeConcurso.setLong(1, idSede);
                    rs = buscaEnSedeConcurso.executeQuery();
                    if (!rs.next()){
                        resultado = false;
                    }
                }
            }
            catch(SQLException ex){
            }
        }
        return resultado;
    }



    @Override
    public boolean save(EquiposSedeConcurso dato) {
        boolean resultado = false;
        if (valida(dato)== true){
            try{
                agregaESC.setLong(1, dato.getIdEquipo());
                agregaESC.setLong(2, dato.getIdSedeConcurso());
                agregaESC.setLong(3, dato.getIdEquipoSedeConcurso());
                int numafectados = agregaESC.executeUpdate();
                if (numafectados ==1 ){
                    resultado=true;
                }
            }
            catch(SQLException ex){
            }
        }
        return resultado;
    }

    @Override
    public boolean update(EquiposSedeConcurso dato) {
        boolean resultado = false;
        if (valida(dato)== true){
            try{
                actializaESC.setLong(1, dato.getIdEquipo());
                actializaESC.setLong(2, dato.getIdSedeConcurso());
                actializaESC.setLong(3, dato.getIdEquipoSedeConcurso());
                int numafectados = actializaESC.executeUpdate();
                if (numafectados ==1 ){
                    resultado=true;
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
        if (id instanceof Long) {
            long idESC = (Long) id;
            try{
                borraESC.setLong(1,idESC);
                int numafectados = borraESC.executeUpdate();
                if (numafectados == 1){
                    resultado = true;
                }
            }
            catch(SQLException ex){
            }
        }
        return resultado;
    }
}
