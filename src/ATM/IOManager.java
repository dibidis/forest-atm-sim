package ATM;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

class IOManager {

    private String directory = (
            System.getProperty("user.dir")
                    //Gives path up until the file containing phase1, this may differ for different computers
                    + "/phase2/src/IO_Files/");

    private File config = new File( directory + "config.ser");

    /**
     * Reads config.ser to set up the bank. Returns a bank containing Users, Accounts, MoneyManager, TransactionManagers
     */
    Bank readConfig() {

        MoneyManager moneyManager = new MoneyManager();
        HashMap<String, TransactionService> transactionManagerHashMap = new HashMap<>();
        HashMap<String, User> userHashMap = new HashMap<>();

        if (checkForEmptyConfig()) {
            String defaultUsername = "Manager";
            String defaultPassword = "@";
            setUpNewUser(userHashMap, " " + defaultUsername + " " + defaultPassword, "BankManager");
            setUpNewAccountHolder(userHashMap, transactionManagerHashMap, " King @", "GrandLeader");

        }
        else {
            try {
                // Reading the object from a file
                FileInputStream file = new FileInputStream(config);
                ObjectInputStream in = new ObjectInputStream(file);

                // Method for deserialization of object
                Bank bank = (Bank) in.readObject();

                in.close();
                file.close();

                return bank;

            } catch (IOException ex) {
                System.out.println("Read IOException is caught: " + ex);
            } catch (ClassNotFoundException ex) {
                System.out.println("ClassNotFoundException is caught");
            }

        }
        return new Bank(userHashMap, transactionManagerHashMap, moneyManager);
    }

    /**
     * Checks to see if config.ser is empty.
     * @return true if empty
     */
    private boolean checkForEmptyConfig() {
        return config.length() == 0;
    }

    /**
     * Overwrites config.ser with updated info for the bank using serialization.
     */
    void writeConfig(Bank bank) {

        try {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(config);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(bank);

            out.close();
            file.close();
        }

        catch(IOException ex) {
            System.out.println("Write IOException is caught: " + ex);
        }

    }

    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                                   Report Reading
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */
    boolean [] getIsLow() {
        boolean [] isLow = new boolean[4];
        Path path = Paths.get(directory + "alerts.txt");
        try (BufferedReader fileInput = Files.newBufferedReader(path)) {
            String line = fileInput.readLine();
            while (line != null) {
                int value = extractBills(line);
                processValue(isLow, value);
                line = fileInput.readLine();
            }
        } catch (IOException e) {
            System.out.println("ATM is already well-stocked.");
        }
        return isLow;
    }

    int[] readDeposit() {
        int [] billsDeposited = new int[4];

        Path path = Paths.get(directory + "deposits.txt");
        try (BufferedReader fileInput = Files.newBufferedReader(path)) {
            String line = fileInput.readLine();
            int i = 0;
            while (line != null && i < 4) {
                int amountBills = extractAmount(line);
                billsDeposited[i] = amountBills;
                line = fileInput.readLine();
                i++;
            }
        } catch (IOException e) {
            System.out.println("Could not deposit. Please check deposits.txt file.");
        }
        return billsDeposited;
    }

    private int extractAmount(String line) {
        String[] content = line.split(":");
        return Integer.valueOf(content[1].substring(1, content[1].length() - 1));
    }

    void deleteDeposits() {
        File file = new File(directory + "deposits.txt");
        file.delete();
    }
    void deleteAlerts() {
        File file = new File(directory + "alerts.txt");
        file.delete();
    }

    private int extractBills(String line) {
        String[] content = line.split(" ");
        return Integer.valueOf(content[0].substring(1));
    }

    private void setUpNewUser(HashMap<String, User> userHashMap, String data, String type) {
        String[] userData = data.split(" ");
        if (type.equals("BankManager")) {
            userHashMap.put(userData[1], new BankManager(userData[1], userData[2]));
        }
    }

    private void setUpNewAccountHolder(HashMap<String, User> userHashMap, HashMap<String, TransactionService> thhm,
                                       String data, String type){
        String[] userData = data.split(" ");
        if (type.equals("GrandLeader")) {
            GrandLeader leader = new GrandLeader(userData[1], userData[2]);
            leader.getTreeAccount().setTreesPlanted(50);
            userHashMap.put(userData[1], leader);
            thhm.put(leader.username, new TransactionManager(leader.username));
        }
    }

    private void processValue(boolean[] isLow, int value) {
        if (value == 50){
            isLow[0] = true;
        } else if (value == 20){
            isLow[1] = true;
        } else if (value == 10){
            isLow[2] = true;
        } else if (value == 5){
            isLow[3] = true;
        }
    }

    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                                   Report Writing
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */
    void sendAlert(int[] money){
        File file = new File(directory + "alerts.txt");
        try {file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            String toWrite = prepareAlertContent(money);
            out.print(toWrite);} catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String prepareAlertContent(int[] money) {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < money.length; i++){
            if (money[i] < 20){
                if (i == 0){
                    s.append("$50 Bills are low: ");
                } else if (i == 1){
                    s.append("$20 Bills are low: ");
                } else if (i == 2){
                    s.append("$10 Bills are low: ");
                } else if (i == 3) {
                    s.append("$5 Bills are low: ");
                }
                s.append(money[i]);
                s.append("\n");
            }
        }

        return s.toString();
    }

    void sendOutgoing(String sender, String receiver, double amount){
        int outgoingId = 0;
        boolean foundEmptyFile = false;
        String fileName = "outgoing" + outgoingId + ".txt";
        File file = new File(directory + fileName);
        while (!foundEmptyFile) {
            try {
                if (file.createNewFile()) {
                    System.out.println(fileName + " created in IO_Files");
                    foundEmptyFile = true;
                } else {
                    outgoingId++;
                    fileName = "outgoing" + outgoingId + ".txt";
                    file = new File(directory + fileName);
                }
            }
            catch (IOException e) {
                System.out.println("Error writing to outgoing.txt file");
            }
        }

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            String toWrite = sender + " has sent $" + amount + " to: " + receiver;
            out.print(toWrite);
        } catch (IOException e) {
            System.out.println("Error writing to outgoing.txt file");
        }
    }


}