package Objetos;

/**
 * Created by N550J on 18/01/2017.
 */

public class Actividad {

    int idActividad;
    int idPoyecto;
    String nombreActividad;
    int diasduracion;
    String iniciacion_primera;
    String finalizacion_primera;
    int costo_total;
    int holguralibre;
    double porcentaje;
    boolean finalizacionCompleta;
    double costoTotal;
    String fecha_inicial;
    String fecha_final;

    public String getIniciacion_primera() {
        return iniciacion_primera;
    }

    public void setIniciacion_primera(String iniciacion_primera) {
        this.iniciacion_primera = iniciacion_primera;
    }

    public String getFinalizacion_primera() {
        return finalizacion_primera;
    }

    public void setFinalizacion_primera(String finalizacion_primera) {
        this.finalizacion_primera = finalizacion_primera;
    }

    public int getCosto_total() {
        return costo_total;
    }

    public void setCosto_total(int costo_total) {
        this.costo_total = costo_total;
    }

    public String getFecha_inicial() {
        return fecha_inicial;
    }

    public void setFecha_inicial(String fecha_inicial) {
        this.fecha_inicial = fecha_inicial;
    }

    public String getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    String fecha_creacion;

    double costoReal;




    public Actividad(int idActividad, int idPoyecto, String nombreActividad, int diasduracion, double costoTotal, double costoReal, int holguralibre, boolean finalizacionCompleta, double porcentaje) {
        this.idActividad = idActividad;
        this.idPoyecto = idPoyecto;
        this.nombreActividad = nombreActividad;
        this.diasduracion = diasduracion;
        this.costoTotal = costoTotal;
        this.costoReal = costoReal;
        this.holguralibre = holguralibre;
        this.finalizacionCompleta = finalizacionCompleta;
        this.porcentaje = porcentaje;
    }
    public  Actividad(){}

    public double getPorcentaje() {

        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public double getCostoReal() {
        return costoReal;
    }

    public void setCostoReal(double costoReal) {
        this.costoReal = costoReal;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }


    public int getIdPoyecto() {
        return idPoyecto;
    }

    public void setIdPoyecto(int idPoyecto) {
        this.idPoyecto = idPoyecto;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public int getDiasduracion() {
        return diasduracion;
    }

    public void setDiasduracion(int diasduracion) {
        this.diasduracion = diasduracion;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public int getHolguralibre() {
        return holguralibre;
    }

    public void setHolguralibre(int holguralibre) {
        this.holguralibre = holguralibre;
    }

    public boolean getFinalizacionCompleta() {
        return finalizacionCompleta;
    }

    public void setFinalizacionCompleta(boolean finalizacionCompleta) {
        this.finalizacionCompleta = finalizacionCompleta;
    }



}
