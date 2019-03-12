/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import BaseDeDatos.exceptions.NonexistentEntityException;
import BaseDeDatos.exceptions.PreexistingEntityException;
import Persistencia.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *clase manejadora de los usuarios con conexion a base de datos
 * @author Miguel Acosta
 * @author Mauricio Juarez
 */
public class UsuarioJpaController implements Serializable {

    /**
     * constructor de la clase 
     * @param emf 
     */
    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    /**
     * obtener el manejador de entidades
     * @return manejador de entidades
     */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /** 
     * crear entidad de un usuario en la base de datos 
     * @param usuario usuario que se registrara en la base de datos
     * @throws PreexistingEntityException entidad ya existente
     * @throws Exception excepcion generica
     */
    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getUsuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Editar una entidad de la base de datos
     * @param usuario usuario que se busca editar
     * @throws NonexistentEntityException entidad manejada no existente
     * @throws Exception excepcion generica
     */
    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usuario = em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
     * @param id identificador de la entidad que se desea eliminar
     * @throws NonexistentEntityException Entidad manejada no existente
     */
    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
/**
 * encontrar entidades de usuarios
 * @return lista de entidades
 */
    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }
/**
 * Encuentra entidades de usuario de la base de datos
 * @param maxResults maximo numero de resultados esperados
 * @param firstResult primer resultado
 * @return lista de entidades
 */
    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }
/**
 * Encuentra entidades de usuarios en la base de datos
 * @param all valor de validacion para la obtencion de todas las entidades
 * @param maxResults numero maximo de resultados
 * @param firstResult primero resultado 
 * @return lista de entidades
 */
    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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
     * busca una entidad en la base de datos
     * @param id identificador de la entidad
     * @return 
     */
    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * contador de entidades de la base de datos
     * @return numero de entidades encontradas
     */
    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
