package fr.cyu.controller.dto;

public class SessionDTO {
    private Integer session_id = 0;

    /**
     * @return the session_id. Always not null.
     */
    public Integer getSession_id() {
        return session_id == null ? 0 : session_id;
    }

    /**
     * @param session_id the session_id to set
     */
    public void setSession_id(Integer session_id) {
        this.session_id = session_id == null ? 0 : session_id;
    }
}
