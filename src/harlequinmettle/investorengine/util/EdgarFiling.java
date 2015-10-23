package harlequinmettle.investorengine.util;

import java.io.Serializable;

public class EdgarFiling implements Serializable {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + date;
		result = prime * result + filingTypeHashCode;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EdgarFiling other = (EdgarFiling) obj;
		if (date != other.date)
			return false;
		if (filingTypeHashCode != other.filingTypeHashCode)
			return false;
		return true;
	}

	private static final long serialVersionUID = 8927045798517444650L;

	EdgarFiling() {
	}

	public EdgarFiling(short date, int fileType) {
		this.date = date;
		this.filingTypeHashCode = fileType;
	}

	public short date;
	public int filingTypeHashCode;

}
