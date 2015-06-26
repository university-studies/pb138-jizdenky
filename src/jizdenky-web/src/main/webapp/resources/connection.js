$(document).ready(function() {
    $("#from").select2({
        placeholder: "Select station",
        minimumInputLength: 1,
        maximumSelectionSize:1,
        width: "100%"
    });

    $("#to").select2({
        placeholder: "Select station",
        minimumInputLength: 1,
        maximumSelectionSize: 1,
        width: "100%"
    });

    $("#passengers").on("change", function() {
        var number = $("#passengers").val();

        if (number === "1") {
            $("#passenger-type-1-div").css("visibility", "visible");
            $("#passenger-type-2-div").css("visibility", "hidden");
        } else if (number === "2") {
            $("#passenger-type-1-div").css("visibility", "visible");
            $("#passenger-type-2-div").css("visibility", "visible")
        } else {
            $("#passenger-type-1-div").css("visibility", "hidden");
            $("#passenger-type-2-div").css("visibility", "hidden");
        }
    });

    $("#datepicker").datepicker();
    $("#datepicker").datepicker("option", "dateFormat", "yy-mm-dd").datepicker("setDate",
        $("#datepicker").attr("value") ? $("#datepicker").attr("value") : new Date());
});
