import java.util.*;
import java.io.*;

public class TestGUIRunnerHuffman extends TestGUI.TestData{ 
   public static void main (String[] args) {        
      new TestGUI();   
   }

   public static void runTests() {  //Your test sequence must be within a method called runTests()
      setTimeOutSec(3);
   
      header("Huffman Coding Lab - Testing the tools used to compress");
      srcButton("HuffmanTree, HuffmanCompressor");
   
      deleteFilesIfExist();
   
      if (! makeTestFile())
         message("Unable to create test file. Notify your teacher.", false);
      else
         message("Temporary test file mccoy.txt created.");
   
      message("This tester uses a temporary test file called mccoy.txt.\nIt contains 16 'a' characters, 4 'b' characters, and 1 newline '\\n' characters.\nIt looks like this:\n"+getFileAsString("mccoy.txt"));
   
      Object o = testMethod("HuffmanCompressor", "getFrequency", new Object[]{"mccoy.txt"}, "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]");
      if (! (o instanceof int[])) {
         message("Unable to verify letter frequency.  No further tests can be run.", false);
         return;
      }
   
   
      int[] freq = (int[]) o;
      if (freq.length == 257)
         message("Your frequency array is length 257 instead of 256.  That is probably why the previous test was considered wrong.  This doesn't actually mean you are wrong, though.  It just means that you included the EOF character in the count instead of simply adding it into the HuffmanTree constructor.", false);
   
      boolean t = freq[97] == 16;
      message("Frequency of 97 (letter 'a') should be 16.  You said: " + freq[97], t);   
      t = freq[98] == 4;
      message("Frequency of 98 (letter 'b') should be 4.  You said: " + freq[98], t);   
      t = freq[10] == 1;
      message("Frequency of 10 (letter '\\n') should be 1.  You said: " + freq[10], t);   
   
      o = makeObject("HuffmanTree", new Object[] { freq });
   
      testMethod(o, "printTree", null);
   
      testMethod(o, "write", new Object[] {"mccoy.code"});
      message("Your code file (mccoy.code) looks like this.\nThe path for 97 ('a') should be only 1 bit long (a single 0 or a 1)\n"+getFileAsString("mccoy.code"));
   
      header("Huffman Coding Lab - Testing the full compress method");
      testMethod("HuffmanCompressor", "compress", new Object[] {"mccoy.txt", "mccoy.short", "mccoy.code"});
      message("Your code file (mccoy.code) as generated by full compress.\nThe path for 97 ('a') should be only 1 bit long (a single 0 or a 1)\n"+getFileAsString("mccoy.code"));
   
      String bits = getBits("mccoy.short");
      if(bits != null) {
         t = bits.length() == 32;
         message("Your compressed version (mccoy.short) looks like:\n" + bits);
         message("Compressed version should contain 32 bits. Yours contains: " + bits.length(), t); 
         if (!t) message("If you are off by exactly 1 byte (8 bits), it might be due to mishandling of the end of file or forgetting to close your output stream (meaning the final byte was never written to file.)");  
      
         header("Huffman Coding Lab - Rebuilding the HuffmanTree");
         message("Attempting to build a HuffmanTree from your mccoy.code.  The resulting tree should have the same pathways as when you built the tree using the frequency array.  If it has different pathways, either your .short file is wrong or your constructor is wrong.");
         o = makeObject("HuffmanTree", new Object[] { "mccoy.code" });
         testMethod(o, "printTree", null);
         
                  
         header("Huffman Coding Lab - Expanding it back to normal size");
         message("Attempting to fully expand the mccoy.short file that you made.  The resulting file 'should' be mccoy.new");
         testMethod("HuffmanCompressor", "expand", new Object[] {"mccoy.short", "mccoy.code", "mccoy.new"});
         message("Your expanded file (mccoy.new) looks like this:\n"+getFileAsString("mccoy.new"));
         if (getFileAsString("mccoy.new").trim().equals(getFileAsString("mccoy.txt").trim()))
            message("Good job. Expanded version matches the original version.", true);
         else
            message("Uh oh. Expanded version DOES NOT match the original version.", false);
      
      }else{
         message("Cannot complete the expansion tests for mccoy.short because mccoy.short was not created.", false);
      }
   
      header("Huffman Coding - Expanding a secret message");
   
      if (! makeSecretMessageFile())
         message("Unable to create secret message files. Notify your teacher.", false);
      else
         message("secretmessage.code and secretmessage.short were created successfully.");
   
      o = makeObject("HuffmanTree", new Object[] { "secretmessage.code" });
      testMethod(o, "printTree", null);
   
      testMethod("HuffmanCompressor", "expand", new Object[] {"secretmessage.short","secretmessage.code", "secretmessage.new"});
      String fileAsString = getFileAsString("secretmessage.new");      
      if (fileAsString.length()==104) {
      message("Your expanded version of the secret message looks like this:\n"+fileAsString);
      message("Character count is correct.", true);
      }
      else message("Your exanded version of the secret message looks like this:\n"+fileAsString+"\n\nThis is the wrong number of characters.  Something went wrong.", false);
   
   }    
   public static boolean makeTestFile() {
      PrintWriter diskFile = null;
      try { 
         diskFile = new PrintWriter(new File("mccoy.txt")); 
      }
      catch (IOException io) { 
         return false; }
   
      diskFile.print("aaaaaaaaaa\nbbbbaaaaaa");
      diskFile.close();
      return true;
   }

   public static boolean makeSecretMessageFile() {
      PrintWriter diskFile = null;
      BitOutputStream bos = null;
      try { 
         diskFile = new PrintWriter(new File("secretmessage.code"));
         diskFile.print("115\n00\n32\n0100\n33\n01010\n256\n01011\n108\n0110\n10\n0111\n97\n1\n");
         diskFile.close();
         bos = new BitOutputStream("secretmessage.short");
         for (char bit : "1111111111111111111111111111111111111111110111001011000101000010110001010000101100010101001111111111111111111111111111111111111111111110101100000".toCharArray())            
            bos.writeBit(Integer.parseInt(""+bit));
         bos.close();
         return true;
      }
      catch (IOException io) { 
         return false; }             
   }

   public static String getFileAsString(String fileName) {
      String ret = "";
      try {
         BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"));
         int let = in.read();
         while (let != -1) {
            ret += (char)let;
            let = in.read();
         }   
         in.close();
      } 
      catch (Exception e) { 
         return null; }
      return ret;
   }

   public static String getBits(String fileName) {
      String ret = "";
      try {
         BitInputStream in = new BitInputStream(fileName);
         int bit = in.readBit();
         while (bit != -1) {
            ret += bit;
            bit = in.readBit();
         }   
         in.close();
      } 
      catch (Exception e) { 
         return null; }
      return ret;
   
   }
   
   public static void deleteFilesIfExist() {
      try {
         String[] files = {"mccoy.txt", "mccoy.short", "mccoy.code", "mccoy.new", "secretmessage.code", "secretmessage.short", "secretmessage.txt", "secretmessage.new"};
         for (String f : files) {
            File file = new File(f);
            if (file.exists())
               file.delete();                     
         }
      } catch (Exception e) { }
   }  

}