package poo2.prog3.clientecontrolconcursos.clientes;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import poo2.prog3.clientecontrolconcursos.interfaces.DAOConcursos;
import poo2.prog3.clientecontrolconcursos.entidades.*;
import poo2.prog3.clientecontrolconcursos.utils.HttpUtils;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public class DaoConcursosImpl implements DAOConcursos, Serializable {
    private final String urlBase;
    private final String pathInstitucion = "institucion/";
    private final String pathPersona = "persona/";

    public DaoConcursosImpl(String urlBase) {
        this.urlBase = urlBase;
    }

    @Override
    public List<Entidad> obtenEntidades() throws IOException {
        return null;
    }

    @Override
    public List<Municipio> obtenMunicipios(long idEntidad) throws IOException {
        return null;
    }

    @Override
    public List<Institucion> obtenInstituciones() throws IOException {
        String respuesta;
        respuesta = HttpUtils.httpGet(urlBase+pathInstitucion,"application/json");
        Type tipoListaInstitucion = new TypeToken<List<Institucion>>() {}.getType();
        GsonBuilder constructor = new GsonBuilder();
        Gson gson = constructor.create();
        return gson.fromJson(respuesta, tipoListaInstitucion);
    }

    @Override
    public boolean agregaInstitucion(Institucion dato) throws IOException {
        String metodo = "POST";
        Gson gson = new Gson();
        String datos = gson.toJson(dato);
        String formato = "application/json";
        String respuesta = "false";
        respuesta = HttpUtils.httpPostPutDelete(urlBase+pathInstitucion,datos, formato, metodo);
        return Boolean.valueOf(respuesta);
    }

    @Override
    public boolean eliminaInstitucion(long idInstitucion) throws IOException {
        String metodo = "DELETE";
        String respuesta = "false";
        respuesta = HttpUtils.httpPostPutDelete(urlBase+pathInstitucion+idInstitucion,null, null, metodo);
        return Boolean.valueOf(respuesta);
    }

    @Override
    public boolean actualizaInstitucion(Institucion dato) throws IOException {
        String metodo = "PUT";
        Gson gson = new Gson();
        String datos = gson.toJson(dato);
        String formato = "application/json";
        String respuesta = "false";
        respuesta = HttpUtils.httpPostPutDelete(urlBase+pathInstitucion+dato.getIdInstitucion(),datos, formato, metodo);
        return Boolean.valueOf(respuesta);
    }

    @Override
    public List<Persona> obtenPersonas() throws IOException {
        String respuesta;
        respuesta = HttpUtils.httpGet(urlBase+pathPersona,"application/json");
        Type tipoListaPersona = new TypeToken<List<Persona>>() {}.getType();
        GsonBuilder constructor = new GsonBuilder();
        Gson gson = constructor.create();
        return gson.fromJson(respuesta, tipoListaPersona);
    }

    @Override
    public boolean agregaPersona(Persona dato) throws IOException {
        String metodo = "POST";
        GsonBuilder constructor = new GsonBuilder();
        constructor.setDateFormat("yyyy-MM-dd'T'HH:mm:ss-00:00");
        //1999-01-07T00: 00: 00-06: 00
        Gson gson= constructor.create();;
        String datos = gson.toJson(dato);
        System.out.println(datos);
        String formato = "application/json";
        String respuesta = "false";
        respuesta = HttpUtils.httpPostPutDelete(urlBase+pathPersona,datos, formato, metodo);
        return Boolean.valueOf(respuesta);
    }

    @Override
    public boolean eliminaPersona(String emailPersona) throws IOException {
        String metodo = "DELETE";
        String respuesta = "false";
        respuesta = HttpUtils.httpPostPutDelete(urlBase+pathPersona+emailPersona,null, null, metodo);
        return Boolean.valueOf(respuesta);
    }

    @Override
    public boolean actualizaPersona(Persona dato) throws IOException {
        String metodo = "PUT";
        GsonBuilder constructor = new GsonBuilder();
        constructor.setDateFormat("yyyy-MM-dd'T'HH:mm:ss-00:00");
        //1999-01-07T00: 00: 00-06: 00
        Gson gson= constructor.create();
        String datos = gson.toJson(dato);
        System.out.println(datos);
        String formato = "application/json";
        String respuesta = "false";
        respuesta = HttpUtils.httpPostPutDelete(urlBase+pathPersona+dato.getEmailPersona(),datos, formato, metodo);
        return Boolean.valueOf(respuesta);
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
    public List<EquiposSedeConcursoExtendida> obtenEquiposRegistrados(long idConcurso, long idInstitucion) {
        return null;
    }

    @Override
    public List<Equipo> obtenEquiposDisponibles(long idSedeConcurso, long idInstitucion) {
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
