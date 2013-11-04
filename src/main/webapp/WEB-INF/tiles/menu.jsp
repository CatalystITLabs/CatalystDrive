<script type="text/javascript" src="./webcontent/resources/js/jquery-ui-menu.js"></script>
<script type="text/javascript" src="./webcontent/resources/js/jquery-ui-menubar.js"></script>
<link rel="stylesheet" href= "./webcontent/resources/css/jquery-ui-menu.css" type="text/css">
<link rel="stylesheet" href= "./webcontent/resources/css/jquery-ui-menubar.css" type="text/css">
<script type="text/javascript">

$(function() {
	$("#menu").menubar({
		// Enter options here
	});
});

</script>
<ul id="menu" class="menubar">
	<li><a href="splash.html">Splash</a></li>
	<li><a href="users.html">Demo</a></li>
	<li><a href="#Deployment">Deployment</a>
		<ul>
			<li><a href="standalone.html">Standalone</a></li>
			<li><a href="external.html">External</a></li>
		</ul>
	</li>
</ul>