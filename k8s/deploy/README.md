## Local installation steps
- Require a minikube node minimum 16G memory and 40G disk space and run on Ubuntu operator
```shell
minikube start  --disk-size='40000mb' --memory='4g' --driver=docker
```
- Enable ingress addon
```shell
minikube addons enable ingress
```
- Install helm
  https://helm.sh/
- Install yq (the tool read, update yaml file)
  https://github.com/mikefarah/yq
- Goto `k8s-deployment` folder
- Execute [setup-keycloak.sh](setup-cluster.sh) to set up keycloak as the Identity and Access Management server.
```shell
./setup-keycloak.sh
```