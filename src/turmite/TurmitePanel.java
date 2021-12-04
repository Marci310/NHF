package turmite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class TurmitePanel extends JPanel implements ActionListener {
    private  static final int SCREEN_WIDTH=700;
    private  static final int SCREEN_HEIGTH=700;
    private  static final int UNIT_SIZE=20;
    private  static final int DELAY=70;
    public static int[][] xy =new int[SCREEN_WIDTH/UNIT_SIZE][SCREEN_HEIGTH/UNIT_SIZE];
    private int x=SCREEN_WIDTH/UNIT_SIZE/2;
    private int y=SCREEN_HEIGTH/UNIT_SIZE/2;
    public int direction=0; //0,90,180,270
    public int stat=0;//0,1
    private  boolean running=false;
    private Timer timer;
    private JLabel savefile=new JLabel("Filename for save:");
    private JTextField savef=new JTextField();
    private JButton save=new JButton("Save");
    private JLabel filename=new JLabel("Filename:");
    private JTextField readin =new JTextField();
    private JButton open = new JButton("Open");
    private JLabel state=new JLabel("State(sta.-fie.-dir.-new fie.-new sta.)");
    private JTextField state_read =new JTextField();
    private JButton add=new JButton("Add");
    private JButton start=new JButton("Start");
    private JButton stop=new JButton("Stop");
    private JButton clear =new JButton("Clear");
    public ArrayList<TurmiteLogic> states=new ArrayList<>();



    public TurmitePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH+300,SCREEN_HEIGTH));
        this.setLayout(null);
        clear.setBounds(760,10,180,50);
        savefile.setBounds(710,60,130,50);
        savef.setBounds(710,100,280,50);
        save.setBounds(760,150,180,50);
        filename.setBounds(710,260,130,50);
        state.setBounds(710,420,280,50);
        state_read.setBounds(710,460,280,50);
        add.setBounds(760,510,180,50);
        start.setBounds(760,640,180,50);stop.setBounds(760,640,180,50);open.setBounds(760,350,180,50);readin.setBounds(710,300,280,50);
        stop.setVisible(false);
        save.addActionListener(e -> {
            try {
                save_states(savef.getText());
                save(savef.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        open.addActionListener(e -> {
            try {
                load_states(readin.getText());
                load(readin.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        add.addActionListener(e -> {

            add(state_read.getText());
        });
        clear.addActionListener(e -> {
            clear();
        });
        start.addActionListener(e -> {
            start();
            clear.setVisible(false);
            savefile.setVisible(false);
            savef.setVisible(false);
            save.setVisible(false);
            start.setVisible(false);
            add.setVisible(false);
            state_read.setVisible(false);
            readin.setVisible(false);
            filename.setVisible(false);
            open.setVisible(false);
            state.setVisible(false);
            stop.setVisible(true);
        });
        stop.addActionListener(e -> {
            running=false;
            clear.setVisible(true);
            start.setVisible(true);
            savefile.setVisible(true);
            savef.setVisible(true);
            save.setVisible(true);
            add.setVisible(true);
            state_read.setVisible(true);
            readin.setVisible(true);
            filename.setVisible(true);
            open.setVisible(true);
            state.setVisible(true);
            stop.setVisible(false);
        });


        this.add(start);this.add(stop);this.add(readin);this.add(open);this.add(filename);this.add(state);this.add(state_read);this.add(add);this.add(savefile);this.add(savef);this.add(save);this.add(clear);

    }

    public int[][] getXy() {
        return xy;
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

        for(int i=0;i<SCREEN_WIDTH/UNIT_SIZE;i++){
            for (int j=0;j<SCREEN_HEIGTH/UNIT_SIZE;j++){
                if (xy[i][j]==1){
                    g.setColor(Color.black);
                    g.fillRect((i-1)*UNIT_SIZE,(j-1)*UNIT_SIZE,UNIT_SIZE,UNIT_SIZE);}
            }
        }
        g.setColor(Color.red);
        g.fillRect(x*UNIT_SIZE,y*UNIT_SIZE,UNIT_SIZE,UNIT_SIZE);
        g.setColor(Color.black);


    }
    public void check_border(){
        if (x<1)
            running=false;
        if (y<1)
            running=false;
        if (x>SCREEN_WIDTH-1)
            running =false;
        if (y>SCREEN_HEIGTH-1)
            running =false;
    }
    public void check_state(){
        for (TurmiteLogic tmp : states) {
            if (tmp.state == stat && tmp.field == xy[x][y]) {
                direction=tmp.check_degree(direction);
                xy[x][y] = tmp.newfield;
                stat = tmp.newstate;
                move();
                check_border();
            }
        }
    }
    public void move(){
        switch (direction) {
            case 90 -> x += 1;
            case 180 -> y -= 1;
            case 270 -> x -= 1;
            case 0 -> y += 1;
        }
    }

    public void clear(){
        states.clear();
        for(int i=0;i<SCREEN_WIDTH/UNIT_SIZE;i++){
            for (int j=0;j<SCREEN_HEIGTH/UNIT_SIZE;j++){
                xy[i][j]=0;
            }
        }
        x=SCREEN_WIDTH/UNIT_SIZE/2;
        y=SCREEN_HEIGTH/UNIT_SIZE/2;
        repaint();

    }
    public void add(String text){
        String[] tmp=text.split("-");
        states.add(new TurmiteLogic(Character.getNumericValue( tmp[0].charAt(0)),Character.getNumericValue(tmp[1].charAt(0)),tmp[2].toLowerCase().charAt(0),Character.getNumericValue(tmp[3].charAt(0)),Character.getNumericValue(tmp[4].charAt(0))));
    }

    public void save_states(String name) throws IOException {
        FileOutputStream fos=new FileOutputStream((name+"states.txt"));
        ObjectOutputStream oot=new ObjectOutputStream(fos);
        oot.writeObject(states);
        oot.reset();
        oot.close();
        fos.close();
    }
    public void load_states(String name) throws IOException {
        states.clear();

        try {
            FileInputStream fis = new FileInputStream((name+"states.txt"));
            ObjectInputStream ois= new ObjectInputStream(fis);
            states = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void save(String name) throws IOException {
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<SCREEN_WIDTH/UNIT_SIZE;i++){
            for (int j=0;j<SCREEN_HEIGTH/UNIT_SIZE;j++){
                builder.append(xy[i][j]).append(" ");
                if (j<(SCREEN_HEIGTH/UNIT_SIZE)-1)
                    builder.append(",");
            }
            builder.append("\n");
        }
        BufferedWriter writer =new BufferedWriter(new FileWriter((name+".txt")));
        writer.write(builder.toString());
        writer.close();
    }

    public void load(String name) throws IOException {
        BufferedReader reader =new BufferedReader(new FileReader((name+".txt")));
        int row=0;
        String line;
        while((line=reader.readLine()) != null){
            String [] cols=line.split(",");
            int col = 0;
            for (String c : cols){
                xy[row][col]=Character.getNumericValue(c.charAt(0));
                col++;
            }
            row++;
        }
        reader.close();
        repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            check_state();
        }
        repaint();
    }
}
