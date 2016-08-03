$(document).ready(function(){
    $("#enter").mouseover(function(){$("#enter").css("background-color","656565");});
    $("#enter").mouseout(function(){$("#enter").css("background-color","lightseagreen");});

    $("#enter").click(function(){
        openShodow();
    });
    $("#exitDialog").click(function(){
        closeShodow();
    });
    $("#buttonEnter").click(function(){
        var name = $("#name").val();
        var password = $("#password").val();
        var flag = true;
        if (name.length < 1 ) {nameRed(); flag = false;}
        if (password.length < 1) {passwordRed(); flag = false;}
        if(flag) {InquirySerer(name, password);}
    });

    function InquirySerer( name, password ) {
        $.post("server", { login: name, password: password } ).done(function (data) {
            if (data == "false") {
                nameRed();passwordRed();
                alert("Не верный лигин, или пароль.");
            }
            else {
                closeShodow();
            }
        });
    }
    function closeShodow() {
        $("#shodow").css("display","none");
        $("#dialog-form").css("display","none");
    }
    function openShodow() {
        $("#shodow").css("display","block");
        $("#dialog-form").css("display","block");
    }
    function nameRed() {
        $("#name").css("border-color", "red");
    }
    function passwordRed() {
        $("#password").css("border-color", "red");
    }
    $("#name").click(function(){$("#name").css("border-color","white")})
    $("#password").click(function(){$("#password").css("border-color","white")})
});
