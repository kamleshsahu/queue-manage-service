fetch("./queues.html")
    .then(response => {
        return response.text()
    })
    .then(data => {
        document.querySelector("#queuedata").innerHTML = data;
    });

function reloadQueue(){
    var protocol = location.protocol;
    var slashes = protocol.concat("//");
    var host = slashes.concat(window.location.host);
    var url = host+'/queue';
    var temp = "https://queue-manangement-service.herokuapp.com/queue";
    url = temp;
    console.log(url)

    $.getJSON( url, function(payload) {
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
            var usernameText = document.createTextNode("-> "+message[i].rank);
            usernameElement.appendChild(usernameText);
            messageElement.appendChild(usernameElement);

            $("#inQueue").append(messageElement);
        }
    });
}
