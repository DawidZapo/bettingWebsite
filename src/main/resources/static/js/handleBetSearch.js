function filterMatchesAtpWtaAndRound(selectorType) {
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
        console.error('Nieprawidłowy typ selektora');
        return;
    }
    const betCards = document.querySelectorAll(betSelector);

    const lastRound = selectElement.options[selectElement.options.length - 1].value;

    betCards.forEach(card => {
        const isATPMatch = card.getAttribute('data-atp') === 'true';
        const certainRound = card.getAttribute('data-round');

        if(selectedRound === '0'){
            selectedRound = lastRound;
        }
        selectElement.value = selectedRound;

        if (((isATPMatch && atpChecked) || (!isATPMatch && wtaChecked)) && selectedRound===certainRound) {
            card.style.display = 'block';
        } else {
            card.style.display = 'none';
        }
    });
}