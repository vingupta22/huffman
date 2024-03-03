import java.util.*;
import java.io.*;
public class HuffmanTree
{
   private Node root;
   private HashMap<Integer, String> codeMap;
   public HuffmanTree(int[] count)
   {
     Queue<Node> pqNode = new PriorityQueue<Node>();
     root = null;
     codeMap = new HashMap<Integer, String>();

     for(int i = 0; i < count.length; i++)
     {
       if(count[i] != 0)
       {
        Node tempChar = new Node((char)i, count[i]);
        pqNode.offer(tempChar);
       } 
     }
     Node EOF = new Node((char) 256, 1);
     pqNode.offer(EOF);
    while(pqNode.size() > 1)
    { 
     Node temp1 = pqNode.poll();
     Node temp2 = pqNode.poll();
     Node knew = new Node((char) 0, temp1.frequency + temp2.frequency);
     knew.left = temp1;
     knew.right = temp2;
     pqNode.offer(knew);   
    }
    
    root = pqNode.poll();  
   }
   
   public String getCode(Integer a)
   {
     return codeMap.get(a);
   }
   public void printTree()
   {
     TreePrinter.printTree(root); 
   }
   public void write(String fileName)
   {
     String outputFileName = fileName;
	   PrintWriter diskFile = null;
 	 try { 
       diskFile = new PrintWriter(new File(outputFileName)); 
     }
	  catch (IOException io) { 
        System.out.println("Could not create file: " + outputFileName); 
     }
     writerHelper(root, "", diskFile);
     diskFile.close();
   }
   
   public void writerHelper(Node current, String ret, PrintWriter writer)
   {
     if(current.left == null && current.right == null)
     {
         codeMap.put(current.val, ret); 
         ret = current.val + "\n" + ret;
         
         writer.println(ret);   
     }
    if(current.right != null)
     {      
       writerHelper(current.right, ret + "1", writer);
     }
     if(current.left != null)
     {     
       writerHelper(current.left, ret + "0", writer);
     }
   }
   
   public HuffmanTree(String codeFile)
   {
     codeMap = new HashMap<Integer, String>();
     try
     {
       Scanner console = new Scanner(new File(codeFile));     
       while(console.hasNextLine())
       {
         if(root == null)
         {
           root = new Node(0, 0);
         }
         int value = Integer.parseInt(console.nextLine());
         if(console.hasNextLine())
         {
          String temp = console.nextLine();
          codeMap.put(value, temp);
          root = tree(value, temp, root);
         } 
         
       }
     
     }
     catch (IOException io) 
     { 
       System.out.println("Could not create file" ); 
     }
   }
   
   public Node tree(int inte, String ret, Node current)
   {
     if(ret == "")
     {
       current.val = inte;
     }
     else if(ret.charAt(0) == '1')
     {
       if(current.right == null)
       {
         current.right = new Node(0, 0);       
       }
        current.right = tree(inte, ret.substring(1), current.right);             
     }
     else if(ret.charAt(0) == '0')
     {
       if(current.left == null)
       {
         current.left = new Node(0, 0);       
       }
        current.left = tree(inte, ret.substring(1), current.left);
     }
     
     return current;   
   }
   
   public void decode(BitInputStream in, String outFile)
   {
     int stream = in.readBit();
     String print = "";
     Node current = root;
     while(current.val != 256)
     {
       if(current.val != 256)
       {
         
         if(stream == 0)
         {
           current = current.left;
           if(current.val == 256) break;
           
         }
         if(stream == 1)
         {
           current = current.right;
           if(current.val == 256) break;
           
         }
         if(current.right == null && current.left == null)
         {
           if(current.val != 256) print += (char)current.val;
           current = root;
           
         }
         stream = in.readBit();
       }
     }
     PrintWriter diskFile = null;
 	  try { 
       diskFile = new PrintWriter(outFile);
       diskFile.print(print);
       diskFile.close(); 
     }
	  catch (IOException io) { 
        System.out.println("Could not create file: " + outFile); 
     }
   }
 }  

  class Node implements Comparable <Node>
  {
    public int val;
    public Node left, right;
    public Integer frequency;
    public Node(int val, Integer frequency) 
    {
      this.val = val;
      left = right = null;
      this.frequency = frequency;      
    }
      
    @Override
    public String toString() 
    { 
      if((int) val == 32) return "SPACE";
      if((int) val == 10) return "NL";
      if((int) val == 0) 
         return "" + frequency;
      if(val == 256) return "END";
      return "" + (char) this.val;
    }
    
    public int compareTo(Node other)
   {
     if(other.frequency == this.frequency)
     {
       return 0;
     }
     if(other.frequency > this.frequency)
     {
       return -1;
     }
     return 1;
   }
   }

  