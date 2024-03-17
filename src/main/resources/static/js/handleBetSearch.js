function filterMatchesAtpWtaAndRound() {
    const atpChecked = document.getElementById('atpCheckbox').checked;
    const wtaChecked = document.getElementById('wtaCheckbox').checked;
    const betCards = document.querySelectorAll('[id^="bet"]');
    const selectElement = document.getElementById('roundSelect');
    let selectedRound = document.getElementById('roundSelect').value;

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

document.getElementById('atpCheckbox').addEventListener('change', filterMatchesAtpWtaAndRound);
document.getElementById('wtaCheckbox').addEventListener('change', filterMatchesAtpWtaAndRound);
document.getElementById('roundSelect').addEventListener('change',filterMatchesAtpWtaAndRound);

filterMatchesAtpWtaAndRound();