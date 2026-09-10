package test;

import negocio.PedidoABM;

public class TestReporteDeRecaudaciones {
    public static void main(String[] args) {
    	
    	
        PedidoABM abm = PedidoABM.getInstancia();
        abm.generarReporteRecaudaciones();
    }
}