$(document).ready(function() {
        var height = Math.max($(".col-1").height(), $(".col-2").height());
        $(".col-1").height(height);
        $(".col-2").height(height);
        $(".col-3").height(height);
    });