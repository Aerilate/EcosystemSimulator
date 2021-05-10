/* Organism.java
 * Abstract class Organism, all living things on the grid
 * May 7, 2018
 * Raymond Wang
 */

/**
 * Organism
 * The Plant and Animal classes inherit this class
 */
abstract class Organism{
  private int health;
  
  Organism (int health){
    this.health=health;
  }
  
  //Getters and Setters
  public int getHealth(){
    return health;
  }
  
  public void setHealth(int health){
    this.health=health;
  }
}
