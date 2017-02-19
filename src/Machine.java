

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Machine {
    private Scanner infile;
    private int r;
    private int c;
    private int l;
    private int h;
    private int[][] pizza;

    public Machine(){
        ReadingFile();
    }

    public void ReadingFile(){
        ReadInputFile();
    }

    public boolean ReadInputFile(){
        try {
            infile = new Scanner(new File("Files/Input.txt"));
            System.out.println("File read!");
            if(ReadFirstLine()){
                return ReadValues();
            }else{
                System.out.println("Error reading first line");
                return false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean ReadFirstLine(){
        if(infile.hasNextInt())
            r=infile.nextInt();
        else {
            System.out.println("Reading error : r");
            return false;
        }

        if(infile.hasNextInt())
            c=infile.nextInt();
        else {
            System.out.println("Reading error : c");
            return false;
        }

        if(infile.hasNextInt())
            l=infile.nextInt();
        else {
            System.out.println("Reading error : l");
            return false;
        }

        if(infile.hasNextInt())
            h=infile.nextInt();
        else {
            System.out.println("Reading error : h");
            return false;
        }
        infile.nextLine();
        return true;
    }

    public boolean ReadValues(){
        pizza=new int[r][c];
        for(int i=0;i<r;i++){
            String row;
            if(infile.hasNext()){
                row = infile.nextLine();
                System.out.println(row);
                for(int j=0;j<c;j++){
                    char cell = row.charAt(j);
                    if(cell=='T')
                        pizza[i][j]=0;
                    else if(cell=='M')
                        pizza[i][j]=1;
                }
            }else{
                System.out.println("reading values error , row :"+i+"  r : "+r+" c : "+c);
                return false;
            }
        }
        System.out.println("R: "+pizza.length +" C: "+pizza[0].length+" L: "+l+" H: "+h);
        return true;
    }
}
