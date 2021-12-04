package turmite;

import java.io.Serializable;
public class TurmiteLogic implements Serializable {
    public int state;
    public int field;
    public char direction;
    public int newfield;
    public int newstate;

    public TurmiteLogic(int state, int field, char direction, int newfield, int newstate) {
        this.state = state;
        this.field = field;
        this.direction = direction;
        this.newfield = newfield;
        this.newstate = newstate;
    }

    public int check_degree(int change){
        switch (this.direction) {
            case 'l':
                change -= 90;
                break;
            case 'r':
                change += 90;
                break;
            case 'n':
                break;
            case 'u':
                change -= 180;
        }
        if (change == -90)
            change = 270;
        if (change == 360)
            change = 0;
        if (change == -180)
            change = 180;
        return change;
    }


    @Override
    public String toString() {
        return state +"-"+ field +"-"+ direction +"-" + newfield + "-"+ newstate ;
    }
}
