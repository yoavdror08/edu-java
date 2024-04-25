class Bank 
{
  private String name;
  private BankAccount[] accounts;
  private int numAccounts;

  public Bank(int maxAccounts, String name) {
    this.name = name;
    accounts = new BankAccount[maxAccounts];
    for (int i=0; i<accounts.length; i++)
    {
      accounts[i] = null;
    }
    numAccounts = 0;
  }

  public Bank(Bank bank) {
    name = bank.name;
    numAccounts = bank.numAccounts;

    accounts = new BankAccount[bank.accounts.length];

    for (int i = 0; i < numAccounts; i++)
    {
        accounts[i] = new BankAccount(bank.accounts[i]);
    } 
  }

  // Are all accounts of this bank richer than all those of bank b2 ?
  public boolean areAllRicher(Bank b2) {
    for (int i=0; i< numAccounts; i++)
    {
      for (int j=0; j<b2.numAccounts; j++)
      {
        if (this.accounts[i] != null  &&  b2.accounts[j] != null)
          if (this.accounts[i].getOwner().equals(b2.accounts[j].getOwner()))
            if (this.accounts[i].getBalance() < b2.accounts[j].getBalance())
              return false;
      }
    }
    return true;
  }

  public boolean addAccount(String owner, int balance) {    
    for (int i = 0; i < accounts.length; i++)
    {
      if (accounts[i] == null) {
        accounts[i] = new BankAccount(balance, 0, owner);
        numAccounts++;
        return true;
      }  
    }
    return false;
  }

  public boolean addAccount(BankAccount acct) {    
    for (int i = 0; i < accounts.length; i++)
    {
      if (accounts[i] == null) {
        accounts[i] = acct;
        numAccounts++;
        return true;
      }  
    }
    return false;
  }

  public BankAccount findRichest() {
    BankAccount maxAcct = null;
    for (int i = 0; i < accounts.length; i++)
    {      
      if (accounts[i] != null)
      {
        if (maxAcct == null || accounts[i].getBalance() > maxAcct.getBalance())
        {
          maxAcct = accounts[i];
        }
      }
    }
    return maxAcct;
  }

  public boolean deleteAccount(String owner) {
    for (int i = 0; i < accounts.length; i++)
    {      
      if (account[i] != null) {
        if (accounts[i].getOwner().equals(owner)) {
          accounts[i] = null;      
          numAccounts--;
          return true;
        }
      }
    }
    return false;
  }

  public String toString() {
    String s = "";
    for (int i=0; i < accounts.length; i++) {
      if (accounts[i] != null) {
        s += accounts[i];
        s += "\n";
      }
    }
    return s;
  }
}
