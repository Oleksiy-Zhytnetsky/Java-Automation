package ua.edu.ukma.Zhytnetsky;

import ua.edu.ukma.Zhytnetsky.app.FeedbackReport;
import ua.edu.ukma.Zhytnetsky.app.Product;
import ua.edu.ukma.Zhytnetsky.app.User;
import ua.edu.ukma.Zhytnetsky.model.Packet;
import ua.edu.ukma.Zhytnetsky.utils.DisplayUtils;
import ua.edu.ukma.Zhytnetsky.utils.EncryptionUtils;

public final class Main {

    public static void main(String[] args) {
        // Original object
        final User originalUser = new User("Mike Smith", "mike_smith@gmail.com");
        final Product originalProduct = new Product(
                "Table",
                "Red wood small kitchen table",
                5,
                250
        );
        final FeedbackReport originalReport = new FeedbackReport(
                "Great and reliable - 10/10",
                originalUser,
                originalProduct
        );
        final Packet<FeedbackReport> packet = new Packet<>(originalReport);

        // Encode
        final byte[] encodedMessage = packet.encode();
        System.out.println("Encoded msg:    " + DisplayUtils.bytesToHexString(encodedMessage));

        // Encrypt
        final byte[] encryptedMessage = EncryptionUtils.encrypt(encodedMessage);
        System.out.println("Encrypted msg:  " + DisplayUtils.bytesToHexString(encryptedMessage));

        // Decrypt
        final byte[] decryptedMessage = EncryptionUtils.decrypt(encryptedMessage);
        System.out.println("Decrypted msg:  " + DisplayUtils.bytesToHexString(decryptedMessage));

        // Decode
        final Packet<FeedbackReport> decodedMessage = new Packet<>(new FeedbackReport());
        decodedMessage.decode(decryptedMessage, 0);

        // Received object
        final FeedbackReport decodedReport = decodedMessage.getPayload();
        System.out.println("Decoded report: \"" + decodedReport.getFeedback() + "\" by "
                + decodedReport.getUser().getName() + " (" + decodedReport.getUser().getEmail()
                + ") for product: " + decodedReport.getProduct().getTitle() + " ("
                + decodedReport.getProduct().getDescription() + " for " + decodedReport.getProduct().getPrice()
                + " -- " + decodedReport.getProduct().getQtyAvailable() + " left)"

        );
    }

}