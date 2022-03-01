
/* Login with google */
function init() {
    gapi.load('auth2', function() {
        auth2 = gapi.auth2.init({
	/*================== google client id ============================*/
            client_id: '0000',
            cookiepolicy: 'single_host_origin',
            scope: 'profile email'
        });
        element = document.getElementById('glogin');
        auth2.attachClickHandler(element, {}, onSignUp, onFailure);
    });
}
function onSignUp(googleUser) {
	var profile = googleUser.getBasicProfile();
	let fullName = profile.getName().split(" ");
	let email = profile.getEmail();

	let firstName = fullName[0];
	let lastName = "";
	for(let i = 1; i < fullName.length; i++) {
		lastName += fullName[i] + " ";
	}
	
	let url = getUrlFromServer(email, firstName, lastName);
	if(url == "") {
		console.log("ajax false");
	}
	if(url == 'null') {
		$("div#messageArea").html("Xin chào <em class='bg-warning text-danger'>" + email + "</em><br> tài khoản của bạn tạm thời bị khóa.");
		inputEmail.text("");
		inputPassword.text("");
	} else {
		location.replace(url);
	}
}

function onFailure(data) {
	console.log("login fail!");
}

function getUrlFromServer(email, firstName, lastName) {
	let server = "https://love-and-care.herokuapp.com/action/glogin";
	let url = "";
	$.ajax({
		url: server,
		data: {'email': email, 'firstName': firstName, 'lastName': lastName},
		type: 'post',
		cache: false,
		async: false,
		success: function(result) {
			url = result;
		},
		error: function(error) {
			console.log(error);
		}
	})
	return url;
}


/* Login with facebook */
window.fbAsyncInit = function() {
    FB.init({
	/*================ facebook app id ======================*/
      appId      : 0000,
      cookie     : true,
      xfbml      : true,
      version    : 'v12.0'
    });
      
    FB.AppEvents.logPageView();   
      
};

(function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "https://connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));


function fblogin() {
	FB.login(function(response) {

        if (response.authResponse) {
            FB.api('/me', {fields: 'name, email'}, function(response) {
				let email = response.email;
				let fullName = response.name.split(" ");
				let firstName = fullName[0];
				let lastName = "";
				for(let i = 1; i < fullName.length; i++) {
					lastName += fullName[i] + " ";
				}
				
				if(email == undefined || email == "") {
					$("div#messageArea").html("Xin chào <em class='fw-bold text-primary'>" + firstName + " " + lastName + "</em><br> Chúng tôi không thể truy cập email từ tài khoản của bạn!.");
				} else {
					
					let url = getUrlFromServer(email, firstName, lastName);
					if(url == "") {
						console.log("ajax false");
					}
					if(url == 'null') {
						$("div#messageArea").html("Xin chào <em class='bg-warning text-danger'>" + email + "</em><br> tài khoản của bạn tạm thời bị khóa.");
					} else {
						location.replace(url);
					}
				}
            });
        }
    }, {
        scope: 'public_profile,email'
    });
}