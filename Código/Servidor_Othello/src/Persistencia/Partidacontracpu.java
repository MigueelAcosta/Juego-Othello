/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author XDrakeK
 */
@Entity
@Table(name = "partidacontracpu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partidacontracpu.findAll", query = "SELECT p FROM Partidacontracpu p")
    , @NamedQuery(name = "Partidacontracpu.findByUsuario", query = "SELECT p FROM Partidacontracpu p WHERE p.usuario = :usuario")
    , @NamedQuery(name = "Partidacontracpu.findByJuego", query = "SELECT p FROM Partidacontracpu p WHERE p.juego = :juego")})
public class Partidacontracpu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "juego")
    private String juego;

    /**
     * Constructor de la clase
     */
    public Partidacontracpu() {
    }
/**
 * constructor sobrecargado de la clase
 * @param usuario usuario de la partida
 */
    public Partidacontracpu(String usuario) {
        this.usuario = usuario;
    }
/**
 * constructor sobrecargado de la clase
 * @param usuario usuario de la partida
 * @param juego partida antes creada con el usuario
    */
    public Partidacontracpu(String usuario, String juego) {
        this.usuario = usuario;
        this.juego = juego;
    }

    /**
     * obtener usuario de la partida
     * @return usuario
     */
    public String getUsuario() {
        return usuario;
    }
/**
 * establecer usuario de la partida
 * @param usuario usuario
 */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
/**
 * obtener estado de un juego
 * @return partida
 */
    public String getJuego() {
        return juego;
    }
/**
 * establecer estado de un juego
 * @param juego estado del juego
 */
    public void setJuego(String juego) {
        this.juego = juego;
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
        if (!(object instanceof Partidacontracpu)) {
            return false;
        }
        Partidacontracpu other = (Partidacontracpu) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Persistencia.Partidacontracpu[ usuario=" + usuario + " ]";
    }
    
}
