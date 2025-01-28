import java.math.BigInteger;

public class CipherDemo {
    class AffineCipher {
        static int a = 5, b = 8;
        static int m = 26;

        static String encrypt(String text) {
            StringBuilder result = new StringBuilder();
            for (char c : text.toCharArray()) {
                if (Character.isLetter(c)) {
                    result.append((char) ((((a * (c - 'A')) + b) % m) + 'A'));
                } else {
                    result.append(c);
                }
            }
            return result.toString();
        }

        static int modInverse(int a, int m) {
            for (int i = 1; i < m; i++) {
                if ((a * i) % m == 1)
                    return i;
            }
            return -1;
        }

        static String decrypt(String text) {
            int a_inv = modInverse(a, m);
            StringBuilder result = new StringBuilder();
            for (char c : text.toCharArray()) {
                if (Character.isLetter(c)) {
                    result.append((char) (((a_inv * ((c - 'A' - b + m)) % m)) + 'A'));
                } else {
                    result.append(c);
                }
            }
            return result.toString();
        }
    }

    class VigenereCipher {
        static String encrypt(String text, String key) {
            StringBuilder result = new StringBuilder();
            int keyLen = key.length();
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                char k = key.charAt(i % keyLen);
                result.append((char) (((c + k) % 26) + 'A'));
            }
            return result.toString();
        }

        static String decrypt(String text, String key) {
            StringBuilder result = new StringBuilder();
            int keyLen = key.length();
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                char k = key.charAt(i % keyLen);
                result.append((char) (((c - k + 26) % 26) + 'A'));
            }
            return result.toString();
        }
    }

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

    class RSA {
        static BigInteger p = new BigInteger("61");
        static BigInteger q = new BigInteger("53");
        static BigInteger n = p.multiply(q);
        static BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        static BigInteger e = new BigInteger("17");
        static BigInteger d = e.modInverse(phi);

        static BigInteger encrypt(BigInteger msg) {
            return msg.modPow(e, n);
        }

        static BigInteger decrypt(BigInteger cipher) {
            return cipher.modPow(d, n);
        }
    }

    class ElGamal {
        static BigInteger p = new BigInteger("23");
        static BigInteger e1 = new BigInteger("5");
        static BigInteger d = new BigInteger("6");
        static BigInteger e2 = e1.modPow(d, p);
        static BigInteger r = new BigInteger("15");
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

    public static void main(String[] args) {
        // Affine Cipher Example
        System.out.println("Affine Cipher:");
        String plainTextAffine = "HELLO";
        String encryptedAffine = AffineCipher.encrypt(plainTextAffine);
        String decryptedAffine = AffineCipher.decrypt(encryptedAffine);
        System.out.println("Plain Text: " + plainTextAffine);
        System.out.println("Encrypted: " + encryptedAffine);
        System.out.println("Decrypted: " + decryptedAffine);

        // Vigenere Cipher Example
        System.out.println("\nVigenere Cipher:");
        String plainTextVigenere = "HELLO";
        String keyVigenere = "KEY";
        String encryptedVigenere = VigenereCipher.encrypt(plainTextVigenere, keyVigenere);
        String decryptedVigenere = VigenereCipher.decrypt(encryptedVigenere, keyVigenere);
        System.out.println("Plain Text: " + plainTextVigenere);
        System.out.println("Key: " + keyVigenere);
        System.out.println("Encrypted: " + encryptedVigenere);
        System.out.println("Decrypted: " + decryptedVigenere);

        // Extended Euclidean Algorithm Example
        System.out.println("\nExtended Euclidean Algorithm:");
        int a = 30, b = 20;
        int[] xy = new int[2];
        int gcd = ExtendedEuclidean.gcdExtended(a, b, xy);
        System.out.println("Numbers: " + a + ", " + b);
        System.out.println("GCD: " + gcd);
        System.out.println("Coefficients x, y: " + xy[0] + ", " + xy[1]);

        // RSA Cipher Example
        System.out.println("\nRSA Cipher:");
        BigInteger plainTextRSA = new BigInteger("65");
        BigInteger encryptedRSA = RSA.encrypt(plainTextRSA);
        BigInteger decryptedRSA = RSA.decrypt(encryptedRSA);
        System.out.println("Plain Text: " + plainTextRSA);
        System.out.println("Encrypted: " + encryptedRSA);
        System.out.println("Decrypted: " + decryptedRSA);

        // ElGamal Cipher Example
        System.out.println("\nElGamal Cipher:");
        BigInteger plainTextElGamal = new BigInteger("19");
        ElGamal.encrypt(plainTextElGamal);
        BigInteger decryptedElGamal = ElGamal.decrypt();
        System.out.println("Plain Text: " + plainTextElGamal);
        System.out.println("Encrypted: (" + ElGamal.c1 + ", " + ElGamal.c2 + ")");
        System.out.println("Decrypted: " + decryptedElGamal);

    }

}

/* ** OUTPUT **
PS D:\Desktop\crypto> javac .\CiphersDemo.java
PS D:\Desktop\crypto> java .\CiphersDemo.java 
Affine Cipher:
Plain Text: HELLO
Encrypted: RCLLA
Decrypted: HELLO

Vigenere Cipher:
Plain Text: HELLO
Key: KEY
Encrypted: RIJVS
Decrypted: HELLO

Extended Euclidean Algorithm:
Numbers: 30, 20
GCD: 10
Coefficients x, y: 1, -1

RSA Cipher:
Plain Text: 65
Encrypted: 2790
Decrypted: 65

ElGamal Cipher:
Plain Text: 19
Encrypted: (19, 15)
Decrypted: 19
PS D:\Desktop\crypto> */
