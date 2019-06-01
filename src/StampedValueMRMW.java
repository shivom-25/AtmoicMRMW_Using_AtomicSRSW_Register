public class StampedValueMRMW {

    public long stamp;
    public AtomicMRSW AtomicMRSWObj;

    public StampedValueMRMW() {}

    public StampedValueMRMW(int capacity) {
        stamp = 0;
        AtomicMRSWObj = new AtomicMRSW(capacity);
    }

    public void writeStampedValueMRMW(long stampSent,long valueSent) {
        stamp= stampSent;
        AtomicMRSWObj.writeAtomicMRSW(valueSent);
    }

    public static StampedValueMRMW maxfunc(StampedValueMRMW x, StampedValueMRMW y) {
        if(x.stamp >y.stamp)
            return x;
        else
            return y;
    }

    public static StampedValueMRMW MinValue = new StampedValueMRMW(-1);
}
