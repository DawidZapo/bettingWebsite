function filterMatchesAtpWtaAndRound(selectorType, useRound, useTextSearch) {
    const atpChecked = document.getElementById('atpCheckbox').checked;
    const wtaChecked = document.getElementById('wtaCheckbox').checked;

    const selectElement = document.getElementById('roundSelect');
    let selectedRound = document.getElementById('roundSelect').value;

    let betSelector;
    if (selectorType === 'match') {
        betSelector = '[id^="match"]';
    } else if (selectorType === 'bet') {
        betSelector = '[id^="bet"]';
    } else {
        console.error('Wrong selector type');
        return;
    }
    const betCards = document.querySelectorAll(betSelector);

    const lastRound = selectElement.options[selectElement.options.length - 1].value;

    betCards.forEach(card => {
        const isATPMatch = card.getAttribute('data-atp') === 'true';
        const certainRound = card.getAttribute('data-round');

        if(useTextSearch){
            const searchText = document.getElementById('searchText').value.toLowerCase();
            console.log(searchText);
            const player1FirstName = card.getAttribute('data-player-firstname-1').toLowerCase();
            const player1LastName = card.getAttribute('data-player-lastname-1').toLowerCase();
            const player2FirstName = card.getAttribute('data-player-firstname-2').toLowerCase();
            const player2LastName = card.getAttribute('data-player-lastname-2').toLowerCase();
            if (player1FirstName.includes(searchText) || player1LastName.includes(searchText) ||
                player2FirstName.includes(searchText) || player2LastName.includes(searchText)) {
                card.style.display = 'block';
            } else {
                card.style.display = 'none';
            }
        }


        if(useRound){
            if(selectedRound === '0'){
                selectedRound = lastRound;
            }
            selectElement.value = selectedRound;

            if (((isATPMatch && atpChecked) || (!isATPMatch && wtaChecked)) && selectedRound===certainRound) {
                card.style.display = 'block';
            } else {
                card.style.display = 'none';
            }
        }
        else{
            if ((isATPMatch && atpChecked) || (!isATPMatch && wtaChecked)) {
                card.style.display = 'block';
            } else {
                card.style.display = 'none';
            }
        }
    });
}