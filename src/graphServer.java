import com.google.gson.*;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class graphServer {
    public static void main(String[] args) throws IOException {

        String startingLink = args[0];
        startingLink = startingLink.substring(1, startingLink.length()-1);
        //System.out.println(startingLink);
        String data = Jsoup.connect(startingLink).ignoreContentType(true).execute().body();
        Gson gson = new Gson();
            JsonObject[] td = gson.fromJson(data, JsonObject[].class);

//        System.out.println("HELLOO ==================================");
//        for(JsonObject plant: td){
//            System.out.println(plant);
//        }




//        Scanner userInput = new Scanner(System.in);
//
//        while(!userInput.hasNext());
//        String input = "";
//        if(userInput.hasNext()) input = userInput.nextLine();
//        FileWriter writer3 = new FileWriter("./scanned.txt");
//        writer3.write("input is '" + input + "'");
//        writer3.close();
//        userInput.close();


        Scanner br = new Scanner(System.in);
        boolean quit = false;
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            outerwhile:
            while (true) {
                //System.out.println("This is my loop!\n");
                String readInput = "";

                //readInput = (br.readLine());
                readInput = (br.next());
                JsonObject graphToBuild = new Gson().fromJson(readInput, JsonObject.class);
                if (graphToBuild.get("command").getAsString().equals("quit")) {
                    //System.out.println(graphToBuild.get("command").getAsString().equals("quit"));
                    System.out.println("{\"status\":\"quitting\"}");
                    //quit = true;
                    break outerwhile;
                } else if (graphToBuild.get("command").getAsString().equals("plot")) {
                    //System.out.println(graphToBuild.get("command").getAsString().equals("plot"));
                    //System.out.println("I'm going to print!\n");
                    String xAxis = graphToBuild.get("xAxis").getAsString();
                    String yAxis = graphToBuild.get("yAxis").getAsString();

                    FileWriter writer2 = new FileWriter("./data.txt");
                    for (JsonObject planet : td) {
                        if (planet.get(yAxis).isJsonNull() == false && planet.get(xAxis).isJsonNull() == false) {


                            JsonPrimitive x = planet.getAsJsonPrimitive(xAxis);
                            JsonPrimitive y = planet.getAsJsonPrimitive(yAxis);

                            writer2.write(String.format("%s       %s\n", x, y));
                            //System.out.println(String.format("%s       %s\n", x, y));
                            //System.out.println(planet);
                        }
                    }
                    writer2.close();

                    FileWriter writer = new FileWriter("./scatter.gnu");
                    writer.write("set term png size 640,480\n");
                    writer.write("set output \"./scatterPlot.png\"\n");
                    writer.write(String.format("set title \"%s as a fnc of %s\"\n", yAxis, xAxis));
                    writer.write("plot \"./data.txt\" with points pt 1\n");
                    writer.write("quit\n");
                    writer.close();


                    String[] command = {"C:\\gnuplot\\bin\\gnuplot.exe", "./scatter.gnu"};
                    //String[] command = {"gnuplot", "./scatter.gnu"};


                    //{"command":"plot","xAxis":"pl_orbper","yAxis":"pl_orbeccen"}
                    //{"command":"plot","xAxis":"pl_orbper","yAxis":"pl_bmassj"}
                    ProcessBuilder pb = new ProcessBuilder(command); //"/usr/bin/gnuplot -c scatter.gnu"
                    pb.inheritIO();
                    try {
                        Process p = pb.start();
                        p.waitFor();
                        System.out.println("{\"status\":\"success\",\"imageFilename\":\"scatterPlot.png\",\"height\":360,\"width\":480}");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                //System.out.println("End of loop!");
                //System.out.println("End of loop!");
            }
            br.close();
            br = new Scanner(System.in);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
