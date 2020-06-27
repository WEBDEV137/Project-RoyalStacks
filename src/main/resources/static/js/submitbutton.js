class SubmitButton{
    validate = new Validate();
    isEnabled = true;

    checkFields(fields){
        if(this.isEnabled) {
            document.getElementById("submitButton").disabled = false;
            for (let i = 0; i < fields.length; i++) {
                if (this.validate.isInputValid(fields[i]) === false) {
                    document.getElementById("submitButton").disabled = true;
                    break;
                }
            }
        }
    };

    disable(){
        this.isEnabled = false;
        document.getElementById("submitButton").disabled = true
    }

    mouseEnter(fields) {
        if (document.getElementById("submitButton").disabled === true) {
            for (let i = 0; i < fields.length; i++) {
                this.setFieldGreyOrPink(fields[i]);
            }
        }
    }

    mouseLeave(fields){
        for (let i = 0; i < fields.length; i++) {
            this.setFieldGrey(fields[i]);
        }
    }

    setFieldGreyOrPink(id){
        if(id === "password"){
            this.validate.isInputValid("showPasswordButton") ? this.setFieldGrey(id) : this.setFieldPink(id) ;
        }
        this.validate.isInputValid(id) ? this.setFieldGrey(id) : this.setFieldPink(id);
    }


     setFieldPink(id){
        document.getElementById(id).style.backgroundColor = '#ffdede';
    }

    setFieldGrey(id){
        document.getElementById(id).style.backgroundColor = '#f1f1f1';
    }
}