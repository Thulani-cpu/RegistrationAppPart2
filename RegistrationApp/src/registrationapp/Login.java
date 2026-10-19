
/**
 *
 * @author Thulani Mgidi
 */

package registrationapp;

 public class Login{

  

    // Credentials saved once registered
    private String savedUsername;
    private String savedPassword;

    // Checks if username meets the format rules
    public String checkUserName(String username) {

        if(username.contains("_") && username.length() <= 5){
            return "Username successfully captured.";
        }else{
            return "Username is not correctly formatted, please ensure it contains an underscore and is no more than five characters in length.";
        }
    }

    // Checks if the password is strong enough
    public String checkPasswordComplexity(String password) {

        boolean specialChar = false;
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean capitalLetter = password.matches(".*[A-Z].*");
        boolean properLength = password.length() >= 8;
        
        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (!Character.isLetterOrDigit(ch)) {
                specialChar = true;
            }
        }

        if(properLength && hasNumber && capitalLetter && specialChar){
            return "Password successfully captured.";
        }else {
             return "Password is not correctly formatted, please ensure it contains at least 8 characters, a capital letter, a number and a special character.";
        }
    }

    // Checks for vaild South African number with the +27 prefix
    public String checkCellPhoneNumber(String phoneNumber) {

        if(phoneNumber.matches("^\\+27[0-9]{9}$")){
            return "Cellphone number successfully added.";
        }else{
            return "Cellphone number incorrectly formatted or does not contain international code.";
        }
    }

    // Registeres the user only if all three details are vaild
    public boolean registerUser(String username, String password, String phoneNumber) {
        
         boolean validUser = checkUserName(username).equals("Username successfully captured.");

        boolean validPass = checkPasswordComplexity(password).equals("Password successfully captured.");

        boolean validPhone = checkCellPhoneNumber(phoneNumber).equals("Cellphone number successfully added.");

        if (validUser && validPass && validPhone) {
             
            savedUsername = username;
            savedPassword = password;

            return true;
        }
        return false;
    }

    // Comfirms the entered details match teh saved ones
    public boolean loginUser(String username, String password) {

        return username.equals(savedUsername) && password.equals(savedPassword);
    }

    // builds the message shown after the login attempt
    public String returnLoginStatus(String firstName, String lastName, boolean validLogIN) {

        if (validLogIN) {
            return "Welcome Back: " + firstName + " " + lastName +
                   ", It is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
