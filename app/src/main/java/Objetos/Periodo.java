package Objetos;

import java.util.Date;

/**
 * Created by N550J on 17/01/2017.
 */

public class Periodo {

    int idPerido;
    int idActividad;
    double dineroAsigado;
    Date fechaInicial;
    Date fechaFinal;
    double costoreal;
    double porcentajeAvance;
    Date fechaIngreso;
    Date fechaCreacion;

    public Periodo( int idPerido,int idActividad, double dineroAsigado, double costoreal, double porcentajeAvance) {
        this.idPerido=idPerido;
        this.idActividad = idActividad;
        this.dineroAsigado = dineroAsigado;
        this.costoreal = costoreal;
        this.porcentajeAvance = porcentajeAvance;
    }


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

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }




}
