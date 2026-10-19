
/**
 *
 * @author Thulani Mgidi
 */

import registrationapp.Message;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class TestMessage {
    
    // Start every test with empty arrays and zeroed counters
    @Before
    public void setUp() {
        
        Message.resetArrays();
    }
 
    @Test
    public void testRecipientCorrectlyFormatted() {
 
        Message message =
                new Message(
                        "+27718693002",
                        "Hi Mike, can you join us for dinner tonight?","J_y"
                );
 
        assertEquals(
                true,
                message.checkRecipientCell()
        );
    }
 
    @Test
    public void testRecipientIncorrectlyFormatted() {
 
        Message message =
                new Message(
                        "08575975889",
                        "Hi Keegan, did you recieve the payment?","J_y"
                );
 
        assertEquals(
                false,
                message.checkRecipientCell()
        );
    }
 
    @Test
    public void testMessageLengthSuccess() {
 
        Message message =
                new Message(
                        "+27718693002",
                        "Hi Mike, can you join us for dinner tonight?", "J_y"
                );
 
        assertEquals(
                "Message ready to send.",
                message.checkMessageLength()
        );
    }
 
    @Test
    public void testMessageLengthFail() {
 
        String longMessage = "A".repeat(260);
 
        Message message =
                new Message(
                        "+27831234567",
                        longMessage, "J_y"
                );
 
        assertEquals(
                "Message exceeds 250 characters by 10",
                message.checkMessageLength()
        );
    }
 
    @Test
    public void testMessageHashCreated() {
 
        Message message =
                new Message(
                        "+27718693002",
                        "Hi Mike, can you join us for dinner tonight?", "J_y"
                );
 
        String hash = message.createMessageHash();
 
        assertEquals(
                true,
                hash.contains("HITONIGHT")
        );
    }
 
    @Test
    public void testSendMessageSuccessfully() {
 
        Message message =
                new Message(
                        "+27718693002",
                        "Hi Mike, can you join us for dinner tonight?", "J_y"
                );
 
        String result = message.SentMessage(1);
 
        assertEquals(
                true,
                result.contains("Message successfully sent.")
        );
    }
 
    @Test
    public void testDisregardMessage() {
 
        Message message =
                new Message(
                        "+27718693002",
                        "Hi Mike, can you join us for dinner tonight?", "J_y"
                );
 
        assertEquals(
                "Press 0 to delete the message",
                message.SentMessage(0)
        );
    }
 
    @Test
    public void testStoreMessage() {
 
        Message message =
                new Message(
                        "+27718693002",
                        "Hi Mike, can you join us for dinner tonight?", "J_y"
                );
 
        assertEquals(
                "Message successfully stored.",
                message.SentMessage(2)
        );
    }
 
    @Test
    public void testInvalidRecipientWhenSending() {
 
        Message message =
                new Message(
                        "08575975889",
                        "Hi Keegan, did you recieve the payment?", "J_y"
                );
 
        assertEquals(
                "Cell number is incorrectly formatted.",
                message.SentMessage(1)
        );
    }
}
 
    
    