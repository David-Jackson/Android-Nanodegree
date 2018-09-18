package fyi.jackson.activejournal.dummy;

public class Point {
    public int index;
    public long ts;
    public double lat;
    public double lng;
    public int acc;
    public Double alt;
    public Float vAcc;

    public Point(int index, long ts, double lat, double lng, int acc, Integer alt, Integer vAcc) {
        this.index = index;
        this.ts = ts;
        this.lat = lat;
        this.lng = lng;
        this.acc = acc;
        if (alt != null) this.alt = alt * 1.0;
        if (vAcc != null) this.vAcc = vAcc * 1.0f;
    }
}
