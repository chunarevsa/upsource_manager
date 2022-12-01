package com.rtkit.upsource_manager.entities.user;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "developer")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "role_id") })
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(User user) {
        this.id = user.id;
        this.login = user.login;
        this.password = user.password;
        this.active = user.active;
        this.roles = user.roles;
    }

    public User(Long id,
                String login,
                String password,
                Boolean active,
                Set<Role> roles) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void addRoles(Set<Role> roles) {
        roles.forEach(this::addRole);
    }

    public Long getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isActive() {
        return this.active;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", password='" + getPassword() + "'" +
                ", active='" + isActive() + "'" +
                ", roles='" + getRoles() + "'" +
                "}";
    }
}
