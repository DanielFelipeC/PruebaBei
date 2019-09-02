/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebabeitech.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import pruebabeitech.entities.Customer;
import pruebabeitech.entities.Order1;
import pruebabeitech.entities.OrderDetail;
import pruebabeitech.entities.Product;
import pruebabeitech.facade.CustomerFacadeLocal;
import pruebabeitech.facade.Order1FacadeLocal;
import pruebabeitech.facade.OrderDetailFacadeLocal;
import pruebabeitech.facade.ProductFacadeLocal;
import pruebabeitech.webservices.ImplementarWS;

/**
 *
 * @author dfcastellanosc
 */
@Named(value = "productoView")
@ViewScoped
public class ProductoView implements Serializable {

    @EJB
    private CustomerFacadeLocal facadeCliente;

    @EJB
    private ProductFacadeLocal facadeProducto;

    @EJB
    private Order1FacadeLocal facadeOrder;

    @EJB
    private OrderDetailFacadeLocal facadeDetail;

    private String correoCliente;

    private String moneda;

    private String direccionEntrega;

    private Customer cliente;
    private List<Product> productosCliente;
    private List<Order1> ordenesCliente;
    private List<OrderDetail> detalleOrden;

    private List<Product> productosCarritoOrdenNueva;

    /**
     * Creates a new instance of ProductoView
     */
    public ProductoView() {
        productosCliente = new ArrayList<>();
        productosCarritoOrdenNueva = new ArrayList<>();
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public Customer getCliente() {
        return cliente;
    }

    public void setCliente(Customer cliente) {
        this.cliente = cliente;
    }

    public List<Product> getProductosCliente() {
        return productosCliente;
    }

    public void setProductosCliente(List<Product> productosCliente) {
        this.productosCliente = productosCliente;
    }

    public List<Order1> getOrdenesCliente() {
        return ordenesCliente;
    }

    public void setOrdenesCliente(List<Order1> ordenesCliente) {
        this.ordenesCliente = ordenesCliente;
    }

    public List<OrderDetail> getDetalleOrden() {
        return detalleOrden;
    }

    public void setDetalleOrden(List<OrderDetail> detalleOrden) {
        this.detalleOrden = detalleOrden;
    }

    public List<Product> getProductosCarritoOrdenNueva() {
        return productosCarritoOrdenNueva;
    }

    public void setProductosCarritoOrdenNueva(List<Product> productosCarritoOrdenNueva) {
        this.productosCarritoOrdenNueva = productosCarritoOrdenNueva;
    }

    public void buscarProductorCliente() {
        cliente = facadeCliente.findClientCorreo(correoCliente);

        productosCliente = facadeProducto.findProductsCliente(correoCliente);

        ordenesCliente = facadeOrder.findOrdersCliente(cliente.getCustomerId());
    }

    public void buscarDetalleOrden(int idOrden) {
        detalleOrden = facadeDetail.findDetailByOrder(idOrden);
    }

    public void agregarProductoCarrito(Product prod) {
        if (productosCarritoOrdenNueva.size() < 5) {
            productosCarritoOrdenNueva.add(prod);
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Limite de Productos", "Solo puede agregar 5 productos a la orden"));
        }
    }

    public void eliminarProductoCarrito(Product prod) {
        productosCarritoOrdenNueva.remove(prod);
    }

    public void crearOrdenCliente() {
        Order1 nuevaOrden = new Order1();

        HashMap<Integer, Integer> cantidadPro = new HashMap<>();

        double total = 0;
        for (Product product : productosCarritoOrdenNueva) {
            total = total + product.getPrice();

            if (cantidadPro.containsKey(product.getProductId())) {
                Integer cant = cantidadPro.get(product.getProductId());
                cantidadPro.put(product.getProductId(), cant++);
            } else {
                cantidadPro.put(product.getProductId(), 1);
            }
        }

        nuevaOrden.setCustomerId(cliente);
        nuevaOrden.setCreationDate(new Date());
        nuevaOrden.setDeliveryAddress(direccionEntrega);

        ImplementarWS imp = new ImplementarWS();
        Double valorTotal = imp.cotizacion(total, moneda);

        nuevaOrden.setTotal(valorTotal.doubleValue());

        int result = facadeOrder.crearOrden(nuevaOrden);

        nuevaOrden = facadeOrder.findLastOrder();

        for (Product product : productosCarritoOrdenNueva) {
            if (cantidadPro.containsKey(product.getProductId())) {
                OrderDetail detalle = new OrderDetail();
                detalle.setOrderId(nuevaOrden);
                detalle.setProductId(product);
                detalle.setProductDescription(product.getProductDescription());
                detalle.setQuantity(cantidadPro.get(product.getProductId()));
                detalle.setPrice(cantidadPro.get(product.getProductId()) * product.getPrice());

                facadeDetail.insertDetalleOrden(detalle);
            }

        }

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Orden Generada", "Su orden fue creada correctamente"));

        direccionEntrega = "";
        productosCarritoOrdenNueva.clear();
    }
}
