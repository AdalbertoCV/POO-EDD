package registros.Computadoras;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaDinamica;
import estructuraslineales.auxiliares.Nodo;

import java.util.Date;
/**
 * Clase ControlComputadoras - Contiene todos los metodos para manipular el registro de las computadoras, usuarios, sesiones y aplicaciones
 * @author  Adalberto Cerrillo Vazquez - 4B
 * @version  1.0
 */
public class ControlComputadoras {
    protected ListaDinamica computadoras;
    protected ListaDinamica usuarios;

    public ControlComputadoras(){
        this.computadoras = new ListaDinamica();
        this.usuarios = new ListaDinamica();
    }

    /**
     * Imprime la lista completa de computadoras con todos sus datos
     */
    public void ImprimirListaComputadoras(){
        computadoras.ImprimirFormatoColumna();
    }

    /**
     * Imprime los datos de una computadora especificada como parametro
     * @param numComputadora es la computadora a imprimir
     */
    public void ImprimirDatosComputadora(int numComputadora){
        if (!computadoras.vacia()){
            Computadora computadora = null;
            Nodo iterador = computadoras.getPrimerNodo();
            while (iterador!=null){
                Computadora computadoraActual = (Computadora) iterador.getContenido();
                if (computadoraActual.getNumComputadora() == numComputadora){
                    computadora = computadoraActual;
                }
                iterador = iterador.getNodoDer();
            }
            if (computadora!=null){
                computadora.ImprimirCaracteristicasYAplicaciones();
            }
        }else{
            SalidaPorDefecto.consola("No hay computadoras.\n");
        }
    }

    /**
     * Agrega una aplicacion a una computadora especificada como parametro
     * @param numComputadora es la computadora a la que se le agregara la aplicacion
     * @param app es la aplicacion a agregar
     * @return regresa 0 si se pudo agregar y -1 si no se pudo
     */
    public int agregarAplicacion(int numComputadora, Aplicacion app){
        if (!computadoras.vacia()){
            Computadora computadora = null;
            Nodo iterador = computadoras.getPrimerNodo();
            while (iterador!=null){
                Computadora computadoraActual = (Computadora) iterador.getContenido();
                if (computadoraActual.getNumComputadora() == numComputadora){
                    computadora = computadoraActual;
                }
                iterador = iterador.getNodoDer();
            }
            if (computadora!=null){
                return computadora.agregarAplicacion(app);
            }else{
                return -1;
            }
        }else{
            return -1;
        }
    }

    /**
     * Elimina una aplicacion de una computadora especificada como parametro
     * @param numComputadora es la computadora a la cual se le eliminara la aplicacion
     * @param app es la aplicacion a eliminar
     * @return regresa la aplicacion eliminada si se elimino y null si no se elimino
     */
    public Object eliminarAplicacion(int numComputadora, Aplicacion app){
        if (!computadoras.vacia()){
            Computadora computadora = null;
            Nodo iterador = computadoras.getPrimerNodo();
            while (iterador!=null){
                Computadora computadoraActual = (Computadora) iterador.getContenido();
                if (computadoraActual.getNumComputadora() == numComputadora){
                    computadora = computadoraActual;
                }
                iterador = iterador.getNodoDer();
            }
            if (computadora!=null){
                return computadora.eliminarAplicacion(app);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * Registra una nueva sesion de un usuario especificado, en una computadora especificada, con fecha y hora
     * @param sesion es la sesion a agregar
     * @return regresa 0 si se pudo agregar y -1 si no
     */
    public int agregarSesion(SesionComputadora sesion, int numComputadora){
        if (!computadoras.vacia()){
            Computadora computadora = null;
            Nodo iterador = computadoras.getPrimerNodo();
            while (iterador!=null){
                Computadora computadoraActual = (Computadora) iterador.getContenido();
                if (computadoraActual.getNumComputadora() == numComputadora){
                    computadora = computadoraActual;
                }
                iterador = iterador.getNodoDer();
            }
            if (computadora!=null){
                return computadora.agregarSesion(sesion);
            }else{
                return -1;
            }
        }else{
            return -1;
        }
    }

    /**
     * se agrega una nueva computadora a la lista de computadoras registradas
     * @param computadora es la computadora a agregar
     * @return regresa un 0 si se registro correctamente y un -1 si no se pudo
     */
    public int darDeAltaComputadora(Computadora computadora){
        return computadoras.agregar(computadora);
    }

    /**
     * Se elimina una computadora si eciste dentro de la lista
     * @param computadora es la computadora a eliminar
     * @return regresa la computadora eliminada si se pudo eliminar y null si no se pudo
     */
    public Object darDeBajaComputadora(Computadora computadora){
        return computadoras.eliminar(computadora);
    }

    /**
     * agrega un usuario a la lista de usuarios
     * @param usuario es el usuario a agregar
     * @return regresa 0 si se pudo agregar y -1 si no
     */
    public int agregarUsuario(Usuario usuario){
        return usuarios.agregar(usuario);
    }

    /**
     * Elimina un usuario de la lista de usuarios
     * @param usuario es el usuario a eliminar
     * @return regresa el usaurio eliminado o null si no se pudo eliminar
     */
    public Object eliminarUsuario(Usuario usuario){
        return usuarios.eliminar(usuario);
    }

    /**
     * Busca todas las computadoras que tengan instalada la aplicacion indicada como parametro
     * @param app es la app a buscar
     * @return regresa una lista dinamica con las computadoras que contienen la app
     */
    public ListaDinamica buscarComputadorasConApp(Aplicacion app){
        if (!computadoras.vacia()){
            ListaDinamica computadorasConApp = new ListaDinamica();
            Nodo iterador = computadoras.getPrimerNodo();
            while (iterador!=null){
                Computadora computadoraActual = (Computadora) iterador.getContenido();
                Aplicacion buscar = (Aplicacion) computadoraActual.aplicaciones.buscar(app);
                if (buscar !=null){
                    computadorasConApp.agregar(computadoraActual);
                }
                iterador = iterador.getNodoDer();
            }
            return computadorasConApp;
        }else{
            return null;
        }
    }

    /**
     * Imprime los datos de las computadoras que sean aptas para correr la aplicacion indicada como parametro
     * @param app es la app a buscar
     */
    public void imprimirComputadorasAptas(Aplicacion app){
        if (!computadoras.vacia()){
            Nodo iterador = computadoras.getPrimerNodo();
            while (iterador!=null){
                Computadora computadoraActual = (Computadora) iterador.getContenido();
                Aplicacion buscar = (Aplicacion) computadoraActual.aplicaciones.buscar(app);
                if (computadoraActual.getRAM()>= app.getRamMinima()){
                    SalidaPorDefecto.consola(computadoraActual+"\n");
                }
                iterador = iterador.getNodoDer();
            }
        }
    }

    /**
     * Imprime a los usuarios que han usado la computadora indicada como parametro
     * @param numComputadora es la computadora a buscar
     */
    public void imprimirUsuariosQueHanUsado(int numComputadora){
        if (!computadoras.vacia()){
            Computadora computadora = null;
            Nodo iterador = computadoras.getPrimerNodo();
            while (iterador!=null){
                Computadora computadoraActual = (Computadora) iterador.getContenido();
                if (computadoraActual.getNumComputadora() == numComputadora){
                    computadora = computadoraActual;
                }
                iterador = iterador.getNodoDer();
            }
            iterador = computadora.sesionesUsuarios.getPrimerNodo();
            while(iterador!=null){
                SesionComputadora sesionActual = (SesionComputadora) iterador.getContenido();
                SalidaPorDefecto.consola(""+ sesionActual.getUsuario()+"\n");
                iterador = iterador.getNodoDer();
            }
        }
    }

    /**
     * Imprime la fecha y hora en que el usuario indicado utilizo la computadora indicada
     * @param usuario es el usuario a buscar
     * @param numComputadora es la computadora a buscar
     */
    public void imprimirFechasyHorasUso(Usuario usuario, int numComputadora){
        if (!computadoras.vacia()){
            Computadora computadora = null;
            Nodo iterador = computadoras.getPrimerNodo();
            while (iterador!=null){
                Computadora computadoraActual = (Computadora) iterador.getContenido();
                if (computadoraActual.getNumComputadora() == numComputadora){
                    computadora = computadoraActual;
                }
                iterador = iterador.getNodoDer();
            }
            iterador = computadora.sesionesUsuarios.getPrimerNodo();
            while(iterador!=null){
                SesionComputadora sesionActual = (SesionComputadora) iterador.getContenido();
                if (sesionActual.getUsuario().toString().equalsIgnoreCase(usuario.toString())){
                    SalidaPorDefecto.consola("Centro de computo: "+ computadora.getCentroComputo() + " Inicio Sesion: " + sesionActual.getFechaInicio() + "  Final Sesion: "+ sesionActual.getFechaFin()+"\n");
                }
                iterador = iterador.getNodoDer();
            }
        }
    }

    /**
     * Imprime todas las aplicaciones usadas por el usuario en la computadora especificada en la fecha especificada
     * @param usuario es el usuario a buscar
     * @param numComputadora es la computadora que se uso
     * @param fecha es la fecha a buscar
     */
    public void imprimirAplicacionesAbiertas(Usuario usuario,int numComputadora, Date fecha){
        if (!computadoras.vacia()){
            Computadora computadora = null;
            Nodo iterador = computadoras.getPrimerNodo();
            while (iterador!=null){
                Computadora computadoraActual = (Computadora) iterador.getContenido();
                if (computadoraActual.getNumComputadora() == numComputadora){
                    computadora = computadoraActual;
                }
                iterador = iterador.getNodoDer();
            }
            iterador = computadora.sesionesUsuarios.getPrimerNodo();
            while(iterador!=null){
                SesionComputadora sesionActual = (SesionComputadora) iterador.getContenido();
                if (sesionActual.getUsuario().toString().equalsIgnoreCase(usuario.toString())){
                    if (sesionActual.getFechaInicio().compareTo(fecha) == 0){
                        ListaDinamica apps = sesionActual.getAplicacionesUsadas();
                        apps.imprimir();
                    }
                }
                iterador = iterador.getNodoDer();
            }
        }
    }

    /**
     * Imprime en pantalla los usuarios que no usan los centros de computo
     */
    public void imprimirUsuariosSinActividad(){
        if (!usuarios.vacia()){
            ListaDinamica usuariosActivos = new ListaDinamica();
            Nodo iteradorUsuarios = usuarios.getPrimerNodo();
            while (iteradorUsuarios!=null){
                Usuario usuarioActual = (Usuario) iteradorUsuarios.getContenido();
                Nodo iteradorComputadoras = computadoras.getPrimerNodo();
                while (iteradorComputadoras !=null){
                    Computadora computadoraActual = (Computadora) iteradorComputadoras.getContenido();
                    Nodo iteradorSesiones = computadoraActual.sesionesUsuarios.getPrimerNodo();
                    while (iteradorSesiones!= null){
                        SesionComputadora sesionActual = (SesionComputadora) iteradorSesiones.getContenido();
                        if (sesionActual.getUsuario().toString().equalsIgnoreCase(usuarioActual.toString())){
                            usuariosActivos.agregar(sesionActual.getUsuario());
                        }
                        iteradorSesiones = iteradorSesiones.getNodoDer();
                    }
                    iteradorComputadoras = iteradorComputadoras.getNodoDer();
                }
                iteradorUsuarios = iteradorUsuarios.getNodoDer();
            }
            iteradorUsuarios = usuarios.getPrimerNodo();
            while (iteradorUsuarios!=null){
                Usuario actual = (Usuario) iteradorUsuarios.getContenido();
                Usuario buscar = (Usuario) usuariosActivos.buscar(actual);
                if (buscar == null){
                    SalidaPorDefecto.consola(actual.toString()+"\n");
                }
                iteradorUsuarios = iteradorUsuarios.getNodoDer();
            }
        }
    }
}
