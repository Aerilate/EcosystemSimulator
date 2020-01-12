/* Ecosystem.java
 * Runs a simulation with plants, sheep, and wolves
 * 05/07/18
 * Raymond Wang
 */

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Ecosystem
 * The class containing the main method, the spawn method and the move method
 */
class Ecosystem{
  
  /**
   * spawn
   * This method spawns additional animals into the array
   * @param the 2D map, the map size, the type of animal to spawn, the starting health for baby sheep and wolves
   * @return nothing
   */
  public static void spawn(Organism[][] map, int mapSize, String organism, int babySheepHealth, int babyWolfHealth){
    Random myRandom= new Random();
    
    int gender = myRandom.nextInt(1);
    int spawnX;
    int spawnY;
    int spawnThreshold=0;
    
    do {
      spawnX = myRandom.nextInt(mapSize);
      spawnY = myRandom.nextInt(mapSize);
      spawnThreshold+=1;
    }while( ( ((map[spawnY][spawnX] instanceof Plant)) || ((map[spawnY][spawnX] instanceof Sheep)) ||((map[spawnY][spawnX] instanceof Wolf)) ) && (spawnThreshold<10) );
    
    if (organism.equals("Sheep")){
      map[spawnY][spawnX]=new Sheep(babySheepHealth, gender, true);
      
    } else if (organism.equals("Wolf")){
      map[spawnY][spawnX]=new Wolf(babyWolfHealth, gender, true);
    }
  }
  
  
  /**
   * move
   * This method allows the objects to move around the array
   * @param A 2D array map, the map size, the type of animal, coordinates, a boolean representing if the animal already moved,
   * starting health for baby sheep and wolves
   * @return nothing
   */
  public static void move(Organism[][] map, int mapSize, Object animal, int x, int y, int health, boolean alreadyMoved, int babySheepHealth, int babyWolfHealth){
    Random myRandom= new Random();
    int direction = myRandom.nextInt(5);
    
    if (!alreadyMoved){
      
      //Sheep Movement
      //Avoids walking over the border and on wolves
      if (map[y][x] instanceof Sheep){
        ((Sheep)map[y][x]).setAlreadyMoved(true); //Marks down that it doesn't need to move again this turn
        
        if (direction==0){ //No Movement (Staying)
          
        }else if ( (direction==1) && (y!=map[0].length-1) && (!(map[y+1][x] instanceof Wolf)) ){ //Down
          if (!(map[y+1][x] instanceof Sheep) ){ //Not Breeding
            if (map[y+1][x] instanceof Plant){ //Eating a plant
              map[y][x].setHealth(health+map[y+1][x].getHealth());
            }
            map[y+1][x]=map[y][x];
            map[y][x]=null;
            
          } else if (map[y+1][x] instanceof Sheep ){ //Breeding
            if ( ((((Sheep)map[y+1][x]).getHealth()) + (((Sheep)map[y][x]).getHealth()) >20) && (((Sheep)map[y+1][x]).getGender() != ((Sheep)map[y][x]).getGender()) ){ //Checks for different genders
              map[y+1][x].setHealth(health-10);
              map[y][x].setHealth(health-10);
              spawn(map, map.length, "Sheep", babySheepHealth, babyWolfHealth);
            }
          }
          
        } else if ( (direction==2) && (y!=0) && (!(map[y-1][x] instanceof Wolf)) ){ //Up
          if ( !(map[y-1][x] instanceof Sheep) ){ //Not Breeding
            if (map[y-1][x] instanceof Plant){ //Eating a plant
              map[y][x].setHealth(health+map[y-1][x].getHealth());
            }
            map[y-1][x]=map[y][x];
            map[y][x]=null;
            
          } else if (map[y-1][x] instanceof Sheep){ //Breeding
            if ( ((((Sheep)map[y-1][x]).getHealth()) + (((Sheep)map[y][x]).getHealth()) >20)  && (((Sheep)map[y-1][x]).getGender() != ((Sheep)map[y][x]).getGender()) ){ //Checks for different genders
              map[y-1][x].setHealth(health-10);
              map[y][x].setHealth(health-10);
              spawn(map, map.length, "Sheep",  babySheepHealth, babyWolfHealth);
            }
          }
          
        } else if ( (direction==3) && (x!=map[0].length-1) && (!(map[y][x+1] instanceof Wolf)) ){ //Right
          if (!(map[y][x+1] instanceof Sheep) ){ //Not Breeding
            if (map[y][x+1] instanceof Plant){ //Eating a plant
              map[y][x].setHealth(health+map[y][x+1].getHealth());
            }
            map[y][x+1]=map[y][x];
            map[y][x]=null;
            
          } else if (map[y][x+1] instanceof Sheep ){ //Breeding
            
            if ( ((((Sheep)map[y][x+1]).getHealth()) + (((Sheep)map[y][x]).getHealth()) >20) && (((Sheep)map[y][x+1]).getGender() != ((Sheep)map[y][x]).getGender()) ){ //Checks for different genders
              map[y][x+1].setHealth(health-10);
              map[y][x].setHealth(health-10);
              spawn(map, map.length, "Sheep", babySheepHealth, babyWolfHealth);
            }
          }
          
        } else if( (direction==4) && (x!=0) && (!(map[y][x-1] instanceof Wolf)) ){ //Left
          if (!(map[y][x-1] instanceof Sheep) ){ //Not Breeding 
            if (map[y][x-1] instanceof Plant){ //Eating a plant
              map[y][x].setHealth(health+map[y][x-1].getHealth());
            }
            map[y][x-1]=map[y][x];
            map[y][x]=null;
            
          } else if (map[y][x-1] instanceof Sheep ){ //Breeding
            if ( ((((Sheep)map[y][x-1]).getHealth()) + (((Sheep)map[y][x]).getHealth()) >20) && (((Sheep)map[y][x-1]).getGender() != ((Sheep)map[y][x]).getGender()) ){ //Checks for different genders
              map[y][x-1].setHealth(health-10);
              map[y][x].setHealth(health-10);
              spawn(map, map.length, "Sheep", babySheepHealth, babyWolfHealth);
            }
          }
        }
      }
      
      //Wolf Movement
      if (map[y][x] instanceof Wolf){
        ((Wolf)map[y][x]).setAlreadyMoved(true); //Marks down that it doesn't need to move again this turn
        
        if (direction==0){ //No Movement (Staying)
          
        }else if ( (direction==1) && (y!=map[0].length-1) ){ //Down
          if ( !(map[y+1][x] instanceof Wolf) ){ //Not Breeding or Fighting
            if (map[y+1][x] instanceof Sheep){ //Eating a Sheep
              map[y][x].setHealth(health+map[y+1][x].getHealth());
            }
            map[y+1][x]=map[y][x];
            map[y][x]=null;
            
          } else if (map[y+1][x] instanceof Wolf ){ //Breeding or Fighting
            if ( ((((Wolf)map[y+1][x]).getHealth()) + (((Wolf)map[y][x]).getHealth()) >20) && (((Wolf)map[y+1][x]).getGender() != ((Wolf)map[y][x]).getGender()) ){ //Breeding: genders are different
              map[y+1][x].setHealth(health-10);
              map[y][x].setHealth(health-10);
              spawn(map, map.length, "Wolf",   babySheepHealth, babyWolfHealth);
            } else if (((Wolf)map[y+1][x]).getGender() == ((Wolf)map[y][x]).getGender()){ //Fighting: genders are the same
              Wolf.attack(map, y, x, y+1, x);
            }
          }
          
        } else if ((direction==2) && (y!=0)){ //Up
          if (!(map[y-1][x] instanceof Wolf) ){ //Not Breeding or Fighting
            if (map[y-1][x] instanceof Sheep){ //Eating a Sheep
              map[y][x].setHealth(health+map[y-1][x].getHealth());
            }
            map[y-1][x]=map[y][x];
            map[y][x]=null;
            
          } else if (map[y-1][x] instanceof Wolf ){ //Breeding or Fighting
            if ( ((((Wolf)map[y-1][x]).getHealth()) + (((Wolf)map[y][x]).getHealth()) >20) && (((Wolf)map[y-1][x]).getGender() != ((Wolf)map[y][x]).getGender()) ){ //Breeding: genders are different
              map[y-1][x].setHealth(health-10);
              map[y][x].setHealth(health-10);
              spawn(map, map.length, "Wolf",   babySheepHealth, babyWolfHealth);
            } else if (((Wolf)map[y-1][x]).getGender() == ((Wolf)map[y][x]).getGender()){ //Fighting: genders are the same
              Wolf.attack(map, y, x, y-1, x);
            }
          }
          
        } else if ((direction==3) && (x!=map[0].length-1)){ //Right
          if (!(map[y][x+1] instanceof Wolf) ){ //Not Breeding or Fighting
            if (map[y][x+1] instanceof Sheep){ //Eating a Sheep
              map[y][x].setHealth(health+map[y][x+1].getHealth());
            }
            map[y][x+1]=map[y][x];
            map[y][x]=null;
            
          } else if (map[y][x+1] instanceof Wolf ){ //Breeding or Fighting
            if ( ((((Wolf)map[y][x+1]).getHealth()) + (((Wolf)map[y][x]).getHealth()) >20) && (((Wolf)map[y][x+1]).getGender() != ((Wolf)map[y][x]).getGender()) ){ //Breeding: genders are different
              map[y][x+1].setHealth(health-10);
              map[y][x].setHealth(health-10);
              spawn(map, map.length, "Wolf", babySheepHealth, babyWolfHealth);
            }
            else if ( ((Wolf)map[y][x+1]).getGender() == ((Wolf)map[y][x]).getGender() ){ //Fighting: genders are the same
              Wolf.attack(map, y, x, y, x+1);
            }
          }
          
        } else if((direction==4) && (x!=0) ){ //Left
          if (!(map[y][x-1] instanceof Wolf) ){ //Not Breeding or Fighting
            if (map[y][x-1] instanceof Sheep){ //Eating a Sheep
              map[y][x].setHealth(health+map[y][x-1].getHealth());
            }
            map[y][x-1]=map[y][x];
            map[y][x]=null;
            
          } else if (map[y][x-1] instanceof Wolf ){ //Breeding or Fighting
            if ( ((((Wolf)map[y][x-1]).getHealth()) + (((Wolf)map[y][x]).getHealth()) >20) && (((Wolf)map[y][x-1]).getGender() != ((Wolf)map[y][x]).getGender()) ){ //Breeding: genders are different
              map[y][x-1].setHealth(health-10);
              map[y][x].setHealth(health-10);
              spawn(map, map.length, "Wolf",  babySheepHealth, babyWolfHealth);
            }else if (((Wolf)map[y][x-1]).getGender() == ((Wolf)map[y][x]).getGender()){ //Fighting: genders are the same
              Wolf.attack(map, y, x, y, x-1);
            }
          }
        }
      }
    }
  }
  
  
  /**
   * main
   * The main method that incorporates the other methods
   * @param String[] args
   * @return nothing
   */
  public static void main(String[] args){
    Scanner input=new Scanner(System.in);
    Random myRandom= new Random();
    
    int mapSize;
    int plantSpawnRate;
    int plantHealth, sheepHealth, wolfHealth;
    int babySheepHealth, babyWolfHealth;
    int numInitialPlants, numInitialSheep, numInitialWolves;
    int health;
    int delayTime;
    
    int numTurn=0;
    int spawnX, spawnY;
    int gender;
    int numPlants, numSheep, numWolves;
    boolean alreadyMoved;
    
    //Prompts user for initial configuration
    //Recommended settings generally run for a long time except in certain cases where the wolves die immediately
    System.out.print("Enter map size (recommended 17): ");
    mapSize=input.nextInt();
    System.out.print("Enter max plant spawns per turn (recommended 25): ");
    plantSpawnRate=input.nextInt();
    
    System.out.print("Enter plant health (recommended 10): ");
    plantHealth=input.nextInt();
    System.out.print("Enter sheep health (recommended 30): ");
    sheepHealth=input.nextInt();
    System.out.print("Enter wolf health (recommended 12): ");
    wolfHealth=input.nextInt();
    System.out.print("Enter new sheep spawn health (recommended 30): ");
    babySheepHealth=input.nextInt();
    System.out.print("Enter new wolf spawn health (recommended 12): ");
    babyWolfHealth=input.nextInt();
    
    System.out.print("Enter number of initial plants (recommended 30): ");
    numInitialPlants=input.nextInt();
    System.out.print("Enter number of initial sheep (recommended 30): ");
    numInitialSheep=input.nextInt();
    System.out.print("Enter number of initial wolves (recommended 3): ");
    numInitialWolves=input.nextInt();
    System.out.print("Enter delay time (in ms) (recommended 400): ");
    delayTime=input.nextInt();
    input.close();
    
    //Sets up the map
    Organism [][] map=new Organism [mapSize][mapSize];
    DisplayGrid grid= new DisplayGrid (map);
    
    //Initialises the two coordinate spawn variables
    spawnX = myRandom.nextInt(mapSize);
    spawnY = myRandom.nextInt(mapSize);
    
    //Initial Spawns
    //Initial Spawned Plants
    for (int i=0; i<numInitialPlants;i++){
      do{
        spawnX = myRandom.nextInt(mapSize);
        spawnY = myRandom.nextInt(mapSize);
      }while (((map[spawnY][spawnX] instanceof Plant))|| ((map[spawnY][spawnX] instanceof Sheep)) ||((map[spawnY][spawnX] instanceof Wolf))); //Checks for empty spaces
      map[spawnY][spawnX]=new Plant(plantHealth);
    }
    
    //Initial Spawned Sheep
    for (int i=0; i<numInitialSheep;i++){
      do{
        spawnX = myRandom.nextInt(mapSize);
        spawnY = myRandom.nextInt(mapSize);
        gender =myRandom.nextInt(2);
      } while(((map[spawnY][spawnX] instanceof Plant))|| ((map[spawnY][spawnX] instanceof Sheep)) || ((map[spawnY][spawnX] instanceof Wolf))); //Checks for empty spaces
      map[spawnY][spawnX]=new Sheep(sheepHealth, gender, false);
    }
    
    //Initial Spawned Wolves
    for (int i=0; i< numInitialWolves;i++){
      do {
        spawnX = myRandom.nextInt(mapSize);
        spawnY = myRandom.nextInt(mapSize);
        gender =myRandom.nextInt(2);
      }while (((map[spawnY][spawnX] instanceof Plant))||((map[spawnY][spawnX] instanceof Sheep)) || ((map[spawnY][spawnX] instanceof Wolf))); //Checks for empty spaces
      map[spawnY][spawnX]=new Wolf(wolfHealth, gender, false);
    }
    
    do{
      //Resets species counters, adds one to the turn counter
      numTurn+=1;
      numPlants=0;
      numSheep=0;
      numWolves=0;
      
      //Adds a buffer
      try{ Thread.sleep(delayTime); }catch(Exception e) {};
      grid.refresh();
      
      //Spawns plants each turn
      for (int i=0; i<plantSpawnRate;i++){
        spawnX = myRandom.nextInt(mapSize);
        spawnY = myRandom.nextInt(mapSize);
        if ( (!(map[spawnY][spawnX] instanceof Plant))&& (!(map[spawnY][spawnX] instanceof Sheep)) && (!(map[spawnY][spawnX] instanceof Wolf)) ){ //Checks for empty spaces
          map[spawnY][spawnX]=new Plant(plantHealth);
        }
      }
      
      //Moves sheep and wolves
      for(int i = 0; i<map.length;i++){        
        for(int j = 0; j<map[0].length;j++){
          if ((map[i][j] instanceof Sheep)){
            health=map[i][j].getHealth();
            alreadyMoved=((Sheep)map[i][j]).getAlreadyMoved();
            move(map,mapSize, map[i][j], j,i, health, alreadyMoved, babySheepHealth, babyWolfHealth);
            
          } else if(map[i][j] instanceof Wolf){
            health=map[i][j].getHealth();
            alreadyMoved=((Wolf)map[i][j]).getAlreadyMoved();
            move(map, mapSize, map[i][j], j,i, health, alreadyMoved, babySheepHealth, babyWolfHealth);
          }
        }
      }
      
      for(int i = 0; i<map.length;i++){        
        for(int j = 0; j<map[0].length;j++){
          //Decays the health of each species every turn
          if ((map[i][j] instanceof Plant) || (map[i][j] instanceof Sheep) || (map[i][j] instanceof Wolf)){
            health=map[i][j].getHealth();
            map[i][j].setHealth(health-1);
            
            //Dereferences the dead objects
            if (map[i][j].getHealth()==0){
              map[i][j]=null;
            }
          }
          
          //Counts each species
          if( map[i][j] instanceof Plant){
            numPlants+=1;
          }else if ( map[i][j] instanceof Sheep){
            numSheep+=1;
          } else if ( map[i][j] instanceof Wolf){
            numWolves+=1;
          }
          
          //Allows every object to move again
          if (map[i][j] instanceof Sheep){
            ((Sheep)map[i][j]).setAlreadyMoved(false);
          } else if (map[i][j] instanceof Wolf)
            ((Wolf)map[i][j]).setAlreadyMoved(false);
        }
      }  
      
      //Ouputs the turn number and number of each species
      System.out.println("----------");
      System.out.println("Turn: "+numTurn);
      System.out.println("Plants: "+numPlants); //Note: First turn will intentionally have a higher plant count than the initial number due to the first turn already spawning plants
      System.out.println("Sheep: "+numSheep);
      System.out.println("Wolves: "+numWolves);
      
    }while ((numPlants !=0) && (numSheep!=0) && (numWolves !=0)); //Program ends when a species becomes extinct
    
    //Refreshes one last time
    try{ Thread.sleep(delayTime); }catch(Exception e) {}; 
    grid.refresh();
    System.out.println("Simulation Ended");
  }
}