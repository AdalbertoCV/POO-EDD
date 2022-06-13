package poo2.progs.basedatos;

import poo2.progs.interfaces.DAOConcursos;
import poo2.progs.interfaces.DaoException;
import poo2.progs.entidades.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoConcursosImpl  implements DAOConcursos {
    private Connection conn;
    private DaoInstitucion daoInst;
    private PreparedStatement stmtObtenMunsDeEntidad;

    /**
     * Constructor vacio que creara una conexion en base
     * a los datos por default
     * @throws DaoException  Si no se puede crear la conexion
     */
    public DaoConcursosImpl() throws DaoException {
        this("localhost","controlconcursos",
                "IngSW","UAZsw2021");
    }

    /**
     * Constructor que recibe el objeto conexion desde fuera
     * y con el cual se establecera la comunicacion con MySQL
     * @param con  Objeto conexion creado desde fuera
     * @throws DaoException  En caso de que el objeto conexion no sea valido
     */
    public DaoConcursosImpl(Connection con) throws DaoException {
        if (con==null) {
            throw new DaoException("Conector recibido es nulo");
        }
        conn=con;
        inicializaDaos(conn);
    }

    public DaoConcursosImpl(String ubicacionServidor, String nomBD,
                            String usuario, String clave)
            throws DaoException {
        String url=String.format("jdbc:mysql://%s/%s",
                ubicacionServidor, nomBD);
        try {
            conn=DriverManager.getConnection(url, usuario, clave);
        }
        catch (SQLException exsql) {
            throw new DaoException(exsql.getMessage(), exsql.getCause());
        }
        inicializaDaos(conn);
    }

    /**
     * Este metodo inicializara los Dao necesarios
     * pasandoles el objeto de conexion
     * @param conn Objeto de conexion a MySQL
     */
    private void inicializaDaos(Connection conn) {
        daoInst = new DaoInstitucion(conn);
        try{
            stmtObtenMunsDeEntidad = conn.prepareStatement("SELECT * FROM municipio WHERE id_municipio DIV (1000) =?");
        }
        catch (SQLException ex){
            throw new DaoException(ex.getMessage());
        }

    }

    @Override
    public List<Entidad> obtenEntidades() {
        return null;
    }

    @Override
    public List<Municipio> obtenMunicipios(long idEstado) {
        List<Municipio> resultado = new ArrayList<>();
        try{
            stmtObtenMunsDeEntidad.setLong(1,idEstado);
            ResultSet rs = stmtObtenMunsDeEntidad.executeQuery();
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
    public List<Institucion> obtenInstituciones() {
        return daoInst.getAll();
    }

    @Override
    public boolean agregaInstitucion(Institucion dato) {
        return daoInst.save(dato);
    }

    @Override
    public boolean eliminaInstitucion(long idInstitucion) {
        return daoInst.delete(idInstitucion);
    }

    @Override
    public boolean actualizaInstitucion(Institucion dato) {
        return daoInst.update(dato);
    }

    @Override
    public List<Persona> obtenPersonas() {
        return null;
    }

    @Override
    public boolean agregaPersona(Persona dato) {
        return false;
    }

    @Override
    public boolean eliminaPersona(String emailPersona) {
        return false;
    }

    @Override
    public boolean actualizaPersona(Persona dato) {
        return false;
    }

    @Override
    public Optional<DatosEstudiante> obtenDatosEstudiante(String emailEstudiante) {
        return Optional.empty();
    }

    @Override
    public boolean agregaDatosEstudiante(DatosEstudiante dato) {
        return false;
    }

    @Override
    public boolean eliminaDatosEstudiante(String emailEstudiante) {
        return false;
    }

    @Override
    public boolean actualizaDatosEstudiante(DatosEstudiante dato) {
        return false;
    }

    @Override
    public List<Sede> obtenSedes() {
        return null;
    }

    @Override
    public boolean agregaSede(Sede dato) {
        return false;
    }

    @Override
    public boolean eliminaSede(long idSede) {
        return false;
    }

    @Override
    public boolean actualizaSede(Sede dato) {
        return false;
    }

    @Override
    public List<Concurso> obtenConcursos() {
        return null;
    }

    @Override
    public boolean agregaConcurso(Concurso dato) {
        return false;
    }

    @Override
    public boolean eliminaConcurso(long idConcurso) {
        return false;
    }

    @Override
    public boolean actualizaConcurso(Concurso dato) {
        return false;
    }

    @Override
    public List<Equipo> obtenEquipos() {
        return null;
    }

    @Override
    public boolean agregaEquipo(Equipo dato) {
        return false;
    }

    @Override
    public boolean eliminaEquipo(long idEquipo) {
        return false;
    }

    @Override
    public boolean actualizaEquipo(Equipo dato) {
        return false;
    }

    @Override
    public List<String> obtenCorreosDeInstitucion(long idInstitucion, String tipo) {
        return null;
    }

    @Override
    public List<Sede> obtenSedesDisponibles(long idConcurso) {
        return null;
    }

    @Override
    public List<SedeConcursoExtendida> obtenSedesAsignadas(long idConcurso) {
        return null;
    }

    @Override
    public boolean agregaSedeConcurso(SedeConcurso dato) {
        return false;
    }

    @Override
    public boolean eliminaSedeConcurso(long idSedeConcurso) {
        return false;
    }

    @Override
    public List<EquiposSedeConcursoExtendida> obtenEquiposRegistrados(long idSedeConcurso, long idInstitucion) {
        return null;
    }

    @Override
    public List<Equipo> obtenEquiposDisponibles(long idConcurso, long idInstitucion) {
        return null;
    }

    @Override
    public boolean registrarEquipoSedeConcurso(EquiposSedeConcurso dato) {
        return false;
    }

    @Override
    public boolean cancelarEquipoSedeConcurso(long idEquipoSedeConcurso) {
        return false;
    }
}
