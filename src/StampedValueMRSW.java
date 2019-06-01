public class StampedValueMRSW {


    public long stamp;
    public AtmoicSRSW AtomicSRSWObj;

    public StampedValueMRSW() {}

    public StampedValueMRSW(long init) {
        stamp=0;
        AtomicSRSWObj = new AtmoicSRSW(init);
    }

    public void writeStampedValueMRSW(long stampSent,long valueSent) {
        stamp= stampSent;
        AtomicSRSWObj.writeAtomicSRSW(valueSent);
    }

    public static StampedValueMRSW maxfunc(StampedValueMRSW x, StampedValueMRSW y) {
        if(x.stamp >y.stamp)
            return x;
        else
            return y;
    }

    public static StampedValueMRSW MinValue = new StampedValueMRSW(-1);
}
