class Validate{
    constructor() {
    }


    USERNAME_NOT_AVAILABLE = "Choose another username";
    USERNAME_IS_INVALID = "Between " + regex.min_username_length + " and " + regex.max_username_length + " letters and numbers";
    BSN_INCORRECT_LENGTH = "Enter " + regex.bsn_length + " numbers";
    BSN_IS_INVALID = "Enter a valid BSN";
    PHONE_NUMBER_INVALID = "Must be 9 numbers starting with 0";
    EMAIL_INVALID = "Enter a valid email address";

    /**
     * FUNCTIONS
     */
    username() {
        const USERNAME_FIELD = document.getElementById("username");
        let username = USERNAME_FIELD.value;

        if (!regex.username.test(username)) {
            this.setElementInvalid("username");
            this.revealElement("usernameNotAvailable", this.USERNAME_IS_INVALID);
        } else {
            const api = new API();
            api.isUsernameUnique(username).then(r => {
                if (r) {
                    this.setElementValid("username");
                    this.hideElement("usernameNotAvailable");
                } else {
                    this.setElementInvalid("username");
                    this.revealElement("usernameNotAvailable", this.USERNAME_NOT_AVAILABLE);
                }
            })
        }
    }


    password(fields) {
        let allValid = true;
        for (const [key, value] of Object.entries(fields)) {
            if (document.getElementById("password").value.match(value)) {

                document.getElementById(key).classList.remove("passReqInvalid");
                document.getElementById(key).classList.add("passReqValid");
            } else {

                document.getElementById(key).classList.remove("passReqValid");
                document.getElementById(key).classList.add("passReqInvalid");
                allValid = false;
            }
        }
        allValid ? this.setElementValid("showPasswordButton") : this.setElementInvalid("showPasswordButton");
    }


    names(id){
        let nameInput = document.getElementById(id).value;
        regex.names.test(nameInput) ? this.setElementValid(id) : this.setElementInvalid(id);
    }


    email(){
        const EMAIL_FIELD = document.getElementById("email");

        if (regex.email.test(EMAIL_FIELD.value)) {
            this.hideElement("InvalidEmail");
            this.setElementValid("email");
        } else {
            this.revealElement("InvalidEmail", this.EMAIL_INVALID);
            this.setElementInvalid("email");
        }
    }


    phoneNumber(){
        const PHONE_NUMBER_FIELD = document.getElementById("phoneNumber");

        if (regex.phoneNumber.test(PHONE_NUMBER_FIELD.value)) {
            this.hideElement("InvalidPhoneNumber");
            this.setElementValid("phoneNumber");

        } else {
            this.revealElement("InvalidPhoneNumber", this.PHONE_NUMBER_INVALID);
            this.setElementInvalid("phoneNumber")
        }
    }


    bsn(){
        const BSN_FIELD = document.getElementById("BSN");
        let BSNInput = BSN_FIELD.value;

        if (!regex.bsn.test(BSNInput)) {
            this.setElementInvalid("BSN");
            this.revealElement("BSNNotAvailable", this.BSN_INCORRECT_LENGTH);

        } else if (!this.passesCheckDigit(BSNInput)) {
            this.setElementInvalid("BSN");
            this.revealElement("BSNNotAvailable", this.BSN_IS_INVALID);

        } else {
            const api = new API();
            api.isBSNUnique(BSNInput).then(r => {
                if (r) {
                    this.setElementValid("BSN");
                    this.hideElement("BSNNotAvailable");
                } else {
                    this.revealElement("BSNNotAvailable", this.BSN_IS_INVALID);
                }
            });
        }
    }


    // 11-proef
    passesCheckDigit(BSN) {
        if(!regex.bsn.test(BSN)){
            return false;
        }

        const firstNumbers = BSN.substring(0, regex.bsn_length -1 );
        const lastNumber = BSN.charAt(regex.bsn_length - 1);
        let sum = 0;
        let i;
        for (i = 0; i < firstNumbers.length; i++) {
            sum += firstNumbers.charAt(i) * (regex.bsn_length - i);
        }
        sum += lastNumber;

        return sum % 11 === 0;
    }


    postalCode(){
        const POSTAL_CODE_FIELD = document.getElementById("postalCode");

        regex.postalCode.test(POSTAL_CODE_FIELD.value) ?
            this.setElementValid("postalCode") : this.setElementInvalid("postalCode");
    }


    addressFields(){
        if(this.isInputValid("postalCode")){
            const api = new API();
            api.cityAddress().then(r => {
                if(r !== undefined){
                    this.setValue("city", r.city);
                    this.setValue("street", r.street);
                    this.setElementValid("houseNumber");
                } else {
                    this.emptyValue("city");
                    this.emptyValue("street");
                    this.setElementInvalid("houseNumber");
                }
            })

        } else {
            this.emptyValue("city");
            this.emptyValue("street");
            this.removeValidation("houseNumber");
        }
    }

    isInputValid(id){
        if(id === "password"){
            return document.getElementById("showPasswordButton").classList.contains("isValid")
        }
        return document.getElementById(id).classList.contains("isValid");
    }

    /**
     * SETTERS
     */
    hideElement(id){
        document.getElementById(id).style.display = "none";
    }
    revealElement(id, String){
        document.getElementById(id).style.display = 'inline';
        document.getElementById(id).innerHTML = String;
    }


    setElementValid(id){
        document.getElementById(id).classList.remove("isInvalid");
        document.getElementById(id).classList.add("isValid");
    }
    setElementInvalid(id){
        document.getElementById(id).classList.add("isInvalid");
        document.getElementById(id).classList.remove("isValid");
    }


    setValue(id, value){
        document.getElementById(id).value = value;
        this.setElementValid(id);
    }
    emptyValue(id){
        document.getElementById(id).value = "";
        document.getElementById(id).classList.remove("isValid");
    }


    removeValidation(id){
        document.getElementById(id).classList.remove("isValid");
        document.getElementById(id).classList.remove("isInvalid");
    }
}