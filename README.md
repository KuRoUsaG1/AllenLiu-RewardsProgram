# Antra-AllenLiu-RewardsProgram
This is a rewards service application that calculates rewards points for customers based on their transactions, which includes two Restful endpoints and test cases
built with Junit4.

#Prerequisites
JDK 11 or later
Maven 3.6.3 or later
MySQL 5.7 or higher

#Built With
Spring Boot - The web framework used
Maven - Dependency Management
MySQL - Database

#Installing
Clone the repository:
git clone https://github.com/KuRoUsaG1/Antra-AllenLiu-RewardsProgram.git

#Endpoints
The service has two endpoints:

GET /calculate-rewards?startDate={startDate}&endDate={endDate}

The endpoint accepts two query parameters:
startDate: the start date for the range of transactions (inclusive)
endDate: the end date for the range of transactions (inclusive)

The endpoint returns a list of CustomerRewards objects, each containing the following fields:
customerId: the ID of the customer
month: the month and year of the transaction, in the format "MMM-yyyy"
rewards: the total rewards points earned by the customer in that month

Example: 
GET /calculate-rewards?startDate=2022-01-01&endDate=2022-03-31

{
    {
        "customerId": 1,
        "month": "Jan-2022",
        "rewards": 150
    },
    {
        "customerId": 2,
        "month": "Jan-2022",
        "rewards": 120
    },
    {
        "customerId": 1,
        "month": "Feb-2022",
        "rewards": 200
    },
}

GET /total-rewards-for-customer?customerId={customerId}
The endpoint accepts one query parameters:
customerId: the id of the customer we are looking for

The endpoint returns one CustomerRewards object contains the following fields:
customerId: the ID of the customer
rewards: the total rewards points earned by the customer

Example:
GET /total-rewards-for-customer?customerId=1

{
    {
        "customerId": 1,
        "month": "N/A",
        "rewards": 350
    }
}

#Exception Handling
In this project, I have implemented exception handling to handle errors and unexpected scenarios that may occur during the execution of the program, 
which I defined a global exception handler class, to handle different types of exceptions that may be thrown by the program.

For example, if the customer id is not found in the database, I throw a ResourceNotFoundException, which is caught by the global exception handler 
and returned as a HttpStatus.NOT_FOUND response to the client. Similarly, I have handled other exceptions such as InvalidInputException, DataAccessException, 
and InternalServerError.

#Logging
I have used Slf4j for logging the events and errors that occur during the execution of the program. This helps me in debugging and troubleshooting any issues 
that may arise.

#Authors
Allen Liu - Initial work
