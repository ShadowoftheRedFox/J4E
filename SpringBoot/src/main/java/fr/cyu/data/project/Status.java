package fr.cyu.data.project;

public enum Status {
    ONGOING("On going"),
    FINISHED("Finished"),
    CANCELED("Canceled");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static Status fromValue(String v) {
        for (Status s : values())
            if (s.name().equals(v))
                return s;
        throw new IllegalArgumentException("Unknown status: " + v);
    }
}