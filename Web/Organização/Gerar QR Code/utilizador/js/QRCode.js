document.addEventListener("DOMContentLoaded", function(){
    var btnQR = document.getElementById('criar');
    var nome = document.getElementById('nome');
    var email = document.getElementById('email');
    var tel = document.getElementById('tel');
    var qrcode = new QRCode(document.getElementById('qrcode'));

    var num = /^[0-9]+$/;
    var filtro_email = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

    
    

    btnQR.addEventListener("click", function(){
        
            if(tel.value.match(num)){
               if(email.value.match(filtro_email)){
                firebase.initializeApp({
                    apiKey: "AIzaSyBaSdpYnaS7FRs5mLhxX_y7p46lU1D8x_0",
                    authDomain: "qr-generator-fbd48.firebaseapp.com",
                    projectId: "qr-generator-fbd48",
                });
                var database = firebase.firestore();
                const userstb = database.collection("users");
                const ID = userstb.doc(); console.log(ID.id);
                ID.set({
                    id: ID.id,
                    Nome: nome.value,
                    Email: email.value,
                    Telefone: tel.value
                })
                .then(()=> {console.log('Sucesso');})
                .catch(error => {console.error(error)});
                
                var data = ID.id;
                qrcode.makeCode(data);
        
                document.querySelector('.email').style.display = 'block'
               }
               else{
                   alert("email")
               }
            }
            else{
                alert("Preencha corretamente os campos");
            }
        
        
    

        
    });
});