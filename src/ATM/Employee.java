package ATM;

/**
 * Employees can manage accounts, and they themselves have accounts.
 */

class Employee extends Client implements WorksHere{

    Employee(String username, String password){
        super(username, password);
    }
}