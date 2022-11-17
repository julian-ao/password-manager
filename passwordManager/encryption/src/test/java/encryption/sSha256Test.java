package encryption;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class sSha256Test {
  private Map<byte[], byte[]> expected;

  public sSha256Test() {
    expected = new HashMap<>();

    expected.put(HexStringUtils.hexStringToByteArray("35af563efefef97234638920"),
        HexStringUtils.hexStringToByteArray("cf13fda39c981506985ec0ed5684a6b57d4d7296cb382f2c9e8f149971fe257c"));
    expected.put(
        HexStringUtils.hexStringToByteArray("14f948006ad422d1bc256b7f80415bd7f1ba1e2eb48ccdd03e96d7970330bab5"),
        HexStringUtils.hexStringToByteArray("59a55b45486bb4412068e7554121657127d3de938da03eb480ba7cc52e7b820d"));
    expected.put(
        HexStringUtils.hexStringToByteArray("59a55b45486bb4412068e7554121657127d3de938da03eb480ba7cc52e7b820d"),
        HexStringUtils.hexStringToByteArray("3859cd561742ccd172dc31e1c5547bfa3292a82c5ff153fbba5eb792322dad20"));
  }

  @Test
  public void testGetHash() {

    for (Entry<byte[], byte[]> e : expected.entrySet()) {
      Sha256 hash = new Sha256();
      Assertions.assertTrue(Arrays.equals(e.getValue(), hash.getHash(e.getKey())));
    }
  }

}
