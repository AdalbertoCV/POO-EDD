package poo2.progs.servicios;


import poo2.progs.interfaces.DaoWeb;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.*;
import java.util.List;
import java.util.Set;

/**
 * Esta clase implementa de manera abstracta la interface DaoWeb
 * para poder ser usado de manera generica por los diversos servicios
 * REST
 * @param <T>  Clase de Entidad con la que se trabajara
 */
public abstract class RESTAbstracto<T> implements DaoWeb<T> {
    private final Class<T> tipoClaseEntidad;

    /**
     * Constructor de la clase que recibe el tipo de clase de Entidad
     * para la cual se proporciona el servicio REST
     * @param tipoEntidad Clase que representa al tipo de clase entidad a manejar
     */
    public RESTAbstracto(Class<T> tipoEntidad) {
        this.tipoClaseEntidad = tipoEntidad;
    }

    /**
     * Este metodo debe ser implementado por los REST a generar
     * y debe devolver el EntityManager que contiene
     * @return  El objeto EntityManager que utiliza el REST
     */
    protected abstract EntityManager getEntityManager();

    /**
     * Este metodo se usa para limpiar el cache y asegurarse que las pruebas son realizadas correctamente
     * NO DEBERIA HACERSE ESTO EN PRODUCCION
     */
    private void limpiaCache() {
        getEntityManager().getEntityManagerFactory().getCache().evictAll();
    }

    /**
     * Este metodo es para uso en depuracion y permite determinar
     * las violaciones a las restricciones que tuviera un objeto
     * a ser guardado o actualizado
     * @param dato  Objeto de la clase de entidad a validar
     */
    private boolean validaDatos(T dato) {
        ValidatorFactory factory= Validation.buildDefaultValidatorFactory();
        Validator validator=factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations=validator.validate(dato);
        if (constraintViolations.size()>0) {
            for (ConstraintViolation<T> error : constraintViolations) {
                String causaError = error.getMessage();
                String enAtributo = error.getPropertyPath().toString();
                System.err.printf("Error en el atributo %s: %s\n", enAtributo, causaError);
            }
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public T get(Object id) {
        limpiaCache();
        return getEntityManager().find(tipoClaseEntidad,id);
    }

    @Override
    public List<T> getAll() {
        limpiaCache();
        CriteriaQuery<T> cq=getEntityManager().getCriteriaBuilder().createQuery(tipoClaseEntidad);
        cq.select(cq.from(tipoClaseEntidad));
        return getEntityManager().createQuery(cq).getResultList();
    }


    @Override
    public String save(Object id, T dato) {
        String resultado="false";
        boolean datosValidos=validaDatos(dato);
        if (datosValidos) {
            // Siguiente linea es para que las pruebas funcionen correctamente
            limpiaCache();
            try {
                if (get(id)==null) {
                    getEntityManager().persist(dato);
                    resultado = "true";
                }

            }
            catch (Exception ignored) {
                // En caso de requerir depurar descomente la siguiente linea
                ignored.printStackTrace();
            }
        }
        return resultado;
    }

    @Override
    public String update(Object id, T dato) {
        String resultado="false";
        boolean datosValidos=validaDatos(dato);
        if (datosValidos) {
            // Siguiente linea es para que las pruebas funcionen correctamente
            limpiaCache();
            try {
                if (get(id)!=null) {
                    getEntityManager().merge(dato);
                    resultado="true";
                }
            }
            catch (Exception ignored) {
                // En caso de requerir depurar descomente la siguiente linea
                //ignored.printStackTrace();
            }
        }
        return resultado;
    }

    @Override
    public String delete(Object id) {
        String resultado="false";
        EntityManager em=getEntityManager();
        try {
            // Siguiente linea es para que las pruebas funcionen correctamente
            limpiaCache();
            T objeto= em.find(tipoClaseEntidad,id);
            if (objeto!=null) {
                em.remove(objeto);
                resultado="true";
            }
        }
        catch (Exception ignored) {
            // En caso de requerir depurar descomente la siguiente linea
            ignored.printStackTrace();
        }
        return resultado;
    }
}
