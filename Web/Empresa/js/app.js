document.addEventListener("DOMContentLoaded", function(){

  var txtEmail = document.getElementById('txtEmail');
  var txtPass = document.getElementById('txtPass');
  var btn = document.getElementById('btn');

  btn.addEventListener("click", function(){
    firebase.auth().signInWithEmailAndPassword(txtEmail.value, txtPass.value)
      .then((user) => {
        window.location.href= 'table.html'
      })
      .catch((error) => {
        var errorCode = error.code;
        var errorMessage = error.message;
      
        if(txtEmail == ""){
          alert("Email obrigatório");
        }
        else if(txtPass == ""){
          alert("Password obrigatória");
        }
        else if(errorCode == 'auth/invalid-email'){
          alert("Email inválido");
        }
        else if(errorCode == 'auth/user-disabled'){
          alert("Utlizador indisponivel");
        }
        else if(errorCode == 'auth/user-not-found'){
          alert("Utlizador não encontrado");
        }
        /*else if(errorCode == 'auth/wrong-password'){
          alert("Password Errada");
        }*/
        else{
          alert(errorMessage);
        }
      })
  });

 
})