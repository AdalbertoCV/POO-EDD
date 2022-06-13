package pruebas.PruebasComputadoras;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaDinamica;
import registros.Computadoras.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PruebaControlComputadoras {
    public static void main (String args[]) throws ParseException {
        SalidaPorDefecto.consola("---Creando computadoras, Aplicaciones,  usuarios y sus sesiones---\n");
        ListaDinamica appsPC1 = new ListaDinamica();
        ListaDinamica appsPC2 = new ListaDinamica();
        ListaDinamica appsPC3 = new ListaDinamica();
        ListaDinamica appsPC4 = new ListaDinamica();
        Aplicacion chrome = new Aplicacion("crhome", "imagen1.jpg","Google","Version 1.5",1.0);
        Aplicacion word = new Aplicacion("word","logo1.png","Microsoft","Version 8", 1.5 );
        Aplicacion powerPoint = new Aplicacion("PowerPoint", "logo2.png", "Microsoft", "Version 8", 1.8);
        Aplicacion Intellig = new Aplicacion("Intellij", "logo3.png", "JetBrains","Version 2021", 4.0);
        Aplicacion pyCharm = new Aplicacion("pyCharm", "logo4.png", "JetBrains","Version 2021", 4.0);
        appsPC1.agregar(chrome);
        appsPC1.agregar(word);
        appsPC1.agregar(powerPoint);
        appsPC2.agregar(word);
        appsPC2.agregar(powerPoint);
        appsPC3.agregar(chrome);
        appsPC3.agregar(pyCharm);
        appsPC4.agregar(word);
        appsPC4.agregar(powerPoint);
        appsPC4.agregar(chrome);
        appsPC4.agregar(Intellig);
        Computadora pc1 = new Computadora(1,"Centro1",4.0,60.0,"Intel i5","HP",appsPC1);
        Computadora pc2 = new Computadora(2,"Centro1",2.0,60.0,"Intel i4","Acer",appsPC2);
        Computadora pc3 = new Computadora(3,"Centro2",1.0,60.0,"Intel Celeron","Dell",appsPC3);
        Computadora pc4 = new Computadora(4,"Centro4",4.0,60.0,"Intel i7","Alienware",appsPC4);

        Usuario usuario1 = new Usuario("Adal", "Cerrillo", 19, "Estudiante");
        Usuario usuario2 = new Usuario("Luis", "Perez", 36, "Profesor");
        Usuario usuario3 = new Usuario("Jesus", "Gonzales", 19, "Estudiante");
        Usuario usuario4 = new Usuario("Miles", "Duron", 35, "Profesor");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ControlComputadoras control = new ControlComputadoras();
        SalidaPorDefecto.consola("Prueba dar de alta computadora (pc1): " + control.darDeAltaComputadora(pc1)+"\n");
        SalidaPorDefecto.consola("Prueba dar de alta computadora (pc2): " + control.darDeAltaComputadora(pc2)+"\n");
        SalidaPorDefecto.consola("Prueba dar de alta computadora (pc3): " + control.darDeAltaComputadora(pc3)+"\n");
        SalidaPorDefecto.consola("Prueba dar de alta computadora (pc4): " + control.darDeAltaComputadora(pc4)+"\n");

        SalidaPorDefecto.consola("Prueba dar de alta usuario1: " + control.agregarUsuario(usuario1)+ "\n");
        SalidaPorDefecto.consola("Prueba dar de alta usuario2: " + control.agregarUsuario(usuario2)+ "\n");
        SalidaPorDefecto.consola("Prueba dar de alta usuario3: " + control.agregarUsuario(usuario3)+ "\n");
        SalidaPorDefecto.consola("Prueba dar de alta usuario4: " + control.agregarUsuario(usuario4)+ "\n");

        SesionComputadora sesionU1 = new SesionComputadora(usuario1,sdf.parse("2021-09-15 15:03:23"),sdf.parse("2021-09-15 16:03:23"));
        SesionComputadora sesionU2 = new SesionComputadora(usuario1,sdf.parse("2021-10-15 15:00:00"),sdf.parse("2021-10-15 16:00:00"));
        sesionU2.usarAplicacion(Intellig);
        SesionComputadora sesionU3 = new SesionComputadora(usuario3,sdf.parse("2021-11-10 12:00:00"),sdf.parse("2021-11-10 14:00:00"));
        sesionU3.usarAplicacion(word);
        sesionU3.usarAplicacion(powerPoint);
        SesionComputadora sesionU4 = new SesionComputadora(usuario3,sdf.parse("2021-12-15 15:30:00"),sdf.parse("2021-12-15 16:00:00"));
        sesionU4.usarAplicacion(chrome);
        SesionComputadora sesionU5 = new SesionComputadora(usuario2,sdf.parse("2021-12-16 15:30:00"),sdf.parse("2021-12-16 16:00:00"));
        sesionU5.usarAplicacion(chrome);
        SalidaPorDefecto.consola("Registrando Sesion usuario1, pc1: " + control.agregarSesion(sesionU1,1)+ "\n");
        SalidaPorDefecto.consola("Registrando Sesion usuario1, pc4: " + control.agregarSesion(sesionU2,4)+ "\n");
        SalidaPorDefecto.consola("Registrando Sesion usuario3, pc2: " + control.agregarSesion(sesionU3,2)+ "\n");
        SalidaPorDefecto.consola("Registrando Sesion usuario3, pc3: " + control.agregarSesion(sesionU4,3)+ "\n");
        SalidaPorDefecto.consola("Registrando Sesion usuario2, pc3: " + control.agregarSesion(sesionU5,3)+ "\n");
        SalidaPorDefecto.consola("\n\n");

        SalidaPorDefecto.consola("Prueba imprimir computadoras con todos sus datos: \n");
        control.ImprimirListaComputadoras();
        SalidaPorDefecto.consola("\n\n");
        SalidaPorDefecto.consola("Prueba imprimir datos de pc3: \n");
        control.ImprimirDatosComputadora(3);
        SalidaPorDefecto.consola("\n\n");

        SalidaPorDefecto.consola("Creando nueva aplicacion a agregar: \n");
        Aplicacion excel = new Aplicacion("Excel","excel.png","Microsoft","Version 8",1.5);
        SalidaPorDefecto.consola(excel+"\n");
        SalidaPorDefecto.consola("Prueba agregar aplicacion (Excel) a pc2: " + control.agregarAplicacion(2,excel)+"\n");
        pc2.ImprimirCaracteristicasYAplicaciones();
        SalidaPorDefecto.consola("Prueba eliminar aplicacion (word) de pc2: "+ control.eliminarAplicacion(2,word)+"\n");
        pc2.ImprimirCaracteristicasYAplicaciones();
        SalidaPorDefecto.consola("Prueba dar de baja pc1: " + control.darDeBajaComputadora(pc1)+"\n");
        SalidaPorDefecto.consola("---Imprimiendo de nuevo la lista de computadoras---\n");
        control.ImprimirListaComputadoras();
        SalidaPorDefecto.consola("\n\n");

        SalidaPorDefecto.consola("Prueba Buscar todas las computadoras que tenga instalado chrome: \n");
        ListaDinamica conChrome = control.buscarComputadorasConApp(chrome);
        conChrome.ImprimirFormatoColumna();
        SalidaPorDefecto.consola("\n\n");

        SalidaPorDefecto.consola("--Imprimiendo apps que tengan suficiente raam para correr intellij: \n");
        control.imprimirComputadorasAptas(Intellig);
        SalidaPorDefecto.consola("\n\n");

        SalidaPorDefecto.consola("Prueba imprimir usuarios que han usado la pc3:\n");
        control.imprimirUsuariosQueHanUsado(3);
        SalidaPorDefecto.consola("\n\n");

        SalidaPorDefecto.consola("Prueba imprimir dias y horas en que el usuario 3 ha usado la pc2 \n");
        control.imprimirFechasyHorasUso(usuario3,2);
        SalidaPorDefecto.consola("\n\n");

        SalidaPorDefecto.consola("Prueba imprimir aplicaciones abiertas por el usuario3 el 2021-11-10:\n");
        control.imprimirAplicacionesAbiertas(usuario3,2,sdf.parse("2021-11-10 12:00:00"));
        SalidaPorDefecto.consola("\n\n");

        SalidaPorDefecto.consola("Prueba imprimir usuarios que no usan el centro de computo: \n");
        control.imprimirUsuariosSinActividad();
        SalidaPorDefecto.consola("\n\n");
    }
}
