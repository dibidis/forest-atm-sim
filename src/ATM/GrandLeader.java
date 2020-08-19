package ATM;

/**
 * Our bank's GRAND LEADER.
 */

class GrandLeader extends Client implements OwnsThePlace {

    GrandLeader(String username, String password){
        super(username, password);
    }
}
