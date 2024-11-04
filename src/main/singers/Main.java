
public class Main {

	public static void main(String[] args) {
		test();
	}

	public static void lineup(Singer[] singers) {
		boolean[] sung = new boolean[100];
		for (int i = 0; i < singers.length; i++) {
			Singer singer = singers[i];
			int j = -1;
			boolean used = true;
			while (used && j < 4) {
				j++;
				int song = singer.getSong(j).getCode();
				used = sung[song];
			}
			if (used)
				j = (int) Math.random() * 5 + 1;
			System.out.println(singer + ": " + singer.getSong(j));
		}
	}

	public static void lineup2(Singer[] singers) {
		String jenre = null;
		for (int i = 0; i < singers.length; i++) {
			Singer singer = singers[i];
			int j = singer.getDiff(jenre);			
			Song song = singer.getSong(j);
			System.out.println(singer + ": " + song.getName());
			jenre = song.getGenre();
		}
	}

	public static String winnerGenre(Singer[] singers, String[] genres)
	{
		int[] mone = new int[genres.length];
		int max = 0;
		int imax = 0;

		for(int i = 0; i < singers.length; i++)
		{
			for(int j = 0;j < genres.length; j++)
			{
				if(singers[i].likes(genres[j]))
				{
					mone[j]++;
				}
			}
		}
		
		for(int i = 0; i < mone.length; i++)
		{
			if(mone[i] > max)
			{
				max = mone[i];
				imax = i;
			}
		}
		return genres[imax];
	}
	
	public static void test() {
		Song s1 = new Song("a", 1, "d");
		Song s2 = new Song("b", 2, "d");
		Song s3 = new Song("c", 3, "e");
		Song s4 = new Song("a", 4, "d");
		Song s5 = new Song("d", 5, "e");
		Song s6 = new Song("d", 5, "r");
		Singer a1 = new Singer("1", 3); // likes d
		Singer a2 = new Singer("2", 3); // likes e
		Singer a3 = new Singer("3", 3); // likes r
		a1.addSong(s1);  
		a1.addSong(s3);
		a1.addSong(s4);
		a2.addSong(s2);
		a2.addSong(s3);
		a2.addSong(s5);
		a3.addSong(s6);
		Singer[] a = {a1, a2, a3, a1};  // likes: d e r d
		String[] b = {"a", "b", "c", "d", "e"};
	
		System.out.println(s1.isTwin(s4));
		System.out.println(s3.isTwin(s5));
		System.out.println(a1.likes("d"));
		System.out.println(a1.likes("a"));
		System.out.println(a2.likes("e"));
		System.out.println(a3.likes("r"));
		System.out.println(winnerGenre(a, b));
	}
}
