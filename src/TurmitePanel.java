import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class TurmitePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH=700;
    static final int SCREEN_HEIGTH=700;
    static final int UNIT_SIZE=20;
    static final int DELAY=70;
    final int xy[][]=new int[SCREEN_WIDTH/UNIT_SIZE][SCREEN_HEIGTH/UNIT_SIZE];
    int x=SCREEN_WIDTH/UNIT_SIZE/2;
    int y=SCREEN_HEIGTH/UNIT_SIZE/2;
    int direction=0; //0,90,180,270
    int stat=0;//0,1
    boolean running=false;
    Timer timer;
    JLabel savefile=new JLabel("Filename for save:");
    JTextField savef=new JTextField();
    JButton save=new JButton("Save");
    JLabel filename=new JLabel("Filename:");
    JTextField readin =new JTextField();
    JButton open = new JButton("Open");
    JLabel state=new JLabel("State(sta.-fie.-dir.-new fie.-new sta.)");
    JTextField state_read =new JTextField();
    JButton add=new JButton("Add");
    JButton start=new JButton("Start");
    JButton stop=new JButton("Stop");
    JButton clear =new JButton("Clear");
    ArrayList<TurmiteLogic> states=new ArrayList<>();


    TurmitePanel(){
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
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    save();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] tmp=state_read.getText().split("-");
                states.add(new TurmiteLogic(Character.getNumericValue( tmp[0].charAt(0)),Character.getNumericValue(tmp[1].charAt(0)),tmp[2].charAt(0),Character.getNumericValue(tmp[3].charAt(0)),Character.getNumericValue(tmp[4].charAt(0))));
                }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                states.clear();
                for(int i=0;i<SCREEN_WIDTH/UNIT_SIZE;i++){
                    for (int j=0;j<SCREEN_HEIGTH/UNIT_SIZE;j++){
                        xy[i][j]=0;
                    }
                }
            }
        });
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                running=true;
                start();
                clear.setVisible(false);
                savefile.setVisible(false);
                savef.setVisible(false);    // Create and populate the list
                save.setVisible(false);
                start.setVisible(false);
                add.setVisible(false);
                state_read.setVisible(false);
                readin.setVisible(false);
                filename.setVisible(false);
                open.setVisible(false);
                state.setVisible(false);
                stop.setVisible(true);
            }
        });
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });


        this.add(start);this.add(stop);this.add(readin);this.add(open);this.add(filename);this.add(state);this.add(state_read);this.add(add);this.add(savefile);this.add(savef);this.add(save);this.add(clear);

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
    public void check_state(){
        Iterator<TurmiteLogic> iter= states.iterator();
        while(iter.hasNext()){
            TurmiteLogic tmp =iter.next();
            if (tmp.state==stat && tmp.field==xy[x][y]){

                switch (tmp.direction){
                    case 'l':
                        direction-=90;
                        break;
                    case 'r':
                        direction+=90;
                        break;
                    case 'n':
                        break;
                    case 'u':
                        direction-=90;
                }
                if (direction==-90)
                    direction=270;
                if (direction==360)
                    direction=0;
                if (direction==-180)
                    direction=180;
                xy[x][y]=tmp.newfield;
                stat=tmp.newstate;
                move();
            }

        }


    }
    public void move(){
        switch(direction) {
            case 90:
                x+=1;
                break;
            case 180:
                y-=1;
                break;
            case 270:
                x-=1;
                break;
            case 0:
                y+=1;
                break;

        }
    }

    public void save() throws IOException {
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<SCREEN_WIDTH/UNIT_SIZE;i++){
            for (int j=0;j<SCREEN_HEIGTH/UNIT_SIZE;j++){
                builder.append(xy[x][y]+" ");
                if (j<(SCREEN_HEIGTH/UNIT_SIZE)-1)
                    builder.append(",");
            }
            builder.append("\n");
        }
        BufferedWriter writer =new BufferedWriter(new FileWriter(savef.getText()+".txt"));
        writer.write(builder.toString());
        writer.close();
    }

    public void load() throws IOException {
        BufferedReader reader =new BufferedReader(new FileReader(readin.getText()+".txt"));
        int row=0;
        String line;
        while((line=reader.readLine()) != null){
            String [] cols=line.split(",");
            int col = 0;
            for (String c : cols){
                xy[row][col]=Integer.parseInt(c);
                col++;
            }
            row++;
        }
        reader.close();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){

            check_state();
        }
        repaint();

    }
}
