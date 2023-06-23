package com.minsait.api.controller;

import com.minsait.api.controller.dto.MessageResponse;
import com.minsait.api.controller.dto.UsuarioRequest;
import com.minsait.api.controller.dto.UsuarioResponse;
import com.minsait.api.repository.UsuarioEntity;
import com.minsait.api.repository.UsuarioRepository;
import com.minsait.api.util.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class UsuarioController implements UsuarioSwagger{

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PreAuthorize("hasAuthority('LEITURA_USUARIO')")
	@GetMapping("/usuario")
	public ResponseEntity<Page<UsuarioResponse>> usuarioFindAll(@RequestParam (required = false) String nome,
																@RequestParam (required = false) String login,
																@RequestParam (required = false) String email,
																@RequestParam (required = false, defaultValue = "0") int page,
																@RequestParam (required = false, defaultValue = "10") int pageSize) {
		final var usuarioEntity =  new UsuarioEntity();
		usuarioEntity.setNome(nome);
		usuarioEntity.setLogin(login);
		usuarioEntity.setEmail(email);
		Pageable pageable = PageRequest.of(page, pageSize);

		final Page<UsuarioEntity> usuarioEntityListPage = usuarioRepository
				.findAll(usuarioEntity.usuarioEntitySpecification(),pageable);

		final var usuarioResponseList = ObjectMapperUtil
				.mapAll(usuarioEntityListPage, UsuarioResponse.class);
		return ResponseEntity.ok(usuarioResponseList);
	}

	@PreAuthorize("hasAuthority('ESCRITA_USUARIO')")
	@PostMapping("/usuario")
	public ResponseEntity<UsuarioResponse> insert(@RequestBody UsuarioRequest request) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		request.setSenha(encoder.encode(request.getSenha()));

		final var usuarioEntity = ObjectMapperUtil.map(request, UsuarioEntity.class);
		final var usuarioInsert = usuarioRepository.save(usuarioEntity);
		final var usuarioResponse = ObjectMapperUtil.map(usuarioInsert, UsuarioResponse.class);

		return new ResponseEntity<>(usuarioResponse, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('ESCRITA_USUARIO')")
	@PutMapping("/usuario")
	public ResponseEntity<UsuarioResponse> update(@RequestBody UsuarioRequest request) {

		final var usuarioEntity = ObjectMapperUtil.map(request, UsuarioEntity.class);

		final var usuarioEntityFound = usuarioRepository.findById(usuarioEntity.getId());

		if(usuarioEntityFound.isEmpty()){
			return new ResponseEntity<>(new UsuarioResponse(), HttpStatus.NOT_FOUND);
		}

		if(Objects.isNull(request.getLogin())){
			usuarioEntity.setSenha(usuarioEntityFound.get().getLogin());
		}


		if(Objects.isNull(request.getSenha())){
			usuarioEntity.setSenha(usuarioEntityFound.get().getSenha());
		}

		if(Objects.nonNull(request.getSenha())){
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			usuarioEntity.setSenha(encoder.encode(request.getSenha()));
		}

		final var usuarioInsert = usuarioRepository.save(usuarioEntity);

		final var usuarioResponse = ObjectMapperUtil.map(usuarioInsert, UsuarioResponse.class);

		return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ESCRITA_USUARIO')")
	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {

		final var usuarioEntityFound = usuarioRepository.findById(id);

		if (usuarioEntityFound.isPresent()){
			usuarioRepository.delete(usuarioEntityFound.get());
		} else {

			return new ResponseEntity<>(MessageResponse.builder()
					.message("Usuário não encontrado")
					.date(LocalDateTime.now())
					.error(false)
					.build(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(MessageResponse.builder()
				.message("OK")
				.date(LocalDateTime.now())
				.error(false)
				.build(), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('LEITURA_USUARIO')")
	@GetMapping("/usuario/{id}")
	public ResponseEntity<UsuarioResponse> usuarioFindId(@PathVariable Long id) {

		final var usuarioEntity = usuarioRepository.findById(id);

		UsuarioResponse usuarioResponse = new UsuarioResponse();

		if (usuarioEntity.isPresent()){
			usuarioResponse = ObjectMapperUtil.map(usuarioEntity.get(), UsuarioResponse.class);
		} else {

			return new ResponseEntity<>(usuarioResponse, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
	}



}