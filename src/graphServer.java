import org.jsoup.nodes.Document;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

class PlanetData {

    String pl_name; //Planet name
	Long pl_orbper; //Orbital Period (in Earth days), i.e. the length of its year
	Long pl_orbeccen; //Orbital Eccentricity
	Long pl_bmassj; //Best mass estimate (in Jupiters)
	Long pl_radj; //Planetary radius (in Jupiters)
	Long st_dist; //Distance to star (in Parsecs)
	Long st_teff; //Effective stellar temperature (in Kelvin)
	Long st_mass; //Stellar mass (in Suns)
	Long st_rad; //Stellar radius (in Suns)
	
	public PlanetData(String pl_name, Long pl_orbper, Long pl_orbeccen, Long pl_bmassj,
			Long pl_radj, Long st_dist, Long st_teff, Long st_mass, Long st_rad) {
		pl_name; //Planet name
		pl_orbper; //Orbital Period (in Earth days), i.e. the length of its year
		pl_orbeccen; //Orbital Eccentricity
		pl_bmassj; //Best mass estimate (in Jupiters)
		pl_radj; //Planetary radius (in Jupiters)
		st_dist; //Distance to star (in Parsecs)
		st_teff; //Effective stellar temperature (in Kelvin)
		st_mass; //Stellar mass (in Suns)
		st_rad; //Stellar radius (in Suns)
	}
	
	
    @Override
    public String toString() {
        return "pl_name: " + pl_name + ", pl_orbper: " + pl_orbper + ", pl_orbeccen: " + pl_orbeccen + ", pl_bmassj :" + pl_bmassj +  ", pl_radj: " 
        		+ pl_radj + ", st_dist: " + st_dist + ", st_teff: " + st_teff + ", st_mass: " + st_mass + ", st_rad: " + st_rad + "}";
    }
}

public class graphServer {
	//argv[1] = url to jsoup call
	//grab all of the json values and stuff them in arrays somehow
	//
	public static void main(String[] args) throws IOException{
		
		List<PlanetData> PlanetList = new ArrayList<>();
		
		
		
		Document doc;
		String startingLink = args[0];
		String data = Jsoup.connect(startingLink).ignoreContentType(true).execute().body();
		System.out.print(data);
		Gson gson = new Gson();
        PlanetData td = gson.fromJson(data, PlanetData.class);
        System.out.println(td);
		
	}
	
	
	
	
}
