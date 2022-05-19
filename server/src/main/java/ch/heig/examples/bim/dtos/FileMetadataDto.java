package ch.heig.examples.bim.dtos;

import java.util.Date;

public record FileMetadataDto(String name, Date creationDate, Date lastUpdate) {}
