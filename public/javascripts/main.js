/**
 * Created by david on 12/10/13.
 */


$(function() {

   var app = new App();
   app.read();
});

function App() {
    this.status = new Status("#status");
    this.couicoui = doT.compile($("#couicoui").html());
    var that = this;
    $("#formulaire").submit(function(event) {
        event.preventDefault();
        that.write();
    });
}

App.prototype.read = function() {
    var that = this;
    that.status.fireRead();
    $.ajax({url: "/couicoui", dataType: 'json',
        success: function (data) {
            if (!data || data.length == 0) {
                return;
            }
            $("#couicouiConteneur").html(that.couicoui(data))
            that.status.end();
        },
        error: function () {
            that.status.end();
        }
    });
};

App.prototype.write = function() {
    var that = this;
    that.status.fireWrite();
    var user = $("#formulaire :input[name='user']").val();
    var content = $("#formulaire :input[name='coui']").val();
    $.ajax({
        url : $("#formulaire").attr("action"),
        type: "POST",
        data: "user="+user+"&coui="+content,
        success: function(data) {
            that.read();
        },
        error: function()  {
            that.status.end();
        }
    });
}

function Status(id) {
    this.id = id;
}

Status.prototype.fireRead = function() {
    $(this.id).removeClass("alert-info alert-warning alert-danger").addClass("alert-success");
    $(this.id).html("Lecture...");
}

Status.prototype.fireWrite = function() {
    $(this.id).removeClass("alert-info alert-warning alert-success").addClass("alert-danger");
    $(this.id).html("Ecriture...");
}

Status.prototype.end = function() {
    $(this.id).removeClass("alert-success alert-danger").addClass("alert-warning");
    $(this.id).html("Status");
}