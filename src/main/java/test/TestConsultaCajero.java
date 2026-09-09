package test;

import datos.Cajero;
import negocio.CajeroABM;

public class TestConsultaCajero {

    public static void main(String[] args) {
        try {
            CajeroABM cajeroAbm = CajeroABM.getInstancia();
            
            Cajero cajero = cajeroAbm.traer(4L);
            
            System.out.println(cajero);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}