/***************
 *             *
 *   CONTEXT   *
 *             *
 ***************/
function addNumberL(e) { session.addNumL(e); }
function addNumberR(e) { session.addNumR(e); }
function stopButtonL() { session.stopL(); }
function delButtonL() { session.delL(); }
function delButtonR() { session.delR(); }
function okButtonL() { session.okL(); }
function transButtonL() { session.transL(); }
function connButtonL() { session.connL(); }
function openButtonL() { session.openL(); }
function closeButtonL() { session.closeL(); }
function stopButtonR() { session.stopR(); }
function okButtonR() { session.okR(); }


/*************
 *           *
 *   STATE   *
 *           *
 *************/
let Session = function() {

    this.transactionAmount = 0;
    this.toAccount = null;
    this.pincode = null;
    this.connected = null;
    this.isOpened = false;

    this.currentState = new TransactionMode(this);

    this.changeState = function (state) {
        if (this.connected) {
            this.currentState.exit();
            this.currentState = state;
            this.currentState.setup();
        } else {
            this.currentState = new ConnectionMode(this);
            this.currentState.setup();
            pos.responseLeft.innerText = "Device is not connected";
        }
    };

    this.addNumL = function(e) { this.currentState.addNumL(e); };
    this.addNumR = function(e) { this.currentState.addNumR(e); };
    this.delL = function() { this.currentState.delL(); };
    this.delR = function() { this.currentState.delR(); };
    this.stopL = function () { this.currentState.stopL(); };
    this.okL = function() { this.currentState.okL(); };
    this.transL = function() { this.currentState.transL(); };
    this.connL = function() { this.currentState.connL(); };
    this.openL = function() { this.currentState.openL(); };
    this.closeL = function() { this.currentState.closeL(); };
    this.stopR = function(){ this.currentState.stopR(); };
    this.okR = function(){ this.currentState.okR(); };
};


/********************
 *                  *
 * TRANSACTION MODE *
 *                  *
 ********************/
let TransactionMode = function (session) {

    this.keyboardInput = function (e){
        pos.transactionModeKeyboard(e)
    };

    this.setup = function(){
        pos.setTransactionMode()
    };

    this.exit = function(){
    };

    this.addNumL = function(e) {
        pos.shortButtonPress('addNumberL' + e);
        pos.setResponseLeft("");

        if(session.transactionAmount < 1000000) {
            session.transactionAmount = Math.round((session.transactionAmount * 10) + Number(e));
        }
        pos.showTransactionAmount(session.transactionAmount)
    };

    this.delL = function() {
        pos.shortButtonPress('delButtonL');
        session.transactionAmount = Math.floor(session.transactionAmount / 10);
        pos.showTransactionAmount(session.transactionAmount)
    };

    this.stopL = function () {
        session.transactionAmount = null;
        pos.shortButtonPress('stopButtonL');
        session.changeState(new TransactionMode(session));
    };

    this.okL = function() {
        pos.shortButtonPress('okButtonL');
        if(!session.isOpened){
            pos.setResponseLeft("This device is closed");
        } else if(session.transactionAmount !== null && session.transactionAmount > 0){
            session.transactionAmount = (session.transactionAmount/100).toFixed(2);
            session.changeState(new PaymentPending(session));
        }
    };

    this.connL = function() {
        !session.isOpened ? session.changeState(new ConnectionMode(session))
            : pos.responseLeft.innerText = ("Close before connecting");
    };

    this.openL = function() {
        if(!session.isOpened) {
            session.isOpened = true;
            session.journal = new Journal(new Date().toLocaleString("en-GB"));
            pos.setPressAndUnpressed("openButtonL", "closeButtonL");
            pos.setResponseLeft("Device is opened");
        }
    };

    this.closeL = function() {
        if(session.isOpened) {
            pos.setPressAndUnpressed("closeButtonL", "openButtonL");

            session.isOpened = false;
            session.journal.closingTime = new Date().toLocaleString("en-GB");
            session.journal.download();
            session.journal = null;

            pos.setResponseLeft("Device is closed");
        }
    };

    this.addNumR = function(e) {/*disabled*/}; this.delR = function() {/*disabled*/};
    this.transL = function() {/*disabled*/}; this.stopR = function() {/*disabled*/};
    this.okR = function(){/*disabled*/};
};


/********************
 *                  *
 * CONNECTION MODE  *
 *                  *
 ********************/
let ConnectionMode = function (session) {

    let connectionCode = null;
    let businessAccountNumber = null;

    this.keyboardInput = function (e) {
        pos.connectionModeKeyboard(e)
    };

    this.setup = function(){
        pos.setConnectionMode()
    };

    this.exit = function(){
    };

    this.addNumL = function(e) {
        pos.setResponseLeft("");
        pos.shortButtonPress('addNumberL' + e);
        if (pos.inputLeft.innerHTML.length < 10 && businessAccountNumber === null) {
            pos.inputLeft.innerHTML = pos.inputLeft.innerHTML + e;
        } else if (pos.inputLeft.innerHTML.length < 5 && connectionCode === null) {
            pos.inputLeft.innerHTML = pos.inputLeft.innerHTML + e;
        }
    };


    this.delL = function() {
        pos.shortButtonPress('delButtonL');
        pos.inputLeft.innerHTML = pos.inputLeft.innerHTML.slice(0, -1);
    };

    this.stopL = function () {
        pos.shortButtonPress('stopButtonL');
        session.changeState(new ConnectionMode(session)); };

    this.okL = function() {
        pos.shortButtonPress('okButtonL');
        let inputLeft = pos.inputLeft.innerHTML;

        if (businessAccountNumber == null && inputLeft.length === 10) {
            pos.titleLeft.innerHTML = "Enter connection code";
            pos.inputLeft.innerHTML = "";
            businessAccountNumber = inputLeft;

        } else if (connectionCode == null && inputLeft.length === 5) {
            connectionCode = inputLeft;
            sendConnectionRequest();
        }

        function sendConnectionRequest() {
            const api = new API();
            let connectionRequest = {
                businessAccountNumber: businessAccountNumber,
                connectionCode: connectionCode
            };
            api.postConnectionRequest(connectionRequest).then(connectionResult =>
                connectionResult.succeeded ? connectionSuccess(connectionResult) : connectionFailed());
        }

        function connectionSuccess(body) {
            pos.responseLeft.classList.add("success");
            pos.setResponseLeft("Connection successful!");
            window.location.href = window.location.origin + "/pos/" + body.id;
        }

        function connectionFailed() {
            session.changeState(new ConnectionMode(session));
            pos.setResponseLeft("Connection failed!");
            pos.titleLeft.innerHTML = "Enter account number";
        }
    };

    this.transL = function() { session.changeState(new TransactionMode(session)); };

    this.addNumR = function(e){/* disabled*/}; this.delR = function(){/* disabled*/};
    this.connL = function() {/*disabled*/}; this.openL = function() {/*disabled*/};
    this.closeL = function() {/*disabled*/}; this.stopR = function() {/*disabled*/};
    this.okR = function() {/*disabled*/};

};


/********************
 *                  *
 * PAYMENT PENDING  *
 *                  *
 *******************/
let PaymentPending = function (session) {

    let fillBankAccount = true;

    this.keyboardInput = function (e){
        pos.paymentPendingKeyboard(e)
    };

    this.setup = function(){
        pos.setPaymentMode(session.transactionAmount);
    };

    this.exit = function(){
        session.pincode = null;
    };

    this.addNumR = function(e) {
        pos.shortButtonPress('addNumberR' + e);
        let displayAccountNumber = pos.inputRight.innerHTML;
        if(session.toAccount === null){
            if (displayAccountNumber.length < 10) {
                pos.inputRight.innerHTML = pos.inputRight.innerHTML + e;
            }
        } else if (displayAccountNumber.length < 4) {
            session.pincode = Math.round((session.pincode * 10) + Number(e));
            e = '*';
            pos.inputRight.innerHTML = pos.inputRight.innerHTML + e
        }
    };

    this.delR = function() {
        pos.shortButtonPress('delButtonR');
        pos.inputRight.innerHTML = pos.inputRight.innerHTML.slice(0, -1);
        if(session.toAccount !== null) {
            session.pincode = session.pincode.toString().slice(0, -1)
        }
    };

    this.stopL = function () {
        pos.shortButtonPress('stopButtonL');
        session.changeState(new TransactionStopped(session));
    };
    this.stopR = function() {
        pos.shortButtonPress('stopButtonR');
        session.changeState(new TransactionStopped(session));
    };
    this.okR = function() {
        pos.shortButtonPress('okButtonR');
        if (session.toAccount == null && pos.inputRight.innerText.length < 10) {
            pos.setResponseRight("Enter your account number");

        } else if (session.toAccount == null && pos.inputRight.innerText.length === 10) {
            session.toAccount = pos.inputRight.innerText;
            pos.setPincodePending();

        } else if (pos.inputRight.innerText.length < 4) {
            pos.setResponseRight("Enter your pincode");

        } else if (pos.inputRight.innerText.length === 4) {
            postPaymentData();
        }

        function postPaymentData() {
            let paymentData = {
                identificationNumber: identificationNumber,
                account: session.toAccount,
                pin: session.pincode,
                amount: session.transactionAmount
            };

            let api = new API();
            api.postPaymentData(paymentData).then(r => {
                checkPaymentResult(r)
            });
        }

        function checkPaymentResult(paymentResult) {

            if(paymentResult.paymentSuccess) {
                session.changeState(new PaymentCompleted(session, paymentResult.transactionId))
            } else if (!paymentResult.accountVerified) {
                session.changeState(new TransactionStopped(session, 'Invalid account number'));
            } else if (!paymentResult.sufficientBalance) {
                session.changeState(new TransactionStopped(session, 'Insufficient balance'));
            } else {
                session.changeState(new TransactionStopped(session, 'Internal Error'));
            }

        }
    };

    this.addNumL = function(e) {/*disabled*/}; this.delL = function() {/*disabled*/};
    this.okL = function() {/*disabled*/}; this.transL = function() {/*disabled*/};
    this.connL = function(){/*disabled*/}; this.openL = function() {/*disabled*/};
    this.closeL = function() {/*disabled*/};
};


/**********************
 *                    *
 * PAYMENT COMPLETED  *
 *                    *
 **********************/
let PaymentCompleted = function (session, transactionId) {

    this.keyboardInput = function (e){
        pos.paymentCompletedKeyboard(e);
    };

    this.setup = function(){
        session.journal.updateJournal(session, transactionId);
        session.toAccount = null;
        pos.setResponseLeftSuccess("Success! Press STOP or OK");
        pos.setResponseRightSuccess("id: " + transactionId);
    };

    this.exit = function(){
    };

    this.stopL = function () {
        pos.shortButtonPress('stopButtonL');
        session.transactionAmount = null;
        session.changeState(new TransactionMode(session));
    };

    this.okL = function() {
        pos.shortButtonPress('okButtonL');
        session.changeState(new PaymentPending(session));
        pos.inputLeft.innerText = "€" + session.transactionAmount;
    };

    this.addNumL = function(e) {/*disabled*/}; this.addNumR = function(e) {/*disabled*/};
    this.delL = function() {/*disabled*/}; this.delR = function() {/*disabled*/};
    this.transL = function() {/*disabled*/}; this.connL = function() {/*disabled*/};
    this.openL = function() {/*disabled*/}; this.closeL = function() {/*disabled*/};
    this.stopR = function() {/*disabled*/}; this.okR = function() {/*disabled*/};
};


/************************
 *                      *
 * TRANSACTION STOPPED  *
 *                      *
 ************************/
let TransactionStopped = function (session, message) {

    this.keyboardInput = function (e){
        pos.transactionStoppedKeyboard(e);
    };

    this.setup = function(){
        session.toAccount = null;
        pos.setResponseLeftFail("Transaction stopped. Press STOP or OK");
        message === undefined ? pos.setResponseRightFail("Transaction stopped")
            :  pos.setResponseRightFail(message);

    };

    this.exit = function(){
    };

    this.stopL = function () {
        pos.shortButtonPress('stopButtonL');
        session.transactionAmount = null;

        session.changeState(new TransactionMode(session));
    };
    this.okL = function() {
        pos.shortButtonPress('okButtonL');
        session.changeState(new PaymentPending(session));
    };

    this.addNumL = function(e) {/*disabled*/}; this.addNumR = function(e) {/*disabled*/};
    this.delL = function() {/*disabled*/}; this.delR = function() {/*disabled*/};
    this.transL = function() {/*disabled*/}; this.connL = function() {/*disabled*/};
    this.openL = function() {/*disabled*/}; this.closeL = function() {/*disabled*/};
    this.stopR = function() {/*disabled*/}; this.okR = function() {/*disabled*/};
};

let session = new Session();
let pos = new PosGui();

window.onload = function() {
    pos.setPressAndUnpressed("closeButtonL", "openButtonL");
    if(identificationNumber !== 0){
        session.connected = true;
        session.changeState(new TransactionMode(session));
    } else {
        session.connected = false;
        session.changeState(new ConnectionMode(session));
    }
};