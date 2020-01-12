/* Animal.java
 * Abstract class Animal extends Organisms
 * Includes sheep and wolves, who have movement
 * May 7, 2018
 * Raymond Wang
 */

/**
 * Animal
 * The Animal class is extended by the moving organisms - the Sheep and the Wolf classes
 */
abstract class Animal extends Organism{
  private int gender;
  private boolean alreadyMoved;
  
  Animal(int health, int gender, boolean alreadyMoved){
    super(health);
    this.gender=gender;
    this.alreadyMoved=false;
  }
  
  //Getters and Setters
  public int getGender(){
    return gender;
  }
  
  public boolean getAlreadyMoved(){
    return alreadyMoved;
  }
  
  public void setAlreadyMoved(boolean alreadyMoved){
    this.alreadyMoved=alreadyMoved;
  }
}