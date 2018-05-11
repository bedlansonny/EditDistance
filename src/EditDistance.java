import java.io.*;
import java.util.*;

public class EditDistance
{
    public static void main(String args[]) throws IOException
    {
        Scanner in = new Scanner(new File("input.txt"));

        int c = in.nextInt();
        for(int ci = 0; ci < c; ci++)
        {
            String a = in.next();
            String b = in.next();
            System.out.println(distance(a,b));
        }
    }

    static int distance(String a, String b)     //b is x, a is y
    {
        //memo table
        int[][] d = new int[a.length()+1][b.length()+1];

        //base case - filling top row
        for(int bi = 0; bi < d[0].length; bi++)
            d[0][bi] = bi;
        //base case - filling left column
        for(int ai = 0; ai < d.length; ai++)
            d[ai][0] = ai;

        //recurrence
        for(int ai = 1; ai < d.length; ai++)
        {
            for(int bi = 1; bi < d[0].length; bi++)
            {
                if(a.charAt(ai-1) == b.charAt(bi-1))
                    d[ai][bi] = d[ai-1][bi-1];
                else
                    d[ai][bi] = Math.min(d[ai-1][bi-1]+1, Math.min(d[ai-1][bi]+1, d[ai][bi-1]+1));
            }
        }

        return d[d.length-1][d[0].length-1];
    }
}
