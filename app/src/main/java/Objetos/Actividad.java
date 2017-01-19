package Objetos;

/**
 * Created by N550J on 18/01/2017.
 */

public class Actividad {

    int idActividad;
    int idPoyecto;
    String nombreActividad;
    int diasduracion;
    double costoTotal;
    double costoReal;
    int holguralibre;
    int finalizacionCompleta;
    double porcentaje;

    public Actividad(int idActividad, int idPoyecto, String nombreActividad, int diasduracion, double costoTotal, double costoReal, int holguralibre, int finalizacionCompleta, double porcentaje) {
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

    public int getFinalizacionCompleta() {
        return finalizacionCompleta;
    }

    public void setFinalizacionCompleta(int finalizacionCompleta) {
        this.finalizacionCompleta = finalizacionCompleta;
    }



}
