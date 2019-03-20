package main.java;

import org.eclipse.jetty.http2.HTTP2Cipher;
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class ServerConscrypt {
	
	
	private static void startServer(Server server) throws Exception
    {
       
      
        HttpConfiguration httpsConfig = new HttpConfiguration();
        httpsConfig.addCustomizer(new SecureRequestCustomizer());
        ConnectionFactory h2 = new HTTP2ServerConnectionFactory(httpsConfig);
        ConnectionFactory ssl = new SslConnectionFactory(newSslContextFactory(), h2.getProtocol());
        ServerConnector connector = new ServerConnector(server, 1, 1, ssl, h2);
        connector.setHost("localhost");
        connector.setPort(8443);
        server.addConnector(connector);
        server.start();
    }
	
	 private static SslContextFactory newSslContextFactory()
	    {
	        SslContextFactory sslContextFactory = new SslContextFactory();
	        sslContextFactory.setKeyStorePath("C:/Users/sdasgupt/workspaceV41/jetty-server-example-http2/src/main/resources/keystore");
	        sslContextFactory.setKeyStorePassword("OBF:1vny1zlo1x8e1vnw1vn61x8g1zlu1vn4");
	        sslContextFactory.setKeyManagerPassword("OBF:1u2u1wml1z7s1z7a1wnl1u2g");
	        sslContextFactory.setUseCipherSuitesOrder(true);
	        sslContextFactory.setCipherComparator(HTTP2Cipher.COMPARATOR);
	        return sslContextFactory;
	    }
	 
	 public static void main(String[] args)throws Exception{
		 QueuedThreadPool serverThreads = new QueuedThreadPool();
	     serverThreads.setName("server");
		 Server server = new Server(serverThreads);
		 ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
	     context.addServlet(new ServletHolder(new Servlet()), "/");
	     server.setHandler(context);
	     startServer(server);
	 }


}
