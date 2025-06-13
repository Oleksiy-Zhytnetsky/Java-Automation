package ua.edu.ukma.Zhytnetsky.model;

import lombok.Data;
import ua.edu.ukma.Zhytnetsky.config.AppConfig;
import ua.edu.ukma.Zhytnetsky.contract.Codable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@Data
public final class Message<T extends Codable> implements Codable {

    private int type;
    private int userId;
    private T content;

    public Message(final T payload) {
        this.type = AppConfig.TEAM_CODE;
        this.userId = AppConfig.APP_USER_ID;
        this.content = payload;
    }

    @Override
    public int decode(final byte[] bytes, final int offset) {
        final ByteBuffer buffer = ByteBuffer
                .wrap(bytes, offset, bytes.length - offset)
                .order(ByteOrder.BIG_ENDIAN);
        this.type = buffer.getInt();
        this.userId = buffer.getInt();
        return this.content.decode(bytes, offset + 4 + 4);
    }

    @Override
    public byte[] encode() {
        final ByteBuffer buffer = ByteBuffer.allocate(byteLength()).order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(this.type)
                .putInt(this.userId)
                .put(this.content.encode());
        return buffer.array();
    }

    @Override
    public int byteLength() {
        return 4 + 4 + this.content.byteLength();
    }

}
