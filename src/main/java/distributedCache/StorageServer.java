package distributedCache;

import java.util.ArrayList;
import java.util.Arrays;

public class StorageServer {

    public static void main(String[] argv) {
        int start = Integer.parseInt(argv[0]);
        ArrayList<String> cache = new ArrayList<>(Arrays.asList(argv).subList(1, argv.length));
        int end = start + cache.size();

        
        while(poller.poll(3000) != -1) {

        }
    }
}
