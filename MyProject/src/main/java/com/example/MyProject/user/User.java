package com.example.MyProject.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data //Generates getters and setters for all fields + more (hover)
@Builder //From Lombok. Used for building objects from class
@NoArgsConstructor //Generates a constructor with no parameter
@AllArgsConstructor //Generates a constructor requiring argument for every field in the annotated class
@Entity //The JPA specification requires the @Entity annotation. It identifies a class as an entity class.
@Table(name="_user") //PostgreSQL already has a table called "user", use "_user" instead
public class User implements UserDetails { //UserDetails: Tells spring that this is a Spring Security user class

    @Id //Defines the unique ID
    @GeneratedValue //Everytime its null it should be incremented
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING) //EnumType.ORDINAL is 1, 2, 3. EnumType.STRING is the string value of the constants
    private Role role;


    //ALL @Override METHODS COMES FROM UserDetails! These methods are required by Spring Security

    //Returns a list of roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
