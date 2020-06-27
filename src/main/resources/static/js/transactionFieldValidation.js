const fromAccountField = document.getElementById("fromAccountNumber");
const toAccountField = document.getElementById("toAccountNumber");
const amountField = document.getElementById("amount");
const desciptionField = document.getElementById("description");
const errorAccountDoesntExist1 = "Pleas enter a valid Royal Stacks Iban"
const errorAccountDoesntExist2 = "Account doesn't exist, enter an existing account "
const errorSenderSameAsReceiver = "The receiving account can't be the same as the sending account"
const errorAmountTooHigh = "The amount can't be higher than the available balance"
const errorAmountFormatInvalid = "The amount should only consist of numbers, with a maximum of two decimals"
const errorDescriptionTooLong = "The description length is too long, use a maximum of 45 character"
const ibanLength = 18;
const maxDescriptionLentgh = 45;
let ibanExistInDb;
let keyPressCode;

//Input validation checks
function isAccountFormatValid(){
    let accountFormatRegex = new RegExp(/^nl[\d]{2}roya[\d]{10}$/i);
    return (accountFormatRegex.test(toAccountField.value));
}
function isAccountFormatValidWhileTyping(){
    let accountFormatRegex = new RegExp(/^(?:n|$)(?:l|$)(?:\d|$)(?:\d|$)(?:r|$)(?:o|$)(?:y|$)(?:a|$)[\d]*$/i);
    return (accountFormatRegex.test(toAccountField.value));
}
function isAccountDifferentThanFromAccount(){
    return(fromAccountField.value.toUpperCase() !== toAccountField.value.toUpperCase());
}
function existInDbHandler(){

    const options = {
        method: 'POST',
        body: toAccountField.value,
        headers: {
            'Content-Type': 'text/plain'
        }
    }
    fetch("/api/iban/",options)
        .then(response => {
            return response.text();
        })
        .then(response => {
            console.log(response);
        if(response === "true"){
            setClassValid("toAccountNumber");
            ibanExistInDb = true;
        }
        else{
            showError(errorAccountDoesntExist2, "toAccountError");
            setClassInvalid("toAccountNumber");
            ibanExistInDb = false;
        }
    });
}
function isAmountFormatValid(){
    let amountFormatRegex = new RegExp(/^\d{0,11}([,.][\d]{1,2})?$/);
    return (amountFormatRegex.test(amountField.value));
}

function isAmountSmallerThanBalance(){
    return (parseFloat(fromAccountField.options[fromAccountField.selectedIndex].text) >= amountField.value);
}
function isAmountFilledIn(){
    return (amountField.value.length !== 0);
}
function isDescriptionFormatValid(){
    return (desciptionField.value.length < maxDescriptionLentgh+1);
}
//Combined validation checks on singel input fields
function isToAccountValid(){
    return(isAccountFormatValid() && isAccountDifferentThanFromAccount() && ibanExistInDb);
}
function isAmountValid(){
    return(isAmountFormatValid() && isAmountSmallerThanBalance() && isAmountFilledIn());
}
//Combined check on ALL input fields
function isAllInputValid(){
    return (isToAccountValid() && isAmountValid() && isDescriptionFormatValid());
}
//Combined check and error handling for toAccount inputField
function toAccountFieldErrorHandler(){
    if(toAccountField.value.length < ibanLength) {
        if (!isAccountFormatValidWhileTyping()) {
            showError(errorAccountDoesntExist1, "toAccountError");
            setClassInvalid("toAccountNumber");
        }
    }
    else if (!isAccountFormatValid()){
        showError(errorAccountDoesntExist1, "toAccountError");
        setClassInvalid("toAccountNumber")
    }
    else if(!isAccountDifferentThanFromAccount()){
        showError(errorSenderSameAsReceiver, "toAccountError");
        setClassInvalid("toAccountNumber");
    }
    else {
        existInDbHandler()
    }
};
//Combined check and error handling for amount inputField
function amountFieldErrorHandler(){
    if(!isAmountFormatValid()){
        showError(errorAmountFormatInvalid, "amountError");
        setClassInvalid("amount");
    }
    else if(!isAmountSmallerThanBalance()){
        showError(errorAmountTooHigh, "amountError");
        setClassInvalid("amount");
    }
    else if (amountField.value.length>0) {
        setClassValid("amount");
    }

};

//Description validation and error handler
function descriptionFieldErrorHandler(){
    if(!isDescriptionFormatValid()){
        showError(errorDescriptionTooLong, "descriptionError");
        setClassInvalid("description");
    }
    else{
        setClassValid("description");
    }
}

//Button enable/disable handler
function buttonHandler(){
    if(isAllInputValid()){
        enableButton();
    }
    else{
        disableButton()
    }
}

//Event Listeners
toAccountField.addEventListener("input", function() {
    hideError(errorAccountDoesntExist1, "toAccountError");
    hideError(errorAccountDoesntExist2, "toAccountError");
    hideError(errorSenderSameAsReceiver, "toAccountError");
    removeValidInValidClasses("toAccountNumber");
    toAccountFieldErrorHandler();
    buttonHandler();
});
amountField.addEventListener("keypress", function(event){
    if(event.key === "e" || event.key ==="+" || event.key ==="-"){
        event.preventDefault();
    }
});
amountField.addEventListener("input", function(){
    hideError(errorAmountFormatInvalid, "amountError");
    hideError(errorAmountTooHigh, "amountError");
    removeValidInValidClasses("amount");
    amountFieldErrorHandler();
    buttonHandler();
});
fromAccountField.addEventListener("change", function(){
    hideError(errorAccountDoesntExist1, "toAccountError");
    hideError(errorAccountDoesntExist2, "toAccountError");
    hideError(errorSenderSameAsReceiver, "toAccountError");
    hideError(errorAmountFormatInvalid, "amountError");
    hideError(errorAmountTooHigh, "amountError");
    removeValidInValidClasses("amount");
    removeValidInValidClasses("toAccountNumber");
    toAccountFieldErrorHandler();
    amountFieldErrorHandler();
    buttonHandler();
});
desciptionField.addEventListener("input", function(){
    hideError(errorDescriptionTooLong, "descriptionError");
    removeValidInValidClasses("description");
    descriptionFieldErrorHandler();
    buttonHandler()
})

function enableButton(){
    if(document.getElementById("submitButton").disabled === true){
        document.getElementById("submitButton").disabled = false;
    }
}
function disableButton(){
    if(document.getElementById("submitButton").disabled === false) {
        document.getElementById("submitButton").disabled = true;
    }
}
function showError(errorMessage, elementId){
    let currentText = document.getElementById(elementId).innerHTML;
    if(!currentText.includes(errorMessage)) {
        document.getElementById(elementId).innerHTML = currentText.concat(errorMessage);
    }
}
function hideError(errorMessage, elementId){
    let currentText = document.getElementById(elementId).innerHTML;
    if(currentText.includes(errorMessage)) {
        document.getElementById(elementId).innerHTML = currentText.replace(errorMessage, "");
    }
}

function setClassValid(id) {
    if(document.getElementById(id).classList.contains('isInvalid')){
        document.getElementById(id).classList.remove('isInvalid');
    }
    if(!document.getElementById(id).classList.contains('isValid')) {
        document.getElementById(id).classList.add('isValid');
    }
}
function setClassInvalid(id) {
    if(!document.getElementById(id).classList.contains('isInvalid')) {
        document.getElementById(id).classList.add('isInvalid');
    }
    if(document.getElementById(id).classList.contains('isValid')){
        document.getElementById(id).classList.remove('isValid');
    }
}
function removeValidInValidClasses(id){
    if(document.getElementById(id).classList.contains('isValid')){
        document.getElementById(id).classList.remove('isValid');
    }
    if(document.getElementById(id).classList.contains('isInvalid')){
        document.getElementById(id).classList.remove('isInvalid');
    }
}








