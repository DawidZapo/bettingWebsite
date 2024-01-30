package com.bettingwebsite.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "seeded")
    private Integer seeded;

    public Player() {
    }

    public Player(String firstName, String lastName, Integer seeded) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.seeded = seeded;
    }

    public Player(Long id, String firstName, String lastName, Integer seeded) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.seeded = seeded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getSeeded() {
        return seeded;
    }

    public void setSeeded(Integer seeded) {
        this.seeded = seeded;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", seeded=" + seeded +
                '}';
    }
}
