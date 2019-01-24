package com.BNPL.BuildParticles;

public class Distance {

	private float x;
	private float y;
	private float z;

	public Distance(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return getX() + ":" + getY() + ":" + getZ();
	}

	public static Distance getDistanceFromString(String text) {
		String[] cut = text.split(":");
		return new Distance(Float.parseFloat(cut[0]), Float.parseFloat(cut[1]), Float.parseFloat(cut[2]));
	}
}
