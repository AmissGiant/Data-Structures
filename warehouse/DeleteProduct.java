package warehouse;

/*
 * Use this class to test the deleteProduct method.
 */ 
public class DeleteProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        Warehouse newObj = new Warehouse();
        int numofdelete = StdIn.readInt();
        for(int i = 0; i<numofdelete;i++){
            String decide = StdIn.readString();
            if(decide.equals("add")){
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();
                newObj.addProduct(id, name, stock, day,demand);
            }else{
                int id = StdIn.readInt();
                newObj.deleteProduct(id);
            }
            }
            StdOut.println(newObj);
        }
}