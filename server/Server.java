import java.io.*;
import java.net.*;
import java.util.Random;

public class Server {
    private static ServerSocket servers = null;
    private static Socket       fromclient = null;
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;

    public static void main(String[] args) {
      System.out.println("Welcome to Server side");

      // create server socket
      try {
        servers = new ServerSocket(4444);
      } catch (IOException e) {
        System.out.println("Couldn't listen to port 4444");
        System.exit(-1);
      }

      try {
        System.out.print("Waiting for a client...");
        fromclient= servers.accept();
        System.out.println("Client connected");
      } catch (IOException e) {
        System.out.println("Can't accept");
        System.exit(-1);
      }

      // in  = new BufferedReader(new
      //  InputStreamReader(fromclient.getInputStream()));
      // out = new PrintWriter(fromclient.getOutputStream(),true);
      // String         input,output;
      try{
        ois = new ObjectInputStream(fromclient.getInputStream());
        oos = new ObjectOutputStream(fromclient.getOutputStream());
      } catch (IOException e) {
        System.out.println("streams not created");
        System.exit(-1);
      }

      int[][] field=new int[40][40];
      int[] bit;
      try{
        field = (int[][])ois.readObject();
        System.out.println("first field[0][0]="+field[0][0]);
      }catch (IOException e) {
        System.out.println("Can't accept field");
        System.exit(-1);
      }catch (ClassNotFoundException e) {
        System.out.println("Can't cast field");
        System.exit(-1);
      }

      while(field[0][0]!=-1){
        int[] b = createBit(field);
        try{
          oos.writeObject((Object)b);
          System.out.println("bit: x="+b[0]+" y="+b[1]);
        }catch(IOException ioe){
          System.out.println("Can't send bit to client");
        }

        try{
          field = (int[][])ois.readObject();
          System.out.println("field[0][0]="+field[0][0]);
        }catch (IOException e) {
          System.out.println("Can't accept field in cicle");
        }catch (ClassNotFoundException e) {
          System.out.println("Can't cast field");
        }


      }


      try{
      fromclient.close();
      servers.close();
      } catch (IOException e) {
        System.out.println("Can't close");
        System.exit(-1);
      }
    }



    static int[] createBit(int[][] field){
      boolean ok = false;
      int x,y;
      int[] bit = new int[2];
      while(!ok){
        x = randInt(0,39);
        y = randInt(0,39);
        if(field[x][y]==0){
           bit[0]=x;
           bit[1]=y;
           ok=true;
         }
      }
      return bit;
    }

    public static int randInt(int min, int max) {
      Random rand = new Random();
      int randomNum = rand.nextInt((max - min) + 1) + min;
      return randomNum;
    }


}
