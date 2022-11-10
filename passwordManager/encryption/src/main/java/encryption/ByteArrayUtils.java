package encryption;

public class ByteArrayUtils {

  public static int bytesToIntLittleEndian(byte[] bytes) {
    return (((int) bytes[0]) << 24) | (((int) bytes[1]) << 16) | (((int) bytes[2]) << 8) | (int) bytes[3];
  }

  public static int bytesToIntBigEndian(byte[] bytes) {
    return ((((int) bytes[3]) << 24) & 0xff000000) | ((((int) bytes[2]) << 16) & 0x00ff0000)
        | ((((int) bytes[1]) << 8) & 0x0000ff00) | ((int) bytes[0] & 0x000000ff);
  }

  public static byte[] intToBytesLittleEndian(int integer) {
    byte[] result = new byte[4];
    result[0] = (byte) (integer >>> 24);
    result[1] = (byte) (integer >>> 16);
    result[2] = (byte) (integer >>> 8);
    result[3] = (byte) (integer);
    return result;
  }

  public static byte[] intToBytesBigEndian(int integer) {
    byte[] result = new byte[4];
    result[3] = (byte) (integer >>> 24);
    result[2] = (byte) (integer >>> 16);
    result[1] = (byte) (integer >>> 8);
    result[0] = (byte) (integer);
    return result;
  }

  public static byte[] wrapAroundFourBytesLeft(byte[] bytes, int amount) {
    int a = bytesToIntLittleEndian(bytes);
    a = (a << amount) & a >>> (32 - amount);
    return intToBytesLittleEndian(a);
  }

  public static byte[] wrapAroundFourBytesRight(byte[] bytes, int amount) {
    int a = bytesToIntLittleEndian(bytes);
    a = (a >>> amount) & a << (32 - amount);
    return intToBytesLittleEndian(a);
  }

  public static byte[] bytesXor(byte[] a, byte[] b) {
    assert (a.length == b.length);
    byte[] result = new byte[a.length];
    for (int i = 0; i < a.length; i++) {
      result[i] = (byte) (a[i] ^ b[i]);
    }
    return result;
  }

}
