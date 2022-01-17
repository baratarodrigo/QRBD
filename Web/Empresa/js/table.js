document.addEventListener("DOMContentLoaded", function(){

const cafelist = document.getElementById('table');

function renderTable(doc){
    let li = document.createElement('tr');
    let name = document.createElement('td');
    let tele = document.createElement('td');
    let email = document.createElement('td');
    let cross = document.createElement('td');

    li.setAttribute('data-id', doc.id);
    name.textContent = doc.data().Nome;
    tele.textContent = doc.data().Telefone;
    email.textContent = doc.data().Email;
    cross.textContent = 'X';

    li.appendChild(name);
    li.appendChild(tele);
    li.appendChild(email);
    li.appendChild(cross);

    cafelist.appendChild(li);

    cross.addEventListener('click', (e) => {
        e.stopPropagation();
        let id = e.target.parentElement.getAttribute('data-id');
        db.collection('users').doc(id).delete();
        
    })
}

db.collection('users').get().then((snapshot) =>{
    snapshot.docs.forEach(doc =>{
        renderTable(doc);
    })
})

});