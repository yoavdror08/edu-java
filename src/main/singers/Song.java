
public class Song {
	private int code;
	private String name;
	private String genre;

	public Song(String name, int code, String genre) 
	{
		this.name = name;
		this.code = code;
		this.genre = genre;
	}	
	
	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getGenre() {
		return genre;
	}
	
	public boolean isTwin(Song other)
	{
        return this.name.equals(other.name) && this.genre.equals(other.genre);
	}
}
