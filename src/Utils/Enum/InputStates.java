package Utils.Enum;

/**
 * Created by George on 21/06/2017.
 */
public enum InputStates {
        NOINPUT(0),
        TOWERSELECTED(1),
        DRAGGINGTOWER(2),
        REASSIGNINGCONNECT(3);

        private int index;

        InputStates(int index){
            this.index = index;
        }

        public int index(){
            return index;
        }
}

