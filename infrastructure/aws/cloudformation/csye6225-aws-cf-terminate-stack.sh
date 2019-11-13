##echo "Enter your stack name: "
##read name

aws cloudformation delete-stack --stack-name $1
aws cloudformation wait stack-delete-complete --stack-name $1
echo "Stack $name deleted!"
