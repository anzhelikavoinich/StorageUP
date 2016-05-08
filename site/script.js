var messagelist = [];
var count = 0;
function buttonOk(){
    var namefield = document.getElementById("namefield");
    document.getElementById("username").innerText = namefield.value;
    namefield.value= "";
}
function buttonSend(){
    if(document.getElementById("username").innerText == "UserName"){
        alert("Enter name, please");
        return;
    }
    var table = document.createElement("table");
    table.className = "message";
    table.onclick = function(){selectMessage(this)};
    var trname = document.createElement("tr");
    var trmes = document.createElement("tr");
    var trtime = document.createElement("tr");
    var trid = document.createElement("tr");
    var id = uniqueId();

    trid.innerHTML = id;
    trid.style.display = "none";

    trname.className = "message-text";
    trname.innerHTML = document.getElementById("username").innerText;

    var messagetext = document.getElementById("messagetext");
    trmes.innerHTML = "<p class='message-text'>" + messagetext.value + "</p>";

    trtime.className = "message-text";
    var now = new Date();
    var strtime = "" + now.getHours() + ":" + now.getMinutes();
    trtime.innerHTML = strtime;


    table.appendChild(trname);
    table.appendChild(trmes);
    table.appendChild(trtime);
    table.appendChild(trid);
    document.getElementById("work-area").appendChild(table);

    messagelist.push(newMessage(messagetext.value, document.getElementById("username").innerText, strtime, id  ));
    messagetext.value = "";
    store(messagelist);
}
function selectMessage(div){
    if(div.className == "selected-message") {
        div.className = "message";
        count--;
    }
    else {
        div.className = "selected-message";
        count++;
    }
    if(count > 1){
        document.getElementById("edit").style.display = "none";
    } else {
        document.getElementById("edit").style.display = "inline";
    }
}
function deleteMessage(){
    var storagelist = restore();
    var listdel = document.getElementsByClassName("selected-message");
    for(var i =0; i< listdel.length; i++) {


        for(var j = 0; j < storagelist.length; j++){
            if(listdel[i].rows[3].innerHTML == storagelist[j].id){
                storagelist.splice(j,1);
                listdel[i].remove();
            }
        }

    }
    store(storagelist);
}
function changebutton(){
    var buttonSend = document.getElementById("send");
    var message = document.getElementsByClassName("selected-message");
    buttonSend.onclick = function(){editMessage(message[0])}
}
function editMessage(div){
    var trmes = div.rows[1];
    trmes.innerHTML = "<p class='message-text'>" + messagetext.value + "</p>";
    var storagelist = restore();
    var list = document.getElementsByClassName("selected-message");
        for(var j = 0; j < storagelist.length; j++){
            if(list[0].rows[3].innerHTML == storagelist[j].id){
                storagelist[j].description = "<p class='message-text'>" + messagetext.value + "</p>";
            }
    }
    store(storagelist);
    var buttonsend = document.getElementById("send");
    buttonsend.onclick = function(){buttonSend()};
}
function newMessage(text, name, time, id) {
    return {
        description:text,
        username : name,
        time : time,
        id: id
    };
}
function uniqueId() {
    var date = Date.now();
    var random = Math.random() * Math.random();

    return Math.floor(date * random);
}
function restore() {
    if(typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return;
    }
    var item = localStorage.getItem("messageList");
    return item && JSON.parse(item);
}

function store(listToSave) {
    if(typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return;
    }
    localStorage.setItem("messageList", JSON.stringify(listToSave));
}
function fillAllMessage(list){
    for(var i = 0; i < list.length; i++){
        addMessage(list[i]);
    }

}
function addMessage(message){


    var table = document.createElement("table");
    table.className = "message";
    table.onclick = function(){selectMessage(this)};
    var trname = document.createElement("tr");
    var trmes = document.createElement("tr");
    var trtime = document.createElement("tr");
    var trid = document.createElement("tr");

    trname.innerHTML = "<p class='message-text'>" + message.username + "</p>";
    trmes.innerHTML = "<p class='message-text'>" + message.description + "</p>";
    trtime.innerHTML = "<p class='message-text'>" + message.time + "</p>";
    trid.innerHTML = message.id ;
    trid.style.display = "none";

    table.appendChild(trname);
    table.appendChild(trmes);
    table.appendChild(trtime);
    table.appendChild(trid);

    document.getElementById("work-area").appendChild(table);



    messagelist.push(message);
    //document.getElementById("work-area").appendChild(mes);

}
function run(){
    var message = restore();
    fillAllMessage(message);
}

