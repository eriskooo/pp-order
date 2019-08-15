package sk.pazurik.customerservice.infrastructure.entity;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Abstract entity. Provides means for entity classes.
 */
@MappedSuperclass
public abstract class AbstractEntity<I> implements Identifiable<I>, Serializable {

  @Override
  public int hashCode() {
    I id = getId();
    return id == null ? super.hashCode() : id.hashCode();
  }

  @Override
  public final boolean equals(final Object object) {
    if (this == object) {
      return true;
    }

    if (object == null
        || !(object.getClass().isAssignableFrom(getClass()) && getClass().isAssignableFrom(object.getClass()))) {
      return false;
    }

    AbstractEntity<I> entity = (AbstractEntity<I>) object;
    return getId() != null && getId().equals(entity.getId());
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "#" + getId();
  }
}
