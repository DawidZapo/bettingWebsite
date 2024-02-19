function updateSum() {
    let sumPlayer1 = 0;
    let sumPlayer2 = 0;

    // Pobranie wszystkich inputów dla gracza 1
    const inputsPlayer1 = document.querySelectorAll('input[id^="match"][id$="player1"]');
    inputsPlayer1.forEach(input => {
        sumPlayer1 += parseFloat(input.value) || 0;
    });

    // Pobranie wszystkich inputów dla gracza 2
    const inputsPlayer2 = document.querySelectorAll('input[id^="match"][id$="player2"]');
    inputsPlayer2.forEach(input => {
        sumPlayer2 += parseFloat(input.value) || 0;
    });

    // Aktualizacja sumy dla wszystkich graczy
    let totalSum = sumPlayer1 + sumPlayer2;
    totalSum = parseFloat(totalSum.toFixed(2));
    console.log('Suma dla wszystkich graczy:', totalSum);

    const saveBtn = document.getElementById("saveBtn");
    const investedPoints = document.getElementById("investedPoints");
    const currentPoints = document.getElementById("currentPoints")

    const currentPointsValue = parseFloat(currentPoints.innerText);

    investedPoints.innerHTML = totalSum;

    if(totalSum > currentPointsValue){
        saveBtn.disabled = true;
        investedPoints.className = "";
        investedPoints.classList.add("form-control","text-center","alert","alert-danger","my-alert");
        showToast();
    }
    else{
        investedPoints.className = "";
        investedPoints.classList.add("form-control","text-center");
        saveBtn.disabled = false;
        hideToast();
    }
}

// Dodanie nasłuchiwania zdarzeń dla inputów
document.querySelectorAll('input[id^="match"]').forEach(input => {
    input.addEventListener('input', updateSum);
});


function showToast(){
    var toast = document.getElementById("toast");
    toast.innerText = "Dostosuj wydane punkty do swojego budżetu";
    toast.style.display = "block";
    setTimeout(function() {
        toast.style.display = "none";
    }, 3000); // Powiadomienie znika po 3 sekundach
}
function hideToast(){
    var toast = document.getElementById("toast");
    toast.style.display = "none"
}


function validateInput(input) {
    // Pobierz wartość wprowadzoną przez użytkownika
    let value = input.value;

    // Usuń wszelkie znaki, które nie są cyframi, kropkami lub przecinkami
    value = value.replace(/[^0-9.,]/g, '');

    // Zamień przecinek na kropkę, jeśli istnieje
    value = value.replace(/,/g, '.');

    // Podziel wartość na część przed i po kropce
    let parts = value.split('.');

    // Jeśli istnieje więcej niż dwie części (czyli więcej niż jedna kropka), przyciąć do dwóch miejsc po przecinku
    if (parts.length > 1) {
        value = parts[0] + '.' + parts[1].slice(0, 2);
    }

    // Ustaw przefiltrowaną wartość w polu input
    input.value = value;
    console.log(value);
}