package bean;

/**
 * Created by charrier on 14/11/16.
 */
public enum Right {
    READ(0), WRITE(1), ADD_MEMBER(2), MODIFY_RIGHT(3);
    private final int value;

    private Right(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}
