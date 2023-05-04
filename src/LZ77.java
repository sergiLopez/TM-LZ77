import java.util.ArrayList;
import java.util.List;

/**
 * Algoritme LZ77
 *
 * @author Sergio Lopez
 * @author Jordi Bujaldón
 */
public class LZ77 {

    private String message;
    private final int Mdes;
    private final int Ment;
    private final List<LDNode> coincidences;
    private final List<Integer> bitInsertionsIndexs;

    /**
     * Constructor que rep el missatge a comprimir i les mides de les finestres
     *
     * @param message Missatge a comprimir
     * @param Mdes Mida de la finestra lliscant
     * @param Ment Mida de la finestra d'entrada
     */
    public LZ77(String message, int Mdes, int Ment) {
        this.message = message;
        if (Ment > Mdes) {
            throw new IllegalStateException("La finestra d'entrada no pot ser mes gran que la lliscant");
        }
        if ((Mdes + Ment) > message.length()) {
            throw new IllegalStateException("La mida de les finestres no ha de ser mes gran que la mida del missatge");
        }
        this.Mdes = Mdes;
        this.Ment = Ment;
        this.coincidences = new ArrayList<>();
        this.bitInsertionsIndexs = new ArrayList<>();
    }

    /**
     * Mètode que comprimeix el missatge amb l'algoritme LZ77
     *
     * @return El missatge comprimit
     */
    public String encode() {
        updateMessageEncode();
        String tempMessage = message;
        String remainingBits = "";

        while (tempMessage.length() >= Ment) {
            if (Mdes + Ment > tempMessage.length()) {
                remainingBits = tempMessage.substring(Mdes);
                break;
            } else {
                String messageMdes = tempMessage.substring(0, Mdes);
                String messageMent = tempMessage.substring(Mdes, Mdes + Ment);

                LDNode node = findCoincidence(messageMdes, messageMent);
                coincidences.add(node);

                tempMessage = tempMessage.substring(node.getOffset());
            }
        }

        return formatMessage() + remainingBits;
    }

    /**
     * Mètode que actualitza el missatge utilitzant la tècnica d'inserció de bits per a que hi hagi
     * sempre coincidencies
     */
    private void updateMessageEncode() {
        StringBuilder result = new StringBuilder(message);

        for (int i = 0; i < result.length(); i++) {
            if ((i + Mdes) < result.length()) {
                String sequence = result.substring(i, i + Mdes - 1);

                if (sequence.contains("0") && !sequence.contains("1")) {
                    result.insert(i + Mdes - 1, '1');
                    bitInsertionsIndexs.add(i + Mdes - 1);
                } else if (sequence.contains("1") && !sequence.contains("0")) {
                    result.insert(i + Mdes - 1, '0');
                    bitInsertionsIndexs.add(i + Mdes - 1);
                }
            }
        }

        message = result.toString();
    }

    /**
     * Mètode que retorna la coincidencia de les finestres
     *
     * @param messageMdes Cadena de la finestra lliscant
     * @param messageMent Cadena de la finestra d'entrada
     * @return un LDNode amb la informació de la coincidencia
     */
    private LDNode findCoincidence(String messageMdes, String messageMent) {
        LDNode findedNode = null;
        boolean find = false;
        int offset = -1, position = -1;

        for (int i = messageMent.length() - 1; i >= 0; i--) {
            for (int j = 0; j <= messageMdes.length() - i; j++) {
                if (j + i < messageMdes.length()) {
                    String checkMessageEnt = messageMent.substring(0, i + 1);
                    String checkMessageDes = messageMdes.substring(j, j + i + 1);
                    if (checkMessageDes.equals(checkMessageEnt)) {
                        offset = i + 1;
                        position = messageMdes.length() - j;
                        find = true;
                    }
                }
            }
            if (find) {
                findedNode = new LDNode(offset, position);
                break;
            }
        }

        return findedNode;
    }

    /**
     * Mètode que formateja el missatge comprimit
     *
     * @return El missatge amb el format per veure les coincidencies
     */
    private String formatMessage() {
        int MentLength = (int) Math.ceil(Math.log(Ment) / Math.log(2));
        int MdesLength = (int) Math.ceil(Math.log(Mdes) / Math.log(2));

        StringBuilder messageResult = new StringBuilder();
        String messageMdesMent = message.substring(0, Mdes);
        messageResult.append(messageMdesMent).append(" ");

        String L, D;

        for (LDNode node: coincidences) {
            int numOfBits;
            if (node.getOffset() == Ment && node.getOffset() == Math.pow(2, MentLength)) {
                numOfBits = 0;
            } else {
                numOfBits = node.getOffset();
            }
            L = String.format("%" + MentLength + "s", Integer.toBinaryString(numOfBits)).replace(' ', '0');

            if (node.getPosition() == Mdes && node.getPosition() == Math.pow(2, MdesLength)) {
                numOfBits = 0;
            } else {
                numOfBits = node.getPosition();
            }
            D = String.format("%" + MdesLength + "s", Integer.toBinaryString(numOfBits)).replace(' ', '0');

            String result = L + " " + D + " ";
            messageResult.append(result);
        }

        return messageResult.toString();
    }

    /**
     * Mètode que descomprimeix el missatge passat utilitzant LZ77
     *
     * @param encodedMessage Missatge a descomprimir
     * @return El missatge originalº
     */
    public String decode(String encodedMessage) {
        String trimMessage = encodedMessage.replace(" ", "");
        String initialPart = trimMessage.substring(0, Mdes);

        StringBuilder result = new StringBuilder(initialPart);

        for (int i = 0; i < coincidences.size(); i++) {
            LDNode node = coincidences.get(i);
            int D = node.getPosition();
            int L = node.getOffset();

            int startIndex = result.length() - D;
            String LDResult = result.substring(startIndex, startIndex + L);

            result.append(LDResult);
        }

        if (result.length() < message.length()) {
            result.append(message.substring(result.length()));
        }

        return updateResultDecode(result.toString());
    }

    /**
     * Mètode que elimina les insercions de bits fetes al mètode encodd
     *
     * @param result El missatge de la descompressió
     * @return El missatge original
     */
    private String updateResultDecode(String result) {
        StringBuilder updatedResult = new StringBuilder(result);

        for (Integer index: bitInsertionsIndexs) {
            updatedResult.deleteCharAt(index);
        }

        return updatedResult.toString();
    }
}
