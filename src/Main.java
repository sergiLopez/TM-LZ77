import java.util.Random;

public class Main {

    public static void main(String[] args) {
        StringBuffer binaryText = TextReader.cargarTxt("./res/quijote_short.txt");
        String originalText = binaryText.toString();

        for (int i = 3; i < 12; i++) {
            for (int j = 2; j < 12 && j <= i; j++) {
                LZ77 lz77 = new LZ77(originalText, (int) Math.pow(2, i), (int) Math.pow(2, j));

                System.out.println("Mdes = " + (int) Math.pow(2, i) + " / Ment = " + (int) Math.pow(2, j));

                long firstTime = System.nanoTime();
                String encodedMessage = lz77.encode();
                String decodedMessage = lz77.decode(encodedMessage);
                long lastTime = System.nanoTime();

                StringBuffer textResult = new StringBuffer(decodedMessage);
                StringBuffer text = TextReader.ASCIIbin2string(textResult);
                System.out.println(text);
                System.out.println("Total time = " + (lastTime - firstTime));
                System.out.println("Compress factor = " + calculateCompressionFactor(encodedMessage, originalText));
                System.out.println("=====================================");
            }
        }
    }

    private static double calculateCompressionFactor(String encodedMessage, String originalMessage) {
        int originalSize = originalMessage.length();
        int compressSize = encodedMessage.trim().replace(" ", "").length();
        return (double) originalSize / compressSize;
    }
}