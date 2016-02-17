import java.net.*;
import java.awt.EventQueue;


public class Main{

  public static void main(String[] args){
    StartFrame sf = new StartFrame();
    sf.setVisible(true);
    //play(null);
  }

  public static void play(Client cl){
    //PlayFrame pf = new PlayFrame(sock);
    //pf.setVisible(true);
    EventQueue.invokeLater(new Runnable() {

    @Override
    public void run() {
        PlayFrame pf = new PlayFrame(cl);
        pf.setVisible(true);
    }
});
    }

}
