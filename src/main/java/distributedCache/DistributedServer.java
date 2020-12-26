package distributedCache;

import org.zeromq.*;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.util.ArrayList;
import java.util.Collections;

public class DistributedServer {

    public static final int THREADS_COUNT = 1;
    public static final String CLIENT_SERVER = "tcp://localhost:8086";
    public static final String STORAGE_SERVER = "tcp://localhost:8086";
    public static final int POLLER_SIZE = 2;
    public static final int TIMEOUT = 5000;
    private static final ArrayList<Cache> caches = new ArrayList<>();
    public static final int CLIENT_SOCKET = 0;
    public static final int STORAGE_SOCKET = 1;
    public static final String GET_REQUEST = "get";
    public static final String PUT_REQUEST = "put";
    public static final String NOT_FOUND_ERROR = "not found";
    public static final String ERROR = "error";

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
            if (System.currentTimeMillis() - time >= TIMEOUT) {
                Collections.shuffle(caches);
                time = System.currentTimeMillis();
            }
            if (poller.pollin(CLIENT_SOCKET)) {
                ZMsg msg = ZMsg.recvMsg(clientSocket);
                String stringMessage = msg.getLast().toString().toLowerCase();
                if(stringMessage.startsWith(GET_REQUEST)) {
                    String[] splitedString = stringMessage.split(" ");
                    if (splitedString.length >= 2) {
                        int key = Integer.parseInt(splitedString[1]);
                        boolean found = false;
                        for (Cache c : caches) {
                            if (c.getStart() <= key && c.getEnd() >= key && c.isActual()) {
                                c.getFrame().send(storageSocket, ZFrame.REUSE | ZFrame.MORE);
                                msg.send(storageSocket, false);
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            msg.getLast().reset(NOT_FOUND_ERROR);
                            msg.send(clientSocket);
                        }
                    } else {
                        msg.getLast().reset(ERROR);
                        msg.send(clientSocket);
                    }
                } else if (stringMessage.startsWith(PUT_REQUEST)) {
                    String[] splitedString = stringMessage.split(" ");
                    if (splitedString.length >= 3) {
                        int key = Integer.parseInt(splitedString[1]);
                        String value = splitedString[2];
                        boolean found = false;
                        for (Cache c : caches) {
                            if (c.getStart() <= key && c.getEnd() >= key) {
                                c.getFrame().send(storageSocket, ZFrame.REUSE | ZFrame.MORE);
                                msg.send(storageSocket, false);
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            msg.getLast().reset(NOT_FOUND_ERROR);
                            msg.send(clientSocket);
                        }
                    } else {
                        msg.getLast().reset(ERROR);
                        msg.send(clientSocket);
                    }
                }
            }
            if (poller.pollin(STORAGE_SOCKET)) {

            }
        }
        context.destroySocket(clientSocket);
        context.destroySocket(storageSocket);
        context.destroy();
    }
}
