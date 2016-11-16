import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

public class StartServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.setThreadPool(new QueuedThreadPool(20));
        Connector connector = new SelectChannelConnector();

        connector.setMaxIdleTime(1000 * 60 * 60);
        connector.setPort(Integer.getInteger("config.port", Integer.valueOf(System.getProperty("server.port"))));
        server.setConnectors(new Connector[]{connector});

        ResourceCollection resources = new ResourceCollection(new String[] {
                "server/src/main/webapp",
        });


        WebAppContext bb = new WebAppContext();
        bb.setServer(server);
        bb.setContextPath("/");
        bb.setBaseResource(resources);

        bb.setWar("/server/src/main/webapp");
        server.setHandler(bb);

        try {
            System.out.println(">>>MediaServ STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
            server.start();
            System.in.read();
            System.out.println(">>>MediaServ STOPPING EMBEDDED JETTY SERVER");
            server.stop();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }
    }
}