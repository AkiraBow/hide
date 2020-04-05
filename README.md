# Create k8s cluster microservice
## check k8s cluster
Run this command to check what are set in k8s cluster
>kubectl get all
## create worker node
install docker (https://phoenixnap.com/kb/how-to-install-docker-on-ubuntu-18-04)
>sudo apt-get update
>
>sudo apt-get remove docker docker-engine docker.io
>
>sudo apt install docker.io
>
>sudo systemctl start docker
>
>sudo systemctl enable docker
>
install kubeadm, kubelet and kubectl
>apt-get update && apt-get install -y apt-transport-https curl
>
>curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add -

>cat <<EOF >/etc/apt/sources.list.d/kubernetes.list
>deb http://apt.kubernetes.io/ kubernetes-xenial main
>EOF

>apt-get update
>
>apt-get install -y kubelet kubeadm kubectl
>
>sysctl net.bridge.bridge-nf-call-iptables=1
>
>systemctl daemon-reload
>
>systemctl restart kubelet

## additional steps for master node
>apt-get update && apt-get upgrade
>
>kubeadm init --pod-network-cidr=10.244.0.0/16

copy the output similar to:
>kubeadm join 172.31.38.70:6443 --token mocuzj.5iecxqrt77ltpx96 --discovery-token-ca-cert-hash sha256:57f28710a92132a3cceeb32a82ac82893446d7251606795a0b596c22d1cf5d6b

This will be used in worker node to join the cluster

>mkdir -p $HOME/.kube
>
>sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
>
>sudo chown $(id -u):$(id -g) $HOME/.kube/config

Install Flannel (https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/create-cluster-kubeadm/)
>kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/2140ac876ef134e0ed5af15c65e414cf26827915/Documentation/kube-flannel.yml

Run
>kubectl get services --all-namespaces -o wide

copy dns IP, this will be used in hide.yaml:dnsConfig

## join the cluster
run the command copied in above step in *all worker node*, something similar to
>kubeadm join 172.31.38.70:6443 --token mocuzj.5iecxqrt77ltpx96 --discovery-token-ca-cert-hash sha256:57f28710a92132a3cceeb32a82ac82893446d7251606795a0b596c22d1cf5d6b

Run 
>kubectl get nodes

It should show 3 nodes, all in ready status
## create deploy
refer to resources/k8s/hide.yaml
>kubectl create -f ./hide.yaml
## create service
refer to resources/k8s/hide-service.yaml
>kubectl create -f ./hide-service.yaml
## visit service from outside
http://{node public ip}:30000/allbooks
## apply changes to k8s
>kubectl apply -f ./{your config file}.yaml