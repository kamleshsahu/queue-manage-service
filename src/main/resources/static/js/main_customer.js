'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();

    if (username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/javatechie');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
  //  event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/inqueue', onMessageReceived_inqueue);
    stompClient.subscribe('/topic/cancelled', onMessageReceived_cancelled);
    stompClient.subscribe('/topic/processed', onMessageReceived_processed);
    // Tell your username to the server
    // stompClient.send("/app/chat.register",
    //     {},
    //     JSON.stringify({sender: username, type: 'JOIN'})
    // )

    // stompClient.send("/app/man.enqueue",
    //     {},
    //     JSON.stringify({id: username})
    // )

    //connectingElement.classList.add('hidden');
}

function onError(error) {
    console.log(error);
    // connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    // connectingElement.style.color = 'red';
}

function addInQueue() {
    if (stompClient) {
        var man = {
            id: username
        };

        stompClient.send("/app/man.enqueue", {}, JSON.stringify(man));
   //     messageInput.value = '';
    }
}

function removeFromQueue() {

    if (stompClient) {
        var man = {
            id: username
        };

        stompClient.send("/app/man.dequeue", {}, JSON.stringify(man));
       // messageInput.value = '';
    }
}


function onMessageReceived_inqueue(payload) {
    reloadQueue();
}

function onMessageReceived_processed(payload) {
    reloadQueue();
}

function onMessageReceived_cancelled(payload) {
    reloadQueue();
}

function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

$(".user").on('click', function (event) {
    console.log(event.target.id)
    document.querySelector('#name').value = event.target.id;
    connect();
   // usernameForm.submit();
})

if (usernameForm)
    usernameForm.addEventListener('submit', connect, true);

