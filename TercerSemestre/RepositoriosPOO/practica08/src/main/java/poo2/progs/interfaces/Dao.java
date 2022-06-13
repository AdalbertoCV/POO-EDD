package poo2.progs.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Interface que describe las operaciones basicas a realizar sobre
 * una la tabla que representa una clase de entidad
 * @param <T> T representa la clase de entidad relacionada a la tabla
 */
public interface Dao<T> {
    /**
     * Este metodo regresa el Optional asociado a una llave primaria
     * @param id Objeto que contiene la llave primaria del objeto deseado
     * @return Objeto Optional asociado a la llave primaria (vacio si no existe)
     */
    Optional<T> get(Object id);

    /**
     * Este metodo devuelve una List con todos los objetos de la tabla asociada
     * a la clase de entidad
     * @return List que contiene todos los objetos de la tabla
     */
    List<T> getAll();

    /**
     * Este metodo agrega un objeto en la tabla asociada a la clase de entidad
     * @param dato Objeto con la informacion a agregar
     * @return true si se puede agregar, false de si no se puede
     */
    boolean save(T dato);

    /**
     * Este metodo actualiza un objeto en la tabla asociada a la clase de entidad
     * @param dato Objeto con la informacion a actualizar
     * @return true si se puede actualizar, false de si no se puede
     */
    boolean update(T dato);

    /**
     * Este metodo elimina un objeto en la tabla asociada a la clase de entidad
     * @param id Objeto que contiene la llave primaria del objeto a eliminar
     * @return true si se puede eliminar, false de si no se puede
     */
    boolean delete(Object id);
}