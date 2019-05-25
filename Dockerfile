FROM openjdk:8-alpine
RUN apk update
EXPOSE 2552
COPY target/pack/bin /srv/cm/bin
COPY target/pack/lib /srv/cm/lib
WORKDIR /srv/cm
ENTRYPOINT ["sh", "./bin/start"]