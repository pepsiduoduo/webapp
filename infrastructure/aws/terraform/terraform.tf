provider "aws"{
        
	region = var.region

}

variable "name"{
	type = string
}

variable "region"{
	type = string
        default = "us-east-1"
}

variable "cidr_block"{
	type= string
	default="10.0.0.0/16"
}

variable "subnet1_cidr_block"{
        type= string
        default="10.0.1.0/24"
}

variable "subnet2_cidr_block"{
        type= string
        default="10.0.16.0/24"
}

variable "subnet3_cidr_block"{
        type= string
        default="10.0.32.0/24"
}

resource "aws_vpc""vpc_resource"{
	cidr_block		= "10.0.0.0/16"
	enable_dns_hostnames	= true
	enable_dns_support	= true
	enable_classiclink_dns_support	=true
	assign_generated_ipv6_cidr_block=false
	tags={
		Name=var.name
	}
}

resource "aws_subnet""subnet1_resource"{
        cidr_block              = "10.0.1.0/24"
        vpc_id			= aws_vpc.vpc_resource.id
        availability_zone	= "us-east-1a"
        map_public_ip_on_launch	= true
        tags={
                Name=var.name
        }
}

resource "aws_subnet""subnet2_resource"{
        cidr_block              = "10.0.16.0/24"
        vpc_id                  = aws_vpc.vpc_resource.id
        availability_zone       = "us-east-1b"
        map_public_ip_on_launch = true
        tags={
                Name=var.name
        }
}

resource "aws_subnet""subnet3_resource"{
        cidr_block              = "10.0.32.0/24"
        vpc_id                  = aws_vpc.vpc_resource.id
        availability_zone       = "us-east-1c"
        map_public_ip_on_launch = true
        tags={
                Name=var.name
        }
}

resource "aws_internet_gateway" "gateway"{
        vpc_id  = aws_vpc.vpc_resource.id
}

resource "aws_route_table" "route"{
        vpc_id  = aws_vpc.vpc_resource.id
        route   {
                cidr_block = "0.0.0.0/0"
                gateway_id = aws_internet_gateway.gateway.id
        }
}

resource "aws_route_table_association" "association1"{
        subnet_id = aws_subnet.subnet1_resource.id

        route_table_id = aws_route_table.route.id
}

resource "aws_route_table_association" "association2"{
        subnet_id = aws_subnet.subnet2_resource.id
        
        route_table_id = aws_route_table.route.id
}

resource "aws_route_table_association" "association3"{
        subnet_id = aws_subnet.subnet3_resource.id
        
        route_table_id = aws_route_table.route.id
}

resource "aws_network_acl" "acl"{
        vpc_id = aws_vpc.vpc_resource.id

        egress{
                protocol = "-1"
                rule_no =100
                action = "allow"
                cidr_block = "0.0.0.0/0"
                from_port = 0
                to_port = 0
        }
        
        ingress{
                protocol = "-1"
                rule_no = 200
                action = "allow"
                cidr_block = "0.0.0.0/0"
                from_port = 0
                to_port = 0
        }
}

resource "aws_security_group" "security"{
        name = "aws_security_group"
        description = "aws_security_group"
        vpc_id = aws_vpc.vpc_resource.id
                
        ingress{
                protocol = "tcp"
                cidr_blocks = ["0.0.0.0/0"]
                from_port = 22
                to_port = 22
        }

        egress{
                protocol = "-1"
                cidr_blocks = ["0.0.0.0/0"]
                from_port = 0
                to_port = 0
        }
}
