document.addEventListener("DOMContentLoaded", function() {
    var selectElement = document.getElementById('roundSelect');

    selectElement.addEventListener('change', function() {
        var selectedValue = selectElement.value;

        var redirectUrl = '/?round=' + selectedValue;

        window.location.href = redirectUrl;
    });

    var urlParams = new URLSearchParams(window.location.search);
    var roundValue = urlParams.get('round');

    if (roundValue === null) {
        var lastRound = selectElement.options[selectElement.options.length - 1].value;
        window.location.href = '/?round=' + lastRound;
    } else {
        selectElement.value = roundValue;
    }
});