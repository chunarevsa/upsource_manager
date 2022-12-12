package com.rtkit.upsource_manager.entities.developer;

import com.rtkit.upsource_manager.entities.participant.ParticipantEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "developer")
public class Developer {

    @Id
    @Column(name = "developer_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "developer_seq")
    @SequenceGenerator(name = "developer_seq", allocationSize = 1)
    private Long id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password = "";

    @Column(name = "userId")
    private String userId;

    @Column(name = "name")
    private String name;

    @Column(name = "avatarUrl")
    private String avatarUrl;

    @Column(name = "email")
    private String email;

    @Column(name = "profileUrl")
    private String profileUrl;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "developer_id")
    private List<ParticipantEntity> participants;

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

    public void addRole(Role role) {
        roles.add(role);
        role.getDevelopers().add(this);
    }

    public void addRoles(Set<Role> roles) {
        roles.forEach(this::addRole);
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public List<ParticipantEntity> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantEntity> participants) {
        this.participants = participants;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public DeveloperStatus getStatus() {
        return status;
    }

    public void setStatus(DeveloperStatus status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
