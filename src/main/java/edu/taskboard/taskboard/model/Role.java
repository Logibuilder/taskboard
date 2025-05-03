package edu.taskboard.taskboard.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role  implements GrantedAuthority {
    ADMIN, USER;

    public String getAuthority() {
        return name();
    }
}


