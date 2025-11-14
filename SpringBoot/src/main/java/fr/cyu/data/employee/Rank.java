package fr.cyu.data.employee;

public enum Rank {
    ADMIN("Administrateur"),
    MANAGER("Manageur"),
    PROJECT_LEADER("Chef de projet"),
    DEPARTMENT_LEADER("Chef de département"),
    SENIOR("Senior"),
    JUNIOR("Junior");

    private String rank;

    Rank(String rank) {
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    public static Rank fromValue(String v) {
        for (Rank p : values())
            if (p.name().equals(v))
                return p;
        throw new IllegalArgumentException("Unknown rank: " + v);
    }
}
