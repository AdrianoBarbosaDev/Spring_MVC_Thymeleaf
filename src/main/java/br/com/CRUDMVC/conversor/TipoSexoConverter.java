package br.com.CRUDMVC.conversor;

import org.springframework.cglib.core.Converter;

import br.com.CRUDMVC.domain.TipoSexo;

public class TipoSexoConverter implements org.springframework.core.convert.converter.Converter<String, TipoSexo>{

	public TipoSexo convert (String texto) {
		char tipo = texto.charAt(0);
		return tipo == TipoSexo.FEMININO.getDesc() ? TipoSexo.FEMININO : TipoSexo.MASCULINO;
	}
}
