import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class StartFrame extends JFrame {
  JPanel panel;
  JLabel conn_lbl;
  JTextField hostname_tf;
  JTextField port_tf;
  JButton conn_btn;
  JButton play_btn;
  final static boolean shouldFill = true;
  final static boolean shouldWeightX = true;

  Client cl;

  StartFrame(){
    super("Подключение к серверу игры");
    setSize( 300, 250 );
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    hostname_tf = new JTextField("localhost");
    port_tf = new JTextField("4444");
    conn_lbl = new JLabel("conn_lbl");
    conn_btn = new JButton("Подключиться");
    play_btn = new JButton("Играть");
    conn_lbl = new JLabel("Подключение...");
    panel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    if (shouldFill) {
      c.fill = GridBagConstraints.HORIZONTAL;
    }
    if (shouldWeightX) {
      c.weightx = 1.0;
    }
    c.insets = new Insets(5, 10, 0, 10);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.ipady = 12;
    c.gridx = 0;
    c.gridy = 0;
    panel.add(new Label("Hostname: "), c);
    c.insets = new Insets(0, 0, 0, 10);
    c.gridx = 1;
    panel.add(hostname_tf, c);

    c.insets = new Insets(0, 10, 0, 0);
    c.gridx = 0;
    c.gridy = 1;
    panel.add(new Label("Port: "), c);
    c.insets = new Insets(0, 0, 0, 10);
    c.gridx = 1;
    panel.add(port_tf, c);

    c.insets = new Insets(0, 10, 0, 0);
    c.gridx = 0;
    c.gridy = 2;
    panel.add(conn_btn, c);
    c.insets = new Insets(0, 0, 0, 10);
    c.gridx = 1;
    panel.add(conn_lbl, c);
    conn_lbl.setVisible(false);

    c.insets = new Insets(5, 10, 5, 10);
    c.gridwidth = 2;
    c.gridx = 0;
    c.gridy = 3;
    panel.add(play_btn, c);
    play_btn.setVisible(false);
    add(panel);

    conn_btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        conn_lbl.setVisible(true);
        //Подключение к серверу
      //   try {
      //   fromserver = new Socket(hostname_tf.getText(),
      //         Integer.parseInt(port_tf.getText()));
      //     try{
      //       BufferedReader in  = new BufferedReader(new
      //                 InputStreamReader(fromserver.getInputStream()));
      //       PrintWriter    out = new
      //        PrintWriter(fromserver.getOutputStream(),true);
      //       BufferedReader inu = new
      //        BufferedReader(new InputStreamReader(System.in));
      //
      //       String fuser,fserver;
      //
      //       conn_lbl.setText("Подключено");
      //       play_btn.setVisible(true);
      //
      //     } catch (IOException ioe){
      //       conn_lbl.setText("IOException");
      //       JOptionPane.showMessageDialog(null,
      //            "IOException", "Ошибочка", JOptionPane.ERROR_MESSAGE);
      //       ioe.printStackTrace();
      //
      //     }
      // } catch(UnknownHostException uhe){
      //   conn_lbl.setText("UnknownHostException");
      //   JOptionPane.showMessageDialog(null,
      //       "UnknownHostException", "Ошибочка", JOptionPane.ERROR_MESSAGE);
      //
      //   uhe.printStackTrace();
      // } catch(IOException ioes){
      //   conn_lbl.setText("IOException on serv");
      //   JOptionPane.showMessageDialog(null,
      //       "IOException on server", "Ошибочка", JOptionPane.ERROR_MESSAGE);
      //   ioes.printStackTrace();
      // }

      try{
        cl = new Client(hostname_tf.getText(),port_tf.getText());
        conn_lbl.setText("Подключено");
        play_btn.setVisible(true);
      } catch(UnknownHostException unknhe){
        unknhe.printStackTrace();
        JOptionPane.showMessageDialog(null,
               "UnknownHostException", "Ошибочка", JOptionPane.ERROR_MESSAGE);
      } catch(IOException ioe){
        ioe.printStackTrace();
        JOptionPane.showMessageDialog(null,
             "IOException on server", "Ошибочка", JOptionPane.ERROR_MESSAGE);
      }

      }
    });

    play_btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //PlayFrame pf = new PlayFrame(fromserver);
        Main.play(cl);
        dispose();
      }

      });



  }



}
