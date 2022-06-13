package registros.Computadoras;

import estructuraslineales.ListaDinamica;

import java.util.Date;

/**
 * Clase SesionComputadora - Contiene toda la informacion de un usuario, fecha inicio y fecha final en que uso una computadora
 * @author  Adalberto Cerrillo Vazquez - 4B
 * @version  1.0
 */
public class SesionComputadora {
    protected Usuario usuario;
    protected Date fechaInicio;
    protected Date fechaFin;
    protected ListaDinamica aplicacionesUsadas;

    public SesionComputadora(Usuario usuario, Date fechaInicio, Date fechaFin) {
        this.usuario = usuario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.aplicacionesUsadas = new ListaDinamica();
    }

    /**
     * Agreamos una aplicacion a la lista de las aplicaciones usadas por el usuario actual
     * @param app es la aplicacion a usar
     * @return regresa un 0 si se registro el uso correctamente y -1 si no fue posible
     */
    public int usarAplicacion(Aplicacion app){
        return this.aplicacionesUsadas.agregar(app);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public ListaDinamica getAplicacionesUsadas() {
        return aplicacionesUsadas;
    }
}
