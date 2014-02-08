/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.EntitesControl;

import com.ACME.Entites.Homeloan;
import com.ACME.EntitesControl.exceptions.NonexistentEntityException;
import com.ACME.EntitesControl.exceptions.PreexistingEntityException;
import com.ACME.EntitesControl.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author WEIQIANGLIANG
 */
public class HomeloanJpaController implements Serializable {

    public HomeloanJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Homeloan homeloan) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(homeloan);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findHomeloan(homeloan.getAccNum()) != null) {
                throw new PreexistingEntityException("Homeloan " + homeloan + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Homeloan homeloan) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            homeloan = em.merge(homeloan);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = homeloan.getAccNum();
                if (findHomeloan(id) == null) {
                    throw new NonexistentEntityException("The homeloan with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Homeloan homeloan;
            try {
                homeloan = em.getReference(Homeloan.class, id);
                homeloan.getAccNum();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The homeloan with id " + id + " no longer exists.", enfe);
            }
            em.remove(homeloan);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Homeloan> findHomeloanEntities() {
        return findHomeloanEntities(true, -1, -1);
    }

    public List<Homeloan> findHomeloanEntities(int maxResults, int firstResult) {
        return findHomeloanEntities(false, maxResults, firstResult);
    }

    private List<Homeloan> findHomeloanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Homeloan.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Homeloan findHomeloan(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Homeloan.class, id);
        } finally {
            em.close();
        }
    }

    public int getHomeloanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Homeloan> rt = cq.from(Homeloan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
