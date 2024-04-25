class BankAccount
{
  private int minBalance;
  private int balance;
  private String owner;

  public BankAccount(BankAccount acct) {
    this.minBalance = acct.minBalance;
    this.balance =  acct.balance;    
    this.owner = acct.owner;
  }

  public BankAccount(int balance) {
    this.balance = balance;
    this.minBalance = 0;
    this.owner = "noname";
  } 
  /*
  minBalance must be lower than balance
  */
  public BankAccount(int balance, int minBalance, String owner) {
    this.balance = balance;
    this.minBalance = minBalance;
    this.owner = owner;
  } 

  public int getBalance() { return balance; }

  public boolean isLarger(BankAccount other) {
    if (balance > other.balance)
      return true;
    else
      return false;
  }

  public String getOwner() { return owner; }
  public void setOwner(String owner) { this.owner = owner; }
  
  public boolean withdraw(int amount) {
    if (balance - amount < minBalance)
      return false;
    balance = balance - amount;
    return true;
  }

  public void deposit(int amount) {        
    balance = balance + amount;
  }

  public boolean transfer(BankAccount target, int amount) {
    if (withdraw(amount) == false)
      return false;
    target.deposit(amount);
    return true;
  }

  public String toString() {
    String s = "Balance of " + owner;
    s = s + ": " + balance;
    return s;
  }  

}
