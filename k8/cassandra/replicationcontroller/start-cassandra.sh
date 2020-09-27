#!/bin/bash
echo "Namespace: $1"

if [ -z "$1" ]
  then
    echo "Using default namespace"
    kubectl create -f cassandra-peer-service.yml
    kubectl create -f cassandra-service.yml
    kubectl create -f cassandra-replication-controller.yml
  else
    kubectl create -f cassandra-peer-service.yml -n $1
    kubectl create -f cassandra-service.yml -n $1
    kubectl create -f cassandra-replication-controller.yml -n $1
fi


