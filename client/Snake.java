public class Snake{
  private final int FSIZE;
  int length;
  int[][] coord;
  Snake(int fsize){
    this.FSIZE = fsize;
    length = 11;
    coord = new int[FSIZE*FSIZE][3];
    int tmp = 10;
    for (int i=0;i<length ;i++ ) {
      coord[i][0]=i;
      coord[i][1]=tmp--;
      coord[i][2]=0;
    }
  }

  void go(){
    for (int i=length-1;i>0;i-- ) {
      coord[i][1]=coord[i-1][1];
      coord[i][2]=coord[i-1][2];
    }
  }

  public void goRight(){
    // if(coord[0][1]==FSIZE-1){
    //   go();
    //   coord[0][1]=0;
    // }
    // else {
    //     go();
    //     coord[0][1]+=1;
    // }

    go();
    coord[0][1]+=1;
    if(coord[0][1]==FSIZE) coord[0][1]%=FSIZE;
  }

  public void goLeft(){
    go();
    coord[0][1]-=1;
    if(coord[0][1]==-1) coord[0][1]=FSIZE-1;
  }
  public void goUp(){
    go();
    coord[0][2]-=1;
    if(coord[0][2]==-1) coord[0][2]=FSIZE-1;
  }
  public void goDown(){
    go();
    coord[0][2]+=1;
    if(coord[0][2]==FSIZE) coord[0][2]%=FSIZE;
  }
  public void grow(){
    length+=1;
    coord[length-1][1]=coord[length-2][1];
    coord[length-1][2]=coord[length-2][2];
  }


}
