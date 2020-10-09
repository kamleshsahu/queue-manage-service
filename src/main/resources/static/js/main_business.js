'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea_inqueue = document.querySelector('#messageArea_inqueue');
var messageArea_cancelled = document.querySelector('#messageArea_cancelled');
var messageArea_processed = document.querySelector('#messageArea_processed');
var inqueue = document.querySelector("#inQueue");
var processed = document.querySelector('#processed');

//var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    //  username = document.querySelector('#name').value.trim();
    username = 'khusi hospital'
    if (username) {
        //   usernamePage.classList.add('hidden');
        //   chatPage.classList.remove('hidden');

        var socket = new SockJS('/javatechie');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    //event.preventDefault();
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

    //   connectingElement.classList.add('hidden');
}


function onError(error) {
    console.log(error);
    //  connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    //  connectingElement.style.color = 'red';
}

function processFirst() {
    //  var messageContent = messageInput.value.trim();
    console.log("process first button clicked")
    if (stompClient) {
        var man = {
            id: username
        };

        stompClient.send("/app/processFirst", {}, JSON.stringify(man));
    }
    reloadQueue();
}

function send(event) {
    var messageContent = messageInput.value.trim();

    if (messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };


        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
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

connect()
