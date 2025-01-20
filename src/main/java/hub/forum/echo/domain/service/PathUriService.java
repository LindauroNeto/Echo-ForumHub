package hub.forum.echo.domain.service;

import java.net.URI;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PathUriService {

	public URI criacaoPathUri(UriComponentsBuilder uriBuilder, Long id, String caminho) {
		String path = String.format("/%s/{id}", caminho);
		return uriBuilder.path(path).buildAndExpand(id).toUri();
	}
}
