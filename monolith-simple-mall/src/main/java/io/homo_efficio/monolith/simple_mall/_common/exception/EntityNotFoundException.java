package io.homo_efficio.monolith.simple_mall._common.exception;

import io.homo_efficio.monolith.simple_mall.domain.BaseEntity;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */

public class EntityNotFoundException extends RuntimeException {

    private final Class<? extends BaseEntity> entityClazz;

    private Long entityId;

    public EntityNotFoundException(Class<? extends BaseEntity> entityClazz) {
        this.entityClazz = entityClazz;
    }

    public EntityNotFoundException(Class<? extends BaseEntity> entityClazz, String message) {
        super(message);
        this.entityClazz = entityClazz;
    }

    public EntityNotFoundException(Class<? extends BaseEntity> entityClazz, Long id) {
        super(String.format("ID [%d] 인 %s 는 존재하지 않습니다.", id, entityClazz.getSimpleName()));
        this.entityId = id;
        this.entityClazz = entityClazz;
    }

    public Long getEntityId() {
        return entityId;
    }

    public Class<? extends BaseEntity> getEntityClazz() {
        return entityClazz;
    }

    public String getEntityClassSimpleName() {
        return entityClazz.getSimpleName();
    }
}
