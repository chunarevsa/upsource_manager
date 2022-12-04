package com.rtkit.upsource_manager.entities.prticipant;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "participant")
public class Participant {

    @Id
    @Column(name = "participant_id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "participant_seq")
    @SequenceGenerator(name = "participant_seq", allocationSize = 1)
    private Long id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "participant_authority",
            joinColumns = { @JoinColumn(name = "participant_id", referencedColumnName = "participant_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "role_id") })
    private Set<Role> roles = new HashSet<>();

    public Participant() {}

    public Participant(Participant participant) {
        this.id = participant.id;
        this.login = participant.login;
        this.password = participant.password;
        this.active = participant.active;
        this.roles = participant.roles;
    }

    public Participant(Long id,
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
        role.getParticipants().add(this);
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
