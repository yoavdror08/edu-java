import java.util.Random;

class Main {
  public static void testBasic() {
    BankAccount acct1 = new BankAccount(0);
    BankAccount acct2 = new BankAccount(acct1);
    double million = 1.0e+6;
    double billion = Math.pow(10, 9);
    acct1.deposit((int)million);
    acct2.deposit((int)billion);
    acct1.withdraw((int)1.0e+5);    
    acct2.withdraw(1);    
    System.out.println(acct1);
    System.out.println(acct2);
  }

  public static void testTransfer() {
    BankAccount acct1 = new BankAccount(3000);
    BankAccount acct2 = new BankAccount(3000);
    boolean success = acct1.transfer(acct2, 500);
    if (success)
      System.out.println("transfer succeeded");
    else
      System.out.println("transfer failed");
  }

  public static boolean addAcct(BankAccount[] accts) {    
    Random rand = new Random();
    for (int i=0; i<accts.length; i++)
    {
      if (accts[i] == null) {
        accts[i] = new BankAccount(rand.nextInt(10000));
        System.out.println("added " + accts[i]);
        return true;
      }  
    }
    return false;
  }

  public static int findRichest(BankAccount[] accts) {
    int iMax = 0;
    int max = accts[0].getBalance();
    for (int i=1; i < accts.length; i++)
    {      
      if (accts[i]!=null && accts[i].getBalance() > max) {
        iMax = i;
        max = accts[i].getBalance();
      }
    }
    return iMax;
  }

  public static void testArray() {
    BankAccount[] accts = new BankAccount[10];
    for (int i = 0; i < accts.length; i++)
    {
      accts[i] = null;
    }
    for (int i = 0; i < 5; i++)
    {
      if (!addAcct(accts))
        System.out.println("addAcct failed!");
    }
    int iMax = findRichest(accts);
    System.out.println("Richest is "+iMax+" with balance="+accts[iMax].getBalance());
  }
  
  public static void testBank() {
    Bank bank = createRandomBank();
    BankAccount richest = bank.findRichest();
    System.out.println("Richest has balance: " + richest.getBalance());
  }

  public static Bank createRandomBank() {
    Random rand = new Random();
    Bank bank = new Bank(10, "BestBank");
    int amount;
    for (int i = 0; i < 5; i++)
    {
      amount = rand.nextInt(10000);
      if (!bank.addAccount("dummy", amount))
        System.out.println("addAccount failed!");
    }
    return bank;
  }

  public static void testCopyBank()
  {
    Bank bank1 = createRandomBank();
    Bank bank2 = new Bank(bank1);
    bank2.addAccount("John", 2500);
    System.out.println(bank1);
    System.out.println(bank2);    
  }

  public static void main(String[] args) {
    BankAccount a1 = new BankAccount(2500);
    BankAccount a2 = new BankAccount(3600);
    boolean b = a1.isLarger(a2);
    
    testBasic();
    testCopyBank();
  }
}
