package com.rtkit.upsource_manager.entities.developer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    @Enumerated (EnumType.STRING)
    @NaturalId
    private RoleName role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Developer> developers = new HashSet<>();

    public Role(RoleName role) {
        this.role = role;
    }
    public Role() {}

    public boolean isAdminRole() {
        return this.role.equals(RoleName.ROLE_ADMIN);
    }

    public Long getId() {
        return this.id;
    }

    public RoleName getRole() {
        return this.role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

    public Set<Developer> getDevelopers() {
        return this.developers;
    }

    public void setDeveloper(Set<Developer> developers) {
        this.developers = developers;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role=" + role +
                ", developer=" + developers +
                '}';
    }
}
