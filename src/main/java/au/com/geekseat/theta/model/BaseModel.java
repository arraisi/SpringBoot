package au.com.geekseat.theta.model;

import au.com.geekseat.theta.helper.MapConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @CreatedBy
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String updatedBy;

    @JsonIgnore
    @CreatedDate
    private LocalDateTime created;

    @JsonIgnore
    @LastModifiedDate
    private LocalDateTime updated;

    @JsonIgnore
    @Type(type = "text")
    protected String creator;

    @JsonIgnore
    @Type(type = "text")
    protected String editor;

    @Convert(converter = MapConverter.class)
    protected Object storageMap;

    @Transient
    @Convert(converter = MapConverter.class)
    protected Object transitMap;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public Object getStorageMap() {
        return storageMap;
    }

    public void setStorageMap(Object storageMap) {
        this.storageMap = storageMap;
    }

    public Object getTransitMap() {
        return transitMap;
    }

    public void setTransitMap(Object transitMap) {
        this.transitMap = transitMap;
    }
}
