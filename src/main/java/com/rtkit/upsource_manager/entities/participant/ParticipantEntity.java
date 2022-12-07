package com.rtkit.upsource_manager.entities.participant;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * Участник в конкретном Review.
 */
@Entity
public class ParticipantEntity {

    @Id
    @Column(name = "participant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Уникальный для конкретного Review (13eb8051-7373-4d2d-8aa5-1f18f18063b9)
     */
    @Column(name = "user_id", unique = true)
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
