package distributedCache;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Client {

    public static final int THREADS_COUNT = 1;
    public static final int TIMEOUT = 3000;
    public static final String SERVER_ADDRESS = "tcp://localhost:8086";

    public static void main(String[] argv) {
        ZContext context = new ZContext(THREADS_COUNT);
        ZMQ.Socket client = connect(context);
    }

    public static  ZMQ.Socket connect(ZContext context) {
        ZMQ.Socket socket = context.createSocket(SocketType.REQ);
        socket.setReceiveTimeOut(TIMEOUT);
        socket.connect(SERVER_ADDRESS);
        return socket;
    }
}
