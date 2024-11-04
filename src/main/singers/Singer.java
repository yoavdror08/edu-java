
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

	public int getDiff(String jenre) {
		int j = -1;
		boolean same = true;
		while (same && j < 4) {
			j++;
			same = songs[j].getGenre().equals(jenre);
		}
		if (same)
			j = (int) Math.random() * 5 + 1;
		return j;
	}

	public boolean delSong(int s) {
		boolean found = false;
		int i;
		for (i = 0; i < numSongs && !found; i++) {
			found = (songs[i].getCode() == s);
		}
		if (!found)
			return false;
		for (int j = i; j < numSongs - 1; j++) {
			songs[j] = songs[j + 1];
		}
		return true;
	}

	public boolean hasSong(int t) {
		for (int i = 0; i < songs.length; i++) {
			if (songs[i].getCode() == t)
				return true;
		}
		return false;
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
		return;
	}
		
}
