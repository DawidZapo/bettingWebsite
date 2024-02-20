function submitPoints(){
    let sumPlayer1 = 0;
    let sumPlayer2 = 0;
    let arguments = '';

    // Pobranie wszystkich inputów dla gracza 1
    const inputsPlayer1 = document.querySelectorAll('input[id^="inputMatch"][id$="player1"]');
    inputsPlayer1.forEach(input => {
        let value = parseFloat(input.value) || 0;
        sumPlayer1 += value;

        if(value > 0){
            if(arguments.length !== 0){
                arguments += ';';
            }
            arguments += input.value;
            arguments += input.id.toString();
        }
    });

    // Pobranie wszystkich inputów dla gracza 2
    const inputsPlayer2 = document.querySelectorAll('input[id^="inputMatch"][id$="player2"]');
    inputsPlayer2.forEach(input => {
        let value = parseFloat(input.value) || 0;
        sumPlayer1 += value;

        if(value > 0){
            if(arguments.length !== 0){
                arguments += ';';
            }
            arguments += input.value;
            arguments += input.id.toString();
        }
    });

    console.log(arguments);
    window.location.href="/processPointsSubmission?arguments="+arguments;
}