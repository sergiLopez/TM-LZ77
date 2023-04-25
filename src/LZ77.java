import java.util.ArrayList;
import java.util.List;


public class LZ77 {
  public static void main(String[] args) {
    String datos = "11011100101001111010100010001110111001010011110101000100011101110010100111101010001000111011100101001111010100010001110111001010011110101000100011101110010100111101010001000111011100101001111010100010001110111001010011110101000100011101110010100111101010001000111011100101001111010100010001";

    int Ment = 8;
    int Mdest = 6;

    List<Tuple> tuples = findTuples(datos, Ment, Mdest);

    for (Tuple tuple : tuples) {
      System.out.println(tuple);
      System.out.println(tupleToBinary(tuple,Ment,Mdest));

    }

    // Decodificar los datos utilizando las tuplas
    String decodedData = decode(tuples, Ment);

    // Imprimir los datos decodificados
    System.out.println("Datos decodificados: " + decodedData);
  }

  public static List<Tuple> findTuples(String datos, int Ment, int Mdest) {
    List<Tuple> tuples = new ArrayList<>();
    while (datos.length() >= Ment + Mdest) {
      String largeBin = datos.substring(0, Ment);
      String smallBin = datos.substring(Ment, Ment + Mdest);

      Tuple tuple = findBinarySubstring(largeBin, smallBin);
      tuples.add(tuple);

      int length = tuple.length;
      datos = datos.substring(length);
    }
    return tuples;
  }

  public static Tuple findBinarySubstring(String largeBin, String smallBin) {
    int largeLength = largeBin.length();

    while (smallBin.length() > 0) {
      int position = findMatch(largeBin, smallBin);
      if (position != -1) {
        return new Tuple(smallBin.length(), largeLength - position);
      }
      smallBin = smallBin.substring(0, smallBin.length() - 1);
    }

    return new Tuple(-1, -1);
  }

  private static int findMatch(String largeBin, String smallBin) {
    int largeLength = largeBin.length();
    int smallLength = smallBin.length();

    for (int i = largeLength - smallLength; i >= 0; i--) {
      if (largeBin.substring(i, i + smallLength).equals(smallBin)) {
        return i;
      }
    }
    return -1;
  }
  public static String tupleToBinary(Tuple tuple, int Ment, int Mdest) {
    int mentBits = Integer.toBinaryString(Ment - 1).length();
    int mdestBits = Integer.toBinaryString(Mdest - 1).length();

    String lengthBinary;
    String positionBinary;

    if (tuple.length == Mdest) {
      lengthBinary = "0".repeat(mdestBits);
    } else {
      lengthBinary = String.format("%" + mdestBits + "s", Integer.toBinaryString(tuple.length)).replace(' ', '0');
    }

    if (tuple.position == Ment) {
      positionBinary = "0".repeat(mentBits);
    } else {
      positionBinary = String.format("%" + mentBits + "s", Integer.toBinaryString(tuple.position)).replace(' ', '0');
    }

    return lengthBinary + " " + positionBinary;
  }








  public static String decode(List<Tuple> tuples, int Ment) {
    StringBuilder decodedData = new StringBuilder("0".repeat(Ment)); // Agregar ceros iniciales

    for (Tuple tuple : tuples) {
      int length = tuple.length;
      int position = tuple.position;

      if (length == 0) {
        decodedData.append("0");
      } else {
        decodedData.append(decodedData.substring(decodedData.length() - position, decodedData.length() - position + length));
      }
    }

    return decodedData.substring(Ment); // Retornar la cadena sin los ceros iniciales agregados
  }

}