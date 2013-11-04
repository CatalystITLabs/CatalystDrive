package com.catalyst.drive.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Example Enum defining application specific authorities for use in ActiveDirectoryGrantedAuthoritiesMapper
 * @author kpolen
 *
 */
public enum CatalystAuthority implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN, ROLE_OTHER;

    /**
     * Method which returns an CatalystAuthority enum value as a String
     */
    public String getAuthority() {
        return name();
    }
}
	
