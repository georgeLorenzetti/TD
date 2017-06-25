package Utils.Enum;

/**
 * Created by George on 23/06/2017.
 */
public enum Cursors {
    DEFAULT(0),
    REASSIGN1(1),
    REASSIGN2(2);

    private int index;

    Cursors(int index){
        this.index = index;
    }

    public int index(){
        return index;
    }
}
