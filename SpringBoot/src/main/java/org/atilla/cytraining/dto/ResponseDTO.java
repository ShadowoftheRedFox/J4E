package org.atilla.cytraining.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for MCQ responses.
 */
public class ResponseDTO {

    @NotBlank(message = "La réponse ne peut pas être vide.")
    private String answer;

    private boolean correct;

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public boolean isCorrect() { return correct; }
    public void setCorrect(boolean correct) { this.correct = correct; }


    @Override
    public String toString() {
        return "ResponseDTO{" +
                "answer='" + answer + '\'' +
                ", correct=" + correct +
                '}';
    }
}