

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Machine {
    private Scanner infile;
    private int v;
    private int e;
    private int r;
    private int c;
    private int x;
    private ArrayList<Video> videos = new ArrayList<Video>();
    private ArrayList<Endpoint> endpoints = new ArrayList<Endpoint>();
    private ArrayList<Request> requests = new ArrayList<Request>();
    private ArrayList<Server> servers = new ArrayList<Server>();





    public boolean OptimizeSystem(){
        return false;
    }

    public Machine(){
        if(ReadInputFile()){
            System.out.println("File read!");
            if(OptimizeSystem())
                System.out.println("OptimizeSystem ok");
            else
                System.out.println("OptimizeSystem error");
        }else{
            System.out.println("Error input file!");
        }
    }

    public boolean ReadInputFile(){
        try {
            infile = new Scanner(new File("Files/me_at_the_zoo.in"));
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
            v=infile.nextInt();
        else {
            System.out.println("Reading error : v");
            return false;
        }

        if(infile.hasNextInt())
            e=infile.nextInt();
        else {
            System.out.println("Reading error : e");
            return false;
        }

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
            x=infile.nextInt();
        else {
            System.out.println("Reading error : x");
            return false;
        }

        for(int i=0;i<c;i++){
            servers.add(new Server(i,x));
        }
        //infile.nextLine();
        System.out.println("v:"+v);
        System.out.println("e:"+e);
        System.out.println("r:"+r);
        System.out.println("c:"+c);
        System.out.println("x:"+x);

        return true;
    }


    public boolean ReadValues(){
        for(int i=0;i<v;i++){
            videos.add(new Video(i,infile.nextInt()));
        }

        for(int i=0;i<e;i++){
            Endpoint endpoint = new Endpoint();
            endpoint.setId(i);
            endpoint.setData_center_lat(infile.nextInt());
            ArrayList<ServerLat> serverLats = new ArrayList<ServerLat>();
            int size = infile.nextInt();
            for(int j=0;j<size;j++){
                serverLats.add(new ServerLat(servers.get(infile.nextInt()),infile.nextInt()));
            }
            endpoint.setList_servers(serverLats);
            endpoints.add(endpoint);
        }

        for(int i=0;i<r;i++){
            Request request = new Request();
            request.setId(i);
            request.setVideo(videos.get(infile.nextInt()));
            request.setEndpoint(endpoints.get(infile.nextInt()));
            request.setRequests(infile.nextInt());
            requests.add(request);
        }








        return true;
    }

    /*public void DrawFile(){
        try {

            String content = "This is the content to write into file";

            File output = new File("Files/Output.txt");

            if (output.exists())
                output.delete();
            output.createNewFile();


            FileWriter fw = new FileWriter(output.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            *//*bw.write("MinMax : "+minmax+'\n');
            String ct="C : ";
            for(int c1=0;c1<50;c1++)
                if(c[c1]!=0)
                    ct+=c[c1]+"|";
            bw.write(ct+'\n');
*//*




            //bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ;
    }*/


}
