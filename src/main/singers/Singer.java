
public class Singer {
	private String name;
	private Song[] songs;
	private int numSongs;

	public Singer(String name, int size) {
		this.name = name;
		this.numSongs = 0;
		this.songs = new Song[size];
	}

	public int getNumSongs() {
		return numSongs;
	}

	public Song getSong(int i) {
		return songs[i];
	}
	public boolean likes(String genre) {
		int c = 0;
		for (int i = 0; i < numSongs; i++) {
			if (songs[i].getGenre().equals(genre))
				c++;
		}
		return c > numSongs / 2;
	}
	

	public String getName() 
	{
		return name;
	}

	public void addSong(Song s)
	{
		if(numSongs == songs.length)
		{
			return;
		}
        	songs[numSongs] = s;
		numSongs++;
	}
		
}
