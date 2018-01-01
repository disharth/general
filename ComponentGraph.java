package test.gcd;

import java.util.Arrays;
import java.util.Scanner;

public class ComponentGraph {

    private static int[] people;
    private static int[] count;

    public ComponentGraph(int N){
        N = 2*N;
        people = new int[N];
        count = new int[N];
        for (int i = 0; i <N ; i++) { // Initialize each with itself.
            people[i] = i;
            count[i] = 0;
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        ComponentGraph mergeCommunities = new ComponentGraph(N);
        for (int i = 0; i <N ; i++) {
            int first = scanner.nextInt();
            int second = scanner.nextInt();
            mergeCommunities.merge(first,second);

        }
        int max = Integer.MIN_VALUE , min = Integer.MAX_VALUE;
        for (Integer c:count) {
            if(c != 0 && c != 1){
                if(c > max)
                    max = c;
                if(c< min)
                    min = c;
            }

        }
        System.out.println(min+" "+max);

    }

    private static void merge(int I , int J){
        I = I -1; J = J -1;
        if(count[I] == 0) count[I] =1;
        if(count[J] == 0) count[J] =1;

        int iRoot =  getRoot(I);
        int jRoot =  getRoot(J);

        if(iRoot != jRoot){
            if(count[iRoot] >= count[jRoot]) {
                people[iRoot] = jRoot;
                count[jRoot] = count[jRoot] + count[iRoot];
                count[iRoot] = 0;
            }else {
                people[jRoot] = iRoot;
                count[iRoot] = count[iRoot] + count[jRoot];
                count[jRoot] = 0;
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
