/**



free.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class GraficarValores extends JFrame {
    private static final long serialVersionUID = 6294689542092367723L;
    ListaDinamica vectorX;
    ListaDinamica vectorY;

    public GraficarValores(String title, String archivox, String archivoy) {
        super(title);
        vectorX = leerArchivo(archivox);
        vectorY = leerArchivo(archivoy);
        XYDataset dataset = createDataset();
        JFreeChart chart = ChartFactory.createScatterPlot("Grafica valores X,Y habitaciones-Precio casas", "X", "Y", dataset);
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(new Color(255,190,100));
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private ListaDinamica leerArchivo(String archivo){
        try {
            ListaDinamica listaLineas = new ListaDinamica();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo));
            String linea = bufferedReader.readLine();
            while (linea != null) {
                listaLineas.agregar(linea);
                linea = bufferedReader.readLine();
            }
            bufferedReader.close();
            return listaLineas;
        } catch (Exception e) {
            return null;
        }

    }

    private XYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries xy = new XYSeries("X,Y");
        Nodo iteradorx = vectorX.getPrimerNodo();
        Nodo iteradory = vectorY.getPrimerNodo();
        while (iteradorx!=null){
            xy.add(Double.parseDouble((String)iteradorx.getContenido()),Double.parseDouble((String)iteradory.getContenido()));
            iteradorx = iteradorx.getNodoDer();
            iteradory = iteradory.getNodoDer();
        }
        dataset.addSeries(xy);
        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraficarValores example = new GraficarValores("Tabla xy", "src\\Examen1\\Programa2\\X.txt","src\\Examen1\\Programa2\\Y.txt");
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}**/