package ua.edu.ukma.Zhytnetsky.app;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.edu.ukma.Zhytnetsky.contract.Codable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class User implements Codable {

    private String name = "";
    private String email = "";

    @Override
    public int decode(final byte[] bytes, final int offset) {
        final ByteBuffer buffer = ByteBuffer
                .wrap(bytes, offset, bytes.length - offset)
                .order(ByteOrder.BIG_ENDIAN);

        final int nameLength = buffer.getInt();
        final byte[] nameBytes = new byte[nameLength];
        buffer.get(nameBytes);
        this.name = new String(nameBytes, StandardCharsets.UTF_8);

        final int emailLength = buffer.getInt();
        final byte[] emailBytes = new byte[emailLength];
        buffer.get(emailBytes);
        this.email = new String( emailBytes, StandardCharsets.UTF_8);

        return 4 + 4 + nameLength + emailLength + offset;
    }

    @Override
    public byte[] encode() {
        final byte[] nameBytes = this.name.getBytes(StandardCharsets.UTF_8);
        final byte[] emailBytes = this.email.getBytes(StandardCharsets.UTF_8);

        final ByteBuffer buffer = ByteBuffer.allocate(byteLength()).order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(nameBytes.length)
                .put(nameBytes)
                .putInt(emailBytes.length)
                .put(emailBytes);
        return buffer.array();
    }

    @Override
    public int byteLength() {
        return 4 + 4 + this.name.getBytes(StandardCharsets.UTF_8).length
                + this.email.getBytes(StandardCharsets.UTF_8).length;
    }
}
