package pruebas.PruebasPintores;

import entradasalida.SalidaPorDefecto;
import registros.ControlPintores.Actividad;
import registros.ControlPintores.ControlPintores;
import registros.ControlPintores.Pintor;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PruebaControlPintores {
    public static void main (String args[]) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        ControlPintores cp = new ControlPintores(10);
        Pintor pintor1 = new Pintor("Adal", "Cerrillo", 19, formato.parse("23/04/2002"),"Calle Margaritas","ABC2323","Ingeniero");
        Pintor pintor2 = new Pintor("Alexia","Hernandez",20,formato.parse("15/01/2002"),"Calle magnolias","HMA382838","Ingeniero");
        Pintor pintor3 = new Pintor("Judith","Trejo",19,formato.parse("23/10/2002"),"Calle Cuarto de Milla","JTS382838","Doctor");
        Pintor pintor4 = new Pintor("Armando","Garay",20,formato.parse("15/06/2001"),"Calle lilis","AGAHSHS8","Ingeniero");
        Pintor pintor5 = new Pintor("Diego","Garcia",19,formato.parse("08/03/2002"),"Calle rosas","GGM34RFR","Ingeniero");

        cp.agregarPintor(pintor1);
        cp.agregarPintor(pintor2);
        cp.agregarPintor(pintor3);
        cp.agregarPintor(pintor4);
        cp.agregarPintor(pintor5);

        SalidaPorDefecto.consola("Agregando Actividades a los pintores.....\n");
        //Agregando actividades a los pintores
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor1.getRFC() + ": "+ cp.agregarActividad(pintor1.getRFC(),formato.parse("11/03/2001"),new Actividad("Paris"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor1.getRFC() + ": "+ cp.agregarActividad(pintor1.getRFC(),formato.parse("11/03/2002"),new Actividad("ExpoArte2002","Madrid", formato.parse("11/03/2002"),500))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor3.getRFC() + ": "+ cp.agregarActividad(pintor3.getRFC(),formato.parse("10/07/2002"),new Actividad("ExpoBarroco2002","Brasil", formato.parse("10/07/2002"),800))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor1.getRFC() + ": "+ cp.agregarActividad(pintor1.getRFC(),formato.parse("10/07/2002"),new Actividad("ExpoClasica2002","Brasil", formato.parse("10/07/2002"),800))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("10/07/2002"),new Actividad("ExpoNaturalismo2002","Brasil", formato.parse("10/07/2002"),800))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor4.getRFC() + ": "+ cp.agregarActividad(pintor4.getRFC(),formato.parse("10/07/2002"),new Actividad("ExpoRomanticismo2002","Brasil", formato.parse("10/07/2002"),800))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor5.getRFC() + ": "+ cp.agregarActividad(pintor5.getRFC(),formato.parse("10/07/2002"),new Actividad("ExpoCubismo2002","Brasil", formato.parse("10/07/2002"),800))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor5.getRFC() + ": "+ cp.agregarActividad(pintor5.getRFC(),formato.parse("10/09/2002"),new Actividad("ExpoMinimalismo2002","España", formato.parse("10/09/2002"),1000))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("30/03/2000"),new Actividad("CDMX"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2002"),new Actividad("Flores de Mayo", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("16/05/2004"),new Actividad())+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor3.getRFC() + ": "+ cp.agregarActividad(pintor3.getRFC(),formato.parse("27/03/2015"),new Actividad("Paris", formato.parse("27/03/2015")))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor3.getRFC() + ": "+ cp.agregarActividad(pintor3.getRFC(),formato.parse("11/03/2014"),new Actividad())+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor1.getRFC() + ": "+ cp.agregarActividad(pintor1.getRFC(),formato.parse("20/07/2020"),new Actividad())+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor1.getRFC() + ": "+ cp.agregarActividad(pintor1.getRFC(),formato.parse("19/02/2019"),new Actividad())+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor5.getRFC() + ": "+ cp.agregarActividad(pintor5.getRFC(),formato.parse("11/03/2018"),new Actividad("Los Angeles", formato.parse("11/03/2018")))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor5.getRFC() + ": "+ cp.agregarActividad(pintor5.getRFC(),formato.parse("10/04/2018"),new Actividad("Luna llena", "Gotico"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor4.getRFC() + ": "+ cp.agregarActividad(pintor4.getRFC(),formato.parse("01/09/2013"),new Actividad("Los Angeles"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor4.getRFC() + ": "+ cp.agregarActividad(pintor4.getRFC(),formato.parse("09/01/2013"),new Actividad("ExpoBarroco2013","CDMX",formato.parse("09/01/2013"),700))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor4.getRFC() + ": "+ cp.agregarActividad(pintor4.getRFC(),formato.parse("17/06/2014"),new Actividad())+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2003"),new Actividad("Otoño", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2004"),new Actividad("Frutas", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2005"),new Actividad("Paisaje", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2006"),new Actividad("Montaña", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2007"),new Actividad("Arboledas", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2008"),new Actividad("Mares", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2009"),new Actividad("Primaveras", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2010"),new Actividad("Gardenias", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2011"),new Actividad("Margaritas", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2012"),new Actividad("noche buena", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2013"),new Actividad("lilis", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2014"),new Actividad("violetas", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2015"),new Actividad("Licoris radiata", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2016"),new Actividad("Mandarinas", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2017"),new Actividad("Hierba", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2018"),new Actividad("flores de abril", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2019"),new Actividad("invierno", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/08/2020"),new Actividad("nieve", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/09/2020"),new Actividad("olas", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/10/2020"),new Actividad("luciernagas", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/10/2001"),new Actividad("Cigarras", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("14/10/2000"),new Actividad("Playa", "Naturalista"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor2.getRFC() + ": "+ cp.agregarActividad(pintor2.getRFC(),formato.parse("19/10/2005"),new Actividad())+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor1.getRFC() + ": "+ cp.agregarActividad(pintor1.getRFC(),formato.parse("19/10/2005"),new Actividad("España"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor3.getRFC() + ": "+ cp.agregarActividad(pintor3.getRFC(),formato.parse("19/10/2005"),new Actividad("España"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor5.getRFC() + ": "+ cp.agregarActividad(pintor5.getRFC(),formato.parse("19/10/2005"),new Actividad("Brasil"))+"\n");
        SalidaPorDefecto.consola("Prueba Agregar Actividad a Pintor "+ pintor1.getRFC() + ": "+ cp.agregarActividad(pintor1.getRFC(),formato.parse("19/10/2009"),new Actividad("Dubai"))+"\n");

        //Pruebas
        SalidaPorDefecto.consola("Prueba actividad mas popular del pintor "+ pintor2.getRFC()+ ": \n");
        cp.imprimirActividadMasPopularPintor(pintor2.getRFC());
        cp.ImprimirpintorQueMasRealizaActividad("Descansar");
        SalidaPorDefecto.consola("Prueba obtener pintor que Viajo el 11/03/2001: " + cp.ObtenerpintorQueRealizoActividad("Viajar",formato.parse("11/03/2001") )+"\n");
        SalidaPorDefecto.consola("Prueba obtener pintor que Pinto el 11/03/2001: " + cp.ObtenerpintorQueRealizoActividad("Pintar", formato.parse("11/03/2001"))+"\n");
        SalidaPorDefecto.consola("Prueba Imprimir dias y Eventos en los que expuso el pintor "+ pintor1.getRFC()+"\n");
        cp.ImprimirDiasYEventosEnQueExpuso(pintor1.getRFC());
        SalidaPorDefecto.consola("Prueba Imprimir dias y en los que expuso el pintor "+ pintor3.getRFC()+"\n");
        cp.ImprimirDiasYEventosEnQueExpuso(pintor3.getRFC());
        SalidaPorDefecto.consola("Prueba obtener año con mas exposiciones: "+ cp.anioMasExposiciones()+ "\n");
        SalidaPorDefecto.consola("Prueba obtener nombre de exposiciones realizadas en el año 2002: " + cp.nombreEventosAnioX(2002) + "\n");
        SalidaPorDefecto.consola("Prueba obtener nombre de exposiciones realizadas en el año 2019: " + cp.nombreEventosAnioX(2019)+"\n");
        SalidaPorDefecto.consola("Prueba obtener dia en el que todos expusieron: " + cp.diaQueTodosExpusieron() + "\n");
        cp.anioQueDedicoMenosTiempoActividad("Pintar",pintor2.getRFC());
        SalidaPorDefecto.consola("Prueba obtener año en el que menos pinto el pintor "+ pintor2.getRFC() + ": "+ cp.anioQueDedicoMenosTiempoActividad("Pintar",pintor2.getRFC())+"\n");
        SalidaPorDefecto.consola("Prueba pintor con mayor aforo en el año 2002: " + cp.obtnerPintorConMayorAforo(2002) + "\n");
        SalidaPorDefecto.consola("Prueba pintor con menor variedad de actividades en el año 2005: " + cp.pintorMenosVariedad(2005)+"\n");
        SalidaPorDefecto.consola("Prueba imprimir actividades agendadas en Brasil en 2002: \n");
        cp.ImprimirActividadesEnLugar("Brasil",2002);
        SalidaPorDefecto.consola("Prueba imprimir actividades agendadas en España en 2002: \n");
        cp.ImprimirActividadesEnLugar("España",2002);
    }
}
