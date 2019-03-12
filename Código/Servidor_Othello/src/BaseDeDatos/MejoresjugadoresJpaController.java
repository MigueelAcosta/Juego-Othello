/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import BaseDeDatos.exceptions.NonexistentEntityException;
import BaseDeDatos.exceptions.PreexistingEntityException;
import Persistencia.Mejoresjugadores;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *Clase encargada de la conexion con la base de datos
 * @author Miguel Acosta
 * @author Mauricio Juarez3
 */
public class MejoresjugadoresJpaController implements Serializable {

    /**
     * COnstructor de la clase
     * @param emf  manejador de entidades
     */
    public MejoresjugadoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    /**
     * Obtiene el manejador de entidades
     * @return el manejador de entidaes
     */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Creacion de una entidad de mejores jugadores
     * @param mejoresjugadores objeto mejores jugadores
     * @throws PreexistingEntityException la entidad ya existe
     * @throws Exception excepcion
     */
    public void create(Mejoresjugadores mejoresjugadores) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(mejoresjugadores);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMejoresjugadores(mejoresjugadores.getUsuario()) != null) {
                throw new PreexistingEntityException("Mejoresjugadores " + mejoresjugadores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** 
     * Edicion de una entidad de la base de datos
     * @param mejoresjugadores objeto mejores jugadores
     * @throws NonexistentEntityException manejor de una entidad no creada
     * @throws Exception error en la conexion
     */
    public void edit(Mejoresjugadores mejoresjugadores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            mejoresjugadores = em.merge(mejoresjugadores);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = mejoresjugadores.getUsuario();
                if (findMejoresjugadores(id) == null) {
                    throw new NonexistentEntityException("The mejoresjugadores with id " + id + " no longer exists.");
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
     * eliminar o destruir una entidad de la base de datos
     * @param id identificador de la entidad
     * @throws NonexistentEntityException manejo de una entidad no existente
     */
    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mejoresjugadores mejoresjugadores;
            try {
                mejoresjugadores = em.getReference(Mejoresjugadores.class, id);
                mejoresjugadores.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mejoresjugadores with id " + id + " no longer exists.", enfe);
            }
            em.remove(mejoresjugadores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
/**
 * Recoleccion de los mejores jugadores
 * @return lista con objetos tipo MejoresJugadores
 */
    public List<Mejoresjugadores> findMejoresjugadoresEntities() {
        return findMejoresjugadoresEntities(true, -1, -1);
    }

    /**
     * sobre escritura del metodo findMejoresjugadoresEntities con rango de busqueda
     * @param maxResults resultado maximo
     * @param firstResult primer resultado
     * @return lista de MejoresJugadores
     */
    public List<Mejoresjugadores> findMejoresjugadoresEntities(int maxResults, int firstResult) {
        return findMejoresjugadoresEntities(false, maxResults, firstResult);
    }
/**
 * sobre escritura del metodo findMejoresjugadoresEntities con rango de busqueda y seleccion de todos los registros
 * @param all valor para validacion del rango de recuperacion
 * @param maxResults maximo resultado recuperado
 * @param firstResult primer resultado
 * @return lista de los MejoresJugadores
 */
    private List<Mejoresjugadores> findMejoresjugadoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mejoresjugadores.class));
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
 * Regresa el jugador con la puntuacion mas alta
 * @param id identificador del jugador
 * @return 
 */
    public Mejoresjugadores findMejoresjugadores(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mejoresjugadores.class, id);
        } finally {
            em.close();
        }
    }
/**
 * contador de mejores contradores
 * @return numero entero con los mejores jugadores
 */
    public int getMejoresjugadoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mejoresjugadores> rt = cq.from(Mejoresjugadores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
