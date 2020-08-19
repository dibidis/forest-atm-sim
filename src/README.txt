

This README is divided into four sections: UML Diagram, IO_Files, Set-Up, and Notes on Implementation.


### UML DIAGRAM

Please find our four UML diagrams in the "phase1" directory. We made four so as to simplify high-level understanding
of the system. The diagrams contain the information described below.

"transactions.pdf" -> the hierarchy among our Transaction classes
"users.pdf"        -> the hierarchy among our User classes
"accounts.pdf"     -> the hierarchy among our Account classes
"design.pdf"       -> a high-level view of our system. Note that, while the User, Account, and Transaction classes are
                      present, their hierarchies are not

The diagrams were generated using the SketchIt and PlantUML plug-ins. What follows below is an explanation of the
symbols associated with variables, methods, and class relationships. Note that the symbols for variables and methods
have the same shapes and colours, with the difference being that method symbols are filled-in, while variable symbols
are not. We have opted not to include private methods.

METHODS AND VARIABLES
public          -> green circle
package-private -> blue triangle
protected       -> yellow diamond
private         -> red square

CLASS RELATIONSHIPS
inheritance     -> arrow ending in a triangle
composition     -> arrow ending in a diamond


### IO_FILES

Our I/O files can be found in "phase1/src/IO_Files" or "phase1/IO_Templates". The "IO_Files" directory is used by the
program, while the "IO_Templates" directory is where our templates are stored.

In "IO_Templates" you'll find "deposits.txt" and "config.ser".

As per the specification, "deposits.txt" is read when a Client deposits money into the ATM. The file is set up to model
a Client depositing five $50 bills, two $20 bills, and so on. Note that when a Client deposits money, they are not
prompted for an amount. Instead the deposit file is read and then cleared. For subsequent deposits, the file must be
rewritten, or perhaps copied and pasted from the "IO_Templates" directory.

The second file, "config.ser", is how our system stores information about its Users, Accounts, Transactions, and bills.

If you wish to simulate a new bank, you can do so by running a blank config file. This will generate a system with only
a default BankManager, username "Manager" and password "@", who can then set their own password and begin adding
Clients.

Two other types of I/O file are produced in the running of our system. These are "alerts.txt" and "outgoing.txt". These
function in accordance with the specification and are created in the "IO_Files" directory as needed.


### Set-Up

Run the Starter class's main method to begin the program. This constructs pre-existing Users and their information
from the config file. The existing users have the following information.

TYPE              USERNAME           PASSWORD
GrandLeader       King               @
BankManager       Manager            @
Client            BobSmith           123
Client            AlicePots          456
Employee          TomScary           789

You will be prompted on the console to log into the system.

After logging in, you'll be presented with a list of actions you may perform. See "Notes on Implementation" for details.


### Notes on Implementation

Before getting into specifics, the thinking behind our bank's unusual features bears explaining. TreeBank is the bank
for an eco-cult centered around a Grand Leader. The members of the bank have special asset accounts named Tree Accounts.
When they make transactions from these accounts, they tribute a dollar to the Grand Leader and they add a tree to their
personal forest, which they can view in their user options. To continue the banking session after viewing your forest,
simply exit the Forest Display window. Their social standing depends in part on the size of their forest. This is why,
when a User logs onto the system, they have the options to see the members of the bank with the largest forests. Those
members are exemplars worth emulating.

Our Users are typed according to the interfaces they implement. These are CreatesUsers, WorksHere, AccountHolder, and
OwnsThePlace. For example, the Bank Manager implements CreatesUsers and WorksHere, while the Grand Leader implements
AccountHolder and OwnsThePlace. Some of the more interesting methods that come with implementing OwnsThePlace are the
abilities to levy a tax from all AccountHolders and to exile Users from the bank entirely.

Here are brief descriptions of the major object classes in our system:

Account            -> Abstract ancestor to all types of bank accounts
User               -> Abstract ancestor to all types of user
TransactionManager -> Specific to each AccountHolder, keeps track of their transaction history
MoneyManager       -> Manages the physical money in the ATM
Bank               -> A large class that organizes Users and their TransactionManagers into HashMaps.

Below we've included a number of comments we'd like to make concerning implementation details.

The specification mentioned that the BankManager should set the time in the system. However, we've designed the system
to use the computer's system clock, which makes setting the time unnecessary.

Our system expects the midnight reboot to be external.

Withdrawals from and deposits to the ATM are not considered reversible transactions.

One final note is that our system does not handle money amounts greater than 1000000000.


### In Closing

We hope this README has been detailed enough to get you up to speed on our system.

Thanks very much for your time, and we hope you have a great day!