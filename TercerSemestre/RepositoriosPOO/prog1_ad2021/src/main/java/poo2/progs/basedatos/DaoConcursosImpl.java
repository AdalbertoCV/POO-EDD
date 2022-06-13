package poo2.progs.basedatos;

import poo2.progs.interfaces.DAOConcursos;
import poo2.progs.interfaces.DaoException;
import poo2.progs.entidades.*;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoConcursosImpl  implements DAOConcursos {
    private Connection conn;
    private DaoInstitucion daoInst;
    private DaoPersona daoPersona;
    private DaoDatosEstudiante daoDE;
    private DaoSede daoSede;
    private DaoConcurso daoConcurso;
    private DaoEquipo daoEquipo;
    private DaoSedeConcurso daoSC;
    private DaoEntidad daoEntidad;
    private DaoEquiposSedeConcurso daoESC;
    private PreparedStatement stmtObtenMunsDeEntidad;
    private PreparedStatement buscaSedeenConcurso;
    private PreparedStatement buscaSede;
    private PreparedStatement buscaSedes;
    private PreparedStatement buscaConcurso;
    private PreparedStatement buscaInstEquipo;
    private PreparedStatement buscaEquiposSedeConcurso;
    private PreparedStatement buscaEquipos;
    private PreparedStatement buscaPersona;
    private PreparedStatement buscaEquipo;

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
        daoPersona = new DaoPersona(conn);
        daoDE = new DaoDatosEstudiante(conn);
        daoSede = new DaoSede(conn);
        daoConcurso = new DaoConcurso(conn);
        daoEquipo = new DaoEquipo(conn);
        daoSC = new DaoSedeConcurso(conn);
        daoEntidad = new DaoEntidad(conn);
        daoESC = new DaoEquiposSedeConcurso(conn);
        try{
            stmtObtenMunsDeEntidad = conn.prepareStatement("SELECT * FROM municipio WHERE id_municipio DIV (1000) =?");
            buscaSedeenConcurso = conn.prepareStatement("SELECT * FROM sede_concurso WHERE id_concurso=?");
            buscaSede = conn.prepareStatement("SELECT * FROM sede WHERE id_sede=?");
            buscaSedes = conn.prepareStatement("SELECT * FROM sede");
            buscaConcurso = conn.prepareStatement("SELECT * FROM concurso WHERE id_concurso=?");
            buscaInstEquipo = conn.prepareStatement("SELECT * FROM equipo WHERE id_institucion_equipo=?");
            buscaEquiposSedeConcurso = conn.prepareStatement("SELECT * FROM equipos_sede_concurso WHERE id_sede_concurso=?");
            buscaEquipos = conn.prepareStatement("SELECT * FROM equipo");
            buscaPersona = conn.prepareStatement("SELECT * FROM persona");
            buscaEquipo = conn.prepareStatement("SELECT * FROM equipo WHERE id_equipo=?");
        }
        catch (SQLException ex){
            throw new DaoException(ex.getMessage());
        }

    }

    @Override
    public List<Entidad> obtenEntidades() {
        return daoEntidad.getAll();
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
       return  daoPersona.getAll();
    }

    @Override
    public boolean agregaPersona(Persona dato) {
        return daoPersona.save(dato);
    }

    @Override
    public boolean eliminaPersona(String emailPersona) {
        return daoPersona.delete(emailPersona);
    }

    @Override
    public boolean actualizaPersona(Persona dato) {
        return daoPersona.update(dato);
    }

    @Override
    public Optional<DatosEstudiante> obtenDatosEstudiante(String emailEstudiante) {
        return daoDE.get(emailEstudiante);
    }

    @Override
    public boolean agregaDatosEstudiante(DatosEstudiante dato) {
        return daoDE.save(dato);
    }

    @Override
    public boolean eliminaDatosEstudiante(String emailEstudiante) {
        return daoDE.delete(emailEstudiante);
    }

    @Override
    public boolean actualizaDatosEstudiante(DatosEstudiante dato) {
        return daoDE.update(dato);
    }

    @Override
    public List<Sede> obtenSedes() {
        return daoSede.getAll();
    }

    @Override
    public boolean agregaSede(Sede dato) {
        return daoSede.save(dato);
    }

    @Override
    public boolean eliminaSede(long idSede) {
        return daoSede.delete(idSede);
    }

    @Override
    public boolean actualizaSede(Sede dato) {
        return daoSede.update(dato);
    }

    @Override
    public List<Concurso> obtenConcursos() {
        return daoConcurso.getAll();
    }

    @Override
    public boolean agregaConcurso(Concurso dato) {
        return daoConcurso.save(dato);
    }

    @Override
    public boolean eliminaConcurso(long idConcurso) {
        return daoConcurso.delete(idConcurso);
    }

    @Override
    public boolean actualizaConcurso(Concurso dato) {
        return daoConcurso.update(dato);
    }

    @Override
    public List<Equipo> obtenEquipos() {
        return daoEquipo.getAll();
    }

    @Override
    public boolean agregaEquipo(Equipo dato) {
        return daoEquipo.save(dato);
    }

    @Override
    public boolean eliminaEquipo(long idEquipo) {
        return daoEquipo.delete(idEquipo);
    }

    @Override
    public boolean actualizaEquipo(Equipo dato) {
        return daoEquipo.update(dato);
    }

    @Override
    public List<String> obtenCorreosDeInstitucion(long idInstitucion, String tipo) {
        List<String> Resultado = new ArrayList<>();
        try{
            ResultSet rs = buscaPersona.executeQuery();
            while(rs.next()){
                long idinst=rs.getLong(12);
                String tipoP = rs.getString(13);
                if((idInstitucion==idinst)&&(tipoP.equals(tipo))){
                    String email=rs.getString(1);
                    Resultado.add(email);
                }
            }

        }catch(SQLException ex){
            throw new DaoException(ex.getMessage());
        }

        return Resultado;
    }

    @Override
    public List<Sede> obtenSedesDisponibles(long idConcurso) {
        List<Sede> resultado = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        try{
            buscaSedeenConcurso.setLong(1,idConcurso);
            ResultSet rs = buscaSedeenConcurso.executeQuery();
            while (rs.next()) {
                long idSedeC = rs.getLong("id_sede");
                ids.add(idSedeC);
            }
            ResultSet rs1 = buscaSedes.executeQuery();
            while (rs1.next()){
                long idSede = rs1.getLong("id_sede");
                String nombre = rs1.getString("nombre_sede");
                long idInst = rs1.getLong("id_institucion_sede");
                String email = rs1.getString("email_director_sede");
                String url = rs1.getString("url_sede");
                    if (!ids.contains(idSede)){
                        Sede sede = new Sede(idSede,nombre,idInst,email);
                        sede.setUrlSede(url);
                        resultado.add(sede);
                    }

            }
        }
        catch(SQLException ex){
        }
        return resultado;
    }

    @Override
    public List<SedeConcursoExtendida> obtenSedesAsignadas(long idConcurso) {
        List<SedeConcursoExtendida> resultado = new ArrayList<>();
        try{
            buscaSedeenConcurso.setLong(1,idConcurso);
            ResultSet rs = buscaSedeenConcurso.executeQuery();
             while (rs.next()){
                 long idSC =  rs.getLong("id_sede_concurso");
                 long idSede = rs.getLong("id_sede");
                 buscaSede.setLong(1,idSede);
                 ResultSet rs1 = buscaSede.executeQuery();
                 buscaConcurso.setLong(1,idConcurso);
                 ResultSet rs2 = buscaConcurso.executeQuery();
                 SedeConcursoExtendida sede = new SedeConcursoExtendida(idSC,idSede,idConcurso);
                 if (rs1.next()){
                     sede.setNombreSede(rs1.getString("nombre_sede"));
                 }
                 if (rs2.next()){
                     sede.setNombreConcurso(rs2.getString("nombre_concurso"));
                 }
                 resultado.add(sede);
             }
        }
        catch(SQLException ex){
        }
        return resultado;
    }

    @Override
    public boolean agregaSedeConcurso(SedeConcurso dato) {
        return daoSC.save(dato);
    }

    @Override
    public boolean eliminaSedeConcurso(long idSedeConcurso) {
        return daoSC.delete(idSedeConcurso);
    }

    @Override
    public List<EquiposSedeConcursoExtendida> obtenEquiposRegistrados(long idConcurso, long idInstitucion) {
        List<EquiposSedeConcursoExtendida> resultado = new ArrayList<>();
        try{
            buscaConcurso.setLong(1,idConcurso);
            ResultSet rs = buscaConcurso.executeQuery();
            if (rs.next()){
                String nombreConcurso = rs.getString("nombre_concurso");
                buscaSedeenConcurso.setLong(1,idConcurso);
                rs = buscaSedeenConcurso.executeQuery();
                while (rs.next()){
                    long idSedeConcurso = rs.getLong("id_sede_concurso");
                    long idSede = rs.getLong("id_sede");
                    buscaSede.setLong(1,idSede);
                    ResultSet rs1 = buscaSede.executeQuery();
                    if (rs1.next()){
                        String nombreSede = rs1.getString("nombre_sede");
                        buscaEquiposSedeConcurso.setLong(1,idSedeConcurso);
                        rs1 = buscaEquiposSedeConcurso.executeQuery();
                        while (rs1.next()){
                            long idESC = rs1.getLong("id_equipo_sede_concurso");
                            long idEquipo = rs1.getLong("id_equipo");
                            buscaEquipo.setLong(1,idEquipo);
                            ResultSet rs2 = buscaEquipo.executeQuery();
                            if (rs2.next()){
                                String nombreEquipo = rs2.getString("nombre_equipo");
                                long idInstitucionEquipo = rs2.getLong("id_institucion_equipo");
                                if (idInstitucionEquipo == idInstitucion){
                                    EquiposSedeConcursoExtendida ESC = new EquiposSedeConcursoExtendida(idESC,idEquipo,idSedeConcurso);
                                    ESC.setNombreConcurso(nombreConcurso);
                                    ESC.setNombreSede(nombreSede);
                                    ESC.setNombreEquipo(nombreEquipo);
                                    resultado.add(ESC);
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(SQLException ex){
        }
        return resultado;
    }

    @Override
    public List<Equipo> obtenEquiposDisponibles(long idConcurso, long idInstitucion) {
        List<Equipo> resultado = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        try {
            buscaSedeenConcurso.setLong(1, idConcurso);
            ResultSet rs = buscaSedeenConcurso.executeQuery();
            while (rs.next()) {
                long idSedeConcurso = rs.getLong("id_sede_concurso");
                buscaEquiposSedeConcurso.setLong(1, idSedeConcurso);
                ResultSet rs1 = buscaEquiposSedeConcurso.executeQuery();
                while (rs1.next()) {
                    long idEquipo = rs1.getLong("id_equipo");
                    ids.add(idEquipo);
                }
            }
            if (ids.size()==0){
                buscaEquiposSedeConcurso.setLong(1, idConcurso);
                ResultSet rs3 = buscaEquiposSedeConcurso.executeQuery();
                while(rs3.next()){
                    long idEquipo = rs3.getLong("id_equipo");
                    ids.add(idEquipo);
                }
                try{
                    ResultSet rs4 = buscaEquipos.executeQuery();
                    while (rs4.next()) {
                        long idE = rs4.getLong("id_equipo");
                        String nom = rs4.getString("nombre_equipo");
                        String mailC = rs4.getString("email_coach");
                        String mailC1 = rs4.getString("email_concursante1");
                        String mailC2 = rs4.getString("email_concursante2");
                        String mailC3 = rs4.getString("email_concursante3");
                        String mailR = rs4.getString("email_concursante_reserva");
                        long idI = rs4.getLong("id_institucion_equipo");
                        if (idI == idInstitucion) {
                            if (ids.contains(idE)) {
                                Equipo equipo = new Equipo(idE, nom, mailC, mailC1, mailC2, mailC3, idI);
                                equipo.setEmailConcursanteReserva(mailR);
                                resultado.add(equipo);
                            }
                        }
                    }
                    if (!(resultado.size()==0)){
                        resultado.remove(0);
                    }
                }
                catch(SQLException ex) {
                }
            }
    }
        catch(SQLException ex) {
    }
        try {
        ResultSet rs2 = buscaEquipos.executeQuery();
        while (rs2.next()) {
            long idE = rs2.getLong("id_equipo");
            String nom = rs2.getString("nombre_equipo");
            String mailC = rs2.getString("email_coach");
            String mailC1 = rs2.getString("email_concursante1");
            String mailC2 = rs2.getString("email_concursante2");
            String mailC3 = rs2.getString("email_concursante3");
            String mailR = rs2.getString("email_concursante_reserva");
            long idI = rs2.getLong("id_institucion_equipo");
            if (idI == idInstitucion) {
                if (!ids.contains(idE)) {
                    Equipo equipo = new Equipo(idE, nom, mailC, mailC1, mailC2, mailC3, idI);
                    equipo.setEmailConcursanteReserva(mailR);
                    resultado.add(equipo);
                }
            }
        }
    }
        catch(SQLException ex){
    }
        return resultado;
    }

    @Override
    public boolean registrarEquipoSedeConcurso(EquiposSedeConcurso dato) {
        return daoESC.save(dato);
    }

    @Override
    public boolean cancelarEquipoSedeConcurso(long idEquipoSedeConcurso) {
        return daoESC.delete(idEquipoSedeConcurso);
    }
}
