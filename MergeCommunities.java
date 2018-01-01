package test.gcd;

import java.util.Arrays;
import java.util.Scanner;

public class MergeCommunities
{
    private static int[] people;
    private static int[] count;

    public MergeCommunities(int N){
        people = new int[N];
        count = new int[N];
        for (int i = 0; i <N ; i++) { // Initialize each with itself.
            people[i] = i;
            count[i] = 1;
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        scanner.nextLine();
        MergeCommunities mergeCommunities = new MergeCommunities(N);
        String[] queries = new String[M];
        for (int i = 0; i <M ; i++) {
            String line = scanner.nextLine();
            queries[i] = line;

        }
        Arrays.toString(queries);
        for (String line :queries) {

            String[] strs = line.split(" ");
            if (strs[0].equalsIgnoreCase("Q"))
                System.out.println(mergeCommunities.getCount(Integer.valueOf(strs[1])));
            else if (strs[0].equalsIgnoreCase("M")) {
                mergeCommunities.merge(Integer.valueOf(strs[1]), Integer.valueOf(strs[2]));
            }
        }

    }

    private static void merge(int I , int J){
        I = I -1; J = J -1;

        int iRoot =  getRoot(I);
        int jRoot =  getRoot(J);

        if(iRoot != jRoot){
            if(count[iRoot] >= count[jRoot]) {
                people[iRoot] = jRoot;
                count[jRoot] = count[jRoot] + count[iRoot];
            }else {
                people[jRoot] = iRoot;
                count[iRoot] = count[iRoot] + count[jRoot];
            }
        }

    }

    private static int getRoot(int person){
        while (people[person] != person){
            person = people[person];
        }
        return person;
    }

    private static int getCount(int I){
        I = I-1;
        int iRoot = getRoot(I);
        return count[iRoot];
    }

}
