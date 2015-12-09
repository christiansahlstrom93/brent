function initViewModel() {

    var name = localStorage.getItem("username");
    var self = this;
    self.firstname = ko.observable();
    self.lastname = ko.observable();
    self.mail = ko.observable();
    self.phone = ko.observable();
    self.place = ko.observable();

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
                }
                ko.applyBindings(NewAdViewModel());
            });
        }
    });
}