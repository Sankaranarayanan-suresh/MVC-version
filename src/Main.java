import com.dunzo.core.model.user.Admin;
import com.dunzo.core.model.user.Customer;
import com.dunzo.core.model.user.Rider;
import com.dunzo.database.model.JobDatabase;
import com.dunzo.database.model.UserDatabase;
import com.dunzo.database.repository.JobDataManager;
import com.dunzo.database.repository.NotificationManager;
import com.dunzo.database.repository.NotificationManger;
import com.dunzo.database.repository.UserDataManager;
import com.dunzo.database.utils.Utils;
import com.dunzo.userinterface.AdminUI;
import com.dunzo.userinterface.CustomerUI;
import com.dunzo.userinterface.LoginUI;
import com.dunzo.userinterface.RiderUI;

public class Main {
    public static void main(String[] args) {
        NotificationManager notificationManager = new NotificationManger();
        JobDatabase jobDatabase =  JobDatabase.getInstance();
        UserDatabase userDatabase = UserDatabase.getInstance();
        UserDataManager userDataManager = new UserDataManager(userDatabase, notificationManager);
        JobDataManager jobDataManager = new JobDataManager(jobDatabase, userDataManager, notificationManager, userDataManager);
        LoginUI login = new LoginUI(userDataManager, jobDataManager);
        Customer customer =  userDataManager.addNewCustomer("Sankar","9876543210","1","sankar@gmail.com",jobDataManager);
        Admin admin = userDataManager.addNewAdmin("shakthi","7890123456","shakthi","shakthi@gmail.com",userDataManager,userDataManager);
        Rider rider  = userDataManager.addNewRider("shakthi","8765432190","shakthi","shakthi@gmail.com","600008",jobDataManager);
        mainFunction:
        while (true) {
            System.out.println("\t\t\tWelcome to Dunzo application.");
            System.out.println("1.Customer\n2.Rider\n3.Admin\n4.Exit");
            int user = Utils.getInteger();
            int userPreference;
            switch (user) {
                case 1:
                    System.out.println("1.Sign-Up\n2.Sign-In\n3.Go back to main menu");
                    userPreference = Utils.getInteger();
                    CustomerUI customerUI = null;
                    if (userPreference == 1) {
                        try {
                            customerUI = new CustomerUI((Customer) login.signUp("customer"), jobDataManager);
                            System.out.println("\n\nSignUp Successful!!");
                        }catch (RuntimeException e){
                            System.out.println(e.getMessage());
                            break ;
                        }
                    } else if (userPreference == 2) {
                        try {
                            customerUI = new CustomerUI((Customer) login.signIn("customer"), jobDataManager);
                            System.out.println("\n\nSignIn Successful!!");
                        }catch (RuntimeException e){
                            System.out.println(e.getMessage());
                            break ;
                        }
                    } else {
                        break;
                    }
                    customerUI.showMenu();
                    break;
                case 2:
                    System.out.println("1.Apply for rider\n2.Sign-In\n3.Go back to main menu");
                    userPreference = Utils.getInteger();
                    RiderUI riderUI = null;
                    if (userPreference == 1) {
                        try {
                            riderUI = new RiderUI((Rider) login.signUp("rider"));
                        }catch (RuntimeException e){
                            System.out.println(e.getMessage());
                        }
                    } else if (userPreference == 2) {
                        try {
                            riderUI = new RiderUI((Rider) login.signUp("rider"));
                        }catch (RuntimeException e){
                            System.out.println(e.getMessage());
                        }
                    } else {
                        break;
                    }
                    assert riderUI != null;
                    riderUI.showMenu();
                    break;
                case 3:
                    System.out.println("1.Sign-In\n2.Go back to main menu");
                    userPreference = Utils.getInteger();
                    AdminUI adminUI = null;
                    if (userPreference == 1) {
                        try {
                            adminUI = new AdminUI((Admin) login.signIn("admin"));
                        }catch (RuntimeException e){
                            System.out.println(e.getMessage());
                        }
                    }else {
                        break;
                    }
                    assert adminUI != null;
                    adminUI.showMenu();
                    break;
                case 4:
                    break mainFunction;
                default:
                    System.out.println("Select any given option");
                    break;
            }

        }
    }
}

