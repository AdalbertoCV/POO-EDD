package pruebas.PruebasEstructurasnolineales;

import entradasalida.SalidaPorDefecto;
import estructurasnolineales.ArbolIndices;

import java.io.IOException;

public class PruebaArbolIndices {
    public static void main (String args[]) throws IOException {
        ArbolIndices arbol = new ArbolIndices("src\\estructurasnolineales\\datos\\orders.txt", 0); // llave primaria
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Pruebas tabla ordenes (llave primaria): \n");
        SalidaPorDefecto.consola("Buscando indice 2381: " +"\n");
        arbol.buscarIndice("2381");
        SalidaPorDefecto.consola("Eliminando indice 2381: " + arbol.eliminarIndice("2381")+"\n");
        SalidaPorDefecto.consola("Prueba agregando nuevos datos: \n");
        SalidaPorDefecto.consola("Datos: 2666\t40:12.2\tonline\t105\t0\t78298.6\t198\t  \n");
        SalidaPorDefecto.consola("Prueba agregando nuevos datos: \n");
        SalidaPorDefecto.consola("Datos: 2667\t42:12.2\tonline\t131\t0\t79298.6\t128\t  \n");
        arbol.agregarDatos("2666\t40:12.2\tonline\t105\t0\t78298.6\t198\t");
        arbol.agregarDatos("2667\t42:12.2\tonline\t131\t0\t79298.6\t128\t");
        SalidaPorDefecto.consola("Buscando valor agregado (2666): " + "\n");
        arbol.buscarIndice("2666");
        SalidaPorDefecto.consola("Buscando valor agregado (2667): "+ "\n");
        arbol.buscarIndice("2667");

        SalidaPorDefecto.consola("\n\n Pruebas tabla clientes (valores repetidos): \n");
        ArbolIndices arbol2 = new ArbolIndices("src\\estructurasnolineales\\datos\\costumer.txt", 13);
        SalidaPorDefecto.consola("Buscar genero M: " +  "\n");
        arbol2.buscarIndice("M");
        SalidaPorDefecto.consola("\n\n\n");
        SalidaPorDefecto.consola("Prueba agregando nuevos datos: \n");
        SalidaPorDefecto.consola("Datos: 666\tAdal\tCerrillo\toracle.sql.STRUCT@212c0ba\t[+1 717 123 4739]\tus\tAMERICA\t2300\tAHM@PYRRHULOXIA.COM\t149\toracle.sql.STRUCT@3393a9d6\t23/06/1973\tmarried\tM\tB: 30,000 - 49,999 \n");
        arbol2.agregarDatos("666\tAdal\tCerrillo\toracle.sql.STRUCT@212c0ba\t[+1 717 123 4739]\tus\tAMERICA\t2300\tAHM@PYRRHULOXIA.COM\t149\toracle.sql.STRUCT@3393a9d6\t23/06/1973\tmarried\tM\tB: 30,000 - 49,999");
        SalidaPorDefecto.consola("\n\n\n");
        SalidaPorDefecto.consola("Buscar genero F: " + "\n");
        arbol2.buscarIndice("F");
    }
}
