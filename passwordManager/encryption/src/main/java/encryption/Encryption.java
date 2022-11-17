package encryption;

import java.util.Arrays;
import java.util.Random;

/**
 * Encryption is a class that contains methods for encrypting and decrypting strings.
 */
public class Encryption { // CBC

  private static final int blockSize = 16;
  private static Random random = new Random();

  /**
   * Encrypts a string using AES-128 in CBC mode.
   */
  private static byte[] addPadding(byte[] data, int blockSize) {
    int paddingAmount = blockSize - data.length % blockSize;
    if (paddingAmount == 0) {
      paddingAmount = blockSize;
    }
    byte paddingByte = (byte) paddingAmount;
    byte[] padded = new byte[data.length + paddingAmount];
    for (int i = 0; i < data.length; i++) {
      padded[i] = data[i];
    }
    for (int i = data.length; i < data.length + paddingAmount; i++) {
      padded[i] = paddingByte;
    }

    return padded;
  }

  /**
   * removes the padding from the data.
   *
   * @param data the data to remove padding from
   * @return the data without padding
   */
  private static byte[] removePadding(byte[] data) {
    int paddingAmount = data[data.length - 1];
    byte[] result = new byte[data.length - paddingAmount];

    for (int i = 0; i < result.length; i++) {
      result[i] = data[i];
    }

    return result;

  }

  /**
   * The encrypt function uses cipher block chaining to allow for more than 128 bits to be
   * encrypted.
   *
   * @param plaintext The plaintext to be encrypted
   * @param key Key used in the encryption
   * @return an Encrypted object, containing nonce used, and the
   */
  public static Encrypted encrypt(String plaintext, byte[] key) {

    TwoFish twoFish = new TwoFish(); // class for block cipher algorithm
    Encrypted result = new Encrypted(); // result of encryption is stored as a "Encrypted" object

    /**
     * store the incoming plaintext as a byte array Padding added to plaintext, so the length is a
     * multiple of the blockSize. This is needed because the TwoFish block cipher only accepts
     * blocks which are exactly 128 bits long
     */
    byte[] plainTextBlocks = HexStringUtils.textToBytes(plaintext);
    plainTextBlocks = addPadding(plainTextBlocks, blockSize);

    byte[] cipherTextBlocks = new byte[plainTextBlocks.length]; // create byte array to store the
                                                                // encryption result

    // nonce is used so every encryption is different, even if the same plaintext is
    // encrypted multiple times
    byte[] nonce = new byte[blockSize];
    random.nextBytes(nonce);
    result.setNonce(nonce);

    // xor first plaintext block with nonce before calculating the first ciphertext
    // block
    byte[] in = Arrays.copyOfRange(plainTextBlocks, 0, blockSize);
    in = ByteArrayUtils.bytesXor(in, nonce);
    byte[] firstBlock = twoFish.encrypt(in, key);
    for (int i = 0; i < blockSize; i++) {
      cipherTextBlocks[i] = firstBlock[i];
    }

    // subsequent plaintext blocks are xor'ed with the previous ciphertextblock, two
    // blocks containg the same bytes will not be encrypted to the same
    // ciphertextblock
    for (int i = 1; i < plainTextBlocks.length / blockSize; i++) {

      byte[] localInput =
          Arrays.copyOfRange(plainTextBlocks, i * blockSize, i * blockSize + blockSize);
      localInput = ByteArrayUtils.bytesXor(localInput, Arrays.copyOfRange(cipherTextBlocks,
          (i - 1) * blockSize, (i - 1) * blockSize + blockSize));
      byte[] localResult = twoFish.encrypt(localInput, key);
      for (int j = 0; j < blockSize; j++) {
        cipherTextBlocks[i * blockSize + j] = localResult[j];

      }
    }

    result.setData(cipherTextBlocks);
    return result;

  }

  /**
   * Decrypts a string using AES-128 in CBC mode.
   *
   * @param cipherObject Object conating the ciphertext and the nonce used in encryption
   * @param key key to be used in the decryption
   * @return the original plaintext, in String format
   */
  public static String decrypt(Encrypted cipherObject, byte[] key) {

    // setup
    TwoFish twoFish = new TwoFish();
    byte[] nonce = cipherObject.getNonce();
    byte[] cipherText = cipherObject.getData();
    byte[] plainText = new byte[cipherText.length];

    // Each plaintext P[i] is equal to decrypt(C[i]) xor C[i-1]. For i > 0
    for (int i = (cipherText.length - 1) / blockSize; i > 0; i--) {
      byte[] localResult = twoFish
          .decrypt(Arrays.copyOfRange(cipherText, i * blockSize, i * blockSize + blockSize), key);
      localResult = ByteArrayUtils.bytesXor(localResult,
          Arrays.copyOfRange(cipherText, (i - 1) * blockSize, (i - 1) * blockSize + blockSize));
      for (int j = 0; j < blockSize; j++) {
        plainText[i * blockSize + j] = localResult[j];
      }
    }

    // for i = 0, P[i] = decrypt(C[i]) xor nonce
    byte[] firstBlock = twoFish.decrypt(Arrays.copyOfRange(cipherText, 0, blockSize), key);
    firstBlock = ByteArrayUtils.bytesXor(firstBlock, nonce);
    for (int j = 0; j < blockSize; j++) {
      plainText[j] = firstBlock[j];
    }

    return HexStringUtils.bytesToText(removePadding(plainText));
  }
}
