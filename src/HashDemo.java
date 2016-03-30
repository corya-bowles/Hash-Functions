import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;



public class HashDemo {
	
	//as with encryption, we create and initialize an object
	//that will execute the hash function
	public static MessageDigest md;
	
	//Truncated version of hash; just take first 4 bytes
	//requires java.util.Arrays
	public static byte[] H(byte [] msg){
		return Arrays.copyOfRange(md.digest(msg), 0, 4);
	}
	
	public static void main(String[] args){

		// SHA-1 is one of the most widely used hash functions
		try { md = MessageDigest.getInstance("SHA-1");//32 bytes
		}
		catch(NoSuchAlgorithmException e) { e.printStackTrace();
		}

	    
		String input = "This is the message to be hashed";
		System.out.println("Message: " + input);
		byte[] inputBytes = input.getBytes();
		byte[] hashBytes = md.digest(inputBytes);
		String Hash = byteArrayToHex(hashBytes);
		System.out.println("SHA-1 Hash: " + Hash);
		System.out.println("Truncated Hash: " + byteArrayToHex(H(inputBytes)) + "\n");

		//small change produces entirely different result
		input = "this is the message to be hashed";
		System.out.println("Message: " + input);
		inputBytes = input.getBytes();
		hashBytes = md.digest(inputBytes);
		Hash = byteArrayToHex(hashBytes);
		System.out.println("SHA-1 Hash: " + Hash + "\n");
		

		//given hash function produces fixed-length output for any input
		input = "Now we have a very long input message, but no matter how long the input may be, a given hash function produces a fixed-length output...";
		System.out.println("Message: " + input);
		inputBytes = input.getBytes();
		hashBytes = md.digest(inputBytes);
		Hash = byteArrayToHex(hashBytes);
		System.out.println("SHA-1 Hash: " + Hash + "\n");
					
	}


	//for displaying byte arrays
	public static String byteArrayToHex(byte [] array)
	{
		String s = new String();
		for(int i = 0; i < array.length-1; i++)
		{
			s = s + (Integer.toHexString((array[i]>>4)&0x0F).toUpperCase());
			s = s + (Integer.toHexString(array[i]&0x0F).toUpperCase() );
		}

		s = s + (Integer.toHexString(array[array.length-1]>>4&0x0F).toUpperCase());
		s = s +(Integer.toHexString(array[array.length-1]&0x0F).toUpperCase());
		return s;
	}
}
