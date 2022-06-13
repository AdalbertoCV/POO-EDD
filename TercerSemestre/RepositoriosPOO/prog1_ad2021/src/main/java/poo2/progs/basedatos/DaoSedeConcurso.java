package poo2.progs.basedatos;

import poo2.progs.entidades.DatosEstudiante;
import poo2.progs.entidades.SedeConcurso;
import poo2.progs.interfaces.Dao;
import poo2.progs.interfaces.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoSedeConcurso implements Dao<SedeConcurso> {
   private Connection conn;
   private PreparedStatement obtenSedeC;
   private PreparedStatement obtenTodas;
   private PreparedStatement borraSedeC;
   private PreparedStatement agregaSedeC;
   private PreparedStatement actualizaSedeC;
   private PreparedStatement buscaSede;
   private PreparedStatement buscaConcurso;
   private PreparedStatement buscaEquipoSc;

    public DaoSedeConcurso(Connection con) throws DaoException {
        if (con==null) {
            throw new DaoException("Conector recibido es nulo");
        }
        conn=con;
        inicializaStmts();
    }
    public DaoSedeConcurso(String ubicacionServidor, String nomBD,
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

    public DaoSedeConcurso() throws DaoException {
        this("localhost","controlconcursos",
                "IngSW","UAZsw2021");
        inicializaStmts();
    }

   public void inicializaStmts() throws DaoException {
        try{
            obtenSedeC = conn.prepareStatement("SELECT * FROM sede_concurso WHERE id_sede_concurso=?");
            obtenTodas = conn.prepareStatement( "SELECT * FROM sede_concurso");
            borraSedeC = conn.prepareStatement("DELETE FROM sede_concurso WHERE id_sede_concurso=?");
            agregaSedeC = conn.prepareStatement("INSERT INTO sede_concurso SET "+
                    "id_sede=?, id_concurso=?, id_sede_concurso=?");
            actualizaSedeC = conn.prepareStatement("UPDATE sede_concurso SET "+
                    "id_sede=?, id_concurso=? WHERE id_sede_concurso=?");
            buscaConcurso = conn.prepareStatement("SELECT * FROM concurso WHERE id_concurso=?");
            buscaSede = conn.prepareStatement("SELECT * FROM sede WHERE id_sede=?");
            buscaEquipoSc = conn.prepareStatement("SELECT * FROM equipos_sede_concurso WHERE id_sede_concurso=?");
        }
        catch(SQLException ex){
            throw new DaoException(ex.getMessage());
        }
    }
    @Override
    public Optional<SedeConcurso> get(Object id) {
        Optional<SedeConcurso> resultado =  Optional.empty();
        if (id instanceof  Long){
            long idSedeC = (long) id;
            try{
                obtenSedeC.setLong(1, idSedeC);
                ResultSet rs = obtenSedeC.executeQuery();
                if (rs.next()){
                    long idSede = rs.getLong("id_sede");
                    long idC = rs.getLong("id_concurso");
                    SedeConcurso sc = new SedeConcurso(idSedeC,idSede,idC);
                    resultado = Optional.of(sc);
                }
            }
            catch(SQLException ex){
                throw new DaoException(ex.getMessage());
            }
        }
        return resultado;
    }

    @Override
    public List<SedeConcurso> getAll() {
        List<SedeConcurso> resultado =  new ArrayList<>();
            try{
                ResultSet rs = obtenTodas.executeQuery();
                while (rs.next()){
                    long idSedeC = rs.getLong("id_sede_concurso");
                    long idSede = rs.getLong("id_sede");
                    long idC = rs.getLong("id_concurso");
                    SedeConcurso sc = new SedeConcurso(idSedeC,idSede,idC);
                    resultado.add(sc);
                }
            }
            catch(SQLException ex){
                throw new DaoException(ex.getMessage());
            }
        return resultado;
    }

    public boolean valida(SedeConcurso info){
        boolean resultado = true;
        if (info == null){
            resultado = false;
        }
        else{
            long idC = info.getIdConcurso();
            long idSC = info.getIdSedeConcurso();
            long idS = info.getIdSede();
            try{
                buscaSede.setLong(1,idS);
                ResultSet rs = buscaSede.executeQuery();
                if (!rs.next()){
                    resultado = false;
                }
                else{
                    buscaConcurso.setLong(1, idC);
                    rs = buscaConcurso.executeQuery();
                    if (!rs.next()){
                        resultado = false;
                    }
                }
            }
            catch(SQLException ex ){
            }
        }

        return resultado;
    }

    @Override
    public boolean save(SedeConcurso dato) {
        boolean resultado = false;
        if (valida(dato)== true){
            try{
                agregaSedeC.setLong(1, dato.getIdSede());
                agregaSedeC.setLong(2, dato.getIdConcurso());
                agregaSedeC.setLong(3, dato.getIdSedeConcurso());
                int numafectados = agregaSedeC.executeUpdate();
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
    public boolean update(SedeConcurso dato) {
        boolean resultado = false;
        if (valida(dato)== true){
            try{
                actualizaSedeC.setLong(1, dato.getIdSede());
                actualizaSedeC.setLong(2, dato.getIdConcurso());
                actualizaSedeC.setLong(3, dato.getIdSedeConcurso());
                int numafectados = actualizaSedeC.executeUpdate();
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
        if (id instanceof Long){
            long idSedeC = (Long) id;
            try{
                buscaEquipoSc.setLong(1,idSedeC);
                ResultSet rs = buscaEquipoSc.executeQuery();
                if(!rs.next()){
                    borraSedeC.setLong(1, idSedeC);
                    int numafectados = borraSedeC.executeUpdate();
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
