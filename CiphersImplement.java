import java.math.BigInteger;

public class CiphersImplement {
    
    // Implementation of Affine cipher
    class AffineCipher {
        static int a = 5; // Must be coprime with 26
        static int b = 8;

        // Function to encrypt plaintext
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

        // Function to decrypt ciphertext
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
                if ((a * x) % m == 1)
                    return x;
            }
            return 1;
        }
    }

    // Implementation of Vigenere cipher
    class VigenereCipher {
        // Function to encrypt plaintext
        static String encrypt(String plaintext, String keyword) {
            StringBuilder ciphertext = new StringBuilder();
            keyword = keyword.toUpperCase();
            plaintext = plaintext.toUpperCase();

            for (int i = 0, j = 0; i < plaintext.length(); i++) {
                char letter = plaintext.charAt(i);
                if (!Character.isLetter(letter)) {
                    ciphertext.append(letter);
                    continue;
                }
                char shift = keyword.charAt(j % keyword.length());
                char encryptedLetter = (char) ((letter + shift - 2 * 'A') % 26 + 'A');
                ciphertext.append(encryptedLetter);
                j++;
            }

            return ciphertext.toString();
        }

        // Function to decrypt ciphertext
        static String decrypt(String ciphertext, String keyword) {
            StringBuilder plaintext = new StringBuilder();
            keyword = keyword.toUpperCase();
            ciphertext = ciphertext.toUpperCase();

            for (int i = 0, j = 0; i < ciphertext.length(); i++) {
                char letter = ciphertext.charAt(i);
                if (!Character.isLetter(letter)) {
                    plaintext.append(letter);
                    continue;
                }

                char shift = keyword.charAt(j % keyword.length());
                char decryptedLetter = (char) ((letter - shift + 26) % 26 + 'A');
                plaintext.append(decryptedLetter);
                j++;
            }

            return plaintext.toString();
        }
    }
    // Implementation of Extended Euclidean Algorithm
    class ExtendedEuclidean {
        static int gcdExtended(int a, int b, int[] xy) {
            if (a == 0) {
                xy[0] = 0;
                xy[1] = 1;
                return b;
            }
            int[] xy1 = new int[2];
            int gcd = gcdExtended(b % a, a, xy1);
            xy[0] = xy1[1] - (b / a) * xy1[0];
            xy[1] = xy1[0];
            return gcd;
        }
    }

    // Implementaion of RSA Algorithm
    class RSA {
        static BigInteger p = new BigInteger("3");
        static BigInteger q = new BigInteger("11");
        static BigInteger n = p.multiply(q);
        static BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        static BigInteger e = new BigInteger("3"); // e is relatively prime to phi
        static BigInteger d = e.modInverse(phi);

        // Function to encrypt plaintext
        static BigInteger encrypt(BigInteger msg) {
            return msg.modPow(e, n);
        }

        // Function to decrypt ciphertext
        static BigInteger decrypt(BigInteger cipher) {
            return cipher.modPow(d, n);
        }
    }
    
    // Implementaion of El-Gamal Algorithm
    class ElGamal {
        static BigInteger p = new BigInteger("11");
        static BigInteger e1 = new BigInteger("2");
        static BigInteger d = new BigInteger("3");
        static BigInteger e2 = e1.modPow(d, p);
        static BigInteger r = new BigInteger("4");
        static BigInteger c1 = e1.modPow(r, p);
        static BigInteger c2;

        static void encrypt(BigInteger msg) {
            c2 = msg.multiply(e2.modPow(r, p)).mod(p);
        }

        static BigInteger decrypt() {
            BigInteger c1_d = c1.modPow(d, p);
            BigInteger c1_d_inv = c1_d.modInverse(p);
            return c2.multiply(c1_d_inv).mod(p);
        }
    }


    // Main function
    public static void main(String[] args) {
        // Affine Cipher 
        System.out.println("Affine Cipher:");
        String plainTextAffine = "HELLO";
        String encryptedAffine = AffineCipher.encrypt(plainTextAffine);
        String decryptedAffine = AffineCipher.decrypt(encryptedAffine);
        System.out.println("Plain Text: " + plainTextAffine);
        System.out.println("Encrypted: " + encryptedAffine);
        System.out.println("Decrypted: " + decryptedAffine);

        // Vigenere Cipher 
        System.out.println("\nVigenere Cipher:");
        String plainTextVigenere = "ATTACK AT DAWN";
        String keyVigenere = "SECURE";
        String encryptedVigenere = VigenereCipher.encrypt(plainTextVigenere, keyVigenere);
        String decryptedVigenere = VigenereCipher.decrypt(encryptedVigenere, keyVigenere);
        System.out.println("Plain Text: " + plainTextVigenere);
        System.out.println("Key: " + keyVigenere);
        System.out.println("Encrypted: " + encryptedVigenere);
        System.out.println("Decrypted: " + decryptedVigenere);

        // Extended Euclidean Algorithm 
        System.out.println("\nExtended Euclidean Algorithm:");
        int a = 35, b = 15;
        int[] xy = new int[2];
        int gcd = ExtendedEuclidean.gcdExtended(a, b, xy);
        System.out.println("Numbers: " + a + ", " + b);
        System.out.println("GCD(" + a + "," + b + "): " + gcd);
        System.out.println("Coefficients x, y: " + xy[0] + ", " + xy[1]);

        // RSA Cipher 
        System.out.println("\nRSA Cipher:");
        BigInteger plainTextRSA = new BigInteger("7");
        BigInteger encryptedRSA = RSA.encrypt(plainTextRSA);
        BigInteger decryptedRSA = RSA.decrypt(encryptedRSA);
        System.out.println("Plain Text: " + plainTextRSA);
        System.out.println("Encrypted: " + encryptedRSA);
        System.out.println("Decrypted: " + decryptedRSA);

        // ElGamal Cipher 
        System.out.println("\nElGamal Cipher:");
        BigInteger plainTextElGamal = new BigInteger("7");
        ElGamal.encrypt(plainTextElGamal);
        BigInteger decryptedElGamal = ElGamal.decrypt();
        System.out.println("Plain Text: " + plainTextElGamal);
        System.out.println("Encrypted: (" + ElGamal.c1 + ", " + ElGamal.c2 + ")");
        System.out.println("Decrypted: " + decryptedElGamal);

    }

}







/* ** OUTPUT **
PS D:\Desktop\crypto> javac .\CiphersImplement.java
PS D:\Desktop\crypto> java .\CiphersImplement.java 
Affine Cipher:
Plain Text: HELLO
Encrypted: RCLLA
Decrypted: HELLO

Vigenere Cipher:
Plain Text: ATTACK AT DAWN
Key: SECURE
Encrypted: SXVUTO SX FUNR
Decrypted: ATTACK AT DAWN

Extended Euclidean Algorithm:
Numbers: 35, 15
GCD(35,15): 5
Coefficients x, y: 1, -2

RSA Cipher:
Plain Text: 7
Encrypted: 13
Decrypted: 7

ElGamal Cipher:
Plain Text: 7
Encrypted: (5, 6)
Decrypted: 7               */
