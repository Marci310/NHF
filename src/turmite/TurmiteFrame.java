package turmite;

import javax.swing.*;

public class TurmiteFrame extends JFrame {
    public TurmiteFrame(){

        this.add(new TurmitePanel());
        this.setTitle("turmite.Turmite");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
