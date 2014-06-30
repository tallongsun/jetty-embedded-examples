package org.eclipse.jetty.embedded;
 
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.toolchain.test.MavenTestingUtils;
import org.eclipse.jetty.util.resource.Resource;
 
public class SplitFileServer
{
         
    public static void main(String[] args) throws Exception
    {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8090);
        server.setConnectors(new Connector[]
        { connector });
 
        ContextHandler context0 = new ContextHandler();
        context0.setContextPath("/");
        ResourceHandler rh0 = new ResourceHandler();
        rh0.setBaseResource( Resource.newResource(MavenTestingUtils.getTestResourceDir("dir0")));
        context0.setHandler(rh0);
 
        ContextHandler context1 = new ContextHandler();
        context1.setContextPath("/");   
        ResourceHandler rh1 = new ResourceHandler();
        rh1.setBaseResource( Resource.newResource(MavenTestingUtils.getTestResourceDir("dir1")));
        context1.setHandler(rh1);
 
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]
        { context0, context1 });
 
        server.setHandler(contexts);
 
        server.start();
        System.err.println(server.dump());
        server.join();
    }
}