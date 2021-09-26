abstract class Animal extends Organism {
    private final Gender gender;
    private boolean alreadyMoved = false;

    Animal(int health, Gender gender) {
        super(health);
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean getAlreadyMoved() {
        return alreadyMoved;
    }

    public void setAlreadyMoved(boolean alreadyMoved) {
        this.alreadyMoved = alreadyMoved;
    }
}