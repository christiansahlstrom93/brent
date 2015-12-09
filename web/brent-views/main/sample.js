function invoker() {
    var link ="http://thevarguy.com/site-files/thevarguy.com/files/gallery_images/tesla-model-s-apple.jpg?1438199158";
    var name = "Tommy";
    $.ajax({
        url: "AdServlet",
        type: 'POST',
        dataType: 'JSON',
        data:  {link : link,name : name},
        success: function (data) {
            $(function () {
                function AppViewModel() {
                    this.firstName = ko.observable(data.name);
                    this.imagePath = ko.observable(data.path);
                }
                ko.applyBindings(AppViewModel());
            });
        }
    });
    
   //window.open("../Brent/brent-views/adviews/adview-template.html#","_self");
}


