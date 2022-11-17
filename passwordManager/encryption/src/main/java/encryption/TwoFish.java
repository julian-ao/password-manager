package encryption;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * TwoFish is a class that implements the TwoFish encryption algorithm.
 */
public class TwoFish {

  /**
   * the first 4 integers are the state of the encryption/decryption. They start
   * as the input, are manipulated in various ways troughout the algorithm and
   * finally they are outputted out of the encryption/decryption.
   */
  private int left0;
  private int left1;
  private int right0;
  private int right1;

  /**
   * the expanded key is stored in keyWords as they are used multiple places, this
   * removes the need to pass this data troughout the algorithm.
   */
  private int[] keyWords;

  // constant substition tables used to generate the key dependent ones.
  private static final byte[] q0t0 = 
  { 8, 1, 7, 13, 6, 15, 3, 2, 0, 11, 5, 9, 14, 12, 10, 4 };
  private static final byte[] q0t1 = 
  { 14, 12, 11, 8, 1, 2, 3, 5, 15, 4, 10, 6, 7, 0, 9, 13 };
  private static final byte[] q0t2 = 
  { 11, 10, 5, 14, 6, 13, 9, 0, 12, 8, 15, 3, 2, 4, 7, 1 };
  private static final byte[] q0t3 = 
  { 13, 7, 15, 4, 1, 2, 6, 14, 9, 11, 3, 0, 8, 5, 12, 10 };
  private static final byte[] q1t0 = 
  { 2, 8, 11, 13, 15, 7, 6, 14, 3, 1, 9, 4, 0, 10, 12, 5 };
  private static final byte[] q1t1 = 
  { 1, 14, 2, 11, 4, 12, 3, 7, 6, 13, 10, 5, 15, 9, 0, 8 };
  private static final byte[] q1t2 = 
  { 4, 12, 7, 5, 1, 6, 9, 10, 0, 14, 13, 8, 2, 11, 3, 15 };
  private static final byte[] q1t3 = 
  { 11, 9, 5, 1, 12, 3, 13, 14, 6, 4, 7, 15, 2, 0, 8, 10 };

  /**
   * MDSmatrix and RSD are transformations used in the algorithm. RSD is used in
   * the keyschedule MDS is used on the internal state every round
   */
  private static final byte[][] MDSmatrix = { 
      { (byte) 0x01, (byte) 0xef, (byte) 0x5b, (byte) 0x5b },
      { (byte) 0x5b, (byte) 0xef, (byte) 0xef, (byte) 0x01 }, 
      { (byte) 0xef, (byte) 0x5b, (byte) 0x01, (byte) 0xef },
      { (byte) 0xef, (byte) 0x01, (byte) 0xef, (byte) 0x5b } };

  private static final byte[][] RSD = {
      { (byte) 0x01, (byte) 0xa4, (byte) 0x55, (byte) 0x87, 
        (byte) 0x5a, (byte) 0x58, (byte) 0xdb, (byte) 0x9e },
      { (byte) 0xa4, (byte) 0x56, (byte) 0x82, (byte) 0xf3, 
        (byte) 0x1e, (byte) 0xc6, (byte) 0x68, (byte) 0xe5 },
      { (byte) 0x02, (byte) 0xa1, (byte) 0xfc, (byte) 0xc1, 
        (byte) 0x47, (byte) 0xae, (byte) 0x3d, (byte) 0x19 },
      { (byte) 0xa4, (byte) 0x55, (byte) 0x87, (byte) 0x5a, 
        (byte) 0x58, (byte) 0xdb, (byte) 0x9e, (byte) 0x03 } };

  private byte[] sbox0 = new byte[256];
  private byte[] sbox1 = new byte[256];
  private byte[] sbox2 = new byte[256];
  private byte[] sbox3 = new byte[256];

  // Reducing polynomials used to define galois fields
  private static final byte RSMinPoly = 0b01001101;
  private static final byte MDSMinPoly = 0b01101001;

  /**
   * right rotates the 4 rightmost bits in a byte example ROR(xxxx1011, 2) =
   * 00001110 the four upper bits are discarded.
   *
   * @param x      the byte which contains the 4 bits to be rotated
   * @param amount hwo much the 4 bits should be rotated
   *
   * @return byte whose 4 least significant bits are rotated
   */
  private static byte ROR(byte x, int amount) {
    byte result;
    result = (byte) ((x >> amount) & 0x0f);
    result |= x << (4 - amount);
    result = (byte) (result & 0x0f);
    return result;
  }

  public static byte RORtest(byte x, int amount) {
    return ROR(x, amount);
  }

  /**
   * Substitutes the 4 bits in a byte with the value in the sbox.
   *
   * @param x the byte to be substituted
   * @param a a 1 or a 0, which chooses between two sets of 4 bit substitutions
   * @return the substituted byte
   */
  private byte qSubstitute(byte x, int a) {
    byte[] t0;
    byte[] t1;
    byte[] t2;
    byte[] t3;
    if (a == 0) {
      t0 = q0t0;
      t1 = q0t1;
      t2 = q0t2;
      t3 = q0t3;
    } else {
      t0 = q1t0;
      t1 = q1t1;
      t2 = q1t2;
      t3 = q1t3;
    }
    byte a0 = (byte) ((x >>> 4) & 0x0f);
    byte b0 = (byte) (x & 0x0f);
    byte a1 = (byte) ((a0 ^ b0) & 0x0f);
    byte b1 = (byte) ((a0 ^ ROR(b0, 1) ^ ((a0 << 3) & 0x0f)) & 0x0f);
    byte a2 = t0[a1];
    byte b2 = t1[b1];
    byte a3 = (byte) (a2 ^ b2);
    byte b3 = (byte) ((a2 ^ ROR(b2, 1) ^ ((a2 << 3) & 0x0f)) & 0x0f);
    byte a4 = t2[a3];
    byte b4 = t3[b3];
    return (byte) (b4 << 4 | (a4 & 0x0f));
  }

  /**
   * q0 is a substition table.
   *
   * @param x the byte to be substituted
   *
   * @return the substition for the given byte
   */

  private byte q0(byte x) {
    return qSubstitute(x, 0);
  }

  private byte q1(byte x) {
    return qSubstitute(x, 1);
  }

  /**
   * this function takes in 4 bytes and substitutes them with different
   * substitution boxes.
   *
   * @param bytes 4 bytes to be substituted
   * @return substituted bytes
   */
  private byte[] s_boxBytes(byte[] bytes) {
    assert (bytes.length == 4);
    byte[] result = new byte[4];
    result[0] = sbox0[(bytes[0]) < 0 ? ((int) (bytes[0])) + 256 : bytes[0]];
    result[1] = sbox1[(bytes[1]) < 0 ? ((int) (bytes[1])) + 256 : bytes[1]];
    result[2] = sbox2[(bytes[2]) < 0 ? ((int) (bytes[2])) + 256 : bytes[2]];
    result[3] = sbox3[(bytes[3]) < 0 ? ((int) (bytes[3])) + 256 : bytes[3]];
    return result;
  }

  // Galois field stuff
  private byte addGf28(byte a, byte b) {
    return (byte) (a ^ b);
  }

  /**
   * this function is the same as a*b when a and b is in GF(2^8) with reducing
   * polynomial: minPoly. NOTE: this function does not commute
   *
   * @param a       left hand side operand
   * @param b       right hand side operand
   * @param minPoly reducing polynomial, where x^8 is implicit. 01010101 -->
   *                x^8+x^6+x^4+x^2+1
   * @return byte a*b in GF(2^8)
   */
  private byte multiplyGf28(byte a, byte b, byte minPoly) {
    byte res = 0;
    while (a != 0 && b != 0) {
      if ((b & 0x01) != 0) {
        res ^= a;
      }
      if ((a & 0x80) != 0) {
        a = (byte) ((a << 1) ^ minPoly);
      } else {
        a <<= 1;
      }
      b >>>= 1;
      b = (byte) (b & 0x7f);

    }
    return res;
  }

  public byte multiplyGF28Test(byte a, byte b, byte minPoly) {
    return multiplyGf28(a, b, minPoly);
  }

  /**
   * the function interprets a byte array as a vector over a certain galois field
   * defined by the reducing polynomial(param minPoly), with a matrix defining a
   * transformation in the vector space.
   *
   * @param matrix  matrix representing the transformation
   * @param vector  to be transformed
   * @param minPoly the reducing polynomial passed on to the multiplication
   * @return the transformed vector of bytes
   */
  private byte[] matrixVectorMultiplyGF28(byte[][] matrix, byte[] vector, byte minPoly) {
    byte[] result = new byte[matrix.length];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        result[i] = addGf28(result[i], multiplyGf28(matrix[i][j], vector[j], minPoly));
      }
    }
    return result;
  }

  /**
   * input/output whiteing xor's the current state of the encryption with key
   * material.
   * 
   */
  private void inputWhitening() {
    this.left0 ^= keyWords[0];
    this.left1 ^= keyWords[1];
    this.right0 ^= keyWords[2];
    this.right1 ^= keyWords[3];
  }

  private void outputWhitening() {
    this.left0 ^= keyWords[4];
    this.left1 ^= keyWords[5];
    this.right0 ^= keyWords[6];
    this.right1 ^= keyWords[7];
  }

  private int mdsMultiply(byte[] vector) {
    return ByteArrayUtils.bytesToIntBigEndian(
      matrixVectorMultiplyGF28(MDSmatrix, vector, MDSMinPoly)
      );
  }

  /**
   * the f function is at the heart of the twofish algorithm and carries out most
   * of the substitution and transposition.
   *
   * @param x0 the first input
   * @param x1 the second input
   * @param r  the round number
   * @return int[] the output of the function
   */
  private int[] fFunction(int x0, int x1, int r) {
    x1 = (x1 << 8) | (x1 >>> (32 - 8)); // rightrotate x1 8 to the left before passing it to gFunction
    x0 = gFunction(ByteArrayUtils.intToBytesBigEndian(x0));
    x1 = gFunction(ByteArrayUtils.intToBytesBigEndian(x1));

    x0 = x0 + x1;
    x1 = x1 + x0;

    x0 += keyWords[(2 * r) + 8];
    x1 += keyWords[(2 * r) + 9];

    return new int[] { x0, x1 };
  }

  /**
   * the g function substitutes bytes and performs a transformation on the bytes
   * s_boxBytes substitutes bytes mdsMultiply is a spesific instance of
   * matrixVectorMultiplyGF28 function, with a hardcoded reducing polynomial and
   * transformation.
   *
   * @param bytes the byte array input
   * @return an integer output
   */
  private int gFunction(byte[] bytes) {
    bytes = s_boxBytes(bytes);
    return mdsMultiply(bytes);
  }

  /**
   * roundEncrypt performs one of the 16 rounds of encryption.
   *
   * @param r the round number
   */
  private void roundEncrypt(int r) {
    int[] fLeftSide = fFunction(this.left0, this.left1, r);
    this.right1 = (this.right1 << 1) | ((this.right1 >>> 31));
    this.right1 ^= fLeftSide[1];
    this.right0 ^= fLeftSide[0];
    this.right0 = ((this.right0 >>> 1)) | ((this.right0 << 31));
    flip();

  }

  /**
   * roundDecrypt performs one of the 16 rounds of decryption.
   *
   * @param r round number
   */
  private void roundDecrypt(int r) {
    flip();
    this.right0 = ((this.right0 << 1)) | ((this.right0 >>> 31));
    int[] fLeftSide = fFunction(this.left0, this.left1, r);
    this.right0 ^= fLeftSide[0];
    this.right1 ^= fLeftSide[1];
    this.right1 = ((this.right1 >>> 1)) | ((this.right1 << 31));
  }

  /**
   * flip swaps the left and right side of the state.
   */
  public boolean roundEncryptDecryptTest(byte[] input, byte[] key) {
    keySchedule(key);
    getInput(input);

    roundEncrypt(0);
    roundDecrypt(0);

    byte[] result = getOutput();
    return Arrays.equals(result, input);

  }

  /**
   * flips the left side with the right side.
   */
  private void flip() {
    int tmp0 = this.left0;
    this.left0 = this.right0;
    this.right0 = tmp0;
    int tmp1 = this.left1;
    this.left1 = this.right1;
    this.right1 = tmp1;
  }

  /**
   * the h Function is used in the key schedule to generate more key material.
   *
   * @param X some integer
   * @param L some list of integers
   * @param k the size of L
   * @return integer
   */
  private int hFunction(int X, int[] L, int k) {
    Map<Integer, int[]> qMap = new HashMap<>();

    qMap.put(4, new int[] { 1, 0, 0, 1 });
    qMap.put(3, new int[] { 1, 1, 0, 0 });
    qMap.put(2, new int[] { 0, 1, 0, 1 });
    qMap.put(1, new int[] { 0, 0, 1, 1 });
    qMap.put(0, new int[] { 1, 0, 1, 0 });

    byte[] bytesX = ByteArrayUtils.intToBytesBigEndian(X);
    /*
     * byte[][] bytesL = new byte[L.length][4]; for (int i = 0; i < L.length; i++) {
     * bytesL[i] = ByteArrayUtils.intToBytesBigEndian(L[i]); }
     */

    byte[] result = new byte[4];
    boolean xAdded = false;
    for (int i = 4; i > 0; i--) {

      // Handling of different keysizes
      if (i > k) {
        continue;
      }
      if (!xAdded) {
        result = Arrays.copyOf(bytesX, 4);
        xAdded = true;
      }

      // substitute
      result[0] = qSubstitute(result[0], qMap.get(i)[0]);
      result[1] = qSubstitute(result[1], qMap.get(i)[1]);
      result[2] = qSubstitute(result[2], qMap.get(i)[2]);
      result[3] = qSubstitute(result[3], qMap.get(i)[3]);

      // xor
      result = ByteArrayUtils.bytesXor(result, ByteArrayUtils.intToBytesBigEndian(L[i - 1]));

    }
    // last substitute
    result[0] = qSubstitute(result[0], qMap.get(0)[0]);
    result[1] = qSubstitute(result[1], qMap.get(0)[1]);
    result[2] = qSubstitute(result[2], qMap.get(0)[2]);
    result[3] = qSubstitute(result[3], qMap.get(0)[3]);

    result = ByteArrayUtils.intToBytesBigEndian(mdsMultiply(result));

    return ByteArrayUtils.bytesToIntBigEndian(result);

  }

  private int intRotateLeft(int a, int amount) {
    return a << amount | a >>> (32 - amount);
  }

  /**
   * expands the key into 40 words of key material, used for input/output
   * whitening and for the f function.
   *
   * @param Me keyMaterial list
   * @param M0 another keyMaterial list
   */
  private void expandKeyWords(int[] Me, int[] M0) {
    int p = 0x01010101;
    int keyAmount = 20;
    int[] A = new int[keyAmount];
    int[] B = new int[keyAmount];
    this.keyWords = new int[keyAmount * 2];
    for (int i = 0; i < keyAmount; i++) {
      A[i] = hFunction(p * i * 2, Me, Me.length);
      B[i] = intRotateLeft(hFunction((p * (i * 2 + 1)), M0, M0.length), 8);
      keyWords[2 * i] = A[i] + B[i];
      keyWords[(2 * i) + 1] = intRotateLeft((A[i] + (2 * B[i])), 9);
    }

  }

  /**
   * the keySchedule expands the key into several subkeys as well as creating the
   * key dependent substitution boxes.
   *
   * @param key the raw key used
   */
  private void keySchedule(byte[] key) {
    int k = key.length / 8;
    int[] M = new int[2 * k];

    for (int i = 0; i < M.length; i++) {
      M[i] = ByteArrayUtils.bytesToIntBigEndian(Arrays.copyOfRange(key, i * 4, i * 4 + 4));
    }

    // keymaterial used for keywords
    int[] Me = new int[k];
    int[] M0 = new int[k];
    for (int i = 0; i < k; i++) {
      Me[i] = M[i * 2];
      M0[i] = M[(i * 2) + 1];
    }

    expandKeyWords(Me, M0); // Works

    // keymaterial used for sboxes
    int[] S = new int[k];
    for (int i = 0; i < k; i++) {
      S[k - 1 - i] = ByteArrayUtils
          .bytesToIntBigEndian(matrixVectorMultiplyGF28(
            RSD,
            Arrays.copyOfRange(key, i * 8, i * 8 + 8),
            RSMinPoly
            ));
    }

    // setting up keydependent substitution boxes
    int p = 0x01010101;
    for (int i = 0; i < 256; i++) {
      sbox0[i] = (byte) ((hFunction((i * p) & 0xff000000, S, k) & 0xff000000) >>> 24);
      sbox1[i] = (byte) ((hFunction((i * p) & 0x00ff0000, S, k) & 0x00ff0000) >>> 16);
      sbox2[i] = (byte) ((hFunction((i * p) & 0x0000ff00, S, k) & 0x0000ff00) >>> 8);
      sbox3[i] = (byte) ((hFunction((i * p) & 0x000000ff, S, k) & 0x000000ff));
    }
  }

  /**
   * Converts 16 bytes of data inputted in to the algorithm, and
   * stores them a 4 seperate integers.
   *
   * @param input the input to the encryption or decryption function
   */
  private void getInput(byte[] input) {

    this.left0 = ByteArrayUtils.bytesToIntBigEndian(Arrays.copyOfRange(input, 0, 4));
    this.left1 = ByteArrayUtils.bytesToIntBigEndian(Arrays.copyOfRange(input, 4, 8));
    this.right0 = ByteArrayUtils.bytesToIntBigEndian(Arrays.copyOfRange(input, 8, 12));
    this.right1 = ByteArrayUtils.bytesToIntBigEndian(Arrays.copyOfRange(input, 12, 16));
  }

  /**
   * gets output of the encryption or decryption function and converts it to a
   * byte array.
   */
  private byte[] getOutput() {

    byte[] left0Bytes = ByteArrayUtils.intToBytesBigEndian(left0);
    byte[] left1Bytes = ByteArrayUtils.intToBytesBigEndian(left1);
    byte[] right0Bytes = ByteArrayUtils.intToBytesBigEndian(right0);
    byte[] right1Bytes = ByteArrayUtils.intToBytesBigEndian(right1);
    byte[] result = new byte[16];
    System.arraycopy(left0Bytes, 0, result, 0, 4);
    System.arraycopy(left1Bytes, 0, result, 4, 4);
    System.arraycopy(right0Bytes, 0, result, 8, 4);
    System.arraycopy(right1Bytes, 0, result, 12, 4);
    return result;
  }

  /**
   * Encrypts the input using the key.
   *
   * @param plaintext the plaintext is the raw data
   * @param key       arbritrary string of bits used in the encryption
   * @return the cipher text
   */
  public byte[] encrypt(byte[] plaintext, byte[] key) {
    assert (plaintext.length == 16);

    keySchedule(key);
    getInput(plaintext);
    // input whitening
    inputWhitening();

    for (int i = 0; i < 16; i++) {
      // rounds
      roundEncrypt(i);
    }
    // output whitening

    flip();

    outputWhitening();

    return getOutput();
  }

  /** 
   * Decrypts the given ciphertext using the given key.

   * @param ciphertext the cipher text is the encrypted data
   * @param key        key used to encrypt,
   * @return the decrypted plaintext
   */
  public byte[] decrypt(byte[] ciphertext, byte[] key) {
    assert (ciphertext.length == 16);

    keySchedule(key);

    getInput(ciphertext);

    outputWhitening();
    flip();
    for (int i = 15; i >= 0; i--) {
      roundDecrypt(i);
    }

    inputWhitening();
    return getOutput();
  }

  public int[] testKeySchedule(byte[] key) {
    keySchedule(key);
    return keyWords;
  }

  public static void main(String[] args) {

  }
}