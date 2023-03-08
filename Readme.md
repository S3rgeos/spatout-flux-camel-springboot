
# Setup

## Créer un compte Spotify

* Aller sur [Spotify for Developers](https://developer.spotify.com/dashboard/)
* Créer un compte developer
* Créer une application
* Récupérer le "Client ID" et le "Client Secret"
* Le mettre dans le fichier application.properties

## Build

./mvnw package

## Launch in dev mode

### Docker compose

Pour utiliser une base de données ou un rabbitmq dans vos développements, vous pouvez utilisez le fichier docker-compose.yml disponible dans ce projet.

> Il vous faut docker d'installer sur votre machine : [Get Docker](https://docs.docker.com/get-docker/)

Pour démarrer, rabbitmq :
`docker compose up rabbitmq`

Pour démarrer, mariadb :
`docker compose up mariadb`

Pour ré-initialiser les serveurs rabbitmq et mariadb dans docker :
`docker compose down -v`

### Launch camel module

./mvnw spring-boot:run
