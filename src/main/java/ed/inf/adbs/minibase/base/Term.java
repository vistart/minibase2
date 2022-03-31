package ed.inf.adbs.minibase.base;

import java.util.Objects;

public class Term {

	public String getName() {
		return "";
	}

	@Override
	public boolean equals(Object obj)
	{
		return (obj instanceof Term && Objects.equals(this.toString(), obj.toString()));
	}
}


