package interviews;
import java.util.HashSet;
import java.util.Set;
public class MobileKeypad {

    public static void main(String[] args) {
        System.out.println("Possible 6-digit numbers with given constraints:");
        generateNumbers("", 6);
    }

    public static void generateNumbers(String number, int n) {
        if (n == 0) {
            System.out.println(number);
            return;
        }

        char lastDigit = number.isEmpty() ? '\0' : number.charAt(number.length() - 1);
        int row = -1;
        int col = -1;

        // Find the row and column of the last digit
        char[][] keypad = {{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}, {'*', '0', '#'}};
        for (int i = 0; i < keypad.length; i++) {
            for (int j = 0; j < keypad[i].length; j++) {
                if (keypad[i][j] == lastDigit) {
                    row = i;
                    col = j;
                    break;
                }
            }
            if (row != -1) {
                break;
            }
        }

        // Loop through all possible adjacent digits
        int[] rowOffset = {-1, 0, 0, 1};
        int[] colOffset = {0, -1, 1, 0};
        for (int i = 0; i < rowOffset.length; i++) {
            int nextRow = row + rowOffset[i];
            int nextCol = col + colOffset[i];
            if (nextRow >= 0 && nextRow < keypad.length && nextCol >= 0 && nextCol < keypad[nextRow].length && keypad[nextRow][nextCol] != '*' && keypad[nextRow][nextCol] != '#') {
                String nextNumber = number + keypad[nextRow][nextCol];
                generateNumbers(nextNumber, n - 1);
            }
        }
    }
}
