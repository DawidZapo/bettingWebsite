package com.bettingwebsite.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "beginning_score")
    private Double beginningScore;
    @Column(name = "after_first_round")
    private Double afterFirstRound;
    @Column(name = "after_second_round")
    private Double afterSecondRound;
    @Column(name = "after_third_round")
    private Double afterThirdRound;
    @Column(name = "after_fourth_round")
    private Double afterFourthRound;
    @Column(name = "after_quarter_final")
    private Double afterQuarterFinal;
    @Column(name = "after_semi_final")
    private Double afterSemiFinal;
    @Column(name = "after_final")
    private Double afterFinal;

    public Result() {
    }


    public Result(Long id, User user, Double beginningScore, Double afterFirstRound, Double afterSecondRound, Double afterThirdRound, Double afterFourthRound, Double afterQuarterFinal, Double afterSemiFinal, Double afterFinal) {
        this.id = id;
        this.user = user;
        this.beginningScore = beginningScore;
        this.afterFirstRound = afterFirstRound;
        this.afterSecondRound = afterSecondRound;
        this.afterThirdRound = afterThirdRound;
        this.afterFourthRound = afterFourthRound;
        this.afterQuarterFinal = afterQuarterFinal;
        this.afterSemiFinal = afterSemiFinal;
        this.afterFinal = afterFinal;
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

    public Double getBeginningScore() {
        return beginningScore;
    }

    public void setBeginningScore(Double beginningScore) {
        this.beginningScore = beginningScore;
    }

    public Double getAfterFirstRound() {
        return afterFirstRound;
    }

    public void setAfterFirstRound(Double afterFirstRound) {
        this.afterFirstRound = afterFirstRound;
    }

    public Double getAfterSecondRound() {
        return afterSecondRound;
    }

    public void setAfterSecondRound(Double afterSecondRound) {
        this.afterSecondRound = afterSecondRound;
    }

    public Double getAfterThirdRound() {
        return afterThirdRound;
    }

    public void setAfterThirdRound(Double afterThirdRound) {
        this.afterThirdRound = afterThirdRound;
    }

    public Double getAfterFourthRound() {
        return afterFourthRound;
    }

    public void setAfterFourthRound(Double afterFourthRound) {
        this.afterFourthRound = afterFourthRound;
    }

    public Double getAfterQuarterFinal() {
        return afterQuarterFinal;
    }

    public void setAfterQuarterFinal(Double afterQuarterFinal) {
        this.afterQuarterFinal = afterQuarterFinal;
    }

    public Double getAfterSemiFinal() {
        return afterSemiFinal;
    }

    public void setAfterSemiFinal(Double afterSemiFinal) {
        this.afterSemiFinal = afterSemiFinal;
    }

    public Double getAfterFinal() {
        return afterFinal;
    }

    public void setAfterFinal(Double afterFinal) {
        this.afterFinal = afterFinal;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", beginningScore=" + beginningScore +
                ", afterFirstRound=" + afterFirstRound +
                ", afterSecondRound=" + afterSecondRound +
                ", afterThirdRound=" + afterThirdRound +
                ", afterFourthRound=" + afterFourthRound +
                ", afterQuarterFinal=" + afterQuarterFinal +
                ", afterSemiFinal=" + afterSemiFinal +
                ", afterFinal=" + afterFinal +
                '}';
    }
}
