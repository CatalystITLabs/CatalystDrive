package com.catalyst.drive.websocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.WebSocket.OnTextMessage;

/**
 * WebSocketDocumentController implementation of OnTextMessage which is an
 * extended interface of WebSocket. All websocket requests containing "/document" in
 * the request URL are routed here.
 * 
 * @author kpolen
 * 
 */
@WebSocket(value = "/document")
public class WebSocketDocumentController implements OnTextMessage {
	private Connection connection;
	// Map of rooms to the web socket connections in them
	private static Map<String, List<WebSocketDocumentController>> documentToWebSocketsMap = new HashMap<String, List<WebSocketDocumentController>>();
	private static final Logger LOGGER = Logger.getLogger(WebSocketDocumentController.class);

	/**
	 * Event that is fired upon receiving a message
	 */
	public void onMessage(String data) {
		// The name of the document being edited
		String document;
		// The web sockets for the users editing the document
		List<WebSocketDocumentController> webSockets;
		if (!data.startsWith("close") && !data.startsWith("open")) {
			document = data.substring(0, data.indexOf(":"));
			String text = data.substring(data.indexOf(":") + 1);
			webSockets = documentToWebSocketsMap.get(document);
			for (WebSocketDocumentController user : webSockets) {
				if (user != this) {
					try {
						user.connection.sendMessage(text);
					} catch (Exception e) {
						LOGGER.info("Message not sent.  Connection may be closed.");
					}
				}
			}
		} else if (data.startsWith("open")) {
			document = data.substring(data.indexOf(":") + 1);
			webSockets = getWebSocketsForDocument(document);
			// Add this web socket if it's not already there
			if (!webSockets.contains(this)) {
				webSockets.add(this);
			}
		} else if (data.startsWith("close")) {
			document = data.substring(data.indexOf(":") + 1);
			webSockets = documentToWebSocketsMap.get(document);
			if (webSockets != null && webSockets.contains(this)) {
				webSockets.remove(this);
				if (webSockets.isEmpty()) {
					documentToWebSocketsMap.remove(document);
				}
			}
		} else {
			LOGGER.info("Unrecognized data sent to " + this.getClass().getName() + ": " + data);
		}
	}

	/**
	 * Gets all the web sockets for this room
	 * 
	 * @param document
	 * @return
	 */
	private List<WebSocketDocumentController> getWebSocketsForDocument(String document) {
		List<WebSocketDocumentController> webSockets;
		if ((webSockets = documentToWebSocketsMap.get(document)) == null) {
			webSockets = new ArrayList<WebSocketDocumentController>();
			documentToWebSocketsMap.put(document, webSockets);
		}
		return webSockets;
	}

	/**
	 * Event that is fired on open
	 */
	@Override
	public void onOpen(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Event that is fired on close
	 */
	@Override
	public void onClose(int closeCode, String message) {
	}
}