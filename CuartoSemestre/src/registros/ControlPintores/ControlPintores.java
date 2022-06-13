package registros.ControlPintores;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaEstatica;
import estructurasnolineales.Matriz3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ControlPintores {
    protected Matriz3 actividadesPintores;
    protected ListaEstatica pintores;
    protected ListaEstatica dias;
    protected ListaEstatica actividades;
    protected SimpleDateFormat formatoFecha;

    public ControlPintores(int cantidadPintores) throws ParseException {
        formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        pintores = new ListaEstatica(cantidadPintores);
        dias = new ListaEstatica(8036);
        actividades = new ListaEstatica(5);
        llenarActividades();
        llenarAnios(formatoFecha.parse("01/01/2000"), formatoFecha.parse("31/12/2021"));
        actividadesPintores = new Matriz3(5,cantidadPintores,8036,null); // 5 renglones para las 5 clases de actividades, 8036 profundidad por los 21 años pedidos
    }

    /**
     * Llena la lista de actividades con las actividades correspondientes
     */
    public void llenarActividades(){
        actividades.agregar("Pintar");
        actividades.agregar("Exponer");
        actividades.agregar("Firmar Autografos");
        actividades.agregar("Viajar");
        actividades.agregar("Descansar");
    }

    /**
     * llena la lista de dias con todos los dias entre el dia inicial y el dia final
     * @param diaIncio primer dia a guardar
     * @param diaFinal ultimo dia a guardar
     */
    public void llenarAnios(Date diaIncio, Date diaFinal) throws ParseException {
        Calendar fechaInicio = Calendar.getInstance();
        fechaInicio.setTime(diaIncio);
        fechaInicio.add(Calendar.DAY_OF_YEAR, 1);
        Calendar fechaFinal = Calendar.getInstance();
        fechaFinal.setTime(diaFinal);
        dias.agregar(diaIncio);
        while (fechaInicio.before(fechaFinal)) {
            dias.agregar(fechaInicio.getTime());
            fechaInicio.add(Calendar.DAY_OF_YEAR, 1);
        }
    }

    /**
     * agrega un pintor nuevo
     * @param pintor es el pintor a agregar
     * @return regresa true si se pudo agregar y false si no se pudo
     */
    public boolean agregarPintor(Pintor pintor){
        Integer buscar=(Integer)pintores.buscar(pintor);
        if(buscar==null){
            int respuesta= pintores.agregar(pintor);
            if(respuesta==-1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    /**
     * agrega una actividad a un pintor en el dia indicado
     * @param pintor es el pintor al que le corresponde la actividad
     * @param dia es el dia en que se realizo la actividad
     * @param actividad es la actividad a agregar
     * @return regresa true si se pudo agregar y false si no se pudo
     */
    public boolean agregarActividad(String pintor, Date dia, Actividad actividad){
        int tipoActividad = (int) actividades.buscar(actividad);
        ListaEstatica indicesBusqueda=obtenerIndicesCeldas((String) actividades.obtener(tipoActividad), pintor, dia);
        if(indicesBusqueda!=null){
            return actividadesPintores.cambiar((int)indicesBusqueda.obtener(0),(int)indicesBusqueda.obtener(1),
                    (int)indicesBusqueda.obtener(2), actividad);
        }else{
            return false;
        }
    }


    /**
     * Obtiene los indices de las celdas de los valores correspondientes
     * @param actividad es el tipo de actividad a buscar
     * @param Pintor es el pintor a buscar
     * @param dia es el dia a buscar
     * @return regresa la lista con los indices
     */
    private ListaEstatica obtenerIndicesCeldas(String actividad , String Pintor, Date dia){
        Integer indiceActividad = (Integer) actividades.buscar(actividad);
        Integer indicePintor=(Integer)pintores.buscar(Pintor);
        Integer indicedDia=(Integer) dias.buscar(dia);

        if(indiceActividad!=null && indicePintor!=null && indicedDia!=null){
            ListaEstatica indicesArgumentos=new ListaEstatica(3);
            indicesArgumentos.agregar(indiceActividad);
            indicesArgumentos.agregar(indicePintor);
            indicesArgumentos.agregar(indicedDia);
            return indicesArgumentos;
        }else{
            return null;
        }
    }

    /**
     * Obtienen al pintor que mas le gusta pintar (El que mas pinto en total)
     * @throws ParseException
     */
    public void ImprimirpintorQueMasRealizaActividad(String actividad) throws ParseException {
        int tipoActividad = (int) actividades.buscar(actividad);
        ListaEstatica numVecesPintores = new ListaEstatica(pintores.numeroElementos());
        for (int pos =0; pos< pintores.numeroElementos(); pos++){
            int cantidadPintorActual = numVecesActividadTotal((String)actividades.obtener(tipoActividad),(String)pintores.obtener(pos).toString());
            numVecesPintores.agregar(cantidadPintorActual);
        }
        int mayor = (int) numVecesPintores.obtener(0);
        int posMayor=0;
        for (int pos=0; pos< numVecesPintores.numeroElementos(); pos++){
            if ((int)numVecesPintores.obtener(pos)>= mayor){
                posMayor = pos;
                mayor = (int) numVecesPintores.obtener(pos);
            }
        }
        SalidaPorDefecto.consola("El pintor al que mas le gusta "+actividades.obtener(tipoActividad)+ " es " + pintores.obtener(posMayor)+ "\n");
    }

    /**
     * Imprime la actividad mas pupular del pintor
     * @param pintor es el pintor a imprimir
     */
    public void imprimirActividadMasPopularPintor(String pintor) throws ParseException {
        ListaEstatica vecesActividades = obtenerNumVecesActividades(pintor);
        int masPopular = (int) vecesActividades.obtener(0);
        String imprimir = (String) actividades.obtener(0);
        for (int pos=0; pos< vecesActividades.numeroElementos(); pos++){
            if ((int)vecesActividades.obtener(pos) >= masPopular){
                imprimir = (String) actividades.obtener(pos);
            }
        }
        SalidaPorDefecto.consola("La actividad mas popular del pintor "+ pintor+ " es: " + imprimir+"\n");
    }
    /**
     * Obtiene todas la cantidad en que el pintor realizo cada actividad
     * @param Pintor es el pintor a buscar
     * @return regresa una lista con las cantidades.
     * @throws ParseException
     */
    public ListaEstatica obtenerNumVecesActividades(String Pintor) throws ParseException {
        SalidaPorDefecto.consola("Obteniendo informacion, Por favor Espere.. \n");
        ListaEstatica cantidades = new ListaEstatica(5);
        for (int pos=0; pos< actividades.numeroElementos(); pos++){
            cantidades.agregar(numVecesActividadTotal((String) actividades.obtener(pos),Pintor));
        }
        return cantidades;
    }

    /**
     * Obtiene la cantidad total de veces que el pintor realizo la actividad en los 21 años
     * @param actividad es la actividad a buscar
     * @param Pintor es el pintor a buscar
     * @return regresa la cantidad de veces que el pintor realizo la actividad en esos años.
     * @throws ParseException
     */
    public int numVecesActividadTotal(String actividad, String Pintor ) throws ParseException {
        int contador =0;
        for (int anio = 2000; anio<=2021; anio++){
            contador = contador + numVecesActividadXAnio(actividad, Pintor, anio);
        }
        return contador;
    }

    /**
     * Obtiene la cantidad de veces que un pintor realizo una actividad en el año especificado
     * @param actividad es la actividad a buscar
     * @param Pintor es el pintor a buscar
     * @param anio es el año a obtener
     * @return regresa la cantidad de veces que el pintor realizo la actividad en ese año.
     * @throws ParseException
     */
    public int numVecesActividadXAnio(String actividad, String Pintor, int anio) throws ParseException {
        int contador =0;
        String cadenaFechainicio = "01/01/" + anio;
        String cadenaFechaFinal = "30/12/" + anio;
        Date inicio = formatoFecha.parse(cadenaFechainicio);
        Date Final = formatoFecha.parse(cadenaFechaFinal);
        Calendar fechaInicio = Calendar.getInstance();
        fechaInicio.setTime(inicio);
        Calendar fechaFinal = Calendar.getInstance();
        fechaFinal.setTime(Final);
        int tipoActividad = (int) actividades.buscar(actividad);
        while (fechaInicio.before(fechaFinal)) {
            Date fechaActual = fechaInicio.getTime();
            ListaEstatica indicesBusqueda=obtenerIndicesCeldas((String)actividades.obtener(tipoActividad),Pintor,fechaActual);
            fechaInicio.add(Calendar.DAY_OF_YEAR, 1);
            if(indicesBusqueda!=null){
                if(actividadesPintores.obtener((int)indicesBusqueda.obtener(0), (int)indicesBusqueda.obtener(1),
                        (int)indicesBusqueda.obtener(2))!=null){
                    contador++;
                }
            }else{
                return -1;
            }
        }
        return contador;
    }

    /**
     * Obtiene una cadena con el nombre y edad del pintor que realizo la actividad indicada en el dia indicado
     * @param actividad actividad a buscar
     * @param dia dia a buscar
     * @return regresa la cadena con el nombre y edad del pintor o null si no se encontro
     */
    public String ObtenerpintorQueRealizoActividad(String actividad, Date dia){
        int tipoActividad = (int) actividades.buscar(actividad);
        String pintor = null;
        for (int pos=0; pos< pintores.numeroElementos(); pos++){
            ListaEstatica indicesBusqueda=obtenerIndicesCeldas((String)actividades.obtener(tipoActividad), pintores.obtener(pos).toString(),dia);
            Object casillaActual = actividadesPintores.obtener((int)indicesBusqueda.obtener(0),(int) indicesBusqueda.obtener(1),(int) indicesBusqueda.obtener(2));
            if (casillaActual!=null){
                Pintor pintorEncontrado = (Pintor) pintores.obtener(pos);
                pintor = pintorEncontrado.getNombre() + " "+ pintorEncontrado.getApellido()+" " + pintorEncontrado.getEdad() + "\n";
            }
        }
        return pintor;
    }

    /**
     * Imprime la informacion de los eventos y el dia en que el pintor expuso
     * @param Pintor es el pintor a buscar
     */
    public void ImprimirDiasYEventosEnQueExpuso(String Pintor){
        int tipoActividad = (int) actividades.buscar("Exponer");
        int contadorEventos =0;
        for (int pos=0; pos<dias.numeroElementos(); pos ++){
            ListaEstatica indicesBusqueda = obtenerIndicesCeldas((String) actividades.obtener(tipoActividad),Pintor, (Date) dias.obtener(pos));
            Object casillaActual = actividadesPintores.obtener((int) indicesBusqueda.obtener(0), (int) indicesBusqueda.obtener(1), (int) indicesBusqueda.obtener(2));
            if (casillaActual!=null){
                contadorEventos++;
                Actividad actividadActual = (Actividad) casillaActual;
                SalidaPorDefecto.consola("El pintor "+ Pintor + " expuso el " + formatoFecha.format(actividadActual.getFechaEvento()) + " en el evento "+ actividadActual.getNombreEvento()+"\n");
            }
        }
        if (contadorEventos==0){
            SalidaPorDefecto.consola("El pintor "+ Pintor + " No realizo ninguna exposicion. \n");
        }
    }

    /**
     * Obtiene el año en el que mas exposiciones se realizaron
     * @return regresa el año
     * @throws ParseException
     */
    public int anioMasExposiciones() throws ParseException {
        ListaEstatica cantidadxAnio = new ListaEstatica(22);
        ListaEstatica anios = new ListaEstatica(22);
        for (int anio =2000; anio<=2021; anio++){
            anios.agregar(anio);
            int numExposicionesActual =0;
            for (int pos =0; pos<pintores.numeroElementos(); pos++){
                numExposicionesActual = numExposicionesActual + numVecesActividadXAnio("Exponer",pintores.obtener(pos).toString(),anio);
            }
            cantidadxAnio.agregar(numExposicionesActual);
        }
        int mayor = (int) cantidadxAnio.obtener(0);
        int anioMayor = (int) anios.obtener(0);
        for (int pos=0; pos< cantidadxAnio.numeroElementos(); pos++){
            if ((int)cantidadxAnio.obtener(pos) >= mayor){
                mayor = (int) cantidadxAnio.obtener(pos);
                anioMayor = (int) anios.obtener(pos);
            }
        }
        return anioMayor;
    }

    /**
     * obtiene los nombre de los eventos que se realizaron en el anio enviado como parametro
     * @param anio es el anio a buscar
     * @return regresa una cadena con la informacion
     * @throws ParseException
     */
    public String nombreEventosAnioX(int anio) throws ParseException {
        String cadenaRetorno = "";
        String cadenaFechainicio = "01/01/" + anio;
        String cadenaFechaFinal = "30/12/" + anio;
        Date inicio = formatoFecha.parse(cadenaFechainicio);
        Date Final = formatoFecha.parse(cadenaFechaFinal);
        Calendar fechaInicio = Calendar.getInstance();
        fechaInicio.setTime(inicio);
        Calendar fechaFinal = Calendar.getInstance();
        fechaFinal.setTime(Final);
        int tipoActividad = (int) actividades.buscar("Exponer");
        while (fechaInicio.before(fechaFinal)) {
            Date fechaActual = fechaInicio.getTime();
            for (int pos=0; pos < pintores.numeroElementos(); pos++){
                ListaEstatica indicesBusqueda=obtenerIndicesCeldas((String)actividades.obtener(tipoActividad),pintores.obtener(pos).toString(),fechaActual);
                if(indicesBusqueda!=null){
                    Actividad actividadActual = (Actividad) actividadesPintores.obtener((int) indicesBusqueda.obtener(0),(int) indicesBusqueda.obtener(1), (int) indicesBusqueda.obtener(2));
                    if (actividadActual!=null){
                        cadenaRetorno = cadenaRetorno + "\n" + actividadActual.getNombreEvento();
                    }
                }
            }
            fechaInicio.add(Calendar.DAY_OF_YEAR, 1);
        }
        if (!cadenaRetorno.equals("")){
            return cadenaRetorno;
        }else{
            return null;
        }
    }

    /**
     * Obtiene el dia o dias en el que todos los pintores expusieron
     * @return regresa una cadena con la informacion
     * @throws ParseException
     */
    public String diaQueTodosExpusieron() throws ParseException {
        String cadenaRetorno = "";
        String cadenaFechainicio = "01/01/2000";
        String cadenaFechaFinal = "30/12/2021";
        Date inicio = formatoFecha.parse(cadenaFechainicio);
        Date Final = formatoFecha.parse(cadenaFechaFinal);
        Calendar fechaInicio = Calendar.getInstance();
        fechaInicio.setTime(inicio);
        Calendar fechaFinal = Calendar.getInstance();
        fechaFinal.setTime(Final);
        int tipoActividad = (int) actividades.buscar("Exponer");
        while (fechaInicio.before(fechaFinal)) {
            Date fechaActual = fechaInicio.getTime();
            ListaEstatica listaActividades = new ListaEstatica(pintores.numeroElementos());
            for (int pos=0; pos < pintores.numeroElementos(); pos++){
                ListaEstatica indicesBusqueda=obtenerIndicesCeldas((String)actividades.obtener(tipoActividad),pintores.obtener(pos).toString(),fechaActual);
                if(indicesBusqueda!=null){
                    Actividad actividadActual = (Actividad) actividadesPintores.obtener((int) indicesBusqueda.obtener(0),(int) indicesBusqueda.obtener(1), (int) indicesBusqueda.obtener(2));
                    if (actividadActual!=null){
                        listaActividades.agregar(actividadActual);
                    }
                }
            }
            if (listaActividades.numeroElementos() == pintores.numeroElementos()){
                Actividad actividad = (Actividad) listaActividades.obtener(0);
                cadenaRetorno = cadenaRetorno + formatoFecha.format(actividad.getFechaEvento()) + " ";
            }
            fechaInicio.add(Calendar.DAY_OF_YEAR, 1);
        }
        if (!cadenaRetorno.equals("")){
            return cadenaRetorno;
        }else{
            return null;
        }
    }

    /**
     * Obtiene el anio en el que el pintor realizo menos veces la actividad especificada
     * @param actividad es la actividad a buscar
     * @param Pintor es el pintor a buscar
     * @return regresa el anio con menos coincidencia de la actividad para el pintor
     * @throws ParseException
     */
    public int anioQueDedicoMenosTiempoActividad(String actividad, String Pintor) throws ParseException {
        ListaEstatica cantidadxAnio = new ListaEstatica(22);
        ListaEstatica anios = new ListaEstatica(22);
        for (int pos = 2000; pos<= 2021; pos++){
            anios.agregar(pos);
            cantidadxAnio.agregar(numVecesActividadXAnio(actividad,Pintor,pos));
        }

        int menor = (int) cantidadxAnio.obtener(0);
        int retorno = (int) anios.obtener(0);
        for (int pos=0; pos< cantidadxAnio.numeroElementos(); pos++){
            if ((int)cantidadxAnio.obtener(pos) < menor){
                menor = (int) cantidadxAnio.obtener(pos);
                retorno = (int) anios.obtener(pos);
            }
        }
        return retorno;
    }

    /**
     * Obtiene el pintor con mayor aforo del anio indicado
     * @param anio es el anio a buscar
     * @return regresa al pintor con mayor aforo
     * @throws ParseException
     */
    public Pintor obtnerPintorConMayorAforo(int anio) throws ParseException {
        ListaEstatica listaAforos = new ListaEstatica(pintores.numeroElementos());
        for (int pos =0; pos< pintores.numeroElementos(); pos++){
            listaAforos.agregar((aforoXAnio(pintores.obtener(pos).toString(), anio)));
        }
        Pintor mayor = (Pintor) pintores.obtener(0);
        int mayorAforo = (int) listaAforos.obtener(0);
        for (int pos=0; pos< listaAforos.numeroElementos(); pos++){
            if ((int)listaAforos.obtener(pos) >= mayorAforo){
                mayorAforo = (int) listaAforos.obtener(pos);
                mayor = (Pintor) pintores.obtener(pos);
            }
        }
        return mayor;
    }

    /**
     * Obtiene el aforo total de las presentaciones del pintor en el anio indicado
     * @param Pintor es el pintor a buscar
     * @param anio es el anio a buscar
     * @return regresa la suma total del aforo
     * @throws ParseException
     */
    public int aforoXAnio(String Pintor, int anio) throws ParseException {
        int aforo =0;
        String cadenaFechainicio = "01/01/" + anio;
        String cadenaFechaFinal = "30/12/" + anio;
        Date inicio = formatoFecha.parse(cadenaFechainicio);
        Date Final = formatoFecha.parse(cadenaFechaFinal);
        Calendar fechaInicio = Calendar.getInstance();
        fechaInicio.setTime(inicio);
        Calendar fechaFinal = Calendar.getInstance();
        fechaFinal.setTime(Final);
        int tipoActividad = (int) actividades.buscar("Exponer");
        while (fechaInicio.before(fechaFinal)) {
            Date fechaActual = fechaInicio.getTime();
            ListaEstatica indicesBusqueda=obtenerIndicesCeldas((String)actividades.obtener(tipoActividad),Pintor,fechaActual);
            fechaInicio.add(Calendar.DAY_OF_YEAR, 1);
            if(indicesBusqueda!=null) {
                Actividad actividad = (Actividad) actividadesPintores.obtener((int) indicesBusqueda.obtener(0), (int) indicesBusqueda.obtener(1), (int) indicesBusqueda.obtener(2));
                if (actividad!=null){
                    aforo = aforo + (int)actividad.getAforoEvento();
                }
            }
        }
        return aforo;
    }

    /**
     * Obtiene al pintor que realizo menos variedad de actividades en el anio especificado
     * @param anio es el anio a buscar
     * @return regresa al pintor
     */
    public Pintor pintorMenosVariedad(int anio) throws ParseException {
        ListaEstatica listaVariedades = new ListaEstatica(pintores.numeroElementos());
        for (int pos =0; pos< pintores.numeroElementos(); pos++){
            listaVariedades.agregar(variedadDeActividadesXAnio(pintores.obtener(pos).toString(),anio));
        }

        Pintor menor = (Pintor) pintores.obtener(0);
        int menorVariedad = (int) listaVariedades.obtener(0);
        for (int pos=0; pos< listaVariedades.numeroElementos(); pos++){
            if ((int)listaVariedades.obtener(pos) < menorVariedad){
                menorVariedad = (int)listaVariedades.obtener(pos);
                menor = (Pintor) pintores.obtener(pos);
            }
        }
        return menor;
    }

    /**
     * Obtiene la variedad de actividades que realizo el pintor en el anio especificado
     * @param pintor es el pintor a buscar
     * @param anio es el anio a buscar
     * @return regresa la variedad
     * @throws ParseException
     */
    public int variedadDeActividadesXAnio(String pintor, int anio) throws ParseException {
        int variedad =0;
        int numExpos =0;
        int numFirmas =0;
        int numPint =0;
        int numDescansos =0;
        int numViajes =0;
        String cadenaFechainicio = "01/01/" + anio;
        String cadenaFechaFinal = "30/12/" + anio;
        Date inicio = formatoFecha.parse(cadenaFechainicio);
        Date Final = formatoFecha.parse(cadenaFechaFinal);
        Calendar fechaInicio = Calendar.getInstance();
        fechaInicio.setTime(inicio);
        Calendar fechaFinal = Calendar.getInstance();
        fechaFinal.setTime(Final);
        while (fechaInicio.before(fechaFinal)) {
            Date fechaActual = fechaInicio.getTime();
            fechaInicio.add(Calendar.DAY_OF_YEAR, 1);
            for (int pos =0; pos<actividades.numeroElementos(); pos++){
                ListaEstatica indicesBusqueda=obtenerIndicesCeldas((String)actividades.obtener(pos),pintor,fechaActual);
                if(indicesBusqueda!=null) {
                    Actividad actividad = (Actividad) actividadesPintores.obtener((int) indicesBusqueda.obtener(0), (int) indicesBusqueda.obtener(1), (int) indicesBusqueda.obtener(2));
                    if (actividad!=null){
                        if (actividad.getNomActividad().equals("Pintar")){
                            numPint++;
                        }else{
                            if (actividad.getNomActividad().equals("Exponer")){
                                numExpos++;
                            }else{
                                if (actividad.getNomActividad().equals("Firmar Autografos")){
                                    numFirmas++;
                                }else{
                                    if (actividad.getNomActividad().equals("Viajar")){
                                        numViajes++;
                                    }else{
                                        if (actividad.getNomActividad().equals("Descansar")){
                                            numDescansos++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (numExpos!=0){
            variedad++;
        }
        if (numViajes!=0){
            variedad++;
        }
        if (numDescansos!=0){
            variedad++;
        }
        if (numFirmas!=0){
            variedad++;
        }
        if (numPint!=0){
            variedad++;
        }
        return variedad;
    }

    /**
     * Imprime las actividades que se realizan en el lugar indicada en el año indicado
     * @param lugar es el lugar a buscar
     * @param anio es el anio a buscar
     * @throws ParseException
     */
    public void ImprimirActividadesEnLugar(String lugar, int anio) throws ParseException {
        String cadenaFechainicio = "01/01/" + anio;
        String cadenaFechaFinal = "30/12/" + anio;
        Date inicio = formatoFecha.parse(cadenaFechainicio);
        Date Final = formatoFecha.parse(cadenaFechaFinal);
        Calendar fechaInicio = Calendar.getInstance();
        fechaInicio.setTime(inicio);
        Calendar fechaFinal = Calendar.getInstance();
        fechaFinal.setTime(Final);
        while (fechaInicio.before(fechaFinal)) {
            Date fechaActual = fechaInicio.getTime();
            fechaInicio.add(Calendar.DAY_OF_YEAR, 1);
            for (int pos = 0; pos < actividades.numeroElementos(); pos++) {
                for (int pos2 =0; pos2< pintores.numeroElementos(); pos2++){
                    ListaEstatica indicesBusqueda = obtenerIndicesCeldas((String) actividades.obtener(pos), pintores.obtener(pos2).toString(), fechaActual);
                    if (indicesBusqueda != null) {
                        Actividad actividad = (Actividad) actividadesPintores.obtener((int) indicesBusqueda.obtener(0), (int) indicesBusqueda.obtener(1), (int) indicesBusqueda.obtener(2));
                        if (actividad != null) {
                            if (actividad.getNomActividad().equals("Exponer")){
                                if (actividad.getLugarEvento().equals(lugar)){
                                    SalidaPorDefecto.consola(actividad.obtenerDatos()+"\n");
                                }
                            }else{
                                if( actividad.getNomActividad().equals("Viajar")){
                                    if (actividad.getLugarViaje().equals(lugar)){
                                        SalidaPorDefecto.consola(actividad.obtenerDatos()+"\n");
                                    }
                                }else{
                                    if( actividad.getNomActividad().equals("Firmar Autografos")){
                                        if (actividad.getLugarFirma().equals(lugar)){
                                            SalidaPorDefecto.consola(actividad.obtenerDatos()+"\n");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

