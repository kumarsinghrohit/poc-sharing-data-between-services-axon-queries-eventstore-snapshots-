# poc-sharing-data-between-services-axon

## POC to explore below options of data sharing between multiple services:
1. using axon query handler
2. Using axon event store.

## There are two services in this project:
1. Service1: It behaves as the upstream service and uses spring's CommandLineRunner to create multiple apps/libraries in the elastic 
             search and corresponding events in the axon event store.
2. Service2: It behaves as the downstream service and reads data from service1 by sending queries and also accessing axon event store.

Apart from above there is commonapi which is included as dependency in above services to share all events, queries and value objects.

## To see the outcome of the poc perform below steps
  - Pull repo and checkout.
  - Build poc-sharing-data-between-services-axon project and create JAR using `mvn package`.
  - Run the docker compose file to start axon and elastic-search services.
  - Start Service1 and Service2.

Currently Service2 fetches two types of data:
  - Immutable( Like appType, it cannot be changed once the app is created)
  - Mutable( Like shortDescription, it can be changed by events after its creation)
  
 To get apptype of an app, hit below url in browser, in console it shows the total time taken to fetch the apptype using query-handler
 as well as using event-store respectively:
  - http://localhost:8081/appType/{appId}.
 
 To get short-description of an app hit below url in browser, in console it shows the total time taken to fetch the short-description
 using query-handler as well as using event-store respectively:
  - http://localhost:8081/shortDesc/{appId}/{versionId}
  
## After multiple runs below is the outcome:
##### With Immutable data.
| Run | AppId | Time Taken with Query-Handler | Time Taken with Event-Store |
|-----|-------|-------------------------------|-----------------------------|
|1st|d6658316-4d4d-465b-af65-bfe68c0999d8|14 ms|15 ms
|2nd|d6658316-4d4d-465b-af65-bfe68c0999d8|16 ms|13 ms
|3rd|d6658316-4d4d-465b-af65-bfe68c0999d8|16 ms|12 ms
|4th|d6658316-4d4d-465b-af65-bfe68c0999d8|12 ms|9 ms
|5th|d6658316-4d4d-465b-af65-bfe68c0999d8|16 ms|10 ms
|6th|d6658316-4d4d-465b-af65-bfe68c0999d8|10 ms|7 ms
|7th|d6658316-4d4d-465b-af65-bfe68c0999d8|22 ms|19 ms

##### With Mutable data( without snapshot).
| Run | AppId | Time Taken with Query-Handler | Time Taken with Event-Store |
|-----|-------|-------------------------------|-----------------------------|
|1st|d6658316-4d4d-465b-af65-bfe68c0999d8|12 ms|27995 ms
|2nd|d6658316-4d4d-465b-af65-bfe68c0999d8|6 ms|27995 ms
|3rd|d6658316-4d4d-465b-af65-bfe68c0999d8|7 ms|27995 ms
|4th|d6658316-4d4d-465b-af65-bfe68c0999d8|8 ms|27995 ms
|5th|d6658316-4d4d-465b-af65-bfe68c0999d8|8 ms|27995 ms
|6th|d6658316-4d4d-465b-af65-bfe68c0999d8|8 ms|27995 ms
|7th|d6658316-4d4d-465b-af65-bfe68c0999d8|18 ms|27995 ms

##### With Mutable data( with snapshot, current threshold is 100).
| Run | AppId | Time Taken with Query-Handler | Time Taken with Event-Store |
|-----|-------|-------------------------------|-----------------------------|
|1st|d6658316-4d4d-465b-af65-bfe68c0999d8|12 ms|9 ms
|2nd|d6658316-4d4d-465b-af65-bfe68c0999d8|11 ms|10 ms
|3rd|d6658316-4d4d-465b-af65-bfe68c0999d8|12 ms|7 ms
|4th|d6658316-4d4d-465b-af65-bfe68c0999d8|12 ms|9 ms
|5th|d6658316-4d4d-465b-af65-bfe68c0999d8|19 ms|11 ms
|6th|d6658316-4d4d-465b-af65-bfe68c0999d8|24 ms|15 ms
|7th|d6658316-4d4d-465b-af65-bfe68c0999d8|10 ms|10 ms

- Total no of Tokens in Event-Store: 28059.
- Total number of aggregates: 30.
- Total no of Tokens for the given aggregateIdentifier(d6658316-4d4d-465b-af65-bfe68c0999d8): 28001
- In case of Mutable data, Token no at which the desired event(AppVersionShortDescriptionUpdated) was found: 28053.
- In case of Mutable data, AggregateSequenceNumber for the given event(AppVersionShortDescriptionUpdated): 27995

## OutCome:
In case of queryHandler the time consumed is more or less remains constant. But with event store it depends on the total number of
AggregateSequences in the event store and the snapshot threshold(if implemented).