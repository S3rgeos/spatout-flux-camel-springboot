package com.groupeonepoint.codingchallenge.flux.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.BindyType;

import org.springframework.stereotype.Component;
import com.groupeonepoint.codingchallenge.flux.routes.entities.BearerToken;
import com.groupeonepoint.codingchallenge.flux.routes.entities.ListAlbums;
import com.groupeonepoint.codingchallenge.flux.routes.entities.AlbumMinify;
import com.groupeonepoint.codingchallenge.flux.routes.processor.AlbumProcessor;



@Component
public class SampleRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		

		from("file:src/main/resources/?fileName=test.json&noop=true").routeId("ResponseToPOJO")
			.unmarshal(new JacksonDataFormat( ListAlbums.class))
			.process(new AlbumProcessor())
			.split(body())
			.wireTap("direct:toCSV")
			.marshal().json(org.apache.camel.model.dataformat.JsonLibrary.Jackson)
			.to("direct:toMq");

		// /*Route MAIN*/
		// from("timer://get-new-tracks?fixedRate=true&period=3600000&repeatCount=1").routeId("SpotifyMain")
		// 	.log("clientId={{spotify.clientId}}, clientSecret={{spotify.clientSecret}}")
		// 	.to("direct:getSpotifyToken");
		// 	//.to("direct:getSpotifyWeekLP")
		
		// /*Route AUTH */
		// from("direct:getSpotifyToken").routeId("getSpotifyToken")
		// 	.log("Appel de l'API Spotify token {{spotify.uri_token}}")
		// 	.setBody().simple("{{spotify.clientId}}:{{spotify.clientSecret}}")
		// 	.marshal().base64(HIGHEST, null, true)
		// 	.setHeader("Authorization",simple("Basic ${body}"))
		// 	.setHeader(Exchange.HTTP_METHOD, constant("POST"))
		// 	.setHeader(Exchange.CONTENT_TYPE, constant("application/x-www-form-urlencoded"))
		// 	.setBody().constant("grant_type=client_credentials")
		// 	.to("{{spotify.uri_token}}")
		// 	.unmarshal(new JacksonDataFormat(BearerToken.class))
		// 	.setHeader("Authorization", simple("Bearer ${body.access_token}"));
			

		// /*Route SEARCH */
		// from("direct:getSpotifyWeekLP").routeId("getSpotifyWeekLP")
		// 	.log("Récupération des albums de la semaine")
		// 	.setHeader(Exchange.HTTP_METHOD, constant("GET"))
		// 	.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
		// 	.setHeader(Exchange.HTTP_QUERY, simple("q=tag%3Anew&type=album&market=US&limit=50&offset=0"))
		// 	.to("{{spotify.uri_search}}")
		// 	.unmarshal(new JacksonDataFormat(ListAlbums.class))
		// 	.log("${body.href}");
		// 	

		/* Route RabbitMQ */
		from("direct:toMq").routeId("toMq")
		.log("Dépôt des résultats dans la queue {{rabbitmq.queue}}: ")
		.to("rabbitmq://{{rabbitmq.uri}}/tasks?username={{rabbitmq.user}}&password={{rabbitmq.pwd}}&autoDelete=false&routingKey=camel&queue={{rabbitmq.queue}}");

		/* Route CSV */
		from("direct:toCSV").routeId("toCSV")
		.log("Création de l'export CSV")
		.marshal().bindy(BindyType.Csv, AlbumMinify.class)
		.to("file:src/main/resources/?fileName=albums_${header.DateTraitement}.csv&fileExist=Append");
	}
}
