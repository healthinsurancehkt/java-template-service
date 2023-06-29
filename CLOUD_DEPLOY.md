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
Helm will be used for installing your service in any of the environments you decide to deploy (api-dev/api-prod/other)

## Configure Docker

```
gcloud auth configure-docker us-east1-docker.pkg.dev
```

## Build docker image
```
cp ~/.config/gcloud/legacy_credentials/your@email.com/adc.json ./credentials.json
```

```
docker build -t "us-east1-docker.pkg.dev/steadfast-leaf-390318/insurance-now/java-template-service:dev-001" .
```

```
docker push us-east1-docker.pkg.dev/steadfast-leaf-390318/insurance-now/java-template-service:dev-001
```

## Install the application through helm
```
# uninstall if necesary
#helm -n api-dev uninstall java-template-service

helm -n api-dev install -f ./helm/dev/values.yaml java-template-service ./helm/dev

kubectl patch svc java-template-service-helm -n api-dev -p '{"spec": {"type": "LoadBalancer"}}'
# if you need to specify port
# kubectl -n api-dev patch svc java-template-service-helm -p '{"spec": {"ports": [{"port": 80,"targetPort": 8383,"name": "http"}],"type": "LoadBalancer"}}'