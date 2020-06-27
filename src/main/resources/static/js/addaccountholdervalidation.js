const inviteeUsernameField = document.getElementById("inviteeUsername");
const verificationCodeField = document.getElementById("verificationCode");

function isVerificationCodeFormatValid(){
    let verificationCodeFormatRegex = new RegExp(/^[/\d]{5}?$/);
    return (verificationCodeFormatRegex.test(verificationCodeField.value));
}


function verificationCodeFieldMessageHandler() {
    if (!isVerificationCodeFormatValid()) {
        setClassInvalid("verificationCode");
    } else {
        setClassValid("verificationCode")
    }
}


function isAllInputValid(){
    console.log("verificationCode Valid"+isVerificationCodeFormatValid()+document.getElementById("inviteeUsername").classList.contains("isValid"))
    return (isVerificationCodeFormatValid() && document.getElementById("inviteeUsername").classList.contains("isValid"));
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
inviteeUsernameField.addEventListener("input", function() {
    removeValidInValidClasses("inviteeUsername");
})

verificationCodeField.addEventListener("input", function() {
    removeValidInValidClasses("verificationCode");
    verificationCodeFieldMessageHandler();
    buttonHandler();
})

//API EVENT LISTENERS
inviteeUsernameField.addEventListener("input", () => {
    usernameExist();
});


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

function doesUsernameExist(username) {
    return this.fetchGet(`/api/usernameExist?username=${username}`);
}


function usernameExist() {
    let username = inviteeUsernameField.value;
    doesUsernameExist(username).then(r => {
        if (r) {
                setClassValid("inviteeUsername");
            } else {
                setClassInvalid("inviteeUsername");
            }
        buttonHandler();
        })
}






