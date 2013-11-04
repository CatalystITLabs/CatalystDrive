package com.catalyst.drive.servers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSocketConnector;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.http.ssl.SslContextFactory;

/**
 * EmbeddedJettyServer with SSL and/or non-SSL used during deployment only. By
 * default, this will deploy with 2 connectors--one serving through ssl and one
 * through non-ssl. You can specify to have only one or the other running with
 * arguments to the program (see below).
 * 
 * To deploy as a "quick-run", first do a maven-install to a .war in target
 * directory. Then use the command:
 * 
 * java -jar <WAR_NAME>.war
 * 
 * This "quick-run" method only works without BIRT. If you want to deploy a
 * project with BIRT, you need to extract the .war before running this server.
 * That can be done with the following 2 commands, which are done in the batch
 * files included with this project:
 * 
 * jar -xvf <WAR_NAME>.war java com.catalyst.drive.servers.EmbeddedJettyServer
 * 
 * Optional Deployment Parameters [Default Value]: -host:<hostName> [Network
 * name of computer] -path:<contextPath> ["/"] -nonssl:<boolean> ["true"]
 * -ssl:<boolean> ["true"] -port:<portNumber> ["8080"] -sslport:<sslPortNumber>
 * ["8443"]
 * 
 * **Parameters should include no front slashes or other special characters**
 * 
 * This class is for deployment purposes only. You should be able to debug
 * during development with the maven-jetty-plugin using: run as -> maven build
 * -> jetty:run.
 * 
 * This class, along with resource, keystore.jks, is copied to the root
 * directory of the war during a maven-install. If you have a different keystore
 * you would like to use, please place it in the keystore directory and change
 * KEYSTORE_NAME in this class to the name of that .jks file.
 * 
 * @author kpolen
 * 
 */
public class EmbeddedJettyServer {

	// Set to true if wishing to debug with embedded jetty server
	private static boolean devMode = true;

	// Start up etherpad server
	private static boolean runEtherpadServerInstance = true;

	// Properties of the keystore.jks which is utilized/moved as a resource
	// during maven-install and
	// should be located in the /keystore directory before doing maven-install
	private final static String KEYSTORE_NAME = "keystore.jks";

	// KEYSTORE PASSWORD: The keystore password is set during keystore
	// generation
	private final static String KEYSTORE_PASS = "secret";

	/*
	 * THE FOLLOWING FIELDS CAN BE SET AS PARAMETERS DURING DEPLOYMENT
	 */
	// You can modify this field to root path (usually the name) of whatever app
	// is being deployed, beginning with "/",
	// e.g. For an application named MyWebApp, set this field to "/MyWebApp"
	private static String useSsl = "true";
	private static String useNonSsl = "true";
	private static String contextPath = "/";
	private static String sslPortNumber = "8443";
	private static String portNumber = "8080";
	// Default host name. THIS WILL BE SET AUTOMATICALLY IN MAIN METHOD
	private static String hostName = null;
	private static final int DEFAULT_SSL_PORT_NUMBER = 8443;
	private static final int DEFAULT_PORT_NUMBER = 8080;
	/*
	 * ARGUMENTS WHOSE VALUES ARE SET AS PARAMETERS FOR SERVER
	 */
	private static final String ARG_NON_SSL = "-nonssl:";
	private static final String ARG_SSL = "-ssl:";
	private static final String ARG_PORT = "-port:";
	private static final String ARG_SSL_PORT = "-sslport:";
	private static final String ARG_PATH = "-path:";
	private static final String ARG_HOST = "-host:";
	private static final String ARG_DEV_MODE = "-devmode:";
	private static final String ARG_ETHERPAD_SERVER = "-etherpad:";

	private static Logger logger = LoggerFactory.getLogger(EmbeddedJettyServer.class);

	private static final String DEV_MODE_WAR_PATH = "src/main/webapp";

	private static void INFO_STREAM(final InputStream src) {
	    new Thread(new Runnable() {
	        public void run() {
	            Scanner sc = new Scanner(src);
	            while (sc.hasNextLine()) {
	                logger.info(sc.nextLine());
	            }
	        }
	    }).start();
	}
	
	private static void WARN_STREAM(final InputStream src) {
	    new Thread(new Runnable() {
	        public void run() {
	            Scanner sc = new Scanner(src);
	            while (sc.hasNextLine()) {
	                logger.warn(sc.nextLine());
	            }
	        }
	    }).start();
	}
	
	/**
	 * Runs the embedded server with SSL using a keystore. Description is in
	 * class javadoc.
	 * 
	 * @param args
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException { // NOSONAR
		// Sets any parameters that may have been passed by user
		setServerParameters(args);
		logger.info("Server is starting.");
		Server server = new Server();
		List<Connector> connectors = new ArrayList<Connector>();

		boolean useSSL = true;
		try {
			useSSL = Boolean.valueOf(useSsl);
		} catch (Exception e3) {
			logger.info("Could not parse -ssl parameter.  Using true by default");
		}
		if (useSSL && !devMode) {
			SslContextFactory sslContextFactory = new SslContextFactory();
			// Sets the keystore to the one located in the keystore directory.
			// TO SET TO A KEYSTORE WITH AN ABSOLUTE PATH: If you have your own
			// keystore at an external location, remove the following try/catch
			// and instantiate SslContextFactory above with the path to that
			// keystore.
			// example: new SslContextFactory("c:/keystores/keystore.jks");
			try {
				sslContextFactory.setKeyStoreResource(Resource.newResource(EmbeddedJettyServer.class.getResource("/"
						+ KEYSTORE_NAME)));
			} catch (IOException e2) {
				logger.error("Local keystore resource: " + KEYSTORE_NAME + " could not be set");
			}
			sslContextFactory.setKeyStorePassword(KEYSTORE_PASS);
			SslSocketConnector connector = new SslSocketConnector(sslContextFactory);
			try {
				connector.setPort(Integer.parseInt(sslPortNumber));
			} catch (Exception e) {
				connector.setPort(DEFAULT_SSL_PORT_NUMBER);
			}
			try {
				if (hostName != null) {
					connector.setHost(hostName);
				} else {
					hostName = java.net.InetAddress.getLocalHost().getHostName();
					connector.setHost(hostName);
				}
			} catch (UnknownHostException e1) {
				logger.error("Unknown host exception.  Please set valid hostname in '-host:' argument or embedded server class.");
				throw new UnknownHostException();
			}
			connectors.add(connector);
		}

		boolean useNonSSL = true;
		try {
			useNonSSL = Boolean.valueOf(useNonSsl);
		} catch (Exception e3) {
			logger.info("Could not parse -nonssl parameter.  Using true by default");
		}
		if (useNonSSL || devMode) {
			SelectChannelConnector connector = new SelectChannelConnector();
			if (hostName != null) {
				connector.setHost(hostName);
			} else {
				hostName = java.net.InetAddress.getLocalHost().getHostName();
				connector.setHost(hostName);
			}
			try {
				connector.setPort(Integer.parseInt(portNumber));
			} catch (NumberFormatException e) {
				connector.setPort(DEFAULT_PORT_NUMBER);
			}
			connectors.add(connector);
		}
		// register the connectors
		server.setConnectors(connectors.toArray(new Connector[connectors.size()]));
		WebAppContext wac = new WebAppContext();
		wac.setContextPath(contextPath);

		if (devMode) {
			wac.setWar(DEV_MODE_WAR_PATH);
		} else {
			ProtectionDomain protectionDomain = EmbeddedJettyServer.class.getProtectionDomain();
			URL location = protectionDomain.getCodeSource().getLocation();
			wac.setWar(location.toExternalForm());
		}
		server.setHandler(wac);
		try {
			// Start Jetty Server
			server.start();
			// Start node.js Etherpad server
			if (runEtherpadServerInstance) {
				Runnable etherpad = new Runnable() {
					public void run() {
						try {
							Process process;
							String path = System.getProperty("user.dir")+"/etherpad/";
							if (System.getProperty("os.name").contains("win")) {
								process = Runtime.getRuntime().exec(
									"cmd.exe /c start " + path
											+ "node node_modules/ep_etherpad-lite/node/server.js", null,
									new File(path));
							} else {
								process = Runtime.getRuntime().exec( path+"bin/run.sh", null, new File(path));
							}
							INFO_STREAM(process.getInputStream());
						    WARN_STREAM(process.getErrorStream());
						} catch (IOException e) {
							logger.warn("Cannot start up Etherpad:\n"+e.getMessage());
						}
					}
				};
				new Thread(etherpad).start();
				logger.info("Started etherpad server.");
			}

			logger.info("Host name set to: " + hostName);
			if (useNonSSL || devMode) {
				logger.info("Port number set to: " + portNumber);
			}
			if (useSSL && !devMode) {
				logger.info("SSL port number set to: " + sslPortNumber);
			}
			logger.info("Root context set to: " + contextPath);
			logger.info("Server has started!");
			logger.info("Press enter key to stop...");
			System.in.read();
			if (runEtherpadServerInstance) {
				try {
					// Destroy the etherpad -- THIS DOES NOT WORK IF YOU USE
					// THE ECLIPSE STOP BUTTON.  YOU MUST CLICK IN THE CONSOLE
					// WINDOW OF ECLIPSE AND HIT ENTER, SO THAT IT SHUTS DOWN
					// GRACEFULLY!
					Runtime.getRuntime().exec("taskkill /F /IM node.exe");
					logger.info("Shutdown etherpad.");
				} catch (Exception e) {
					logger.warn("Error shutting down etherpad:\n", e.getMessage());
				}
				
			}
			server.stop();
			server.join();
		} catch (Exception e) {
			logger.warn("Server Error:\n", e.getMessage());
		} finally {
			System.exit(0);
		}
	}

	/**
	 * Sets the fields based on the args passed in
	 * 
	 * @param args
	 */
	protected static void setServerParameters(String[] args) {
		for (String arg : args) {
			if (arg.startsWith(ARG_PATH)) {
				contextPath = "/" + arg.substring(ARG_PATH.length());
			}
			if (arg.startsWith(ARG_SSL)) {
				useSsl = arg.substring(ARG_SSL.length());
			}
			if (arg.startsWith(ARG_NON_SSL)) {
				useNonSsl = arg.substring(ARG_NON_SSL.length());
			}
			if (arg.startsWith(ARG_SSL_PORT)) {
				sslPortNumber = arg.substring(ARG_SSL_PORT.length());
			}
			if (arg.startsWith(ARG_PORT)) {
				portNumber = arg.substring(ARG_PORT.length());
			}
			if (arg.startsWith(ARG_HOST)) {
				hostName = arg.substring(ARG_HOST.length());
			}
			if (arg.startsWith(ARG_DEV_MODE)) {
				devMode = Boolean.valueOf(arg.substring(ARG_DEV_MODE.length()));
			}
			if (arg.startsWith(ARG_ETHERPAD_SERVER)) {
				runEtherpadServerInstance = Boolean.valueOf(arg.substring(ARG_ETHERPAD_SERVER.length()));
			}
		}
	}
}
