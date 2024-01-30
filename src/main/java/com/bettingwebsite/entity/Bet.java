package com.bettingwebsite.entity;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "bet")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "match_to_bet_id")
    private Match match;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "bet_on")
    private String betOn;
    @Column(name = "succeed")
    private Boolean succeed;
    @Column(name = "created_at")
    private LocalTime createdAt;
    @Column(name = "updated_at")
    private LocalTime updatedAt;

    public Bet() {
    }

    public Bet(User user, Match match, Double amount, String betOn, LocalTime createdAt) {
        this.user = user;
        this.match = match;
        this.amount = amount;
        this.betOn = betOn;
        this.createdAt = createdAt;
    }

    public Bet(Long id, User user, Match match, Double amount, String betOn, Boolean succeed, LocalTime createdAt, LocalTime updatedAt) {
        this.id = id;
        this.user = user;
        this.match = match;
        this.amount = amount;
        this.betOn = betOn;
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

    public String getBetOn() {
        return betOn;
    }

    public void setBetOn(String betOn) {
        this.betOn = betOn;
    }

    public Boolean getSucceed() {
        return succeed;
    }

    public void setSucceed(Boolean succeed) {
        this.succeed = succeed;
    }

    public LocalTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", amount=" + amount +
                ", betOn='" + betOn + '\'' +
                ", succeed=" + succeed +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
