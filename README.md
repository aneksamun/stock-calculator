# Stock Calculator
Determines what stocks and cash customers hold on a given date given a list of transactions.

The transactions are supplied in a CSV file in the format specified below.

## Calculation Explanation

- For the Stock purchase(sale) transactions, calculator deducts(increases) the cash balance of the customer by the value of the transaction increment(decrement) the holdings for that stock
- For a deposit(withdrawal), calculator increases(decreases) the customers cash balance by the given amount
- For a dividend the customers cash balance is increased by the given amount
- All decimal values are calculated to 4 decimal places with RoundingMode.HALF_UP if necessary
- If some line is failed to parse it is simply is ignored
- Always report a customers cash balance even if it is zero
- Stock holdings of zero is not included in the output
- Customers can be overdrawn

# Input File Format

```
Account,Date,TxnType,Units,Price,Asset
```

Where:
- ```Account``` - A string of 8 alphanumeric characters
- ```Date``` - In YYYYMMDD format
- ```TxnType``` - A 3 character string, one of 
  - ```BOT``` Stock Purchase
  - ```SLD``` Stock sale
  - ```DIV``` Dividend payment
  - ```DEP``` Deposit
  - ```WDR``` Withdrawal
- ```Units``` - The number of units bought or sold to 4 decimal places
- ```Asset``` - A four character string
  - For ```BOT```/```SLD``` transactions, this is the stock purchased/sold
  - For ```DIV``` transactions this will be the stock for which the dividend was issued
  - For ```DEP```/```WDR``` transactions it will be ```CASH```
- ```Units``` - Number of units purchased/sold to 4 decimal places
- ```Price``` - Price per unit to 4 decimal places.  For ```CASH``` transactions this will always be ```1```.

## Example input file

```
NEAA0000,20170101,DEP,100,1,CASH
NEAA0000,20170102,BOT,20,2.123,VUKE
NEAA0000,20170102,BOT,30,1.500,VUSA
NEAA0000,20170201,DIV,0.2024,1,VUKE
NEAA0000,20170201,SLD,20,2.000,VUSA 
NEAA0000,20170201,BOT,10.512,3.3350,GILS
NEAB0001,20161201,DEP,10000,1,CASH
NEAB0001,20170301,WDR,5000,1,CASH
```

## Holdings given a date of 2017/02/01

```
Current Holdings on 20170201:
    NEAA0000:
        VUSA    10.0000
        VUKE    20.0000
        GILS    10.5120
        CASH    17.6849
    NEAB0001:
        CASH    10000.0000
```

## User manual

In order to perform calculations the file and date should be selected first.

![initial](doc/form1.jpg?raw=true "Initial")

On a click calculate button the statement of the customer current holdings is printed out.  

![statement](doc/form2.jpg?raw=true "Statement")
