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
public final class Product implements Codable {

    private String title = "";
    private String description = "";
    private int qtyAvailable;
    private double price;

    @Override
    public int decode(final byte[] bytes, final int offset) {
        final ByteBuffer buffer = ByteBuffer
                .wrap(bytes, offset, bytes.length - offset)
                .order(ByteOrder.BIG_ENDIAN);

        final int titleLength = buffer.getInt();
        final byte[] titleBytes = new byte[titleLength];
        buffer.get(titleBytes);
        this.title = new String(titleBytes, StandardCharsets.UTF_8);

        final int descriptionLength = buffer.getInt();
        final byte[] descriptionBytes = new byte[descriptionLength];
        buffer.get(descriptionBytes);
        this.description = new String(descriptionBytes, StandardCharsets.UTF_8);

        this.qtyAvailable = buffer.getInt();
        this.price = buffer.getDouble();

        return 4 + 4 + 4 + 8 + titleLength + descriptionLength + offset;
    }

    @Override
    public byte[] encode() {
        final byte[] titleBytes = this.title.getBytes(StandardCharsets.UTF_8);
        final byte[] descriptionBytes = this.description.getBytes(StandardCharsets.UTF_8);

        final ByteBuffer buffer = ByteBuffer.allocate(byteLength()).order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(titleBytes.length)
                .put(titleBytes)
                .putInt(descriptionBytes.length)
                .put(descriptionBytes)
                .putInt(this.qtyAvailable)
                .putDouble(this.price);
        return buffer.array();
    }

    @Override
    public int byteLength() {
        return 4 + 4 + 4 + 8 + this.title.getBytes(StandardCharsets.UTF_8).length
                + this.description.getBytes(StandardCharsets.UTF_8).length;
    }
}
