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
import pruebabeitech.entities.Product;

/**
 *
 * @author dfcastellanosc
 */
@Stateless
public class ProductFacade extends AbstractFacade<Product> implements ProductFacadeLocal {

    @PersistenceContext(unitName = "PruebaBeitechPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(Product.class);
    }

    @Override
    public List<Product> findProductsCliente(String correo) {
        try {
            String sentencia = "select pro.* from test.product pro "
                    + "inner join test.customer_product cpr on pro.product_id = cpr.product_id "
                    + "inner join test.customer cus on cus.customer_id = cpr.customer_id "
                    + "where cus.email = '" + correo + "' ;";
            Query q = em.createNativeQuery(sentencia, Product.class);

            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
