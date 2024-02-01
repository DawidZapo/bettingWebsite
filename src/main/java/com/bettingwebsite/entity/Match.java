package com.bettingwebsite.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "match_to_bet")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "match_date")
    private LocalDate matchDate;
    @Column(name = "match_time")
    private LocalTime matchTime;
    @Column(name = "court_number")
    private String courtNumber;
    @Column(name = "match_duration")
    private Double matchDuration;
    @Column(name = "round")
    private String round;
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bet> bets;
    @ManyToOne
    @JoinColumn(name = "player1_id", referencedColumnName = "id")
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player2_id", referencedColumnName = "id")
    private Player player2;
    @Column(name = "player1_odds")
    private Double player1Odds;
    @Column(name = "player2_odds")
    private Double player2Odds;
    @Column(name = "score")
    private String score;
    @Column(name = "winner")
    private String winner;

    public Match() {
    }

    public Match(LocalDate matchDate, LocalTime matchTime, String courtNumber, String round, Player player1, Player player2, Double player1Odds, Double player2Odds) {
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.courtNumber = courtNumber;
        this.round = round;
        this.player1 = player1;
        this.player2 = player2;
        this.player1Odds = player1Odds;
        this.player2Odds = player2Odds;
    }

    public Match(Long id, LocalDate matchDate, LocalTime matchTime, String courtNumber, Double matchDuration, String round, List<Bet> bets, Player player1, Player player2, Double player1Odds, Double player2Odds, String score, String winner) {
        this.id = id;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.courtNumber = courtNumber;
        this.matchDuration = matchDuration;
        this.round = round;
        this.bets = bets;
        this.player1 = player1;
        this.player2 = player2;
        this.player1Odds = player1Odds;
        this.player2Odds = player2Odds;
        this.score = score;
        this.winner = winner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public LocalTime getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(LocalTime matchTime) {
        this.matchTime = matchTime;
    }

    public String getCourtNumber() {
        return courtNumber;
    }

    public void setCourtNumber(String courtNumber) {
        this.courtNumber = courtNumber;
    }

    public Double getMatchDuration() {
        return matchDuration;
    }

    public void setMatchDuration(Double matchDuration) {
        this.matchDuration = matchDuration;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Double getPlayer1Odds() {
        return player1Odds;
    }

    public void setPlayer1Odds(Double player1Odds) {
        this.player1Odds = player1Odds;
    }

    public Double getPlayer2Odds() {
        return player2Odds;
    }

    public void setPlayer2Odds(Double player2Odds) {
        this.player2Odds = player2Odds;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }


    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", matchDate=" + matchDate +
                ", matchTime=" + matchTime +
                ", courtNumber='" + courtNumber + '\'' +
                ", matchDuration=" + matchDuration +
                ", round='" + round + '\'' +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", player1Odds=" + player1Odds +
                ", player2Odds=" + player2Odds +
                ", score='" + score + '\'' +
                ", winner='" + winner + '\'' +
                '}';
    }

    public String getPolishNameForHTML(String round){
        switch(round){
            case "round1" ->{return "1. Runda";}
            case "round2" ->{return "2. Runda";}
            case "round3" ->{return "3. Runda";}
            case "round4" ->{return "4. Runda";}
            case "quarter-final" ->{return "Ćwierćfinały";}
            case "semi-final" -> {return "Półfinały";}
            case "final" ->{return "Finały";}
            default -> {return "Błąd";}
        }
    }
}
