package io.homo_efficio.monolith.simple_mall._common.exception;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
public class NoStockRemainedException extends RuntimeException {

    public NoStockRemainedException(String message) {
        super(message);
    }

    public NoStockRemainedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoStockRemainedException(long current, long toBeSold) {
        super(String.format("현재 [%d]개 남아 있어서 [%d]개를 주문할 수 없습니다.", current, toBeSold));
    }
}
