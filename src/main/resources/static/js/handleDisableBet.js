function disableBetAccordingToTime(){
   console.log("Funkcja wywołana");
   const betElements = document.querySelectorAll('[id^="bet"]');
   const deleteButtonElements = document.querySelectorAll('[id^="deleteBetButton"]');

   betElements.forEach(betElement=>{

      const bet = betElement.getAttribute('data-bet');
      const betId = betElement.getAttribute('data-bet-id');
      const match = betElement.getAttribute('data-bet-match');
      const deleteBtn = betElement.querySelector('button[type="submit"]');
      const matchDate = betElement.getAttribute('data-bet-match-date');
      const matchTime = betElement.getAttribute('data-bet-match-time');
      const form = betElement.querySelector('form');
      const liveIcon = document.getElementById('liveIcon'+betId);
      const score = betElement.getAttribute('data-bet-match-score');

      const currentDateTime = new Date();

      const [year, month, day] = matchDate.split('-').map(Number);
      const [hours, minutes] = matchTime.split(':').map(Number);
      const matchDateTime = new Date(year, month - 1, day, hours, minutes);

      if(currentDateTime > matchDateTime){
         deleteBtn.disabled = true;
         if(score == null){
            liveIcon.style.display='block';
         }
         // form.style.display='none';

         // do wyboru czy chcemy schowac element czy go wylaczyc
      }

   });
}

disableBetAccordingToTime();

setInterval(disableBetAccordingToTime,60000);