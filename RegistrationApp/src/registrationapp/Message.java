package registrationapp;

/**
 *
 * @author Thulani Mgidi
 */
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public final class Message {

    // message list count
    private static int totalMessages = 0;

    private final String messageID;
    private final String recipient;
    private final String messageText;
    private final String messageHash;
    private final String sender;
    
    // Arrays that hold the messages the user has sent
    private static String[] sentMessageIDs = new String[100];
    private static String[] sentRecipients = new String[100];
    private static String[] sentMessageHashes = new String[100];
    private static String[] sentMessages = new String[100];
    
    private static int sentCounter = 0;

    
    // Arrays that hold the messages saved for later
    private static String[] storedMessages = new String[100];
    private static String[] storedRecipients = new String[100];
    private static String[] storedMessageHashes = new String[100];
    private static String[] storedMessageIDs = new String[100];
    private static String[] storedSenders = new String [100];
        
    private static int storedCounter = 0;
        
    // Arrays that holds the messages the user chose to discard
    private static String[] disregardedMessages = new String[100];
    private static int disregardedCounter = 0;
    

    // Builds a new message form the recipient, the text an the sender 
    public Message(String recipient, String messageText, String sender) {

        this.recipient = recipient;
        this.messageText = messageText;
        this.sender = sender;

        messageID = generateMessageID();

        totalMessages++;

        messageHash = createMessageHash();
    }
    
    
    // Produces a random 10-Digit ID for the message
    private String generateMessageID() {

        Random random = new Random();

        String id = "";

        for (int i = 0; i < 10; i++) {

            id += random.nextInt(10);
        }

        return id;
    }
    
    
    // Verifies the message does not got to over 250 characters
    public String checkMessageLength() {

        if (messageText.length() <= 250) {

            return "Message ready to send.";

        } else {

            int excess = messageText.length() - 250;

            return "Message exceeds 250 characters by "
                    + excess;
        }
    }


    // Verifies the recipient's number format(+27 followed by 9 digits)
    public boolean checkRecipientCell() {

        return recipient.matches("^\\+27[0-9]{9}$");
    }
    
    
    // Builds the message hash from the ID, count the first and last words
    public String createMessageHash() {

        String[] words = messageText.split(" ");

        String firstWord = words[0].toUpperCase();

        String lastWord = words[words.length - 1].toUpperCase();

        return messageID.substring(0, 2)
                + ":"
                + totalMessages
                + ":"
                + firstWord
                + lastWord;
    }


    // Check the messages and, if vaild, reports a succesfull send
    public String sendMessage() {

        if (!checkRecipientCell()) {

            return "Cell number is incorrectly formatted.";
        }

        if (!checkMessageLength().equals("Message ready to send.")) {
            return checkMessageLength();
        } else {
        }

        return """
               Message successfully sent.
               Message ID: %s
               Message Hash: %s
               Recipient: %s
               Message: %s
               """
                .formatted(messageID, messageHash,recipient, messageText);
    }


    // Gives back how many message have been created
    public static int returnTotalMessages() {

        return totalMessages;
    }
    
    public String SentMessage(int choice) {

    if (!checkRecipientCell()) {
        return "Cell number is incorrectly formatted.";
    }
    

    if (!checkMessageLength().equals("Message ready to send.")) {
        return checkMessageLength();
    }

    switch (choice) { 
        case 1 -> {
            // Record the message in the sent arrays
            sentMessages[sentCounter] = messageText;
            sentMessageHashes[sentCounter] = messageHash;
            sentMessageIDs[sentCounter] = messageID;
            sentRecipients[sentCounter] = recipient;
            
            sentCounter++;
            
            return sendMessage();
            }

        case 0 -> {
            // Record the message in the discarded array
            disregardedMessages[disregardedCounter] = messageText;
            
            disregardedCounter++;
            
            return "Press 0 to delete the message";
            }

        case 2 -> {
            storedMessages[storedCounter] = messageText;

            storedRecipients[storedCounter] = recipient;

            storedMessageHashes[storedCounter] = messageHash;

            storedMessageIDs[storedCounter] = messageID;
            
            storedSenders[storedCounter] = sender;

            storedCounter++;

            saveMessageToJSON();
            
            return "Message successfully stored.";
            }

        default -> {
            return "Invalid option.";
            }
    }
}
    
// WRITE THE MESSAGE TO A JSON FILE

public void saveMessageToJSON() {

    try {

        try (FileWriter writer = new FileWriter("messages.json", true)) {
            writer.write(
                    "{\n" +
                            "\"MessageID\": \"" + messageID + "\",\n" +
                                    "\"MessageHash\": \"" + messageHash + "\",\n" +
                                            "\"Recipient\": \"" + recipient + "\",\n" +
                                                    "\"Message\": \"" + messageText + "\"\n" +
                                                            "}\n\n");
        }

    } catch (IOException e) {

        System.out.println(
                "Error while writing message to file."
        );
    }
}

public static String displayStoredSenderAndRecipients(){
    
    // Make sure something has been saved first   
    if(storedCounter == 0){
            return "No stored messages found.";
        }
        String result = "";
        
        for(int i = 0; i < storedCounter; i++){
            result += "From: " + storedSenders[i] + "|| To: " + storedRecipients[i];
        }
        return result;
    }

    public static String displayLongestMessage(){
        
        // Make sure something has been saved first
        if(storedCounter == 0){
            return "No stored messages found.";
            
        }
        // Starting point for comparison
        String longest = storedMessages[0];
        
        // Begin at 1 so the first message isn't comparing itself
        for(int i = 1; i < storedCounter; i++){
            
            if(storedMessages[i].length()>longest.length()){
                longest = storedMessages[i];
                
            }
        }
        
        // Report the longest message
        return "Longest Message: " + longest;
    }
    
    public static String searchMessageID(String searchID){
        
        // Search for the ID and return the matching message
        for(int i = 0; i < storedCounter; i++){
            if(storedMessageIDs[i].equals(searchID)){
                
                return "Recipient: " + storedRecipients[i] + "\nMessage: " + storedMessages[i];
            }
            
        }
        // No match for the ID
        return "Message ID not found";
    }
    
    public static String searchUsingPhoneNum(String recipient){
        
        String result = "";
        // Search messages by phone number
        for(int i = 0; i < storedCounter; i++){
            if(storedRecipients[i].equals(recipient)){
                
                result += "Recipient: " + storedRecipients[i] + "\nMessage: " + storedMessages[i]; 
            }
        }
        
        if(result.equals("")){
            return "No messages found for recipient";
        }
        
        return result;
    }
    
    public static String deleteUsingHash(String hash){
        
        // Walk through the saved messages
        for(int i = 0; i < storedCounter; i++){
            // Move later entries one place left to fill the gap
            if(storedMessageHashes[i].equals(hash)){
                for(int j = i; j < storedCounter - 1; j++){
                    
                    // Repeat for each of the saved-message arrays
                    storedMessages[j] = storedMessages[j + 1];
                    storedRecipients[j] = storedRecipients[j + 1];
                    storedSenders[j] = storedSenders[j + 1];
                    storedMessageHashes[j] = storedMessageHashes[j + 1];
                    
                    
                }
                storedCounter --;
                
                return"Message successfully deleted.";
            }
        }
        
        return "Message hash not found.";
    }
    
    public static String displayStoredMessage(){
        // Nothing has been saved
        if(storedCounter == 0){
            return "No stored messages.";
        }
        
        String display = "";
        
        for(int i = 0; i < storedCounter; i++){
            
            display += """
                       
                       ============
                       Sender: """ + storedSenders[i] +
                    "\nRecipient: " + storedRecipients[i]+
                    "\nMessage ID: " + storedMessageIDs[i]+
                    "\nMessage hash: " + storedMessageHashes[i]+
                    "\nMessage: " + storedMessages[i]+
                    "\n===========";
        }
        return display;
    }
    
    //<
    // Extra method that makes the testing possible
    //>
    
    //Getters (for testing), because JUnit tests cannot reach private fields
    
    public static String[] getStoredMessageHashes(){// returns the saved message hashes
        return storedMessageHashes;
    }
    
    public static String[] getStoredMessageIDs(){// returns the saved message IDs
        return storedMessageIDs;
    }
     
    public static String[] getDisregardedMessages(){// returns the discarded messages
        return disregardedMessages;
    }
    
    public static String[] getSentMessages(){// returns the sent messages
        return sentMessages;
    }
    
    
    
    // Start each test form empty arrays and zeroed counters
    
    public static void resetArrays()
{
     storedRecipients = new String[100];

    storedSenders = new String[100];

    storedMessageIDs = new String[100];

    storedMessageHashes = new String[100];

    disregardedMessages = new String[100];

    sentMessages = new String[100];

    sentMessageIDs = new String[100];

    sentMessageHashes = new String[100];

    sentRecipients = new String[100];

    storedMessages = new String[100];

    disregardedCounter = 0;

    totalMessages = 0;
    
    sentCounter = 0;

    storedCounter = 0;
}
    
}
