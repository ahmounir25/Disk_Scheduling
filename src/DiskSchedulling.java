

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class DiskSchedulling {
    private static int initialHead;
    private static int queue[];
    private static ArrayList<Integer> sequence;
    private static int max;
    DiskSchedulling(int choice){
        getInput(choice);
        System.out.println("\nFCFS ALGORITHM:");
        FCFS();
        System.out.println("\nSSTF ALGORITHM:");
        SSTF();
        System.out.println("\nSCAN ALGORITHM:");
        SCAN();
        System.out.println("\nCSCAN ALGORITHM:");
        CSCAN();
        System.out.println("\nLOOK ALGORITHM:");
        LOOK();
        System.out.println("\nCLOOK ALGORITHM:");
        CLOOK();
        System.out.println("\nOPTIMIZED ALGORITHM:");
        OPTIMIZED();
    }
    public static void getInput(int choice) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter initial head start: ");
        initialHead = sc.nextInt();
        sc.nextLine();
        if(choice == 0) {
            System.out.print("Enter Queue: ");
            String line = sc.nextLine();
            String stringArr[] = line.split(",", -1);
            queue = new int[stringArr.length];
            for(int i = 0; i < queue.length; i++) queue[i] = Integer.parseInt(stringArr[i]);
        }
        else {
            String filePath = "C:\\Users\\LEGION\\OneDrive\\Desktop\\New folder\\input.txt";
            queue = getInputFile(filePath);
        }
        sequence = new ArrayList<>();
        System.out.print("Enter max: ");
        max = sc.nextInt();
        sc.close();
    }
    private static int[] getInputFile(String filePath) {
        int arr[] = {};
        ArrayList<Integer> arrayList = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            String stringArr[];
            while((line = br.readLine()) != null) {
                stringArr = line.split(",", -1);
                for(int i = 0; i < stringArr.length; i++) arrayList.add(Integer.parseInt(stringArr[i]));
            }
            arr = new int[arrayList.size()];
            for(int i = 0; i < arr.length; i++) arr[i] = arrayList.get(i);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }
    //Print sequence of head movement
    private void printSeq() {
        System.out.print("Sequence: ");
        for(int i = 0; i < sequence.size(); i++) {
            System.out.print(sequence.get(i));
            if(i != sequence.size() - 1) {
                System.out.print(" => ");
            }
        }
        System.out.println();
    }
    //Print total head movement
    private void totalHeadMoves() {
        int count = 0;
        for(int i = 1; i < sequence.size(); i++) {

            count += Math.abs(sequence.get(i) - sequence.get(i - 1));
        }
        System.out.println("Total Head Movement (Seek Time) is: " + count);
    }
    //FCFS Algorithm
    public ArrayList<Integer> FCFS() {
        sequence = new ArrayList<>();
        sequence.add(initialHead);
        for(int i = 0; i < queue.length; i++) sequence.add(queue[i]);
        printSeq();
        totalHeadMoves();
        return sequence;
    }
    //SSTF Algorithm
    public ArrayList<Integer> SSTF() {
        sequence = new ArrayList<>();
        sequence.add(initialHead);
        int queueCopy[] = queue.clone();
        int min, idx = 0;
        for(int i = 0; i < queue.length; i++) {
            min = Integer.MAX_VALUE;
            for(int j = 0; j < queue.length; j++) {
                if(Math.abs(sequence.get(i) - queueCopy[j]) < min) {
                    min = Math.abs(sequence.get(i) - queueCopy[j]);
                    idx = j;
                }
            }
            sequence.add(queueCopy[idx]);
            queueCopy[idx] = -Integer.MAX_VALUE;
        }
        printSeq();
        totalHeadMoves();
        return sequence;
    }
    //SCAN Algorithm
    public ArrayList<Integer> SCAN() {
        sequence = new ArrayList<>();
        sequence.add(initialHead);
        ArrayList<Integer> smallValues = new ArrayList<>();
        int queueCopy[] = queue.clone();
        Arrays.sort(queueCopy);
        int count = 0;
        for(int i = 0; i < queue.length; i++) {
            if(queueCopy[i] > sequence.get(count)) {
                sequence.add(queueCopy[i]);
                count++;
            }
            else {smallValues.add(queueCopy[i]);}
            if(i == queue.length - 1) sequence.add(max);
        }
        Collections.sort(smallValues, Collections.reverseOrder());
        for(int i = 0; i < smallValues.size(); i++)
        {sequence.add(smallValues.get(i));}
        printSeq();
        totalHeadMoves();
        return sequence;
    }
    //CSCAN Algorithm
    public ArrayList<Integer> CSCAN() {
        sequence = new ArrayList<>();
        sequence.add(initialHead);
        ArrayList<Integer> smallValues = new ArrayList<>();
        int queueCopy[] = queue.clone();
        Arrays.sort(queueCopy);
        int count = 0;
        for(int i = 0; i < queue.length; i++) {
            if(queueCopy[i] > sequence.get(count)) {
                sequence.add(queueCopy[i]);
                count++;
            }
            else smallValues.add(queueCopy[i]);
            if(i == queue.length - 1) {
                sequence.add(max);
                sequence.add(0);
            }
        }
        for(int i = 0; i < smallValues.size(); i++) sequence.add(smallValues.get(i));
        printSeq();
        totalHeadMoves();
        return sequence;
    }
    //LOOK Algorithm
    public ArrayList<Integer> LOOK() {
        sequence = new ArrayList<>();
        sequence.add(initialHead);
        ArrayList<Integer> smallValues = new ArrayList<>();
        int queueCopy[] = queue.clone();
        Arrays.sort(queueCopy);
        int count = 0;
        for(int i = 0; i < queue.length; i++) {
            if(queueCopy[i] > sequence.get(count)) {
                sequence.add(queueCopy[i]);
                count++;
            }
            else smallValues.add(queueCopy[i]);
        }
        Collections.sort(smallValues, Collections.reverseOrder());
        for(int i = 0; i < smallValues.size(); i++) sequence.add(smallValues.get(i));
        printSeq();
        totalHeadMoves();
        return sequence;
    }
    //CLOOK Algorithm
    public ArrayList<Integer> CLOOK() {
        sequence = new ArrayList<>();
        sequence.add(initialHead);
        ArrayList<Integer> smallValues = new ArrayList<>();
        int queueCopy[] = queue.clone();
        Arrays.sort(queueCopy);
        int count = 0;
        for(int i = 0; i < queue.length; i++) {
            if(queueCopy[i] > sequence.get(count)) {
                sequence.add(queueCopy[i]);
                count++;
            }
            else smallValues.add(queueCopy[i]);

        }
        for(int i = 0; i < smallValues.size(); i++) sequence.add(smallValues.get(i));
        printSeq();
        totalHeadMoves();
        return sequence;
    }
    //Optimized Algorithm
    public ArrayList<Integer> OPTIMIZED() {
        sequence = new ArrayList<>();
        sequence.add(0);
        int queueCopy[] = queue.clone();
        Arrays.sort(queueCopy);
        for(int i = 0; i < queue.length; i++)
        {sequence.add(queueCopy[i]);}
        printSeq();
        totalHeadMoves();
        return sequence;
    }
    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter '0' to get queue from cmd or any other digit to read from a file:");
        int choice = sc.nextInt();
        new DiskSchedulling(choice);
        sc.close();
    }

}
