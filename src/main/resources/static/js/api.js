class API {
    constructor() {
    }

    fetchGet(url, header) {
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

    fetchPost(url, body) {
        return fetch(url, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
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

    isUsernameUnique(username) {
        return this.fetchGet(`/api/username?username=${username}`);
    }

    isBSNUnique(bsn){
        return this.fetchGet(`/api/bsn?bsn=${bsn}`);
    }

    isBusinessAccount(accountNumber) {
        return this.fetchGet(`/api/isBusinessAccount?accountNumber=${accountNumber}`);
    }

    postConnectionRequest(connectionRequest){
        return this.fetchPost(`/pos/connect`, connectionRequest)
    }

    postPaymentData(paymentData){
        return this.fetchPost(`/pos/client`, paymentData)
    }

    postSignUp(customerJson){
        return this.fetchPost(`/signup`, customerJson)
    }


    cityAddress(){
        const header = new Headers;
        const TOKEN = "ccf855f3-4bd0-4cd6-8f12-25c9e254efd2";
        header.append('Authorization', 'Bearer ' + TOKEN);
        let url = `https://postcode.tech/api/v1/postcode?postcode=${document.getElementById("postalCode").value}&number=${document.getElementById("houseNumber").value}`;

        return this.fetchGet(url, header);
    }

}