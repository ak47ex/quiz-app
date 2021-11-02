package ru.pyatkinmv.dao;

import net.bytebuddy.utility.RandomString;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class QuizShortcutGenerator implements IdentifierGenerator {

    private static final int SHORTCUT_LENGTH = 6;
    private static final char[] ALPHABET =  new char[]{'1','2','3','4','5','6','7','8','9','0'};

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return generateShortcut();
//        Connection conn = sharedSessionContractImplementor.connection();
//        try {
//            PreparedStatement stmt = conn.prepareStatement("INSERT INTO shortcut (shortcut)  VALUES (?)");
//            String shortcut = generateShortcut();
//            stmt.setString(1, shortcut);
//            stmt.executeUpdate();
//            System.out.println("Insert '" + shortcut + "'");
//            return shortcut;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    @Override
    public boolean supportsJdbcBatchInserts() {
        return IdentifierGenerator.super.supportsJdbcBatchInserts();
    }

    private String generateShortcut() {
        final Random rnd = ThreadLocalRandom.current();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SHORTCUT_LENGTH; ++i) {
            int index = Math.abs(rnd.nextInt() % ALPHABET.length);
            sb.append(ALPHABET[index]);
        }
        return sb.toString();
    }
}
