import java.util.Vector;

public class AtomicMRSW {


    ThreadLocal<Long> lastStamp;
    private Vector<Vector<StampedValueMRSW>> a_table = new Vector<Vector<StampedValueMRSW>>();

    public AtomicMRSW() {}

    public AtomicMRSW(int readers) {
        lastStamp = new ThreadLocal<Long>() {
            protected Long initialValue() {
                return (long) 0;
            };
        };
        for(int i=0;i<readers;i++) {
            Vector<StampedValueMRSW> insideVect = new Vector<StampedValueMRSW>();
            for(int j=0;j<readers;j++) {
                StampedValueMRSW stmpPtr = new StampedValueMRSW(0);
                insideVect.addElement(stmpPtr);
            }
            a_table.addElement(insideVect);
        }
    }

    public long readAtomicMRSW(int id) {
        int me =id;
        StampedValueMRSW val = a_table.get(me).get(me);
        for(int i=0;i<a_table.size();i++) {
            val = StampedValueMRSW.maxfunc(val,a_table.get(i).get(me));
        }
        for(int i=0;i<a_table.size();i++) {
            a_table.get(me).set(i,val);
        }
        return val.AtomicSRSWObj.readAtmoicSRSW();
    }

    public void writeAtomicMRSW(long input) {
        long stamp = lastStamp.get() +1;
        lastStamp.set(stamp);
        StampedValueMRSW val = new StampedValueMRSW(0);
        val.writeStampedValueMRSW(stamp,input);

        for(int i=0;i<a_table.size();i++) {
            a_table.get(i).set(i,val);
        }
    }
}