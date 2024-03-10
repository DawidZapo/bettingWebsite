document.addEventListener("DOMContentLoaded", function (){
    const isEligibleString = document.getElementById('isEligible').getAttribute('data-is-eligible');
    const isEligibleValue = isEligibleString === 'true';
    const matchElements = document.querySelectorAll('[id^="match"]');
    const inputElements = document.querySelectorAll('[id^="inputMatch"]');


    if(!isEligibleValue){
        inputElements.forEach(input=>{
            input.readOnly = true;
            input.classList.add('my-disabled');
        });
    }
});