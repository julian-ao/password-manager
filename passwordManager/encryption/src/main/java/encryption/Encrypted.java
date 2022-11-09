package encryption;

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
    return this.data;
  }

  public byte[] getNonce() {
    return this.nonce;
  }
}
