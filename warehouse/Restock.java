package warehouse;

public class Restock {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        Warehouse newObj = new Warehouse();
        int numofrestock = StdIn.readInt();
        for(int i = 0; i<numofrestock;i++){
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
                int demand = StdIn.readInt();
                newObj.restockProduct(id, demand);
            }
        }
        StdOut.println(newObj);
    }
}




