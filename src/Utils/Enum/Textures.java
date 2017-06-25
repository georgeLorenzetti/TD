package Utils.Enum;

/**
 * Created by George on 17/06/2017.
 */
public enum Textures{
    MAP(0),
    TOWER(1),
    TOWERSELECTED(2),
    ENEMY(3),
    TRICONNECT(4),
    BICONNECT(5);

    private int index;

    Textures(int index) {
        this.index = index;
    }

    public int index(){
        return index;
    }
}

