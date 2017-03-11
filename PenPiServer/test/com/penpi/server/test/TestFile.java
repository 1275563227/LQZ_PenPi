package com.penpi.server.test;

import java.io.File;

import org.junit.Test;

public class TestFile {

	@Test
	public void  test1() {
		File file = new File("D:\\uplufa");
		if (!file.exists())
			file.mkdir();
		file = new File("D:\\uplufa\\dadas");
		if (!file.exists())
			file.mkdir();
	}
}
