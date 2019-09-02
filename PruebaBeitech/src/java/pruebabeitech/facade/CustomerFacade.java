/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebabeitech.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pruebabeitech.entities.Customer;

/**
 *
 * @author dfcastellanosc
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> implements CustomerFacadeLocal {

    @PersistenceContext(unitName = "PruebaBeitechPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }

    @Override
    public Customer findClientCorreo(String correo) {
        try {
            
            Query q = em.createNamedQuery("Customer.findByEmail");
            q.setParameter("email", correo);
            
            return (Customer)q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
