/**
 * Created by anbabans on 2/4/2018.
 */
import java.io.File;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class Main {
    public static void main(String[] args) throws Exception {
        configureAPaaS();
       //configureStandalone();

    }
    private static void configureStandalone() throws Exception{

        String webappDirLocation = "src/main/webapp/";
        String port = "8080";
        Tomcat tomcat = new Tomcat();

        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

        // Declare an alternative location for your "WEB-INF/classes" dir
        // Servlet 3.0 annotation will work
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.setPort(Integer.valueOf(port));
        tomcat.start();
        tomcat.getServer().await();
    }

    private static void configureAPaaS() throws Exception {
        String appHome = System.getenv("APP_HOME");
        if (null == appHome || appHome.isEmpty()) {
            appHome = ".";
        }

        String webappDir = appHome + "/" +  "target/webapp";

        String port = System.getenv("PORT");
        if (null == port || port.isEmpty()) {
            port = "8080";
        }
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.valueOf(port));
        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDir).getAbsolutePath());

        String classesDir = appHome + "/" +  "target/classes";
        File additionWebInfClasses = new File(classesDir);
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);
        tomcat.start();
        tomcat.getServer().await();
    }
}
