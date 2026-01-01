# Register docker in ecr
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 897316073585.dkr.ecr.us-east-1.amazonaws.com

# Build parse-service image
aws ecr create-repository --repository-name parser-service
docker build -f parser-service/Dockerfile -t mykhailo/parser-service:0.0.1 .

docker run -p 8080:8080 mykhailo/parser-service:0.0.1


# Build processor-service image
aws ecr create-repository --repository-name processor-service
docker build -f processor-service/Dockerfile -t mykhailo/processor-service:0.0.1 .


# Push to aws
docker tag mykhailo/parser-service:0.0.1 897316073585.dkr.ecr.us-east-1.amazonaws.com/parser-service:0.0.1
docker push 897316073585.dkr.ecr.us-east-1.amazonaws.com/parser-service:0.0.1

docker tag mykhailo/processor-service:0.0.1 897316073585.dkr.ecr.us-east-1.amazonaws.com/processor-service:0.0.1
docker push 897316073585.dkr.ecr.us-east-1.amazonaws.com/processor-service:0.0.1


# Run cfn
aws cloudformation create-stack \
--stack-name spring-cloud-fargate \
--template-body file://template.yml \
--parameters \
ParameterKey=VpcId,ParameterValue=vpc-0abc226a4844979c0 \
ParameterKey=PublicSubnets,ParameterValue=\"subnet-0994bc765f2ad05f0,subnet-0e0074d0493ddaada\" \
ParameterKey=ParserServiceImage,ParameterValue=897316073585.dkr.ecr.us-east-1.amazonaws.com/parser-service:0.0.1 \
ParameterKey=ProcessorServiceImage,ParameterValue=897316073585.dkr.ecr.us-east-1.amazonaws.com/processor-service:0.0.1 --capabilities CAPABILITY_IAM

# Verify cfn stack
aws cloudformation describe-stacks --stack-name spring-cloud-fargate
