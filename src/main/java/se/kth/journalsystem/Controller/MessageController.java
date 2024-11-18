package se.kth.journalsystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.kth.journalsystem.DTO.MessageDTO;
import se.kth.journalsystem.Service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // Get all messages
    @GetMapping
    public List<MessageDTO> getAllMessages() {
        return messageService.getAllMessages();
    }

    // Get a specific message by ID
    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable Long id) {
        MessageDTO messageDTO = messageService.getMessageById(id);
        return messageDTO != null ? ResponseEntity.ok(messageDTO) : ResponseEntity.notFound().build();
    }

    // Get all messages by sender ID
    @GetMapping("/sender/{senderId}")
    public List<MessageDTO> getMessagesBySenderId(@PathVariable Long senderId) {
        return messageService.getMessagesBySenderId(senderId);
    }

    // Get all messages by receiver ID
    @GetMapping("/receiver/{receiverId}")
    public List<MessageDTO> getMessagesByReceiverId(@PathVariable Long receiverId) {
        return messageService.getMessagesByReceiverId(receiverId);
    }

    // Get all messages between two users
    @GetMapping("/conversation")
    public List<MessageDTO> getMessagesBetweenUsers(@RequestParam Long user1, @RequestParam Long user2) {
        return messageService.getMessagesBetweenUsers(user1, user2);
    }

    // Create a new message
    @PostMapping
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO) {
        MessageDTO createdMessage = messageService.createMessage(messageDTO);
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    // Delete a message
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        boolean deleted = messageService.deleteMessage(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
