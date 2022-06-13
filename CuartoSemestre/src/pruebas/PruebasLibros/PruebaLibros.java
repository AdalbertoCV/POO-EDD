package pruebas;

import entradasalida.SalidaPorDefecto;
import enumerados.TipoOrden;
import estructuraslineales.ListaEstaticaOrdenada;
import registros.Libros.MenuLibros;
import registros.Libros.Subtermino;
import registros.Libros.Termino;

public class PruebaLibros {
    public static void main(String args[]){
        SalidaPorDefecto.consola("...Pruebas de el sistema de libros... \n");
        String nombre1 = "atalaya";
        String nombre2 = "arte";
        String nombre3 = "Bosque";
        String nombre4 = "Camino";
        String nombre5 = "Capilla";
        String nombreSub1 = "Bosque templado";
        String nombreSub2 = "Carretera";
        String nombreSub3 = "arte barroco";
        String def1 = "Torre construida en un lugar alto para vigilar gran extensión de terreno";
        String def2 = "Cosa de belleza subjetiva realizada por el hombre";
        String def3 = "ecosistema natural con muchos arboles";
        String def4 = "Via para llegar a un destino";
        String def5 = "Lugar reservado al culto religioso";
        String defSub1 = "ecosistema boscoso de clima mayormente templado";
        String defSub2 = "Tipo de camino que comunica pueblos o ciudades";
        String defSub3 = "Tipo de arte comprendida en el periodo barroco";
        String rango1 = ("1");
        String rango2 = ("2");
        String rango3 = ("2");
        String rango4 = ("3-4");
        String rango5 = ("4");
        String rangoSub1 = ("5");
        String rangoSub2 = ("5");
        String rangoSub3 = ("6-8");

        SalidaPorDefecto.consola("...Creando los Terminos y Subterminos de prueba... \n");
        Termino termino1 = new Termino(nombre1,rango1,def1);
        Termino termino2 = new Termino(nombre2,rango2,def2);
        Termino termino3 = new Termino(nombre3,rango3, def3);
        Termino termino4 = new Termino(nombre4,rango4, def4);
        Termino termino5 = new Termino(nombre5,rango5, def5);
        Subtermino subtermino1 = new Subtermino(nombreSub1, rangoSub1, defSub1);
        Subtermino subtermino2 = new Subtermino(nombreSub2, rangoSub2, defSub2);
        Subtermino subtermino3 = new Subtermino(nombreSub3, rangoSub3, defSub3);
        SalidaPorDefecto.consola("...Terminos y subterminos de prueba creados... \n");

        SalidaPorDefecto.consola("...Agregando los Terminos y subterminos a sus listas... \n");
        ListaEstaticaOrdenada listaTerminos = new ListaEstaticaOrdenada(6, TipoOrden.INCR);
        ListaEstaticaOrdenada listaSubTerminos = new ListaEstaticaOrdenada(6, TipoOrden.INCR);

        MenuLibros menu = new MenuLibros();

        SalidaPorDefecto.consola("Prueba agregar termino: "+ menu.agregarTermino(listaTerminos, termino1)+ "\n");
        SalidaPorDefecto.consola("Prueba agregar termino: "+ menu.agregarTermino(listaTerminos, termino2)+ "\n");
        SalidaPorDefecto.consola("Prueba agregar termino: "+ menu.agregarTermino(listaTerminos, termino3)+ "\n");
        SalidaPorDefecto.consola("Prueba agregar termino: "+ menu.agregarTermino(listaTerminos, termino4)+ "\n");
        SalidaPorDefecto.consola("Prueba agregar termino: "+ menu.agregarTermino(listaTerminos, termino5)+ "\n");

        SalidaPorDefecto.consola("Prueba agregar subtermino: "+menu.agregarSubTermino(listaSubTerminos, subtermino1)+"\n");
        SalidaPorDefecto.consola("Prueba agregar subtermino: "+menu.agregarSubTermino(listaSubTerminos, subtermino2)+"\n");
        SalidaPorDefecto.consola("Prueba agregar subtermino: "+menu.agregarSubTermino(listaSubTerminos, subtermino3)+"\n");

        SalidaPorDefecto.consola("...Terminos y subterminos agregados a sus listas... \n");

        SalidaPorDefecto.consola("Prueba consultar termino por nombre: Bosque" + "\n");
        menu.ConsultarTermino(listaTerminos, "Bosque");
        SalidaPorDefecto.consola("Prueba consultar subtermino por nombre: Bosque Templado \n");
        menu.ConsultarSubTermino(listaSubTerminos,"Bosque Templado");

        SalidaPorDefecto.consola("Prueba listar Terminos: \n");
        menu.Listar(listaTerminos);
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba listar subterminos: \n");
        menu.Listar(listaSubTerminos);
        SalidaPorDefecto.consola("\n");

        SalidaPorDefecto.consola("Prueba listar por rango (B-C): \n");
        menu.ListarPorRango(listaTerminos, "B", "C");


    }
}
