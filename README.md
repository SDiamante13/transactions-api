# transactions-api

**AS** a developer on the Cardmember Behavior Analytics team<br/>
**I WANT** to retrieve card member transactions<br/>
**SO THAT** I can analyze and model card member behavior to better present the card member with relevant promotional
material

_Scenario 1: Retrieve transactions for account with no transactions_

**GIVEN** a card member account with no transactions<br/>
**WHEN** I request a list of transactions for that account<br/>
**THEN** I will receive a success response<br/>
**AND** I will see an empty list of transactions

_Scenario 2: Account does not exist in database_

**GIVEN** a card member account id that does not exist<br/>
**WHEN** I request a list of transactions for that account<br/>
**THEN** I will receive a not found response<br/>
**AND** I will see a detailed not found error message

_Scenario 3: Retrieve transactions for an account with one or more transactions_

**GIVEN** a card member account with transactions<br/>
**WHEN** I request a list of transactions for that account<br/>
**THEN** I will receive a success response<br/>
**AND** I will a list of all their transactions

_Scenario 4: Retrieve transactions for an account after a given a date_

**GIVEN** a card member account with transactions<br/>
**WHEN** I request a list of transactions after the given date<br/>
**THEN** I will receive a success response<br/>
**AND** I will see a list of transactions that occurred on and after the given date

## Notes:

GET `/accounts/{accountId}/transactions?fromDate=2021-01-31`

### Sample Response:

```json
[
  {
    "transactionId": 1,
    "date": "2022-02-03",
    "amount": 50.00,
    "merchantName": "Amazon",
    "summary": "XP Explained (Book)"
  }
]
```