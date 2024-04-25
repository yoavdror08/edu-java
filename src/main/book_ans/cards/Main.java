class Main {
    public static void main(String[] args) {
        Player p1 = new Player("a");
        Player p2 = new Player("b");
        Player p3 = new Player("c");
        Player p4 = new Player("d");
        int round = 0;
        boolean won = false;
        while (won == false) {
            if (round % 4 == 0) {
                p1.throwCard();
                p1.addCard();
                System.out.println(p1);
                won = p1.playerWon();
            } else if (round % 4 == 1) {
                p2.throwCard();
                p2.addCard();
                System.out.println(p2);
                won = p2.playerWon();
            } else if (round % 4 == 2) {
                p3.throwCard();
                p3.addCard();
                System.out.println(p3);
                won = p3.playerWon();
            } else if (round % 4 == 3) {
                p4.throwCard();
                p4.addCard();
                System.out.println(p4);
                won = p4.playerWon();
            }
            round++;
        }
    }
    public static void main2(String[] args) {
        int numPlayers = 4;
        Player p[] = new Player[numPlayers];
        int i;
        for (i = 0; i < numPlayers; i++)
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
