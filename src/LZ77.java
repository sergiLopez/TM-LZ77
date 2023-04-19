import java.util.*;

public class LZ77 {

  public static void main(String[] args) {
    String input = "1010110110110110101011100101110010111001010111101010111101010110110";
    int ment = 4;
    int mdes = 8;
    String compressed = compress(input, ment, mdes);
    System.out.println("Compressed: " + compressed);
    String decompressed = decompress(compressed, ment, mdes);
    System.out.println("Decompressed: " + decompressed);
  }

  public static String compress(String input, int ment, int mdes) {

    StringBuilder compressed = new StringBuilder();
    compressed.append(input.substring(0, mdes));
    int maxLD = (int) (Math.log(ment) / Math.log(2)) + (int) (Math.log(mdes) / Math.log(2));
    for (int i = mdes; i < input.length(); ) {
      int[] ld = findLongestMatch(input, i, ment, mdes);
      compressed.append(ldToBinaryString(ld[0], ld[1], ment, mdes));
      i += ld[0];
      if (i + ment >= input.length()) {
        compressed.append(input.substring(i));
        break;
      }
    }
    return compressed.toString();
  }

  public static String decompress(String compressed, int ment, int mdes) {

    StringBuilder decompressed = new StringBuilder();
    decompressed.append(compressed.substring(0, mdes));
    int maxLD = (int) (Math.log(ment) / Math.log(2)) + (int) (Math.log(mdes) / Math.log(2));
    for (int i = mdes; i < compressed.length();) {
      if (compressed.length() - i >= maxLD) {
        String ldString = compressed.substring(i, i + maxLD);
        int[] ld = binaryStringToLD(ldString, ment, mdes);
        for (int j = 0; j < ld[0] && decompressed.length() - ld[1] + j < decompressed.length(); j++) {
          decompressed.append(decompressed.charAt(decompressed.length() - ld[1] + j));
        }
        i += maxLD;
      } else {
        decompressed.append(compressed.substring(i));
        break;
      }
    }
    return decompressed.toString();
  }


  private static int[] findLongestMatch(String input, int pos, int ment, int mdes) {
    int maxLength = 0;
    int maxDistance = 0;
    for (int i = 1; i <= mdes && pos - i >= 0; i++) {
      int length = 0;
      while (length < ment && pos + length < input.length()
          && input.charAt(pos - i + length) == input.charAt(pos + length)) {
        length++;
      }
      if (length > maxLength) {
        maxLength = length;
        maxDistance = i;
      }
    }
    return new int[]{maxLength, maxDistance};
  }

  private static String ldToBinaryString(int l, int d, int ment, int mdes) {
    String lBinary = Integer.toBinaryString(l);
    String dBinary = Integer.toBinaryString(d);
    int lPadding = Math.max(0, (int) (Math.log(ment) / Math.log(2)) - lBinary.length());
    int dPadding = Math.max(0, (int) (Math.log(mdes) / Math.log(2)) - dBinary.length());

    lBinary = "0".repeat(lPadding) + lBinary;
    dBinary = "0".repeat(dPadding) + dBinary;

    return lBinary + dBinary;
  }


  private static int[] binaryStringToLD(String ldString, int ment, int mdes) {
    int lBits = (int) (Math.log(ment) / Math.log(2));
    int dBits = (int) (Math.log(mdes) / Math.log(2));

    int l = Integer.parseInt(ldString.substring(0, lBits), 2);
    int d = Integer.parseInt(ldString.substring(lBits, lBits + dBits), 2);

    return new int[]{l, d};
  }
}
