package distributedCache;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.Arrays;

public class StorageServer {

    public static final String SERVER_ADDRESS = "tcp://localhost:8088";
    public static final int TIMEOUT = 1000;
    private static final ArrayList<Cache> caches = new ArrayList();
    public static final int GLOBAL_TIMEOUT = 3000;

    public static void main(String[] argv) {
        int start = Integer.parseInt(argv[0]);
        ArrayList<String> cache = new ArrayList<>(Arrays.asList(argv).subList(1, argv.length));
        int end = start + cache.size();

        ZContext context = new ZContext(1);
        ZMQ.Socket dealerSocket = context.createSocket(SocketType.DEALER);
        dealerSocket.connect(SERVER_ADDRESS);
        ZMQ.Poller poller = context.createPoller(1);
        poller.register(dealerSocket, ZMQ.Poller.POLLIN);
        long time = System.currentTimeMillis();
        while(poller.poll(GLOBAL_TIMEOUT) != -1) {
            if (System.currentTimeMillis() - time >= TIMEOUT) {
                dealerSocket.send("notify " + id + " " + start + " " + end);
            }
        }
        context.destroySocket(dealerSocket);
        context.destroy();
    }
}
