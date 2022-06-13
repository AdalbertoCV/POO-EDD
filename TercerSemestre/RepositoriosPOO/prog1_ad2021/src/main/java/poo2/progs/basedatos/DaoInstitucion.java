package poo2.progs.basedatos;

import poo2.progs.entidades.Institucion;
import poo2.progs.entidades.Municipio;
import poo2.progs.interfaces.Dao;
import poo2.progs.interfaces.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoInstitucion  implements Dao<Institucion> {
    private final  Connection conn;
    private PreparedStatement stmtObtenUna;
    private PreparedStatement stmtObtenTodas;
    private PreparedStatement stmtAgrega;
    private PreparedStatement stmtActualiza;
    private PreparedStatement stmtElimina;
    private PreparedStatement stmtBuscaMun;
    private PreparedStatement stmtBuscaInstEnPersona;
    private PreparedStatement stmtBuscaInstEnSede;
    private PreparedStatement stmtBuscaInstEnEquipo;
    public DaoInstitucion(Connection con) throws DaoException {
        if (con==null) {
            throw new DaoException("Conector recibido es nulo");
        }
        conn=con;
        inicializaStmts();
    }

    public DaoInstitucion(String ubicacionServidor, String nomBD,
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

    public DaoInstitucion() throws DaoException {
        this("localhost","controlconcursos",
                "IngSW","UAZsw2021");
        inicializaStmts();
    }

    /**
     * Este metodo inicializa los Statements a usar por parte de la implementacion del Dao
     * @throws DaoException Excepcion en caso de no poderse crear los statement
     */
    private void inicializaStmts() throws DaoException  {
        try{
            stmtObtenUna = conn.prepareStatement("SELECT * FROM institucion WHERE id_institucion=?");
            stmtObtenTodas = conn.prepareStatement("SELECT * FROM institucion");
            stmtElimina = conn.prepareStatement("DELETE FROM institucion WHERE id_institucion=?");
            stmtAgrega = conn.prepareStatement("INSERT INTO institucion SET "+
                    "nombre_institucion=?, nombre_corto_institucion=?, url_institucion=?, " +
                    "calle_num_institucion=?, colonia_institucion=?, " +
                    "id_municipio_institucion=?, id_entidad_institucion=?,"+
                    "codpostal_institucion=?, telefono_institucion=?, id_institucion=?");
            stmtActualiza = conn.prepareStatement("UPDATE institucion SET "+
                    "nombre_institucion=?, nombre_corto_institucion=?, url_institucion=?," +
                    "calle_num_institucion=?, colonia_institucion=?," +
                    "id_municipio_institucion=?, id_entidad_institucion=?,"+
                    "codpostal_institucion=?, telefono_institucion=? WHERE id_institucion=?");
            stmtBuscaMun = conn.prepareStatement("SELECT * FROM municipio WHERE id_municipio=?");
            stmtBuscaInstEnPersona = conn.prepareStatement("SELECT * FROM persona WHERE id_institucion_persona=?");
            stmtBuscaInstEnEquipo = conn.prepareStatement("SELECT * FROM equipo WHERE id_institucion_equipo=?");
            stmtBuscaInstEnSede = conn.prepareStatement("SELECT * FROM sede WHERE id_institucion_sede=?");
        }
        catch(SQLException ex) {

        }

    }

    @Override
    public Optional<Institucion> get(Object id) {
        Optional<Institucion> resultado = Optional.empty();
        if (id instanceof Long){
            long idInst = (Long) id;
            try {
                stmtObtenUna.setLong(1, idInst);
                ResultSet rs = stmtObtenUna.executeQuery();
                if (rs.next()){
                    String nom = rs.getString("nombre_institucion");
                    String nomCorto = rs.getString("nombre_corto_institucion");
                    String url = rs.getString("url_institucion");
                    String calle = rs.getString("calle_num_institucion");
                    String colonia = rs.getString("colonia_institucion");
                    long idMun = rs.getLong("id_municipio_institucion");
                    long idEntidad = rs.getLong("id_entidad_institucion");
                    String codpostal = rs.getString("codpostal_institucion");
                    String telefono = rs.getString("telefono_institucion");
                    Institucion inst = new Institucion(idInst, nom, nomCorto, url, calle, idMun, idEntidad);
                    inst.setCodpostalInstitucion(codpostal);
                    inst.setColoniaInstitucion(colonia);
                    inst.setTelefonoInstitucion(telefono);
                    resultado = Optional.of(inst);
                }
            }
            catch (SQLException ex){
                throw new DaoException(ex.getMessage());
            }

        }
        return resultado;

    }

    @Override
    public List<Institucion> getAll() {
            List<Institucion> resultado = new ArrayList<>();
                try {
                    ResultSet rs = stmtObtenTodas.executeQuery();
                    while (rs.next()){
                        long idInst = rs.getLong("id_institucion");
                        String nom = rs.getString("nombre_institucion");
                        String nomCorto = rs.getString("nombre_corto_institucion");
                        String url = rs.getString("url_institucion");
                        String calle = rs.getString("calle_num_institucion");
                        String colonia = rs.getString("colonia_institucion");
                        long idMun = rs.getLong("id_municipio_institucion");
                        long idEntidad = rs.getLong("id_entidad_institucion");
                        String codpostal = rs.getString("codpostal_institucion");
                        String telefono = rs.getString("telefono_institucion");
                        Institucion inst = new Institucion(idInst, nom, nomCorto, url, calle, idMun, idEntidad);
                        inst.setCodpostalInstitucion(codpostal);
                        inst.setColoniaInstitucion(colonia);
                        inst.setTelefonoInstitucion(telefono);
                        resultado.add(inst);
                    }
                }
                catch (SQLException ex){
                    throw new DaoException(ex.getMessage());
                }


            return resultado;
    }
    private boolean validaDatos(Institucion info){
        boolean resultado = true;
        if (info == null) {
            resultado = false;
        }
        else{
            String nom = info.getNombreInstitucion();
            String nomcorto = info.getNombreCortoInstitucion();
            String url = info.getUrlInstitucion();
            String calle = info.getCalleNumInstitucion();
            if (nom == null  || nom.length()==0 || nom.length()>100 ||
                    nomcorto == null || nomcorto.length()==0 || nomcorto.length()>20||
                    url == null  || url.length()==0 || url.length()>200 ||
                    calle == null  || calle.length()==0 || calle.length()>50){
                      resultado = false;
            }
            else{
                String colonia = info.getColoniaInstitucion();
                String telefono = info.getTelefonoInstitucion();
                String codpos = info.getCodpostalInstitucion();
                if (colonia!= null &&  colonia.length()>50){
                    resultado= false;
                }
                else {
                    if (telefono!=null && !(telefono.length()==0 || telefono.length()==10)){
                        resultado= false;
                    }
                    else{
                        if (codpos!= null  && !(codpos.length()==0 || codpos.length()==5)){
                            resultado= false;
                        }
                        else {
                            long idEntidad = info.getIdEntidadInstitucion();
                            long idMun = info.getIdMunicipioInstitucion();
                            if (idEntidad<1 || idEntidad>32){
                                resultado = false;
                            }
                            else{
                                try{
                                    stmtBuscaMun.setLong(1,idMun);
                                    ResultSet rs = stmtBuscaMun.executeQuery();
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
    public boolean save(Institucion dato) {
        boolean resultado = false;
        if (validaDatos(dato) == true) {
            try {
                stmtAgrega.setString(1, dato.getNombreInstitucion());
                stmtAgrega.setString(2, dato.getNombreCortoInstitucion());
                stmtAgrega.setString(3, dato.getUrlInstitucion());
                stmtAgrega.setString(4, dato.getCalleNumInstitucion());
                stmtAgrega.setString(5, dato.getColoniaInstitucion());
                stmtAgrega.setLong(6, dato.getIdMunicipioInstitucion());
                stmtAgrega.setLong(7, dato.getIdEntidadInstitucion());
                stmtAgrega.setString(8, dato.getCodpostalInstitucion());
                stmtAgrega.setString(9, dato.getTelefonoInstitucion());
                stmtAgrega.setLong(10, dato.getIdInstitucion());
                int numafectados = stmtAgrega.executeUpdate();
                if (numafectados == 1) {
                    resultado = true;
                }
            } catch (SQLException ex) {
                //ex.printStackTrace();
                //throw new DaoException(ex.getMessage());
            }
        }
        return resultado;
    }

    @Override
    public boolean update(Institucion dato) {
        boolean resultado = false;
        if (validaDatos(dato) == true) {
            try {
                stmtActualiza.setString(1, dato.getNombreInstitucion());
                stmtActualiza.setString(2, dato.getNombreCortoInstitucion());
                stmtActualiza.setString(3, dato.getUrlInstitucion());
                stmtActualiza.setString(4, dato.getCalleNumInstitucion());
                stmtActualiza.setString(5, dato.getColoniaInstitucion());
                stmtActualiza.setLong(6, dato.getIdMunicipioInstitucion());
                stmtActualiza.setLong(7, dato.getIdEntidadInstitucion());
                stmtActualiza.setString(8, dato.getCodpostalInstitucion());
                stmtActualiza.setString(9, dato.getTelefonoInstitucion());
                stmtActualiza.setLong(10, dato.getIdInstitucion());
                int numafectados = stmtActualiza.executeUpdate();
                if (numafectados == 1) {
                    resultado = true;
                }
            } catch (SQLException ex) {
                //ex.printStackTrace();
                //throw new DaoException(ex.getMessage());
            }
        }
        return resultado;
    }

    @Override
    public boolean delete(Object id) {
        boolean resultado = false;
        if (id instanceof  Long){
            long idInst = (Long) id;
            try{
                stmtBuscaInstEnPersona.setLong(1,idInst);
                ResultSet rs = stmtBuscaInstEnPersona.executeQuery();
                if (!rs.next()){
                    stmtBuscaInstEnSede.setLong(1,idInst);
                    rs = stmtBuscaInstEnSede.executeQuery();
                    if (!rs.next()){
                        stmtBuscaInstEnEquipo.setLong(1, idInst);
                        rs = stmtBuscaInstEnEquipo.executeQuery();
                        if (!rs.next()){
                            stmtElimina.setLong(1,idInst);
                            int numafectados = stmtElimina.executeUpdate();
                            if (numafectados ==1){
                                resultado = true;
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
