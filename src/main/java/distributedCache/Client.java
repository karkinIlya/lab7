package distributedCache;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Scanner;

public class Client {

    public static final int THREADS_COUNT = 1;
    public static final int TIMEOUT = 3000;
    public static final String SERVER_ADDRESS = "tcp://localhost:8086";

    public static void main(String[] argv) {
        ZContext context = new ZContext(THREADS_COUNT);
        ZMQ.Socket client = connectToServer(context);
        Scanner in = new Scanner(System.in);
        while (true) {

        }
        context.destroySocket(client);
        context.destroy();
    }

    public static  ZMQ.Socket connectToServer(ZContext context) {
        ZMQ.Socket socket = context.createSocket(SocketType.REQ);
        socket.setReceiveTimeOut(TIMEOUT);
        socket.connect(SERVER_ADDRESS);
        return socket;
    }
}
