function showOtherBets(){
    const matchElements = document.querySelectorAll('[id^="match"]');

    matchElements.forEach(matchElement=>{
        const matchId = matchElement.getAttribute('data-match-id');
        const matchDate = matchElement.getAttribute('data-match-date');
        const matchTime = matchElement.getAttribute('data-match-time');
        const liveIcon = document.getElementById('liveIcon'+matchId);
        const score = matchElement.getAttribute('data-match-score');
        const otherBets = document.getElementById('otherBets'+matchId);
        const info = document.getElementById('info'+matchId);


        const currentDateTime = new Date();

        const [year, month, day] = matchDate.split('-').map(Number);
        const [hours, minutes] = matchTime.split(':').map(Number);
        const matchDateTime = new Date(year, month - 1, day, hours, minutes);

        if(currentDateTime > matchDateTime){
            otherBets.style.display='block';
            info.style.display='none';
            if(score == null){
                liveIcon.style.display='block';
            }
        }
        else{
            otherBets.style.display='none';
            info.style.display='block';
        }

    });
}

showOtherBets();

setInterval(showOtherBets,60000);