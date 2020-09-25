# Problem Statement

Swiggy is a food delivery service with 100s of restaurant partners in over 10 metropolitan cities. We help restaurants accept orders through their partner portal online or through the partner mobile app, and prepare them. The delivery partners are responsible for pickup and delivery through their logistics application, while the consumers use the app from Google's PlayStore or AppStore to browse restaurants and place orders

# Proposed Solution

As part of the solution, Swiggy will build three apps:

1. Swiggy - The app available on PlayStore / AppStore for consumers to browse restaurants, their menus and place orders
1. Swiggy Restaurant Partner - The app that is installed on Restaurant Manager's mobile phone, which is used for receiving and accepting new orders
1. Swiggy Delivery Agent - The app used by delivery agency to pickup delivery from the restaurant, and deliver to the given address

For the first milestone, Swiggy is looking to build the backend APIs for the three apps as three different microservices, and needs your help to implement and containerize the application.

# Specification

- Event Storming: https://miro.com/app/board/o9J_kvHMlDU=/
- API Spec: Paste the content of [openapi.yaml](./openapi.yaml) in [editor.swagger.io](http://editor.swagger.io) for testing APIs before submission

# Milestones

Milestone 1: SBA: Pre-req Java/dotnet Sr-FSE TaskRobo (Approx. 6 hours)
  - Step 1: Create three projects, one each for microservice "Swiggy API", "Swiggy Restaurant Partner API", "Swiggy Delivery Agent API"
  - Step 2: Write Dockerfiles for each microservice; Kubernetes Manifest files, to deploy each microservice in a different namespace. Also configure a separate database deployment for each microservice. You may choose to use NoSQL database of your choice (preferably MongoDB; also accepted Cassandra, DynamoDB, CosmosDB, Cloud Firestore)
  - Step 3: Implement the APIs, implement JWT Authorization, and ensure that the APIs are working with OpenAPI Spec shared above
  - Step 4: Implement Messaging (RabbitMQ or Kafka), to synchronize Order data between the three microservices (All arrows in Event-storming spec)
