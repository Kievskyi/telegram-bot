package com.example.demo5bot.repository.impl;

import com.example.demo5bot.entity.User;
import com.example.demo5bot.entity.Vocabulary;
import com.example.demo5bot.enums.Emoji;
import com.example.demo5bot.enums.MessageStatus;
import com.example.demo5bot.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
public class UserRepositoryMySqlImpl implements UserRepository {

    private final SessionFactory sessionFactory;


    public UserRepositoryMySqlImpl() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "org.postgresql.Driver");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.put(Environment.URL, "jdbc:postgresql://ec2-34-193-44-192.compute-1.amazonaws.com:5432/dce122h3cmnomq");
        properties.put(Environment.USER, "ibknamckvngwjb");
        properties.put(Environment.PASS, "0f78046d8a1cecb355fb07337341828c91ec511df0f0fb76f75bd84766c79e59");

        sessionFactory = new Configuration()
                .setProperties(properties)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Vocabulary.class)
                .buildSessionFactory();
    }

    @Override
    public void saveNewWord(long userId, String englishWord, String translationWord) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Vocabulary newWord = new Vocabulary();
        String hql = "FROM User WHERE userId = :user_id";

        Query<User> userQuery = session.createQuery(hql, User.class);
        userQuery.setParameter("user_id", userId);

        newWord.setEnglishWord(englishWord);
        newWord.setTranslation(translationWord);
        newWord.setUser(userQuery.getSingleResult());

        session.save(newWord);
        transaction.commit();
    }

    @Override
    public String getAllWords(long userId, MessageStatus messageStatus) {
        Session session = sessionFactory.openSession();
        String hqlWithoutTransl = "SELECT englishWord FROM Vocabulary WHERE user.userId = :user_id ORDER BY englishWord";
        String hqlWithTransl = "SELECT concat('<b>',englishWord, '</b> - ',  translation) FROM Vocabulary WHERE user.userId = :user_id ORDER BY englishWord";
        Query<String> query = null;

        if (messageStatus == MessageStatus.GET_ALL_WORDS_WITH_TRANSLATION) {
            query = session.createQuery(hqlWithTransl, String.class);
            query.setParameter("user_id", userId);
        } else if (messageStatus == MessageStatus.GET_ALL_WORDS_WITHOUT_TRANSLATION) {
            query = session.createQuery(hqlWithoutTransl, String.class);
            query.setParameter("user_id", userId);
        }

        return buildWords(query.list());
    }

    @Override
    public boolean deleteWord(long userId, String englishWord) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query<Vocabulary> query = session.createQuery("FROM Vocabulary WHERE user.userId = :user_id AND englishWord = :eng_word", Vocabulary.class);
        query.setParameter("user_id", userId);
        query.setParameter("eng_word", englishWord);

        if (query.list().size() == 0) {
            return false;
        }

        for (Vocabulary vocabulary : query.list()) {
            session.delete(vocabulary);
        }

        transaction.commit();

        return true;
    }

    private String buildWords(List<String> list) {
        StringBuilder builder = new StringBuilder();
        String sadCatEmoji = Emoji.CRYING_CAT.getEmoji();

        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                builder.append(i + 1).append(". ").append(list.get(i)).append("\n");
            }
        } else {
            builder.append("Ваш список слів поки що порожній " + sadCatEmoji);
        }

        return builder.toString();
    }

    @Override
    public Map<String, String> getQuizWords(long userId) {
        Session session = sessionFactory.openSession();
        Query<Vocabulary> query = session.createQuery("FROM Vocabulary WHERE user.userId = :user_id ORDER BY RAND()", Vocabulary.class);
        query.setParameter("user_id", userId);
        query.setMaxResults(1);

        Map<String, String> wordsMap = new HashMap<>();

        for (Vocabulary vocabulary : query.list()) {
            wordsMap.put(vocabulary.getEnglishWord(), vocabulary.getTranslation());
        }

        return wordsMap;
    }

    @Override
    public void createNewUser(long userId, String name) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User newUser = new User();
        newUser.setUserId(userId);
        newUser.setName(name);

        session.save(newUser);
        transaction.commit();
    }

    public boolean isUserExist(long userId) {
        Session session = sessionFactory.openSession();
        String hql = "FROM User WHERE userId = :user_id";

        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("user_id", userId);

        return !query.list().isEmpty();
    }

    @Override
    public List<Long> getAllUsers() {
        Session session = sessionFactory.openSession();
        String hql = "SELECT userId FROM User";

        return session.createQuery(hql, Long.class).list();
    }
}