import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Random;
import javax.swing.Timer;

public class PlayFrame extends JFrame {

  PlayFrame(Client cli){
    super("Змейка");
    setFocusable(true);
    requestFocus();
    setSize(500,500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    Surface sr = new Surface(cli);
    add(sr);
    addKeyListener(sr);

  }
}

class Surface extends JPanel implements ActionListener,KeyListener{
  private Timer timer;
  private final int INITIAL_DELAY = 200;
  private final int DELAY = 80;
  private final int FSIZE = 40;
  private Snake snake;
  int[] bit;
  private Client cl;
  int[][] field;
  int dest=0;
  boolean eaten = true;

  public Surface(Client cl) {
      this.cl = cl;
      field = new int[FSIZE][FSIZE];
      bit = new int[2];
      setFocusable(true);
      setVisible(true);
      requestFocus();
      snake = new Snake(FSIZE);
      initTimer();
    }

    private void initTimer() {

        timer = new Timer(DELAY, this);
        timer.setInitialDelay(INITIAL_DELAY);
        timer.start();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        BasicStroke bs1 = new BasicStroke(2, BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_BEVEL);
        g2d.setStroke(bs1);
        g2d.setPaint(new Color(180, 0, 255));

        mkField(snake.coord);

        for(int i=0;i<FSIZE;i++){
          for(int j=0;j<FSIZE;j++){
            switch(field[i][j]){
              case 0: g2d.drawRect(50+i*10, 35+j*10, 10, 10); break;
              case 1: g2d.fillRect(50+i*10, 35+j*10, 10, 10); break;
              case 2:
                  g2d.setPaint(new Color(255, 20, 25));
                  g2d.fillRect(50+i*10, 35+j*10, 10, 10);
                  g2d.setPaint(new Color(180, 0, 255));
                  break;
            }
        }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        switch(dest){
          case 0:
            switch(field[(snake.coord[0][1]+1)%FSIZE][snake.coord[0][2]]){
              case 0: snake.goRight(); break;
              case 1:  gameOver(); timer.stop(); break;
              case 2: snake.goRight(); bite(); break;
            }
            break;
          case 1:
            switch(field[((snake.coord[0][1]-1)+40)%FSIZE][snake.coord[0][2]]){
              case 0:  snake.goLeft(); break;
              case 1:  timer.stop(); gameOver(); break;
              case 2:  snake.goLeft(); bite(); break;
            }
            break;
          case 2:
            switch(field[snake.coord[0][1]][((snake.coord[0][2]-1)+40)%FSIZE]){
              case 0: snake.goUp(); break;
              case 1: timer.stop(); gameOver();break;
              case 2: snake.goUp(); bite(); break;
            }
          break;
          case 3:
            switch(field[snake.coord[0][1]][(snake.coord[0][2]+1)%FSIZE]){
              case 0: snake.goDown(); break;
              case 1: timer.stop(); gameOver(); break;
              case 2: snake.goDown(); bite(); break;
            }
          break;
        }
    }

    @Override
    public void keyPressed(KeyEvent evt) {
       switch(evt.getKeyCode()) {
          case KeyEvent.VK_LEFT:
            if(dest!=0) dest=1;
             break;
          case KeyEvent.VK_RIGHT:
             if(dest!=1) dest=0;
             break;
         case KeyEvent.VK_UP:
             if(dest!=3) dest=2;
             break;
         case KeyEvent.VK_DOWN:
            if(dest!=2) dest=3;
            break;
       }
    }

    @Override
    public void keyTyped(KeyEvent evt) {
    }
    @Override
    public void keyReleased(KeyEvent evt) {
    }

    void mkField(int[][] scoord){
      for (int i=0;i<FSIZE ;i++ ) {
        for (int j=0;j<FSIZE ;j++ ) {
          field[i][j]=0;
        }
      }
      for(int i=0;i<snake.length;i++){
        //System.out.println("x="+snake.coord[i][1]+"  y="+snake.coord[i][2]);
        field[snake.coord[i][1]][snake.coord[i][2]]=1;
      }
      if(eaten){
        //createBit(scoord);
        try{
          cl.sendField(field);
        } catch(IOException ioe){
          System.out.println("Can't send field to serv");
        }
        try{
          bit = cl.reciveBit();
        } catch(IOException ioe){
          System.out.println("Can't recieve bit from");
        }catch (ClassNotFoundException e) {
            System.out.println("Can't cast bit");
        }

        eaten = false;
      }
      field[bit[0]][bit[1]] = 2;
    }

    void bite(){
      eaten = true;
      snake.grow();
    }
    void gameOver(){
      field[0][0]=-1;
      try{
        cl.sendField(field);
        System.out.println("the end");
      } catch(IOException ioe){
        System.out.println("Can't send field to serv");
      }
      JOptionPane.showMessageDialog(null,
           "Нехорошо есть себя", "Конец игры", JOptionPane.ERROR_MESSAGE);

    }

}
