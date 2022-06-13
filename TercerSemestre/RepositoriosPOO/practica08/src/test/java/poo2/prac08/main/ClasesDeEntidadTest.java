package poo2.prac08.main;

import poo2.progs.entidades.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;

public class ClasesDeEntidadTest {
    private final static double CALIF_CLASE_INSTITUCION=3.5;
    private final static double CALIF_CLASE_MUNICIPIO=2.5;
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


    @AfterAll
    static void afterAll() {
        System.out.printf("Puntos para Pruebas Clases de Entidad: %.2f/%.2f\n",calificacion,MAX_CALIF);
        TotalAcumulado.acumula(calificacion);
        System.out.printf("PUNTOS ACUMULADOS: %.2f\n", TotalAcumulado.getTotal());
    }
}
