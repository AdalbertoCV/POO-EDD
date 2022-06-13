package poo2.progs.basedatos;

import poo2.progs.entidades.Entidad;
import poo2.progs.interfaces.Dao;
import poo2.progs.interfaces.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoEntidad implements Dao<Entidad> {
    private Connection conn;
    private PreparedStatement obtenEntidad;
    private PreparedStatement obtenTodas;

    public DaoEntidad(Connection con) throws DaoException {
        if (con == null) {
            throw new DaoException("Conector recibido es nulo");
        }
        conn = con;
        inicializaStmts();
    }

    public DaoEntidad(String ubicacionServidor, String nomBD,
                      String usuario, String clave)
            throws DaoException {
        String url = String.format("jdbc:mysql://%s/%s",
                ubicacionServidor, nomBD);
        try {
            conn = DriverManager.getConnection(url, usuario, clave);
        } catch (SQLException exsql) {
            throw new DaoException(exsql.getMessage(), exsql.getCause());
        }
        inicializaStmts();
    }

    public DaoEntidad() throws DaoException {
        this("localhost", "controlconcursos",
                "IngSW", "UAZsw2021");
        inicializaStmts();
    }

    public void inicializaStmts() throws DaoException {
        try {
            obtenEntidad = conn.prepareStatement("SELECT * FROM entidad WHERE id_entidad=?");
            obtenTodas = conn.prepareStatement("SELECT * FROM entidad");
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage());
        }
    }

    @Override
    public Optional<Entidad> get(Object id) {
        Optional<Entidad> resultado = Optional.empty();
        if (id instanceof Long) {
            long idEn = (Long) id;
            try {
                obtenEntidad.setLong(1, idEn);
                ResultSet rs = obtenEntidad.executeQuery();
                if (rs.next()) {
                    String nom = rs.getString("nombre_entidad");
                    Entidad entidad = new Entidad(idEn, nom);
                    resultado = Optional.of(entidad);
                }
            } catch (SQLException ex) {
                throw new DaoException(ex.getMessage());
            }
        }
        return resultado;
    }

    @Override
    public List<Entidad> getAll() {
        List<Entidad> resultado = new ArrayList<>();
        try {
            ResultSet rs = obtenTodas.executeQuery();
            while (rs.next()) {
                long idEn = rs.getLong("id_entidad");
                String nom = rs.getString("nombre_entidad");
                Entidad entidad = new Entidad(idEn, nom);
                resultado.add(entidad);
            }
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage());
        }
        return resultado;
    }


    @Override
    public boolean save(Entidad dato) {
        return false;
    }

    @Override
    public boolean update(Entidad dato) {
        return false;
    }

    @Override
    public boolean delete(Object id) {
        return false;
    }
}
