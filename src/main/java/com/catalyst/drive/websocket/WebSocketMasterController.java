package com.catalyst.drive.websocket;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import com.catalyst.drive.util.Utils;

/**
 * Servlet implementation class WebSocketMasterController
 */
public class WebSocketMasterController extends WebSocketServlet {
	private static final long serialVersionUID = 1L;
	private static Map<String, Class<? extends WebSocket>> _controllerMap = createControllerMap(WebSocketMasterController.class
			.getPackage());
	public static final String VALUE_ATTRIBUTE = "value";
	private static Logger _LOGGER = Logger
			.getLogger(WebSocketMasterController.class);

	/**
	 * @see WebSocketServlet#WebSocketServlet()
	 */
	public WebSocketMasterController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}
	
	/**
	 * Maps request to the appropriate websocket
	 */
	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest request, String arg1) {
		Pattern p;
		for (Map.Entry<String, Class<? extends WebSocket>> e : _controllerMap.entrySet()) {
			p = Pattern.compile(".*" + e.getKey() + ".*");
			if (p.matcher(request.getRequestURL()).find()) {
				try {
					Class<?> clazz = Class.forName(e.getValue().getName());
					Constructor<?> ctor = clazz.getConstructor();
					Object object = ctor.newInstance(new Object[] {});	
					return (WebSocket) object;
				} catch (Exception e1) {
					_LOGGER.warn("No empty constructor for: " + e.getValue().getName() + ", with ex: " + Utils.getStackTrace(e1));
				} 				
			}
		}
		return null;
	}

	/**
	 * Creates an in memory map of controllers' @WebSocket annotation values and the controllers themselves
	 * @param p - package where the controller resides
	 * @return the map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map<String, Class<? extends WebSocket>> createControllerMap(
			Package p) {
		Map<String, Class<? extends WebSocket>> controllerMap = new HashMap<String, Class<? extends WebSocket>>();
		try {
			/* 
			 * Scan this package.  Add other packageNames to getClasses to scan here.
			 */
			List<Class> classesInPackage = Utils.getClasses(p.getName());
			for (Class c : classesInPackage) {
				if (WebSocket.class.isAssignableFrom(c) && c.isAnnotationPresent(com.catalyst.drive.websocket.WebSocket.class)) {
					controllerMap.put(Utils.getClassAnnotationValue(c, com.catalyst.drive.websocket.WebSocket.class, VALUE_ATTRIBUTE),c);
				}
			}
		} catch (ClassNotFoundException e) {
			_LOGGER.error("WebSocketMasterController could not find classes.  With ex: "
					+ Utils.getStackTrace(e));
		} catch (IOException e) {
			_LOGGER.error("WebSocketMasterController could not find classes.  With ex: "
					+ Utils.getStackTrace(e));
		}
		return controllerMap;
	}
}
