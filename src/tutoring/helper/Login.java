package tutoring.helper;



import java.util.ArrayList;
import tutoring.entity.*;

/**
 *
 * @author pres_is
 */
public class Login
{

    private String username = null;
    private String password = null;
    private boolean loginStatus = false;
    private boolean usernameStatus = false;
    private boolean passwordStatus = false;
    private String loginFeedback = "Please enter your 'username' and 'password'";
    private ArrayList<User> list = null;
    private String realPassword = null;
    private Role role = null;

    public Login(String username, String password)
    {
        this.username = username;
        this.password = password;
        DatabaseHelper.open();
        list = (ArrayList<User>) User.selectAllUser("", DatabaseHelper.getConnection());
        DatabaseHelper.close();
        login();
    }

    public Login()
    {
    }
    
    /***

    * This is a sample Java Comment

    *

    */
    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public final boolean login()
    {
        usernameStatus = lookUpUser();
        passwordStatus = lookUpPassword();

        if ((usernameStatus == true) && (passwordStatus == true))
        {
            loginStatus = true;
            return loginStatus;
        } else
        {
            loginStatus = false;
            return loginStatus;
        }

    }

    public boolean isLoggedIn()
    {
        return loginStatus;
    }

    public Role getRole()
    {
        if (loginStatus)
        {
            return role;
        }

        return role; //should be null if none
    }

    private boolean lookUpUser()
    {
        boolean userFound = false;

        for (int i = 0; i < list.size(); i++)
        {
            if (username.equals(list.get(i).getUserName()))
            {
                userFound = true;
                realPassword = list.get(i).getPassword();
                role = list.get(i).getRoleID();
                return userFound;
            }
        }

        return userFound;
    }

    private boolean lookUpPassword()
    {
        boolean passwordFound = false;

        if (password.equals(realPassword))
        {
            passwordFound = true;
            return passwordFound;
        }

        return passwordFound;
    }

    public String loginFeedback()
    {
        if (loginStatus == true)
        {
            loginFeedback = "Username and passwords match.";
            return loginFeedback;
        } else if ((usernameStatus == false) && (passwordStatus == true))
        {
            loginFeedback = "The username that you entered was not found.";
            return loginFeedback;
        } else if (passwordStatus == false && (usernameStatus == true))
        {
            loginFeedback = "The password that you entered is invalid.";
            return loginFeedback;
        } else if (loginStatus == false)
        {
            loginFeedback = "Please recheck your typing and try again. Remember, your password is case sensitive.";
            return loginFeedback;
        }

        return loginFeedback;
    }
}
