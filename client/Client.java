import java.io.*;
import java.net.*;

public class Client {

  private Socket fromserver=null;
  private ObjectOutputStream oos;
  private ObjectInputStream ois;

  Client(String hostname, String  port)
    throws UnknownHostException, IOException {

    System.out.println("Welcome to Client side");

    System.out.println("Connecting to... "+hostname);

    fromserver = new Socket(hostname,Integer.parseInt(port));

    oos = new ObjectOutputStream(fromserver.getOutputStream());
    ois = new ObjectInputStream(fromserver.getInputStream());
  }
  void close() throws IOException{
    fromserver.close();
    oos.close();
    ois.close();
  }
  void sendField(int[][] f) throws IOException{
    oos.reset();
    oos.writeObject((Object) f);
    oos.flush();
  }
  int[] reciveBit() throws IOException, ClassNotFoundException{
    int[] f = new int[2];
    f = (int[])ois.readObject();
    return f;
  }
}
