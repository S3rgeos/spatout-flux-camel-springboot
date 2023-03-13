package com.groupeonepoint.codingchallenge.flux.routes.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.groupeonepoint.codingchallenge.flux.routes.entities.AlbumMinify;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> albumsMap = new HashMap<String, Object>();
        AlbumMinify albumMinify = new AlbumMinify();
        
        if (exchange.getIn().getBody() != null) {

            albumMinify = (AlbumMinify)exchange.getIn().getBody();

                albumsMap.put("id", albumMinify.getId());
                albumsMap.put("name", albumMinify.getName());
                albumsMap.put("first_artist_name", albumMinify.getArtist());
                albumsMap.put("release_date",albumMinify.getReleaseDateDate());
                albumsMap.put("track_number", albumMinify.getTotalTracks());

            exchange.getIn().setBody(albumsMap);
        }
    }
}
