/**
 * Created by david on 12/10/13.
 */


$(function() {
    var couicoui = doT.compile($("#couicoui").html());
    $.ajax({url: "/couicoui", dataType: 'json',
        success: function (data) {
            if (!data || data.length == 0) {
                return;
            }
            $("#couicouiConteneur").html(couicoui(data))
        },
        error: function () {
        }
    });

});