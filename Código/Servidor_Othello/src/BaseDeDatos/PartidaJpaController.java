/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import BaseDeDatos.exceptions.NonexistentEntityException;
import Persistencia.Partida;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *clase manejadora de partidas en linea con conexion a base de datos
 * @author MAURICIO
 */
public class PartidaJpaController implements Serializable {

    /**
     * constructor de la clase 
     * @param emf Manejador de entidades
     */
    public PartidaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
/**
 * obtener manejador de entidades
 * @return manejador de entidades
 */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
/**
 * metodo encargado de crear una entidad en la base de datos
 * @param partida partida que se creara como entidad
 */
    public void create(Partida partida) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(partida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * edicion de una entidad de partida
     * @param partida entidad que se busca editar
     * @throws NonexistentEntityException Entidad manejada no existente
     * @throws Exception excepcion generica
     */
    public void edit(Partida partida) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            partida = em.merge(partida);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = partida.getIdPartida();
                if (findPartida(id) == null) {
                    throw new NonexistentEntityException("The partida with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * eliminar una entidad de la base de datos 
     * @param id identificador de entidad que se busca destruir
     * @throws NonexistentEntityException Entidad manejada no existente
     */
    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partida partida;
            try {
                partida = em.getReference(Partida.class, id);
                partida.getIdPartida();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partida with id " + id + " no longer exists.", enfe);
            }
            em.remove(partida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Encuentra las entidades de partidas en la base de datos
     * @return lista de entidades de partida
     */
    public List<Partida> findPartidaEntities() {
        return findPartidaEntities(true, -1, -1);
    }
    /**
     * Encuentra las entidades de partidas en la base de datos
     * @param maxResults numero maximo de resultados requeridos
     * @param firstResult primer resultado esperado
     * @return lista de entidades de partidas
     */
    public List<Partida> findPartidaEntities(int maxResults, int firstResult) {
        return findPartidaEntities(false, maxResults, firstResult);
    }

    /**
     * Encuentra las entidades de partidas en la base de datos
     * @param all valor de validacion de retorno de todas las entidades
     * @param maxResults numero maximo de resultados esperados
     * @param firstResult primer resultado esperado
     * @return lista de entidades de partida
     */
    private List<Partida> findPartidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Partida.class));
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

    /**
     * regresa una entidad de partida de la base de datos
     * @param id identificador de la partida
     * @return la entidad requerida
     */
    public Partida findPartida(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Partida.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * contador de entidades en la base de datos
     * @return numero de entidades encontradas
     */
    public int getPartidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Partida> rt = cq.from(Partida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
