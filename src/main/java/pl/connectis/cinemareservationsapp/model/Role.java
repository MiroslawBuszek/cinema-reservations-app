package pl.connectis.cinemareservationsapp.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    CLIENT,
    EMPLOYEE;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
