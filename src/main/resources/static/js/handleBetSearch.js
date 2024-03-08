// Funkcja do filtrowania i pokazywania meczów w zależności od zaznaczonych checkboxów
function filterMatches() {
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

document.getElementById('atpCheckbox').addEventListener('change', filterMatches);
document.getElementById('wtaCheckbox').addEventListener('change', filterMatches);
document.getElementById('roundSelect').addEventListener('change',filterMatches);

filterMatches();