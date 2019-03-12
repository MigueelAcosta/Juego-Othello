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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "partida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partida.findAll", query = "SELECT p FROM Partida p"),
    @NamedQuery(name = "Partida.findByIdPartida", query = "SELECT p FROM Partida p WHERE p.idPartida = :idPartida"),
    @NamedQuery(name = "Partida.findByUsuario", query = "SELECT p FROM Partida p WHERE p.usuario = :usuario"),
    @NamedQuery(name = "Partida.findByFecha", query = "SELECT p FROM Partida p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "Partida.findByAdversario", query = "SELECT p FROM Partida p WHERE p.adversario = :adversario"),
    @NamedQuery(name = "Partida.findByDuracion", query = "SELECT p FROM Partida p WHERE p.duracion = :duracion"),
    @NamedQuery(name = "Partida.findByResultado", query = "SELECT p FROM Partida p WHERE p.resultado = :resultado")})
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPartida")
    private Integer idPartida;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "fecha")
    private String fecha;
    @Column(name = "adversario")
    private String adversario;
    @Column(name = "duracion")
    private String duracion;
    @Column(name = "resultado")
    private String resultado;

    /**
     * Constructor de la clase
     */
    public Partida() {
    }

    /**
     * constructor sobrecargado
     * @param idPartida identificador de la partida
     */
    public Partida(Integer idPartida) {
        this.idPartida = idPartida;
    }
/**
 * obtener identificador de partida
 * @return identificador de la partida
 */
    public Integer getIdPartida() {
        return idPartida;
    }

    /**
     * establecer identificador de partida
     * @param idPartida identificador de partida
     */
    public void setIdPartida(Integer idPartida) {
        this.idPartida = idPartida;
    }

    /**
     * Obtener nombre de usuario
     * @return nombre de usuario
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
     * obtener fecha de la partida
     * @return fecha de la partida
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * establecer fecha de la partida
     * @param fecha fecha de la partida
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
/**
 * obtener adversario
 * @return adversario en la partida
 */
    public String getAdversario() {
        return adversario;
    }

    /**
     * establecer adversario 
     * @param adversario adversario de la partida
     */
    public void setAdversario(String adversario) {
        this.adversario = adversario;
    }

    /**
     * obtener duracion de la partida
     * @return duracion de la partida
     */
    public String getDuracion() {
        return duracion;
    }
/**
 * establecer duracion de la partida
 * @param duracion duracion de la partida
 */
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
/**
 * obtener resultado
 * @return resultado de la partida
 */
    public String getResultado() {
        return resultado;
    }

    /** 
     * establecer resultado
     * @param resultado resultado de la partida
     */
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPartida != null ? idPartida.hashCode() : 0);
        return hash;
    }

    /**
     * sobreescritura del metodo equals
     * @param object objeto a comparar
     * @return 
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partida)) {
            return false;
        }
        Partida other = (Partida) object;
        if ((this.idPartida == null && other.idPartida != null) || (this.idPartida != null && !this.idPartida.equals(other.idPartida))) {
            return false;
        }
        return true;
    }

    /**
     * sohreescritura del metodo toString
     * @return una cadena convertida
     */
    @Override
    public String toString() {
        return "Persistencia.Partida[ idPartida=" + idPartida + " ]";
    }
    
}
