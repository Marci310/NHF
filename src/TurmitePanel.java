import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TurmitePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH=700;
    static final int SCREEN_HEIGTH=700;
    static final int UNIT_SIZE=20;
    static final int UNITS=(SCREEN_HEIGTH*SCREEN_WIDTH)/UNIT_SIZE;
    static final int DELAY=70;
    final int x[]=new int[UNITS];
    final int y[]=new int[UNITS];
    boolean running=false;
    Timer timer;
    JButton start=new JButton("Start");
    JButton stop=new JButton("Stop");
    JTextField readin =new JTextField();
    JTextField state_read =new JTextField();
    JButton open = new JButton("Open");
    JLabel filename=new JLabel("Filename:");
    JLabel state=new JLabel("State(sta.-fie.-dir.-new fie.-new sta.)");
    JButton add=new JButton("Add");
    ArrayList<TurmiteLogic> states=new ArrayList<>();


    TurmitePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH+300,SCREEN_HEIGTH));
        this.setLayout(null);
        filename.setBounds(710,260,130,50);
        state.setBounds(710,420,280,50);
        state_read.setBounds(710,460,280,50);
        add.setBounds(760,510,180,50);
        start.setBounds(760,640,180,50);stop.setBounds(760,640,180,50);open.setBounds(760,350,180,50);readin.setBounds(710,300,280,50);
        stop.setVisible(false);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] tmp=state_read.getText().split("-");
                states.add(new TurmiteLogic(Character.getNumericValue( tmp[0].charAt(0)),Character.getNumericValue(tmp[1].charAt(0)),tmp[2].charAt(0),Character.getNumericValue(tmp[3].charAt(0)),Character.getNumericValue(tmp[4].charAt(0))));
                System.out.println(states.toString());
            }
        });
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                running=true;
                start();
                start.setVisible(false);
                stop.setVisible(true);
            }
        });
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                running=false;
                start.setVisible(true);
                stop.setVisible(false);
            }
        });


        this.add(start);this.add(stop);this.add(readin);this.add(open);this.add(filename);this.add(state);this.add(state_read);this.add(add);

    }
    public void start(){
        running=true;
        timer= new Timer(DELAY,this);
        timer.start();
    }
    public void paint(Graphics g){
        super.paint(g);
        draw(g);
    }
    public void draw (Graphics g){
        for (int i=0;i<SCREEN_HEIGTH/UNIT_SIZE;i++){
            g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGTH);
            g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
        }
    }
    public void move(){

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
