package poo2.progs.interfaces;

import poo2.progs.entidades.*;

import java.util.List;
import java.util.Optional;

public interface DAOConcursos {
    /**
     * Este metodo regresa las entidades encontradas en la base de datos en la
     * tabla entidad
     *
     * @return Lista de entidades
     */
    public List<Entidad> obtenEntidades();

    /**
     * Este metodo regresa los municipios encontrados en la base de datos en la
     * tabla municipio de una entidad en particular
     *
     * @param idEntidad ID de la entidad de la que se desean los municipios
     * @return Lista de municipios de la entidad indicada
     */
    public List<Municipio> obtenMunicipios(long idEntidad);

    /**
     * Este metodo regresa las instituciones encontradas en la base de datos en la
     * tabla institucion
     *
     * @return Lista de instituciones
     */
    public List<Institucion> obtenInstituciones();

    /**
     * Este metodo agrega una institucion a la base de datos
     * @param dato Objeto de tipo Institucion con los datos del registro a agregar
     * @return true si se pudo agregar, false en caso contrario
     */
    public boolean agregaInstitucion(Institucion dato);

    /**
     * Este metodo elimina una institucion de la base de datos
     * @param idInstitucion ID de la institucion a eliminar
     * @return true si se pudo eliminar, false en caso contrario
     */
    public boolean eliminaInstitucion(long idInstitucion);

    /**
     * Este metodo actualiza la informacion de una institucion en la base de datos
     * @param dato Objeto de tipo Institucion con los datos del registro a actualizar
     * @return true si se pudo actualizar, false en caso contrario
     */
    public boolean actualizaInstitucion(Institucion dato);

    /**
     * Este metodo regresa las personas encontradas en la base de datos en la
     * tabla persona
     *
     * @return Lista de personas
     */
    public List<Persona> obtenPersonas();

    /**
     * Este metodo agrega una persona a la base de datos
     * @param dato Objeto de tipo Persona con los datos del registro a agregar
     * @return true si se pudo agregar, false en caso contrario
     */
    public boolean agregaPersona(Persona dato);

    /**
     * Este metodo elimina una persona de la base de datos
     * @param emailPersona email de la persona a eliminar
     * @return true si se pudo eliminar, false en caso contrario
     */
    public boolean eliminaPersona(String emailPersona);

    /**
     * Este metodo actualiza la informacion de una persona en la base de datos
     * @param dato Objeto de tipo Persona con los datos del registro a actualizar
     * @return true si se pudo actualizar, false en caso contrario
     */
    public boolean actualizaPersona(Persona dato);

    /**
     * Este metodo obtiene la informacion de un estudiante de la base de datos
     * @param emailEstudiante email del estudiante del cual obtener la informacion
     * @return Objeto con la informacion del estudiante
     */
    public Optional<DatosEstudiante> obtenDatosEstudiante(String emailEstudiante);

    /**
     * Este metodo agrega los datos de un estudiante a la base de datos
     * @param dato Objeto de tipo DatosEstudiante con los datos del registro a agregar
     * @return true si se pudo agregar, false en caso contrario
     */
    public boolean agregaDatosEstudiante(DatosEstudiante dato);

    /**
     * Este metodo elimina los datos de un estudiante de la base de datos
     * @param emailEstudiante email del estudiante cuyos datos se desean eliminar
     * @return true si se pudo eliminar, false en caso contrario
     */
    public boolean eliminaDatosEstudiante(String emailEstudiante);

    /**
     * Este metodo actualiza la informacion de un estudiante en la base de datos
     * @param dato Objeto de tipo DatosEstudiante con los datos del registro a actualizar
     * @return true si se pudo actualizar, false en caso contrario
     */
    public boolean actualizaDatosEstudiante(DatosEstudiante dato);

    /**
     * Este metodo regresa las sedes encontradas en la base de datos en la
     * tabla sede
     *
     * @return Lista de sedes
     */
    public List<Sede> obtenSedes();

    /**
     * Este metodo agrega una sede a la base de datos
     * @param dato Objeto de tipo Sede con los datos del registro a agregar
     * @return true si se pudo agregar, false en caso contrario
     */
    public boolean agregaSede(Sede dato);

    /**
     * Este metodo elimina una sede de la base de datos
     * @param idSede ID de la sede a eliminar
     * @return true si se pudo eliminar, false en caso contrario
     */
    public boolean eliminaSede(long idSede);

    /**
     * Este metodo actualiza la informacion de una sede en la base de datos
     * @param dato Objeto de tipo Sede con los datos del registro a actualizar
     * @return true si se pudo actualizar, false en caso contrario
     */
    public boolean actualizaSede(Sede dato);

    /**
     * Este metodo regresa los concursos encontrados en la base de datos en la
     * tabla concurso
     *
     * @return Lista de concursos
     */
    public List<Concurso> obtenConcursos();

    /**
     * Este metodo agrega un concurso a la base de datos
     * @param dato Objeto de tipo Concurso con los datos del registro a agregar
     * @return true si se pudo agregar, false en caso contrario
     */
    public boolean agregaConcurso(Concurso dato);

    /**
     * Este metodo elimina un concurso de la base de datos
     * @param idConcurso ID del concurso a eliminar
     * @return true si se pudo eliminar, false en caso contrario
     */
    public boolean eliminaConcurso(long idConcurso);

    /**
     * Este metodo actualiza la informacion de un concurso en la base de datos
     * @param dato Objeto de tipo Concurso con los datos del registro a actualizar
     * @return true si se pudo actualizar, false en caso contrario
     */
    public boolean actualizaConcurso(Concurso dato);

    /**
     * Este metodo regresa los equipos encontrados en la base de datos en la
     * tabla equipo
     *
     * @return Lista de equipos
     */
    public List<Equipo> obtenEquipos();

    /**
     * Este metodo agrega un equipo a la base de datos
     * @param dato Objeto de tipo Equipo con los datos del registro a agregar
     * @return true si se pudo agregar, false en caso contrario
     */
    public boolean agregaEquipo(Equipo dato);

    /**
     * Este metodo elimina un equipo de la base de datos
     * @param idEquipo ID del equipo a eliminar
     * @return true si se pudo eliminar, false en caso contrario
     */
    public boolean eliminaEquipo(long idEquipo);

    /**
     * Este metodo actualiza la informacion de un equipo en la base de datos
     * @param dato Objeto de tipo Equipo con los datos del registro a actualizar
     * @return true si se pudo actualizar, false en caso contrario
     */
    public boolean actualizaEquipo(Equipo dato);


    /**
     * Este metodo devuelve la lista de correos de las personas de una
     * institucion y tipo especificados (de la tabla persona)
     * @param idInstitucion ID de la institucion de la cual se quieren los correos
     * @param tipo Tipo de correos deseados  (Estudiante o Profesor)
     * @return Lista de correos que cumplen con los parametros especificados
     */
    public List<String> obtenCorreosDeInstitucion(long idInstitucion, String tipo);

    /**
     * Este metodo obtiene las sedes disponibles para un concurso, es decir,
     * las sedes que aun no han sido registradas para el concurso
     * (para ello se usan las tablas sede y sede_concurso)
     * @param idConcurso ID del concurso deseado
     * @return Lista de sedes que aun no estan en el concurso especificado
     */
    public List<Sede> obtenSedesDisponibles(long idConcurso);

    /**
     * Este metodo obtiene las sedes asignadas a un concurso, es decir,
     * las sedes que aun ya fueron registradas para el concurso
     * donde el nombre que se obtiene es el nombre de la sede
     * (para ello se usan las tablas sede y sede_concurso)
     * @param idConcurso ID del concurso deseado
     * @return Lista de sedes que ya estan en el concurso especificado
     */
    public List<SedeConcursoExtendida> obtenSedesAsignadas(long idConcurso);

    /**
     * Este metodo agrega una sede a un concurso en la base de datos
     * lo cual implica agregar un registro en la tabla sede_concurso
     * @param dato Datos de la Sede del Concurso a agregar
     * @return true si se pudo agregar, false en caso contrario
     */
    public boolean agregaSedeConcurso(SedeConcurso dato);

    /**
     * Este metodo elimina una sede para un concurso en la base de datos
     * lo cual implica eliminar un registro en la tabla sede_concurso
     * @param idSedeConcurso ID de la sede del concurso a eliminar
     * @return true si se pudo eliminar, false en caso contrario
     */
    public boolean eliminaSedeConcurso(long idSedeConcurso);


    /**
     * Este metodo obtiene la lista de equipos (en objetos EquiposSedeConcurso)
     * que ya estan registrados en el concurso especificado por el primer argumento
     * y que son de la Institucion indicada por el segundo argumento
     * (para lo cual hay que consultar en la tabla equipos_sede_concurso, sede_concurso, concurso y equipo)
     * @param idConcurso id del concurso en el que se desea verificar que estan registrados los equipos
     * @param idInstitucion ID de la institucion a la que deben pertenecer los equipos
     * @return Lista de objetos EquiposSedeConcursoExtendida que representa a los equipos que
     *         ya estan registrados en para participar en el concurso especificada
     **/
    public List<EquiposSedeConcursoExtendida> obtenEquiposRegistrados(long idConcurso, long idInstitucion);

    /**
     * Este metodo obtiene la lista de equipos de la Institucion especificada
     * que aun no estan registrados en ninguna sede del concurso especificado
     * (para lo cual hay que consultar las tablas equipo, equipos_sede_concurso y sede_concurso)
     * @param idSedeConcurso ID de sede_concurso asociado con el concurso que se pregunta
     * @param idInstitucion ID de la institucion a la que deben pertenecer los equipos
     * @return Lista de objetos Equipo que cumplen con lo solicitado
     */
    public List<Equipo> obtenEquiposDisponibles(long idSedeConcurso, long idInstitucion);

    /**
     * Este metodo registrar un equipo para participar en una sede de un concurso
     * (lo cual implica agregar un registro a la tabla equipos_sede_concurso)
     * @param dato Objeto EquiposSedeConcurso con la informacion a agregar
     * @return true si se pudo agregar, false en caso contrario
     */
    public boolean registrarEquipoSedeConcurso(EquiposSedeConcurso dato);

    /**
     * Este metodo cancela el registro de un equipo en una sede de un concurso
     * (lo cual implica eliminar un registro de la tabla equipos_sede_concurso)
     * @param idEquipoSedeConcurso ID del EquiposSedeConcurso a eliminar
     * @return true si se pudo eliminar, false en caso contrario
     */
    public boolean cancelarEquipoSedeConcurso(long idEquipoSedeConcurso);
}
