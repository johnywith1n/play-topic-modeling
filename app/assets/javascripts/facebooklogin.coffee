class $.FacebookLogin
	constructor: ->
	
	@login: (onLoggedIn)->
		FB.login (response) ->
			if response.authResponse
				onLoggedIn()
			else
				alert "login failed. please try again"
	
	@testAPI: ->
		console.log "Welcome!  Fetching your information.... "
		FB.api "/me", (response) ->
			console.log "Good to see you, " + response.name + "."
		
	@getLoginStatus: (onLoggedIn)->
		FB.getLoginStatus (response) ->
			if response.status is "connected"
				#connected
				onLoggedIn()
			else if response.status is "not_authorized"
				FacebookLogin.login(onLoggedIn)
			else
				FacebookLogin.login(onLoggedIn)
	
	@init: (myAppId) ->
		window.fbAsyncInit = ->
			FB.init
				appId: myAppId # App ID
				channelUrl: "//localhost:9000/channel" # Channel File
				status: true # check login status
				cookie: true # enable cookies to allow the server to access the session
				xfbml: true # parse XFBML
			
			FacebookLogin.getLoginStatus(FacebookLogin.testAPI)

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