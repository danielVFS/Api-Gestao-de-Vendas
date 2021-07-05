package com.daniel.gvendas.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.gvendas.dto.cliente.ClienteResponseDTO;
import com.daniel.gvendas.entities.Cliente;
import com.daniel.gvendas.services.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Cliente")
@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@ApiOperation(value = "Lista todas clientes")
	@GetMapping
	public List<ClienteResponseDTO> findAll() {
		return clienteService.listAll().stream().map(cliente -> ClienteResponseDTO.convertToClienteDTO(cliente))
				.collect(Collectors.toList());
	}

	@ApiOperation(value = "Lista um cliente por id")
	@GetMapping("/{id}")
	public ResponseEntity<ClienteResponseDTO> findById(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteService.findById(id);

		return cliente.isPresent() ? ResponseEntity.ok(ClienteResponseDTO.convertToClienteDTO(cliente.get()))
				: ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Cria um cliente")
	@PostMapping
	public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
		Cliente newCliente = clienteService.create(cliente);

		return ResponseEntity.status(HttpStatus.CREATED).body(newCliente);
	}

}
