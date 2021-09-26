import java.util.Random;

class Ecosystem {
    static final int MAP_SIZE = 17;
    static final int DELAY_TIME_IN_MS = 400;
    
    static final int INITIAL_PLANTS = 30;
    static final int INITIAL_SHEEP = 30;
    static final int INITIAL_WOLVES = 6;
    static final int PLANTS_EACH_TURN = 25;
    static final int BREED_WHEN_HEALTH_SUM_OVER = 20;

    int turn = 0;
    int numPlants = 0;
    int numSheep = 0;
    int numWolves = 0;

    Organism[][] terrain;
    OrganismFactory factory;
    DisplayGrid display;

    Ecosystem() {
        this.terrain = new Organism[MAP_SIZE][MAP_SIZE];
        this.display = new DisplayGrid(this.terrain);
        this.factory = new OrganismFactory();
    }

    void initialSpawn() {
        for (int i = 0; i < INITIAL_PLANTS; i++) {
            spawn(factory.createOrganism(OrganismType.Plant));
        }
        for (int i = 0; i < INITIAL_SHEEP; i++) {
            spawn(factory.createOrganism(OrganismType.Sheep));
        }
        for (int i = 0; i < INITIAL_WOLVES; i++) {
            spawn(factory.createOrganism(OrganismType.Wolf));
        }
    }

    void spawn(Organism organism) {
        Random rand = new Random();
        int row = rand.nextInt(terrain.length);
        int col = rand.nextInt(terrain[0].length);
        if (terrain[row][col] == null) {
            terrain[row][col] = organism;
        }
    }

    void run() {
        initialSpawn();
        display.refresh();

        do {
            // Spawns plants each turn
            for (int i = 0; i < PLANTS_EACH_TURN; i++) {
                spawn(factory.createOrganism(OrganismType.Plant));
            }

            // Moves sheep and wolves
            for (int i = 0; i < terrain.length; i++) {
                for (int j = 0; j < terrain[0].length; j++) {
                    if ((terrain[i][j] instanceof Animal)) {
                        move((Animal) terrain[i][j], i, j);
                    }
                }
            }

            display.refresh();
            endTurn();
            System.out.println("----------");
            System.out.println("Turn: " + turn);
            System.out.printf("%d plants, %d sheep, %d wolves\n", numPlants, numSheep, numWolves);
            try {
                Thread.sleep(DELAY_TIME_IN_MS);
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted!");
            }
        } while (!hasEnded());
        display.refresh();
        System.out.println("Simulation Ended");
    }

    Pair<Integer, Integer> getRandDirection() {
        Random rand = new Random();
        int direction = rand.nextInt(5);
        switch (direction) {
            case 1:
                return new Pair<>(1, 0);
            case 2:
                return new Pair<>(0, 1);
            case 3:
                return new Pair<>(-1, 0);
            case 4:
                return new Pair<>(0, -1);
            default:
                return new Pair<>(0, 0);
        }
    }

    boolean inBounds(Pair<Integer, Integer> posn) {
        return 0 <= posn.first && posn.first < terrain.length
                && 0 <= posn.second && posn.second < terrain[0].length;
    }

    public void move(Animal currAnimal, int row, int col) {
        if (currAnimal.getAlreadyMoved()) {
            return;
        }

        Pair<Integer, Integer> direction = getRandDirection();
        if (direction.first == 0 && direction.second == 0) {
            return;
        }
        Pair<Integer, Integer> dest = new Pair<>(direction.first + row, direction.second + col);
        if (!inBounds(dest)) {
            return;
        }

        // Sheep Movement
        if (currAnimal instanceof Sheep) {
            currAnimal.setAlreadyMoved(true); //Marks down that it doesn't need to move again this turn

            if (terrain[dest.first][dest.second] instanceof Plant) {
                currAnimal.setHealth(currAnimal.getHealth() + terrain[dest.first][dest.second].getHealth());
                terrain[dest.first][dest.second] = terrain[row][col];
                terrain[row][col] = null;
            } else if (terrain[dest.first][dest.second] instanceof Sheep) {
                Sheep currSheep = (Sheep) currAnimal;
                Sheep other = (Sheep) terrain[dest.first][dest.second];

                if (currSheep.getHealth() + other.getHealth() > BREED_WHEN_HEALTH_SUM_OVER
                        && currSheep.getGender() != other.getGender()) {
                    spawn(factory.createOrganism(OrganismType.Sheep));
                }
            }
        }

        // Wolf Movement
        if (currAnimal instanceof Wolf) {
            currAnimal.setAlreadyMoved(true);

            if (terrain[dest.first][dest.second] instanceof Plant || terrain[dest.first][dest.second] instanceof Sheep) {
                currAnimal.setHealth(currAnimal.getHealth() + terrain[dest.first][dest.second].getHealth());
                terrain[dest.first][dest.second] = terrain[row][col];
                terrain[row][col] = null;
            } else if (terrain[dest.first][dest.second] instanceof Wolf) { //Breeding or Fighting
                Wolf currWolf = (Wolf) currAnimal;
                Wolf other = (Wolf) terrain[dest.first][dest.second];

                if (currWolf.getGender() == other.getGender()) {
                    currWolf.attack(other);
                } else if (currWolf.getHealth() + other.getHealth() > BREED_WHEN_HEALTH_SUM_OVER) {
                    spawn(factory.createOrganism(OrganismType.Wolf));
                }
            }
        }
    }

    public void endTurn() {
        numPlants = 0;
        numSheep = 0;
        numWolves = 0;

        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[0].length; j++) {
                Organism organism = terrain[i][j];
                if (organism == null) {
                    continue;
                }

                // Decays the health of each species every turn
                organism.setHealth(organism.getHealth() - 1);
                if (organism.getHealth() == 0) {
                    terrain[i][j] = null;
                    continue;
                }

                // Counts each species
                if (organism instanceof Plant) {
                    numPlants += 1;
                } else if (organism instanceof Sheep) {
                    numSheep += 1;
                } else if (organism instanceof Wolf) {
                    numWolves += 1;
                }

                if (organism instanceof Animal) {
                    ((Animal) organism).setAlreadyMoved(false);
                }
            }
        }
        turn++;
    }

    public boolean hasEnded() {
        return numPlants * numSheep * numWolves == 0;
    }
}