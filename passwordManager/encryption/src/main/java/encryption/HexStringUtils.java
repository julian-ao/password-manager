package encryption;

/**
 * HexStringUtils is a class that contains methods for converting between hex
 * strings and byte arrays.
 */
public class HexStringUtils {

  /**
   * byteArrayToHexString is a method that converts a byte array to a hex string.
   *
   * @param text the byte array to be converted
   * @return the hex string
   */
  public static byte[] textToBytes(String text) {
    byte[] result = new byte[text.length()];
    for (int i = 0; i < text.length(); i++) {
      result[i] = (byte) text.charAt(i);
    }
    return result;
  }

  /**
   * converts a byte array to a hex string.
   *
   * @param bytes the bytes to convert to hex
   * @return String representation of the bytes
   */
  public static String bytesToText(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      sb.append((char) (bytes[i] & 0x00ff));

    }
    String temp = sb.toString();
    return temp;
  }

  /**
   * hexStringToByteArray converts between hexstring and byte array. example:
   * "89ABCDEF" -> {0x89, 0xAB, 0xCD, 0xEF} Code stolen from
   * https://stackoverflow.com/questions/140131/convert-a-string-representation-of
   * -a-hex-dump-to-a-byte-array-using-java.
   */
  public static byte[] hexStringToByteArray(String s) {
    s = s.replace(" ", "");
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
    }
    return data;
  }

  private static char bitsToHex(byte b) {
    char[] array = "0123456789abcdef".toCharArray();
    return array[b];
  }

  /**
   * Converts a byte array to a hex string.
   */
  public static String byteArrayToHexString(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      char lower = bitsToHex((byte) (b & 0x0f));
      char upper = bitsToHex((byte) ((b >> 4) & 0x0f));
      sb.append(upper);
      sb.append(lower);
    }
    return sb.toString();
  }
}
