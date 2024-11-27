package ERP.Utils;

import java.util.Scanner;

public class Console {
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void waitEnter() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter to continue...");
        scanner.nextLine();  // Waits for the Enter key to be pressed
    }
}
