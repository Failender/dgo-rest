package de.failender.dgo.rest.helden.geld;


public enum Waehrung {

	Kreuzer("Mittelreich"), Heller("Mittelreich"), Silbertaler("Mittelreich"), Dukat("Mittelreich");

	private String währungName;
	private Waehrung baseWaehrung;
	private Waehrung prev;
	private Waehrung next;
	private int value = 1;

	Waehrung(String währungName) {
		this.währungName = währungName;
	}

	static {
		Kreuzer.baseWaehrung = Kreuzer;
		Heller.baseWaehrung = Kreuzer;
		Silbertaler.baseWaehrung = Kreuzer;
		Dukat.baseWaehrung = Kreuzer;

		Kreuzer.next = Heller;

		Heller.prev = Kreuzer;
		Heller.next = Silbertaler;
		Heller.value = 10;

		Silbertaler.prev = Heller;
		Silbertaler.next = Dukat;
		Silbertaler.value = 100;

		Dukat.prev = Silbertaler;
		Dukat.value = 1000;

	}

	public Waehrung getBaseWaehrung() {
		return baseWaehrung;
	}

	public Waehrung getPrev() {
		return prev;
	}

	public Waehrung getNext() {
		return next;
	}

	public int getValue() {
		return value;
	}

	public String getWährungName() {
		return währungName;
	}
}
