package com.marcone.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF8");
		}
		catch(UnsupportedEncodingException e) {
			return "";
		}
	}
	
	public static Iterable<Integer> decodeIntList(String s){
		String[] vet = s.split(",");
		Iterable<Integer> list = new ArrayList<>();
		for( int i=0; i<vet.length; i++) {
			((List<Integer>) list).add(Integer.parseInt(vet[i]));
		}
		return list;
		// com expressao lambda
		//return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}

}
