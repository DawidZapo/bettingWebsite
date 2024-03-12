function filterMatches() {
    const atpChecked = document.getElementById('atpCheckbox').checked;
    const wtaChecked = document.getElementById('wtaCheckbox').checked;
    const matchCards = document.querySelectorAll('[id^="match"]');
    matchCards.forEach(card => {
        const isATPMatch = card.getAttribute('data-atp') === 'true';

        if ((isATPMatch && atpChecked) || (!isATPMatch && wtaChecked)) {
            card.style.display = 'block';
        } else {
            card.style.display = 'none';
        }
    });
}

document.getElementById('atpCheckbox').addEventListener('change', filterMatches);
document.getElementById('wtaCheckbox').addEventListener('change', filterMatches);
filterMatches();