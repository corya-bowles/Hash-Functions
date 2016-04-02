import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
/**
 * Properties of hash functions project.
 *
 * Selects an arbitrary 8-byte 64-bit message M
 * and determines its hash H(M). Message M is then
 * non-cumulatively changed L(M) amount of times
 * L being the amount of bits in M.
 *
 * Statisitcs are collected; such as the amount of
 * bits that differ on average per bit change, as
 * well as the max and min number of bits that change.
 *
 * @juanvallejo
 * @corya-bowles
 */

public class HashFunctions {

	/**
	 * Receives two byte arrays and determines the number of bits
	 * that are different between the two arrays. Assumes both byte
	 * arrays are of the same length
	 * @return difference
	 */
	public static int diff(byte[] a, byte[] b) {

		int diffCount = 0;
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < 8; j++) {
				diffCount += (a[i] >> j) ^ (b[i] >> j);
			}
		}

		return diffCount;
	}

	/**
	 * Receives a byte array, creates a copy of this array and
	 * flips the bit located at the specified position.
	 * The array returned will be one bit different than the
	 * original. If position arg is outside of the valid range,
	 * no bit is flipped
	 * @return newArray
	 */
	public static byte[] flip(byte[] a, int position) {

		String[] binStringArr = HashFunctions.getBinaryByteArrayString(a);

		if(position >= 8 * a.length || position <= 0) {
			return a;
		}

		// a[0] ^ (0x01 << position % 8)

		int arrPos = (position % 8);
		int arrNum = (int)Math.floor(position / 8);
		char newPosVal = binStringArr[arrNum].charAt(arrPos) == '1' ? '0' : '1';
		binStringArr[arrNum] = binStringArr[arrNum].substring(0, arrPos) + 
			newPosVal + binStringArr[arrNum].substring(arrPos + 1);

		byte[] fByteArr = new byte[a.length];
		for(int i = 0; i < a.length; i++) {
			fByteArr[i] = (byte)(Byte.parseByte(binStringArr[i], 2));
		}

		return fByteArr;

	}

	public static String[] getBinaryByteArrayString(byte[] array) {
		
		String[] binByteArr = new String[array.length];
		for(int i = 0; i < array.length; i++) {

			String binStr = "";
			for(int j = 7; j >= 0; j--) {
				binStr += Integer.toBinaryString((array[i] >> j) & 0x01);
			}
			binByteArr[i] = binStr;

		}

		return binByteArr;

	}

	public static String getBinaryByteString(byte[] array) {
		
		String[] binByteArr = HashFunctions.getBinaryByteArrayString(array);
		String binByteStr = "";
		for(int i = 0; i < binByteArr.length; i++) {
			binByteStr += binByteArr[i];
		}

		return binByteStr;

	}

	public static byte[] xor(byte[] b1, byte[] b2) {

		byte[] diff = new byte[b1.length];
		for(int i = 0; i < b1.length && 1 < b2.length; i++) {
			diff[i] = (byte)(b1[i] ^ b2[i]);
		}

		return diff;

	}

	//FROM DEMO CODE
	//as with encryption, we create and initialize an object
	//that will execute the hash function
	public static MessageDigest md;

	//FROM DEMO CODE
	//Truncated version of hash; just take first 4 bytes
	//requires java.util.Arrays
	public static byte[] H(byte [] msg){
		return Arrays.copyOfRange(md.digest(msg), 0, 4);
	}
	

	public static void main(String[] args) {

		// test print method
		byte[] test1 = {0x01, 0x00, 0x00};
		System.out.println(HashFunctions.getBinaryByteString(test1));

		// test flip method
		byte[] test2 = HashFunctions.flip(test1, 5);
		System.out.println(HashFunctions.getBinaryByteString(test2));

		// test diff method
		byte[] test3 = {0x11, 0x11, 0x01};
		System.out.println(HashFunctions.getBinaryByteString(test3));
		System.out.println(HashFunctions.diff(test1, test3));

		System.out.println();

		//Trying out part two of the project

		//SHA-1 is one of the most widely used hash functions
		//Creates an instance of a SHA-1 mdSUM to be used in this class or something like that, right?  
		try { md = MessageDigest.getInstance("SHA-1");//32 bytes
		}
		catch(NoSuchAlgorithmException e) { e.printStackTrace();
		}

		 

		

		byte[] arbM = {0x01, 0x01, 0x01, 0x01};
		System.out.println(HashFunctions.getBinaryByteString(arbM));
		byte[] hashBytes = md.digest(arbM);
		System.out.println(HashFunctions.getBinaryByteString(H(hashBytes)));
	}

}