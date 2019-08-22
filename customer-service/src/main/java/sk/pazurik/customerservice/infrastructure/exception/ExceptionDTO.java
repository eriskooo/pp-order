package sk.pazurik.customerservice.infrastructure.exception;

import sk.pazurik.customerservice.infrastructure.value.AbstractValueObject;

public class ExceptionDTO extends AbstractValueObject {

    private Integer code;
    private String message;

    public ExceptionDTO() {
        super();
    }

    public ExceptionDTO(Integer code, String message) {
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
