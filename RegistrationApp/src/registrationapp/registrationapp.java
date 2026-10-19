
package registrationapp;


/**
 *
 * @author Thulani Mgidi
 */
import java.util.Scanner;

public class registrationapp{

    public static void main(String[] args) {

        try (Scanner input = new Scanner(System.in)) {
            Login login = new Login();
            
            String firstName;
            String lastName;
            String username;
            String password;
            String phoneNumber;
            
            // Gather the user's registration details
            System.out.print("Please type in your first name: ");
            firstName = input.nextLine();
            
            System.out.print("Please type in your last name: ");
            lastName = input.nextLine();
            
            System.out.print("Enter username (maximum 5 characters, must include a  '_'): ");
            username = input.nextLine();
            
            System.out.print("Enter password (must have 8 characters or more, at least 1 number, a capital letter and a special character): ");
            password = input.nextLine();
            
            System.out.print("Enter your cellphone number (must start with South African code '+27'): ");
            phoneNumber = input.nextLine();
            
            // Print the validation feedback for each field
            System.out.println(login.checkUserName(username));
            System.out.println(login.checkPasswordComplexity(password));
            System.out.println(login.checkCellPhoneNumber(phoneNumber));
            
            // Outcome of the registration attempt
            boolean registered = login.registerUser(username, password, phoneNumber);
            
            if (registered) {
                System.out.println("You have been registered.");
            } else {
                System.out.println("Registration was unsuccessful");
            }
            
// LOGIN OR SIGN-IN STEP

boolean authenticated = false;

if (registered){

    System.out.println("\n< Login >");

    System.out.print("Enter your username: ");
    String loginUser = input.nextLine();

    System.out.print("Enter your password: ");
    String loginPass = input.nextLine();

    // Records whether the login worked
    authenticated = login.loginUser(loginUser, loginPass);

    System.out.println(login.returnLoginStatus(firstName, lastName, authenticated));
}

// QUICKCHAT: only reachable once the user has logged in successfully

if (authenticated) {

    System.out.println("Welcome to QuickChat");

    int option;

    do {

        System.out.println("""
                                   1) Compose and send messages
                                   2) View recently sent messages
                                   3) Saved messages
                                   4) Exit""");
        
        System.out.print("Select option: ");

        option = input.nextInt();
        input.nextLine();

        switch (option) {

            case 1:

                System.out.print("Number of message to send: ");

                int number = input.nextInt();
                input.nextLine();

                for (int i = 1; i <= number; i++) {
                    
                    System.out.println("\nMessage " + i);
                    
                    System.out.print("Recipient's cell number (must start with South African code '+27'): ");
                    String recipient = input.nextLine();
                    
                    System.out.print("Message (Fewer than 250 characters): ");
                    String text = input.nextLine();
                    
                    Message message = new Message(recipient, text, username);
                    
                    System.out.println("Choose an option:");
                    System.out.println("1) Send Message");
                    System.out.println("0) Discard Message");
                    System.out.println("2) Save message for later");
                    System.out.print("Option: ");
                    
                    int choice = input.nextInt();
                    input.nextLine();
                    
                    System.out.println(message.SentMessage(choice));
                }

                break;

            case 2:

                System.out.println("This feature is not available yet");

                break;

            case 3:
                
                int displayOption;
                
                do {

                    System.out.println("""
                                                           < SAVED MESSAGE REPORTS >
                                                           1) Show Sender and Recipient
                                                           2) Show the Longest Message
                                                           3) Find a message by ID
                                                           4) Find message by recipient
                                                           5) Remove a message using its hash
                                                           6) Show the complete report
                                                           7) Go Back""");

                    System.out.print("Select option: ");

                    displayOption = input.nextInt();
                    input.nextLine();

                    switch(displayOption)
                    {
                        case 1 -> System.out.println(Message.displayStoredSenderAndRecipients());

                        case 2 -> System.out.println(Message.displayLongestMessage());

                        case 3 -> {
                            System.out.print("Message ID to search for: ");

                            String searchID = input.nextLine();

                            System.out.println(Message.searchMessageID(searchID));
                        }
                        
                        case 4 -> {
                            System.out.print("Recipient to search for: ");

                            String searchRecipient = input.nextLine();

                            System.out.println(Message.searchUsingPhoneNum(searchRecipient));
                        }
                        
                        case 5 -> {
                            System.out.print("Hash of the message to delete: ");

                            String hash = input.nextLine();

                            System.out.println(Message.deleteUsingHash(hash));
                        }
                        
                        case 6 -> System.out.println(
                                Message.displayStoredMessage()
                        );
                        
                        case 7 -> System.out.println(
                                "Going back to Main Menu..."
                        );
                        
                        default -> System.out.println("That option is not available.");
                    }
                    
                } while(displayOption != 7);
                
                break;            
                
                
                
            case 4:
                
                System.out.println("Goodbye.");
                
            default:

                System.out.println("That option is not available.");
        }

    } while (option != 4);

} else {

    System.out.println("Invalid Username or Password.");
}       }
    }
}
