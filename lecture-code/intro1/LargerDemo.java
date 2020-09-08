public class LargerDemo{
    /** Return the larger number */
    public static int Larger(int x, int y){
        if (x < y){
            return y;
        }
        return x;
    }

    public static void main(String[] args){
        System.out.print(Larger(5, 10));
    }
}