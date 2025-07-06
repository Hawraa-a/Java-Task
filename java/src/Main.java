//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String[] names = {"Nasser", "Haitham", "Manal", "Hanin"};
        String[] rNames = reverNames(names);

        System.out.print("{ ");

        for (int i = 0; i < names.length; i++) {
            System.out.print(rNames[i]);

            if (i < rNames.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("} ");
    }
    public static String[] reverNames(String[] names) {
        String[] reversed = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            reversed[i] = new StringBuilder(names[i]).reverse().toString();

        }
        return reversed;
    }
}


