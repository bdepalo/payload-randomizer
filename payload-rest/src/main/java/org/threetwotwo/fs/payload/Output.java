package org.threetwotwo.fs.payload;

public class Output {
    private final int pax;
    private final int zfw;

    public Output(int pax, int zfw) {
        this.pax = pax;
        this.zfw = zfw;
    }

    public int getPax() {
        return pax;
    }

    public int getZfw() {
        return zfw;
    }
}
