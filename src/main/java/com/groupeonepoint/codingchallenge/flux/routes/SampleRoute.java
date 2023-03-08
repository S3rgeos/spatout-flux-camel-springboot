package com.groupeonepoint.codingchallenge.flux.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SampleRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("timer://get-new-tracks?fixedRate=true&period=3600000")
			.log("clientId={{spotify.clientId}}, clientSecret={{spotify.clientSecret}}")
			.to("log:codingchallenge");
	}
}
