# TimelessCode

TimelessCode is a Java-based temporal cipher that encrypts any text message into a sequence of seemingly random dates and times. This provides a unique method of steganography, concealing data within a format that appears innocuous.

## How It Works

The core of TimelessCode is a custom character set (alphabet) and a numeric secret key.

### Encryption
1.  Each character in the plaintext message is mapped to its index within the internal alphabet.
2.  For each character's index, the algorithm searches for a UNIX timestamp (between 01 Jan 1970 and 19 Jan 2038).
3.  A timestamp is considered a match if `(timestamp % secretKey) % alphabetSize` equals the character's index. To ensure randomness, the search starts from a random point in the valid timestamp range.
4.  The matching timestamp is formatted into a `dd.MM.yyyy HH:mm:ss` string.
5.  The final encrypted output is a comma-separated list of these date strings.

### Decryption
1.  The encrypted text is split into individual date strings.
2.  Each date string is parsed back into its corresponding UNIX timestamp.
3.  The original character's index is recovered by calculating `(timestamp % secretKey) % alphabetSize`.
4.  The character at the recovered index in the internal alphabet is appended to the result, reconstructing the original message.

## Features

*   **Temporal Encryption**: Encrypts messages into a list of dates.
*   **Large Character Set**: Supports a wide range of characters including ASCII, Latin-1 Supplement, Cyrillic, Greek, and various symbols.
*   **CLI and Interactive Modes**: Can be run with command-line arguments for automation or in an interactive mode for ease of use.
*   **File I/O**: Supports encrypting and decrypting entire files.

## Usage

### Compilation
First, compile the Java source file. The use of `-encoding UTF-8` is recommended due to the special characters in the source code.

```bash
javac -encoding UTF-8 TimelessCode.java
```

### Interactive Mode
To run the program in interactive mode, simply execute the class without any arguments. You will be prompted to choose an operation, enter a key, and provide the text or file paths.

```bash
java TimelessCode
```
To package the compiled class into an executable JAR file:
```bash
jar --create --file TimelessCode.jar --main-class TimelessCode *.class
```
**Sample Session:**
```
=== TimelessCode ===

Choose an option:
1 - Encrypt text
2 - Decrypt text
3 - Encrypt file
4 - Decrypt file
5 - Exit
> 1
Enter secret key (1 to 20 digits): 1234567890987654321
Enter text to encrypt:
Hello
Encrypted:
10.02.2031 03:07:04, 11.01.2018 20:00:15, 05.08.2033 11:29:56, 04.11.2003 01:29:56, 05.01.2017 00:00:23
By Scream [dev]
```

### Command-Line Mode
You can also use command-line flags for non-interactive use.

**Options:**

| Flag                 | Description                                    |
| -------------------- | ---------------------------------------------- |
| `-e`, `--encrypt`    | Encrypt the provided text or input file.       |
| `-d`, `--decrypt`    | Decrypt the provided text or input file.       |
| `-k`, `--key`        | **Required.** The secret key (1-20 digits).    |
| `-i`, `--input`      | Path to the input file.                        |
| `-o`, `--output`     | Path to the output file (defaults to stdout).  |
| `-h`, `--help`       | Display the help message.                      |

**Key Requirement:** For encryption, the secret key must be numerically larger than the size of the internal character set (671).

### Examples

**Encrypting a string:**
```bash
java TimelessCode -e "Hello World" -k 123456789012345
```

**Decrypting a string:**
```bash
java TimelessCode -d "10.02.2031 03:07:04, 11.01.2018 20:00:15" -k 123456789012345
```

**Encrypting a file:**
```bash
java TimelessCode --encrypt --key 123456789012345 --input plaintext.txt --output encrypted.txt
```

**Decrypting a file:**
```bash
java TimelessCode --decrypt --key 123456789012345 --input encrypted.txt --output decrypted.txt
```
[![Ask DeepWiki](https://devin.ai/assets/askdeepwiki.png)](https://deepwiki.com/scream-dev/TimelessCode)

## License
This project is licensed under the [Mozilla Public License 2.0](LICENSE).
