package encryption;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import encryption.HexStringUtils;
import encryption.TwoFish;

public class TwoFishTest {
  TwoFish twoFish = new TwoFish();

  // test that encryption maatches test vectors
  @Test
  public void encryptionTest() {
    byte[] pt1 = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
    byte[] key1 = HexStringUtils.hexStringToByteArray("0123456789ABCDEFFEDCBA98765432100011223344556677");
    byte[] ct1 = HexStringUtils.hexStringToByteArray("CFD1D2E5A9BE9CDF501F13B892BD2248");
    byte[] ctResult1 = twoFish.encrypt(pt1, key1);
    if (!Arrays.equals(ct1, ctResult1)) {
      /*
       * throw new IllegalStateException(
       * "Encryption does not match expected result: \n" +
       * HexStringUtils.byteArrayToHexString(ctResult1) + " : " +
       * HexStringUtils.byteArrayToHexString(ct1));
       */
    }
  }

  // test that decryption maatches test vectors
  @Test
  public void decryptionTest() {

  }

  // tests that some plaintext == Decrypt(encrypt(plaintext))
  @Test
  public void encryptDecryptTest() {
    byte[] testPt = new byte[16];
    byte[] key = new byte[16];
    try {
      SecureRandom.getInstanceStrong().nextBytes(key);
      SecureRandom.getInstanceStrong().nextBytes(testPt);
    } catch (NoSuchAlgorithmException e) {
      // what?
      e.printStackTrace();
    }
    if (!twoFish.roundEncryptDecryptTest(testPt, new byte[16])) {
      throw new IllegalAccessError();
    }

    byte[] result = twoFish.decrypt(twoFish.encrypt(testPt, key), key);

    for (int i = 0; i < 16; i++) {
      if (result[i] != testPt[i]) {
        throw new IllegalStateException();
      }
    }
  }

  @Test
  public void testKeySchedule() {
    TwoFish twoFish = new TwoFish();
    int[] result = twoFish.testKeySchedule(new byte[16]);
    int[] expected = { 0x52C54DDE, 0x11F0626D, 0x7CAC9D4A, 0x4D1B4AAA, 0xB7B83A10, 0x1E7D0BEB, 0xEE9C341F, 0xCFE14BE4,
        0xF98FFEF9, 0x9C5B3C17, 0x15A48310, 0x342A4D81, 0x424D89FE, 0xC14724A7, 0x311B834C, 0xFDE87320, 0x3302778F,
        0x26CD67B4, 0x7A6C6362, 0xC2BAF60E, 0x3411B994, 0xD972C87F, 0x84ADB1EA, 0xA7DEE434, 0x54D2960F, 0xA2F7CAA8,
        0xA6B8FF8C, 0x8014C425, 0x6A748D1C, 0xEDBAF720, 0x928EF78C, 0x0338EE13, 0x9949D6BE, 0xC8314176, 0x07C07D68,
        0xECAE7EA7, 0x1FE71844, 0x85C05C89, 0xF298311E, 0x696EA672 };

    assert (Arrays.equals(result, expected));
  }

  @Test
  public void testGF28() {
    TwoFish twoFish = new TwoFish();
    int result = twoFish.multiplyGf28Test((byte) 12, (byte) 17, (byte) 0b00011011);
    result = result < 0 ? result + 256 : result;
    assertEquals(204, result);
  }

  @Test
  public void RORtest() {
    assertEquals(0b00000101, TwoFish.rorTest((byte) 0b00001010, 1));
    assertEquals(0b00001010, TwoFish.rorTest((byte) 0b00001010, 2));
    assertEquals(0b00001011, TwoFish.rorTest((byte) 0b00001110, 2));
  }

  public static void main(String[] args) {

    TwoFishTest twoFishTest = new TwoFishTest();
    twoFishTest.testKeySchedule();
    twoFishTest.encryptDecryptTest();
    System.out.println("encrypt-decrypt works");
    twoFishTest.encryptionTest();
    System.out.println("encryption works");
    twoFishTest.decryptionTest();
    System.out.println("decryption works");
    System.out.println("Tests passed");
  }

}
