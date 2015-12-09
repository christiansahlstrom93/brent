function initViewModel() {
    var name = localStorage.getItem("username");
    var self = this;
    self.firstname = ko.observable();
    self.lastname = ko.observable();
    self.mail = ko.observable();
    self.phone = ko.observable();
    self.place = ko.observable();
    self.admessage = ko.observable();

    $.ajax({
        url: "../../../NewAdServlet",
        type: 'POST',
        dataType: 'JSON',
        data: {username: name},
        success: function (response) {
            $(function () {
                function NewAdViewModel() {
                    var data = response.credentials[0];
                    firstname(data.firstname);
                    lastname(data.lastname);
                    mail(data.mail);
                    phone(data.phone);
                    place(data.location);
                    admessage("Skapa en annons");
                }
                ko.applyBindings(NewAdViewModel());
            });
        }
    });
}

$(function () {
    $('#upload-form').ajaxForm({
        success: function (msg) {
            saveAd();
        },
        error: function (msg) {
            alert(msg.toString());
        }
    });
});

function saveAd() {

    var firstname = $("#firstname").val();
    var lastname = $("#lastname").val();
    var mail = $("#mail").val();
    var phone = $("#phone").val();
    var place = $("#place").val();
    var price = $("#price").val();
    var title = $("#adtitle").val();
    var pricetype = getCheckBoxType();
    var adtext = $("#adtext").val();

    if (adtext && firstname && lastname && mail && phone && place && price && title && pricetype && adtext) {
        $.ajax({
            url: "../../../SaveAdServlet",
            type: 'POST',
            dataType: 'JSON',
            data: {firstname: firstname, lastname: lastname, mail: mail,
                phone: phone, place: place, price: price, title: title, pricetype: pricetype, adtext: adtext},
            success: function (response) {
                document.getElementById('success').style.visibility = 'visible';
                document.getElementById('fail').style.visibility = 'hidden';
            },
            error: function (response) {
                console.log(response);
            }

        });
    } else {
        document.getElementById('success').style.visibility = 'hidden';
        document.getElementById('fail').style.visibility = 'visible';
    }
}

$(document).on('change', '.btn-file :file', function () {
    var input = $(this),
            numFiles = input.get(0).files ? input.get(0).files.length : 1,
            label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
    input.trigger('fileselect', [numFiles, label]);

    if (input.get(0).files.length > 0) {
        readURL(this);
        document.getElementById('upload-button').style.visibility = "visible";
        document.getElementById('blah').style.visibility = "visible";
    } else {
        document.getElementById('upload-button').style.visibility = "hidden";
    }
});

$(document).ready(function () {
    $('.btn-file :file').on('fileselect', function (event, numFiles, label) {
        var input = $(this).parents('.input-group').find(':text'),
                log = numFiles > 1 ? numFiles + ' files selected' : label;

        if (input.length) {
            input.val(log);
        }
    });
});


function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#blah').attr('src', e.target.result);
        };

        reader.readAsDataURL(input.files[0]);
    }
}

function handleCheckBox(id) {
    if (id === 'day') {
        $("#daybox").prop("checked", true);
        $("#hourbox").prop("checked", false);
    } else if (id === 'hour') {
        $("#daybox").prop("checked", false);
        $("#hourbox").prop("checked", true);
    }
}

function getCheckBoxType() {
    if (document.getElementById('daybox').checked) {
        return "day";
    } else {
        return "hour";
    }
}