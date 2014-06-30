package org.eclipse.jetty.embedded;
 
import java.util.Collections;
 
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.security.Constraint;
 
public class SecuredHelloHandler
{
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
 
        LoginService loginService = new HashLoginService("MyRealm","src/test/resources/realm.properties");
        server.addBean(loginService); 
 
        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        server.setHandler(security);
 
        Constraint constraint = new Constraint();
        constraint.setName("auth");
        constraint.setAuthenticate( true );
        constraint.setRoles(new String[]{"user", "admin"});
 
        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setPathSpec( "/*" );
        mapping.setConstraint( constraint );
 
        security.setConstraintMappings(Collections.singletonList(mapping));
        security.setAuthenticator(new BasicAuthenticator());
        security.setLoginService(loginService);
 
        HelloHandler hh = new HelloHandler();
 
        security.setHandler(hh);
 
        server.start();
        server.join();
    }
}