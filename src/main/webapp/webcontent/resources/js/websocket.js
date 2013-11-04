/**
 * Document collaboration via websockets functionality
 */
var ws;
$(function() {
	/* Set up websocket */
	ws = new WebSocket("ws://" + $('#serverName').text().replace("/", "") + ":"
			+ $('#portNumber').text() + $('#contextPath').text()
			+ "/document.ws");
	ws.onopen = function(event) {
		ws.send("open:" + $('.documentName').attr('id'));
	};
	ws.onmessage = function(event) {
		var $textarea = $('#documentText');
		$textarea.val(event.data);
	};
	ws.onclose = function(event) {
		ws.send("close:" + $('.documentName').attr('id'));
	};
	$('#documentText').on(
			'keyup',
			function() {
				if (windowsHasFocus) {
					var message = $('.documentName').attr('id') + ":"
							+ $('#documentText').val();
					ws.send(message);
				}
			});
});

var windowsHasFocus;

$(function() {
	$(window).on("blur focus", function(e) {
		var prevType = $(this).data("prevType");

		if (prevType != e.type) { // reduce double fire issues
			switch (e.type) {
			case "blur":
				windowsHasFocus = true;
				break;
			case "focus":
				windowsHasFocus = true;
				break;
			default:
				windowsHasFocus = false;
			}
		}

		$(this).data("prevType", e.type);
	});
});