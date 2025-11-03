package fr.cyu.data.project;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<Project> findByName(String name);
}
