package de.cooperateproject.cdo.dawn.rest.util;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.eclipse.emf.ecore.EObject;

import com.google.gson.Gson;

/**
 * The Emf Ready Provider is used to serialize messages. A interal switch uses
 * emfjson for emf-objects and gson for every other objects
 * 
 * @author Sebastian Hahner (sebinside)
 */
@Provider
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class EMFReadyProvider<T> implements MessageBodyReader<T>, MessageBodyWriter<T> {

	private Gson gson = new Gson();
	//private ObjectMapper jsonMapper = new ObjectMapper();

	public EMFReadyProvider() {
		// ResourceSet resourceSet = new ResourceSetImpl();
		// EMFModule module = new EMFModule(resourceSet); // TODO: Settings?
		// jsonMapper.registerModule(module);
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public long getSize(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		if (t instanceof EObject) {
			// FIXME: Killed emfjson. Never working as intended
			// String json = jsonMapper.writeValueAsString(t);
			// try (OutputStream stream = entityStream) {
			// entityStream.write(json.getBytes("utf-8"));
			// entityStream.flush();
			// }
		} else {
			try (OutputStream stream = entityStream) {
				entityStream.write(gson.toJson(t).getBytes("utf-8"));
				entityStream.flush();
			}
		}
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public T readFrom(Class<T> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
		// FIXME: Can currently not read EObjects
		// See: http://emfjson.org/projects/jackson/latest/
		try (InputStreamReader reader = new InputStreamReader(entityStream, "UTF-8")) {
			return gson.fromJson(reader, type);
		}
	}

}
