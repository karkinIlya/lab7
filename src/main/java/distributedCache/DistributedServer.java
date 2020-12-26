package distributedCache;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class DistributedServer {

    public static final int THREADS_COUNT = 1;
    public static final String CLIENT_SERVER = "tcp://localhost:8086";
    public static final String STORAGE_SERVER = "tcp://localhost:8086";
    public static final int POLLER_SIZE = 2;
    public static final int TIMEOUT = 5000;

    public static void main(String[] argv) {
        ZContext context = new ZContext(THREADS_COUNT);
        ZMQ.Socket clientSocket = context.createSocket(SocketType.ROUTER);
        clientSocket.bind(CLIENT_SERVER);
        ZMQ.Socket storageSocket = context.createSocket(SocketType.ROUTER);
        storageSocket.bind(STORAGE_SERVER);
        ZMQ.Poller poller = context.createPoller(POLLER_SIZE);
        poller.register(clientSocket, ZMQ.Poller.POLLIN);
        poller.register(storageSocket, ZMQ.Poller.POLLIN);
        long time = System.currentTimeMillis();
        while (poller.poll(TIMEOUT) != -1) {
            // обработка
        }
        context.destroySocket(clientSocket);
        context.destroySocket(storageSocket);
        context.destroy();
    }
}
