var questions = {
    count:0,
    question:[],
    addElement:function (value) {
        this.question[this.count] = value
    }
}
var svaz ={
    idObject:0,
    idProropertis:0,
    apply:function (idOnject, idPropertis) {
        this.idObject = idOnject;
        this.idProropertis = idPropertis;
    }
}

var object_ = {
    id:0,
    name:"",
    question:"",
    propertis:[],
    count:0,
    addProperty: function addElement (value) {
        this.propertis[this.count]= value;
        this.count += 1;
    },
    apply:function (id, name, question) {
        this.id = id;
        this.name = name;
        this.question = question;
    }
}
var property_ ={
    value:"",
    id:0,
    apply:function (id,value) {
        this.value = value;
        this.id = id;
    },
}

$(document).ready(function() {

    var symbolLength = 7.5;//длина символа
    var indent = 20; // отступ

    $("#editor").prepend('<input type="text"  id="name" class="object">')

    $(".object").keyup(function () {
        $(".object").css("width",(indent + (symbolLength * $(".object").val().length)))
        if(event.keyCode == 13){
            $("#textDialog-form").prepend("<p>Выбор типа объекта</p>")
            openShodow()
        }
    })

    //выбор текстового типа
    $("#enterText").click(function () {
        $("#selectType").css("display","none")
        $("#textDialog-form").prepend("<p>Свойства объекта</p>")
        $("#selectText").css("display", "block")
        $("#selectText").prepend("<li><input type='text' class='propertyObject'></li>")

    })


    $("#exitDialog").click(function(){
        closeShodow();
    });
    function closeShodow() {
        $("#shodow").css("display","none");
        $("#dialog-form").css("display","none");
    }
    function openShodow() {
        $("#shodow").css("display","block");
        $("#dialog-form").css("display","block");
    }
})