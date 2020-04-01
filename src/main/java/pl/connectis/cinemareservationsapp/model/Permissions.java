package pl.connectis.cinemareservationsapp.model;

import org.springframework.security.core.GrantedAuthority;

public enum Permissions implements GrantedAuthority {

    CLIENT_ACCESS,
    EMPLOYEE_ACCESS;

    @Override
    public String getAuthority() {
        return name();
    }

}

