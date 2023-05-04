/**
 * Classe que representa les coincidencies de l'algoritme LZ77
 *
 * @author Sergio Lopez
 * @author Jordi Bujaldón
 */
public class LDNode {

    private final int position;
    private final int offset;

    /**
     * Constructor principal que rep la posició i el desplaçament
     *
     * @param offset Desplaçament de bits
     * @param position Posició on comença el desplaçament
     */
    public LDNode(int offset, int position) {
        this.position = position;
        this.offset = offset;
    }

    /**
     * Retorna la posició de la coincidencia
     *
     * @return La posició
     */
    public int getPosition() {
        return position;
    }

    /**
     * Retorna el desplaçament de bits de la coincidencia
     *
     * @return El desplaçament
     */
    public int getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "(" + offset + ", " + position + ")";
    }
}
