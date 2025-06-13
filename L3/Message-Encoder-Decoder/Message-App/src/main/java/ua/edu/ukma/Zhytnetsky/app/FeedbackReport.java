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
public final class FeedbackReport implements Codable {

    private String feedback = "";
    private User user = new User();
    private Product product = new Product();

    @Override
    public int decode(final byte[] bytes, final int offset) {
        final ByteBuffer buffer = ByteBuffer
                .wrap(bytes, offset, bytes.length - offset)
                .order(ByteOrder.BIG_ENDIAN);

        final int feedbackLength = buffer.getInt();
        final byte[] feedbackBytes = new byte[feedbackLength];
        buffer.get(feedbackBytes);
        this.feedback = new String(feedbackBytes, StandardCharsets.UTF_8);
        int nextOffset = 4 + feedbackLength + offset;

        nextOffset = this.user.decode(bytes, nextOffset);
        nextOffset = this.product.decode(bytes, nextOffset);
        return nextOffset;
    }

    @Override
    public byte[] encode() {
        final byte[] feedbackBytes = this.feedback.getBytes(StandardCharsets.UTF_8);

        final ByteBuffer buffer = ByteBuffer.allocate(byteLength()).order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(feedbackBytes.length)
                .put(feedbackBytes)
                .put(user.encode())
                .put(product.encode());
        return buffer.array();
    }

    @Override
    public int byteLength() {
        return 4 + this.feedback.getBytes(StandardCharsets.UTF_8).length
                + this.user.byteLength() + this.product.byteLength();
    }
}
