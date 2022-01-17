document.addEventListener("DOMContentLoaded", function(){

    firebase.database().ref('admin_web').on('value', function(snapshot){
        document.getElementById('para').value = snapshot.val().email;
        document.getElementById('txt').value = snapshot.val().txt;
        document.getElementById('cc').value = snapshot.val().cc;
        document.getElementById('ass').value = snapshot.val().ass;
        
    });
})