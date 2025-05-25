package edu.taskboard.taskboard.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Data
@Table(name = "users")
public class User  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;


    private String name;


    private String email;

    @Enumerated(EnumType.STRING)
    private Role role ;

    private String color;

    private String password;

    private boolean active = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getUsername() {
        return email;
    }

}
