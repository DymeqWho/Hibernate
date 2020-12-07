package pl.misiejuk.dymitr.kalkulator.walut.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import pl.misiejuk.dymitr.kalkulator.walut.service.Exchange;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class DatabaseDaoHibernateImpl implements DatabaseDao {

    private static final SessionFactory sessionFactory;

    static {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        final Metadata metadata = new MetadataSources(registry)
                .buildMetadata();

        sessionFactory = metadata.buildSessionFactory();
    }


    @Override //powinno działać pobieranie bazy danych z bazy danych
    public Exchange getByCriteria(String currencyFrom, String currencyTo, LocalDate day) {

        Session session = sessionFactory.openSession();

        Query<ExchangeEntity> q = session.createQuery("select exchange " +
                " from ExchangeEntity exchange " +
                "where exchange.fromCurrency=:from " +
                "and exchange.toCurrency=:to " +
                "and exchange.exchangeDate=:day", ExchangeEntity.class); //HQL and JPQL
        q.setParameter("from", currencyFrom);
        q.setParameter("to", currencyTo);
        q.setParameter("day", day);

        Exchange ex = null;
        try {
            ExchangeEntity e = q.getSingleResult();
            ex = fromEntity(e);
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }

        session.close();
        return ex;
    }

    @Override //zapisywanie do bazy danych
    public void save(Exchange data) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.getTransaction();
            session.save(fromDto(data));
            transaction.commit();


        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        session.close();
        //throw new UnsupportedOperationException("not implemented yet");
    }

    static ExchangeEntity fromDto(Exchange dto) {
        ExchangeEntity entity = new ExchangeEntity();
        entity.setFromCurrency(dto.getCurrencyFrom());
        entity.setToCurrency(dto.getCurrencyTo());
        entity.setRate(BigDecimal.valueOf(dto.getExchangeRate()));
        entity.setExchangeDate(dto.getExchangeDate());
        return entity;
    }

    static Exchange fromEntity(ExchangeEntity entity) {
        Exchange exchange = new Exchange();
        exchange.setCurrencyFrom(entity.getFromCurrency());
        exchange.setCurrencyTo(entity.getToCurrency());
        exchange.setExchangeRate(entity.getRate().doubleValue());
        exchange.setExchangeDate(entity.getExchangeDate());
        return exchange;
    }
}
