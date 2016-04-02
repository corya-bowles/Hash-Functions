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

	//FROM DEMO CODE
	//as with encryption, we create and initialize an object
	//that will execute the hash function
	public static MessageDigest md;

	/**
	 * Receives two byte arrays and determines the number of bits
	 * that are different between the two arrays. Assumes both byte
	 * arrays are of the same length
	 * @return difference
	 */
	public static int diff(byte[] a, byte[] b) {

		int diffCount = 0;

		String strA = HashFunctions.getBinaryByteString(a);
		String strB = HashFunctions.getBinaryByteString(b);

		for(int i = 0; i < strA.length(); i++) {
			if(strA.charAt(i) != strB.charAt(i)) {
				diffCount++;
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

		int arrPos = (position % 8);
		int arrNum = (int)Math.floor(position / 8);
		char newPosVal = binStringArr[arrNum].charAt(arrPos) == '1' ? '0' : '1';
		binStringArr[arrNum] = binStringArr[arrNum].substring(0, arrPos) + 
			newPosVal + binStringArr[arrNum].substring(arrPos + 1);

		try {

			byte[] fByteArr = new byte[a.length];
			for(int i = 0; i < a.length; i++) {
				fByteArr[i] = (byte)(Byte.parseByte("+" + (binStringArr[i]), 2));
			}

			return fByteArr;

		} catch(NumberFormatException e) {
			// e.printStackTrace();
			// System.out.println("NumberFormatException flipping bit " + position);
		}

		return a;

	}

	/**
	 * Takes the original message's 32 bit hash and an updated
	 * message's 32 bit hash, incrementing count for each index
	 * value passed as the last argument. Assumes msgA and msgB
	 * are 32 bit messages and bitDiffCount is a 32 int array
	 * @param msgA 			byte array containing 32-bit hash of a message
	 * @param msgB 			byte array containing 32-bit hash of a message
	 * @param bitDiffCount 	int array containing bit counts for each of 32 bits
	 */
	public static int[] updateBitChangeCount(byte[] msgA, byte[] msgB, int[] bitDiffCount) {

		String strA = HashFunctions.getBinaryByteString(msgA);
		String strB = HashFunctions.getBinaryByteString(msgB);

		for(int i = 0; i < strA.length(); i++) {
			if(strA.charAt(i) != strB.charAt(i)) {
				bitDiffCount[i] = (bitDiffCount[i] + 1);
			}
		}

		return bitDiffCount;
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
		byte[] test2 = HashFunctions.flip(test1, 0);
		System.out.println(HashFunctions.getBinaryByteString(test2));

		// test diff method
		byte[] test3 = {0x11, 0x11, 0x01};
		System.out.println(HashFunctions.getBinaryByteString(test3));
		System.out.println(HashFunctions.diff(test1, test3));

		System.out.println("Begin part 2");

		//SHA-1 is one of the most widely used hash functions
		//Creates an instance of a SHA-1 mdSUM to be used in this class or something like that, right?  
		try { 
			md = MessageDigest.getInstance("SHA-1");//32 bytes
		}
		catch(NoSuchAlgorithmException e) { 
			e.printStackTrace();
		}

		// 8-byte arbitrary message
		byte[] arbM = (new String("Test Msg")).getBytes();
		
		// first four bytes of arbMHash
		byte[] arbMHash4 = H(md.digest(arbM));

		// keep track of each bit's change
		int[] msgBitChangeCount = new int[32];

		int totalDiffs = 0;
		int lowestDiff = 0;
		int highestDiff = 0;
		int msgCount = 0;
		double avgDiffCount = 0;

		for(int i = 0; i < arbM.length * 8; i++) {
			byte[] newMsgHash4 = H(md.digest(HashFunctions.flip(arbM, i)));
			int diffCount = HashFunctions.diff(newMsgHash4, arbMHash4);
			
			if(diffCount < lowestDiff) {
				lowestDiff = diffCount;
			}

			if(diffCount > highestDiff) {
				highestDiff = diffCount;
			}

			HashFunctions.updateBitChangeCount(arbMHash4, newMsgHash4, msgBitChangeCount);

			totalDiffs += diffCount;
			msgCount++;
		}

		avgDiffCount = totalDiffs / msgCount;

		System.out.println("Message count = " + msgCount);
		System.out.println("Lowest diff count = " + lowestDiff);
		System.out.println("Highest diff count = " + highestDiff);
		System.out.println("Average diff count = " + avgDiffCount);
		System.out.println("Total diffs = " + totalDiffs);

		System.out.println("Change frequency for each bit:");

		for(int i = 0; i < msgBitChangeCount.length; i++) {
			System.out.println("> " + msgBitChangeCount[i]);
		}

	}

}