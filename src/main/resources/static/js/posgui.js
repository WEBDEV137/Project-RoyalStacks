class PosGui {
    inputLeft = document.getElementById("displayPos");
    inputRight = document.getElementById("displayAccountNumber");
    responseLeft = document.getElementById("responseL");
    responseRight = document.getElementById("response");
    titleRight = document.getElementById("title");
    titleLeft = document.getElementById("notify");
    amountR = document.getElementById("transactionInfo");

    pressedColor = "rgba(214, 214, 214, 0.35)";
    upColor = "#ffffff";


    transactionModeKeyboard(e) {
        switch (true) {
            case (e >= 0 && e <= 10):
                session.addNumL(e);
                break;
            case (e === 'Backspace'):
                session.delL();
                break;
            case (e === 'Enter'):
                session.okL();
                break;
            case (e === 'Escape'):
                session.stopL();
                break;
            case (e === 'F1'):
                session.transL();
                break;
            case (e === 'F2'):
                session.connL();
                break;
            case (e === 'F3'):
                session.openL();
                break;
            case (e === 'F4'):
                session.closeL();
                break;
            default:
                break;
        }
    }

    transactionStoppedKeyboard(e){
        switch(true) {
            case (e === 'Enter'):
                session.okL();
                break;
            case (e === 'Escape'):
                session.stopL();
                break;
            default:
                break;
        }
    }

    connectionModeKeyboard(e){
        switch (true) {
            case (e >= 0 && e <= 10):
                session.addNumL(e); break;
            case (e === 'Backspace'):
                session.delL(); break;
            case (e === 'Enter'):
                session.okL(); break;
            case (e === 'Escape'):
                session.stopL(); break;
            case (e === 'F1'):
                session.transL(); break;
            case (e === 'F2'):
                session.connL(); break;
            default: break;
        }
    }

    paymentPendingKeyboard(e){
        switch(true){
            case (e >= 0 && e <= 10):
                session.addNumR(e); break;
            case (e === 'Backspace'):
                session.delR(); break;
            case (e === 'Enter'):
                session.okR(); break;
            case (e === 'Escape'):
                session.stopR(); break;
            default: break;
        }
    }

    paymentCompletedKeyboard(e){
        switch(true){
            case (e === 'Enter'):
                session.okL(); break;
            case (e === 'Escape'):
                session.stopL(); break;
            default: break;
        }
    }

    shortButtonPress(id) {
        const tempColor = this.upColor;

        document.getElementById(id).classList.add('pressed');
        document.getElementById(id).style.backgroundColor = this.pressedColor;
        setTimeout(function(){
            document.getElementById(id).style.backgroundColor = tempColor;
            document.getElementById(id).classList.remove('pressed')
        }, 80);
    }

    setPressAndUnpressed(press, unpress){
        document.getElementById(press).classList.add('pressed');
        document.getElementById(unpress).classList.remove('pressed');

        document.getElementById(press).style.backgroundColor = this.pressedColor;
        document.getElementById(unpress).style.backgroundColor = this.upColor;
    }

    setResponseLeftFail(message){
        this.responseLeft.innerText = message;
        this.responseLeft.classList.add("failed");
        this.responseLeft.classList.remove("neutral");
    }

    setResponseRightFail(message){
        this.responseRight.innerText = message;
        this.responseRight.classList.add("failed");
        this.responseRight.classList.remove("neutral");
    }

    setResponseLeftSuccess(message){
        this.responseLeft.innerText = message;
        this.responseLeft.classList.remove("neutral", "failed");
        this.responseLeft.classList.add("success");
    }

    setResponseRightSuccess(message){
        this.responseRight.innerText = message;
        this.responseRight.classList.remove("neutral", "failed");
        this.responseRight.classList.add("success");

    }

    setResponseRight(message){
            this.responseRight.innerText = message;
    }


    setResponseLeft(message){
            this.responseLeft.innerText = message;
    }

    setPincodePending(){
            this.inputRight.innerText = "";
            this.responseRight.innerText = "Pending payment...";
            this.titleRight.innerText = "Pincode";
    }

    setPaymentMode(transactionAmount){
        this.reset();
        this.inputLeft.innerText = "€" + transactionAmount;
        this.amountR.innerText = "€" + transactionAmount;

        this.responseLeft.innerText = "Waiting for client...";
        this.responseRight.innerText = "Pending payment...";

        this.titleRight.innerText = "Account number:";
    }

    setTransactionMode(){
        this.reset();
        this.titleLeft.innerText = "Enter amount";
        this.setPressAndUnpressed("transButtonL", "connButtonL");
        this.showTransactionAmount(session.transactionAmount)
    }

    setConnectionMode(){
        this.reset();
        pos.setPressAndUnpressed("connButtonL", "transButtonL");
        pos.titleLeft.innerText = "Bank Account";
        pos.inputLeft.innerText = "";
    }

    showTransactionAmount(transactionAmount){
        this.inputLeft.innerHTML = "€" + (transactionAmount / 100).toFixed(2);
    }



    reset(){
        this.responseRight.classList.remove("success", "failed");
        this.responseRight.classList.add("neutral");
        this.responseRight.innerText = "";
        this.inputRight.innerText = "";
        this.amountR.innerText = "";
        this.responseLeft.classList.remove("success", "failed");
        this.responseLeft.classList.add("neutral");
        this.responseLeft.innerText = "";
        this.inputLeft.innerText = "";
    }
}

document.addEventListener('keydown', function (e) {
    session.currentState.keyboardInput(e.key)
});
