### What I did
- Followed DDD patterns to pass requests to create DTOs, and then create an entity to put in database.
- Used H2 database to be able to post valid invoices into the database and return the object to client.
  - utils folder has a valid invoice to use. 
- Validated the request before I tried making an object. 
  - if a string was passed in where a int should be, a type mismatch would occur and you would not be able to make the DTO. 
    - these cases were not saved to the database
  - if some other validation failed like the size of the transactions list doesn't match that of the variable totalTransactionNumber, the DTO could be built and was saved to database, using a **validity** flag as true/false if the object is valid.
    - these requests are apart of /invoice/{id}/status
### How I tested
- unit tested parts I had problem with
  - dates
  - validation, could ensure that class returned appropriate error messages.
  - the service

- i used a python script to pull our the data matched by IDs given in the csvs.
  - from there, i had a set of invalid and valid test data
  - could use the json from these scripts as POST bodies. 
  - this was my 'integration test'

### What I'd do next time
- given time constraint I reluctantly spent more time on getting it working rather than doing typical TDD style of work. I'd typically have more test and write them contiugously as I go.
- post a screen recording

### What you might need to run
- I used java 17 and if you had the JDK, from like SDKman you shouldn't have a problem being able to right-click run the application, and like gradle create an executable.