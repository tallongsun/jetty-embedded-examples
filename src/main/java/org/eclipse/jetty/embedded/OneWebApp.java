package org.eclipse.jetty.embedded;
 
import java.lang.management.ManagementFactory;
 
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
 
public class OneWebApp
{
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
         
        MBeanContainer mbContainer=new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
        server.addBean(mbContainer);
 
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setWar("E:/tool/jetty-distribution-9.2.1.v20140609/demo-base/webapps/test.war");
 
        server.setHandler(webapp);
 
        HashLoginService loginService = new HashLoginService();
        loginService.setName("Test Realm");
        loginService.setConfig("src/test/resources/realm.properties");
        server.addBean(loginService);
 
        server.start();
        server.join();
    }
}