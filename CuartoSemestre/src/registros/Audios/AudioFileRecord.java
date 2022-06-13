package registros.Audios;

import java.io.*;

import estructuraslineales.ListaEstaticaNumerica;
import registros.Audios.wavfile.*;

public class AudioFileRecord {
    private long numFrames;  //numero de tramas, número de muestras totales del archivo por canal
    private long sampleRate; //numero de muestras por segundo a la que se discretiza la señal
    private int numChannels; //número de canales
    private double[] buffer; //audio original
    private double[] buffer2; //audio modificado
    private String archivo;   //nombre de archivo dado por el usuario
    private WavFile wavFileR; //apuntador de archivo leido
    private WavFile wavFileW;  //apuntador de archivo a escribir
    private String nomArchivo; //nombre archivo (uno.wav)
    private String nomRuta;    //ruta donde esta guardado el archivo (/home)
    private int validBits;     //bits usados para la discretización
    private ListaEstaticaNumerica listaBuffer;

    public AudioFileRecord(String archivo) {
        this.archivo = archivo;
        // Abre el archivo wav y asigna parámetros a las variables
        try {
            File file = new File(archivo);
            wavFileR = WavFile.openWavFile(file);
            nomArchivo = file.getName();
            nomRuta = file.getParent();
        } catch (Exception e) {

        }
        numChannels = wavFileR.getNumChannels();
        numFrames = wavFileR.getNumFrames();
        sampleRate = wavFileR.getSampleRate();
        validBits=wavFileR.getValidBits();
    }

    public void leerAudio() {
        try {

            // Muestra datos del archivo
            wavFileR.display();

            // Crea buffer de origen y de temporal
            buffer = new double[(int) numFrames * numChannels];
            buffer2 = new double[buffer.length];
            Object[] buffer2copia = new Object[buffer.length];

            //tramas leidas
            int framesRead;

            // Lee tramas totales
            framesRead = wavFileR.readFrames(buffer, (int) numFrames);

            // Recorrer todas las tramas del archivo y guardarlas en el arreglo.
            for (int s = 0; s < framesRead * numChannels; s++) {
                buffer2[s] = buffer[s];
                buffer2copia[s] = buffer[s];
            }

            listaBuffer = new ListaEstaticaNumerica((int) numFrames * numChannels);
            listaBuffer.guardarContenidoBuffer(buffer2copia);

            // Cierra archivo
            wavFileR.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void EscribirAudio() {
        try {

            //Crear el apuntador al archivo para guardar datos del temporal
            File file = new File(nomRuta + "/2_" + nomArchivo);

            // Creamos un nuevo archivo de audio con los mismos datos que el original
            wavFileW = WavFile.newWavFile(file, numChannels, numFrames, validBits, sampleRate);

            // Escribimos los frames o muestras totales del temporal
            wavFileW.writeFrames(buffer2, (int) numFrames);

            buffer2 = listaBuffer.leerArreglo();

            // Cerramos el archivo
            wavFileW.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Aplica el filtro FIR al audio
     */
    public void preEnfasis(){
        double ALFA = 0.95;
        for (int pos=0; pos<buffer2.length;pos++){
            buffer2[pos] = buffer2[pos] + ALFA * buffer2[pos];
        }
        EscribirAudio();
    }

    /**
     * Incrementa el volumen del audio segun lo indicado como parametro
     * @param intensidad es el valor de volumen a aumentar
     */
    public void subirVolumen(int intensidad){
        for (int pos=0; pos<buffer2.length;pos++){
            buffer2[pos] = buffer2[pos] *intensidad;
        }
        EscribirAudio();
    }

    /**
     * Decrementa el volumen del audio segun lo indicado como parametro
     * @param intensidad es el valor de volumen a disminuir
     */
    public void bajarVolumen(int intensidad){
        for (int pos=0; pos<buffer2.length;pos++){
            buffer2[pos] = buffer2[pos] /intensidad;
        }
        EscribirAudio();
    }

    /**
     * acelera el audio reduciendo la cantidad de frames
     * @param aceleracion es el valor de la aceleracion a realizar
     */
    public void acelerarAudio(int aceleracion){
        numFrames = numFrames/aceleracion;
        for (int posicion=0; posicion<numFrames* numChannels;posicion++){
            buffer2[posicion] = buffer2[posicion*aceleracion];
        }
        EscribirAudio();
    }

    /**
     * retresa el audio aumentando y rellenando la cantidad de frames
     * @param retraso es el valor del retraso a aplicar
     */
    public void retrasarAudio(int retraso){
        numFrames = numFrames * retraso;
        double[] buffer3 = new double[buffer.length*retraso];
        for (int posicion=0; posicion< buffer2.length; posicion++){
            buffer3[posicion* retraso] = buffer2[posicion];
        }
        buffer2 = buffer3;
        EscribirAudio();
    }

    /**
     * Elimina los silencios dentro del audio
     */
    public void eliminarSilencio(){
        double[] buffer3 = new double[buffer.length];
        int indiceB3=0;
        int contadorSilencios=0;
        for (int posicion=0; posicion< buffer2.length; posicion++){
            if (!(buffer2[posicion] ==0)){
                buffer3[indiceB3] = buffer2[posicion];
                indiceB3++;
            }else{
                contadorSilencios++;
            }
        }
        numFrames = numFrames - contadorSilencios;
        buffer2 = buffer3;
        EscribirAudio();

    }

    /**
     * Invierte el eje x del audio, es decir, se invertira el orden de los frames
     */
    public void invertirAudioX(){
        for (int posicion = 0; posicion < buffer2.length / 2; posicion++) {
            double memoria = (double) buffer2[posicion];
            int ultimo = buffer2.length - posicion - 1;
            buffer2[posicion] = buffer2[ultimo];
            buffer2[ultimo] = memoria;
        }
        EscribirAudio();
    }

    /**
     * se invierte el valor de las frecuencias negativas y positivas del eje y
     */
    public void invertirAudioY(){
        for (int posicion=0; posicion< buffer2.length; posicion++){
            buffer2[posicion] = buffer2[posicion]*-1;
        }
        EscribirAudio();
    }

    /**
     * se obtiene la energia total del audio
     * @return regresa el valor de la suma de la energia de la señal de audio
     */
    public double obtenerEnergiaAudio(){
        double sumaEnergia = 0.0;
        for (int posicion=0; posicion< buffer2.length; posicion++){
            sumaEnergia = sumaEnergia + (buffer2[posicion] * buffer2[posicion]);
        }
        return sumaEnergia;
    }

}
