##echo $1
##read name
echo "your stack name is :" $1
aws cloudformation create-stack --stack-name $1 --template-body file://csye6225-cf-networking.json --parameters "ParameterKey=StackName,ParameterValue=o$1"

