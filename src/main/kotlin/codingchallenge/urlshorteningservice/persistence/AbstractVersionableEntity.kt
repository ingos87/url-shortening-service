package codingchallenge.urlshorteningservice.persistence

import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Version
import org.springframework.data.domain.Persistable
import java.time.Instant
import java.util.UUID

/**
 * The super class for all entities. Providing:
 *
 * - optimistic locking using version
 * - creation date
 */
@MappedSuperclass
abstract class AbstractVersionableEntity : Persistable<String> {

    @Id
    private var id: String? = null

    /** The version field used for optimistic locking. */
    @Version
    protected open val version: Long? = null

    /** Moment this entity was created */
    @Column(name = "CREATED", nullable = false, updatable = false)
    protected open val created: Instant = Instant.now()

    /** Moment this entity was last updated */
    @Column(name = "MODIFIED", insertable = false)
    protected open var modified: Instant? = null

    @PrePersist
    open fun prePersist() {
        id = UUID.randomUUID().toString()
    }

    @PreUpdate
    fun preUpdate() {
        modified = Instant.now()
    }

    // The entity manager checks here if this entity is new or not.
    override fun isNew(): Boolean = version == null

    // This getter and the private field are necessary to compile to the Java interface `Persistable`.
    override fun getId(): String = id ?: throw UninitializedPropertyAccessException("id not yet set")
}
