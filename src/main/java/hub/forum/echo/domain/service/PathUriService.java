package hub.forum.echo.domain.service;

import java.net.URI;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PathUriService {

	public URI criacaoPathUri(UriComponentsBuilder uriBuilder, Long id) {
		return uriBuilder.path("/cadastro/{id}").buildAndExpand(id).toUri();
	}
}
