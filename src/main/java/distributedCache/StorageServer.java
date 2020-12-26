package distributedCache;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.Arrays;

public class StorageServer {

    public static void main(String[] argv) {
        int start = Integer.parseInt(argv[0]);
        ArrayList<String> cache = new ArrayList<>(Arrays.asList(argv).subList(1, argv.length));
        int end = start + cache.size();

        ZContext context = new ZContext(1);
        ZMQ.Poller poller = context.createPoller(1);
        while(poller.poll(3000) != -1) {

        }
    }
}
