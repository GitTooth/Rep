var name;
var messages = new Array;
var key = localStorage.length;


function changeUsername() {
    name = document.getElementById("changeUsername").value;
	document.getElementById("Username").innerHTML = name;
}

function run(myList) {
    var tbody = d.getElementById('tab1').getElementsByTagName('TBODY')[0];
    for (var i = 0; i < localStorage.length; i++) {
        if (localStorage.getItem(i) == null) {
            continue;
        }
        var obj = localStorage.getItem(i);
        obj = JSON.parse(obj);

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

        date.innerHTML = obj.dateArr;
        nameSpace.innerHTML = obj.nameArr;
        messageSpace.innerHTML = obj.messageArr;
        idSpace.innerHTML = obj.idArr;

    }
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
     
    tempDate = Date();
    date.innerHTML = tempDate;
    tempName = name;
    nameSpace.innerHTML = tempName;
    tempMessage = message;
    messageSpace.innerHTML = tempMessage;
    tempId = guid();
    idSpace.innerHTML = tempId;

    //var toText = date: tempDate + " " + tempName + " " + tempMessage + " " + tempId;
    messages.push({ dateArr: tempDate, nameArr: tempName, messageArr: tempMessage, idArr: tempId })

    localStorage.setItem(key, JSON.stringify({ dateArr: tempDate, nameArr: tempName, messageArr: tempMessage, idArr: tempId }));
    var item = localStorage.getItem(key);
    key++;
    //alert(key);
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
        if ('checkbox' == chkbox.type && true == chkbox.checked) {
            table.rows[i].cells[1].innerHTML = "Message deleted";
            table.rows[i].cells[2].innerHTML = " ";
            table.rows[i].cells[3].innerHTML = " ";
            table.rows[i].cells[4].innerHTML = " ";

            var obj = localStorage.getItem(i);
            obj = JSON.parse(obj);

            obj.dateArr = "Message deleted";
            obj.nameArr = " ";
            obj.messageArr = " ";
            obj.idArr = " ";

            localStorage.setItem(i, JSON.stringify({ dateArr: obj.dateArr, nameArr: obj.nameArr, messageArr: obj.messageArr, idArr: obj.idArr }));
            
            //table.deleteRow(i);
            //rowCount = table.rows.length;
            //i = 0;
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
	        var obj = localStorage.getItem(i);
	        obj = JSON.parse(obj);
	        obj.messageArr = newMessage;
	        localStorage.setItem(i, JSON.stringify({ dateArr: obj.dateArr, nameArr: obj.nameArr, messageArr: obj.messageArr, idArr: obj.idArr }));

		}
	}
}

function clearLocalStorage() {
    localStorage.clear();
}

function exit() {
    alert("exit");
}