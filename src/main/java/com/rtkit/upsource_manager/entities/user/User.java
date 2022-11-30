package com.rtkit.upsource_manager.entities.user;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "login", unique = true, nullable = false)
    @NotNull(message = "Login cannot be null")
    private String login;

    @Column(name = "password")
    @NotNull(message = "Password cannot be null")
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User() {}

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
