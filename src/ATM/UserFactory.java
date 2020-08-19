package ATM;

class UserFactory {

    /**
     * Creates a new User with the given type.
     */

    User getUser(String type, String username, String password){
        if(type == null){
            return null;
        }
        if(type.equalsIgnoreCase("Employee")){
            return new Employee(username, password);

        } else if(type.equalsIgnoreCase("Client")){
            return new Client(username, password);

        } else if (type.equalsIgnoreCase("GrandLeader")){
            return new GrandLeader(username, password);

        } else {
            return null;
        }
    }

}
