package fr.jdegut.main.env;

import java.time.LocalDateTime;

public record Message(Object key, LocalDateTime sendingDate, Object value) implements Comparable<Message> {
	@Override
	public int compareTo(Message o) {
		return this.sendingDate.compareTo(o.sendingDate);
	}

	@Override
	public String toString() {
		return this.key + " : " + this.value;
	}
}
