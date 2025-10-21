package org.atilla.cytraining.MCQ;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

import org.atilla.cytraining.dto.ResponseDTO;

/**
 * Data Transfer Object for MCQ creation.
 */
public class McqDTO {

    @NotBlank(message = "Le titre est obligatoire.")
    private String title;

    @NotBlank(message = "La description est obligatoire.")
    private String description;

    @Size(min = 1, message = "Ajoutez au moins une réponse.")
    private List<ResponseDTO> responses = new ArrayList<>();

    // Getters and setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ResponseDTO> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseDTO> responses) {
        this.responses = responses;
    }

    @Override
    public String toString() {
        return "McqDTO{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", responses=" + responses +
                '}';
    }

}
