# Quarkus Proof of Work Hasher as a Function 

This is a very simple [OpenFaaS](https://www.openfaas.com) demo using a [Quarkus](https://quarkus.io) function.
The function calculates a hash value based on the given (and currently hard coded) 
difficulty for the requested string. 

## Building and Usage 

You can build the image using simply Docker or faas-cli.

In this case, as it is a OpenFaas demo, lets go the OpenFaaS way and build the image.

**Attention:** This step can take a lot of time and, more important, consume a lot of bandwith as maven downloads the half of the internet. 

```
faas-cli build -f `qpowhash.yml`
...
Successfully tagged hcguersoy/qpowhash:0.1.0
Image: hcguersoy/qpowhash:0.1.0 built.
```

Local testing is simple:

```
docker run -it --rm -p 8080:8080 hcguersoy/qpowhash:0.1.0

# in an other console
curl --no-keepalive http://localhost:8080/teststring
00000e54c75e8d4b6073a050c4f5b6921e4eca68699bb77fc4760862e12d5007%
``` 

Then you can push the image using _faas-cli_, but you should first modify the file `qpowhash.yml` pointing to your own repository.

```
faas-cli push -f qpowhash.yml
```

To deploy the created function you have to authenticate to your OpenFaaS installation first. 
After that, simply deploy:

```
faas-cli --gateway ${FAAS_IP}:${FAAS_PORT} deploy -f qpowhash.yml
```

## Credits

* [Maven Wrapper](https://github.com/takari/maven-wrapper) (Apache-2.0). The wrapper is included in this repository.
* [Quarkus](https://quarkus.io)
* [OpenFaaS](https://www.openfaas.com) 

## Contribution

Please feel free to contribute new ideas, improvements and more using a pull request.

Then you create a pull request, please be aware that I'll only accept them if all commits are signed off (see the git manual for more details).  