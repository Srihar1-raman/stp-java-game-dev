import javax.swing.JFrame;

public class GameFrame extends JFrame {
    //constructor
    GameFrame(){
        // Board board = new Board();
        this.setSize(gameConstants.GWIDTH, gameConstants.GHEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("game - srihari");
        setLocationRelativeTo(null);
        add(new Board());
        setVisible(true);
    }

    public static void main(String[] args) {
        // GameFrame frame = 
        new GameFrame();

        //frame.setSize(gameConstants.GWIDTH, gameConstants.GHEIGHT);
        
    }
}
