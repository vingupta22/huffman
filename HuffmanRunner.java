import java.util.*;
import java.io.*;
import static java.lang.System.*;

public class HuffmanRunner {

   public static long startTime;
   
   public static void main(String[] args) {
      
      //String name = "mccoy";
      String name = "Hamlet";
      //String name = "War and Peace";
      //String name = "BeeMovieScript";
      
      String originalFile = name+".txt";
      String shortFile = name+".short";
      String codeFile = name+".code";
      String newFile = name+".new";
   
      out.println("Compressing " + originalFile + " into " + shortFile);
      startTimer();
      HuffmanCompressor.compress(originalFile, shortFile, codeFile);
      stopTimer();   
   
      out.println("\nExpanding " + shortFile + " into " + newFile);
      startTimer();
      HuffmanCompressor.expand(shortFile, codeFile, newFile);
      stopTimer(); 
      
      out.println();
      
      File og = null, comp = null, exp = null;
      
      try {
         og = new File(originalFile);
         comp = new File(shortFile);
         exp = new File(newFile);
      } catch (Exception e) { out.println("Some files didn't exist.\n" + e); }
   
      out.printf("Size of original file   >> %9d bytes%n", og.length());
      out.printf("Size of compressed file >> %9d bytes (%d%% of original size)%n", comp.length(), 100*comp.length()/og.length());     
      out.printf("Size of expanded file   >> %9d bytes%n%n", exp.length());
        
            
      if (exp.length() == og.length()) {
         out.println("Expanded file is same size as original file.  You mighta done it right.");
         out.println("Compare your " + originalFile + " to your " + newFile + " to verify.");
      }
      else  {
         out.println("Expanded file is NOT same size as original file.  Uh oh.");
         out.println("Common mistakes include: ");
         out.println(" - complete failure to understand this lab.");
         out.println(" - mishandling of the end of file");
         out.println(" - forgetting to close your output streams (last byte not written)");
      }
   }
   
   public static void startTimer() {
      startTime = System.currentTimeMillis();
   }
   
   public static void stopTimer() {
      long end = System.currentTimeMillis();
      float sec = (end - startTime) / 1000F; 
      System.out.println("Run time = " + sec + " seconds");
   }
      
   
}