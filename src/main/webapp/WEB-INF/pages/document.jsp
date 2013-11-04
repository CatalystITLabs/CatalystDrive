<script type="text/javascript" src="./webcontent/resources/js/etherpad.js"></script>
<script type="text/javascript">
	$(function() {
		$('#padDiv').pad({
			'host' : 'http://' + $('#serverName').text() + ':8081',
			'padId' : $('documentName').attr('id'),
			'showControls' : true,
			'height' : '500',
			'width' : '300'
		});
	});
</script>
<h4 class="documentName" id="1">Default Document</h4>
<div id="padDiv"></div>
<!-- <div id="document"> -->
<!-- 	<h4 class="documentName" id="default">Default Document</h4> -->
<!-- 	<div id="documentTextHolder"> -->
<!-- 		<textarea id="documentText" rows="30" cols="70"></textarea> -->
<!-- 	</div> -->
<!-- </div> -->