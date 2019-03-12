/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import BaseDeDatos.exceptions.NonexistentEntityException;
import BaseDeDatos.exceptions.PreexistingEntityException;
import Persistencia.Partidacontracpu;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *Clase encargada del manejo de partidas contra cpu con conexion a la base de datos
 * @author Miguel Acosta
 * @author Mauricio Juarez
 */
public class PartidacontracpuJpaController implements Serializable {

    /**
     * constructor de la clase
     * @param emf Entity Manager Factory
     */
    public PartidacontracpuJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
/**
 * recupera el manejador de entidades
 * @return manejador de entidades
 */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Crear una entidad de partida contra cpu
     * @param partidacontracpu partida que se creara en la base de datos
     * @throws PreexistingEntityException entidad ya existente
     * @throws Exception excepcion de conexion
     */
    public void create(Partidacontracpu partidacontracpu) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(partidacontracpu);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPartidacontracpu(partidacontracpu.getUsuario()) != null) {
                throw new PreexistingEntityException("Partidacontracpu " + partidacontracpu + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * editar entidad de partida contra cpu
     * @param partidacontracpu partida que se va a editar
     * @throws NonexistentEntityException entidad manejada no existente
     * @throws Exception error generico de conexion
     */
    public void edit(Partidacontracpu partidacontracpu) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            partidacontracpu = em.merge(partidacontracpu);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = partidacontracpu.getUsuario();
                if (findPartidacontracpu(id) == null) {
                    throw new NonexistentEntityException("The partidacontracpu with id " + id + " no longer exists.");
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
     * Eliminar una entidad de la base de datos
     * @param id identificador de la entidad que se busca elminar
     * @throws NonexistentEntityException 
     */
    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partidacontracpu partidacontracpu;
            try {
                partidacontracpu = em.getReference(Partidacontracpu.class, id);
                partidacontracpu.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partidacontracpu with id " + id + " no longer exists.", enfe);
            }
            em.remove(partidacontracpu);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
/**
 * Encuentra las entidades de partidas contra cpu
 * @return lista de entidades
 */
    public List<Partidacontracpu> findPartidacontracpuEntities() {
        return findPartidacontracpuEntities(true, -1, -1);
    }
/**
 * encuentra las entidades de partidas contra cpu
 * @param maxResults numero de resultados
 * @param firstResult primer resultado
 * @return lista de entidades
 */
    public List<Partidacontracpu> findPartidacontracpuEntities(int maxResults, int firstResult) {
        return findPartidacontracpuEntities(false, maxResults, firstResult);
    }

    /**
     * encuentra entidades de partidas contra cpu
     * @param all valor de validacion de retorno de todas las entidades
     * @param maxResults numero maximo de resultados
     * @param firstResult primer resultado
     * @return lista de entidades de partidas 
     */
    private List<Partidacontracpu> findPartidacontracpuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Partidacontracpu.class));
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
     * regresa una partida contra la cpu
     * @param id identificador de la partida
     * @return la entidad encontrada
     */
    public Partidacontracpu findPartidacontracpu(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Partidacontracpu.class, id);
        } finally {
            em.close();
        }
    }
/**
 * contador de entidaes de partidas contra cpu
 * @return numero de entidades encontradas
 */
    public int getPartidacontracpuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Partidacontracpu> rt = cq.from(Partidacontracpu.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
