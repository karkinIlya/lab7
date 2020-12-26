package distributedCache;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Client {

    public static final int THREADS_COUNT = 1;
    public static final int TIMEOUT = 3000;

    public static void main(String[] argv) {
        ZContext context = new ZContext(THREADS_COUNT);
        ZMQ.Socket client = createAndConnect(context, "tcp://localhost:8086", TIMEOUT);
    }
}
