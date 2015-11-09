package com.btc.supports;

public class BukkenType {
	public final static int XEVO = 0;
	public final static int SHIGUMA = 1;
	public final static String[] Type = {"XEVO", "Σ" };
	public final static String getType(int id) {
		if (id < 0 || id > BukkenType.Type.length - 1) throw new IndexOutOfBoundsException();
		return BukkenType.Type[id];
	}
}
