import javax.swing.*;

public class TurmiteFrame extends JFrame {
    TurmiteFrame(){

        this.add(new TurmitePanel());
        this.setTitle("Turmite");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
