package com.tutego.date4u.core;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileSystem {
	private final Path root = Paths.get( "src/main/resources/static/images/unicorns/" );

	public FileSystem() {
		try {
			if( ! Files.isDirectory( root ) ) Files.createDirectory( root );
		} catch( IOException e ) {
			throw new UncheckedIOException( e );
		}
	}

	public long getFreeDiskSpace() {
		return root.toFile().getFreeSpace();
	}

	public byte[] load( String filename ) {
		try {
			return Files.readAllBytes( root.resolve( filename ) );
		} catch( IOException e ) {
			throw new UncheckedIOException( e );
		}
	}

	public void store( String filename, byte[] bytes ) {
		try {
			Files.write( root.resolve( filename ), bytes );
		} catch( IOException e ) {
			throw new UncheckedIOException( e );
		}
	}
}
