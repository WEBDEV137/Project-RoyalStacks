const accountNumberField = document.getElementById("accountNumber");
const verificationCodeField = document.getElementById("verificationCode");

function isAccountNumberFormatValid(){
    let accountFormatRegex = new RegExp(/^nl[\d]{2}roya[\d]{10}$/i);
    return (accountFormatRegex.test(accountNumberField.value));
}

function isVerificationCodeFormatValid(){
    let verificationCodeFormatRegex = new RegExp(/^[/\d]{5}?$/);
    return (verificationCodeFormatRegex.test(verificationCodeField.value));
}


function accountNumberFieldMessageHandler() {
    if (!isAccountNumberFormatValid()) {
        setClassInvalid("accountNumber");
    } else {
        setClassValid("accountNumber")
    }
}

function verificationCodeFieldMessageHandler() {
    if (!isVerificationCodeFormatValid()) {
        setClassInvalid("verificationCode");
    } else {
        setClassValid("verificationCode")
    }
}

function isAllInputValid(){
    return (isVerificationCodeFormatValid() && isAccountNumberFormatValid());
}


function buttonHandler(){
    if(isAllInputValid()){
        enableButton();
    }
    else{
        disableButton()
    }
}



//Event Listeners
accountNumberField.addEventListener("input", function() {
    removeValidInValidClasses("accountNumber");
    accountNumberFieldMessageHandler();
    buttonHandler();
})

verificationCodeField.addEventListener("input", function() {
    removeValidInValidClasses("verificationCode");
    verificationCodeFieldMessageHandler();
    buttonHandler();
})

//API EVENT LISTENER
accountNumberField.addEventListener("input", () => {
    accountNumberExist()});


function enableButton(){
    if(document.getElementById("acceptButton").disabled === true){
        document.getElementById("acceptButton").disabled = false;
    }
}
function disableButton(){
    if(document.getElementById("acceptButton").disabled === false) {
        document.getElementById("acceptButton").disabled = true;
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

//API functies

function fetchGet(url, header) {
    return fetch(url, {
        method: 'GET',
        headers : header
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Response error");
            }
            return response.json();
        })
        .catch(() => {
        });
}

function doesAccountNumberExist(accountNumber) {
    return this.fetchGet(`/api/doesAccountNumberExist?accountNumber=${accountNumber}`);
}

function accountNumberExist() {
    let accountNumber = accountNumberField.value;
    doesAccountNumberExist(accountNumber).then(r => {
        if (r) {
            setClassValid("accountNumber");
        } else {
            setClassInvalid("accountNumber");
        }
    })
}
