package Persistencia;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MAURICIO
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario"),
    @NamedQuery(name = "Usuario.findByContrase\u00f1a", query = "SELECT u FROM Usuario u WHERE u.contrase\u00f1a = :contrase\u00f1a"),
    @NamedQuery(name = "Usuario.findByNumerodevictorias", query = "SELECT u FROM Usuario u WHERE u.numerodevictorias = :numerodevictorias")})
public class Usuario implements Serializable, Comparable<Usuario> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "contrase\u00f1a")
    private String contraseña;
    @Column(name = "numerodevictorias")
    private Integer numerodevictorias;

    /**
     * Constructor de la clase
     */
    public Usuario() {
    }
    /**
     * constructor de la clase sobrecargado
     * @param usuario nombre de usuario
     */
    public Usuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * obtener usuario
     * @return usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * establecer nombre de usuario
     * @param usuario nombre de usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
/**
 * obtener contrasena
 * @return contrasena
 */
    public String getContraseña() {
        return contraseña;
    }
/**
 * establecer contrasena 
 * @param contraseña contrasena
 */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
/**
 * obtener numero de victorias
 * @return numero de victorias
 */
    public Integer getNumerodevictorias() {
        return numerodevictorias;
    }
/**
 * establecer numero de victorias
 * @param numerodevictorias numero de victorias
 */
    public void setNumerodevictorias(Integer numerodevictorias) {
        this.numerodevictorias = numerodevictorias;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Persistencia.Usuario[ usuario=" + usuario + " ]";
    }

    @Override
    public int compareTo(Usuario t) {
        if(this.numerodevictorias==t.getNumerodevictorias()){
            return 0;
        }else{
            if(this.numerodevictorias>t.getNumerodevictorias()){
                return -1;
            }else{
                return 1;
            }
        }
    }
}
