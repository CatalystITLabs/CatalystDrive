package com.catalyst.drive.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.catalyst.drive.entities.Role;

/**
*
* @author kpolen
*/
@Entity
@Table(name = "role")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r"),
   @NamedQuery(name = "Role.findById", query = "SELECT r FROM Role r WHERE r.id = :id"),
   @NamedQuery(name = "Role.findByRolename", query = "SELECT r FROM Role r WHERE r.rolename = :rolename")})
public class Role implements Serializable {
   private static final long serialVersionUID = 2L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Basic(optional = false)
   @Column(name = "id")
   private Integer id;
   @Basic(optional = false)
   @Column(name = "rolename")
   private String rolename;
   @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
   private Set<User> users;

   public Role() {
   }

   public Integer getId() {
       return id;
   }

   public void setId(Integer id) {
       this.id = id;
   }

   public String getRolename() {
       return rolename;
   }

   public void setRolename(String rolename) {
       this.rolename = rolename;
   }

   public Set<User> getUsers() {
       return users;
   }

   public void setUsers(Set<User> users) {
       this.users = users;
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
       if (!(object instanceof Role)) {
           return false;
       }
       Role other = (Role) object;
       if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
           return false;
       }
       return true;
   }

   @Override
   public String toString() {
       return "entities.Role[ id=" + id + " ]";
   }
   
}

