package ch.heig.examples.bim.entities;

import java.io.Serializable;
import java.util.Date;

public record FileEntity(int id, String name, Date creationDate) implements Serializable {}
