package registros.Campesinos;

import entradasalida.EntradaPorDefecto;
import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaEstatica;

import java.io.*;

public class MenuCampesinos {

    /**
     * Llena la lista de produccion del campesino tomando datos guardados en un archivo
     * @param campesino es el campesino al que se llenara la lista
     * @param nombreArchivo es el archivo del cual se extraeran los datos
     * @return regresa un true si se pudo realizar la operacion y false si no se pudo
     * @throws FileNotFoundException lanza una excepcion si no se encuentra el archivo especificado
     */
    public boolean llenarDesdeArchivo(Campesino campesino, String nombreArchivo) throws FileNotFoundException {
        File file = new File("src/registros/Campesinos/"+nombreArchivo + ".txt");
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        boolean respuesta = false;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);


        } catch (FileNotFoundException e) {
            return respuesta;
        }

        try {
            String linea = "";
            while( ( linea = bufferedReader.readLine()) != null) {
                double toneladas = Double.parseDouble(linea);
                campesino.agregarProduccionMes(toneladas);
            }
            respuesta = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    /**
     * Permite al usuario seleccionar al campesino del que quiere consultar informacion
     * @param campesino1 primera opcion
     * @param campesino2 segunda opcion
     * @return regresa al campesino elegido
     */
    public Campesino seleccionarCampesino(Campesino campesino1, Campesino campesino2){
        int opcion = 0;
        Campesino campesinoRetorno = null;
        while (opcion!=1 && opcion!=2){
            SalidaPorDefecto.consola("Seleccione un campesino: "+ "\n");
            SalidaPorDefecto.consola("1) "+ campesino1.getNombre()+ " "+ campesino1.getApellido()+ "\n");
            SalidaPorDefecto.consola("2) "+ campesino2.getNombre()+ " "+ campesino2.getApellido()+ "\n");
            opcion = EntradaPorDefecto.consolaInt();
            if (opcion == 1){
                campesinoRetorno= campesino1;
            }else{
                if (opcion == 2){
                    campesinoRetorno =  campesino2;
                }else{
                    MensajeOpcionInvalida();
                }
            }
        }
        return campesinoRetorno;
    }

    /**
     * Muestra en pantalla un mensaje si la opcion seleccionada no es valida
     */
    public void MensajeOpcionInvalida(){
        SalidaPorDefecto.consola("Por favor, Ingrese una opcion valida"+ "\n");
    }

    /**
     * Imprime las opciones de acciones del programa
     */
    public void ImprimirOpciones(){
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("1) Promedio Anual"+ "\n");
        SalidaPorDefecto.consola("2) Meses con menor produccion al promedio"+ "\n");
        SalidaPorDefecto.consola("3) Mes de mayor produccion por año"+ "\n");
        SalidaPorDefecto.consola("4) Toneladas del ultimo mes"+ "\n");
        SalidaPorDefecto.consola("5) Produccion del primer trimestre de cada año"+ "\n");
        SalidaPorDefecto.consola("6) Comparar productividad de campesinos"+ "\n");
        SalidaPorDefecto.consola("7) Mejor mes para los campesinos"+ "\n");
        SalidaPorDefecto.consola("8) Epoca del año con mayores ganancias"+ "\n");
        SalidaPorDefecto.consola("0) Salir"+ "\n");
    }

    /**
     * Imprime el promedio anual de produccion
     * @param campesino es el campesino del que se hara la consulta
     */
    public void ImprimirPromedioAnual(Campesino campesino){
        SalidaPorDefecto.consola("El promedio anual del año 1 de "+ campesino.getNombre()+ " "+ campesino.getApellido()+ " es " + campesino.promedioAnual(1) + " Toneladas" + "\n");
        SalidaPorDefecto.consola("El promedio anual del año 2 de "+ campesino.getNombre()+ " "+ campesino.getApellido()+ " es " + campesino.promedioAnual(2) + " Toneladas" + "\n");
        SalidaPorDefecto.consola("El promedio anual del año 3 de "+ campesino.getNombre()+ " "+ campesino.getApellido()+ " es " + campesino.promedioAnual(3) + " Toneladas" + "\n");
        SalidaPorDefecto.consola("El promedio anual del año 4 de "+ campesino.getNombre()+ " "+ campesino.getApellido()+ " es " + campesino.promedioAnual(4) + " Toneladas" + "\n");
    }

    /**
     * imprime cuales son los meses de produccion por debajo del promedio anual
     * @param campesino es el campesino del que se hara la consulta
     */
    public void ImprimirMesesMenores(Campesino campesino){
        SalidaPorDefecto.consola("El campesino "+campesino.getNombre()+" "+campesino.getApellido()+" tiene "+campesino.mesesMenoresAlPromedio(1)+ " meses menores al promedio del año 1"+ "\n");
        SalidaPorDefecto.consola("El campesino "+campesino.getNombre()+" "+campesino.getApellido()+" tiene "+campesino.mesesMenoresAlPromedio(2)+ " meses menores al promedio del año 2"+ "\n");
        SalidaPorDefecto.consola("El campesino "+campesino.getNombre()+" "+campesino.getApellido()+" tiene "+campesino.mesesMenoresAlPromedio(3)+ " meses menores al promedio del año 3"+ "\n");
        SalidaPorDefecto.consola("El campesino "+campesino.getNombre()+" "+campesino.getApellido()+" tiene "+campesino.mesesMenoresAlPromedio(4)+ " meses menores al promedio del año 4"+ "\n");
    }

    /**
     * imprime los meses de mayor produccion de cada año del campesino
     * @param campesino es el campesino del que se hara la consulta
     */
    public void ImprimirMesesDeMayorProduccion(Campesino campesino){
        ListaEstatica mayores = campesino.mesesMayoresCadaAnio();
        SalidaPorDefecto.consola("Meses de mayor produccion de cada año de "+campesino.getNombre()+ " "+campesino.getApellido()+ "\n");
        for (int posicion = 0; posicion< mayores.numeroElementos(); posicion++){
            SalidaPorDefecto.consola("Mes " + mayores.obtener(posicion)+ "\n");
        }
    }

    /**
     * Imprime las toneladas producidas en el ultimo mes de cada año
     * @param campesino es el campesino del que se hara la consulta
     */
    public void ImprimirToneladasUltimoMes(Campesino campesino){
        ListaEstatica ultimos = campesino.produccionUltimoMesAnual();
        SalidaPorDefecto.consola("Toneladas producidas en el ultimo mes de cada año por "+ campesino.getNombre()+" "+campesino.getApellido() + "\n");
        for (int posicion = 0; posicion< ultimos.numeroElementos(); posicion++){
            SalidaPorDefecto.consola(ultimos.obtener(posicion)+ " Toneladas " +"\n");
        }
    }

    /**
     * imprime la suma de las toneladas producidas en los primeros 3 meses de cada año
     * @param campesino es el campesino del que se hara la consulta
     */
    public void ImprimirPrimerosTrimestres(Campesino campesino){
        ListaEstatica trimestres = campesino.primerosTrimestres();
        SalidaPorDefecto.consola("Toneladas producidas en los primeros trimestres de cada año por "+ campesino.getNombre()+" "+campesino.getApellido() + "\n");
        for (int posicion=0; posicion< trimestres.numeroElementos(); posicion++){
            SalidaPorDefecto.consola(trimestres.obtener(posicion)+ " Toneladas " +"\n");
        }
    }

    /**
     * Compara la productividad anual de dos campesinos
     * @param campesino1 el primer campesino a comparar
     * @param campesino2 el segundo campesino a comparar
     */
    public  void ImprimirComparacionProductividad(Campesino campesino1, Campesino campesino2){
        double promedioCampesino1Anio1 = campesino1.promedioAnual(1);
        double promedioCampesino1Anio2 = campesino1.promedioAnual(2);
        double promedioCampesino1Anio3 = campesino1.promedioAnual(3);
        double promedioCampesino1Anio4 = campesino1.promedioAnual(4);
        double promedioCampesino2Anio1 = campesino2.promedioAnual(1);
        double promedioCampesino2Anio2 = campesino2.promedioAnual(2);
        double promedioCampesino2Anio3 = campesino2.promedioAnual(3);
        double promedioCampesino2Anio4 = campesino2.promedioAnual(4);
        SalidaPorDefecto.consola("...Imprimiendo Comparaciones..."+ "\n");
        if (promedioCampesino1Anio1 > promedioCampesino2Anio1){
            SalidaPorDefecto.consola("El campesino "+ campesino1.getNombre()+ " "+ campesino1.getApellido()+ " fue mas productivo en el año 1"+"\n");
        }else{
            SalidaPorDefecto.consola("El campesino "+ campesino2.getNombre()+ " "+ campesino2.getApellido()+ " fue mas productivo en el año 1"+"\n");
        }

        if (promedioCampesino1Anio2> promedioCampesino2Anio2){
            SalidaPorDefecto.consola("El campesino "+ campesino1.getNombre()+ " "+ campesino1.getApellido()+ " fue mas productivo en el año 2"+"\n");
        }else{
            SalidaPorDefecto.consola("El campesino "+ campesino2.getNombre()+ " "+ campesino2.getApellido()+ " fue mas productivo en el año 2"+"\n");
        }

        if (promedioCampesino1Anio3 > promedioCampesino2Anio3){
            SalidaPorDefecto.consola("El campesino "+ campesino1.getNombre()+ " "+ campesino1.getApellido()+ " fue mas productivo en el año 3"+"\n");
        }else{
            SalidaPorDefecto.consola("El campesino "+ campesino2.getNombre()+ " "+ campesino2.getApellido()+ " fue mas productivo en el año 3"+"\n");
        }

        if(promedioCampesino1Anio4> promedioCampesino2Anio4){
            SalidaPorDefecto.consola("El campesino "+ campesino1.getNombre()+ " "+ campesino1.getApellido()+ " fue mas productivo en el año 4"+"\n");
        }else{
            SalidaPorDefecto.consola("El campesino "+ campesino2.getNombre()+ " "+ campesino2.getApellido()+ " fue mas productivo en el año 4"+"\n");
        }
    }

    /**
     * Imprime el mes de mayor productividad del campesino
     * @param campesino es el campesino del que se hara la consulta
     */
    public void ImprimirMejorMesCampesino(Campesino campesino){
        int mayorMes = campesino.obtenerMejorMes();
        SalidaPorDefecto.consola("El mejor mes para "+ campesino.getNombre()+ " "+ campesino.getApellido()+ " fue el mes "+ mayorMes+ "\n");
    }

    /**
     * Imprime la mejor epoca en cuanto a produccion para un campesino
     * @param campesino es el campesino del que se hara la consulta
     */
    public void ImprimirMejorEpoca(Campesino campesino){
        String mejorEpoca = campesino.obtenerMejorEpoca();
        SalidaPorDefecto.consola("La mejor epoca para "+campesino.getNombre()+ " "+ campesino.getApellido()+ " fue "+ mejorEpoca+"\n");

    }
}
