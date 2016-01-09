function adrequest() {
    var self = this;

    var location = localStorage.getItem("myCity", "nothing");
    var search = "nothing";
    getLocation(location);
    if (!location) {
        location = "nothing";
    }

    self.ads = ko.observableArray([]);
    self.ad = ko.observableArray([]);
    self.imageOrientation = ko.observableArray([]);
    self.showList = ko.observable(true);
    self.displayAd = ko.observable(false);
    self.headerMessage = ko.observable();
    self.isMobile = ko.observable(mobilecheck());
    self.isComputer = ko.observable(!mobilecheck());
    self.isLoggedIn = ko.observable(checkCookie());
    self.loginClass = ko.observable(className());
    self.username = ko.observable(getCookie("username"));
    self.rateText = ko.observable();

    if (mobilecheck()) {
        self.navBarImage = ko.observable("navbar-brand-image");
        self.newUserButton = ko.observable("btn btn-primary newUserButtonMobile");
        self.containerStyle = ko.observable("container newadframemobile");
        self.rowHeight = ko.observable("row rowStylemobile");
        self.displayimageclass = ko.observable("thumbnail adImagewebdisplay");
        self.mobileAdText = ko.observable("mobileTitle");
        self.popUpDiv = ko.observable("popupContactMobile");
        self.closeButtonDiv = ko.observable("closemobile");
    } else {
        self.navBarImage = ko.observable("navbar-brand-image pull-left");
        self.newUserButton = ko.observable("btn btn-primary newUserButtonWeb");
        self.rowHeight = ko.observable("row rowStyleweb");
        self.containerStyle = ko.observable("container newadframeweb");
        self.displayimageclass = ko.observable("thumbnail adImagewebdisplay");
        self.mobileAdText = ko.observable("");
        self.popUpDiv = ko.observable("popupContact");
        self.closeButtonDiv = ko.observable("close");
    }

    $.ajax({
        url: "../../AdServlet",
        type: 'POST',
        dataType: 'JSON',
        data: {location: location, search: search},
        success: function (response) {
            $(function () {
                function AdViewModel() {
                    if (response.ads[0]) {
                        self.ads(response.ads);
                        headerMessage(response.ads[0].headermessage);
                    }

                    self.gotoAd = function (data) {
                        try {
                            self.rateText(data.userInfo[0].rateText);
                            handleRates(data.userInfo[0].rate);
                            self.showList(false);
                            self.displayAd(true);
                            self.ad(data);
                        } catch (ex) {
                        }
                    };
                    self.searchAds = function () {
                        var location = document.getElementById("location").value;
                        var product = document.getElementById("product").value;

                        if (!location) {
                            location = "nothing";
                        }

                        if (!product) {
                            product = "nothing";
                        }

                        makeRequest(self, location, product);
                    };

                    self.goBack = function () {
                        self.showList(true);
                        self.displayAd(false);
                    };

                    self.login = function () {
                        var usr = document.getElementById("username").value;
                        var pass = document.getElementById("password").value;
                        if (usr != "" && pass != "") {
                            $.ajax({
                                url: "../../LoginServlet",
                                type: 'POST',
                                data: {username: usr, password: pass},
                                success: function (response) {
                                    if (response != "failed") {
                                        setCookie("username", response, 365);
                                        setCookie("email", usr, 365);
                                        localStorage.setItem("email", getCookie("email"));
                                        localStorage.setItem("username", response);
                                        self.isLoggedIn(true);
                                        self.username(response);
                                    } else {

                                    }
                                }
                            });
                        }

                    };

                    self.logout = function () {
                        setCookie("username", "", 365);
                        localStorage.setItem("username", "");
                        self.isLoggedIn(false);
                        self.username("");
                    };

                    self.createAd = function () {
                        window.open("../profile-views/ad/newad.html", "_self");
                    };

                    self.createUser = function () {
                        window.open("../profile-views/create-users/createUser.html", "_self");
                    };

                    self.myPages = function () {
                        sessionStorage.setItem("username", getCookie("username"));
                        sessionStorage.setItem("email", getCookie("email"));
                        window.open("../profile-views/user-view/user-view-template.html", "_self");
                    };

                    self.gotoProfile = function (data) {
                        sessionStorage.setItem("username", data.userInfo[0].firstname);
                        sessionStorage.setItem("email", data.userInfo[0].mail);
                        window.open("../profile-views/user-view/user-view-template.html", "_self");
                    };

                    self.sendMail = function (data) {
                        sessionStorage.setItem("mailrecipient", data.email);
                        document.getElementById('abc').style.display = "block";
                        document.getElementById('inputpopmail').value = getCookie("email");
                    };
                }
                ko.applyBindings(AdViewModel());
            });
        }
    });
}

function makeRequest(self, location, product) {
    $.ajax({
        url: "../../AdServlet",
        type: 'POST',
        dataType: 'JSON',
        data: {location: location, search: product},
        success: function (response) {
            self.ads(response.ads);
            self.showList(true);
            self.displayAd(false);
        }
    });
}

window.mobilecheck = function () {
    var check = false;
    (function (a) {
        if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino/i.test(a) || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0, 4)))
            check = true;
    })(navigator.userAgent || navigator.vendor || window.opera);
    return check;
};

function getLocation(location) {
    $.get("http://ipinfo.io", function (response) {
        if (response.city && response.city !== "") {
            localStorage.setItem("myCity", response.city);
        }
    }, "jsonp");
}

function checkCookie() {
    var state = false;
    var user = getCookie("username");
    if (user != "") {
        state = true;
    }
    return state;
}

function className() {
    var str;

    if (mobilecheck()) {
        str = "rightPhone";
    } else {
        str = "rightWeb";
    }
    return str;
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ')
            c = c.substring(1);
        if (c.indexOf(name) == 0)
            return c.substring(name.length, c.length);
    }
    return "";
}

function handleRates(rates) {

    if (rates <= 0.0) {
        document.getElementById("star-1").checked = false;
        document.getElementById("star-2").checked = false;
        document.getElementById("star-3").checked = false;
        document.getElementById("star-4").checked = false;
        document.getElementById("star-5").checked = false;
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
            url: "../../MailServlet",
            type: 'POST',
            data: {sender: sender, recepeint: recipient, name: name, msg: msg},
            success: function (response) {
                div_hide();
            }
        });
    }
}