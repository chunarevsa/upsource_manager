package com.rtkit.upsource_manager.entities.participant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rtkit.upsource_manager.entities.developer.Developer;
import com.rtkit.upsource_manager.payload.api.response.reviewList.Participant;

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
     * (13eb8051-7373-4d2d-8aa5-1f18f18063b9)
     */
    @Column(name = "user_id")
    public String userId;

    /**
     * Author - 1, Reviewer - 2, Watcher - 3
     */
    public int role;
    public int state;
    public String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id", insertable = false, updatable = false)
    private Developer developer;

    public ParticipantEntity() {
    }

    public ParticipantEntity(Participant participant) {
        this.userId = participant.getUserId();
        this.role = participant.getRole();
        this.state = participant.getState();
    }

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

