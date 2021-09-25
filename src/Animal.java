abstract class Animal extends Organism {
    private final Gender gender;
    private boolean alreadyMoved;

    Animal(int health, Gender gender, boolean alreadyMoved) {
        super(health);
        this.gender = gender;
        this.alreadyMoved = alreadyMoved;
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