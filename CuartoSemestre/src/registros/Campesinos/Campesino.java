package registros.Campesinos;

import estructuraslineales.ListaEstatica;

public class Campesino {
    protected String nombre;
    protected String apellido;
    protected ListaEstatica Produccion;

    public Campesino(String nom, String ape){
        this.nombre = nom;
        this.apellido = ape;
        this.Produccion = new ListaEstatica(48); // Por la cantidad de meses en 4 anios
    }

    /**
     * Agrega la produccion de arroz del campesino en un mes
     * @param numToneladas este es el numero de toneladas producidas
     * @return regresa true si se pudo agregar el mes, false si no se pudo
     */
    public boolean agregarProduccionMes(double numToneladas){
        int valorRetorno=Produccion.agregar(numToneladas);
        if(valorRetorno==-1){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Calcula el promedio anual de produccion de arroz del campesino en el anio indicado como parametro
     * @param numAnio este es el anio al que se le calculara el promedio
     * @return regresa el promedio de produccion del anio
     */
    public Double promedioAnual(int numAnio){
        double sumaProduccion=0.0;
        double promedioProduccion=0.0;
        if (numAnio < 1 || numAnio > 4){
            return null;
        }else{
            int mesInicio = (numAnio * 12) - 12;
            int mesFinal = (numAnio* 12) - 1;
            if(Produccion.vacia()==false){
                for(int posicion=mesInicio;posicion<mesFinal;posicion++){
                    sumaProduccion=sumaProduccion+(double)Produccion.obtener(posicion);
                }
                promedioProduccion = sumaProduccion/12;
                return promedioProduccion;
            }else{
                return null;
            }
        }
    }

    /**
     * Indica la cantidad de meses que fueron menores en produccion que el promedio del anio indicado
     * @param numAnio este es el anio del cual se hara la evaluacion
     * @return Regresa la cantidad de meses obtenidos
     */
    public Integer mesesMenoresAlPromedio(int numAnio){
        if (numAnio < 1 || numAnio > 4){
            return null;
        }else{
            Integer contadorMesesMenores = 0;
            if(Produccion.vacia()==false){
                Double promedio = this.promedioAnual(numAnio);
                int mesInicio = (numAnio * 12) - 12;
                int mesFinal = (numAnio* 12) - 1;
                for (int posicion = mesInicio; posicion<= mesFinal; posicion++){
                    if ((Double)Produccion.obtener(posicion) < promedio){
                        contadorMesesMenores++;
                    }
                }
            }
            return contadorMesesMenores;
        }
    }

    /**
     * Indica los meses que tuvieron mayor produccion en cada anio del campesino
     * @return regresa una lista con los meses (1-12) mayores por cada anio
     */
    public ListaEstatica mesesMayoresCadaAnio(){
        ListaEstatica mesesMayores = new ListaEstatica(4);
        int inicioanio1 = 0;
        int inicioanio2 = 12;
        int inicioanio3 = 24;
        int inicioanio4 = 36;
        int limiteanio1 = 11;
        int limiteanio2 = 23;
        int limiteanio3 = 35;
        int limiteanio4 = 47;
        Double mayorAnio1 = (Double) Produccion.obtener(inicioanio1);
        int mesmayor = 0;
        for (int posicion = inicioanio1; posicion<=limiteanio1; posicion++){
            if((Double) Produccion.obtener(posicion) >= mayorAnio1 ){
                mayorAnio1 = (Double) Produccion.obtener(posicion);
                mesmayor = posicion;
            }
        }
        mesesMayores.agregar(mesmayor+1);
        mesmayor = 0;

        Double mayorAnio2 = (Double) Produccion.obtener(inicioanio2);
        for (int posicion = inicioanio2; posicion<=limiteanio2; posicion++){
            if((Double) Produccion.obtener(posicion) >= mayorAnio2 ){
                mayorAnio2 = (Double) Produccion.obtener(posicion);
                mesmayor = posicion;
            }
        }
        mesesMayores.agregar(mesmayor+1);
        mesmayor = 0;
        Double mayorAnio3 = (Double) Produccion.obtener(inicioanio3);
        for (int posicion = inicioanio3; posicion<=limiteanio3; posicion++){
            if((Double) Produccion.obtener(posicion) >= mayorAnio3 ){
                mayorAnio3 = (Double) Produccion.obtener(posicion);
                mesmayor = posicion;
            }
        }
        mesesMayores.agregar(mesmayor+1);
        mesmayor = 0;

        Double mayorAnio4 = (Double) Produccion.obtener(inicioanio4);
        for (int posicion = inicioanio4; posicion<=limiteanio4; posicion++){
            if((Double) Produccion.obtener(posicion) >= mayorAnio4 ){
                mayorAnio4 = (Double) Produccion.obtener(posicion);
                mesmayor = posicion;
            }
        }
        mesesMayores.agregar(mesmayor+1);
        return mesesMayores;
    }

    /**
     * Indica la produccion de toneladas del ultimo mes de cada año del campesino
     * @return Regresa una lista con la prodiccion de los ultimos meses de cada anio
     */
    public ListaEstatica produccionUltimoMesAnual(){
        ListaEstatica ultimosMeses = new ListaEstatica(4);
        int ultimoMesAnio1 = 11;
        int ultimoMesAnio2= 23;
        int ultimoMesAnio3 = 35;
        int ultimoMesAnio4 = 47;

        ultimosMeses.agregar(Produccion.obtener(ultimoMesAnio1));
        ultimosMeses.agregar(Produccion.obtener(ultimoMesAnio2));
        ultimosMeses.agregar(Produccion.obtener(ultimoMesAnio3));
        ultimosMeses.agregar(Produccion.obtener(ultimoMesAnio4));
        return ultimosMeses;
    }

    /**
     * Genera una lista con la suma de la produccion de los primeros tres meses de cada anio
     * @return Regresa la lista generada de la produccion en cada trimestre
     */
    public ListaEstatica primerosTrimestres(){
        ListaEstatica produccionTrimestres = new ListaEstatica(4);
        int inicioanio1 = 0;
        int inicioanio2 = 12;
        int inicioanio3 = 24;
        int inicioanio4 = 36;
        double SumaTrimestreAnio1 = 0.0;
        double SumaTrimestreAnio2 = 0.0;
        double SumaTrimestreAnio3 = 0.0;
        double SumaTrimestreAnio4 = 0.0;
        for (int posicion = inicioanio1; posicion< inicioanio1+3; posicion++){
            SumaTrimestreAnio1 = SumaTrimestreAnio1 + (double) Produccion.obtener(posicion);
        }
        for (int posicion = inicioanio2; posicion< inicioanio2+3; posicion++){
            SumaTrimestreAnio2 = SumaTrimestreAnio2 + (double) Produccion.obtener(posicion);
        }
        for (int posicion = inicioanio3; posicion< inicioanio3+3; posicion++){
            SumaTrimestreAnio3 = SumaTrimestreAnio3 + (double) Produccion.obtener(posicion);
        }
        for (int posicion = inicioanio4; posicion< inicioanio4+3; posicion++){
            SumaTrimestreAnio4 = SumaTrimestreAnio4 + (double) Produccion.obtener(posicion);
        }
        produccionTrimestres.agregar(SumaTrimestreAnio1);
        produccionTrimestres.agregar(SumaTrimestreAnio2);
        produccionTrimestres.agregar(SumaTrimestreAnio3);
        produccionTrimestres.agregar(SumaTrimestreAnio4);
        return produccionTrimestres;
    }

    /**
     * Indica la suma de las ganancias de cada temporada del anio (primavera,vernano, otono, invierno)
     * @return Regresa una lista con las ganancias de las 4 temporadas
     */
    public ListaEstatica gananciasPorTemporada(){
        ListaEstatica gananciasTemporada = new ListaEstatica(4);
        int inicioPrimavera = 0;
        int inicioVerano = 3;
        int inicioOtono = 6;
        int inicioInvierno = 9;
        double gananciasPrimavera = 0.0;
        double gananciasVerano = 0.0;
        double gananciasOtono = 0.0;
        double gananciasInvierno = 0.0;
        for (int posicion =inicioPrimavera; posicion< inicioVerano; posicion++){
            gananciasPrimavera = gananciasPrimavera + (double)Produccion.obtener(posicion);
        }
        for (int posicion =inicioVerano; posicion< inicioOtono; posicion++){
            gananciasVerano = gananciasVerano + (double)Produccion.obtener(posicion);
        }
        for (int posicion =inicioOtono; posicion< inicioInvierno; posicion++){
            gananciasOtono = gananciasOtono + (double)Produccion.obtener(posicion);
        }
        for (int posicion =inicioInvierno; posicion< 12 ; posicion++){
            gananciasInvierno = gananciasInvierno + (double)Produccion.obtener(posicion);
        }
        gananciasTemporada.agregar(gananciasPrimavera);
        gananciasTemporada.agregar(gananciasVerano);
        gananciasTemporada.agregar(gananciasOtono);
        gananciasTemporada.agregar(gananciasInvierno);
        return gananciasTemporada;
    }

    /**
     * Obtiene el mes de mayor produccion de la lista
     * @return regresa el mes encontrado como mayor
     */
    public int obtenerMejorMes(){
        double mayor = (double) Produccion.obtener(0);
        int mesRetorno = 0;
        for (int posicion =0; posicion<Produccion.numeroElementos(); posicion++){
            if ((double)Produccion.obtener(posicion) >= mayor){
                mayor = (double) Produccion.obtener(posicion);
                mesRetorno = posicion;
            }
        }
        return mesRetorno+1;
    }

    /**
     * Obtiene la mejor epoca del año en cuanto a produccion
     * @return regresa el nombre de la epoca encontrada como mejor
     */
    public String obtenerMejorEpoca(){
        ListaEstatica temporadas = this.gananciasPorTemporada();
        double mayor = (double) temporadas.obtener(0);
        for (int posicion = 0; posicion<temporadas.numeroElementos(); posicion++){
            if ((double) temporadas.obtener(posicion) > mayor){
                mayor = (double) temporadas.obtener(posicion);
            }
        }

        if (mayor == (double) temporadas.obtener(0)){
            return "Primavera";
        }else{
            if(mayor == (double) temporadas.obtener(1)){
                return "Verano";
            }else {
                if (mayor == (double) temporadas.obtener(2)) {
                    return "Otoño";
                } else {
                    if (mayor == (double) temporadas.obtener(3)) {
                        return "Invierno";
                    }
                }
            }
        }
        return null;
    }

    public String getNombre(){
        return this.nombre;
    }
    public String getApellido(){
        return this.apellido;
    }
}
