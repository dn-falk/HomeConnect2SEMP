//Settings JavaScript File

//Eventlistener


//Delete Home Connect
function deleteHomeConnect(){
    var val = confirm("Sind Sie sicher?");
    if (val === true) {
        window.location = "/oauth2/delete";
    }
}

function deleteSEMP(){
    var val = confirm("Sind Sie sicher?");
    if (val === true) {
        window.location = "/sempmanager/delete";
    }
}

function authorizeHomeConnect() {

    if ($("#clientID").val() && $("#clientSecret").val()) {
        var val = confirm("Sind Sie sicher?");
        if (val === true) {
            window.location = "/oauth2/authorize";
        }
    } else {
        alert("Bitte Client ID und Client Secret eingeben");
    }

}
function refreshHomeConnect(){
    var val = confirm("Sind Sie sicher?");
    if (val === true) {
        window.location = "/oauth2/refresh";
    }
}

(function () {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
})()


