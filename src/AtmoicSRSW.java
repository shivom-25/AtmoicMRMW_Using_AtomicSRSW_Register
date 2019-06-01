public class AtmoicSRSW {

    private ThreadLocal<Long> lastStamp;
    private ThreadLocal<StampedValue> lastRead;
    private StampedValue rvalue;

    public AtmoicSRSW() {}

    public AtmoicSRSW(long init) {
        rvalue = new StampedValue(init);
        lastRead = new ThreadLocal<StampedValue>() {
            protected  StampedValue initialValue() {
                return rvalue;
            };
        };
        lastStamp = new ThreadLocal<Long>() {
            protected Long initialValue() {
                return (long) 0;
            };
        };
    }

    public long readAtmoicSRSW() {
        StampedValue value = rvalue;
        StampedValue last = lastRead.get();
        StampedValue result = StampedValue.maxfunc(value,last);
        lastRead.set(result);
        return result.value;
    }

    public void writeAtomicSRSW(long input) {
        long stamp = lastStamp.get() + 1;
        rvalue.writeStampedValue(stamp,input);
        lastStamp.set(stamp);
    }
}
