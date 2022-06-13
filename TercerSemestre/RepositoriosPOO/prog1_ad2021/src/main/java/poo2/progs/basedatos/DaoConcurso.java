package poo2.progs.basedatos;

import poo2.progs.entidades.Concurso;
import poo2.progs.entidades.DatosEstudiante;
import poo2.progs.interfaces.Dao;
import poo2.progs.interfaces.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class DaoConcurso implements Dao<Concurso> {
    private Connection conn;
    private PreparedStatement buscaConcurso;
    private PreparedStatement buscaTodos;
    private PreparedStatement borraConcurso;
    private PreparedStatement agregaConcurso;
    private PreparedStatement actualizaConcurso;
    private PreparedStatement buscaSedeConcurso;

    public DaoConcurso(Connection con) throws DaoException {
        if (con==null) {
            throw new DaoException("Conector recibido es nulo");
        }
        conn=con;
        inicializaStmts();
    }
    public DaoConcurso(String ubicacionServidor, String nomBD,
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

    public DaoConcurso() throws DaoException {
        this("localhost","controlconcursos",
                "IngSW","UAZsw2021");
        inicializaStmts();
    }

    public void inicializaStmts() throws DaoException {
        try{
            buscaConcurso = conn.prepareStatement("SELECT * FROM concurso WHERE id_concurso=?");
            buscaTodos = conn.prepareStatement( "SELECT * FROM concurso");
            borraConcurso = conn.prepareStatement("DELETE FROM concurso WHERE id_concurso=?");
            agregaConcurso = conn.prepareStatement("INSERT INTO concurso SET "+
                    "nombre_concurso=?, fecha_concurso=?, "+
                    "fecha_inicio_registro=?, fecha_fin_registro=?, id_concurso=?");
            actualizaConcurso = conn.prepareStatement("UPDATE concurso SET "+
                    "nombre_concurso=?, fecha_concurso=?, "+
                    "fecha_inicio_registro=?, fecha_fin_registro=? WHERE id_concurso=?");
            buscaSedeConcurso = conn.prepareStatement("SELECT * FROM sede_concurso WHERE id_concurso=?");
        }
        catch(SQLException ex){
        }
    }
    @Override
    public Optional<Concurso> get(Object id) {
        Optional<Concurso> resultado =  Optional.empty();
        if (id instanceof  Long){
            long idConcurso = (Long) id;
            try{
                buscaConcurso.setLong(1, idConcurso);
                ResultSet rs = buscaConcurso.executeQuery();
                if (rs.next()){
                    String nombre = rs.getString("nombre_concurso");
                    Date fC = rs.getDate("fecha_concurso");
                    Date fIR = rs.getDate("fecha_inicio_registro");
                    Date fFR = rs.getDate("fecha_fin_registro");
                    Concurso c = new Concurso(idConcurso, nombre, fC, fIR, fFR);
                    resultado = Optional.of(c);
                }
            }
            catch(SQLException ex){
                throw new DaoException(ex.getMessage());
            }
        }
        return resultado;
    }

    @Override
    public List<Concurso> getAll() {
        List<Concurso> resultado =  new ArrayList<>();
            try{
                ResultSet rs = buscaTodos.executeQuery();
                while (rs.next()){
                    long idConcurso = rs.getLong("id_concurso");
                    String nombre = rs.getString("nombre_concurso");
                    Date fC = rs.getDate("fecha_concurso");
                    Date fIR = rs.getDate("fecha_inicio_registro");
                    Date fFR = rs.getDate("fecha_fin_registro");
                    Concurso c = new Concurso(idConcurso, nombre, fC, fIR, fFR);
                    resultado.add(c);
                }
            }
            catch(SQLException ex){
                throw new DaoException(ex.getMessage());
            }
        return resultado;
        }

    public boolean valida(Concurso info){
        boolean resultado= true;
        if (info == null) {
            resultado = false;
        }
        else{
            String nombre = info.getNombreConcurso();
            Date fechaC = (java.sql.Date) info.getFechaConcurso();
            Date fechaIR = (java.sql.Date) info.getFechaInicioRegistro();
            Date fechaFR = (java.sql.Date) info.getFechaFinRegistro();
            Calendar cal = Calendar.getInstance();
            long diaC = 0;
            long mesC=0;
            long diaIR =0;
            long mesIR =0;
            long diaFR =0;
            long mesFR=0;
            if (fechaC!=null){
                cal.setTime(fechaC);
                long val = cal.get(Calendar.DAY_OF_MONTH);
                diaC = val;
                val = cal.get(Calendar.MONTH);
                mesC = val;
            }
            if (fechaIR!=null){
                cal.setTime(fechaIR);
                long val = cal.get(Calendar.DAY_OF_MONTH);
                diaIR = val;
                val = cal.get(Calendar.MONTH);
                mesIR = val;
            }
            if (fechaFR!=null){
                cal.setTime(fechaFR);
                long val = cal.get(Calendar.DAY_OF_MONTH);
                diaFR = val;
                val = cal.get(Calendar.MONTH);
                mesFR = val;
            }
            if (nombre==null|| nombre==""|| nombre.length()==0 || nombre.length()>100){
                resultado = false;
            }
                if(((mesC==mesFR)&&(diaC<=diaFR)) ||
                        (mesC<mesFR) || ((mesC==mesIR)&&(diaC<=diaIR)) ||
                        (mesC<mesIR)){
                    resultado = false;
                }
                    if (((mesFR== mesIR)&&(diaFR<=diaIR)) || (mesFR<mesIR)){
                        resultado= false;
                    }


        }
        return resultado;
    }
    @Override
    public boolean save(Concurso dato) {
        boolean resultado = false;
        if (valida(dato)==true){
            try{
                agregaConcurso.setString(1, dato.getNombreConcurso());
                agregaConcurso.setDate(2, (Date) dato.getFechaConcurso());
                agregaConcurso.setDate(3, (Date) dato.getFechaInicioRegistro());
                agregaConcurso.setDate(4, (Date) dato.getFechaFinRegistro());
                agregaConcurso.setLong(5,dato.getIdConcurso());
                int numafectados= agregaConcurso.executeUpdate();
                if (numafectados ==1){
                    resultado = true;
                }
            }
            catch(SQLException ex){
            }
        }
        return resultado;
    }

    @Override
    public boolean update(Concurso dato) {
        boolean resultado = false;
        if (valida(dato)==true){
            try{
                actualizaConcurso.setString(1, dato.getNombreConcurso());
                actualizaConcurso.setDate(2, (Date) dato.getFechaConcurso());
                actualizaConcurso.setDate(3, (Date) dato.getFechaInicioRegistro());
                actualizaConcurso.setDate(4, (Date) dato.getFechaFinRegistro());
                actualizaConcurso.setLong(5,dato.getIdConcurso());
                int numafectados= actualizaConcurso.executeUpdate();
                if (numafectados ==1){
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
        if (id instanceof  Long ){
            long idC = (Long) id;
            try{
                buscaSedeConcurso.setLong(1,idC);
                ResultSet rs = buscaSedeConcurso.executeQuery();
                if (!rs.next()){
                    borraConcurso.setLong(1,idC);
                    int numafectados = borraConcurso.executeUpdate();
                    if (numafectados == 1){
                        resultado = true;
                    }
                }
            }
            catch(SQLException ex){
                throw new DaoException(ex.getMessage());
            }
        }
        return resultado;
    }
}
