package Utils.Enum;

/**
 * Created by George on 21/06/2017.
 */
public enum Inputs {
    LEFTMOUSEDOWN(0),
    RIGHTMOUSEDOWN(1),
    MOUSEMOVFED(2),
    LEFTMOUSEUP(3),
    RIGHTMOUSEUP(4),
    SHIFT(5);

    private int index;

    Inputs(int index){
        this.index = index;
    }

    public int index(){
        return index;
    }
}
