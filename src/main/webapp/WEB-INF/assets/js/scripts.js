
jQuery(document).ready(function () {
	
    /*
        Fullscreen background
    */
    $.backstretch(["assets/img/backgrounds/1.jpg",
                   "assets/img/backgrounds/2.jpg",
                   "assets/img/backgrounds/3.jpg"
	             ], {duration: 3000, fade: 750});
    
    /*
        Form validation
    */
    $('.login-form input[type="text"], .login-form input[type="password"], .login-form textarea').on('focus', function () {
    	$(this).removeClass('input-error');
    });
    
    $('.login-form').on('submit', function (e) {
    	
    	$(this).find('input[type="text"], input[type="password"], textarea').each(function () {
    		if ($(this).val() == "") {
    			e.preventDefault();
    			$(this).addClass('input-error');
    		} else {
    			$(this).removeClass('input-error');
    		}
    	});
    	
    });

    $('#go').click(function () {
        $('.form').html("");
        $('.form-bottom').css("background-color", "#fff");

        $.ajax({
            url : 'collage',
            data : {
                username : $('#username').val(),
                size : "300x300"
            },
            method : 'POST',
            success : function(responseText) {
                console.log("working even more!");
                //btoa(unescape(encodeURIComponent(responseText)))
                var b64Response = responseText;

                // create an image
                var outputImg = document.createElement('img');
                outputImg.src = 'data:image/jpg;base64,'+b64Response;

                $('.form-bottom').append(outputImg);
            }
        });
        console.log("working!");

        //$(".se-pre-con").fadeOut("slow");
    });


    $(window).load(function() {
    		// Animate loader off screen
    		$(".se-pre-con").fadeOut("slow");;
    	});

});

        function change_state(obj){
            if (obj.checked){
                //if checkbox is being checked, add a "checked" class
                obj.parentNode.classList.add("checked");
            }
            else{
                //else remove it
                obj.parentNode.classList.remove("checked");
            }
        }