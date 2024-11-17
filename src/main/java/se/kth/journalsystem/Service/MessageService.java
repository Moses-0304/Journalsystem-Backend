package se.kth.journalsystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.journalsystem.DTO.MessageDTO;
import se.kth.journalsystem.model.Message;
import se.kth.journalsystem.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
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

    public MessageDTO createMessage(MessageDTO messageDTO) {
        Message message = convertFromDTO(messageDTO);
        message.setSentDate(LocalDateTime.now());
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

    // Konverteringsmetoder
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

    private Message convertFromDTO(MessageDTO dto) {
        Message message = new Message();
        message.setContent(dto.getContent());
        message.setRead(dto.isRead());
        // Sätt sender och receiver baserat på externa tjänster om tillgängliga
        // message.setSender(userService.findById(dto.getSenderId()).orElseThrow(...));
        // message.setReceiver(userService.findById(dto.getReceiverId()).orElseThrow(...));
        return message;
    }
}
