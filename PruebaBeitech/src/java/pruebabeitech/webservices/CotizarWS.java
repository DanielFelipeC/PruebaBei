/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebabeitech.webservices;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author dfcastellanosc
 */
@WebService(serviceName = "CotizarWS")
public class CotizarWS {

    private final double Dolar = 3453.00;
    private final double Euro = 3787.08;
    private final double Libra = 4164.97;

    /**
     * Web service operation
     *
     * @param total
     * @param moneda
     * @return
     */
    @WebMethod(operationName = "cotizacion")
    public Double cotizacion(@WebParam(name = "total") double total, @WebParam(name = "moneda") String moneda) {
        double valorTotal = 0;
        switch (moneda) {
            case "Dolar":
                valorTotal = (total * this.Dolar);
                valorTotal = valorTotal + (valorTotal * 0.16);
                break;
            case "Euro":
                valorTotal = (total * this.Euro);
                valorTotal = valorTotal + (valorTotal * 0.16);
                break;
            case "Libra":
                valorTotal = (total * this.Libra);
                valorTotal = valorTotal + (valorTotal * 0.16);
                break;
            default:
                valorTotal = total +(total * 0.16);
                break;
        }
        return valorTotal;
    }

}
