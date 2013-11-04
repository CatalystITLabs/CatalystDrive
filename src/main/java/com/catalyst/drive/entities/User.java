package com.catalyst.drive.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.catalyst.drive.entities.User;

/**
*
* @author kpolen
*/
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
   @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
   @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")})
public class User implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Basic(optional = false)
   @Column(name = "id")
   private Integer id;
   @Basic(optional = false)
   @Column(name = "username")
   private String username;
   @Basic(optional = true)
   @Column(name = "password")
   private String password;
   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinTable(name = "userrole", 
   				joinColumns = { @JoinColumn(name = "user") }, 
   				inverseJoinColumns = { @JoinColumn(name = "role") })
   private Set<Role> roles;

   public User() {
   }

   public Integer getId() {
       return id;
   }

   public void setId(Integer id) {
       this.id = id;
   }

   public String getUsername() {
       return username;
   }

   public void setUsername(String username) {
       this.username = username;
   }

   /**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

   public Set<Role> getRoles() {
       return roles;
   }

   public void setRoles(Set<Role> roles) {
       this.roles = roles;
   }

   @Override
   public int hashCode() {
       int hash = 0;
       hash += (id != null ? id.hashCode() : 0);
       return hash;
   }

   @Override
   public boolean equals(Object object) {
       // TODO: Warning - this method won't work in the case the id fields are not set
       if (!(object instanceof User)) {
           return false;
       }
       User other = (User) object;
       if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
           return false;
       }
       return true;
   }

   @Override
   public String toString() {
       return "entities.User[ id=" + id + " ]";
   }
   
}

