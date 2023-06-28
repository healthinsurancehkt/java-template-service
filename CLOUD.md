# How to get connected to GCP

## Connect your google account/email to the cluster

* Make sure you have the `gcloud`command installed
```
https://cloud.google.com/sdk/docs/install
```

* Login to your account
```
gcloud auth login
```
* Install the gcloud kubernetes auth plugin
```
gcloud components install gke-gcloud-auth-plugin
```


* Run the following command:
```
gcloud container clusters get-credentials levio-hkt-cluster --region us-east1 --project steadfast-leaf-390318
```

* You need `kubectl` in order to run commands for that k8s instance

## Install Helm

You need helm in order to install packages to k8s (you can think of it like you think of apt-get for debian).
Helm will be used for installing ArgoCD among with other CI/CD related tools. After this ArgoCD will take care of gitops for our application.

* Add argoCD repository (not everyone needs to perform this, do it only if you're intended to operate devOps in the project)
```
helm repo add argo https://argoproj.github.io/argo-helm
```

* Install ArgoCD (That's done only one, don't run this if you already have an ArgoCD configured)
```
kubectl create namespace cicd
```
```
helm install argocd argo/argo-cd --namespace cicd
```

* Use port-forward to be able to access argo-cd in your localhost
```
kubectl port-forward service/argocd-server -n cicd 7043:443
```
* Or patch it to be able to access without port-forwarding
```
kubectl patch svc argocd-server -n cicd -p '{"spec": {"type": "LoadBalancer"}}'

```

* Install the argocd cli (notice this is a link for linux, if you're on Windows or Mac, please find the proper link to install it)
```
https://localhost:7043/download/argocd-linux-amd64
```

* Login to argoCD using your user and password as if it was the ArgoCD login page
```
argocd login localhost:7043
```

## Create the k8s namespaces that will be used by the project
```
kubectl create namespace api-dev
kubectl create namespace api-prod
kubectl create namespace data-dev
kubectl create namespace data-prod
```

## Setup ArgoCD for CICD

* Add cluster
```
argocd cluster add gke_steadfast-leaf-390318_us-east1_levio-hkt-cluster --system-namespace cicd
```

* Workaround for cluster adding if necessary
```
gcloud container clusters upgrade levio-hkt-cluster --project steadfast-leaf-390318 --region us-east1  --master
```

* Create argocd projects
```
# gpg --full-generate-key
# gpg --list-secret-keys --keyid-format=long
# gpg --armor --export KEY_ID

argocd proj create database -d https://34.23.123.165,cicd -s ssh://git@github.com:healthinsurancehkt/helm-charts.git --allow-namespaced-resource cicd,data-dev,data-prod,api-dev,api-prod --signature-keys
```


## Install MYSQL through Helm
```
helm repo add mysql-operator https://mysql.github.io/mysql-operator/
helm repo update

helm install mysql-dev mysql-operator/mysql-operator --namespace data-dev
```

## Install Jenkins through Helm
```
helm repo add jenkins https://charts.jenkins.io
helm repo update

helm upgrade --install jenkins jenkins/jenkins -n cicd
kubectl patch svc jenkins -n cicd -p '{"spec": {"type": "LoadBalancer"}}'
```

## Configure Docker

```
gcloud auth configure-docker us-east1-docker.pkg.dev
```

## Build docker image
```
cp ~/.config/gcloud/legacy_credentials/youruser@email.com/adc.json ./credentials.json
```
```
docker build -t "us-east1-docker.pkg.dev/steadfast-leaf-390318/insurance-now/java-template-service:dev-aaa" .
```

```
docker push us-east1-docker.pkg.dev/steadfast-leaf-390318/insurance-now/java-template-service:dev-aaa
```

## Install the application through helm
```
# uninstall if necesary
#helm -n api-dev uninstall java-template-service

helm -n api-dev install -f ./helm/dev/values.yaml java-template-service ./helm/dev

kubectl patch svc java-template-service-helm -n api-dev -p '{"spec": {"type": "LoadBalancer"}}'
# if you need to specify port
# kubectl -n api-dev patch svc java-template-service-helm -p '{"spec": {"ports": [{"port": 80,"targetPort": 8383,"name": "http"}],"type": "LoadBalancer"}}'
```