# VendingMachine

**Supported Commands and Usage**
INSERT 100	- To insert multiple space separated Coins denom, it suppports denomination in PENCE Â£1 = 100
SELECT 0 	  - To select product slot 
COLLECT 	  - Collect Item and change
MAINTAIN	  - Maintenance
CANCEL 		  - Cancel purchase and refund inserted coins
QUIT		    - Quit

|   Command     | Description  |
| ------------- | ------------ |
| INSERT 100    | To insert Coins, denomination is in pence e.g £1 = 100, Command allows to add more than one coin at a time e.g INSERT 100 200 20 10  |
| SELECT 0      | Select Product slot. This command accepts valid Product Slot Number e.g SELECT 1 |
| COLLECT      	| This Command use to Collect Selected item and returned coin change after purchase |
| MAINTAIN      | To access Maintenance menu, where you can Set/Get Coin Quantity, Price  |
| CANCEL      	| Cancel item purchase |
| QUIT      	| Shutdown machine |

