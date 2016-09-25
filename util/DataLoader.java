package main.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.data.Anime;
import main.data.Song;

public class DataLoader {

	private static final String userDir = System.getProperty("user.dir");
	private static final Path path = Paths.get(userDir).resolve(Paths.get("Resources"));
	
	private static ArrayList<Song> songData = new ArrayList<Song>();
	private static InputStream songDataInput;
	//private static URL songJson= DataLoader.class.getResource("/data/SongData.json");
	
	private static ArrayList<Anime> animeNameData = new ArrayList<Anime>();
	private static InputStream animeNameDataInput;
	//private static URL animeJson= DataLoader.class.getResource("/data/AnimeData.json");
	
	private ObjectMapper mapper;
	
	public void loadData() throws IOException {
		animeNameDataInput = new FileInputStream(new File(path.toString()+"/data/AnimeData.json"));
		
		mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		
		animeNameData = mapper.readValue(animeNameDataInput, new TypeReference<ArrayList<Anime>>(){});
		
		/*
		for(int i=0;i<animeNameData.size();i++){
			System.out.println(animeNameData.get(i).AnimeName);
		}*/
		
		
		songDataInput = new FileInputStream(new File(path.toString()+"/data/SongData.json"));
		
		mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		
		songData = mapper.readValue(songDataInput, new TypeReference<ArrayList<Song>>(){});
		
		/*
		for(int i=0;i<songData.size();i++){
			System.out.println(songData.get(i).Interpret+": "+songData.get(i).Title);
		}*/
		/*
		for(int i=0;i<songData.size();i++){
			System.out.println(songData.get(i).AnimeName);
		}*/
		
		
	}
	
	
	
	public ArrayList<Song> getSongData() {
		return songData;
	}
	public ArrayList<Anime> getAnimeNameData() {
		return animeNameData;
	}
}
