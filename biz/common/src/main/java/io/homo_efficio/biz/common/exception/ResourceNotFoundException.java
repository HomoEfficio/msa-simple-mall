package io.homo_efficio.biz.common.exception;

import lombok.Getter;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-07-24
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final Class<?> resourceClazz;

    private Object resourceId;

    public ResourceNotFoundException(Class<?> resourceClazz) {
        super("자원이 존재하지 않습니다.");
        this.resourceClazz = resourceClazz;
    }

    public ResourceNotFoundException(Class<?> resourceClazz, String id) {
        super(String.format("ID [%s] 인 %s 는 존재하지 않습니다.", id, resourceClazz.getSimpleName()));
        this.resourceClazz = resourceClazz;
    }

    public ResourceNotFoundException(Class<?> resourceClazz, Long id) {
        super(String.format("ID [%d] 인 %s 는 존재하지 않습니다.", id, resourceClazz.getSimpleName()));
        this.resourceId = id;
        this.resourceClazz = resourceClazz;
    }

    public ResourceNotFoundException(Class<?> resourceClazz, String message, Object id) {
        super(message);
        this.resourceClazz = resourceClazz;
    }


    public String getResourceClassSimpleName() {
        return resourceClazz.getSimpleName();
    }
}
