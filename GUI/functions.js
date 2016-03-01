var name;

function changeUsername(){
	name = document.getElementById("changeUsername").value;
	document.getElementById("Username").innerHTML = name;
}

function addMessage(){
	var newMessage;

	var tbody = d.getElementById("t01").getElementsByTagName("TBODY")[0];

	newMessage = document.getElementById("name").value;

	var row = d.createElement("TR");
    tbody.appendChild(row);

    var td1 = d.createElement("TD");
    var td2 = d.createElement("TD");
    var td3 = d.createElement("TD");
    var td4 = d.createElement("TD");

    row.appendChild(td1);
    row.appendChild(td2);
    row.appendChild(td3);
    row.appendChild(td4);

    td1.innerHTML = Date();
    td2.innerHTML = name;
    td3.innerHTML = newMessage;
    td4.innerHTML = Math.random();
}

var d = document;

var message;

function addRow()
{
    message = d.getElementById('message').value;

    var tbody = d.getElementById('tab1').getElementsByTagName('TBODY')[0];


    var row = d.createElement("TR");
    tbody.appendChild(row);

    var td1 = d.createElement("TD");
    var td2 = d.createElement("TD");
    var td3 = d.createElement("TD");
    var td4 = d.createElement("TD");

    row.appendChild(td1);
    row.appendChild(td2);
    row.appendChild(td3);
    row.appendChild(td4);

    td1.innerHTML = Date();
    td2.innerHTML = name;
    td3.innerHTML = message;
    td4.innerHTML = Math.random() % 1000 + 1;


}