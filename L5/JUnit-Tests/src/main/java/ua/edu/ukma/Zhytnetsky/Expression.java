package ua.edu.ukma.Zhytnetsky;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class Expression<T extends Number> {

    private T first;
    private T second;
    private Operation<T> op;

    public T evaluate() {
        return this.op.perform(this.first, this.second);
    }

    public String display() {
        return first.toString() + DisplayUtils.mapOperationToSign(op) + second.toString();
    }

}
