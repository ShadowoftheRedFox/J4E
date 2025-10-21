package org.atilla.cytraining.user;

import org.atilla.cytraining.subjects.Subject;
import org.atilla.cytraining.subjects.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void certifyUserGlobally(int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(user -> {
            user.setGloballyCertified(true);
            userRepository.save(user);
        });
    }

    public void removeGlobalCertification(int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(user -> {
            user.setGloballyCertified(false);
            userRepository.save(user);
        });
    }

    public void certifyUserInSubject(int userId, int subjectId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
        if (optionalUser.isPresent() && optionalSubject.isPresent()) {
            User user = optionalUser.get();
            Subject subject = optionalSubject.get();
            user.addCertifiedSubject(subject);
            user.setCreator(true);
            userRepository.save(user);
        } else {
            // Gérer le cas où l'utilisateur ou la matière n'existe pas
            throw new RuntimeException("Utilisateur ou matière non trouvé");
        }
    }

    public void removeSubjectCertification(int userId, int subjectId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);

        if (optionalUser.isPresent() && optionalSubject.isPresent()) {
            User user = optionalUser.get();
            Subject subject = optionalSubject.get();
            user.removeCertifiedSubject(subject);
            if (user.getCertifiedSubjects().size() == 1 && !user.isAdmin() && !user.isGloballyCertified()) {
                user.setCreator(false);
            }
            userRepository.save(user);
        } else {
            // Gérer le cas où l'utilisateur ou la matière n'existe pas
            throw new RuntimeException("Utilisateur ou matière non trouvé");
        }
    }
}
