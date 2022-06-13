package registros.Imagenes;

import estructurasnolineales.Matriz2Numerica;
import sun.awt.image.ToolkitImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class editorImagen {
    protected BufferedImage imagen;
    protected Matriz2Numerica matrizImagen;
    protected String nomArchivo;
    protected Object[][] arregloMatriz;
    protected ImageIcon imageIcon;
    protected JFrame jFrame;
    protected JLabel jLabel;

    /**
     * lee una imagen enviada como parametro
     *
     * @param archivo es la imagen a leer
     * @throws IOException Si no encuentra, o no puede leer la imagen, lanza una excepcion
     */
    public editorImagen(String archivo) throws IOException {
        try {
            nomArchivo = archivo;
            imagen = ImageIO.read(new File(archivo));
            int w = imagen.getWidth();
            int h = imagen.getHeight();
            matrizImagen = new Matriz2Numerica(w, h);
            //arregloMatriz = matrizImagen.obtenerArreglo();
            llenarArreglo();
            jFrame = new JFrame();
            jFrame.setLayout(new FlowLayout());
            jFrame.setSize(300, 300);
            jLabel = new JLabel();
            mostrarImagen();
        } catch (IOException e) {
        }
    }

    /**
     * escribe la nueva imagen en un nuevo archivo (editada.jpg)
     *
     * @throws IOException
     */
    public void escribirImagen() throws IOException {
        try {
            ImageIO.write(imagen, "JPG", new File("src\\registros\\Imagenes\\editada.jpg"));
        } catch (IOException e) {
        }
    }

    /**
     * llena el arreglo con los valores del estado actual de la imagen despues de cada cambio
     */
    public void llenarArreglo() {
        for (int fila = 0; fila < matrizImagen.obtenerRenglones(); fila++) {
            for (int columna = 0; columna < matrizImagen.obtenerColumnas(); columna++) {
                arregloMatriz[fila][columna] = imagen.getRGB(fila,columna);
            }
        }
    }

    /**
     * realiza los cambios dentro de la imagen
     * @param fila es la fila a buscar
     * @param col es la columna a buscar
     * @param valor es el nuevo valor RGB a colocar
     */
    public void setValorRGBNuevo(int fila, int col, int valor) {
        imagen.setRGB(fila, col, valor);
    }


    /**
     * Muestra la imagen en el estado actual que se encuentre
     */
    public void mostrarImagen() {
        imageIcon = new ImageIcon(imagen);
        jLabel.setIcon(imageIcon);
        jFrame.add(jLabel);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * Transaforma la imagen a escala de grises promediando los colores de cada pixel
     * @throws IOException lanza una excepcion si no puede escribir el nuevo archivo
     */
    public void escalaDeGrises() throws IOException {
        for (int fila = 0; fila < matrizImagen.obtenerRenglones(); fila++) {
            for (int col = 0; col < matrizImagen.obtenerColumnas(); col++) {
                Color colorPixelActual = new Color((int) arregloMatriz[fila][col]);
                //Para promediar los colores;
                int rojo = colorPixelActual.getRed();
                int verde = colorPixelActual.getGreen();
                int azul = colorPixelActual.getBlue();
                int alfa = colorPixelActual.getAlpha();
                int promedio = (rojo + azul + verde) / 3;
                setValorRGBNuevo(fila,col,new Color(promedio, promedio, promedio, alfa).getRGB());
            }
        }
        llenarArreglo();
        escribirImagen();
        mostrarImagen();
    }

    /**
     * si el valor indicado como parametro es positivo, sube el brillo, si no, lo baja
     * @param cantidad es la cantidad de brillo a decrementar
     */
    public void editarBrillo(int cantidad) throws IOException {
        for (int fila = 0; fila < matrizImagen.obtenerRenglones(); fila++) {
            for (int col = 0; col < matrizImagen.obtenerColumnas(); col++) {
                Color colorActual = new Color((int) arregloMatriz[fila][col]);
                int rojo = colorActual.getRed() + cantidad; //sumo la cantidad
                int verde = colorActual.getGreen() + cantidad;
                int azul = colorActual.getBlue() + cantidad;
                if (rojo > 255) { //Para no exceder los limites
                    rojo = 255;
                }
                if (verde > 255) {
                    verde = 255;
                }
                if (azul > 255) {
                    azul = 255;
                }
                if (rojo < 0) { //Para no estar debajo de los limites
                    rojo = 0;
                }
                if (verde < 0) {
                    verde = 0;
                }
                if (azul < 0) {
                    azul = 0;
                }
                setValorRGBNuevo(fila,col, new Color(rojo, verde, azul).getRGB());
            }
        }
        llenarArreglo();
        escribirImagen();
        mostrarImagen();
    }

    /**
     * Invierte la imagen en sentido horizontal
     * @throws IOException lanza una excepcion si no se puede escribir la imagen
     */
    public void invertirHorizontal() throws IOException {
        Object[][] arregloNuevo = new Object[matrizImagen.obtenerRenglones()][matrizImagen.obtenerColumnas()];
        for (int fila =0; fila< matrizImagen.obtenerRenglones(); fila++){
            for (int col=0; col< matrizImagen.obtenerColumnas(); col++){
                arregloNuevo[matrizImagen.obtenerColumnas()-fila-1][col]= arregloMatriz[fila][col];
            }
        }
        reemplazarMatriz(arregloNuevo);
        llenarArreglo();
        escribirImagen();
        mostrarImagen();
    }

    /**
     * invierte la matriz, para lograr que la imagen se muestra inversa en sentido vertical
     * @throws IOException lanza una excepcion si no se puede escribir el archivo
     */
    public void invertirVertical() throws IOException {
        int contFilas=0;
        int contCol=0;
        Object[][] arregloNuevo = new Object[matrizImagen.obtenerRenglones()][matrizImagen.obtenerColumnas()];
        for (int fila = matrizImagen.obtenerRenglones()-1; fila >=0; fila--) {
            for (int col = matrizImagen.obtenerColumnas()-1; col >=0; col--) {
                arregloNuevo[contFilas][contCol] = arregloMatriz[fila][col];
                contCol++;
            }
            contCol=0;
            contFilas++;
        }
        reemplazarMatriz(arregloNuevo);
        llenarArreglo();
        escribirImagen();
        mostrarImagen();
    }

    /**
     * Rota la imagen 90 grados rotando los elementos de la matriz
     * @throws IOException lanza una escepcion si no se puede escribir el archivo
     */
    public void rotar() throws IOException {
        Object[][] arregloNuevo = new Object[matrizImagen.obtenerRenglones()][matrizImagen.obtenerColumnas()];
        for (int fila=0;fila<arregloMatriz.length;fila++) {
            for (int col=0;col<arregloMatriz.length;col++) {
                arregloNuevo[arregloMatriz.length-1-col][fila] = arregloMatriz[fila][col];
            }
        }
        reemplazarMatriz(arregloNuevo);
        llenarArreglo();
        escribirImagen();
        mostrarImagen();
    }

    /**
     * reemplaza el arreglo de la matriz segun los cambios requeridos
     * @param matrizNueva regresa el arreglo nuevo
     */
    public void reemplazarMatriz(Object[][] matrizNueva){
        arregloMatriz = matrizNueva;
        for (int fila = 0; fila<matrizImagen.obtenerRenglones();fila++){
            for (int col =0; col< matrizImagen.obtenerColumnas(); col++) {
                setValorRGBNuevo(fila,col, (Integer) arregloMatriz[fila][col]);
            }
        }
    }

    /**
     * redimensiona la imagen en la forma largoxancho segun lo recibido como parametro
     * @param largo es el largo a cambiar
     * @param ancho es el ancho a cambiar
     * @throws IOException lanza una excepcion si no se puede escribir la imagen
     */
    public void redimensionar(int largo, int ancho) throws IOException {
        if (largo>0 && ancho>0){
            Image imagenNueva = imagen.getScaledInstance(largo, ancho, Image.SCALE_DEFAULT);
            imageIcon.setImage(imagenNueva);
            imagen = ((ToolkitImage) imagenNueva).getBufferedImage();
            escribirImagen();
        }
    }

    /**
     * redimensiona la imagen a la escala pasada como parametro
     * @param escala es la escala a reducir o ampliar
     * @throws IOException lanza una escepcio  si no se puede escribir la imagen
     */
    public void redimensionar(double escala) throws IOException {
        if (escala>0){
            Image imagenNueva = imagen.getScaledInstance((int)Math.round(imageIcon.getIconWidth() * escala), (int)Math.round(imageIcon.getIconHeight() * escala), Image.SCALE_DEFAULT);
            imageIcon.setImage(imagenNueva);
            imagen = ((ToolkitImage) imagenNueva).getBufferedImage();
            escribirImagen();
        }
    }

    /**
     * Agrega un marco a la imagen segun el ancho y el color enviados como parametro
     * @param anchoMarco es el ancho del marco
     * @param color es el color del marco
     * @throws IOException lanza una excepcion si no se puede escribir la imagen
     */
    public void colocarMarco(int anchoMarco, Color color) throws IOException {
        Graphics2D g = (Graphics2D) imagen.getGraphics();
        g.setStroke(new BasicStroke(anchoMarco));
        g.setColor(color);
        g.drawRect(0, 0, imagen.getWidth(), imagen.getHeight());
        escribirImagen();
    }
}





