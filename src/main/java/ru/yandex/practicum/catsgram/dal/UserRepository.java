package ru.yandex.practicum.catsgram.dal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.catsgram.model.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserRepository extends BaseRepository<User> {

    private static final String GENERATOR_NAME = "public.s_user_id";
    private static final String FIND_ALL_QUERY = "SELECT * FROM public.user";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM public.user WHERE id = ?";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM public.user WHERE email = ?";
    private static final String INSERT_QUERY = "INSERT INTO public.user(id, username, email, password, registration_date)" +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE public.user SET username = ?, email = ?, password = ? WHERE id = ?";

    public UserRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    public List<User> findAll() {
        return findAll(FIND_ALL_QUERY);
    }

    public List<User> findAllByEmail(String email) {
        return findAll(FIND_BY_EMAIL_QUERY, email);
    }

    public Optional<User> findById(Long id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }

    public Optional<User> findByEmail(String email) {
        return findOne(FIND_BY_EMAIL_QUERY, email);
    }

    public User save(User user) {

        user.setId(getNextId(GENERATOR_NAME));

        save(
            INSERT_QUERY,
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            Timestamp.from(user.getRegistrationDate())
        );

        return user;
    }

    public User update(User user) {

        save(
            UPDATE_QUERY,
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            user.getId()
        );

        return user;

    }

}
