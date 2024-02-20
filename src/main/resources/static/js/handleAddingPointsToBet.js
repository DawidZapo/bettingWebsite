function updateSum() {
    let sumPlayer1 = 0;
    let sumPlayer2 = 0;
    let arguments = '';

    // Pobranie wszystkich inputów dla gracza 1
    const inputsPlayer1 = document.querySelectorAll('input[id^="inputMatch"][id$="player1"]');
    inputsPlayer1.forEach(input => {
        let value = parseFloat(input.value) || 0;
        sumPlayer1 += value;

        if(value > 0){
            arguments += input.value;
            arguments += input.id.toString();
            arguments += ';';
        }
        // console.log(input.id);
        // console.log(arguments);
    });

    // Pobranie wszystkich inputów dla gracza 2
    const inputsPlayer2 = document.querySelectorAll('input[id^="inputMatch"][id$="player2"]');
    inputsPlayer2.forEach(input => {
        let value = parseFloat(input.value) || 0;
        sumPlayer1 += value;

        if(value > 0){
            arguments += input.value;
            arguments += input.id.toString();
            arguments += ';';
        }
        // console.log(input.id);
        // console.log(arguments);
        // console.log(input.id);
    });

    // Aktualizacja sumy dla wszystkich graczy
    let totalSum = sumPlayer1 + sumPlayer2;
    totalSum = parseFloat(totalSum.toFixed(2));

    const saveBtn = document.getElementById("saveBtn");
    const investedPoints = document.getElementById("investedPoints");
    const currentPoints = document.getElementById("currentPoints")

    const currentPointsValue = parseFloat(currentPoints.innerText);

    investedPoints.innerHTML = totalSum.toString();

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

    const bottomBox = document.getElementById("bottom-box");
    if(totalSum === 0){
        bottomBox.style.display = 'none';
    }
    else{
        bottomBox.style.display = 'block';
    }
}

// Dodanie nasłuchiwania zdarzeń dla inputów
document.querySelectorAll('input[id^="inputMatch"]').forEach(input => {
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
    // console.log(value);

}

updateSum();