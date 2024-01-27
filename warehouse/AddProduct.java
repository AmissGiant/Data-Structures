package warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        Warehouse newObj = new Warehouse();
        int numofprod = StdIn.readInt();
        for(int i = 0; i<numofprod;i++){
            int Day = StdIn.readInt();
            int ID = StdIn.readInt();
            String productname = StdIn.readString();
            int stock = StdIn.readInt();
            int demand = StdIn.readInt();
            newObj.addProduct(ID,productname,stock,Day,demand);
            StdOut.println(newObj);
        }
    }
}
