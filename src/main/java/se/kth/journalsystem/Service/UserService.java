package se.kth.journalsystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.journalsystem.DTO.UserDTO;
import se.kth.journalsystem.model.Patient;
import se.kth.journalsystem.model.User;
import se.kth.journalsystem.repository.PatientRepository;
import se.kth.journalsystem.repository.UserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public UserService(UserRepository userRepository, PatientRepository patientRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
    }

    // Hasha lösenord med SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public UserDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username).map(this::convertToDTO).orElse(null);
    }

    // Registrera ny användare
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setRole(userDTO.getRole());

        // Hasha lösenordet innan det sparas
        String hashedPassword = hashPassword(userDTO.getPassword());
        user.setPassword(hashedPassword);

        // Om rollen är PATIENT, skapa en ny patient och koppla den till användaren
        if ("PATIENT".equalsIgnoreCase(userDTO.getRole())) {
            Patient patient = new Patient();
            patient.setName(userDTO.getUsername()); // Använd username som default name
            patient.setBirthDate(userDTO.getPatientBirthDate());
            patient.setContactInfo(userDTO.getPatientContactInfo());

            Patient savedPatient = patientRepository.save(patient);
            System.out.println("Saved Patient: " + savedPatient.getId());


            // Koppla patient till användaren
            user.setPatient(savedPatient);
        }

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    // Kontrollera lösenord vid inloggning
    public boolean checkPassword(String rawPassword, String storedPassword) {
        // Hasha det råa lösenordet och jämför med det sparade hashade lösenordet
        String hashedPassword = hashPassword(rawPassword);
        return hashedPassword.equals(storedPassword);
    }

    public UserDTO updateUser(Long id, UserDTO userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setRole(userDetails.getRole());

            // Uppdatera lösenord om det finns
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                String hashedPassword = hashPassword(userDetails.getPassword());
                existingUser.setPassword(hashedPassword);
            }

            User updatedUser = userRepository.save(existingUser);
            return convertToDTO(updatedUser);
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setPassword(user.getPassword()); // Lägg till detta enbart om det behövs för inloggning
        dto.setPatientId(user.getPatient() != null ? user.getPatient().getId() : null);
        dto.setPractitionerId(user.getPractitioner() != null ? user.getPractitioner().getId() : null);
        return dto;
    }
}
