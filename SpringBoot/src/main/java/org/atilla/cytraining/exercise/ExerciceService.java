package org.atilla.cytraining.exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciceService {
    @Autowired
    ExerciseRepository<Exercise> exerciseRepository;

    public List<? extends Exercise> getUnvalidated() {
        List<? extends Exercise> list = exerciseRepository.findAll();

        for (int i = 0; i < list.size(); i++) {
            Exercise element = list.get(i);
            if (element.getValidated() == true) {
                list.remove(i);
                i--;
            }
        }
        return list;
    }

    public List<? extends Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    public Exercise addExercice(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public void deleteExercice(Integer id) {
        exerciseRepository.deleteById(id);
    }

    public void ValidateExercise(Integer id) {
        Optional<Exercise> ex = exerciseRepository.findById(id);
        ex.ifPresent(exo -> {
            exo.setValidated(true);
            exerciseRepository.save(exo);
        });
    }

    public void UnvalidateExercise(Integer id) {
        Optional<Exercise> ex = exerciseRepository.findById(id);
        ex.ifPresent(exo -> {
            exo.setValidated(false);
            exerciseRepository.save(exo);
        });
    }
}
