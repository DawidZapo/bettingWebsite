package com.bettingwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

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
    @Column(name = "atp")
    private Boolean atp;

    @JsonIgnore
    @OneToMany(mappedBy = "player1", cascade = CascadeType.ALL)
    private List<Match> matchesAsPlayer1;

    @JsonIgnore
    @OneToMany(mappedBy = "player2", cascade = CascadeType.ALL)
    private List<Match> matchesAsPlayer2;

    public Player() {
    }

    public Player(String firstName, String lastName, Integer seeded) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.seeded = seeded;
    }

    public Player(Long id, String firstName, String lastName, Integer seeded,Boolean atp) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.seeded = seeded;
        this.atp = atp;
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

    public Boolean getAtp() {
        return atp;
    }

    public void setAtp(Boolean atp) {
        this.atp = atp;
    }

    public List<Match> getMatchesAsPlayer1() {
        return matchesAsPlayer1;
    }

    public void setMatchesAsPlayer1(List<Match> matchesAsPlayer1) {
        this.matchesAsPlayer1 = matchesAsPlayer1;
    }

    public List<Match> getMatchesAsPlayer2() {
        return matchesAsPlayer2;
    }

    public void setMatchesAsPlayer2(List<Match> matchesAsPlayer2) {
        this.matchesAsPlayer2 = matchesAsPlayer2;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", seeded=" + seeded +
                ", atp=" + atp +
                '}';
    }
}
