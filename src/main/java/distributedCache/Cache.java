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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZFrame getFrame() {
        return frame;
    }

    public void setFrame(ZFrame frame) {
        this.frame = frame;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
