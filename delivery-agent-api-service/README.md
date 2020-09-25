Swiggy Delivery Agent - The app used by delivery agency to pickup delivery from the restaurant, and deliver to the given address

#Its mandatory to include Authorization token in each request:
#you can use this token to access or ping any api it will return access-token & access-type combine "access-type access-token".
Bearer eyJhbGciOiJIUzUxMiJ9.eyJuYW1lIjoidGVzdCJ9.1Pm03CAhPwBCXQ_v0WJYcNGwRbCihXN1NA5eGE2HVwSkDE_s93WjivBv7XMwjk8Pu9Z59yd5xyawN7oGvJXUIQ

Build application first then execute step 1 or Step 2
#Application run on port 6060 & 
Application run :6060
Kafka broker run : 9092
Zookeeper run: 2181
mongo db run :27017

Step 1: #Build application using following command.

docker build -t deliveryAPI:1.0 .

#Then run docker-compose-delivery-agent.yml in swiggy folder.

sudo docker-compose -f docker-compose-delivery-agent.yml up

#To run entry application run 

sudo docker-compose -f docker-compose-kafka.yml up
