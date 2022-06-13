package poo2.progs.main;

import poo2.progs.entidades.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ClasesDeEntidadTest {
    private final static double CALIF_CLASE_ENTIDAD=0.5;
    private final static double CALIF_CLASE_INSTITUCION=0.5;
    private final static double CALIF_CLASE_SEDE=0.5;
    private final static double CALIF_CLASE_EQUIPO=0.5;
    private final static double CALIF_CLASE_CONCURSO=0.5;
    private final static double CALIF_CLASE_PERSONA=0.5;
    private final static double CALIF_CLASE_DATOSESTUDIANTE=0.5;
    private final static double CALIF_CLASE_MUNICIPIO=0.5;
    private final static double CALIF_CLASE_SEDECONCURSO=0.4;
    private final static double CALIF_CLASE_SEDECONCURSOEXT=0.6;
    private final static double CALIF_CLASE_EQUIPOSSEDECONCURSO=0.4;
    private final static double CALIF_CLASE_EQUIPOSSEDECONCURSOEXT=0.6;
    private final static double MAX_CALIF=6.0;
    private static double calificacion;

    @BeforeAll
    static void beforeAll() {
        calificacion=0;
    }

    @Test
    void testInstitucion() {
        long id=5;
        String nom="Universidad Tecnologica Estado de Zacatecas";
        String otronom="Instituto Inutil";
        String nomcorto="UTZAC";
        String url="http://www.utzac.edu.mx";
        String calle="Carretera Zacatecas, Cd Cuauhtemoc km. 5";
        String colonia="Ejido Cieneguitas";
        long idmun=32017;
        long ident=32;
        String codpostal="98601";
        String tel="4929276180";
        Institucion inst_agregar = new Institucion(id, nom, nomcorto, url,
                calle, idmun, ident);
        inst_agregar.setColoniaInstitucion(colonia);
        inst_agregar.setCodpostalInstitucion(codpostal);
        inst_agregar.setTelefonoInstitucion(tel);
        assertEquals(id, inst_agregar.getIdInstitucion());
        assertEquals(nom, inst_agregar.getNombreInstitucion());
        assertEquals(nomcorto, inst_agregar.getNombreCortoInstitucion());
        assertEquals(url, inst_agregar.getUrlInstitucion());
        assertEquals(calle, inst_agregar.getCalleNumInstitucion());
        assertEquals(colonia, inst_agregar.getColoniaInstitucion());
        assertEquals(idmun, inst_agregar.getIdMunicipioInstitucion());
        assertEquals(ident, inst_agregar.getIdEntidadInstitucion());
        assertEquals(codpostal, inst_agregar.getCodpostalInstitucion());
        assertEquals(tel, inst_agregar.getTelefonoInstitucion());
        assertEquals(nom, inst_agregar.toString());

        Institucion otra=new Institucion(id);
        otra.setNombreInstitucion(otronom);
        assertEquals(inst_agregar, otra, "La clase Institucion deberia comparar dos objetos en base a idInstitucion");

        otra.setNombreInstitucion(nom);
        otra.setNombreCortoInstitucion(nomcorto);
        otra.setUrlInstitucion(url);
        otra.setCalleNumInstitucion(calle);
        otra.setColoniaInstitucion(colonia);
        otra.setIdMunicipioInstitucion(idmun);
        otra.setIdEntidadInstitucion(ident);
        otra.setCodpostalInstitucion(codpostal);
        otra.setTelefonoInstitucion(tel);
        assertEquals(id, otra.getIdInstitucion());
        assertEquals(nom, otra.getNombreInstitucion());
        assertEquals(nomcorto, otra.getNombreCortoInstitucion());
        assertEquals(url, otra.getUrlInstitucion());
        assertEquals(calle, otra.getCalleNumInstitucion());
        assertEquals(colonia, otra.getColoniaInstitucion());
        assertEquals(idmun, otra.getIdMunicipioInstitucion());
        assertEquals(ident, otra.getIdEntidadInstitucion());
        assertEquals(codpostal, otra.getCodpostalInstitucion());
        assertEquals(tel, otra.getTelefonoInstitucion());
        assertEquals(nom, otra.toString());

        otra=new Institucion();
        otra.setIdInstitucion(id+1);
        assertNotEquals(inst_agregar, otra);
        boolean esSerializable=inst_agregar instanceof Serializable;
        assertTrue(esSerializable, "La clase Institucion deberia implementar Serializable");
        calificacion += CALIF_CLASE_INSTITUCION;
    }

    @Test
    void testEntidad() {
        long identidad=20;
        String nombre="Mi nueva entidad";
        String otroNombre="Otra entidad";
        Entidad ent = new Entidad(identidad,nombre);
        assertEquals(identidad,ent.getIdEntidad());
        assertEquals(nombre,ent.getNombreEntidad());
        assertEquals(nombre,ent.toString());

        Entidad otra = new Entidad(identidad);
        otra.setNombreEntidad(otroNombre);
        assertEquals(otroNombre,otra.getNombreEntidad());
        assertEquals(ent,otra,"La clase Entidad deberia comparar dos objetos en base a idEntidad");

        otra.setNombreEntidad(nombre);
        assertEquals(identidad,otra.getIdEntidad());
        assertEquals(nombre,otra.getNombreEntidad());
        assertEquals(nombre,otra.toString());

        otra = new Entidad();
        otra.setIdEntidad(identidad+1);
        assertNotEquals(ent, otra);
        boolean esSerializable=ent instanceof Serializable;
        assertTrue(esSerializable, "La clase Entidad deberia implementar Serializable");
        calificacion += CALIF_CLASE_ENTIDAD;
    }

    @Test
    void testMunicipio() {
        long idmun=30012;
        String nombre="Mi nuevo municipio";
        String otroNombre="Otro municipio";
        Municipio mun = new Municipio(idmun,nombre);
        assertEquals(idmun,mun.getIdMunicipio());
        assertEquals(nombre,mun.getNombreMunicipio());
        assertEquals(nombre,mun.toString());

        Municipio otro = new Municipio(idmun);
        otro.setNombreMunicipio("Otro municipio");
        assertEquals(otroNombre,otro.getNombreMunicipio());
        assertEquals(mun,otro,"La clase Municipio deberia comparar dos objetos en base a idMunicipio");

        otro.setNombreMunicipio(nombre);
        assertEquals(idmun,otro.getIdMunicipio());
        assertEquals(nombre,otro.getNombreMunicipio());
        assertEquals(nombre,otro.toString());

        otro = new Municipio();
        otro.setIdMunicipio(idmun+1);
        assertNotEquals(mun, otro);
        boolean esSerializable=mun instanceof Serializable;
        assertTrue(esSerializable, "La clase Municipio deberia implementar Serializable");
        calificacion += CALIF_CLASE_MUNICIPIO;
    }

    @Test
    void testPersona() {
        String email="rsolis@uaz.edu.mx";
        String nombre="Roberto";
        String apellidos="Solis Robles";
        String nomcompleto=String.format("%s %s",nombre,apellidos);
        String sexo="M";
        String calle="Hidalgo 100";
        String colonia="Centro";
        long idmun=32056L;
        long ident=32L;
        String codpostal="98000";
        String telefono="4921234567";
        Date fechaNac= java.sql.Date.valueOf("1980-08-13");
        long idInst=1L;
        String tipo="Profesor";
        Persona per=new Persona(email,nombre,apellidos,
                sexo,calle,idmun,ident,telefono,fechaNac,idInst,tipo);
        per.setColoniaPersona(colonia);
        per.setCodpostalPersona(codpostal);
        assertEquals(email,per.getEmailPersona());
        assertEquals(nombre,per.getNombrePersona());
        assertEquals(apellidos,per.getApellidosPersona());
        assertEquals(sexo,per.getSexoPersona());
        assertEquals(calle,per.getCalleNumPersona());
        assertEquals(colonia,per.getColoniaPersona());
        assertEquals(idmun,per.getIdMunicipioPersona());
        assertEquals(ident,per.getIdEntidadPersona());
        assertEquals(codpostal,per.getCodpostalPersona());
        assertEquals(telefono,per.getTelefonoPersona());
        assertEquals(fechaNac,per.getFechaNacPersona());
        assertEquals(idInst,per.getIdInstitucionPersona());
        assertEquals(tipo,per.getTipoPersona());
        assertEquals(nomcompleto,per.toString());

        Persona otra = new Persona(email);
        otra.setNombrePersona(nomcompleto);
        assertEquals(per,otra,"La clase Persona deberia comparar dos objetos en base a emailPersona");

        otra.setNombrePersona(nombre);
        otra.setApellidosPersona(apellidos);
        otra.setSexoPersona(sexo);
        otra.setCalleNumPersona(calle);
        otra.setColoniaPersona(colonia);
        otra.setIdMunicipioPersona(idmun);
        otra.setIdEntidadPersona(ident);
        otra.setCodpostalPersona(codpostal);
        otra.setTelefonoPersona(telefono);
        otra.setFechaNacPersona(fechaNac);
        otra.setIdInstitucionPersona(idInst);
        otra.setTipoPersona(tipo);
        assertEquals(email,otra.getEmailPersona());
        assertEquals(nombre,otra.getNombrePersona());
        assertEquals(apellidos,otra.getApellidosPersona());
        assertEquals(sexo,otra.getSexoPersona());
        assertEquals(calle,otra.getCalleNumPersona());
        assertEquals(colonia,otra.getColoniaPersona());
        assertEquals(idmun,otra.getIdMunicipioPersona());
        assertEquals(ident,otra.getIdEntidadPersona());
        assertEquals(codpostal,otra.getCodpostalPersona());
        assertEquals(telefono,otra.getTelefonoPersona());
        assertEquals(fechaNac,otra.getFechaNacPersona());
        assertEquals(idInst,otra.getIdInstitucionPersona());
        assertEquals(tipo,otra.getTipoPersona());
        assertEquals(nomcompleto,otra.toString());

        otra = new Persona();
        otra.setEmailPersona("otro@uaz.edu.mx");
        assertNotEquals(per, otra);
        boolean esSerializable=per instanceof Serializable;
        assertTrue(esSerializable, "La clase Persona deberia implementar Serializable");
        calificacion += CALIF_CLASE_PERSONA;
    }

    @Test
    void testConcurso() {
        long idconcurso=90L;
        String nom="Gran Premio MX 2020";
        Date fechaConc= java.sql.Date.valueOf("2020-10-31");
        Date fechaInicio= java.sql.Date.valueOf("2020-09-30");
        Date fechaFin= java.sql.Date.valueOf("2020-10-28");
        Concurso conc= new Concurso(idconcurso,nom,fechaConc,fechaInicio,fechaFin);
        assertEquals(idconcurso,conc.getIdConcurso());
        assertEquals(nom,conc.getNombreConcurso());
        assertEquals(fechaConc,conc.getFechaConcurso());
        assertEquals(fechaInicio,conc.getFechaInicioRegistro());
        assertEquals(fechaFin,conc.getFechaFinRegistro());
        assertEquals(nom,conc.toString());
        
        Concurso otro=new Concurso(idconcurso);
        otro.setNombreConcurso(nom+nom);
        assertEquals(conc,otro,"La clase Concurso deberia comparar dos objetos en base a idConcurso");
        
        otro.setNombreConcurso(nom);
        otro.setFechaConcurso(fechaConc);
        otro.setFechaInicioRegistro(fechaInicio);
        otro.setFechaFinRegistro(fechaFin);
        assertEquals(idconcurso,otro.getIdConcurso());
        assertEquals(nom,otro.getNombreConcurso());
        assertEquals(fechaConc,otro.getFechaConcurso());
        assertEquals(fechaInicio,otro.getFechaInicioRegistro());
        assertEquals(fechaFin,otro.getFechaFinRegistro());
        assertEquals(nom,otro.toString());

        otro = new Concurso();
        otro.setIdConcurso(idconcurso+1);
        assertNotEquals(conc, otro);
        boolean esSerializable=conc instanceof Serializable;
        assertTrue(esSerializable, "La clase Concurso deberia implementar Serializable");
        calificacion += CALIF_CLASE_CONCURSO;
    }

    @Test
    void testDatosEstudiante() {
        String email="juanito@uaz.edu.mx";
        String carrera="Ing de Software";
        Date fechaInicio= java.sql.Date.valueOf("2018-08-10");
        Date fechaFin= java.sql.Date.valueOf("2023-06-28");
        DatosEstudiante de = new DatosEstudiante(email,carrera,fechaInicio,fechaFin);
        assertEquals(email,de.getEmailEstudiante());
        assertEquals(carrera,de.getCarreraEstudiante());
        assertEquals(fechaInicio,de.getFechaInicioCarrera());
        assertEquals(fechaFin,de.getFechaEsperadaTerminacion());
        assertEquals(email,de.toString());
        
        DatosEstudiante otro=new DatosEstudiante(email);
        otro.setCarreraEstudiante(carrera+carrera);
        assertEquals(de,otro,"La clase DatosEstudiante deberia comparar dos objetos en base a emailEstudiante");
        
        otro.setCarreraEstudiante(carrera);
        otro.setFechaInicioCarrera(fechaInicio);
        otro.setFechaEsperadaTerminacion(fechaFin);
        assertEquals(email,otro.getEmailEstudiante());
        assertEquals(carrera,otro.getCarreraEstudiante());
        assertEquals(fechaInicio,otro.getFechaInicioCarrera());
        assertEquals(fechaFin,otro.getFechaEsperadaTerminacion());
        assertEquals(email,otro.toString());

        otro= new DatosEstudiante();
        otro.setEmailEstudiante(email+email);
        assertNotEquals(de, otro);
        boolean esSerializable=de instanceof Serializable;
        assertTrue(esSerializable, "La clase DatosEstudiante deberia implementar Serializable");
        calificacion += CALIF_CLASE_DATOSESTUDIANTE;
    }

    @Test
    void testSede() {
        long idsede=87L;
        String nombre="UAIE-UAZ";
        long idinst=1L;
        String email="rsolis@uaz.edu.mx";
        String urlsede="http://www.uaz.edu.mx/sedeicpc";
        Sede sede= new Sede(idsede,nombre,idinst,email);
        sede.setUrlSede(urlsede);
        assertEquals(idsede,sede.getIdSede());
        assertEquals(nombre,sede.getNombreSede());
        assertEquals(idinst,sede.getIdInstitucionSede());
        assertEquals(email,sede.getEmailDirectorSede());
        assertEquals(urlsede,sede.getUrlSede());
        assertEquals(nombre,sede.toString());
        
        Sede otra=new Sede(idsede);
        otra.setNombreSede(nombre+nombre);
        assertEquals(sede,otra,"La clase Sede deberia comparar dos objetos en base a idSede");
        otra.setNombreSede(nombre);
        otra.setEmailDirectorSede(email);
        otra.setIdInstitucionSede(idinst);
        otra.setUrlSede(urlsede);
        assertEquals(idsede,otra.getIdSede());
        assertEquals(nombre,otra.getNombreSede());
        assertEquals(idinst,otra.getIdInstitucionSede());
        assertEquals(email,otra.getEmailDirectorSede());
        assertEquals(urlsede,otra.getUrlSede());
        assertEquals(nombre,otra.toString());

        otra=new Sede();
        otra.setIdSede(idsede+1);
        assertNotEquals(sede, otra);
        boolean esSerializable=sede instanceof Serializable;
        assertTrue(esSerializable, "La clase Sede deberia implementar Serializable");
        calificacion += CALIF_CLASE_SEDE;
    }

    @Test
    void testEquipo() {
        long idequipo=10L;
        String nombre="ilusionistas";
        String emailcoach="rsolis@uaz.edu.mx";
        String email1="carlos@uaz.edu.mx";
        String email2="eduardo@mate.com";
        String email3="emilioh@hotmail.com";
        String emailR="quintero@yahoo.com.mx";
        long idinst=1L;
        Equipo equipo= new Equipo(idequipo,nombre,emailcoach,email1,email2,email3,idinst);
        equipo.setEmailConcursanteReserva(emailR);
        assertEquals(idequipo,equipo.getIdEquipo());
        assertEquals(nombre,equipo.getNombreEquipo());
        assertEquals(emailcoach,equipo.getEmailCoach());
        assertEquals(email1,equipo.getEmailConcursante1());
        assertEquals(email2,equipo.getEmailConcursante2());
        assertEquals(email3,equipo.getEmailConcursante3());
        assertEquals(emailR,equipo.getEmailConcursanteReserva());
        assertEquals(idinst,equipo.getIdInstitucionEquipo());
        assertEquals(nombre,equipo.toString());
        
        Equipo otro=new Equipo(idequipo);
        otro.setNombreEquipo(email1);
        assertEquals(equipo,otro,"La clase Equipo deberia comparar dos objetos en base a idEquipo");

        otro.setNombreEquipo(nombre);
        otro.setEmailCoach(emailcoach);
        otro.setEmailConcursante1(email1);
        otro.setEmailConcursante2(email2);
        otro.setEmailConcursante3(email3);
        otro.setEmailConcursanteReserva(emailR);
        otro.setIdInstitucionEquipo(idinst);
        assertEquals(idequipo,otro.getIdEquipo());
        assertEquals(nombre,otro.getNombreEquipo());
        assertEquals(emailcoach,otro.getEmailCoach());
        assertEquals(email1,otro.getEmailConcursante1());
        assertEquals(email2,otro.getEmailConcursante2());
        assertEquals(email3,otro.getEmailConcursante3());
        assertEquals(emailR,otro.getEmailConcursanteReserva());
        assertEquals(idinst,otro.getIdInstitucionEquipo());
        assertEquals(nombre,otro.toString());

        otro=new Equipo();
        otro.setIdEquipo(idequipo+1);
        assertNotEquals(equipo, otro);
        boolean esSerializable=equipo instanceof Serializable;
        assertTrue(esSerializable, "La clase Equipo deberia implementar Serializable");
        calificacion += CALIF_CLASE_EQUIPO;
    }

    @Test
    void testSedeConcurso() {
        long idsedeconc=100;
        long idsede=10L;
        long idconc=5L;
        SedeConcurso sc= new SedeConcurso(idsedeconc,idsede,idconc);
        assertEquals(idsedeconc,sc.getIdSedeConcurso());
        assertEquals(idsede,sc.getIdSede());
        assertEquals(idconc,sc.getIdConcurso());

        SedeConcurso otro=new SedeConcurso(idsedeconc);
        otro.setIdSede(idconc);
        otro.setIdConcurso(idsede);
        assertEquals(sc,otro,"La clase SedeConcurso deberia comparar dos objetos en base a idSedeConcurso");
        assertEquals(idsedeconc, otro.getIdSedeConcurso());
        assertEquals(idsede,otro.getIdConcurso());
        assertEquals(idconc,otro.getIdSede());

        otro=new SedeConcurso();
        otro.setIdSedeConcurso(idsedeconc+1);
        assertNotEquals(sc, otro);
        boolean esSerializable=sc instanceof Serializable;
        assertTrue(esSerializable, "La clase SedeConcurso deberia implementar Serializable");
        calificacion += CALIF_CLASE_SEDECONCURSO;
    }

    @Test
    void testEquiposSedeConcurso() {
        long ideqsedeconc=18L;
        long idsedeconc=100;
        long idequipo=10L;
        EquiposSedeConcurso esc= new EquiposSedeConcurso(ideqsedeconc,idequipo,idsedeconc);
        assertEquals(ideqsedeconc,esc.getIdEquipoSedeConcurso());
        assertEquals(idsedeconc,esc.getIdSedeConcurso());
        assertEquals(idequipo,esc.getIdEquipo());

        EquiposSedeConcurso otro=new EquiposSedeConcurso(ideqsedeconc);
        otro.setIdEquipo(idsedeconc);
        otro.setIdSedeConcurso(idequipo);
        assertEquals(esc,otro,"La clase EquiposSedeConcurso deberia comparar dos objetos en base a idEquiposSedeConcurso");
        assertEquals(ideqsedeconc, otro.getIdEquipoSedeConcurso());
        assertEquals(idequipo,otro.getIdSedeConcurso());
        assertEquals(idsedeconc,otro.getIdEquipo());

        otro=new EquiposSedeConcurso();
        otro.setIdEquipoSedeConcurso(ideqsedeconc+1);
        assertNotEquals(esc, otro);
        boolean esSerializable=esc instanceof Serializable;
        assertTrue(esSerializable, "La clase EquiposSedeConcurso deberia implementar Serializable");
        calificacion += CALIF_CLASE_EQUIPOSSEDECONCURSO;
    }

    @Test
    void testSedeConcursoExtendida() {
        long idsedeconc=100;
        long idsede=10L;
        long idconc=5L;
        String nomSede="UAIE-UAZ";
        String nomConc="Gran Premio MX 2020";
        String nom=String.format("%s (%s)",nomSede,nomConc);
        SedeConcursoExtendida sce= new SedeConcursoExtendida(idsedeconc,idsede,idconc);
        sce.setNombreSede(nomSede);
        sce.setNombreConcurso(nomConc);
        assertEquals(idsedeconc,sce.getIdSedeConcurso());
        assertEquals(idsede,sce.getIdSede());
        assertEquals(idconc,sce.getIdConcurso());
        assertEquals(nomSede, sce.getNombreSede());
        assertEquals(nomConc, sce.getNombreConcurso());
        assertEquals(nom, sce.toString());

        SedeConcursoExtendida otra=new SedeConcursoExtendida(idsedeconc);
        otra.setIdSede(idconc);
        otra.setIdConcurso(idsede);
        assertEquals(sce,otra,"La clase SedeConcursoExtendida deberia comparar dos objetos en base a idSedeConcurso");
        assertEquals(idsedeconc, otra.getIdSedeConcurso());
        assertEquals(idsede,otra.getIdConcurso());
        assertEquals(idconc,otra.getIdSede());

        otra=new SedeConcursoExtendida();
        otra.setIdSedeConcurso(idsedeconc+1);
        assertNotEquals(sce, otra);
        boolean esSerializable=sce instanceof Serializable;
        assertTrue(esSerializable, "La clase SedeConcursoExtendida deberia implementar Serializable");
        calificacion += CALIF_CLASE_SEDECONCURSOEXT;
    }

    @Test
    void testEquiposSedeConcursoExtendida() {
        long ideqsedeconc=18L;
        long idsedeconc=100;
        long idequipo=10L;
        String nomSede="UAIE-UAZ";
        String nomConc="Gran Premio MX 2020";
        String nomEquipo="ilusionistas";
        String nom=String.format("%s en %s (%s)",nomEquipo,nomSede, nomConc);
        EquiposSedeConcursoExtendida esce= new EquiposSedeConcursoExtendida(ideqsedeconc,idequipo,idsedeconc);
        esce.setNombreSede(nomSede);
        esce.setNombreConcurso(nomConc);
        esce.setNombreEquipo(nomEquipo);
        assertEquals(ideqsedeconc,esce.getIdEquipoSedeConcurso());
        assertEquals(idsedeconc,esce.getIdSedeConcurso());
        assertEquals(idequipo,esce.getIdEquipo());
        assertEquals(nomEquipo,esce.getNombreEquipo());
        assertEquals(nomConc,esce.getNombreConcurso());
        assertEquals(nomSede,esce.getNombreSede());
        assertEquals(nom,esce.toString());

        EquiposSedeConcursoExtendida otra=new EquiposSedeConcursoExtendida(ideqsedeconc);
        otra.setIdEquipo(idsedeconc);
        otra.setIdSedeConcurso(idequipo);
        assertEquals(esce,otra,"La clase EquiposSedeConcursoExtendida deberia comparar dos objetos en base a idEquiposSedeConcurso");
        assertEquals(ideqsedeconc, otra.getIdEquipoSedeConcurso());
        assertEquals(idequipo,otra.getIdSedeConcurso());
        assertEquals(idsedeconc,otra.getIdEquipo());

        otra=new EquiposSedeConcursoExtendida();
        otra.setIdEquipoSedeConcurso(ideqsedeconc+1);
        assertNotEquals(esce, otra);
        boolean esSerializable=esce instanceof Serializable;
        assertTrue(esSerializable, "La clase EquiposSedeConcursoExtendida deberia implementar Serializable");
        calificacion += CALIF_CLASE_EQUIPOSSEDECONCURSOEXT;
    }

    @AfterAll
    static void afterAll() {
        System.out.printf("Puntos para Pruebas Clases de Entidad: %.2f/%.2f\n",calificacion,MAX_CALIF);
        TotalAcumulado.acumula(calificacion);
        System.out.printf("PUNTOS ACUMULADOS: %.2f\n", TotalAcumulado.getTotal());
    }
}
