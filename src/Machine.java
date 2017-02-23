

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
    private ArrayList<Video> CenterVideos = new ArrayList<Video>();

    public void RemoveOtherVideos(){
        Video maxV = getMaxVideo();

        boolean isRequested = false;
        ArrayList<Request> RequestedList = new ArrayList<Request>();
        for(int i=0;i<requests.size();i++){
            if(requests.get(i).getVideo().equals(maxV)) {
                isRequested = true;
                RequestedList.add(requests.get(i));
            }
        }
        if(!isRequested){
            for(int i=0;i<endpoints.size();i++){
                if(endpoints.get(i).getList_servers().size()!=0){
                    ServerLat maxLat = getMaxLat(endpoints.get(i));
                    servers.get(maxLat.getServer().getId()).getVideosList().add(maxV);
                    maxV.setChecked(true);
                }
            }
        }


    }

    private ServerLat getMaxLat(Endpoint endpoint){
        ServerLat maxLat=null;
        for(int i=0;i<endpoint.getList_servers().size();i++){
            if(maxLat==null){
                maxLat = endpoint.getList_servers().get(i);
            }else if(endpoint.getList_servers().get(i).getLatency()>maxLat.getLatency())
                maxLat=endpoint.getList_servers().get(i);
        }
        return maxLat;
    }

    private Video getMaxVideo(){
        Video maxV = null;
        for(int i=0;i<videos.size();i++){
            if(!videos.get(i).isChecked()){
                if(maxV==null){
                    maxV = videos.get(i);
                }else if(videos.get(i).getSize()>maxV.getSize())
                    maxV=videos.get(i);
            }
        }
        return maxV;
    }

    public boolean RemoveBigVideos(){
        for(int i=0;i<videos.size();i++){
            if(!videos.get(i).isChecked()){
                if(videos.get(i).getSize()>x){
                    CenterVideos.add(videos.get(i));
                    videos.get(i).setChecked(true);
                    System.out.println("ID : "+videos.get(i).getId()+" Size : "+videos.get(i).getSize()+" stays on data center!");
                }
            }

        }
        return true;
    }

    public Machine(){
        if(ReadInputFile()){
            System.out.println("File read!");
            if(RemoveBigVideos()) {
                System.out.println("RemoveBigVideos ok");
                for(int i=0;i<videos.size();i++)
                    RemoveOtherVideos();
                DrawFile();
            }else
                System.out.println("OptimizeSystem error");
        }else{
            System.out.println("Error input file!");
        }
    }

    public boolean ReadInputFile(){
        try {
            infile = new Scanner(new File("Files/Output.txt"));
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




    public void DrawFile(){
        try {

            File output = new File("Files/Output2.txt");

            if (output.exists())
                output.delete();
            output.createNewFile();

            FileWriter fw = new FileWriter(output.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            int count = 0;
            ArrayList<Server> countList = new ArrayList<Server>();
            for(int i=0;i<servers.size();i++){
                if(servers.get(i).getVideosList().size()!=0) {
                    count++;
                    countList.add(servers.get(i));
                }
            }
            System.out.println(count);
            if(count!=0){
                bw.write(count+"\n");
                for(int i=0;i<countList.size();i++){
                    if(countList.get(i).getVideosList().size()!=0){
                        bw.write(countList.get(i).getId()+" "+countList.get(i).PrintVideos());
                    }

                }
            }

            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ;
    }


}
