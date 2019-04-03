package de.failender.dgo.user;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;


	public BaseEntity() {
	}


	public Integer getId() {
		return this.id;
	}


	public void setId(final Integer id) {
		this.id = id;
	}

	@Override

	public boolean equals(final Object o) {
		if (o == this) return true;
		if (!(o instanceof BaseEntity)) return false;
		final BaseEntity other = (BaseEntity) o;
		if (!other.canEqual((Object) this)) return false;
		final Object this$id = this.getId();
		final Object other$id = other.getId();
		if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
		return true;
	}


	protected boolean canEqual(final Object other) {
		return other instanceof BaseEntity;
	}

	@Override

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $id = this.getId();
		result = result * PRIME + ($id == null ? 43 : $id.hashCode());
		return result;
	}

	@Override

	public java.lang.String toString() {
		return "BaseEntity(id=" + this.getId() + ")";
	}
}