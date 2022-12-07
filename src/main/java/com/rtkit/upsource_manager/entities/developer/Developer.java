package com.rtkit.upsource_manager.entities.developer;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "developer")
public class Developer {

    @Id
    @Column(name = "developer_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "developer_seq")
    @SequenceGenerator(name = "developer_seq", allocationSize = 1)
    private Long id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password")
    private String password = "";

    @Column(name = "is_active", nullable = false)
    private Boolean active;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeveloperStatus status = DeveloperStatus.NOT_VERIFIED;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "developer_authority",
            joinColumns = {@JoinColumn(name = "developer_id", referencedColumnName = "developer_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    private Set<Role> roles = new HashSet<>();

    public Developer() {
    }

    public Developer(Developer developer) {
        this.id = developer.id;
        this.login = developer.login;
        this.password = developer.password;
        this.active = developer.active;
        this.roles = developer.roles;
    }

    public Developer(Long id,
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
        role.getDevelopers().add(this);
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

    public DeveloperStatus getStatus() {
        return status;
    }

    public void setStatus(DeveloperStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", active=" + active +
                ", status=" + status +
                ", roles=" + roles +
                '}';
    }
}
