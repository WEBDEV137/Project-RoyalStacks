class Journal{

    openingTime;
    closingTime;
    transactions = [];
    totalAmount = 0.00;

    constructor(openingTime) {
        this.openingTime = openingTime;
    }

    headers = {
        date: 'Date',
        time: 'Time',
        transactionId: 'Transaction ID',
        accountNumber: "Account Number",
        amount: "Amount"
    };

    updateJournal(session, transactionId){
        let journalData = {
            time : new Date().toLocaleString("en-GB"),
            transactionId : transactionId,
            accountNumber : session.toAccount,
            amount : '€' + session.transactionAmount
        };

        session.journal.transactions.push(journalData);
        session.journal.totalAmount = (Number(session.journal.totalAmount)
            + Number(session.transactionAmount)).toFixed(2);
    }

    download() {
        this.transactions.unshift(this.headers);
        const csv = this.convertToCSV(JSON.stringify(this.transactions));
        const exportedFilename = "Journal of " + session.journal.openingTime + '.csv' || 'export.csv';
        const blob = new Blob([csv], {type: 'text/csv;charset=utf-8;'});

        if (navigator.msSaveBlob) { // IE 10+
            navigator.msSaveBlob(blob, exportedFilename);
        } else {
            let link = document.createElement("a");
            if (link.download !== undefined) {
                const url = URL.createObjectURL(blob);
                link.setAttribute("href", url);
                link.setAttribute("download", exportedFilename);
                link.style.visibility = 'hidden';
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
            }
        }
    }

    convertToCSV(objArray) {
        const array = JSON.parse(objArray);

        let str = 'Opening Date,Opening Time,Closing Date,Closing Time,Total Amount'
            + '\r\n' + session.journal.openingTime + ',' + session.journal.closingTime
            + ',' + '€' + session.journal.totalAmount + '\r\n';

        for (let i = 0; i < array.length; i++) {
            let line = '';
            for (let index in array[i]) {
                if (line !== '') line += ','
                line += array[i][index];
            }
            str += line + '\r\n';
        }
        return str;
    }
}