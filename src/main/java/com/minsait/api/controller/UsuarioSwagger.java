package com.minsait.api.controller;


import com.minsait.api.controller.dto.MessageResponse;
import com.minsait.api.controller.dto.UsuarioRequest;
import com.minsait.api.controller.dto.UsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

@Tag(name="endpoints do curso de práricas tecnológicas")
public interface UsuarioSwagger {

    @Operation(summary = "Busca todos os usuários",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dados do usuário retornados com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
                    @ApiResponse(responseCode = "500", description = "Interno"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            })

    public ResponseEntity<Page<UsuarioResponse>> usuarioFindAll( String nome,
                                                                String login,
                                                                String email,
                                                                 int page,
                                                                 int pageSize);


    @Operation(summary = "Insere Usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário inserido com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
                    @ApiResponse(responseCode = "500", description = "Interno"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
    })

    public ResponseEntity<UsuarioResponse> insert(UsuarioRequest request);



    @Operation(summary = "Altera o Usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário alterado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
                    @ApiResponse(responseCode = "500", description = "Interno"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            })
    public ResponseEntity<UsuarioResponse> update(UsuarioRequest request);


    @Operation(summary = "Deleta Usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro Interno"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            })
    public ResponseEntity<MessageResponse> delete(Long id);



    @Operation(summary = "Buscar Usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro Interno"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            })
    public ResponseEntity<UsuarioResponse> usuarioFindId(Long id);

    }