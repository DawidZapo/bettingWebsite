
// Funkcja do filtrowania i pokazywania meczów w zależności od zaznaczonych checkboxów
function filterMatches() {
    // Pobierz wartość zaznaczonego checkboxa ATP
    const atpChecked = document.getElementById('atpCheckbox').checked;

    // Pobierz wartość zaznaczonego checkboxa WTA
    const wtaChecked = document.getElementById('wtaCheckbox').checked;

    // Pobierz wszystkie karty meczów
    const matchCards = document.querySelectorAll('[id^="match"]');

    // Iteruj przez wszystkie karty meczów
    matchCards.forEach(card => {
        // Pobierz atrybut 'data-atp' z karty meczu, który określa typ meczu (ATP lub WTA)
        const isATPMatch = card.getAttribute('data-atp') === 'true';

        // Sprawdź, czy mecz jest typu ATP i czy checkbox ATP jest zaznaczony, lub czy mecz jest typu WTA i checkbox WTA jest zaznaczony
        if ((isATPMatch && atpChecked) || (!isATPMatch && wtaChecked)) {
            // Jeśli warunek jest spełniony, pokaż kartę meczu
            card.style.display = 'block';
        } else {
            // W przeciwnym razie ukryj kartę meczu
            card.style.display = 'none';
        }
    });
}

// Dodaj nasłuchiwanie na zdarzenie zmiany dla checkboxów ATP i WTA
document.getElementById('atpCheckbox').addEventListener('change', filterMatches);
document.getElementById('wtaCheckbox').addEventListener('change', filterMatches);

// Wywołaj funkcję filterMatches() na początku, aby pokazać meczu w zależności od początkowego stanu checkboxów
filterMatches();