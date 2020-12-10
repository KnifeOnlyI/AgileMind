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
```

# Create database backup

```bash
docker exec -ti docker_agilemind-postgresql_1 bash
pg_dump -U agilemind agilemind > /opt/agilemind.sql
psql -U agilemind agilemind < /opt/agilemind.sql
```

