package com.groupeonepoint.codingchallenge.flux.routes.processor;

import org.apache.camel.Processor;
import org.apache.camel.Exchange;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import com.groupeonepoint.codingchallenge.flux.routes.entities.ListAlbums;
import com.groupeonepoint.codingchallenge.flux.routes.entities.AlbumMinify;
import com.groupeonepoint.codingchallenge.flux.routes.entities.Item;

public class AlbumProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        ListAlbums albums;
        List<AlbumMinify> allbumsMinified = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateTraitement = sdf.format(new Date());

        if (exchange.getIn().getBody() != null) {
            albums = (ListAlbums)exchange.getIn().getBody();
            
            List<Item> items = albums.getAlbums().getItems();
            AlbumMinify albumMinify;

            for (Item i : items) {
                albumMinify = new AlbumMinify();
                albumMinify.setArtist(i.getArtists().get(0).getName());
                albumMinify.setId(i.getId());
                albumMinify.setName(i.getName());
                albumMinify.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd").parse(i.getRelease_date()));
                albumMinify.setTotalTracks(i.getTotal_tracks());
                /*Ajout à la liste d'album*/
                allbumsMinified.add(albumMinify);
            }

            /*La liste d'albums repart dans le body du message*/
            exchange.getIn().setBody(allbumsMinified);
            exchange.getIn().setHeader("DateTraitement",dateTraitement);
            
        }
    }

}
