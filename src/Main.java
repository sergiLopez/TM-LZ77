import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal donde empieza la ejecución del programa
 *
 * @author Sergio López
 * @author Jordi Bujaldón
 */
public class Main {

    public static void main(String[] args) {
        String message = "111111110101010101";
        int Mdest = 8;
        int Ment = 6;

        System.out.println("Mensaje original: " + message);

        // Imprimir los datos codificados
        String encodedMessage = encode(message, Mdest, Ment);
        System.out.println("Datos comprimidos: " + encodedMessage);

        // Decodificar los datos
        String decodedMessage = decode(encodedMessage, Mdest, Ment);
        System.out.println("Datos descomprimidos: " + decodedMessage);

        // Comprobamos que el mensaje original es igual al descomprimido
        System.out.println("El mensaje original y decodificado son iguales? " + message.equals(decodedMessage));
    }

    /**
     * Función que comprime el mensaje utilizando el algoritmo LZ77. El resultado
     * muestra los bits comprimidos separados por espacios
     *
     * @param message Mensaje que se quiere comprimir
     * @param Mdest Número que debe tener la ventana deslizante
     * @param Ment Número que debe tener la ventana de entrada
     * @return El mensaje comprimido
     * @see LDNode
     */
    public static String encode(String message, int Mdest, int Ment) {
        // Encontramos las redundancias en el mensaje
        List<LDNode> LDNodes = findLDNodes(message, Mdest, Ment);
        StringBuilder returnMessage = new StringBuilder(message.substring(0, Mdest));

        for (int i = 0; i < LDNodes.size(); i++) {
            // Convertimos las redundancias en codigo binario
            String LD = LDNodeToBinary(LDNodes.get(i), Mdest, Ment);

            // Aplicamos el formato de salida
            if (i == 0) {
                returnMessage.append(" " + LD + " ");
            } else {
                returnMessage.append(LD + " ");
            }
        }

        return returnMessage.toString();
    }

    /**
     * Función que encuentra las redundancias en el mensaje y las guarda en una lista
     *
     * @param message Mensaje que queremos comprimir
     * @param Ment Número que debe tener la ventana de entrada
     * @param Mdest Númeor que debe tener la ventana deslizante
     * @return La lista de nodos
     * @see LDNode
     */
    public static List<LDNode> findLDNodes(String message, int Mdest, int Ment) {
        List<LDNode> LDNodes = new ArrayList<>();

        while (message.length() > Ment) {
            // Comprobamos si quedan bits que no se han comprimido debido al tamaño de las ventanas
            if (Ment + Mdest > message.length()) {
                int numOfBits = message.length() - Mdest;
                String bits = message.substring(message.length() - numOfBits);
                LDNodes.add(new LDNode(bits));
                break;
            }

            // Cogemos los caracteres referentes a las ventanas de entrada y deslizante
            String largeBin = message.substring(0, Mdest);
            String smallBin = message.substring(Mdest, Ment + Mdest);

            // Encontramos las coincidencias entre estas
            LDNode node = findBinarySubstring(largeBin, smallBin);

            // Si no hay coincidencias hacemos la insercion de bit
            if (node.getPosition() == -1) {
                throw new IllegalStateException("No se encontraron coincidencias");
            }

            // Añadimos el nodo
            LDNodes.add(node);

            // Recortamos la longitud del mensaje
            int length = node.getLength();
            message = message.substring(length);
        }

        return LDNodes;
    }

    /**
     * Función que encuentra las redundancias entre la ventana deslizante y la de entrada
     *
     * @param largeBin Bits de la ventana deslizante
     * @param smallBin Bits de la ventana de entrada
     * @return Un LDNode indicando la posición y el número de bits que hay iguales
     * @see LDNode
     */
    public static LDNode findBinarySubstring(String largeBin, String smallBin) {
        int largeLength = largeBin.length();

        while (smallBin.length() > 0) {
            int position = findMatch(largeBin, smallBin);
            if (position != -1) {
                return new LDNode(smallBin.length(), largeLength - position);
            }
            smallBin = smallBin.substring(0, smallBin.length() - 1);
        }

        return new LDNode(-1, -1);
    }

    /**
     * Función que comprueba si dos cadenas de bits son iguales
     *
     * @param largeBin Cadena que viene de la ventana deslizante
     * @param smallBin Cadena que viene de la ventana de entrada
     * @return La posición donde empieza la redundancia
     */
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

    /**
     * Función que convierte el LDNode a binario
     *
     * @param LDNode Nodo que queremos convertir
     * @param Ment Número que debe tener la ventana de entrada
     * @param Mdest Número que debe tener la ventana deslizante
     * @return El codigo binario del nodo en String
     * @see LDNode
     */
    public static String LDNodeToBinary(LDNode LDNode, int Ment, int Mdest) {
        int MentLenght = (int) Math.round(Math.log(Ment) / Math.log(2));
        int MdestLength = (int) Math.round(Math.log(Mdest) / Math.log(2));

        String lengthBinary;
        String positionBinary;

        // Miramos si es el nodo final
        if (LDNode.isFinal()) {
            return LDNode.getValue();
        }

        // Si el valor de la longitud es el resultado de una potencia de 2 añadimos a zeros, si no el valor binario
        if (LDNode.getLength() == Mdest && LDNode.getLength() == Math.pow(2, MdestLength)) {
            lengthBinary = "0".repeat(MdestLength);
        } else {
            lengthBinary = String.
                    format("%" + MdestLength + "s", Integer.toBinaryString(LDNode.getLength()))
                    .replace(' ', '0');
        }

        // Si el valor de la posición es el resultado de una potencia de 2 añadimos a zeros, si no el valor binario
        if (LDNode.getPosition() == Ment && LDNode.getPosition() == Math.pow(2, MentLenght)) {
            positionBinary = "0".repeat(MentLenght);
        } else {
            positionBinary = String
                    .format("%" + MentLenght + "s", Integer.toBinaryString(LDNode.getPosition()))
                    .replace(' ', '0');
        }

        return lengthBinary + " " + positionBinary;
    }

    /**
     * Función que descomprime el mensaje a partir del algoritmo LZ77
     *
     * @param encodedMessage Mensaje que se quiere descomprimir
     * @param Mdest Número que debe tener la ventana deslizante
     * @param Ment Número que debe tener la ventana de entrada
     * @return El mensaje original
     */
    public static String decode(String encodedMessage, int Mdest, int Ment) {
        int MentLength = (int) Math.round(Math.log(Ment) / Math.log(2));
        int MdestLength = (int) Math.round(Math.log(Mdest) / Math.log(2));
        int separatorIndex = MentLength + MdestLength;

        // Eliminamos los espacios en blanco
        String message = encodedMessage.replace(" ", "");

        String initialMessage = message.substring(0, Mdest);
        String compressPart = message.substring(Mdest);
        StringBuilder result = new StringBuilder(initialMessage);

        for (int i = 0; i < compressPart.length(); i += separatorIndex) {
            if (i + separatorIndex <= compressPart.length()) {
                // Separamos las partes binarias comprimidas
                String LD = compressPart.substring(i, i + separatorIndex);
                String LBinary = LD.substring(0, MentLength);
                String DBinary = LD.substring(MentLength);
                int L, D;

                // Convertimos L a entero decimal
                if (LBinary.contains("0") && !LBinary.contains("1")) {
                    L = Ment;
                } else {
                    L = Integer.parseInt(LBinary, 2);
                }

                // Convertimos D a entero decimal
                if (DBinary.contains("0") && !DBinary.contains("1")) {
                    D = Mdest;
                } else {
                    D = Integer.parseInt(DBinary, 2);
                }

                // Añadimos el resultado
                int startIndex = result.length() - D;
                String LDResult = result.substring(startIndex, startIndex + L);
                result.append(LDResult);
            }
        }

        // Comprobamos si quedan bits en el mensaje que no se han comprimido
        int lastWhiteSpaceIndex = encodedMessage.trim().lastIndexOf(' ');
        if (lastWhiteSpaceIndex != -1) {
            String lastBinary = encodedMessage.substring(lastWhiteSpaceIndex).replace(" ", "");
            result.append(lastBinary);
        }

        return result.toString();
    }
}