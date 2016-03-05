var name;

function changeUsername() {
    name = document.getElementById("changeUsername").value;
	document.getElementById("Username").innerHTML = name;
}


var d = document;

var message;

function addRow()
{
    message = d.getElementById('message').value;

    var tbody = d.getElementById('tab1').getElementsByTagName('TBODY')[0];


    var row = d.createElement("TR");
    tbody.appendChild(row);

    var date = d.createElement("TEXT");
    var nameSpace = d.createElement("TEXT");
    var messageSpace = d.createElement("TEXT");
    var idSpace = d.createElement("TEXT");
    var checkbox = d.createElement("INPUT");

    checkbox.type = "checkbox";
    checkbox.value = "checked";

    
    row.appendChild(d.createElement('TD'));
    row.appendChild(d.createElement('TD'));
    row.appendChild(d.createElement('TD'));
    row.appendChild(d.createElement('TD'));
    row.appendChild(d.createElement('TD'));



    
    row.cells[0].appendChild(checkbox);
    row.cells[1].appendChild(date);
    row.cells[2].appendChild(nameSpace);
    row.cells[3].appendChild(messageSpace);
    row.cells[4].appendChild(idSpace);
    
    

    date.innerHTML = Date();
    nameSpace.innerHTML = name;
    messageSpace.innerHTML = message;
    idSpace.innerHTML = guid();

}

function guid() {
  function s4() {
    return Math.floor((1 + Math.random()) * 0x10000)
      .toString(16)
      .substring(1);
  }
  return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
    s4() + '-' + s4() + s4() + s4();
}

function deleteRows() {
	var table = d.getElementById('tab1').getElementsByTagName('TBODY')[0];
    var rowCount = table.rows.length;
    for(var i=0; i<rowCount; i++) {
        var row = table.rows[i];
        var chkbox = row.cells[0].getElementsByTagName('input')[0];
        if('checkbox' == chkbox.type && true == chkbox.checked) {
            table.deleteRow(i);
            rowCount = table.rows.length;
            i = 0;
         }
    }
} 

function editMessage() {
	var id = d.getElementById("idMessage").value;
	var newMessage = d.getElementById("editedMessage").value;
	var table = d.getElementById('tab1').getElementsByTagName('TBODY')[0];
	var rowCount = table.rows.length;

	for(var i = 0; i < rowCount; i++){
	    var row = table.rows[i];
	    var chkbox = row.cells[4].getElementsByTagName('text')[0];
	    if(chkbox.textContent == id){
	        row.cells[3].textContent = newMessage;
		}
	}
}