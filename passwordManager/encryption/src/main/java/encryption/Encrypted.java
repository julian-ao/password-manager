package encryption;

/**
 * Stores encrypted data.
 */
public class Encrypted {
  private byte[] data;
  private byte[] nonce;

  public void setData(byte[] data) {
    this.data = data;
    
  }

  public void setNonce(byte[] nonce) {
    this.nonce = nonce;
  }

  public byte[] getData() {
    return data.clone();
  }

  public byte[] getNonce() {
    return nonce.clone();
  }
}
