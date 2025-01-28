import java.util.*;

class AffineCipher {
    static int a = 5; // Must be coprime with 26
    static int b = 8;

    // Function to encrypt text
    static String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                result.append((char) (((a * (c - base) + b) % 26) + base));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    // Function to decrypt text
    static String decrypt(String text) {
        StringBuilder result = new StringBuilder();
        int a_inv = modInverse(a, 26);
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                result.append((char) (((a_inv * ((c - base - b + 26) % 26)) % 26) + base));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    // Function to find modular inverse of a under mod m
    static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) return x;
        }
        return 1;
    }

    public static void main(String[] args) {
        String plaintext = "HELLO";
        String encrypted = encrypt(plaintext);
        String decrypted = decrypt(encrypted);
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}

// output
// Plaintext: HELLO
// Encrypted: RCLLA
// Decrypted: HELLO
