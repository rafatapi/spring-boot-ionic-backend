package br.com.ae.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ae.cursomc.domain.Categoria;
import br.com.ae.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	// -----------------------------------------------------------------------
	// FIND
	// -----------------------------------------------------------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	// -----------------------------------------------------------------------
	// INSERT - POST
	// -----------------------------------------------------------------------
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Categoria> insert(@RequestBody Categoria obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	// -----------------------------------------------------------------------
	// UPDATE - PUT
	// -----------------------------------------------------------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Categoria> update(@RequestBody Categoria obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
		
		
	}
	
	// -----------------------------------------------------------------------
	// DELETE - DELETE
	// -----------------------------------------------------------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	

}
