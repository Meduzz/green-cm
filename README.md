# green-cm
Compute module for the green framework.

## Uhm, what be dis?
Green is a take on scaling jvm apps smarter. And this repo is sort of the backbone of that. This is an akka cluster with no added bells and whistles. If you provide an address to join, it will attempt to join that address, otherwise it will start a cluster and attempt to register it in the available discovery services.

## You mentioned discovery services
This repo alone only have access to the default discovery, which is a system property, and no way to register. But that api is a SPI, so you can provide your own means of discovery and registration.

# Action

> docker run --rm -p 2552:2552 meduzz/greencm:0.1 {a public hostname}

```a public hostname``` is used to tell akka where to expect traffic, it's pretty anal about that. When I test locally I use my computers "public" ip. 

If akka was not pleased with your choice of hostname you will see logs like this in the compute module when you try to connect to it:

> [ERROR] ... dropping message [class akka.actor.ActorSelectionMessage] for non-local recipient [Actor[akka.tcp://Green@192.168.1.123:2552/]] arriving at [akka.tcp://Green@192.168.1.123:2552] inbound addresses are [akka.tcp://Green@172.17.0.2:2552]