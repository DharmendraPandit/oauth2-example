package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "USERS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordConfirm;
    private String userType;
    private Date createdOn = new Date();
    private Date updatedOn = new Date();
    private Date lastLogin;
    private String resetToken;
    private boolean enabled = true;
    private boolean accountNonLocked = true;

    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
