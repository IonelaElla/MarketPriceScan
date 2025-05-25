# Price Comparator - Market

Java Spring Boot application that allows users to compare prices across different stores to manage their shopping basket effectively.
This application supports importing CSV data regarding prices and discounts and exposes its features through more API endpoints.

## Features
* Optimize Shopping Basket
* Get Best Discounts
* Get Latest Discounts
* Get Products Prices History
* Get Product Substitutes & Recommendations
* Get Price alerts for specific Products

## Project Structure Overview
The project structure follows the Controller-Service-Repository architectural pattern, which provides high scalability and reusability, with each layer having a single responsibility.
````
price-comparator-market/
├── .idea/            
├── .mvn/
├── postman/    # Contains files related to the Postman collection and the environment for calling this project's API.
│   ├── collections/
│   └── environments/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── eu/accesa/pricecomparatormarket/
│   │   │       ├── controllers/            # Layer responsible for handling HTTP requests.
│   │   │       ├── dtos/                   # Contains DTO classes that expose only essential information in the HTTP response.
│   │   │       ├── importcsv/              # Responsible for importing CSV data to our application.
│   │   │       ├── mapper/                 # Provides mapping methods for converting between different object types.
│   │   │       ├── models/                 # Contains the JPA entities used in our application.
│   │   │       ├── repository/             # Layer responsible for managing and accessing the data from the database.
│   │   │       ├── services/               # Acts as a bridge between the controller and the repository. Responsible for the complex operations of our application.
│   │   │       └── PriceComparatorMarketApplication.java
│   │   └── resources/
│   │       ├── sql/
│   │       │   └── schema.sql              # Contains the SQL statements for creating the database tables necessary for our application. 
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties      # Contains application configuration values like server settings and database credentials.
│   └── test/
├── target/
├── .gitattributes
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml                                 # Maven configuration file
└── README.md
````

## Build & Run
1. Navigate to the project root.
2. ```mvn clean install```
3. ```mvn spring-boot:run```

## Assumptions and Simplifications
* The application imports CSV data at startup and saves them into a PostgreSQL database to simplify data handling and avoid complex Java code. All CSV files should be valid according to the examples provided in the requirements.
* Although a real-world price comparison application would include user authentication, I focused only on the main features for simplicity.
* Considering that a product may have multiple prices in a specific store over time, our application displays the most recent price, which is the current price.
* Regarding the Product Prices History feature, our application displays only the original prices and doesn't treat the discounted prices as separate entries.

## API Usage

To test the API, you could import the [environment](./postman/environments/) and [collection](./postman/collections/) into your Postman application from the [Postman directory](./postman) included in this project.

```POST /api/basket/optimize```
* Optimizes the shopping basket by splitting it into multiple possible shopping lists and recommending the cheapest one.
* **Request Body**
    * example: ```{
      "productNames": ["lapte zuzu", "piept pui"]
      }```
* **Response**: Returns a JSON with more compared shopping lists and a recommended shopping basket.

```GET /api/discounts/top```
* Retrieves the top N available discounts, with N defined by the limit query parameter.
* **Query Params**
    * limit (required): e.g. 10
*  **Response**: Returns a JSON with the top N product discounts.

```GET /api/discounts/new```
* Retrieves the newly added discounts within the last 24 hours.
*  **Response**: Returns a JSON with the discounted products after newly added discounts.

```GET /api/prices/history```
* Retrieves the price history for each product across all stores.
* **Query Params**
    * storeName (optional): e.g. lidl
    * productCategory (optional): e.g. lactate
    * productBrand (optional): e.g. Zuzu
*  **Response**: Returns a JSON with products, each having a list of prices over time.