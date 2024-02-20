package com.bettingwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "bet")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "match_to_bet_id")
    private Match match;
    @Column(name = "amount")
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "bet_on_player_id")
    private Player betOnPlayer;
    @Column(name = "expected_win")
    private Double expectedWin;
    @Column(name = "succeed")
    private Boolean succeed;
    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    public Bet() {
    }

    public Bet(User user, Match match, Double amount, Player betOnPlayer) {
        this.user = user;
        this.match = match;
        this.amount = amount;
        this.betOnPlayer = betOnPlayer;
    }
    public Bet(Double amount, Player betOnPlayer, Double expectedWin) {
        this.amount = amount;
        this.betOnPlayer = betOnPlayer;
        this.expectedWin = expectedWin;
    }
    public Bet(Long id, User user, Match match, Double amount, Player betOnPlayer,Double expectedWin, Boolean succeed, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.user = user;
        this.match = match;
        this.amount = amount;
        this.betOnPlayer = betOnPlayer;
        this.expectedWin = expectedWin;
        this.succeed = succeed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Player getBetOnPlayer() {
        return betOnPlayer;
    }

    public void setBetOnPlayer(Player betOnPlayer) {
        this.betOnPlayer = betOnPlayer;
    }

    public Double getExpectedWin() {
        return expectedWin;
    }

    public void setExpectedWin(Double expectedWin) {
        this.expectedWin = expectedWin;
    }

    public Boolean getSucceed() {
        return succeed;
    }

    public void setSucceed(Boolean succeed) {
        this.succeed = succeed;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", amount=" + amount +
                ", succeed=" + succeed +
                ", expectedWin=" + expectedWin +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
