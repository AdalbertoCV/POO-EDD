package Menus;

import herramientas.Estadistica.MOMEmociones;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * Clase InterfazMOM: interfaz de usuario con la funcionalidad del MOMEmociones
 * @author Adalberto Cerrillo Vazquez
 * @author Alexia Hernandez Martinez
 * @version 1.0
 */
public class InterfazMOM implements ActionListener {
    /**
     * Nuestra ventana
     */
    private JFrame ventana;
    /**
     * Son las etiquetas necesarias para mostrar la informacion
     */
    private JLabel preguntaA,preguntaB,preguntaC,preguntaD, ResultadoA, ResultadoMuestraA,ResultadoB, ResultadoMuestraB,ResultadoC, ResultadoMuestraC,ResultadoD, ResultadoMuestraD,imgLabel;
    /**
     * Son los campos de texto para ingresar la informacion correspondiente
     */
    private JTextField txtA,txtB,txtB1,txtC,txtC1,txtD;
    /**
     * Son los botones para llamar a los metodos correspondientes
     */
    private JButton btnA,btnB,btnC,btnD,btnLimpiar;
    /**
     * Es el panel principal para agregar los elementos
     */
    private JPanel panel;
    /**
     * Es nuestro objeto para realizar los calculos de probabilidades
     */
    private MOMEmociones mom;

    /**
     * Constructor de la clase para inicializar nuetros atributos
     */
    public InterfazMOM(){
        mom = new MOMEmociones();
        ventana=new JFrame();
        //Labels
         preguntaA=new JLabel("<html>Inserte el animo del cual <br/>quiere calcular probabilidad inicial:</html>");
         preguntaB=new JLabel("<html>Inserte un estado de animo y una actividad</br> para calcular la probabilidad de que se realice: </html>");
         preguntaC=new JLabel("<html>Inserte el estado inicial y el estado siguiente</br> para calcular la probabilidad de que suceda</html>");
         preguntaD=new JLabel("<html>Inserte una cadena separada por comas (',') , </br>con una secuencia de estados de animo para calcular su probabilidad</html>");
        ResultadoA=new JLabel("Resultado:");
        ResultadoB=new JLabel("Resultado:");
        ResultadoC=new JLabel("Resultado:");
        ResultadoD=new JLabel("Resultado:");
        ResultadoMuestraA=new JLabel("");
        ResultadoMuestraB=new JLabel("");
        ResultadoMuestraC=new JLabel("");
        ResultadoMuestraD=new JLabel("");


        //TextField

         txtA=new JTextField(20);
         txtB=new JTextField(20);
         txtB1=new JTextField(20);
         txtC=new JTextField(20);
         txtC1=new JTextField(20);
         txtD=new JTextField(100);



        //Buttons
        btnA=new JButton("Calcular");
        btnA.setActionCommand("B1");
        btnA.addActionListener(this);
        btnB=new JButton("Calcular");
        btnB.setActionCommand("B2");
        btnB.addActionListener(this);
        btnC=new JButton("Calcular");
        btnC.setActionCommand("B3");
        btnC.addActionListener(this);
        btnD=new JButton("Calcular");
        btnD.setActionCommand("B4");
        btnD.addActionListener(this);
        btnLimpiar=new JButton("Limpiar");
        btnLimpiar.setActionCommand("B5");
        btnLimpiar.addActionListener(this);

        panel= new JPanel();
        panel.setLayout(null);
        //Parte A
        preguntaA.setBounds(0, 0, 200, 50);
        txtA.setBounds(15,60,50,20);
        btnA.setBounds(80,60,100,20);
        ResultadoA.setBounds(0,80,100,20);
        ResultadoMuestraA.setBounds(60,80,140,20);
        //Parte B
        preguntaB.setBounds(0,100,200,50);
        txtB.setBounds(5,160,50,20);
        txtB1.setBounds(70,160,50,20);
        btnB.setBounds(145,160,100,20);
        ResultadoB.setBounds(0,180,100,20);
        ResultadoMuestraB.setBounds(60,180,140,20);
        //Parte C
        preguntaC.setBounds(0, 200, 200, 50);
        txtC.setBounds(5,260,50,20);
        txtC1.setBounds(70,260,50,20);
        btnC.setBounds(145,260,100,20);
        ResultadoC.setBounds(0,280,100,20);
        ResultadoMuestraC.setBounds(60,280,140,20);

        //Parte D
        preguntaD.setBounds(0, 300, 200, 80);
        txtD.setBounds(5,380,100,20);
        btnD.setBounds(110,380,100,20);
        ResultadoD.setBounds(0,400,100,20);
        ResultadoMuestraD.setBounds(60,400,140,20);

        //limpiar

        btnLimpiar.setBounds(110,500,100,40);

        //Image
        imgLabel = new JLabel(new ImageIcon("src\\Menus\\escenario.jpg"));
        imgLabel.setBounds(300,0,1000,800);

        panel.add(preguntaA);
        panel.add(txtA);
        panel.add(btnA);
        panel.add(ResultadoA);
        panel.add(ResultadoMuestraA);
        panel.add(preguntaB);
        panel.add(txtB);
        panel.add(txtB1);
        panel.add(btnB);
        panel.add(ResultadoB);
        panel.add(ResultadoMuestraB);
        panel.add(preguntaC);
        panel.add(txtC);
        panel.add(txtC1);
        panel.add(btnC);
        panel.add(ResultadoC);
        panel.add(ResultadoMuestraC);
        panel.add(preguntaD);
        panel.add(txtD);
        panel.add(btnD);
        panel.add(ResultadoD);
        panel.add(ResultadoMuestraD);
        panel.add(imgLabel);
        panel.add(btnLimpiar);

        ventana.add(panel);
        ventana.setDefaultCloseOperation(3);
        ventana.setSize(1400, 1000);
        ventana.setVisible(true);
    }

    /**
     * Metodo sobreescrito para controlar la accion de los botones
     * @param e es el evento lanzado actual (determina que boton ha sido pulsado)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("B1")){
            if (txtA.getText()!=""){
                if(mom.CalcularProbabilidadInicial(txtA.getText())!=null){
                    ResultadoMuestraA.setText(mom.CalcularProbabilidadInicial(txtA.getText())*100+"%");
                }else{
                    ResultadoMuestraA.setText("Datos Incorrectos");
                }

            }
        }
        if (e.getActionCommand().equals("B2")){
            if (txtB.getText()!="" && txtB1.getText()!=""){
                if(mom.CalcularProbabilidadActividad(txtB.getText(),txtB1.getText())!=null) {
                    ResultadoMuestraB.setText(mom.CalcularProbabilidadActividad(txtB.getText(), txtB1.getText()) * 100 + "% ");
                }else{
                    ResultadoMuestraB.setText("Datos Incorrectos");
                }
            }
        }
        if (e.getActionCommand().equals("B3")){
            if (txtC.getText()!="" && txtC1.getText()!="") {
                if (mom.CalcularProbabilidadEstado(txtC.getText(), txtC1.getText()) != null) {
                    ResultadoMuestraC.setText(mom.CalcularProbabilidadEstado(txtC.getText(), txtC1.getText()) * 100 + "%");
                }else{
                    ResultadoMuestraC.setText("Datos Incorrectos");
                }
            }
        }
        if (e.getActionCommand().equals("B4")){
            if (txtD.getText()!=""){
                if(mom.CalcularProbabilidadSecuencia(txtD.getText())!=null){
                    DecimalFormat f = new DecimalFormat("##.00");
                    ResultadoMuestraD.setText(f.format(mom.CalcularProbabilidadSecuencia(txtD.getText())*100)+"%");
                }else{
                    ResultadoMuestraD.setText("Datos Incorrectos");
                }

            }
        }

        if(e.getActionCommand().equals("B5")) {
            ResultadoMuestraA.setText("");
            ResultadoMuestraB.setText("");
            ResultadoMuestraC.setText("");
            ResultadoMuestraD.setText("");
            txtA.setText("");
            txtB.setText("");
            txtB1.setText("");
            txtC.setText("");
            txtC1.setText("");
            txtD.setText("");
        }
    }

    /**
     * Es el metodo main para invocar la interfaz
     * @param args son los argumentos del metodo main
     */
    public static void main (String args[]){
        InterfazMOM GUI = new InterfazMOM();
    }
}
