package com.evolution.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EvolutionExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String messageUser = messageSource.getMessage("message.invalid", null, LocaleContextHolder.getLocale());
		String messageDeveloper = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(messageUser, messageDeveloper));
		return handleExceptionInternal(ex, erros.get(0), headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros.get(0), headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
			WebRequest request) {
		String messageUser = messageSource.getMessage("resource.not-found", null, LocaleContextHolder.getLocale());
		String messageDeveloper = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(messageUser, messageDeveloper));
		return handleExceptionInternal(ex, erros.get(0), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {
		String messageUser = messageSource.getMessage("resource.operation-not-allowed", null,
				LocaleContextHolder.getLocale());
		String messageDeveloper = ExceptionUtils.getRootCauseMessage(ex);
		List<Erro> erros = Arrays.asList(new Erro(messageUser, messageDeveloper));
		return handleExceptionInternal(ex, erros.get(0), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ org.hibernate.exception.GenericJDBCException.class })
	public ResponseEntity<Object> GenericJDBCException(org.hibernate.exception.GenericJDBCException ex,
			WebRequest request) {

		String messageUser = ExceptionUtils.getRootCauseMessage(ex).replaceAll("SQLException: ", "");
		String messageDeveloper = ExceptionUtils.getRootCauseMessage(ex).replaceAll("SQLException: ", "");

		List<Erro> erros = Arrays.asList(new Erro(this.getMessageOracle(messageUser), messageDeveloper));
		return handleExceptionInternal(ex, erros.get(0), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ com.evolution.service.ExceptionService.class })
	public ResponseEntity<Object> NegocioExceptionService(com.evolution.service.ExceptionService ex,
			WebRequest request) {
		String messageUser = ExceptionUtils.getRootCauseMessage(ex).replaceAll("Erro: ", "");
		String messageDeveloper = ExceptionUtils.getRootCauseMessage(ex).replaceAll("Erro: ", "");
		List<Erro> erros = Arrays.asList(new Erro(this.getMessageExceptionService(messageUser), messageDeveloper));
		return handleExceptionInternal(ex, erros.get(0), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ com.evolution.service.ExceptionEntityService.class })
	public ResponseEntity<Object> ExceptionEntityService(com.evolution.service.ExceptionEntityService ex,
			WebRequest request) {
		String messageUser = ExceptionUtils.getRootCauseMessage(ex).replaceAll("Erro: ", "");
		String messageDeveloper = ExceptionUtils.getRootCauseMessage(ex).replaceAll("Erro: ", "");
		List<Erro> erros = Arrays.asList(new Erro(this.getMessageExceptionService(messageUser), messageDeveloper));
		return handleExceptionInternal(ex, erros.get(0), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	private List<Erro> criarListaDeErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String messageUser = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String messageDeveloper = fieldError.toString();
			erros.add(new Erro(messageUser, messageDeveloper));
		}

		return erros;
	}

	public static class Erro {

		private String messageUser;
		private String messageDeveloper;

		public Erro(String messageUser, String messageDeveloper) {
			this.messageUser = messageUser;
			this.messageDeveloper = messageDeveloper;
		}

		public String getMessageUser() {
			return messageUser;
		}

		public String getMmssageDeveloper() {
			return messageDeveloper;
		}

	}

	public String getMessageOracle(String value) {
		int fim = 0;
		char b = '#';
		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) == b) {
				fim = i;
			}
		}

		if (fim >= 11) {
			return value.substring(11, fim);
		} else {
			return value;
		}
	}

	public String getMessageExceptionService(String value) {
		return value.substring(24, value.length());
	}
}