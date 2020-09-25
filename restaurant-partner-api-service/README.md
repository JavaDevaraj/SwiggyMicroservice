Swiggy Restaurant Partner - The app that is installed on Restaurant Manager's mobile phone, which is used for receiving and accepting new orders

#Its mandatory to include Authorization token in each request:
#you can use this token to access or ping any api it will return access-token & access-type combine "access-type access-token".
Bearer eyJhbGciOiJIUzUxMiJ9.eyJuYW1lIjoidGVzdCJ9.1Pm03CAhPwBCXQ_v0WJYcNGwRbCihXN1NA5eGE2HVwSkDE_s93WjivBv7XMwjk8Pu9Z59yd5xyawN7oGvJXUIQ


Build application first then execute step 1 or Step 2
#Application run on port 7070 & 
Application run :7070
Kafka broker run : 9092
Zookeeper run: 2181
mongo db run :27017

Step 1: #Build application using following command.

sudo docker build -t restaurantAPI:1.0 .

#Then run restaurant-docker-compose.yml in swiggy folder.

sudo docker-compose -f restaurant-docker-compose.yml up

#To run entry application run 

sudo docker-compose -f docker-compose-kafka.yml up
