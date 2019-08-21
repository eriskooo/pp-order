package sk.pazurik.customerservice.infrastructure.exception;

import sk.pazurik.customerservice.infrastructure.value.AbstractValueObject;

public class ValidationExceptionDTO extends AbstractValueObject {

    private Integer code;
    private String message;

    public ValidationExceptionDTO() {
        super();
    }

    public ValidationExceptionDTO(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    protected Object[] values() {
        return new Object[]{code, message};
    }
}
