
const vatnumber = document.getElementById("vatNumber");
const kvknumber = document.getElementById("kvkNumber");
const companyname = document.getElementById("companyName");



/* Regex */
const companynameregex = /^[\w@ ]*[^\W_ ][\w- @ & +]*$/;
const vatnumberRegex = /^[nN][lL][0-9]{9}[bB][0-9]{2}$/;
const kvkRegex =  /^[0-9]{8}$/;

/* check companyname */
kvknumber.addEventListener('input', function () {
    validateKvkNumber();

});

vatnumber.addEventListener('input', function () {
    validateVatNumber();
});

companyname.addEventListener("input", function () {
    validateCompanyName();
});

/*check id Company name valid*/

function validateCompanyName() {
        let companynameInput = companyname.value;
        if (companynameregex.test(companynameInput)) {
           setElementIsValid("InvalidCompanyName",companyname);
        } else {
           setElementIsInvalid("InvalidCompanyName",companyname);
        }

}



/*check if kvk valid*/


function validateKvkNumber() {

        let kvknumberInput = kvknumber.value;
        if (kvkRegex.test(kvknumberInput)) {
            setElementIsValid("InvalidKvkNumber",kvknumber);
        } else {
            setElementIsInvalid("InvalidKvkNumber",kvknumber);
        }

}


/*set element to valid or not valid*/

function setElementIsValid(id,element){
    document.getElementById(id).style.display = "none";
    element.classList.add("isValid");
    element.classList.remove("isInvalid");

}

function setElementIsInvalid(id,element){
    document.getElementById(id).style.display = "inline";
    element.classList.add("isInvalid");
    element.classList.remove("isValid");

}
/*set vat class to valid/invalid*/

function setVatClassInValid() {
    setVatNumberNotCorrect("Vat number not valid");
    vatnumber.classList.add("isInvalid");
    vatnumber.classList.remove("isValid");
}

function setVatClassValid() {
    hideVatNumberNotCorrect();
    vatnumber.classList.add("isValid");
    vatnumber.classList.remove("isInvalid")
}

/* check if vat valid*/

function validateVatNumber() {
        const vatnumberInput = vatnumber.value;
        const VATchek = window.location.pathname + `/v_check?vatnumber=${vatnumber.value}`;
        if (!vatnumberRegex.test(vatnumberInput)) {
            setVatClassInValid();
        } else {
            getResponseVatCheck(VATchek);
        }
}

/*get respons to check id vat passes the 11 proof*/
function getResponseVatCheck(VATchek) {
    fetch(VATchek)
        .then((response) => {
            if (!response) {
                throw new Error("Response Error")
            }
            return response.json();
        })
        .then((data) => {
            if (data === true) {
                setVatClassValid();
            } else {
                setVatClassInValid();
            }
        })
        .catch((error) => {
            console.log(error);
        })
}


/* enable/disable submit button met click nb: kan waarschijnlijk in een methode maar weet niet hoe */


document.getElementById("businessFields").style.display = "none";
const sector = document.getElementById("sector");
const form = document.getElementById("form");
form.addEventListener('click', function () {
    if(document.getElementById("business").checked) {
        document.getElementById("submitButton").disabled = !(companyname.classList.contains("isValid") &&
            kvknumber.classList.contains("isValid") &&
            vatnumber.classList.contains("isValid") &&
            sector.value !== "");
    }else{
        hideBusinessFields();
    }
});

/* enable/disable submit button met keyup*/

form.addEventListener('keyup', function () {
    showAndHideSubmitButton();
});

function showAndHideSubmitButton(){
    document.getElementById("submitButton").disabled = !(companyname.classList.contains("isValid") &&
        kvknumber.classList.contains("isValid") &&
        vatnumber.classList.contains("isValid") &&
        sector.value !== "");
    console.log("yo")
}



/* SHOW BUSINESS FIELD METHOD */

function showBusinessFields() {

    document.getElementById("businessFields").style.display = "inline";
    document.getElementById("submitButton").disabled = true;
    document.getElementById("companyName").required = true;
    document.getElementById("kvkNumber").required = true;
    document.getElementById("vatNumber").required = true;
    document.getElementById("sector").required = true;



}


/*HIDE BUSINESS FIELD METHOD*/

function hideBusinessFields() {
    document.getElementById("submitButton").disabled = false;
    document.getElementById("businessFields").style.display = "none";
    document.getElementById("companyName").removeAttribute("required");
    document.getElementById("kvkNumber").removeAttribute("required");
    document.getElementById("vatNumber").removeAttribute("required");
    document.getElementById("sector").removeAttribute("required");


}




function setVatNumberNotCorrect(String) {
    const x = document.getElementById("invalidVatNumber");
    x.style.display = "inline";
    x.innerHTML = String;
}

function hideVatNumberNotCorrect() {
    const x = document.getElementById("invalidVatNumber");
    x.style.display = "none";
}







