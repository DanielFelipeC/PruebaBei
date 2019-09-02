/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebabeitech.facade;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pruebabeitech.entities.OrderDetail;

/**
 *
 * @author dfcastellanosc
 */
@Stateless
public class OrderDetailFacade extends AbstractFacade<OrderDetail> implements OrderDetailFacadeLocal {

    @PersistenceContext(unitName = "PruebaBeitechPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderDetailFacade() {
        super(OrderDetail.class);
    }

    @Override
    public List<OrderDetail> findDetailByOrder(int idOrder) {
        try {

            Query q = em.createNamedQuery("OrderDetail.findByOrder");

            q.setParameter("idOrder", idOrder);

            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public int insertDetalleOrden(OrderDetail detalle) {
        try {
            String sentencia = "INSERT INTO test.order_detail "
                    + "(order_id, "
                    + "product_id, "
                    + "product_description, "
                    + "price, "
                    + "quantity) "
                    + "VALUES "
                    + "(?,?,?,?,?);";
            
            Query q = em.createNativeQuery(sentencia);
            
            q.setParameter(1, detalle.getOrderId().getOrderId());
            q.setParameter(2, detalle.getProductId().getProductId());
            q.setParameter(3, detalle.getProductDescription());
            q.setParameter(4, detalle.getPrice());
            q.setParameter(5, detalle.getQuantity());
            
            return q.executeUpdate();
            
        } catch (Exception e) {
            return 0;
        }
    }

}
