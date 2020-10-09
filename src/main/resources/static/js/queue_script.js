fetch("./queues.html")
    .then(response => {
        return response.text()
    })
    .then(data => {
        document.querySelector("#queuedata").innerHTML = data;
    });

function reloadQueue(){
    $.getJSON('http://localhost:8080/queue', function(payload) {
        console.log(payload);
        var message = payload.data;
        $("#inQueue").empty();
        for (let i = 0; i < message.length; i++) {
            var messageElement = document.createElement('li');
            var avatarElement = document.createElement('i');
            var avatarText = document.createTextNode(message[i].id);
            avatarElement.appendChild(avatarText);
            avatarElement.style['background-color'] = getAvatarColor(message[i].id);

            messageElement.appendChild(avatarElement);

            var usernameElement = document.createElement('span');
            var usernameText = document.createTextNode("->tokenId:"+message[i].tokenId);
            usernameElement.appendChild(usernameText);
            messageElement.appendChild(usernameElement);

            $("#inQueue").append(messageElement);
        }
    });
}
