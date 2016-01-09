function initViewModel() {
    var name = localStorage.getItem("email");
    var self = this;
    self.firstname = ko.observable();
    self.lastname = ko.observable();
    self.mail = ko.observable();
    self.phone = ko.observable();
    self.place = ko.observable();
    self.admessage = ko.observable();

    if (mobilecheck()) {
        self.navBarImage = ko.observable("navbar-brand-image");
        self.imageStyle = ko.observable("thumbnail newadImagemobile north");
        self.containerStyle = ko.observable("container newadframemobile");
    } else {
        self.navBarImage = ko.observable("navbar-brand-image pull-left");
        self.imageStyle = ko.observable("thumbnail newadImageweb north");
        self.containerStyle = ko.observable("container newadframeweb");
    }

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
                    place(data.city);
                    admessage("Skapa en annons");
                    self.turnImage = function () {
                        var className;
                        if (mobilecheck()) {
                            className = "newadImagemobile";
                        } else {
                            className = "newadImageweb";
                        }
                        var element = document.getElementById('blah');

                        if (hasClass(element, 'north')) {
                            self.imageStyle("thumbnail " + className + " west");
                        } else if (hasClass(element, 'east')) {
                            self.imageStyle("thumbnail " + className + " north");
                        } else if (hasClass(element, 'west')) {
                            self.imageStyle("thumbnail " + className + " south");
                        } else {
                            self.imageStyle("thumbnail " + className + " east");
                        }
                    };
                }
                ko.applyBindings(NewAdViewModel());
            });
        }
    });
}

function hasClass(element, cls) {
    return (' ' + element.className + ' ').indexOf(' ' + cls + ' ') > -1;
}

$(function () {
    $('#upload-form').ajaxForm({
        success: function (msg) {
            saveAd(msg);
        },
        error: function (msg) {
        }
    });
});

function saveAd(imageurl) {

    var firstname = $("#firstname").val();
    var lastname = $("#lastname").val();
    var mail = $("#mail").val();
    var phone = $("#phone").val();
    var place = $("#place").val();
    var price = $("#priceField").val();
    var title = $("#adtitle").val();
    var pricetype = getCheckBoxType();
    var adtext = $("#adtext").val();
    var imageorientation = getOrientation();

    if (adtext && firstname && lastname && mail && phone && place && price && title && pricetype && adtext && imageurl) {
        $.ajax({
            url: "../../../SaveAdServlet",
            type: 'POST',
            dataType: 'JSON',
            data: {firstname: firstname, lastname: lastname, mail: mail,
                phone: phone, place: place, price: price, title: title, pricetype: pricetype, adtext: adtext, imageurl: imageurl
                , imgorientation: imageorientation},
            success: function (response) {
                if (response.state) {
                    document.getElementById('success').style.visibility = 'visible';
                    document.getElementById('fail').style.visibility = 'hidden';
                    setTimeout(function () {
                        history.back();
                    }, 3000);
                } else {
                    document.getElementById('success').style.visibility = 'hidden';
                    document.getElementById('fail').style.visibility = 'visible';
                }
            },
            error: function (response) {
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
        document.getElementById('arrow').style.visibility = "visible";
    } else {
        document.getElementById('upload-button').style.visibility = "hidden";
        document.getElementById('arrow').style.visibility = "hidden";
        document.getElementById('blah').style.visibility = "hidden";
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

function getOrientation() {

    var element = document.getElementById('blah');

    if (hasClass(element, 'north')) {
        return "north";
    } else if (hasClass(element, 'east')) {
        return "east";
    } else if (hasClass(element, 'west')) {
        return "west";
    } else {
        return "south";
    }

}