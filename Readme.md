# Deribit Exchange Integration

There are five different APIs for interacting with Deribit exchange.

## 1. User Asset Balance API
This API returns the balances of each of its assets (BTC, ETH and USDT).

Getting the user's current balances and reserved funds for all available currencies (‘reserved’ means are not available for withdrawal).

## 2. User Deposit History

This API returns the deposit history of each of its assets (BTC, ETH and USDT).

Getting history of deposits.

## 3. User Withdrawal History
This API returns the withdrawal history of each of its assets (BTC, ETH and USDT).

Getting history of withdrawal

## 4. Transfer asset to sub account
This API transfer asset (BTC, ETH and USDT) to its sub account

Transfer between main account and sub account.

## 5. Transfer asset to external account
This API transfer asset (BTC, ETH and USDT) to external accounts

Transfer from exchange to external crypto address.

# Here is the Documentation with API URL, Parameters and cURL request.

https://documenter.getpostman.com/view/240605/TzmChYhT

# Instructions to Run the Service

Run the shell script for single click building and deployment of service. Use cURL command from [documentation](https://documenter.getpostman.com/view/240605/TzmChYhT) to run test the APIs.

```
./build.sh

# User Asset Balance API
curl --location --request GET 'http://localhost:8080/exchanges/v1/account/balances?client_id=TIYIapc7' \
--header 'client_secret: 4_eoDpo_BfSh7Bdg1-BsfOHQqka_hOXnOI_S8YquERc'

# User Deposit History
curl --location --request GET 'http://localhost:8080/exchanges/v1/account/history/deposits?count=10&client_id=TIYIapc7&offset=0' \
--header 'client_secret: 4_eoDpo_BfSh7Bdg1-BsfOHQqka_hOXnOI_S8YquERc'

# User Withdrawal History
curl --location --request GET 'http://localhost:8080/exchanges/v1/account/history/withdrawals?count=10&client_id=TIYIapc7&offset=0' \
--header 'client_secret: 4_eoDpo_BfSh7Bdg1-BsfOHQqka_hOXnOI_S8YquERc'

# Transfer asset to sub account
curl --location --request GET 'http://localhost:8080/exchanges/v1/account/transfer/sub_account?client_id=TIYIapc7&amount=2&currency=BTC&destination_sub_account=31364' \
--header 'client_secret: 4_eoDpo_BfSh7Bdg1-BsfOHQqka_hOXnOI_S8YquERc'

# Transfer asset to external account
curl --location --request GET 'http://localhost:8080/exchanges/v1/account/transfer/external_account?client_id=TIYIapc7&amount=2&currency=BTC&destination_external_account=2NBqqD5GRJ8wHy1PYyCXTe9ke5226FhavBz' \
--header 'client_secret: 4_eoDpo_BfSh7Bdg1-BsfOHQqka_hOXnOI_S8YquERc'

```

Replace with Deribit exchange user's [credentials](https://test.deribit.com/) like `client_id` and `client_secret` to use your Deribit exchange. 