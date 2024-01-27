package warehouse;

public class PurchaseProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        Warehouse newObj = new Warehouse();
        int numCommands = StdIn.readInt();
        for (int i = 0; i < numCommands; i++) {
            String command = StdIn.readString();
            if (command.equals("add")) {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();
                newObj.addProduct(id, name, stock, day, demand);
            } else if (command.equals("purchase")) {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                int amount = StdIn.readInt();
                newObj.purchaseProduct(id, day, amount);
            }
            StdOut.println(newObj);
        }
    }
}