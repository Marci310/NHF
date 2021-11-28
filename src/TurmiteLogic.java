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

    @Override
    public String toString() {
        return "TurmiteLogic{" +
                "state=" + state +
                ", field=" + field +
                ", direction=" + direction +
                ", newfield=" + newfield +
                ", newstate=" + newstate +
                '}';
    }
}
