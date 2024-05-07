import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class BankInfoFinder {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the first three digits of your bank account number: ");
        String accountPrefix = scanner.nextLine();
        scanner.close();

        try {
            String bankData = downloadBankData("https://ewib.nbp.pl/plewibnra?dokNazwa=plewibnra.txt");
            String[] bankInfo = findBankInfo(bankData, accountPrefix);
            if (bankInfo != null) {
                System.out.println("Your bank's abbreviated number is: " + bankInfo[0]);
                System.out.println("Your bank's name is: " + bankInfo[1]);
            } else {
                System.out.println("No bank found with the given account prefix.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String downloadBankData(String urlString) throws Exception {
        URL url = new URL(urlString);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        reader.close();

        return stringBuilder.toString();
    }

    public static String[] findBankInfo(String bankData, String accountPrefix) {
        String[] lines = bankData.split("\n");
        for (String line : lines) {
            String[] parts = line.split(";");
            if (parts.length > 1 && parts[0].equals(accountPrefix)) {
                return new String[]{parts[0], parts[1]};
            }
        }
        return null;
    }
}
