import java.util.*;
import java.io.*;
public class HuffmanCompressor
{
  public static int[] getFrequency(String fileName) {
        int[] freq = new int[256];
        BufferedReader myFile = null;
        try {
            myFile = new BufferedReader(new FileReader(fileName));
        }
        catch (IOException e) { System.out.println("Could not find file:" + fileName); }
        try {
            int charValue;
            while (myFile.ready()) { 
                charValue = myFile.read(); 
                freq[charValue]+=1;
            }
        } catch (IOException e) { System.out.println("Error reading the file" + e); }
        return freq;
    }
    
    public static void compress(String txtFile, String nameOfShortFileToBeCreated, String codeFileToBeCreated)
    {
      HuffmanTree tree = new HuffmanTree(getFrequency(txtFile));
      tree.write(codeFileToBeCreated);
      BufferedReader myFile = null;
        try {
            myFile = new BufferedReader(new FileReader(txtFile));
        }
        catch (IOException e) { System.out.println("Could not find file:" + txtFile); }
        try {
            BitOutputStream stream = new BitOutputStream(nameOfShortFileToBeCreated);
            int charValue;
            while (myFile.ready()) { 
                charValue = myFile.read(); 
                String code = tree.getCode(charValue);
                for(int i = 0; i < code.length();)
                {
                  if(code.charAt(0) == '1')
                  {
                    stream.writeBit(1);
                  }
                  else if(code.charAt(0) == '0')
                  {
                    stream.writeBit(0);
                  }
                  
                  code = code.substring(1);
                }
            }
            String end = tree.getCode(256);
            for(int i = 0; i < end.length();)
            {
               if(end.charAt(0) == '1')
                  {
                    stream.writeBit(1);
                  }
                  else if(end.charAt(0) == '0')
                  {
                    stream.writeBit(0);
                  }
                  end = end.substring(1);
            }
            stream.close();
          } catch (IOException e) { System.out.println("Error reading the file" + e); }
    }
    
    
    
    public static void expand(String shortFile, String codeFile, String nameOfNewFileToBeCreated)
    {
      HuffmanTree tree = new HuffmanTree(codeFile);
      tree.decode(new BitInputStream(shortFile), nameOfNewFileToBeCreated);
    }
}