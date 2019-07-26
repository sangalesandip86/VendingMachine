# VendingMachine

**Supported Commands and Usage**

|   Command     | Description  |
| ------------- | ------------ |
| INSERT 100    | To insert Coins, denomination is in pence e.g £1 = 100, Command allows to add more than one coin at a time e.g INSERT 100 200 20 10  |
| SELECT 0      | Select Product slot. This command accepts valid Product Slot Number e.g SELECT 1 |
| COLLECT      	| This Command use to Collect Selected item and returned coin change after purchase |
| MAINTAIN      | To access Maintenance menu, where you can Set/Get Coin Quantity, Price  |
| CANCEL      	| Cancel item purchase |
| QUIT      	| Shutdown machine |


**Approach Used to Refund Coin**

Assumption - Coins denomination in PENCE £1 = 100

1. Sorted Supported Coins list in descending order 
2. If coins quantity is available then Find number of possible for given amount
	e.g  
	- refundAmount = £5.2    (£5.2 = 520 PENCE)
	- 2 coins of £2 is possible combination    (also checking that many coins available in machine)
	- refundAmount = refundAmount - 200 * 2    (£2 = 200)
	- For remaining amount try with next highest coin, and repeat same process     

After trying to refund with all possible coins , if refundAmount > 0 then Show Message "Not Sufficient change for amount"

**Problem Statement**

Design and implement a class representing a basic vending machine
capable of keeping track of the number of items of each type currently in the machine, the
amount of change currently in the machine for each type of coin, and to return correct change
given a product selection and a set of coins submitted. The solution is expected to be in the
form of two interfaces (see below) and a class implementing these interfaces, along with any
helpers etc that you may wish to create.

Each vending machine instance has a fixed number of product slots, supports a fixed number of
coin types (both set at instantiation time), but can have price and quantity for each product slot
adjusted as well we the amount of change available - imagine an operator collecting money
from the machine, filling it up with new items and potentially changing which products are being
sold.

The vending machine also supports price display and actual purchases of products using coins
provided by a user. This operation is expected to work the way a vending machine would, i.e.
buying a product decreases the inventory for that product by one, and the amount of change in
the machine is updated.

