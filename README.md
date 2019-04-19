# [Java] Portuguese Citizenship Card KYC

This library executes a Know Your Customer (KYC) process over the [WalliD](https://wallid.io) protocol. As input, this library receives a [json object](https://github.com/walliDprotocol/wallid-MyEtherID/blob/master/test/DataId_Test_Card.json) named DataID.

## Configuration

This library used two environment variables:
- KYC_CONFIG: This variable should point to a properties file which contains the path to a keystore which contains all certificates needed to validate user's identity.
- KYC_DEBUG: This variable is used to print logs that could be useful for debugging purposes, in order to do that, this variable must assume the "true" value, by default is "false".
- KYC_DEBUG_FILE_PATH: This variable is used to persist the logs in a certain file.

## Compile & Build

```unix
mvn clean package
```

## Usage

```Java
PtCCApi api = new PtCCApi();
api.verify(dataID);
```
In a case that the user's identity is not verified successfully, the library triggers an exception which contains the reason of the failure.
