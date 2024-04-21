import java.io.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Convert {
    private final static Scanner scanner = new Scanner(System.in);

    private static String generateRandomString(int length, List<Character> characters) {
        Random random = new Random();
        return random.ints(length, 0, characters.size())
                .mapToObj(characters::get)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    private static void log(String email, String passw) {
        byte[] data = ("\nEmail: " + email + "\nPassword: " + passw).getBytes();
        String str = "\n------------------";

         try(FileOutputStream fileOutputStream = new FileOutputStream("log.txt",true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)){

            byteArrayOutputStream.write(data);
            byteArrayOutputStream.writeTo(fileOutputStream);
            dataOutputStream.writeUTF(str);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void encrypt(String email, String passw) {
        List<Character> specialChars = Arrays.asList('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+', '=', '[', ']', '{', '}', '|', '\\', ';', ':', '\'', '"', ',', '.', '/', '<', '>', '?', '~');

        StringBuilder encrypted = new StringBuilder();
        passw.chars()
                .mapToObj(ch -> (char) ch)
                .forEach(ch -> {
                    char randomChar = specialChars.get(new Random().nextInt(specialChars.size()));
                    char randomCase = new Random().nextBoolean() ? Character.toUpperCase(randomChar) : Character.toLowerCase(randomChar);
                    encrypted.append(randomCase);

                    int numLetters = new Random().nextInt(5) + 1;
                    List<Character> letters = new ArrayList<>();
                    letters.addAll(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));
                    String randomLetters = generateRandomString(numLetters, letters);
                    encrypted.append(randomLetters.toLowerCase()); // Append lowercase random letters
                });

        System.out.println("Encrypted pass: " + encrypted.toString());
        log(email, encrypted.toString());
    }

    public static void main(String... args) {
        String email, password;
        do {
            System.out.print("Enter your email (or 'stop' to exit): ");
            email = scanner.nextLine();
            if (email.equalsIgnoreCase("stop")) break;

            System.out.print("Enter your password (or 'stop' to exit): ");
            password = scanner.nextLine();
            if (password.equalsIgnoreCase("stop")) break;

            encrypt(email, password);
        } while (true);
    }
}
