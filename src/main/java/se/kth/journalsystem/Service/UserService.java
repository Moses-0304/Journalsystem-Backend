package se.kth.journalsystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.journalsystem.DTO.UserDTO;
import se.kth.journalsystem.model.User;
import se.kth.journalsystem.repository.UserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        User user = convertFromDTO(userDTO);

        // Hasha lösenordet innan det sparas
        String hashedPassword = hashPassword(userDTO.getPassword());
        user.setPassword(hashedPassword);

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


    private User convertFromDTO(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setRole(dto.getRole());
        // Låt lösenordet hanteras av createUser eller uppdateringslogiken
        user.setPassword(dto.getPassword());
        return user;
    }

}
