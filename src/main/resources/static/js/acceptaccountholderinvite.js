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
function containsClass(id, className){
    return document.getElementById(id).classList.contains(className);
}
function removeClass(id, className){
    return document.getElementById(id).classList.remove(className);
}
function addClass(id, className){
    return document.getElementById(id).classList.add(className);
}


const invalid = 'isInvalid';
const valid = 'isValid';

function setClassValid(id) {
    if(containsClass(id, invalid)){
        removeClass(id, invalid);
    }
    if(!containsClass(id, valid)) {
        addClass(id, valid);
    }
}
function setClassInvalid(id) {
    if(!containsClass(id, invalid)) {
        addClass(id, invalid);
    }
    if(containsClass(id, valid)){
        removeClass(id, valid);
    }
}
function removeValidInValidClasses(id){
    if(containsClass(id, valid)){
        removeClass(id, valid);
    }
    if(containsClass(id, invalid)){
        removeClass(id, invalid);
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
