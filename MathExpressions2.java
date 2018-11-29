import java.util.Scanner;

// This is a comment line
public class MathExpressions2 {

 public static void main(String[] args) {
  Scanner in = new Scanner(System.in);  // Scanner for keyboard input
  
  System.out.print("Enter the temperature in Celsius:  ");
  int celsiusTemp = in.nextInt();  
  
  double fahrenheitTemp = (double)9/(double)5*(double)celsiusTemp + (double)32;
  
  System.out.print("\nYou entered a Celsius Temperature of " + celsiusTemp + " degrees.");
  System.out.print("\n\nThat is equivalent to " + fahrenheitTemp + " degrees Fahrenheit.");
 }

}