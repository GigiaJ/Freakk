/**
 * @author		GigiaJ
 * @filename	InputType.java
 * @date		Mar 27, 2020
 * @description 
 */

package messages;

enum InputType {
    MOUSE(0),
    KEYBOARD(1);

    private final int value;

    private InputType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
