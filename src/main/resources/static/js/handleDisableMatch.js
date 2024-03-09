function disableMatchAccordingToTime(){
    console.log("Funkcja wywołana");
    const matchElements = document.querySelectorAll('[id^="match"]');

    matchElements.forEach(matchElement=>{

        const deleteBtn = document.getElementById('saveBtn');
        const match = matchElement.getAttribute('data-match');
        const matchDate = matchElement.getAttribute('data-match-date');
        const matchTime = matchElement.getAttribute('data-match-time');
        const matchId = matchElement.getAttribute('data-match-id');
        const liveIcon = document.getElementById('liveIcon'+matchId);
        const score = matchElement.getAttribute('data-match-score');
        const inputMatchPlayer1 = document.getElementById('inputMatch'+matchId+'player1');
        const inputMatchPlayer2 = document.getElementById('inputMatch'+matchId+'player2');

        const currentDateTime = new Date();

        const [year, month, day] = matchDate.split('-').map(Number);
        const [hours, minutes] = matchTime.split(':').map(Number);
        const matchDateTime = new Date(year, month - 1, day, hours, minutes);

        if(currentDateTime > matchDateTime){
            // matchElement.style.color = '#A9A9A9';
            // matchElement.style.backgroundColor = '#F0F0F0';
            // matchElement.style.opacity = '0.5';
            if(score == null){
                liveIcon.style.display='block';
            }
            inputMatchPlayer1.readOnly=true;
            inputMatchPlayer2.readOnly=true;
            deleteBtn.disabled=true;
        }

    });
}

disableMatchAccordingToTime();

setInterval(disableMatchAccordingToTime,60000);