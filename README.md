# CoinDCX Trading Automation Application

## Table of Contents
1. Introduction
2. Features
3. Prerequisites
4. Design Decisions
5. Libraries Used

## Introduction
This Java application connects to the CoinDCX WebSocket API to fetch real-time trading data and automatically places orders based on a user-defined trigger price. The application uses the SLF4J logging facade for logging 

## Features
- Connects to CoinDCX WebSocket for real-time market data.
- Allows users to set a trigger price for automatic order placement.

## Prerequisites
- Java Development Kit (JDK) 8 or higher
- Maven (for dependency management)

## Design Decisions
- WebSocket for Real-Time Data
-Error Handling: Implemented robust error handling and logging for connection management to improve reliability.

## Librarires Used
-SLF4J: For logging functionality.
-Gson: For JSON parsing and serialization.
-Java-WebSocket: For handling WebSocket connections.
