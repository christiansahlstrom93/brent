function init() {
    var self = this;
    var email = sessionStorage.getItem("email");
    var username = sessionStorage.getItem("username");
    var thumb;

    self.myName = ko.observable(localStorage.getItem("username"));
    self.isVisiting = ko.observable(localStorage.getItem("username") != username);
    self.username = ko.observable(username);
    self.firstname = ko.observable();
    self.lastname = ko.observable();
    self.address = ko.observable();
    self.areacode = ko.observable();
    self.phonenumber = ko.observable();
    self.rate = ko.observable();
    self.votes = ko.observable();
    self.email = ko.observable(email);
    self.city = ko.observable();
    self.userImageurl = ko.observable();
    self.ads = ko.observable([]);
    self.adToBeEdit = ko.observable([]);
    self.isMobile = ko.observable(mobilecheck());
    self.isComputer = ko.observable(!mobilecheck());
    self.editAd = ko.observable(true);
    self.startEdit = ko.observable(false);
    self.preview = ko.observable(false);
    self.imageOrient = ko.observable();
    self.adImageUrl = ko.observable();
    self.thumbnail = ko.observable();

    if (mobilecheck()) {
        self.thumbNails = ko.observable("thumbnail webThumbMyPages");
        self.loginClass = ko.observable("rightPhoneMyPages");
        thumb = "thumbnail userImagemobile pull-left";
        self.containerStyle = ko.observable("container newadframemobile");
        self.popUpDiv = ko.observable("popupContactMobile");
        self.closeButtonDiv = ko.observable("closemobile");
    } else {
        self.loginClass = ko.observable("rightWeb");
        self.thumbnail = ko.observable("thumbnail adImagemobiledisplay pull-left");
        thumb = "thumbnail adImagemobiledisplay pull-left";
        self.thumbNails = ko.observable("thumbnail webThumbMyPages");
        self.editadThumb = ko.observable("thumbnail newadImageweb ");
        self.containerStyle = ko.observable("container newadframeweb");
        self.popUpDiv = ko.observable("popupContact");
        self.closeButtonDiv = ko.observable("close");
    }

    if (localStorage.getItem("username") != username) {
        self.adHeadline = ko.observable(username + "s senaste annonser");
        self.ratingLine = ko.observable(username + "s rating");
    } else {
        self.adHeadline = ko.observable("Dina senaste annonser");
        self.ratingLine = ko.observable("Din rating");
    }

    $.ajax({
        url: "../../../MyPagesServlet",
        type: 'POST',
        dataType: 'JSON',
        data: {email: email},
        success: function (response) {
            $(function () {
                function UserViewModel() {
                    self.ads(response.ads);
                    self.firstname(response.data[0].firstname);
                    self.lastname(response.data[0].lastname);
                    self.address(response.data[0].address);
                    self.phonenumber(response.data[0].phonenumber);
                    self.areacode(response.data[0].areacode);
                    self.rate(response.data[0].rateText);
                    self.votes(response.data[0].votes);
                    self.city(response.data[0].city);
                    self.userImageurl(response.data[0].userImageurl);

                    self.thumbnail(thumb + " " + response.data[0].imageorientation);

                    handleRates(response.data[0].rate);


                    self.logout = function () {
                        setCookie("username", "", 365);
                        self.username("");
                        history.back();
                    };
                    self.createAd = function () {
                        window.open("../ad/newad.html", "_self");
                    };
                    self.search = function () {
                        window.open("../../adviews/adview-template.html", "_self");
                    };
                    self.goBack = function () {
                        self.editAd(true);
                        self.preview(false);
                        self.startEdit(false);
                    };
                    self.editAds = function (data) {
                        if (localStorage.getItem("username") === username) {
                            sessionStorage.setItem("adid", data.adid);
                            sessionStorage.setItem("imageURL", data.imageURL);
                            self.adToBeEdit(data);
                            self.editAd(false);
                            self.startEdit(true);
                            self.imageOrient(data.imgorientation);
                            self.adImageUrl(data.imageURL);

                            document.getElementById("adtitle").value = data.title;
                            document.getElementById("priceField").value = parseFloat(data.price);

                            if (data.pricetype === "day") {
                                handleCheckBox('day');
                            } else {
                                handleCheckBox('hour');
                            }
                        } else {
                            self.preview(true);
                            self.editAd(false);
                            self.adToBeEdit(data);
                        }
                    };
                    self.saveAdBasic = function () {
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
                        var imageURL = sessionStorage.getItem("imageURL");
                        var adid = sessionStorage.getItem("adid");

                        if (adtext && firstname && lastname && mail && phone && place && price && title && pricetype && adtext) {
                            $.ajax({
                                url: "../../../EditAdServlet",
                                type: 'POST',
                                dataType: 'JSON',
                                data: {firstname: firstname, lastname: lastname, mail: mail,
                                    phone: phone, place: place, price: price, title: title, pricetype: pricetype, adtext: adtext, imageurl: imageURL
                                    , imgorientation: imageorientation, adid: adid},
                                success: function (response) {
                                    if (response.state) {
                                        document.getElementById('success').style.visibility = 'visible';
                                        document.getElementById('fail').style.visibility = 'hidden';
                                        setTimeout(function () {
                                            self.editAd(true);
                                            self.startEdit(false);
                                            document.getElementById('success').style.visibility = 'hidden';
                                        }, 2000);
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
                    };

                    self.turnImage = function () {
                        var className;
                        if (mobilecheck()) {
                            className = "thumbnail editadImagemobile";
                        } else {
                            className = "thumbnail editadImageweb";
                        }
                        var element = document.getElementById('blah');

                        if (hasClass(element, 'north')) {
                            element.className = className + " west";

                        } else if (hasClass(element, 'east')) {
                            element.className = className + " north";
                        } else if (hasClass(element, 'west')) {
                            element.className = className + " south";
                        } else {
                            element.className = className + " east";
                        }
                    };

                    self.sendMail = function (data) {
                        sessionStorage.setItem("mailrecipient", data.email);
                        document.getElementById('abc').style.display = "block";
                        document.getElementById('inputpopmail').value = localStorage.getItem("email");
                    };
                }

                ko.applyBindings(UserViewModel());
            });
        }
    });
}

function handleRates(rates) {

    if (rates <= 0.0) {
        return;
    } else if (rates <= 1.4) {
        document.getElementById("star-1").checked = true;
    } else if (rates <= 2.4) {
        document.getElementById("star-2").checked = true;
    } else if (rates <= 3.4) {
        document.getElementById("star-3").checked = true;
    } else if (rates <= 4.4) {
        document.getElementById("star-4").checked = true;
    } else if (rates <= 5) {
        document.getElementById("star-5").checked = true;
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
$(document).on('change', '.btn-file :file', function () {
    var input = $(this),
            numFiles = input.get(0).files ? input.get(0).files.length : 1,
            label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
    input.trigger('fileselect', [numFiles, label]);
    if (input.get(0).files.length > 0) {
        readURL(this);
        document.getElementById('upload-button').style.visibility = "visible";
        document.getElementById('basicButton').style.visibility = "hidden";
        document.getElementById('blah').style.visibility = "visible";
        document.getElementById('arrow').style.visibility = "visible";
    } else {
        document.getElementById('upload-button').style.visibility = "hidden";
        document.getElementById('basicButton').style.visibility = "visible";
        document.getElementById('arrow').style.visibility = "hidden";
        document.getElementById('blah').style.visibility = "hidden";
    }
});
$(document).ready(function () {
    $('.btn-file :file').on('fileselect', function (event, numFiles, label) {
        var input = $(this).parents('.input-group').find(':text'), log = numFiles > 1 ? numFiles + ' files selected' : label;
        if (input.length) {
            input.val(log);
        }
    });
});

$(function () {
    $('#upload-form').ajaxForm({success: function (msg) {
            saveAd(msg);
        }, error: function (msg) {
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

function hasClass(element, cls) {
    return (' ' + element.className + ' ').indexOf(' ' + cls + ' ') > -1;
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

function getCheckBoxType() {
    if (document.getElementById('daybox').checked) {
        return "day";
    } else {
        return "hour";
    }
}

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
    var adid = sessionStorage.getItem("adid");
    if (adtext && firstname && lastname && mail && phone && place && price && title && pricetype && adtext && imageurl) {
        $.ajax({
            url: "../../../EditAdServlet",
            type: 'POST',
            dataType: 'JSON',
            data: {firstname: firstname, lastname: lastname, mail: mail,
                phone: phone, place: place, price: price, title: title, pricetype: pricetype, adtext: adtext, imageurl: imageurl
                , imgorientation: imageorientation, adid: adid},
            success: function (response) {
                if (response.state) {
                    document.getElementById('success').style.visibility = 'visible';
                    document.getElementById('fail').style.visibility = 'hidden';
                    setTimeout(function () {

                        document.getElementById('success').style.visibility = 'hidden';
                    }, 2000);
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

function div_hide() {
    document.getElementById('abc').style.display = "none";
}

function check_empty() {
    if (document.getElementById('inputpop').value == "" || document.getElementById('inputpopmail').value == "" || document.getElementById('textareapop').value == "") {
        alert("Fyll i alla fälten");
    } else {
        var recipient = sessionStorage.getItem("mailrecipient");
        var sender = document.getElementById('inputpopmail').value;
        var name = document.getElementById('inputpop').value;
        var msg = document.getElementById('textareapop').value;

        $.ajax({
            url: "../../../MailServlet",
            type: 'POST',
            data: {sender: sender, recepeint: recipient, name: name, msg: msg},
            success: function (response) {
                div_hide();
            }
        });
    }
}