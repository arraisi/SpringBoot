package io.arraisi.theta.dao;

import io.arraisi.theta.helper.Utility;
import io.arraisi.theta.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PersonDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<Person> listDataTables(Long page, Long itemsPerPage, List<String> sortBy, List<Boolean> sortDesc) {
        StringBuilder baseQuery = new StringBuilder("" +
                "select "
                + "id, "
                + "map_data, "
                + "name, "
                + "email, "
                + "password, "
                + "active " +
                "from person ");

        String querySortingAndLimit = Utility.querySorting(sortBy, sortDesc);
        baseQuery.append(querySortingAndLimit);
        baseQuery.append(" LIMIT :offset,:size");

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("offset", page * itemsPerPage);
        map.addValue("size", itemsPerPage);

        return this.jdbcTemplate.query(baseQuery.toString(), map, (rs, rowNum) -> {
            Person person = new Person();
            person.setId(rs.getLong("id"));
            person.setName(rs.getString("name"));
            person.setEmail(rs.getString("email"));
            person.setActive(rs.getBoolean("active"));
            return person;
        });
    }

    public Long rowCountDatatables() {
        String query = "select count(*) rowCount from person";
        return jdbcTemplate.queryForObject(query, new MapSqlParameterSource(), Long.class);
    }
}
