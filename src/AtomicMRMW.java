import java.util.Vector;

public class AtomicMRMW {

    private Vector<StampedValueMRMW> a_table = new Vector<StampedValueMRMW>();

    public AtomicMRMW(int capacity) {

        for(int i=0;i<capacity;i++) {
            StampedValueMRMW stmpPtr = new StampedValueMRMW(capacity);
            a_table.add(stmpPtr);
        }
    }

    public void writeAtomicMRMW(long input,int id) {
        int me = id;
        StampedValueMRMW max = a_table.get(0);
        for(int i=0;i<a_table.size();i++) {
            max =StampedValueMRMW.maxfunc(max,a_table.get(i));
        }
        a_table.get(me).writeStampedValueMRMW(max.stamp+1,input);
    }

    public long readAtomicMRMW(int id) {
        int me = id;
        StampedValueMRMW max = a_table.get(0);
        for(int i=0;i<a_table.size();i++) {
            max = StampedValueMRMW.maxfunc(max,a_table.get(i));
        }
        return max.AtomicMRSWObj.readAtomicMRSW(id);
    }
}
