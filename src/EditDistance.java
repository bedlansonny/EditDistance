import java.io.*;
import java.util.*;

public class EditDistance
{
    static PrintWriter fileWriter;
    static int[][] d;
    static String a;
    static String b;

    public static void main(String args[]) throws IOException
    {
        Scanner in = new Scanner(new File("input.txt"));
        //fileWriter = new PrintWriter(new File("output.csv"));

        int c = in.nextInt();
        for(int ci = 0; ci < c; ci++)
        {
            a = in.next();
            b = in.next();
            System.out.println(distance());
            //exportData();
            System.out.println(trace() + "\n");
        }
        //fileWriter.close();
    }

    static ArrayDeque<String> trace()
    {
        int r = d.length-1;
        int c = d[0].length-1;
        String temp = a;
        ArrayDeque<String> steps = new ArrayDeque<>();
        steps.addLast(temp);

        while(r > 0 || c > 0)
        {
            if(r > 0 && c > 0 && a.charAt(r-1) == b.charAt(c-1))
            {
                //no change
                r = r-1;
                c = c-1;
            }
            else if(c > 0 && d[r][c-1] == d[r][c] - 1)
            {
                //insert
                if(r > temp.length())
                    temp = temp + b.charAt(c-1);
                else
                    temp = temp.substring(0,r)+b.charAt(c-1)+temp.substring(r);
                steps.addLast(temp);
                c = c-1;
            }
            else if(r > 0 && d[r-1][c] == d[r][c] - 1)
            {
                //delete
                temp = temp.substring(0,r-1)+temp.substring(r);
                steps.addLast(temp);
                r = r-1;
            }
            else if(r > 0 && c > 0 && d[r-1][c-1] == d[r][c] - 1)
            {
                //replace
                temp = temp.substring(0,r-1)+b.charAt(c-1)+temp.substring(r);
                steps.addLast(temp);
                r = r-1;
                c = c-1;
            }
            else
            {
                System.out.println("\n\n\n\nERROR - UNHANDLED CASE\n\n\n\n");
            }
        }

        return steps;
    }

    static int distance() throws IOException
    {
        //memo table
        d = new int[a.length()+1][b.length()+1];

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

    static void exportData() throws IOException //for testing
    {
        fileWriter.print(",'',");
        for(int i = 0; i < b.length(); i++)
            fileWriter.print(b.charAt(i)+",");
        fileWriter.println();

        fileWriter.print("'',");
        for(int i = 0; i < d[0].length; i++)
            fileWriter.print(d[0][i]+",");
        fileWriter.println();

        for(int r = 1; r < d.length; r++)
        {
            fileWriter.print(a.charAt(r-1)+",");
            for(int c = 0; c < d[0].length; c++)
                fileWriter.print(d[r][c]+",");
            fileWriter.println();
        }
        fileWriter.println();
    }
}
