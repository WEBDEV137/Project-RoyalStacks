//Creating a new XMLHttpRequest object
const request = new XMLHttpRequest();

//Initialize request
request.open("GET","/api/top10transactions", true);

//Send HTTP request & handle server output
request.onreadystatechange = function() {
    if (request.readyState === 4) {
        if (request.status === 200) {
            createTable(JSON.parse(request.responseText));
        }
        else {
            alert('Something is wrong!');
        }
    }
};
request.send(null);

//Create and fill table
function createTable(top10transactions) {
    const body = document.body,
        tbl = document.createElement('table');
    tbl.style.width = '65%';
    const headerRow = tbl.insertRow();
    createTableHeader("Company name", headerRow);
    createTableHeader("Number of transactions", headerRow);
    createTableHeader("Balance", headerRow);

    top10transactions.forEach(element => {
        const row = tbl.insertRow();
        createTableCell(element.companyName, row);
        createTableCell(element.numberOfTransactions, row);
        createTableCell(
            "€ " + element.balance.toLocaleString(
                undefined,
                {minimumFractionDigits: 2, maximumFractionDigits: 2}),
            row
        );
    });
    body.appendChild(tbl);
}

function createTableHeader(title, row){
    const cell = document.createElement('th');
    cell.appendChild(document.createTextNode(title));
    row.appendChild(cell);
}

function createTableCell(content, row){
    const cell = row.insertCell();
    cell.appendChild(document.createTextNode(content));
}