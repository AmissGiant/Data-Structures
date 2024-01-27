package huffman;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
/**
* This class contains methods which, when used together, perform the
* entire Huffman Coding encoding and decoding process
*
* @author Ishaan Ivaturi
* @author Prince Rawal
*/
public class HuffmanCoding {
 private String fileName;
 private ArrayList<CharFreq> sortedCharFreqList;
 private TreeNode huffmanRoot;
 private String[] encodings;
 /**
  * Constructor used by the driver, sets filename
  * DO NOT EDIT
  * @param f The file we want to encode
  */
 public HuffmanCoding(String f) {
     fileName = f;
 }
 /**
  * Reads from filename character by character, and sets sortedCharFreqList
  * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
  */
public void makeSortedList() {
   StdIn.setFile(fileName);
   int[] freq = new int[128];
   int totalChars = 0;
   while (StdIn.hasNextChar()) {
       char c = StdIn.readChar();
       freq[c]++;
       totalChars++;
   }
   sortedCharFreqList = new ArrayList<>();
   for (int i = 0; i < freq.length; i++) {
       if (freq[i] > 0) {
           double prob = (double) freq[i] / totalChars;
           sortedCharFreqList.add(new CharFreq((char) i, prob));
       }
   }
   if (sortedCharFreqList.size() == 1) {
       char c = sortedCharFreqList.get(0).getCharacter();
       char other = (char) ((c + 1) % 128);
       sortedCharFreqList.add(new CharFreq(other, 0));
   }
   Collections.sort(sortedCharFreqList);
}
 /**
  * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
  * in huffmanRoot
  */
public void makeTree() {
    Queue<TreeNode> sQ = new Queue<TreeNode>();// makes a queue for treenodes
    Queue<TreeNode> tQ = new Queue<TreeNode>();
    for(int i = 0; i<sortedCharFreqList.size(); i++){
        TreeNode NewNode = new TreeNode(); // creates a new treeNode
        CharFreq NewChar = new CharFreq();// creates a new CharFreq node
        NewChar = sortedCharFreqList.get(i); //Sets the index char freq obj in sorted to a new obj
        NewNode.setData(NewChar); //sets the sorted index charFreq in the tree node
        sQ.enqueue(NewNode);
     }
     while (sQ.size() + tQ.size() > 1) {
        TreeNode l = new TreeNode(); //left
        TreeNode r = new TreeNode(); //right
        if (sQ.size() > 0 && (tQ.size() == 0 || sQ.peek().getData().getProbOcc() <= tQ.peek().getData().getProbOcc())) {
            l = sQ.dequeue();
        } else {
            l = tQ.dequeue();
        }
        if (sQ.size() > 0 && (tQ.size() == 0 || sQ.peek().getData().getProbOcc() <= tQ.peek().getData().getProbOcc())) {
            r = sQ.dequeue();
        } else {
            r = tQ.dequeue();
        }
        double prob = l.getData().getProbOcc() + r.getData().getProbOcc();
        CharFreq combinedCharFreq = new CharFreq('\0', prob);
        TreeNode combinedNode = new TreeNode(combinedCharFreq, l, r);
        tQ.enqueue(combinedNode);
        }
        huffmanRoot = tQ.dequeue();
    }


 /**
  * Uses huffmanRoot to create a string array of size 128, where each
  * index in the array contains that ASCII character's bitstring encoding. Characters not
  * present in the huffman coding tree should have their spots in the array left null.
  * Set encodings to this array.
  */
 public void makeEncodings() {
   encodings = new String[128];
   makeEncodingsHelper(huffmanRoot, "");
}
private void makeEncodingsHelper(TreeNode node, String encoding) {
   if (node.getLeft() == null && node.getRight() == null) {
       char c = node.getData().getCharacter();
       encodings[(int) c] = encoding;
       return;
   }
   if (node.getLeft() != null) {
       makeEncodingsHelper(node.getLeft(), encoding + "0");
   }
   if (node.getRight() != null) {
       makeEncodingsHelper(node.getRight(), encoding + "1");
   }
}


 /**
  * Using encodings and filename, this method makes use of the writeBitString method
  * to write the final encoding of 1's and 0's to the encoded file.
  *
  * @param encodedFile The file name into which the text file is to be encoded
  */
 public void encode(String encodedFile) {
   FileInputStream inputFile = null;
   FileOutputStream outputFile = null;
   try {
       inputFile = new FileInputStream(fileName);
       StringBuilder encodedBits = new StringBuilder();
       int nextByte;
       while ((nextByte = inputFile.read()) != -1) {
           String encoding = encodings[nextByte];
           encodedBits.append(encoding);
       }
       byte[] bytes = new byte[encodedBits.length() / 8];
       for (int i = 0; i < bytes.length; i++) {
           String byteString = encodedBits.substring(i * 8, (i + 1) * 8);
           bytes[i] = (byte) Integer.parseInt(byteString, 2);
       }
       outputFile = new FileOutputStream(encodedFile);
       outputFile.write(bytes);
   } catch (Exception e) {
       e.printStackTrace();
   } finally {
       try {
           if (inputFile != null) {
               inputFile.close();
           }
           if (outputFile != null) {
               outputFile.close();
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

}
 /**
  * Writes a given string of 1's and 0's to the given file byte by byte
  * and NOT as characters of 1 and 0 which take up 8 bits each
  * DO NOT EDIT
  *
  * @param filename The file to write to (doesn't need to exist yet)
  * @param bitString The string of 1's and 0's to write to the file in bits
  */
 public static void writeBitString(String filename, String bitString) {
     byte[] bytes = new byte[bitString.length() / 8 + 1];
     int bytesIndex = 0, byteIndex = 0, currentByte = 0;


     // Pad the string with initial zeroes and then a one in order to bring
     // its length to a multiple of 8. When reading, the 1 signifies the
     // end of padding.
     int padding = 8 - (bitString.length() % 8);
     String pad = "";
     for (int i = 0; i < padding-1; i++) pad = pad + "0";
     pad = pad + "1";
     bitString = pad + bitString;
     // For every bit, add it to the right spot in the corresponding byte,
     // and store bytes in the array when finished
     for (char c : bitString.toCharArray()) {
         if (c != '1' && c != '0') {
             System.out.println("Invalid characters in bitstring");
             return;
         }
         if (c == '1') currentByte += 1 << (7-byteIndex);
         byteIndex++;
      
         if (byteIndex == 8) {
             bytes[bytesIndex] = (byte) currentByte;
             bytesIndex++;
             currentByte = 0;
             byteIndex = 0;
         }
     }
     // Write the array of bytes to the provided file
     try {
         FileOutputStream out = new FileOutputStream(filename);
         out.write(bytes);
         out.close();
     }
     catch(Exception e) {
         System.err.println("Error when writing to file!");
     }
 }
 /**
  * Using a given encoded file name, this method makes use of the readBitString method
  * to convert the file into a bit string, then decodes the bit string using the
  * tree, and writes it to a decoded file.
  *
  * @param encodedFile The file which has already been encoded by encode()
  * @param decodedFile The name of the new file we want to decode into
  */
public void decode(String encodedFile, String decodedFile) {
  StdOut.setFile(decodedFile);
  String bitString = readBitString(encodedFile);
  byte[] bytes = new byte[bitString.length() / 8];
  for (int i = 0; i < bitString.length(); i += 8) {
      String byteString = bitString.substring(i, i + 8);
      bytes[i / 8] = (byte) Integer.parseInt(byteString, 2);
  }
   TreeNode node = huffmanRoot;
  for (byte b : bytes) {
      for (int i = 7; i >= 0; i--) {
          int bit = (b >> i) & 1;
          if (bit == 0) {
              node = node.getLeft();
          } else {
              node = node.getRight();
          }
          if (node.getLeft() == null && node.getRight() == null) {
              StdOut.print(node.getData().getCharacter());
              node = huffmanRoot;
              }
          }
      }
       StdOut.close();
  }
 /**
  * Reads a given file byte by byte, and returns a string of 1's and 0's
  * representing the bits in the file
  * DO NOT EDIT
  *
  * @param filename The encoded file to read from
  * @return String of 1's and 0's representing the bits in the file
  */
 public static String readBitString(String filename) {
     String bitString = "";
  
     try {
         FileInputStream in = new FileInputStream(filename);
         File file = new File(filename);


         byte bytes[] = new byte[(int) file.length()];
         in.read(bytes);
         in.close();
      
         // For each byte read, convert it to a binary string of length 8 and add it
         // to the bit string
         for (byte b : bytes) {
             bitString = bitString +
             String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
         }
         // Detect the first 1 signifying the end of padding, then remove the first few
         // characters, including the 1
         for (int i = 0; i < 8; i++) {
             if (bitString.charAt(i) == '1') return bitString.substring(i+1);
         }
      
         return bitString.substring(8);
     }
     catch(Exception e) {
         System.out.println("Error while reading file!");
         return "";
     }
 }
 /*
  * Getters used by the driver.
  * DO NOT EDIT or REMOVE
  */
 public String getFileName() {
     return fileName;
 }
 public ArrayList<CharFreq> getSortedCharFreqList() {
     return sortedCharFreqList;
 }


 public TreeNode getHuffmanRoot() {
     return huffmanRoot;
 }


 public String[] getEncodings() {
     return encodings;
 }
}





