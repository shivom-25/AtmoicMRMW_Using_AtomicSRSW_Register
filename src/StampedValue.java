public class StampedValue {

    public long stamp;
    public long value;

    public StampedValue() {}

    public StampedValue(long init) {
        stamp = 0;
        value = init;
    }

    public void writeStampedValue(long stampSent, long valueSent) {
        stamp = stampSent;
        value = valueSent;
    }
    public static StampedValue maxfunc(StampedValue x,StampedValue y) {
        if(x.stamp > y.stamp)
            return x;
        else
            return y;
    }

    public static StampedValue MinValue = new StampedValue(-1);
}




