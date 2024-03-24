# Book Phone Device Assignment

Goal of this assignment is to make an app to book mobile devices using foneapi as core library and foneapi as basic api to provide required data

## Process

- initially started with attitude that all is well known and experience is enough
- started noticing challenges about unavailability of api and artifacts for implementation
- complicating things, thinking big in terms of project setup and configuration
- started with domain modeling, moved to device entity from library, got back to own model
- thinking about persistent datastore, selecting simple file store for dataset
- thinking about writing tests in terms of strict deadline and flexible timeframe for delivery
- thinking about getting deeper into reactive-manifesto and rxjava for talk about implementation
- thinking about persistence, tradeoffs comparing to assignment requirements
- thinking to google for complete solution :)
- deciding on in-memory store (no persistence, reducing out-of-scope complexity)
- wondering about data attributes, is technology = capabilities and unified response format
- taking data from gsm-arena and figuring out how little I know about technologies and bands
- thinking about emitting events on availability and passing that info to blocking subscribers
- completed service using toList(), now changing based on previous finding

## Technical obstacles

- api library not available on central repository
- kotlin artifacts for java assignment
- project build setup
- bootstrapping 2 api-s with current artifacts in library
- prepare json file with devices and fill in all the data

## Challenges

- handle api failover
- separate bookings from device data and merge observables
- handle concurrent requests

## Improvements

- better handling of observables
- use subscribers and push notifications from observables
- schedule and release subscriber on thread and update availability