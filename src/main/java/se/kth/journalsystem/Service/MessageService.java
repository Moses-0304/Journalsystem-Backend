package se.kth.journalsystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.journalsystem.DTO.MessageDTO;
import se.kth.journalsystem.model.Message;
import se.kth.journalsystem.model.User;
import se.kth.journalsystem.repository.MessageRepository;
import se.kth.journalsystem.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public List<MessageDTO> getAllMessages() {
        return messageRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public MessageDTO getMessageById(Long id) {
        return messageRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public List<MessageDTO> getMessagesBySenderId(Long senderId) {
        return messageRepository.findBySenderId(senderId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<MessageDTO> getMessagesByReceiverId(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<MessageDTO> getMessagesBetweenUsers(Long user1, Long user2) {
        return messageRepository.findMessagesBetweenUsers(user1, user2)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MessageDTO createMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setRead(false); // Nytt meddelande är som standard oläst
        message.setSentDate(LocalDateTime.now());

        // Hämta avsändare och mottagare från databasen baserat på ID
        if (messageDTO.getSenderId() != null) {
            User sender = userRepository.findById(messageDTO.getSenderId())
                    .orElseThrow(() -> new RuntimeException("Sender not found with ID: " + messageDTO.getSenderId()));
            message.setSender(sender);
        }

        if (messageDTO.getReceiverId() != null) {
            User receiver = userRepository.findById(messageDTO.getReceiverId())
                    .orElseThrow(() -> new RuntimeException("Receiver not found with ID: " + messageDTO.getReceiverId()));
            message.setReceiver(receiver);
        }

        // Spara meddelandet
        Message savedMessage = messageRepository.save(message);
        return convertToDTO(savedMessage);
    }

    public boolean deleteMessage(Long id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private MessageDTO convertToDTO(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setSentDate(message.getSentDate());
        dto.setRead(message.isRead());
        dto.setSenderId(message.getSender() != null ? message.getSender().getId() : null);
        dto.setReceiverId(message.getReceiver() != null ? message.getReceiver().getId() : null);
        return dto;
    }
}
