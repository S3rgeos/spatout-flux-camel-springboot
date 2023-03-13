package com.groupeonepoint.codingchallenge.flux.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;
import com.groupeonepoint.codingchallenge.flux.routes.entities.BearerToken;
import com.groupeonepoint.codingchallenge.flux.routes.entities.ListAlbums;
import com.groupeonepoint.codingchallenge.flux.routes.entities.Album;

@Component
public class SampleRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		/*Route MAIN*/
		from("timer://get-new-tracks?fixedRate=true&period=3600000&repeatCount=1")
			.log("clientId={{spotify.clientId}}, clientSecret={{spotify.clientSecret}}")
			.to("direct:getSpotifyToken")
			.to("direct:getSpotifyWeekLP");	
		
		/*Route AUTH */
		from("direct:getSpotifyToken").routeId("getSpotifyToken")
			.log("Appel de l'API Spotify token {{spotify.uri_token}}")
			.setBody().simple("{{spotify.clientId}}:{{spotify.clientSecret}}")
			.marshal().base64()
			.convertBodyTo(String.class)
			.setHeader("Authorization",simple("Basic NmI0NzdjODMzZGNlNDA1YzkxMWQxMGNkMjQzYzZkOWM6Njg1NDM0YTk0NTQ1NGVmZWI1MWYyZDMzNjYyOTA4NGU="))
			//.setHeader("Authorization",simple("Basic ${body}")) //le base 64 est bon mais l'authentification en fonctionne pas avec cette méthode
			.setHeader(Exchange.HTTP_METHOD, constant("POST"))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/x-www-form-urlencoded"))
			.setBody().constant("grant_type=client_credentials")
			.to("{{spotify.uri_token}}")
			.unmarshal(new JacksonDataFormat(BearerToken.class))
			.setHeader("BearerToken", simple("${body.access_token}"))
			.setBody(simple("spotify.bearer=${header.BearerToken}"));
			//.to("file://src/main/resources?fileName=application.properties&fileExist=Append"); //toDo: Ajouter un retour à la ligne avant d'insérer le param
			

		/*Route SEARCH */
		from("direct:getSpotifyWeekLP").routeId("getSpotifyWeekLP")
			.log("Récupération des albums de la semaine")
			.setHeader(Exchange.HTTP_METHOD, constant("GET"))
			.setHeader("Authorization", simple("Bearer {{spotify.bearer}}"))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
			.setHeader(Exchange.HTTP_QUERY, simple("q=tag%3Anew&type=album&market=US&limit=50&offset=0"))
			.to("{{spotify.uri_search}}")
			.unmarshal(new JacksonDataFormat(ListAlbums.class))
			.log("${body.artistName}");
			//.to("rabbitmq://{{rabbitmq.uri}}/tasks?username={{rabbitmq.user}}&password={{rabbitmq.pwd}}&autoDelete=false&routingKey=camel&queue={{rabbitmq.queue}}")
	}
}
