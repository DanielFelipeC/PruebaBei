/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebabeitech.webservices;

/**
 *
 * @author dfcastellanosc
 */
public class ImplementarWS {

    public ImplementarWS() {
    }
    
    public static Double cotizacion(double total, java.lang.String moneda) {
        pruebabeitech.webservicesclient.CotizarWS_Service service = new pruebabeitech.webservicesclient.CotizarWS_Service();
        pruebabeitech.webservicesclient.CotizarWS port = service.getCotizarWSPort();
        return port.cotizacion(total, moneda);
    }
    
}
