# AgileMind

An agile management software

## Development commands

Start maildev server (local mail server)

```bash
docker-compose -f src/main/docker/maildev.yml up -d
```

## Deployment commands

Needed environment variables :

```bash
echo "export AGILEMIND_SERVER_URL=<SERVER_URL>" >> ~/.bashrc
echo "export AGILEMIND_GOOGLE_EMAIL=<EMAIL>" >> ~/.bashrc
echo "export AGILEMIND_GOOGLE_PASSWORD=<PASSWORD>" >> ~/.bashrc
echo "export AGILEMIND_REGISTRY_PASSWORD=<PASSWORD>" >> ~/.bashrc
echo "export AGILEMIND_JWT_SECRET_BASE_64=<PASSWORD>" >> ~/.bashrc
echo "export AGILEMIND_KEYSTORE_PASSWORD=<PASSWORD>" >> ~/.bashrc

source ~/.bashrc
```

Build application

```bash
mvn package -Pprod -DskipTests verify jib:dockerBuild
```

Start application in background

```bash
docker-compose -f src/main/docker/app.yml up -d
```

Stop application

```bash
docker-compose -f src/main/docker/app.yml stop
```

## Docker config

Mounted volumes :

- ~/volumes/jhipster/AgileMind/postgresql/:/var/lib/postgresql/data/
- ~/volumes/jhipster/AgileMind/backups/:/opt/

# Certbot (HTTPS)

```bash
# Generate Certbot certificate
sudo certbot certonly --standalone

# Convert certbot certificate to keystore.p12 (PKCS12)
cd /etc/letsencrypt/live/agilemind.tech
openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out keystore.p12 -name agilemind -CAfile chain.pem -caname root

mv keystore.p12 ~/AgileMind/src/main/resources/config/tls/keystore.p12
```

Don't forget to update env variable AGILEMIND_KEYSTORE_PASSWORD according to new keystore.p12 password !
