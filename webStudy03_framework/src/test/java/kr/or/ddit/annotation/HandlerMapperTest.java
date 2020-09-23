package kr.or.ddit.annotation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.mvc.annotation.HandlerMapper;
import kr.or.ddit.mvc.annotation.IHandlerMapper;

public class HandlerMapperTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindCommandHandler() {
		IHandlerMapper handlerMapper = new HandlerMapper("kr.or.ddit");
		handlerMapper.findCommandHandler(null);
	}

}
