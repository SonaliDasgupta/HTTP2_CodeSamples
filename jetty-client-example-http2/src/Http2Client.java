import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpScheme;
import org.eclipse.jetty.http2.HTTP2Cipher;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.http2.client.HTTP2Client;
import org.eclipse.jetty.http2.client.http.HttpClientTransportOverHTTP2;

public class Http2Client {
	
	
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
	
	 private static HttpClient startClient() throws Exception
	    {
	        QueuedThreadPool clientThreads = new QueuedThreadPool();
	        clientThreads.setName("client");
	        HttpClientTransportOverHTTP2 transport = new HttpClientTransportOverHTTP2(new HTTP2Client());
	        transport.setUseALPN(false);
	       
	        HttpClient client = new HttpClient(transport, newSslContextFactory());
	        client.setExecutor(clientThreads);
	        
	        client.start();
	        return client;
	    }
	 
	 public static void main(String[] args)throws Exception{
		 HttpClient client = startClient();
		 
		ContentResponse response = client.newRequest("https://localhost:8443/")
	                .scheme(HttpScheme.HTTPS.asString())
	                .timeout(5, TimeUnit.SECONDS)
	                .send();

	        System.out.println("STATUS: "+response.getStatus()+" REASON: "+response.getReason());
	 }

}
