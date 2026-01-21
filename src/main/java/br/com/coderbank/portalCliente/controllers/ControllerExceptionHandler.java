package br.com.coderbank.portalCliente.controllers;

import br.com.coderbank.portalCliente.dtos.response.ErrorResponseDTO;
import br.com.coderbank.portalCliente.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.util.HashMap;

    /*
    Essas validações de campos, quantidade minima e máxima de cada atributo, formato de um campo é feito aqui

    Validações como Cpf existir na receita devem ser feitas no Service, porq chamaos outra APi e etc
     */

@ControllerAdvice
public class ControllerExceptionHandler { //Aqui temos um controller global que vai receber qualquer tipo de excessão que ocorrer na Service

    @ExceptionHandler({ClienteJaExistenteException.class, ContaJaExistenteException.class}) //Passando que esse método vai realizar o tratamento adequado para essa Excessão, mas poderiamos passar uma lista de Excessões
    @ResponseBody //Informando que a excessão vai retornar um body pro endpoint onde o erro foi gerado
    @ResponseStatus(HttpStatus.CONFLICT) //Retornando status de conflito(409) todos status code da familia 400 são erro do cliente, como envio de dados incorretos
    public ErrorResponseDTO conflict(final RuntimeException exception) { //Basicamente o ErrorResponseDto vai responder com o erro e a hora, ele recebe um objeto Throwable que possui as infos da excessão

        final var exceptionMessage = exception.getMessage(); //Capturando o motivo da excessão

        return new ErrorResponseDTO(exceptionMessage, System.currentTimeMillis()); //Passando a resposta pro cliente do motivo do erro
    }

    @ExceptionHandler(ContaNaoEncontradaException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND) // Tratando excessões do tipo not Found(404)
    public ErrorResponseDTO handleNotFound(final RuntimeException exception) { //Usando RuntimeException porque usar o Handler generalizamos muito, qualquer tipo de erro capturamos

        return new ErrorResponseDTO(exception.getMessage(), System.currentTimeMillis());
    }

    @ExceptionHandler({SaldoInsuficienteException.class, ContaDestinoInvalidaException.class, TransferenciaParaMesmaContaException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleBusiness(final RuntimeException exception) {

        return new ErrorResponseDTO(exception.getMessage(),System.currentTimeMillis());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //Aqui teremos uma excessão que ocorre quando o formato do dado enviado pelo cliente é inválio ou aplicação não aceita
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleValidation(final  MethodArgumentNotValidException exception) {

        final var errors = new HashMap<>(); //Passamos um hashMAp porq alguns Request possuem mais de um tipo de validação e em uma única mensagem precisamos dizer oq está errado

        exception.getBindingResult() //Objeto que possui as informações do erro
                .getAllErrors() //Lista com todos os erros
                .forEach(error -> { //Capturando cade tipo de erro

                    var fieldName = ((FieldError) error).getField(); //Capturando os campos do body que deram erro

                    var errorMessage = error.getDefaultMessage(); //Capturando a mensagem de erro

                    errors.put(fieldName, errorMessage); //Mapeando cada mensagem de erro para o campo específico
                });

        return new ErrorResponseDTO(errors.toString(), System.currentTimeMillis()); //Retornando os erros
    }

}
