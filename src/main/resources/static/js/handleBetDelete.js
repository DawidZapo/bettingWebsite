function filterBetAccordingToTime(){
   console.log("Funkcja wywołana");
   const betElements = document.querySelectorAll('[id^="bet"]');
   const deleteButtonElements = document.querySelectorAll('[id^="deleteBetButton"]');

   betElements.forEach(betElement=>{

      const bet = betElement.getAttribute('data-bet');
      const match = betElement.getAttribute('data-bet-match');
      const deleteBtn = betElement.querySelector('button[type="submit"]');
      const matchDate = betElement.getAttribute('data-bet-match-date');
      const matchTime = betElement.getAttribute('data-bet-match-time');

      const currentDateTime = new Date();

      const [year, month, day] = matchDate.split('-').map(Number);
      const [hours, minutes] = matchTime.split(':').map(Number);
      const matchDateTime = new Date(year, month - 1, day, hours, minutes);

      if(currentDateTime > matchDateTime){
         deleteBtn.disabled = true;
      }

   });
}

filterBetAccordingToTime();

setInterval(filterBetAccordingToTime,60000);