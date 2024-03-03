/**
 * Clase que guarda la información sobre las redundancias del
 * algoritmo LZ77
 *
 * @author Sergio López
 * @author Jordi Bujaldón
 */
public class LDNode {
    private int length;
    private int position;
    private boolean isFinal;
    private String value;

    /**
     * Constructor dónde se almacena la longitud y la posición
     * sobre la redundancia
     *
     * @param length Longitud de bits
     * @param position Posición donde empieza la redundancia
     */
    public LDNode(int length, Integer position) {
        this.length = length;
        this.position = position;
        // Este nodo nunca es el final y no guardamos ningún valor
        this.isFinal = false;
        this.value = "";
    }

    /**
     * Constructor que almacena un valor final. Se utiliza cuando
     * se quiere ver el último valor de el mensaje comprimido
     *
     * @param value Valor que se almacena
     */
    public LDNode(String value) {
        this.length = 0;
        this.position = 0;
        this.value = value;
        // Ponemos que es el final
        this.isFinal = true;
    }

    /**
     * Función que devuelve la longitud
     *
     * @return Longitud
     */
    public int getLength() {
        return length;
    }

    /**
     * Función que devuelve la posición
     *
     * @return Posición
     */
    public int getPosition() {
        return position;
    }

    /**
     * Función que devuelve si es el nodo final
     *
     * @return Si es final o no
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * Función que devuelve el valor
     *
     * @return Valor de bits
     */
    public String getValue() {
        return value;
    }
}