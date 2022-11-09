package encryption;

import static org.junit.Assert.assertThat;

import org.junit.Test;

public class EncryptionTest {
  @Test
  public void encryptDecryptTest() {
    Encryption encryption = new Encryption();
    String plaintext = "Hello, this is some text that should be encrypted";
    byte[] key = HexStringUtils.hexStringToByteArray("0102030405060708a1a2a3a4a5a6a7a8");
    Encrypted encrypted = encryption.encrypt(plaintext, key);
    String ciphertext = HexStringUtils.byteArrayToHexString(encrypted.getData());
    byte[] nonce = encrypted.getNonce();
    String decrypted = encryption.decrypt(encrypted, key);
    assert (plaintext.equals(decrypted));
  }

}
