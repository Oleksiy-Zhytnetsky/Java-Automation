package ua.edu.ukma.Zhytnetsky.model;

import lombok.Data;
import ua.edu.ukma.Zhytnetsky.utils.ValidationUtils;
import ua.edu.ukma.Zhytnetsky.config.AppConfig;
import ua.edu.ukma.Zhytnetsky.contract.Codable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@Data
public final class Packet<T extends Codable> implements Codable {

    private static long currentPktId = -1;

    private final byte magic = AppConfig.MAGIC_BYTE_VALUE;
    private byte src;
    private long pktId;
    private int len;
    private Short headerCrc16; // null until encoded/decoded
    private Message<T> msg;
    private Short msgCrc16; // null until encoded/decoded

    public Packet(final T payload) {
        this.msg = new Message<>(payload);

        this.src = AppConfig.APP_CLIENT_ID;
        this.pktId = ++Packet.currentPktId;
        this.len = this.msg.byteLength();
    }

    public T getPayload() {
        return this.msg.getContent();
    }

    @Override
    public int decode(final byte[] bytes, final int offset) {
        final ByteBuffer buffer = ByteBuffer
                .wrap(bytes, offset, bytes.length - offset)
                .order(ByteOrder.BIG_ENDIAN);

        ValidationUtils.validateMagicByte(buffer.get());
        this.src = buffer.get();
        this.pktId = buffer.getLong();
        this.len = buffer.getInt();
        this.headerCrc16 = buffer.getShort();
        ValidationUtils.validateCrc(this.headerCrc16, bytes, offset, 14);

        final int nextOffset = this.msg.decode(bytes, offset + 16);
        this.msgCrc16 = buffer.getShort(nextOffset);
        ValidationUtils.validateCrc(this.msgCrc16, bytes, offset + 16, this.len);

        return 1 + 1 + 8 + 4 + 2 + 2 + this.len + offset;
    }

    @Override
    public byte[] encode() {
        final byte[] msgBytes = this.getMsg().encode();
        final ByteBuffer buffer = ByteBuffer.allocate(byteLength()).order(ByteOrder.BIG_ENDIAN);

        buffer.put(this.magic)
                .put(this.src)
                .putLong(this.pktId)
                .putInt(msgBytes.length);

        this.headerCrc16 = ValidationUtils.calculateCrc(buffer.array(), 0, 14);;
        buffer.putShort(this.headerCrc16)
                .put(msgBytes);

        this.msgCrc16 = ValidationUtils.calculateCrc(buffer.array(), 16, msgBytes.length);
        buffer.putShort(this.msgCrc16);

        return buffer.array();
    }

    @Override
    public int byteLength() {
        return 1 + 1 + 8 + 4 + 2 + 2 + this.msg.byteLength();
    }

}
