package fr.cyu.jee.beans.enums;

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
}
