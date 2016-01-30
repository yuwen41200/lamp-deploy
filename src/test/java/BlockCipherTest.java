import org.junit.Test;
import static org.junit.Assert.*;

public class BlockCipherTest {
	@Test
	public void test() throws Exception {
		String testPwd = "Z > B, li da yu bi.";
		String testData = "I don't have any girlfriend, I only have a lot of bugs. So sad...";
		BlockCipher blockCipher = new BlockCipher(testPwd);
		BlockCipher.BlockCipherData blockCipherData = blockCipher.encrypt(testData);
		String testResult = blockCipher.decrypt(blockCipherData);
		assertEquals("BlockCipher test #1", testData, testResult);
	}
}
