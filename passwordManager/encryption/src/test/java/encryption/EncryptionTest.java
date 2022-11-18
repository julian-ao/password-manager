package encryption;

import org.junit.jupiter.api.Test;

public class EncryptionTest {
  @Test
  public void encryptDecryptTest() {
    String plaintext = "Hello, this is some text that should be encrypted";
    byte[] key = HexStringUtils.hexStringToByteArray("0102030405060708a1a2a3a4a5a6a7a8");
    Encrypted encrypted = Encryption.encrypt(plaintext, key);
    String decrypted = Encryption.decrypt(encrypted, key);
    assert (plaintext.equals(decrypted));
  }

}
