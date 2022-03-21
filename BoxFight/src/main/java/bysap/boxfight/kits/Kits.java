package bysap.boxfight.kits;

public enum Kits {
    TANK("Tank"),
    HEXE("Hexe"),
    NINJA("Ninja");

    private final String name;

    private Kits(final String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
