/**
 * Created by ahmed on 10/8/17.
 */



// UTILS - start

$('.backButton').click(function(){

    $( ".queueWindow" ).fadeOut(400, function(){
        $(".courseSelectionWindow").fadeIn(400);
        $(".backButton").fadeOut(500);
    });
});


// adds the necessary boostrap classnames to adjust the view
// also adds the necessary colors
function makeSureEverythingLooksRight() {

    $('.helperQueue').each( function(){
        $(this).addClass("col-12");
        $(this).addClass("col-md-2");
        $(this).css('background-color', "#343c4f");
    });


    $('.inQueueCard').each( function(){
        $(this).addClass("col-12");
        $(this).addClass("my-1");
    });


    $('.course').each( function(){
        $(this).addClass("col-2");
        var color = randomColor({
            luminosity: 'light',
            hue: 'blue'

        });
        $(this).css('background-color', color);
    });

    $('.helperName').each( function(){
        $(this).addClass("col-12");
        $(this).addClass("py-3");
    });

}



function addHelperNameToSearchBar(){


    $('.helperName').each( function(){
        if ($("select:contains("+$(this).text()+")").length == 0){
            $("select").append("<option >"+$(this).text()+"</option>");
        }
    });


}



function getHelperTime(helperName) {

    $.ajax({
        url: TestUrl + "TimeLeft",
        data: {"name": helperName},
        success: function(result) {

            var mins = Math.floor(result/60);
            var secs = Math.floor(( (result/60) - (mins) )*60) ;

            if (mins < 10){
                mins = "0"+mins;
            }

            if (secs < 10){
                secs = "0"+secs;
            }


            $(".helperName:contains("+helperName+")").parent().find(".helperTime").html(mins+":"+secs);
        }
    });


}


function createQueueCard(name){

    var result = '<div class="inQueueCard">';
    result += '<div class=name>' + name + '</div>';

    if (loggedIn && isStudent && name==loggedInUser){
        result += '<span class="close-icon" data-effect="fadeOut"><i class="fa fa-times"></i></span>'
    }

    result += '</div>';

    return result
}

function createHelperQueueColumn(helperName) {
    var result = "<div class=helperQueue>";

        result += "<div class=helperCard>";
            result += "<div class=helperName>"+helperName + "</div>";
            result += '<div class=helperTime>' + getHelperTime(helperName)+ '</div>';
        result += "</div>";

    result += "</div>";

    return result;
}

// UTILS - end



// LOGIN  ------------- START

var loggedIn = false;
var isStudent = false;
var loggedInUser = "";
var loggedInUserEmail = "";


var startApp = function() {
    gapi.load('auth2', function(){
        // Retrieve the singleton for the GoogleAuth library and set up the client.
        auth2 = gapi.auth2.init({
            client_id: '542691178793-lv4aaha2th1lr7srfb0vdnrhbcng0tgn.apps.googleusercontent.com',
            cookiepolicy: 'single_host_origin',
        });
        attachSignin(document.getElementById('loginButton'));
    });
};

function attachSignin(element) {
    auth2.attachClickHandler(element, {}, goodLogin, function(error) {});
}


function goodLogin(user){

    var email = user.getBasicProfile().getEmail();
    var shortName = user.getBasicProfile().getName();

    var shortName = shortName.split(" ")[0] +" "+ shortName.split(" ")[1];



    if (true){

        $.ajax({
            async : false,

            url: TestUrl+"studentCheck",
            data: "email=" +  email,
            // Return student or CP or not found
            success: function(result) {

                if (result == "helper") {
                    console.log("helper");



                    welcomeUser(shortName, email);

                    updateAllQueues();


                    if ($(".helperName:contains(" + shortName + ")").text() != "") {
                        $(".helperToggle").html("Deactivate");
                        $(".helperPauseToggle").show();

                    }

                    $(".AddToQueueHelperSelection").hide();
                    $(".HelperToolsSelection").show();




                } else if (result == "student") {
                    console.log("student");


                    welcomeUser(shortName, email);

                    updateAllQueues();

                    isStudent = true;



                    $(".AddToQueueHelperSelection").show();
                    $(".HelperToolsSelection").hide();



                } else {
                    console.log("not found");


                    alert("Sorry, you're not in our system.")

                }
            }


        });

    }
    else {
        alert("Sorry, you must use a USC email.")
    }

}

function welcomeUser(shortName, email) {
    $(".greeting a").html("Hi, "+ shortName);
    $(".registerButtonContainer").hide();
    $(".loginButtonContainer").hide();
    $(".logoutButtonContainer").show();
    $(".greeting").show();
    loggedIn = true;
    loggedInUser = shortName;
    loggedInUserEmail = email;
}

function logOutUser() {
    $(".greeting a").html("");
    $(".registerButtonContainer").show();
    $(".loginButtonContainer").show();
    $(".logoutButtonContainer").hide();
    $(".greeting").hide();

    if ($(".queueWindow").css('display') !== 'none'){
        loadQueueSystem($(".courseTitle").text());
    }

    $(".AddToQueueHelperSelection").hide();
    $(".HelperToolsSelection").hide();


    loggedIn = false;
    isStudent = false;
    loggedInUser = "";
    loggedInUserEmail = "";

}


$(".logoutButtonContainer a").click(function () {
    logOutUser();
});




startApp();

// LOGIN END -----------------------------------


var TestUrl = "http://"+window.location.href.split("/")[2].split(":")[0]+":5414/HQServer/";


// POPULATE ALL CLASSES - start
$.ajax({
    url: TestUrl + "GetAllClasses",

    success: function(result) {

        result = JSON.parse(result);

        for (var className in result){
            var classButton = '<div class="course"><p>'+result[className]+'</p></div>';
            $(".courseSelectionRow").append(classButton)
        };

        makeSureEverythingLooksRight();
    }
});

// POPULATE ALL CLASSES - end



// ON CLASS CLICK LOAD THE QUEUE - start
$('body').on('click', '.course', function() {

    var className = $(this).find("p").html();
    $(".courseTitle").html(className);

    loadQueueSystem(className);

});


function loadQueueSystem(className) {

    $.ajax({
        url: TestUrl + "AvailableHelpers",
        data: {"course": className},
        success: function(result) {


            result = JSON.parse(result);

            $(".queueSlotRow").empty();

            for (var helperName in result){
                var helperCol = createHelperQueueColumn(result[helperName]);
                $(".queueSlotRow").append(helperCol);
                loadQueueGivenHelperName(result[helperName], null)
            }

            makeSureEverythingLooksRight();
            addHelperNameToSearchBar();
        }
    });


    $( ".courseSelectionWindow" ).fadeOut(400, function(){
        $(".queueWindow").fadeIn(400);
        $(".backButton").fadeIn(500);
    });


}
// ON CLASS CLICK LOAD THE QUEUE - end




// LOAD QUEUE GIVEN HELPER NAME - start
function loadQueueGivenHelperName(helperName, callback) {

    $.ajax({
        url: TestUrl + "ListQueue",
        data: {"cp": helperName},

        success: function(result) {

            if (callback!= null){
                callback();
            }


            result = JSON.parse(result);
            result = result.reverse();
            for (var studentName in result){
                var studentCard = createQueueCard(result[studentName]);
                $(".helperName:contains("+helperName+")").parent().after(studentCard);

                if (helperName == loggedInUser){
                    $(".helperName:contains("+helperName+")").parent().after().append('<span class="close-icon" data-effect="fadeOut"><i class="fa fa-times"></i></span>');
                }

            }

            makeSureEverythingLooksRight();
        }

    });
}
// LOAD QUEUE GIVEN HELPER NAME - end




$(".addToQueue").click(function () {

    var helperName = $(".selectpicker").val();

    if (helperName == "the fastest") {
        var min = -1;
        var minObject = null;

        $(".helperQueue").each(function () {
            var currentLength = $(this).find(".inQueueCard").length;

            if (currentLength < min || min == -1){
                minObject = $(this);
                min = currentLength
            }

        });
        helperName = minObject.find(".helperCard").find(".helperName").text();
    }


    $.ajax({
        url: TestUrl + "AddStudent",
        data: {"cp": helperName, "student": loggedInUser},

        success: function() {

            updateQueue(helperName);
            $(".addToQueue").hide();

        }

    });

});


$( "body" ).on( "click", ".close-icon", function() {

    var nameToRemove = $(this).parent().find(".name").text();
    var helperName = $(this).parent().parent().find(".helperCard").find(".helperName").text();

    $.ajax({
        url: TestUrl + "RemoveStudent",
        data: {"name": nameToRemove},

        success: function() {

            updateQueue(helperName);
            $(".addToQueue").show();
        }

    });


});






function updateQueue(helperName){
    // console.log("updating " + helperName);
    var objectOfQueue = searchForPersonQueue(helperName);
    var removeCurrentQueue = function(){objectOfQueue.siblings().remove();}
    loadQueueGivenHelperName(helperName, removeCurrentQueue);
}



function searchForPersonQueue(name) {

    var obj = null;

    if (name == "the fastest") {
        var min = -1;
        var minObject = null;

        $(".helperQueue").each(function () {
            var currentLength = $(this).find(".inQueueCard").length;

            if (currentLength < min || min == -1){
                minObject = $(this);
                min = currentLength
            }

        });
        obj = minObject;
    }

    else {
        $(".helperQueue .helperName").each(function () {
            if ($(this).text().trim() == name) {
                obj = $(this).parent();
                return false;
            }
        });
    }

    return obj;
}


$( "#search" ).keyup(function() {

    var typed = $("#search").val().toLowerCase().trim();

    if (typed == ""){
        $(".course").show();
        return;
    }

    $('.course').each( function(){

        var content = $(this).find("p").html().toLowerCase().trim();

        if (content.indexOf(typed) < 0){
            $(this).hide();
        }
        else {
            $(this).show();
        }

    });


});




$(".helperToggle").click(function () {


    // If value of button is Activate
    if($(".helperToggle").text() == "Activate") {


        $.ajax({
            url: TestUrl + "ToggleTimer",
            data: {"name": loggedInUser, "pause": 0},
            success: function() {

                updateAllQueues();

                $(".helperToggle").html("Deactivate");
                $(".helperPauseToggle").show();
                $(".helperPauseToggle").html("Pause");




            }
        });

    } else {
        // If value of button is Deactivate


        $.ajax({
            url: TestUrl + "ToggleTimer",
            data: {"name": loggedInUser, "pause": 0},
            success: function() {

                updateAllQueues();

                $(".helperToggle").html("Activate");

                $(".helperPauseToggle").hide();
                $(".helperPauseToggle").html("Pause");


            }
        });


    }

});


$(".helperPauseToggle").click(function () {

    if($(".helperPauseToggle").text() == "Pause") {


        $.ajax({
            url: TestUrl + "ToggleTimer",
            data: {"name": loggedInUser, "pause": 1},
            success: function() {

                updateAllQueues();



                $(".helperToggle").hide();
                $(".helperPauseToggle").html("Resume");

            }
        });

    } else {

        $.ajax({
            url: TestUrl + "ToggleTimer",
            data: {"name": loggedInUser, "pause": 1},
            success: function() {

                updateAllQueues();
                $(".helperToggle").show();
                $(".helperPauseToggle").html("Pause");

            }
        });

    }

});




var helpersFromDB = [];



function updateAllQueues() {

    console.log(" --- update --- ");


    if ($(".queueWindow").css('display') !== 'none') {

        $.ajax({
            url: TestUrl + "AvailableHelpers",
            data: {"course": $(".courseTitle").text()},
            success: function (result) {


                result = JSON.parse(result);


                helpersFromDB = [];


                for (var h in result) {

                    var helperName = result[h];


                    helpersFromDB.push(helperName);


                    // console.log(" looking to see if this person is here-> " + helperName);

                    if ($(".helperName:contains(" + helperName + ")").text() != "") {
                        // console.log(helperName + " is in the system");
                        updateQueue(helperName)
                    }
                    else {
                        // console.log(helperName + " is NOT IN the system");

                        var helperCol = createHelperQueueColumn(helperName);
                        $(".queueSlotRow").append(helperCol);
                        loadQueueGivenHelperName(helperName, null)

                    }

                }


                console.log(helpersFromDB);

                $(".helperName").each(function () {
                    var name = ($(this).text());

                    if (helpersFromDB.indexOf(name) < 0) {
                        // console.log(($(this).text()) + " not in helpers");

                        $(this).parent().parent().remove();
                        $("select option:contains("+name+")").remove();

                    }



                });


                makeSureEverythingLooksRight();
                addHelperNameToSearchBar();


                $(".helperTime").each(function () {
                    var helperName = $(this).parent().find(".helperName").text();
                    getHelperTime(helperName)
                });



            }
        });


    }


}


setInterval(function(){
    updateAllQueues();
} , 5000);
