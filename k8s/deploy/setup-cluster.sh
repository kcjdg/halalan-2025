#!/bin/bash
set -x

helm repo add mysql-operator https://mysql.github.io/mysql-operator/


#Read configuration value from cluster-config.yaml file
read -rd '' DOMAIN MYSQL_REPLICAS MYSQL_USERNAME MYSQL_PASSWORD \
KAFKA_REPLICAS ZOOKEEPER_REPLICAS ELASTICSEARCH_REPLICAES \
GRAFANA_USERNAME GRAFANA_PASSWORD \
< <(yq -r '.domain, .mysql.replicas, .mysql.username,
 .mysql.password, .kafka.replicas, .zookeeper.replicas,
 .elasticsearch.replicas, .grafana.username, .grafana.password' ./cluster-config.yaml)


 # Install the postgres-operator
 helm upgrade --install mysql-operator mysql-operator/mysql-operator \
 --namespace mysql-operator --create-namespace


 #Install postgresql
helm upgrade install my-mysql-innodbcluster mysql-operator/mysql-innodbcluster -n $NAMESPACE \
  --version 2.2.0 \
   --set credentials.root.username="$MYSQL_USERNAME" \
   --set credentials.root.password="$MYSQL_PASSWORD" \
   --set credentials.root.password="$MYSQL_PASSWORD" \
   --set tls.useSelfSigned=true
