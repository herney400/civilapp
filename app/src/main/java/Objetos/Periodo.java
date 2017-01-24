package Objetos;

import java.util.Date;

/**
 * Created by N550J on 17/01/2017.
 */

public class Periodo {



    int idPerido;
    String fechaInicial;
    String fechaFinal;
    String fechaCreacion;

    double dineroAsigado;
    int idActividad;
    double costoreal;
    double porcentajeAvance;
    Date fechaIngreso;

    public Periodo(int idPerido, String fechaInicial, String fechaFinal, String fechaCreacion) {
        this.idPerido = idPerido;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.fechaCreacion = fechaCreacion;
    }
    public Periodo( int idPerido,int idActividad, double dineroAsigado, double costoreal, double porcentajeAvance, Date fechaInicial) {
        this.idPerido=idPerido;
        this.idActividad = idActividad;
        this.dineroAsigado = dineroAsigado;
        this.costoreal = costoreal;
        this.porcentajeAvance = porcentajeAvance;
    }

    public Periodo(){}


    public int getIdPerido() {
        return idPerido;
    }

    public void setIdPerido(int idPerido) {
        this.idPerido = idPerido;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public double getDineroAsigado() {
        return dineroAsigado;
    }

    public void setDineroAsigado(double dineroAsigado) {
        this.dineroAsigado = dineroAsigado;
    }

    public String getFechaInicial() {
        return fechaInicial;


    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public double getCostoreal() {
        return costoreal;
    }

    public void setCostoreal(double costoreal) {
        this.costoreal = costoreal;
    }

    public double getPorcentajeAvance() {
        return porcentajeAvance;
    }

    public void setPorcentajeAvance(double porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }




}
