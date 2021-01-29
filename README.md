# Code for the canary demo as seen on [Tech Talk Thursday](https://www.youtube.com/user/QAwareGmbH/playlists)

Steps to get it running yourself:

1. Start Minikube
1. Install Linkerd CLI (https://linkerd.io/2/getting-started/), latest stable version (tested with 2.9.2)
1. Install linkerd `linkerd install | kubectl apply -f -`
1. Annotate default namespace to enable linkerd injection: `kubectl annotate namespace default linkerd.io/inject=enabled`
1. Install Minikube Ingress addon: `minikube addons enable ingress`
1. Build and deploy [`canary-proxy`](canary-proxy)
1. Install flagger:
  1. `helm repo add flagger https://flagger.app`
  1. `kubectl apply -f https://raw.githubusercontent.com/fluxcd/flagger/main/artifacts/flagger/crd.yaml`
  1. `helm upgrade -i flagger flagger/flagger --namespace=linkerd --set crd.create=false --set meshProvider=linkerd --set metricsServer=http://linkerd-prometheus:9090`
1. Build our application in 4 versions: version 1 which works, version 2 which works, version 3 which is broken, version 4 which works
  1. `VERSION=1 FAILURE=0 ./build-to-minikube.sh`
  1. `VERSION=2 FAILURE=0 ./build-to-minikube.sh`
  1. `VERSION=3 FAILURE=100 ./build-to-minikube.sh`
  1. `VERSION=4 FAILURE=0 ./build-to-minikube.sh`
1. Deploy the application in K8s (`deploy.sh`)
1. Get IP of minikube (the ingress IP) with `minikube ip`
  1. Open that in browser
1. Run `linkerd dashboard` to open the linkerd dashboard in a browser
1. Now you can deploy the different versions, remember to refresh the browser so that the canaries get enough traffic
  1. To deploy a different version, modify [`k8s/01_deployment.yaml`](k8s/01_deployment.yaml) and deploy to k8s (`deploy.sh`)

## Included pictures

The included pictures of the dog, cat, moon and the tree are from Wikipedia:

* [Tree](https://de.wikipedia.org/wiki/Baum#/media/Datei:Baum_im_Sossusvlei.jpg)
* [Dog](https://de.wikipedia.org/wiki/Haushund#/media/Datei:Sennenhund.jpg)
* [Cat](https://de.wikipedia.org/wiki/Hauskatze#/media/Datei:Hauskatze_langhaar.jpg)
* [Moon](https://de.wikipedia.org/wiki/Mond#/media/Datei:Full_Moon_Luc_Viatour.jpg)

