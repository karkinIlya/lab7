package distributedCache;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.Arrays;

public class StorageServer {

    public static final String SERVER_ADDRESS = "tcp://localhost:8088";

    public static void main(String[] argv) {
        int start = Integer.parseInt(argv[0]);
        ArrayList<String> cache = new ArrayList<>(Arrays.asList(argv).subList(1, argv.length));
        int end = start + cache.size();

        ZContext context = new ZContext(1);
        ZMQ.Socket dealerSocket = context.createSocket(SocketType.DEALER);
        dealerSocket.connect(SERVER_ADDRESS);
        ZMQ.Poller poller = context.createPoller(1);
        long time = System.currentTimeMillis();
        while(poller.poll(3000) != -1) {

        }
    }
}
