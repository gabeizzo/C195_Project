package utilities;

/** The WelcomeUserLambda displays the Welcome, (User)! message to the top of the Main Menu.
 * The welcome message will display with the user currently logged in's username in the message for example: Welcome, test! or Welcome, admin!
 */
public interface WelcomeUserLambda {
    void welcomeUser();
}
