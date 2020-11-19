# AgileMind

An agile management software

## Deployment commands

Set environment variables (first deployment) :

```bash
echo "export REGISTRY_PASSWORD=<PASSWORD>" >> ~/.bashrc

source ~/.bashrc
```

Build application

```bash
mvn package -Pprod -DskipTests verify jib:dockerBuild
```

Stop application

```bash
docker-compose -f src/main/docker/app.yml stop
```

Start application in background

```bash
docker-compose -f src/main/docker/app.yml up -d
```

## Docker config

Mounted volumes :

- ~/volumes/jhipster/AgileMind/postgresql/:/var/lib/postgresql/data/
