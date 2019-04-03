package de.failender.dgo.user;

import javax.persistence.Column;
import java.util.Date;

public abstract class AuditingEntity extends BaseEntity {
	@Column(name = "CREATED_DATE")
	private Date createdDate;


	public AuditingEntity() {
	}


	public Date getCreatedDate() {
		return this.createdDate;
	}


	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override

	public boolean equals(final Object o) {
		if (o == this) return true;
		if (!(o instanceof AuditingEntity)) return false;
		final AuditingEntity other = (AuditingEntity) o;
		if (!other.canEqual((Object) this)) return false;
		final Object this$createdDate = this.getCreatedDate();
		final Object other$createdDate = other.getCreatedDate();
		if (this$createdDate == null ? other$createdDate != null : !this$createdDate.equals(other$createdDate)) return false;
		return true;
	}


	protected boolean canEqual(final Object other) {
		return other instanceof AuditingEntity;
	}

	@Override

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $createdDate = this.getCreatedDate();
		result = result * PRIME + ($createdDate == null ? 43 : $createdDate.hashCode());
		return result;
	}

	@Override

	public java.lang.String toString() {
		return "AuditingEntity(createdDate=" + this.getCreatedDate() + ")";
	}
}