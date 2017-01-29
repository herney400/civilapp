package Objetos;

/**
 * Created by N550J on 28/01/2017.
 */

public class Causal {

   int IDCAUSAL;
    String NOMBRE;
    String DESCRIPCION;

    @Override
    public String toString() {
        return "Causal{" +
                "NOMBRE='" + NOMBRE + '\'' +
                '}';
    }

    public int getIDCAUSAL() {
        return IDCAUSAL;
    }

    public void setIDCAUSAL(int IDCAUSAL) {
        this.IDCAUSAL = IDCAUSAL;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }




}
