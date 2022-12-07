package com.rtkit.upsource_manager.entities.participant;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
public class ParticipantEntity {

    @Id
    @Column(name = "participant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "developer_id", unique = true)
    @NaturalId
    public String userId;
    public int role;
    public int state;
    public String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "userId='" + userId + '\'' +
                ", role=" + role +
                ", state=" + state +
                ", name='" + name + '\'' +
                '}';
    }
}

