document.addEventListener("DOMContentLoaded", function() {
    var selectElement = document.getElementById('roundSelect');

    selectElement.addEventListener('change', function() {
        var selectedValue = selectElement.value;

        var redirectUrl = '/?round=' + selectedValue;

        window.location.href = redirectUrl;
    });
});