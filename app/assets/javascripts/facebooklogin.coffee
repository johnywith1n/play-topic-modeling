class $.FacebookLogin
	constructor: ->
	
	@login: (onSuccess, onFailure)->
		FB.login (response) ->
			if response.authResponse
				onSuccess()
			else
				alert "login failed. please try again"
				onFailure()
	
	@testAPI: ->
		console.log "Welcome!  Fetching your information.... "
		FB.api "/me", (response) ->
			console.log "Good to see you, " + response.name + "."
		
	@getLoginStatus: (onSuccess, onFailure)->
		FacebookLogin.withLoginStatus(onSuccess, () -> FacebookLogin.login(onSuccess, onFailure))
		
	@getLoginNavBarText: () ->
		FacebookLogin.withLoginStatus () ->
			FB.api "/me", (response) ->
				$("#nav-bar-login").text "Hello " + response.name
		, () ->
			$("#nav-bar-login").text "Login"
	
	@withLoginStatus: (onSuccess, onFailure) ->
		FB.getLoginStatus (response) ->
			if response.status is "connected"
				onSuccess()
			else if response.status is "not_authorized"
				onFailure()
			else
				onFailure()
	
	@doLogin: () ->
		FacebookLogin.getLoginStatus () -> 
				FB.api "/me", (response) ->
					$("#nav-bar-login")
						.text("Hello " + response.name)
						.click ->
			, () ->
				$("#nav-bar-login")
					.text("Login")
					.click () -> FacebookLogin.doLogin()
				
	@init: (myAppId) ->
		window.fbAsyncInit = ->
			FB.init
				appId: myAppId # App ID
				channelUrl: "//localhost:9000/channel" # Channel File
				status: true # check login status
				cookie: true # enable cookies to allow the server to access the session
				xfbml: true # parse XFBML
			
			FacebookLogin.doLogin()

	@load_async: (d, appId) ->
		FacebookLogin.init(appId)
		js = undefined
		id = "facebook.jssdk"
		ref = d.getElementsByTagName("script")[0]
		return if d.getElementById(id)
		js = d.createElement("script")
		js.id = id
		js.async = true
		js.src = "//connect.facebook.net/en_US/all.js"
		ref.parentNode.insertBefore js, ref