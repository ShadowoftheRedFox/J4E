package fr.cyu.data.employee;

public enum Permission {
    EDIT_USER("Peut modifier les utilisateurs"),
    CREATE_USER("Peut créer un utilisateur"),
    DELETE_USER("Peut effacer les utilisateurs"),

    EDIT_PROJECT("Peut modifier les projets"),
    CREATE_PROJECT("Peut créer un projet"),
    DELETE_PROJECT("Peut effacer les projets"),

    EDIT_DEPARTMENT("Peut modifier les départements"),
    CREATE_DEPARTMENT("Peut créer un département"),
    DELETE_DEPARTMENT("Peut effacer les départements"),

    EDIT_RANK("Peut modifier les rôles"),
    CREATE_RANK("Peut créer un rôle"),
    DELETE_RANK("Peut effacer les rôles"),

    EDIT_PERMISSION("Peut modifier les permissions"),
    CREATE_PERMISSION("Peut créer une permission"),
    DELETE_PERMISSION("Peut effacer les permissions"),

    VIEW_REPORT("Peut générer un rapport"),
    VIEW_PAYSLIP("Peut générer une feuille de paie");

    private String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public static Permission fromValue(String v) {
        for (Permission p : values())
            if (p.name().equals(v))
                return p;
        throw new IllegalArgumentException("Unknown permission: " + v);
    }
}
