class Match {
    constructor(id, matchDate, matchTime, courtNumber, matchDuration, atp, round, bets, player1, player2, player1Odds, player2Odds, score, winner) {
        this.id = id;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.courtNumber = courtNumber;
        this.matchDuration = matchDuration;
        this.atp = atp;
        this.round = round;
        this.bets = bets;
        this.player1 = player1;
        this.player2 = player2;
        this.player1Odds = player1Odds;
        this.player2Odds = player2Odds;
        this.score = score;
        this.winner = winner;
    }
}
class Bet {
    constructor(id, user, match, amount, betOnPlayer, expectedWin, succeed, createdAt, updatedAt) {
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
}
class User {
    constructor(id, userName, password, enabled, userDetails, roles, bets) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.userDetails = userDetails;
        this.roles = roles;
        this.bets = bets;
    }
}
class Player {
    constructor(id, firstName, lastName, seeded, atp) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.seeded = seeded;
        this.atp = atp;
        this.matchesAsPlayer1 = [];
        this.matchesAsPlayer2 = [];
    }
}
class UserDetails {
    constructor(id, user, firstName, lastName, points) {
        this.id = id;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.points = points;
    }
}
