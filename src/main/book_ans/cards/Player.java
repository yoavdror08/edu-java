import java.util.*;

class Player{

  private  int red;
  private  int blue;
  private  int green;
  private  String name;

  public Player(String name){
    this.red = 5;
    this.blue = 5;
    this.green = 5;
    this.name = name;
  }

  public  int getRed(){
    return red;
  }

  public  int getBlue(){
    return blue;
  }

  public  int getGreen(){
    return green;
  }

  public  String getName(){
    return name;
  }

  public  void addCard(){
    int number = (int)(Math.random()*2);
    if(number == 0){
      red++;
    }
    else if(number == 1){
      blue++;
    }
    else{
      green++;
    }
  }

  public  void throwCard(){
    if(red<blue && red<green){
      if(red != 0){
        red--;
      }
      else{
        if(blue > green){
          green--;
        }
        else if(blue < green){
          blue--;
        }
        else{
          int number = (int)(Math.random()*2);
          if(number == 0){
            green--;
          }
          else{
            blue--;
          }
        }
      }
    }

    else if(red>blue && blue<green){
      if(blue != 0){
        blue--;
      }
      else{
        if(red > green){
          green--;
        }
        else if(red < green){
          red--;
        }
        else{
          int number = (int)(Math.random()*2);
          if(number == 0){
            green--;
          }
          else{
            red--;
          }
        }
      }
    }

    else if(green<blue && red>green){
      if(green != 0){
        green--;
      }
      else{
        if(blue > red){
          red--;
        }
        else if(blue < red){
          blue--;
        }
        else{
          int number = (int)(Math.random()*2);
          if(number == 0){
            red--;
          }
          else{
            blue--;
          }
        }
      }
    }

    else if(red == blue && red != green){
      int number = (int)(Math.random()*2);
      if(number == 0){
            red--;
          }
          else{
            blue--;
          }
    }

    else if(red != blue && red == green){
      int number = (int)(Math.random()*2);
      if(number == 0){
        red--;
      }
      else{
        green--;
      }
    }

    else if(red != blue && blue == green){
      int number = (int)(Math.random()*2);
      if(number == 0){
        green--;
      }
      else{
        blue--;
      }
    }

    else if(red == blue && blue == green){
      int number = (int)(Math.random()*3);
      if(number == 0){
        green--;
      }
      else if(number == 1){
        blue--;
      }
      else{
        red--;
      }
   }
  }

  public  String toString(){
      return name + " cards: red: " + red + " blue: " + blue + " green: " + green; 
    }

  public  boolean playerWon(){
    if(red == 10 || green == 10 || blue == 10){
      System.out.println(name + " won");
      return true;
    }
    return false;
  }
}
