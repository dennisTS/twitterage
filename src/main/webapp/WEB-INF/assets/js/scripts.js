
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

                // append it to your page
                document.body.appendChild(outputImg);
            }
        });
        console.log("working!");
    });

});