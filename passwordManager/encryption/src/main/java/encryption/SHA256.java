package encryption;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

// https://qvault.io/cryptography/how-sha-2-works-step-by-step-sha-256/

/**
 * SHA256 is a class that represents the SHA256 hashing algorithm.
 */
public class SHA256 {
  private int h0;
  private int h1;
  private int h2;
  private int h3;
  private int h4;
  private int h5;
  private int h6;
  private int h7;

  private static final int[] k = {0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b,
      0x59f111f1, 0x923f82a4, 0xab1c5ed5, 0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3,
      0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174, 0xe49b69c1, 0xefbe4786, 0x0fc19dc6,
      0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da, 0x983e5152, 0xa831c66d,
      0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967, 0x27b70a85,
      0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
      0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585,
      0x106aa070, 0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a,
      0x5b9cca4f, 0x682e6ff3, 0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa,
      0xa4506ceb, 0xbef9a3f7, 0xc67178f2};

  /**
   * The constructor for SHA256.
   */
  public SHA256() {
    h0 = 0x6a09e667;
    h1 = 0xbb67ae85;
    h2 = 0x3c6ef372;
    h3 = 0xa54ff53a;
    h4 = 0x510e527f;
    h5 = 0x9b05688c;
    h6 = 0x1f83d9ab;
    h7 = 0x5be0cd19;
  }

  /*
   * rightRotateInt shifts the bits in an integer and wraps the bits back around.
   */
  private int rightRotateInt(int word, int a) {
    return word >>> a | word << (32 - a);
  }

  /*
   * bytesToInts and intsToBytes creates an int array out of a byte array and vice versa. It does
   * not convert but instead creates an array which reads the same bits differently.
   */
  private int[] bytesToInts(byte[] bytes) {
    int[] result = new int[bytes.length / 4];
    for (int i = 0; i < bytes.length / 4; i++) {
      result[i] = 0;
      result[i] |= (bytes[i * 4] << 24) & 0xff000000;
      result[i] |= (bytes[i * 4 + 1] << 16) & 0x00ff0000;
      result[i] |= (bytes[i * 4 + 2] << 8) & 0x0000ff00;
      result[i] |= bytes[i * 4 + 3] & 0x000000ff;
    }
    return result;
  }

  private byte[] intsToBytes(int[] ints) {
    byte[] result = new byte[ints.length * 4];
    for (int i = 0; i < ints.length; i++) {
      result[i * 4] = (byte) (ints[i] >>> 24);
      result[i * 4 + 1] = (byte) (ints[i] >>> 16);
      result[i * 4 + 2] = (byte) (ints[i] >>> 8);
      result[i * 4 + 3] = (byte) (ints[i]);
    }
    return result;
  }

  /*
   * padInts copies data from an array to a bigger one, which basically means that the function
   * returns the array with a bunch of added zeros.
   */
  private int[] padInts(int[] words) {
    int[] result = new int[64];

    for (int i = 0; i < words.length; i++) {
      result[i] = words[i];
    }
    return result;
  }

  /*
   * pads the input, stores length of the original data at the end, also adds a 0x80 byte, I do not
   * know why.
   */
  private byte[] pad(byte[] bytes) {
    // Original data|| 1-bit ||Padding to make result 64 bytes long
    byte[] result = new byte[bytes.length + 1 + 64 - ((bytes.length + 1) % 64)];

    for (int i = 0; i < bytes.length; i++) {
      result[i] = bytes[i];
    }
    result[bytes.length] = (byte) 0x80;

    for (int i = bytes.length + 1; i < result.length - 4; i++) {
      result[i] = 0x00;
    }

    int a = result.length - 4;
    long bitSize = bytes.length * 8;

    result[a - 1] = (byte) (bitSize >>> 32);
    result[a] = (byte) (bitSize >>> 24);
    result[a + 1] = (byte) (bitSize >>> 16);
    result[a + 2] = (byte) (bitSize >>> 8);
    result[a + 3] = (byte) bitSize;

    return result;
  }

  /*
   * compress function uses the jumbled data to manipulate the h0-h7 fields.
   */
  private void compress(int[] w) {
    int a = this.h0;
    int b = this.h1;
    int c = this.h2;
    int d = this.h3;
    int e = this.h4;
    int f = this.h5;
    int g = this.h6;
    int h = this.h7;

    for (int i = 0; i < w.length; i++) {
      int S1 = rightRotateInt(e, 6) ^ rightRotateInt(e, 11) ^ rightRotateInt(e, 25);
      int ch = (e & f) ^ (~e & g);
      int temp1 = h + S1 + ch + k[i] + w[i];
      int S0 = rightRotateInt(a, 2) ^ rightRotateInt(a, 13) ^ rightRotateInt(a, 22);
      int maj = (a & b) ^ (a & c) ^ (b & c);
      int temp2 = S0 + maj;
      h = g;
      g = f;
      f = e;
      e = d + temp1;
      d = c;
      c = b;
      b = a;
      a = temp1 + temp2;

    }

    this.h0 += a;
    this.h1 += b;
    this.h2 += c;
    this.h3 += d;
    this.h4 += e;
    this.h5 += f;
    this.h6 += g;
    this.h7 += h;

  }

  /**
   * SHA256 works by reading in the input one block at a time, doing some funky stuff to that block,
   * and then using that block to manipulate its internal state. This is done until there are no
   * more blocks. Lastly the internal states are returned as the hash.
   */
  public byte[] getHash(byte[] input) {

    input = pad(input); // pad data to be multiple of 64 bytes

    for (int c = 0; c < input.length / 64; c++) { // loop trough all chuncks

      int[] w = bytesToInts(Arrays.copyOfRange(input, c * 64, c * 64 + 64));
      w = padInts(w);

      for (int i = 16; i < 64; i++) {
        int s0 = (rightRotateInt(w[i - 15], 7) ^ rightRotateInt(w[i - 15], 18)) ^ (w[i - 15] >>> 3);
        int s1 = rightRotateInt(w[i - 2], 17) ^ rightRotateInt(w[i - 2], 19) ^ (w[i - 2] >>> 10);

        w[i] = (w[i - 16] + s0 + w[i - 7] + s1);
      }
      compress(w);
    }
    return intsToBytes(
        new int[] {this.h0, this.h1, this.h2, this.h3, this.h4, this.h5, this.h6, this.h7});
  }

  /**
   * Get the hash.
   */
  public String getHash(String input, int salt) {
    byte[] inputBytes = null;
    try {
      inputBytes = Arrays.copyOf(input.getBytes("UTF-8"), input.length() + 4);
    } catch (UnsupportedEncodingException e) {
      // This will never happen as long as java supports UTF-8 encoding
    }
    inputBytes[inputBytes.length - 1] = (byte) (salt << 24);
    inputBytes[inputBytes.length - 2] = (byte) (salt << 16);
    inputBytes[inputBytes.length - 3] = (byte) (salt << 8);
    inputBytes[inputBytes.length - 4] = (byte) (salt);
    byte[] result = getHash(inputBytes);

    return HexStringUtils.byteArrayToHexString(result);
  }

  /**
   * the reset function resets the internal fields to their original values.
   */
  public void reset() {
    this.h0 = 0x6a09e667;
    this.h1 = 0xbb67ae85;
    this.h2 = 0x3c6ef372;
    this.h3 = 0xa54ff53a;
    this.h4 = 0x510e527f;
    this.h5 = 0x9b05688c;
    this.h6 = 0x1f83d9ab;
    this.h7 = 0x5be0cd19;
  }
}
