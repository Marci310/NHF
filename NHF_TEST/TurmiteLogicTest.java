import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import turmite.TurmiteLogic;
import turmite.TurmitePanel;

import java.io.IOException;

public class TurmiteLogicTest {
    String state1;
    String state2;
    TurmiteLogic tmp1;
    TurmiteLogic tmp2;
    TurmitePanel panel;

    @Before
    public void setup(){
        panel= new TurmitePanel();
        tmp1= new TurmiteLogic(0,0,'r',1,0);
        tmp2= new TurmiteLogic(0,1,'l',0,0);
        state1=("0-0-r-1-0");
        state2=("0-1-l-0-0");
        panel.states.add(tmp1);
        panel.states.add(tmp2);
    }
    @Test
    public void correct_constructor(){

        Assert.assertEquals(tmp2.toString(),state2);

    }

    @Test
    public void check_degree() {
        int direction=0;
        direction=tmp2.check_degree(direction);
        Assert.assertNotEquals(-90,direction);
        Assert.assertEquals(270,direction);

    }
    @Test
    public void clear(){
        panel.clear();
        Assert.assertEquals(panel.states.size(),0);
    }
    @Test
    public void add(){

        panel.clear();
        panel.add(state1);
        Assert.assertEquals(panel.states.get(0).toString(), state1);
    }

    @Test
    public void game() throws IOException {
        TurmitePanel panel2=new TurmitePanel();
        panel2.add(state1);
        panel2.add(state2);
        for (int i=0; i<5;i++){
            panel.move();
            panel2.move();
        }
        panel.save_states("test");
        panel.save("test");
        panel.clear();
        panel.load("test");
        panel.load_states("test");
        Assert.assertArrayEquals(panel.getXy(), panel2.getXy());
        Assert.assertEquals(panel.states.toString(),panel2.states.toString());

    }

}