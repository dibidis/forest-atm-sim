package ATM;

public class BankManager extends User implements WorksHere, CreatesUsers {

    BankManager(String username, String password){
        super(username, password);
    }
}