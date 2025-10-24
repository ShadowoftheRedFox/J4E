package fr.cyu.jee.beans.enums;

public enum Rank {
     ADMIN("Administrateur"),
     MANAGER("Manageur"),
     PROJECT_LEADER("Chef de projet"),
     DEPARTMENT_LEADER("Chef de département");

     private String rank;

     Rank(String rank) {
     this.rank = rank;
     }

     public String getRank() {
     return rank;
     }

    public static Rank fromValue(String v) {
        for (Rank p : values()) if (p.rank.equals(v)) return p;
        throw new IllegalArgumentException("Unknown rank: " + v);
    }
}
