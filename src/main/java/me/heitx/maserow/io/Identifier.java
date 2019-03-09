package me.heitx.maserow.io;

import java.util.ArrayList;
import java.util.List;

// Taken from an old project ..
public class Identifier {
	private Integer id;
	private Long value;
	private String name;

	public Identifier(Integer id, long value, String name) {
		this.id = id;
		this.value = value;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public long getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += id == null ? 0 : id.hashCode();
		hash += value == null ? 0 : value.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals = false;

		if(obj instanceof Identifier) {
			Identifier other = (Identifier) obj;
			equals = id.equals(other.id) && value.equals(other.value) && name.equals(other.name);
		}

		return equals;
	}

	@Override
	public String toString() {
		return id + " : " + value + " : " + name;
	}

	public static Integer findsIndexById(List<Identifier> identifiers, int id) {
		return findsIndexBy(identifiers, id, true);
	}

	public static Integer findsIndexByValue(List<Identifier> identifiers, long value) {
		return findsIndexBy(identifiers, value, false);
	}

	private static Integer findsIndexBy(List<Identifier> identifiers, long by, boolean byId) {
		Integer result = null;

		for(int i = 0; i < identifiers.size(); i++) {
			Identifier identifier = identifiers.get(i);
			if((byId && by == identifier.id) || (!byId && by == identifier.value)) {
				result = i;
				break;
			}
		}

		return result;
	}

	public static Identifier findsById(List<Identifier> identifiers, int id) {
		return findsBy(identifiers, id, true);
	}

	public static Identifier findsByValue(List<Identifier> identifiers, long value) {
		return findsBy(identifiers, value, false);
	}

	private static Identifier findsBy(List<Identifier> identifiers, long by, boolean byId) {
		Identifier result = null;

		for(Identifier identifier : identifiers) {
			if((byId && by == identifier.id) || (!byId && by == identifier.value)) {
				result = identifier;
				break;
			}
		}

		return result;
	}

	public static List<Integer> findIndicesByValue(List<Identifier> identifiers, long totalValue) {
		return findByValue(identifiers, totalValue, false, true);
	}

	public static List<Integer> findIndicesByValue(List<Identifier> identifiers, long totalValue, boolean valueZeroEqualsAll) {
		return findByValue(identifiers, totalValue, valueZeroEqualsAll, true);
	}

	private static List<Integer> findByValue(List<Identifier> identifiers, long totalValue, boolean valueZeroEqualsAll, boolean findIndexes) {
		List<Integer> integers = new ArrayList<>();

		if(totalValue >= (valueZeroEqualsAll ? 1 : 0)) {
			for(int i = identifiers.size() - 1; valueZeroEqualsAll ? i > 0 : i >= 0; i--) {
				Identifier identifier = identifiers.get(i);

				if(totalValue >= identifier.getValue()) {
					integers.add(findIndexes ? i : identifier.id);
					totalValue -= identifier.getValue();
				}

				if(totalValue == 0) break;
			}
		} else {
			for(int i = 0; i < identifiers.size(); i++) {
				integers.add(i);
			}
		}

		return integers;
	}

	public static long calculateValue(List<Identifier> identifiers) {
		return calculateValue(identifiers, null);
	}

	public static long calculateValue(List<Identifier> identifiers, List<Identifier> match) {
		return calculateValue(identifiers, match, -1);
	}

	public static long calculateValue(List<Identifier> identifiers, List<Identifier> match, long ifMatchSetValue) {
		long value = 0;

		if(match != null && identifiers.size() == match.size()) {
			value = ifMatchSetValue;
		} else {
			for(Identifier identifier : identifiers) {
				value += identifier.getValue();
			}
		}

		return value;
	}
}