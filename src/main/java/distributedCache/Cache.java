package distributedCache;

import org.zeromq.ZFrame;

public class Cache {
    public static final int TIMEOUT = 5000;
    private long time;
    private int start;
    private int end;
    private ZFrame frame;
    private String id;

    public Cache(int start, int end, String id, ZFrame frame) {
        this.time = System.currentTimeMillis();
        this. start = start;
        this.end = end;
        this.frame = frame;
        this.id = id;
    }

    boolean isActual() {
        return System.currentTimeMillis() - time <= TIMEOUT;
    }
}
