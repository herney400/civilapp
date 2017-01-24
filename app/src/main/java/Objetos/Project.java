package Objetos;

/**
 * Created by N550J on 15/01/2017.
 */

public class Project {

    int idProyecto;
    int idUsuatio;
    String name_project;
    String number_contract;
    String city;
    String direction;
    String fechaCreacion;

    public Project(int idProyecto, int idUsuatio, String name_project, String number_contract, String city, String direction) {

        this.idProyecto = idProyecto;
        this.idUsuatio = idUsuatio;
        this.name_project = name_project;
        this.number_contract = number_contract;
        this.city = city;
        this.direction = direction;
      ///  this.fechaCreacion = fechaCreacion;
    }
    public Project(){}

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public int getIdUsuatio() {
        return idUsuatio;
    }

    public void setIdUsuatio(int idUsuatio) {
        this.idUsuatio = idUsuatio;
    }

    public String getName_project() {
        return name_project;
    }

    public void setName_project(String name_project) {
        this.name_project = name_project;
    }

    public String getNumber_contract() {
        return number_contract;
    }

    public void setNumber_contract(String number_contract) {
        this.number_contract = number_contract;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

 /*   public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }*/

}
