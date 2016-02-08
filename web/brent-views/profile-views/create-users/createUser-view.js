/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//getting dynamic update on an div based on input
$(function () {
    $("#pass2").keyup(function () {
        var password = $("#pass1").val();


        if (password != $(this).val()) {
            document.getElementById('divPassMatch').style.visibility = 'visible';

            document.getElementById('divPassMatch').className = 'alert alert-danger';

        } else {
            document.getElementById('divPassMatch').style.visibility = 'visible';

            document.getElementById('divPassMatch').className = 'alert alert-success';


        }
    });

});
function createUser(imageurl) {

    var firstName = document.getElementById("firstname").value;
    var lastName = document.getElementById("lastname").value;
    var personNumber = document.getElementById("personnumber").value;
    var email1 = document.getElementById("mail1").value;
    var email2 = document.getElementById("mail2").value;
    var phoneNumber = document.getElementById("phone").value;
    var city = document.getElementById("city").value;
    var address = document.getElementById("address").value;
    var postalCode = document.getElementById("postalcode").value;
    var password1 = document.getElementById("pass1").value;
    var password2 = document.getElementById("pass2").value;
    var matchEmail = false;
    var matchPass = false;
    var matchPerson = false;
    if (email1 == email2) {
        matchEmail = true;

    } else {

        document.getElementById("accountSuccess").style.visibility = 'visible';
        document.getElementById('accountSuccess').className = 'alert alert-danger';
        document.getElementById("createSuccess").text = "De angivna epost addresserna matchar inte";
    }
    if (password1 == password2) {
        matchPass = true;

    } else {

        document.getElementById("accountSuccess").style.visibility = 'visible';
        document.getElementById('accountSuccess').className = 'alert alert-danger';
        document.getElementById("createSuccess").text = "De angivna lösenorden matchar inte";
    }

    if (checkPersonalNumber(personNumber)) {
        matchPerson = true;
    } else {

        document.getElementById("accountSuccess").style.visibility = 'visible';
        document.getElementById('accountSuccess').className = 'alert alert-danger';
        document.getElementById("createSuccess").text = "Det angivna person nummret är inte giltigt";

    }

    if (matchPerson && matchPass && matchEmail && firstName && lastName && personNumber && email1 && email2 && phoneNumber && city
            && address && postalCode && password1 && password2) {
        $.ajax({
            url: "../../../CreateUserServlet",
            type: 'POST',
            data: {firstname: firstName, lastname: lastName, mail: email1,
                phone: phoneNumber, city: city, personnumber: personNumber, address: address, postalcode: postalCode, password: password1,
                imageurl: imageurl, imgorient: getOrientation()},
            success: function (response) {
                if (response == "success") {

                    document.getElementById("accountSuccess").style.visibility = 'visible';
                    document.getElementById('accountSuccess').className = 'alert alert-success';
                    document.getElementById("createSuccess").text = "Ett aktiveringmail har skickats till din angivna epost";

                } else {

                    document.getElementById("accountSuccess").style.visibility = 'visible';
                    document.getElementById('accountSuccess').className = 'alert alert-danger';
                    document.getElementById("createSuccess").text = "Ett fel har uppstått, testa igen om en stund";
                }
            },
            error: function (response) {
            }

        });

    }
}

//open source algorithm for checking personalnumber
function checkPersonalNumber(sPNum)
{
    var numbers = sPNum.match(/^(\d)(\d)(\d)(\d)(\d)(\d)(\d)(\d)(\d)(\d)(\d)(\d)$/);
    var checkSum = 0;

    var d = new Date();
    if (!isDate(sPNum.substring(0, 4), sPNum.substring(4, 6), sPNum.substring(6, 8))) {
        return false;
    }

    if (numbers == null) {
        return false;
    }

    var n;
    for (var i = 3; i <= 12; i++)
    {
        n = parseInt(numbers[i]);
        if (i % 2 == 0) {
            checkSum += n;
        } else {
            checkSum += (n * 2) % 9 + Math.floor(n / 9) * 9
        }
    }

    if (checkSum % 10 == 0) {
        return true;
    }
    return false;
}

function getYear(y) {
    return (y < 1000) ? y + 1900 : y;
}

function isDate(year, month, day)
{
    month = month - 1; // 0-11 in JavaScript
    var tmpDate = new Date(year, month, day);
    if ((getYear(tmpDate.getYear()) == year) &&
            (month == tmpDate.getMonth()) &&
            (day == tmpDate.getDate()))
        return true;
    else
        return false;
}

function turnImage() {

    var className = "thumbnail editadImageweb";

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