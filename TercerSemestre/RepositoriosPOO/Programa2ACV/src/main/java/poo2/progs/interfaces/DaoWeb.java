package poo2.progs.interfaces;

import java.util.List;

/**
 * Interface para especificar las operaciones que deben realizar los servicios REST
 * @param <T>  T representa el tipo de Entidad con la cual trabaja el servicio REST
 */
public interface DaoWeb<T> {
    /**
     * Este metodo devuelve un objeto con la llave primaria indicada
     * @param id  Llave primaria del objeto a obtener
     * @return  Objeto de la clase entidad correspondiente con la llave primaria indicada
     */
    T get(Object id);

    /**
     * Este metodo devuelve todos los objetos de la clase indicada
     * @return  Lista de objetos de la clase entidad correspondiente
     */
    List<T> getAll();

    /**
     * Este metodo agrega un nuevo registro a la tabla que corresponde
     * @param id Llave primaria del objeto a modificar
     * @param dato  Objeto de la clase de entidad con la informacion a agregar
     * @return  "true" o "false" dependiendo de si se pudo agregar o no
     */
    String save(Object id, T dato);


    /**
     * Este metodo actualiza un registro de la tabla que corresponde
     * @param id Llave primaria del objeto a modificar
     * @param dato  Objeto de la clase de Entidad con la informacion a actualizar
     * @return "true" o "false" dependiendo de si se pudo actualizar o no
     */
    String update(Object id, T dato);

    /**
     * Este metodo elimina un registro de la tabla que corresponde
     * @param id  Llave primaria del objeto a modificar
     * @return  "true" o "false" dependiendo de si se pudo eliminar o no
     */
    String delete(Object id);
}