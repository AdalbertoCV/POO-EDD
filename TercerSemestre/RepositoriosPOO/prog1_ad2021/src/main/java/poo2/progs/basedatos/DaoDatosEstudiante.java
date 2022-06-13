package poo2.progs.basedatos;

import poo2.progs.entidades.DatosEstudiante;
import poo2.progs.interfaces.Dao;
import poo2.progs.interfaces.DaoException;
import sun.security.rsa.RSASignature;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class DaoDatosEstudiante implements Dao<DatosEstudiante> {
   private Connection conn;
   private PreparedStatement obtenunEstudiante;
   private PreparedStatement obtenTodos;
   private PreparedStatement AgregaEstudiante;
   private PreparedStatement ActualizaEstudiante;
   private PreparedStatement BorraEstudiante;
   private PreparedStatement BuscaEstEnPer;


    public DaoDatosEstudiante(Connection con) throws DaoException {
        if (con==null) {
            throw new DaoException("Conector recibido es nulo");
        }
        conn=con;
        inicializaStmts();
    }
    public DaoDatosEstudiante(String ubicacionServidor, String nomBD,
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

    public DaoDatosEstudiante() throws DaoException {
        this("localhost","controlconcursos",
                "IngSW","UAZsw2021");
        inicializaStmts();
    }

    public void inicializaStmts() throws DaoException {
        try{
            obtenunEstudiante = conn.prepareStatement("SELECT * FROM datos_estudiante WHERE email_estudiante=?");
            obtenTodos = conn.prepareStatement("SELECT * FROM datos_estudiante");
            BorraEstudiante = conn.prepareStatement("DELETE FROM datos_estudiante WHERE email_estudiante=?");
            AgregaEstudiante = conn.prepareStatement("INSERT INTO datos_estudiante SET "+
                    "carrera_estudiante =?, fecha_inicio_carrera=?, " +
                    "fecha_esperada_terminacion=?, email_estudiante=?");
            ActualizaEstudiante = conn.prepareStatement("UPDATE datos_estudiante SET "+
                    "carrera_estudiante =?, fecha_inicio_carrera=?, " +
                    "fecha_esperada_terminacion=? WHERE email_estudiante=?");
            BuscaEstEnPer = conn.prepareStatement("SELECT * FROM persona WHERE email_persona=?");
        }
        catch(SQLException ex){
            throw  new DaoException(ex.getMessage());
        }
    }


    @Override
    public Optional<DatosEstudiante> get(Object id) {
        Optional<DatosEstudiante> resultado =  Optional.empty();
        if (id instanceof  String){
            String email = (String) id;
            try{
                obtenunEstudiante.setString(1, email);
                ResultSet rs = obtenunEstudiante.executeQuery();
                if (rs.next()){
                    String carrera = rs.getString("carrera_estudiante");
                    Date fIn = rs.getDate("fecha_inicio_carrera");
                    Date fT = rs.getDate("fecha_esperada_terminacion");
                    DatosEstudiante datos = new DatosEstudiante(email, carrera, fIn, fT);
                    resultado = Optional.of(datos);
                }
            }
            catch(SQLException ex){
                throw new DaoException(ex.getMessage());
            }
        }
        return resultado;
    }

    @Override
    public List<DatosEstudiante> getAll() {
        List<DatosEstudiante> resultado = new ArrayList<>();
        try{
            ResultSet rs = obtenTodos.executeQuery();
            while (rs.next()){
                String email = rs.getString("email_estudiante");
                String carrera = rs.getString("carrera_estudiante");
                Date fIn = rs.getDate("fecha_inicio_carrera");
                Date fT = rs.getDate("fecha_esperada_terminacion");
                DatosEstudiante datos = new DatosEstudiante(email, carrera, fIn, fT);
                resultado.add(datos);
            }
        }
        catch(SQLException ex){

        }
        return resultado;
    }

    public boolean validaDatos(DatosEstudiante info){
        boolean resultado = true;
        if (info == null){
            resultado = false;
        }
        else{
            String email = info.getEmailEstudiante();
            Date fechaIn = (java.sql.Date) info.getFechaInicioCarrera();
            Date fechaTer =  (java.sql.Date) info.getFechaEsperadaTerminacion();
            String carrera = info.getCarreraEstudiante();
            Calendar cal = Calendar.getInstance();
            long valor1 = 0;
            long valor2 = 0;
            if (fechaIn != null){
                cal.setTime(fechaIn);
                long anoIn = cal.get(Calendar.YEAR);
                valor1 = anoIn;
            }
            if (fechaTer != null){
                cal.setTime(fechaTer);
                long anoTer = cal.get(Calendar.YEAR);
                valor2 = anoTer;
            }
            if (email == null || email == "" || email.length()==0 || email.length()>40
            || carrera== null || carrera=="" || carrera.length()==0 || carrera.length()>50
            || ((valor2 - valor1)<3 )){
                resultado = false;
            }
        }
        return resultado;
    }

    @Override
    public boolean save(DatosEstudiante dato) {
        boolean resultado = false;
        if (validaDatos(dato) == true){
            try{
                AgregaEstudiante.setString(1, dato.getCarreraEstudiante());
                AgregaEstudiante.setDate(2, (java.sql.Date) dato.getFechaInicioCarrera());
                AgregaEstudiante.setDate(3, (java.sql.Date) dato.getFechaEsperadaTerminacion());
                AgregaEstudiante.setString(4, dato.getEmailEstudiante());
                int numafectados = AgregaEstudiante.executeUpdate();
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
    public boolean update(DatosEstudiante dato) {
        boolean resultado = false;
        if (validaDatos(dato) == true){
            try{
                ActualizaEstudiante.setString(1, dato.getCarreraEstudiante());
                ActualizaEstudiante.setDate(2, (java.sql.Date) dato.getFechaInicioCarrera());
                ActualizaEstudiante.setDate(3, (java.sql.Date) dato.getFechaEsperadaTerminacion());
                ActualizaEstudiante.setString(4, dato.getEmailEstudiante());
                int numafectados = ActualizaEstudiante.executeUpdate();
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
        if (id instanceof  String ){
            String email = (String) id;
            try{
                BuscaEstEnPer.setString(1,email);
                ResultSet rs = BuscaEstEnPer.executeQuery();
                if (rs.next()){
                    BorraEstudiante.setString(1,email);
                    int numafectados = BorraEstudiante.executeUpdate();
                    if (numafectados == 1){
                        resultado = true;
                    }
                }
                else{
                    obtenunEstudiante.setString(1,email);
                    rs = obtenunEstudiante.executeQuery();
                    if (rs.next()){
                        BorraEstudiante.setString(1,email);
                        int numafectados = BorraEstudiante.executeUpdate();
                        if (numafectados == 1){
                            resultado = true;
                        }
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
