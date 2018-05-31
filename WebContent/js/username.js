$(document).ready(function(){
var user;
$.post('Caller', {
    action : "getUsername"
}, function(returnedData) {
    username.innerText=returnedData.username;
    user=returnedData.username;
}, 'json');

function getUsername(){
    return user;
}
});