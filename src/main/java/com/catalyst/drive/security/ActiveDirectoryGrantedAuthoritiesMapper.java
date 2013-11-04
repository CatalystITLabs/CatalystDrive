package com.catalyst.drive.security;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

/**
 * FOR APPLICATIONS WHICH CAN DIRECTLY MAP LDAP ROLES TO APPLICATION ROLES
 * 
 * NOTE:  THIS IS FOR DEMONSTRATION PURPOSES ONLY--IN THE DEFAULT CONFIGURATION, UserDetailsContextMapperImpl IS USED
 * IN PLACE OF THE STRATEGY IMPLEMENTED BY THIS CLASS.
 * 
 * This class can be used to map roles from ldap to roles specific to this application as defined in 
 * the CatalystAuthority enum.  Notice that the username is not available in the method parameters  of this implementation, 
 * so using this implementation is much more limited (though simpler) than using the UserDetailsContextMapperImpl
 * @author kpolen
 *
 */
public class ActiveDirectoryGrantedAuthoritiesMapper implements GrantedAuthoritiesMapper {
    //ldap roles to be mapped
	public static final String ROLE_MD_ASSOCIATES = "MDAssociates";
    public static final String ROLE_MD_DEVELOPERS = "MDDevelopers";
    
    /**
     * Default constructor
     */
    public ActiveDirectoryGrantedAuthoritiesMapper() {       
    }
 
    /**
     * Method which maps ldap roles to app-specific roles and returns a collection of app-specific 
     * roles for the user who has logged in
     */
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<CatalystAuthority> roles = EnumSet.noneOf(CatalystAuthority.class);
        //Loops through ldap authorities for this user and adds the corresponding app-specific roles to 
        //the user's collection of roles
        for (GrantedAuthority authority : authorities) {
            if (ROLE_MD_ASSOCIATES.equalsIgnoreCase(authority.getAuthority())) {
                roles.add(CatalystAuthority.ROLE_USER);
            } else if (ROLE_MD_DEVELOPERS.equalsIgnoreCase(authority.getAuthority())) {
                roles.add(CatalystAuthority.ROLE_ADMIN);
            }
            else{
            	roles.add(CatalystAuthority.ROLE_OTHER);
            }
        }
 
        return roles;
    }
}
