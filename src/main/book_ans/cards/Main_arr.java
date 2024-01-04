class Main {
    public static void main(String[] args) {
        int numPlayers = 4;
        Player p[] = new Player[numPlayers];
        int i;
        for (i=0; i<numPlayers; i++)
            p[i] = new Player("Player" + i);
        int round = 0;
        boolean won = false;
        while (won == false) {
            i = round % 4;
            p[i].throwCard();
            p[i].addCard();
            System.out.println(p[i]);
            won = p[i].playerWon();
            round++;
        }
    }
}
