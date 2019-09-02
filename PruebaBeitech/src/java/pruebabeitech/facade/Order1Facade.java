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
import pruebabeitech.entities.Order1;

/**
 *
 * @author dfcastellanosc
 */
@Stateless
public class Order1Facade extends AbstractFacade<Order1> implements Order1FacadeLocal {

    @PersistenceContext(unitName = "PruebaBeitechPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Order1Facade() {
        super(Order1.class);
    }

    @Override
    public List<Order1> findOrdersCliente(int idCliente) {
        try {
            String sentencia = "SELECT * FROM test.order where customer_id = ? ;";

            Query q = em.createNativeQuery(sentencia, Order1.class);

            q.setParameter(1, idCliente);

            return q.getResultList();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public int crearOrden(Order1 ordenNueva) {
        try {
            String sentencia = "INSERT INTO test.order "
                    + "(customer_id, "
                    + "creation_date, "
                    + "delivery_address, "
                    + "total) "
                    + "VALUES "
                    + "(?,?,?,?);";
            
            Query q = em.createNativeQuery(sentencia);
            
            q.setParameter(1, ordenNueva.getCustomerId().getCustomerId());
            q.setParameter(2, ordenNueva.getCreationDate());
            q.setParameter(3, ordenNueva.getDeliveryAddress());
            q.setParameter(4, ordenNueva.getTotal());
            
            return q.executeUpdate();
            
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Order1 findLastOrder() {
        try {
            String sentencia = "SELECT * FROM test.order ord order by  ord.order_id desc limit 1 ;";

            Query q = em.createNativeQuery(sentencia, Order1.class);


            return (Order1)q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
