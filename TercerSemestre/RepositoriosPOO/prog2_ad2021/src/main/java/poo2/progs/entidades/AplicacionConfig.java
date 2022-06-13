package poo2.progs.servicios;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * Esta clase se encarga de dar de alta los servicios que contiene el
 * proyecto indicando con @ApplicationPath la ruta a seguir para llegar
 * a los servicios
 */

// Glassfish esta corriendo en una computadora (por ejemplo www.uaz.edu.mx)
// y en un puerto en particular (8080)
//  http://www.uaz.edu.mx:8080/RESTConcursos-37183876  <== URL_BASE

// http://www.uaz.edu.mx:8080/RESTConcursos-37183876/servicios
// URLBASE de los servicios

@ApplicationPath("servicios")
public class AplicacionConfig extends Application {

    /**
     * Este metodo devuelve  las clases de servicios REST que se
     * proporcionaran por este proyecto
     * @return Set de objetos Class que representan los servicios disponibles
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> recursos = new java.util.HashSet<>();
        agregaRecursosREST(recursos);
        return recursos;
    }

    /**
     * Este metodo agrega al argumento recibido las clases de servicios
     * REST que estaran disponibles en el proyecto
     * @param recursos Set de objetos Class donde deben agregarse las clases de servicio
     */
    private void agregaRecursosREST(Set<Class<?>> recursos) {
        recursos.add(InstitucionREST.class);
        recursos.add(MunicipioREST.class);
    }

}
