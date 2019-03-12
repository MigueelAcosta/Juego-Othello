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
 * @author MAURICIO
 */
@Entity
@Table(name = "mejoresjugadores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mejoresjugadores.findAll", query = "SELECT m FROM Mejoresjugadores m"),
    @NamedQuery(name = "Mejoresjugadores.findByUsuario", query = "SELECT m FROM Mejoresjugadores m WHERE m.usuario = :usuario"),
    @NamedQuery(name = "Mejoresjugadores.findByNumeroDeVictorias", query = "SELECT m FROM Mejoresjugadores m WHERE m.numeroDeVictorias = :numeroDeVictorias")})
public class Mejoresjugadores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "numeroDeVictorias")
    private Integer numeroDeVictorias;

    public Mejoresjugadores() {
    }

    public Mejoresjugadores(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getNumeroDeVictorias() {
        return numeroDeVictorias;
    }

    public void setNumeroDeVictorias(Integer numeroDeVictorias) {
        this.numeroDeVictorias = numeroDeVictorias;
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
        if (!(object instanceof Mejoresjugadores)) {
            return false;
        }
        Mejoresjugadores other = (Mejoresjugadores) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Persistencia.Mejoresjugadores[ usuario=" + usuario + " ]";
    }    
}
