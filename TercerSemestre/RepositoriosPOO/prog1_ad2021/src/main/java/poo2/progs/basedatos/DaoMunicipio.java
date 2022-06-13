package poo2.progs.basedatos;

import poo2.progs.entidades.Municipio;
import poo2.progs.interfaces.Dao;
import poo2.progs.interfaces.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoMunicipio implements Dao<Municipio> {
    private final  Connection conn;
    private PreparedStatement stmtObtenUno;
    private PreparedStatement stmtObtenTodos;

    /**
     * Constructor para inicializar la conexion
     * la cual es recibida desde fuera
     * @param con Objeto conexion a MySQL a usar
     * @throws DaoException En caso de que el objeto de conexion no sea valido
     */
    public DaoMunicipio(Connection con) throws DaoException {
        if (con==null) {
            throw new DaoException("Conector recibido es nulo");
        }
        conn=con;
        inicializaStmts();
    }

    public DaoMunicipio(String ubicacionServidor, String nomBD,
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

    public DaoMunicipio() throws DaoException {
        this("localhost","controlconcursos",
                "IngSW","UAZsw2021");
        inicializaStmts();
    }

    /**
     * Este metodo inicializa los Statements a usar por parte de la implementacion del Dao
     * @throws DaoException Excepcion en caso de no poderse crear los statement
     */
    private void inicializaStmts() throws DaoException {
        try {
            stmtObtenUno = conn.prepareStatement("SELECT * FROM municipio WHERE id_municipio=?");
            stmtObtenTodos = conn.prepareStatement("SELECT * FROM municipio");
        } catch (SQLException ex){
            throw new DaoException(ex.getMessage());
        }


    }

    @Override
    public Optional<Municipio> get(Object id) {
        Optional<Municipio> resultado = Optional.empty();
        if (id instanceof Long){
            long idMun = (Long) id;
            try {
                stmtObtenUno.setLong(1, idMun);
                ResultSet rs = stmtObtenUno.executeQuery();
                if (rs.next()){
                    Municipio mun = new Municipio(idMun,rs.getString("nombre_municipio"));
                    resultado = Optional.of(mun);
                }
            }
            catch (SQLException ex){
                throw new DaoException(ex.getMessage());
            }

        }
        return resultado;
    }

    @Override
    public List<Municipio> getAll() {
        List<Municipio> resultado = new ArrayList<>();
        try{
            ResultSet rs = stmtObtenTodos.executeQuery();
            while(rs.next()) {
                long idMun = rs.getLong("id_municipio");
                String nomMun = rs.getString("nombre_municipio");
                Municipio mun = new Municipio(idMun,nomMun);
                resultado.add(mun);
            }

        }
        catch (SQLException ex){
            throw new DaoException(ex.getMessage());
        }
        return resultado;
    }

    @Override
    public boolean save(Municipio dato) {
        return false;
    }

    @Override
    public boolean update(Municipio dato) {
        return false;
    }

    @Override
    public boolean delete(Object id) {
        return false;
    }
}
