package se.kth.journalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.kth.journalsystem.model.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderId(Long senderId);
    List<Message> findByReceiverId(Long receiverId);

    @Query("SELECT m FROM Message m WHERE " +
            "(m.sender.id = :user1 AND m.receiver.id = :user2) " +
            "OR (m.sender.id = :user2 AND m.receiver.id = :user1) " +
            "ORDER BY m.sentDate ASC")
    List<Message> findMessagesBetweenUsers(@Param("user1") Long user1, @Param("user2") Long user2);
}
