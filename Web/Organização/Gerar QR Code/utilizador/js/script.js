document.addEventListener("DOMContentLoaded", function(){

	///VARIAVEIS REFISTO
	var txtEmail = document.getElementById('txtemail');
	var txtPassword = document.getElementById('txtpass');
	var txtConfPassword = document.getElementById('txtconfpass');
	var btnregistar = document.getElementById('btnRegistar');
	//// VARIAVEIS LOGIN
	var txtEm = document.getElementById('txtem');
	var txtPW = document.getElementById('txtpw');
	var btnLogin = document.getElementById('login');
	

	///MUDAR LOGIN PARA REGISTO
	document.querySelector('.img-btn').addEventListener('click', function()
		{
			document.querySelector('.cont').classList.toggle('s-signup');
		}
	);

	////CRIAR CONTA
	btnregistar.addEventListener("click", function(){
		firebase.auth().createUserWithEmailAndPassword(txtEmail.value, txtConfPassword.value)

		.then(function(result){
			window.location.href="QRCode.html";
			
		})
		.catch(function(error) {
			var errorCode = error.code;
			var errorMessage = error.message;

			if(txtEmail.value==""){
				alert("Email Obrigatório");
			}
			else if(errorCode == 'auth/email-already-in-use')
			{
			  alert("Email já em uso");
			} 
			else if(errorCode == 'auth/invalid-email')
			{
			  alert("Email inválido.");
			}
			else if(txtPassword.value==""){
				alert("Password obrigatória");
			}
			else if(txtConfPassword.value != txtPassword.value ){
				alert("Passwords nao coicidendem");
			}
			else if(errorCode == 'auth/weak-password')
			{
			  alert("Password Fraca.");
			}
			else if(errorCode == 'auth/argument-error')
			{
			  alert("Campos Invalidos.");
			}	
			else{
				alert(errorMessage);
			}
		});		
		
	});

	////LOGIN
	btnLogin.addEventListener("click", function(){
		firebase.auth().signInWithEmailAndPassword(txtEm.value, txtPW.value)
		.then((user) => {
			window.location.href="QRCode.html";
		})
		.catch((error) => {
			var errorCode = error.code;
			var errorMessage = error.message;

			if(txtEm == ""){
				alert("Email obrigatório");
			}
			else if(txtPW == ""){
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
			else if(errorCode == 'auth/wrong-password'){
				alert("Password Errada");
			}
			else{
				alert(errorMessage);
			}

		});
	});
	
    //pop Up
	window.addEventListener("keydown", checkKeyPress, false);
	function checkKeyPress(key){
		if(key.keyCode == "69"){
			document.querySelector('.bg-modal').style.display = 'flex'
		}
	}

	document.querySelector('.close').addEventListener('click',function(){
		document.querySelector('.bg-modal').style.display = 'none'
	});

	//go to admin page
	var txtpop = document.getElementById('txtpop');
	var passpop = document.getElementById('passpop');
	var btnpop =  document.getElementById('btnpop');
	btnpop.addEventListener("click", function(){
		if(txtpop.value == "Admin"){
			if(passpop.value == "QRGenerator"){
				window.location.href="../../admin/html/index.html";
			}else{
				alert("Password Errada");
			}

		}else{
			alert("User Errado");
		}
	});

});