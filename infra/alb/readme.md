aws cloudformation create-stack \
--stack-name spring-cloud-alb \
--template-body file://spring-cloud-alb.yml \
--parameters \
ParameterKey=VpcId,ParameterValue=vpc-0abc226a4844979c0 \
ParameterKey=PublicSubnets,ParameterValue=\"subnet-0994bc765f2ad05f0,subnet-0e0074d0493ddaada\"
