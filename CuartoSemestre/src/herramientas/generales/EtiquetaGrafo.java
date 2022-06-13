package herramientas.generales;

public class EtiquetaGrafo {
    protected double metricaAcumulada;
    protected int verticeAnterior;
    protected int interacion;

    public double getMetricaAcumulada() {
        return metricaAcumulada;
    }

    public void setMetricaAcumulada(double metricaAcumulada) {
        this.metricaAcumulada = metricaAcumulada;
    }

    public int getVerticeAnterior() {
        return verticeAnterior;
    }

    public void setVerticeAnterior(int verticeAnterior) {
        this.verticeAnterior = verticeAnterior;
    }

    public int getInteracion() {
        return interacion;
    }

    public void setInteracion(int interacion) {
        this.interacion = interacion;
    }

    @Override
    public String toString(){
        return "[" + metricaAcumulada + ", "+ verticeAnterior +"] " + interacion;
    }
}
