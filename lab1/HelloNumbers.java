public class HelloNumbers{
    /** print the numbers until the loop stop */
    public static void main(String[] args){
        int x = 0；
        int total = 0;
        while (x < 10){
        	total = total + x;
            System.out.print(total + " ");
            x = x + 1;
        }
    }
}