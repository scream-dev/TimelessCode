/*
 * TimelessCode - Temporal Cipher by Scream [dev]
 * github.com/screamdev
 * Nope, don't even try
 */
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

// ScreamDev is my short name
public class TimelessCode {
    private static final String ALPHABET = buildAlphabet();
    private static final int ALPHABET_SIZE = ALPHABET.length();

    // I'll personally record a video of me on my knees for you if you can crack my code)
    private static String buildAlphabet() {
        Set<Integer> set = new TreeSet<>();
        // U+0020-U+007F
        addRange(set, 0x0020, 0x007F);
        // U+00A0-U+00FF
        addRange(set, 0x00A0, 0x00FF);
        // U+0100-U+017F
        addRange(set, 0x0100, 0x017F);
        // U+0192
        set.add(0x0192);
        // U+02C6-U+02C7
        addRange(set, 0x02C6, 0x02C7);
        // U+02D8-U+02DD
        addRange(set, 0x02D8, 0x02DD);
        // U+037E
        set.add(0x037E);
        // U+0384-U+038A
        addRange(set, 0x0384, 0x038A);
        // U+038C
        set.add(0x038C);
        // U+038E-U+03A1
        addRange(set, 0x038E, 0x03A1);
        // U+03A3-U+03CE
        addRange(set, 0x03A3, 0x03CE);
        // U+0400-U+045F
        addRange(set, 0x0400, 0x045F);
        // U+0490-U+0491
        addRange(set, 0x0490, 0x0491);
        // U+2010-U+2015
        addRange(set, 0x2010, 0x2015);
        // U+2018-U+201F
        addRange(set, 0x2018, 0x201F);
        // U+2020-U+2022
        addRange(set, 0x2020, 0x2022);
        // U+2026
        set.add(0x2026);
        // U+2030
        set.add(0x2030);
        // U+2032-U+2033
        addRange(set, 0x2032, 0x2033);
        // U+2039-U+203A
        addRange(set, 0x2039, 0x203A);
        // U+20A4
        set.add(0x20A4);
        // U+20AC
        set.add(0x20AC);
        // U+2105
        set.add(0x2105);
        // U+2113
        set.add(0x2113);
        // U+2116
        set.add(0x2116);
        // U+2122
        set.add(0x2122);
        // U+2126
        set.add(0x2126);
        // U+2190-U+2193
        addRange(set, 0x2190, 0x2193);
        // U+2194-U+2195
        addRange(set, 0x2194, 0x2195);
        // U+2202
        set.add(0x2202);
        // U+2206
        set.add(0x2206);
        // U+220F
        set.add(0x220F);
        // U+2211-U+2212
        addRange(set, 0x2211, 0x2212);
        // U+2215
        set.add(0x2215);
        // U+221A
        set.add(0x221A);
        // U+221E
        set.add(0x221E);
        // U+222B
        set.add(0x222B);
        // U+2248
        set.add(0x2248);
        // U+2260
        set.add(0x2260);
        // U+2261
        set.add(0x2261);
        // U+2264-U+2265
        addRange(set, 0x2264, 0x2265);
        // U+2302
        set.add(0x2302);
        // U+2320-U+2321
        addRange(set, 0x2320, 0x2321);
        // U+2500-U+254B
        addRange(set, 0x2500, 0x254B);
        // U+2550-U+2573
        addRange(set, 0x2550, 0x2573);
        // U+2580-U+2595
        addRange(set, 0x2580, 0x2595);
        // U+25A0-U+25A1
        addRange(set, 0x25A0, 0x25A1);
        // U+25AC-U+25AD
        addRange(set, 0x25AC, 0x25AD);
        // U+25B2
        set.add(0x25B2);
        // U+25BA
        set.add(0x25BA);
        // U+25BC
        set.add(0x25BC);
        // U+25C4
        set.add(0x25C4);
        // U+25CB
        set.add(0x25CB);
        // U+25CF-U+25D0
        addRange(set, 0x25CF, 0x25D0);
        // U+25D4
        set.add(0x25D4);
        // U+25E6
        set.add(0x25E6);
        // U+FB01-U+FB02
        addRange(set, 0xFB01, 0xFB02);

        StringBuilder sb = new StringBuilder();
        for (int cp : set) {
            sb.appendCodePoint(cp);
        }
        return sb.toString();
    }

    private static void addRange(Set<Integer> set, int from, int to) {
        for (int i = from; i <= to; i++) {
            set.add(i);
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            interactiveMode();
        } else {
            cliMode(args);
        }
    }

    // scream-dev.ru
    private static void interactiveMode() {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        System.out.println("=== TimelessCode ===");
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1 - Encrypt text");
            System.out.println("2 - Decrypt text");
            System.out.println("3 - Encrypt file");
            System.out.println("4 - Decrypt file");
            System.out.println("5 - Exit");
            System.out.print("> ");
            String choice = scanner.nextLine().trim();
            if (choice.equals("5")) {
                System.out.println("Goodbye.");
                break;
            }
            if (!Arrays.asList("1", "2", "3", "4").contains(choice)) {
                System.out.println("Invalid choice, try again.");
                continue;
            }

            BigInteger key = null;
            while (key == null) {
                System.out.print("Enter secret key (1 to 20 digits): ");
                String keyStr = scanner.nextLine().trim();
                if (!keyStr.matches("\\d{1,20}")) {
                    System.out.println("Key must be digits only, length 1 to 20.");
                    continue;
                }
                BigInteger candidate = new BigInteger(keyStr);
                // Проверка размера ключа для операций шифрования
                if ((choice.equals("1") || choice.equals("3")) && candidate.compareTo(BigInteger.valueOf(ALPHABET_SIZE)) < 0) {
                    System.out.println("Key is too small for encryption. Minimum is " + ALPHABET_SIZE + " (alphabet size).");
                    continue;
                }
                key = candidate;
            }

            try {
                switch (choice) {
                    case "1":
                        System.out.println("Enter text to encrypt:");
                        String plain = scanner.nextLine();
                        String enc = encrypt(plain, key);
                        System.out.println("Encrypted:");
                        System.out.println(enc);
                        System.out.println("By Scream [dev]");
                        break;
                    case "2":
                        System.out.println("Enter encrypted text (dates separated by commas):");
                        String cipher = scanner.nextLine();
                        String dec = decrypt(cipher, key);
                        System.out.println("Decrypted:");
                        System.out.println(dec);
                        System.out.println("By Scream [dev]");
                        break;
                    case "3": {
                        System.out.print("Enter input file path: ");
                        String inFile = scanner.nextLine().trim();
                        System.out.print("Enter output file path: ");
                        String outFile = scanner.nextLine().trim();
                        String content = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(inFile)), "UTF-8");
                        String encrypted = encrypt(content, key);
                        java.nio.file.Files.write(java.nio.file.Paths.get(outFile), encrypted.getBytes("UTF-8"));
                        System.out.println("File encrypted successfully.");
                        System.out.println("By Scream [dev]");
                        break;
                    }
                    case "4": {
                        System.out.print("Enter input file path: ");
                        String inFile = scanner.nextLine().trim();
                        System.out.print("Enter output file path: ");
                        String outFile = scanner.nextLine().trim();
                        String content = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(inFile)), "UTF-8");
                        String decrypted = decrypt(content, key);
                        java.nio.file.Files.write(java.nio.file.Paths.get(outFile), decrypted.getBytes("UTF-8"));
                        System.out.println("File decrypted successfully.");
                        System.out.println("By Scream [dev]");
                        break;
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    // Nope, don't even try
    private static void cliMode(String[] args) {
        String keyStr = null;
        String operation = null;
        String text = null;
        String inputFile = null;
        String outputFile = null;
        String inputData = null;

        int i = 0;
        while (i < args.length) {
            String arg = args[i];
            switch (arg) {
                case "-h":
                case "--help":
                    printHelp();
                    return;
                case "-e":
                case "--encrypt":
                    operation = "encrypt";
                    i++;
                    if (i < args.length && !args[i].startsWith("-")) {
                        text = args[i];
                    } else {
                        i--;
                    }
                    break;
                case "-d":
                case "--decrypt":
                    operation = "decrypt";
                    i++;
                    if (i < args.length && !args[i].startsWith("-")) {
                        text = args[i];
                    } else {
                        i--;
                    }
                    break;
                case "-k":
                case "--key":
                    i++;
                    if (i < args.length) keyStr = args[i];
                    else {
                        System.err.println("Error: --key requires a value.");
                        System.exit(1);
                    }
                    break;
                case "-i":
                case "--input":
                    i++;
                    if (i < args.length) inputFile = args[i];
                    else {
                        System.err.println("Error: --input requires a file path.");
                        System.exit(1);
                    }
                    break;
                case "-o":
                case "--output":
                    i++;
                    if (i < args.length) outputFile = args[i];
                    else {
                        System.err.println("Error: --output requires a file path.");
                        System.exit(1);
                    }
                    break;
                default:
                    System.err.println("Unknown option: " + arg);
                    printHelp();
                    System.exit(1);
            }
            i++;
        }

        if (operation == null) {
            System.err.println("Error: Must specify -e (encrypt) or -d (decrypt).");
            printHelp();
            System.exit(1);
        }
        if (keyStr == null || !keyStr.matches("\\d{1,20}")) {
            System.err.println("Error: Valid -k (key) is required (1-20 digits).");
            printHelp();
            System.exit(1);
        }
        BigInteger key = new BigInteger(keyStr);

        // Проверка ключа для шифрования
        if (operation.equals("encrypt") && key.compareTo(BigInteger.valueOf(ALPHABET_SIZE)) < 0) {
            System.err.println("Error: Key is too small for encryption. Minimum is " + ALPHABET_SIZE + ".");
            System.exit(1);
        }

        if (inputFile != null) {
            try {
                inputData = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(inputFile)), "UTF-8");
            } catch (Exception e) {
                System.err.println("Error reading input file: " + e.getMessage());
                System.exit(1);
            }
        } else if (text != null) {
            inputData = text;
        } else {
            System.err.println("Error: Provide either -i <file> or text after -e/-d.");
            printHelp();
            System.exit(1);
        }

        String result;
        try {
            if (operation.equals("encrypt")) {
                result = encrypt(inputData, key);
            } else {
                result = decrypt(inputData, key);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
            return;
        }

        if (outputFile != null) {
            try {
                java.nio.file.Files.write(java.nio.file.Paths.get(outputFile), result.getBytes("UTF-8"));
                System.out.println("Output written to " + outputFile);
            } catch (Exception e) {
                System.err.println("Error writing output file: " + e.getMessage());
                System.exit(1);
            }
        } else {
            System.out.println(result);
        }
        System.out.println("By Scream [dev]");
    }

    private static void printHelp() {
        System.out.println("TimelessCode - Temporal Cipher");
        System.out.println("Usage: java TimelessCode [options]");
        System.out.println("Options:");
        System.out.println("  -e, --encrypt [text]   Encrypt text (or with -i)");
        System.out.println("  -d, --decrypt [text]   Decrypt text (or with -i)");
        System.out.println("  -k, --key KEY          Secret key (1-20 digits)");
        System.out.println("  -i, --input FILE       Input file");
        System.out.println("  -o, --output FILE      Output file (default stdout)");
        System.out.println("  -h, --help             Show this help");
    }

    // scream-dev.ru
    private static String encrypt(String message, BigInteger key) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                .withZone(ZoneOffset.UTC);
        Random rng = new Random();
        StringBuilder result = new StringBuilder();
        // Ограничение по времени: от начала эпохи UNIX до конца 32-битного UNIX-времени
        final long epochStart = 0L;                           // 01.01.1970 00:00:00 UTC
        final long epochEnd   = 2147483647L;                  // 19.01.2038 03:14:07 UTC

        for (int i = 0; i < message.length(); i++) {
            int cp = message.codePointAt(i);
            if (Character.isSupplementaryCodePoint(cp)) {
                i++;
            }
            int index = ALPHABET.indexOf(cp);
            if (index == -1) {
                throw new IllegalArgumentException(
                    "Character '" + new String(Character.toChars(cp)) + "' not in alphabet."
                );
            }
            int target = index;

            long timestamp = findTimestamp(key, target, epochStart, epochEnd, rng);
            String dateStr = fmt.format(Instant.ofEpochSecond(timestamp));
            if (result.length() > 0) {
                result.append(", ");
            }
            result.append(dateStr);
        }
        return result.toString();
    }

    // github.com/screamdev
    private static long findTimestamp(BigInteger key, int target, long start, long end, Random rng) {
        BigInteger alphSize = BigInteger.valueOf(ALPHABET_SIZE);
        final int MAX_STEPS = 10000;
        while (true) {
            long base = start + (long)(rng.nextDouble() * (end - start));
            long t = base;
            int steps = 0;
            while (steps < MAX_STEPS) {
                BigInteger rem = BigInteger.valueOf(t).mod(key);
                int mod = rem.mod(alphSize).intValue();
                if (mod == target) {
                    return t;
                }
                t++;
                steps++;
                if (t > end) {
                    t = start;
                }
            }
        }
    }

    // I'll personally record a video of me on my knees for you if you can crack my code)
    private static String decrypt(String cipher, BigInteger key) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                .withZone(ZoneOffset.UTC);
        BigInteger alphSize = BigInteger.valueOf(ALPHABET_SIZE);
        StringBuilder result = new StringBuilder();

        String[] tokens = cipher.split("[,\\n]+");
        for (String token : tokens) {
            String trimmed = token.trim();
            if (trimmed.isEmpty()) continue;
            LocalDateTime ldt = LocalDateTime.parse(trimmed, fmt);
            long epoch = ldt.toInstant(ZoneOffset.UTC).getEpochSecond();
            BigInteger rem = BigInteger.valueOf(epoch).mod(key);
            int index = rem.mod(alphSize).intValue();
            char ch = ALPHABET.charAt(index);
            result.append(ch);
        }
        return result.toString();
    }
}