
CREATE TABLE challenge.new_album (
	id varchar(50) NOT NULL,
	name varchar(100) NULL,
	first_artist_name varchar(100) NULL,
	release_date TIMESTAMP NULL,
	track_number TINYINT NULL,
	CONSTRAINT new_album_pk PRIMARY KEY (id)
);
