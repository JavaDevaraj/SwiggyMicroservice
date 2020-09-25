# To Run entry application:

./swiggy-deployment.sh

# Its mandatory to include Authorization token in each request: 
# you can use this token to access or ping any api it will return access-token & access-type combine "access-type access-token". 
Bearer eyJhbGciOiJIUzUxMiJ9.eyJuYW1lIjoidGVzdCJ9.1Pm03CAhPwBCXQ_v0WJYcNGwRbCihXN1NA5eGE2HVwSkDE_s93WjivBv7XMwjk8Pu9Z59yd5xyawN7oGvJXUIQ

# DevelopED Kubernates Service & Deployment file didn't get changes to test.
# You can test if environment is ready
kubernates/deployment $ kubectl create -f .

kubectl get all 

kubectl apply -f filename


